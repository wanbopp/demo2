/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.core.datascope.handler;

import lombok.RequiredArgsConstructor;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.datascope.enums.DataScopeEnum;
import org.springblade.core.datascope.model.DataScopeModel;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.PlaceholderUtil;
import org.springblade.core.tool.utils.StringUtil;

import java.util.*;
import java.util.stream.Collectors;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 默认数据权限规则
 *
 * @author Chill
 */
@RequiredArgsConstructor
public class BladeDataScopeHandler implements DataScopeHandler {

	private final ScopeModelHandler scopeModelHandler;

	@Override
	public String sqlCondition(String mapperId, DataScopeModel dataScope, BladeUser bladeUser, String originalSql) {

		//数据权限资源编号
		String code = dataScope.getResourceCode();

		//根据mapperId从数据库中获取对应模型
		DataScopeModel dataScopeDb = scopeModelHandler.getDataScopeByMapper(mapperId, bladeUser.getRoleId());
		System.err.println("sqlCondition---》 bladeUser " + bladeUser.getCompanyId());
		bladeUser = scopeModelHandler.getUserByMapper(bladeUser);
		System.err.println("sqlCondition---》getUserByMapper " + bladeUser.getCompanyId());

		//mapperId配置未取到则从数据库中根据资源编号获取
		if (dataScopeDb == null && StringUtil.isNotBlank(code)) {
			dataScopeDb = scopeModelHandler.getDataScopeByCode(code);
		}

		//未从数据库找到对应配置则采用默认
		dataScope = (dataScopeDb != null) ? dataScopeDb : dataScope;
		// 参数
		Map<String,Object> params = new HashMap<>();

		//判断数据权限类型并组装对应Sql
		Integer scopeRule = Objects.requireNonNull(dataScope).getScopeType();
		DataScopeEnum scopeTypeEnum = DataScopeEnum.of(scopeRule);
		List<String> ids = new ArrayList<>();
		String whereSql = "where scope.{} in ({})";
		if (DataScopeEnum.ALL == scopeTypeEnum || StringUtil.containsAny(bladeUser.getRoleName(), RoleConstant.ADMINISTRATOR)) {
			return null;
		} else if (DataScopeEnum.CUSTOM == scopeTypeEnum || DataScopeEnum.CUSTOM_STATISTICS == scopeTypeEnum) {
			//自定义
			//whereSql = PlaceholderUtil.getDefaultResolver().resolveByMap(dataScope.getScopeValue(), BeanUtil.toMap(bladeUser));

			//下级部门
			params.put("childrenDeptId",StringUtil.join(getDeptAncestors(bladeUser)));
			//本部门及下级部门
			String childrenAllDeptId = String.format("'%s'",StringUtil.join(getDeptAncestorsAll(bladeUser),"','"));
			params.put("childrenAllDeptId",childrenAllDeptId);
			//本部门
//			params.put("deptIds",StringUtil.join(Func.toStrList(bladeUser.getDeptId())));
			params.put("deptIds",StringUtil.join(Func.toStrList(String.format("'%s'",bladeUser.getDeptId())),"','"));
			System.err.println("------>deptIds-------> #:" + params.get("deptIds"));
			//本角色
			params.put("roleNames",StringUtil.join(Func.toStrList(bladeUser.getRoleName())));
			whereSql = "";
		} else if (DataScopeEnum.OWN == scopeTypeEnum) {
			ids.add(bladeUser.getUserId().toString());
		} else if (DataScopeEnum.OWN_DEPT == scopeTypeEnum || DataScopeEnum.OWN_DEPT_HEAD == scopeTypeEnum) {
			// 本部门及本人可见
			whereSql = StringUtil.format("where (scope.create_user = {} or ",bladeUser.getUserId());
			// 如果类型是部门负责人，则只有本人及本部门负责人可见
			if(DataScopeEnum.OWN_DEPT_HEAD == scopeTypeEnum){
				whereSql += StringUtil.format(" {} = '2' and", bladeUser.getDeptHead());
			}
			whereSql += " scope.{} in ({})) ";
			ids.addAll(Func.toStrList(bladeUser.getDeptId()));

		} else if (DataScopeEnum.OWN_DEPT_CHILD == scopeTypeEnum) {
			//本部门 及 子部门
			List<Long> deptAncestors = getDeptAncestorsAll(bladeUser);
			List<String> deptIds = deptAncestors.stream().map(id -> id.toString()).collect(Collectors.toList());
			ids.addAll(deptIds);
		}else if (DataScopeEnum.OWN_DEPT_PARENT_CHILD == scopeTypeEnum || DataScopeEnum.OWN_DEPT_HEAD_CHILD == scopeTypeEnum) {
			//本人及所在机构负责人及子级可见
			List<Long> deptAncestors = getDeptAncestorsAll(bladeUser);
			List<String> deptIds = deptAncestors.stream().map(id -> id.toString()).collect(Collectors.toList());
			ids.addAll(deptIds);

			whereSql = StringUtil.format("where (scope.create_user = {} or ",bladeUser.getUserId());
			// 如果类型是部门负责人
			if(DataScopeEnum.OWN_DEPT_HEAD_CHILD == scopeTypeEnum){
				whereSql += StringUtil.format("  {} = '2' and", bladeUser.getDeptHead());
			}
			whereSql += " scope.{} in ({})) ";
			//ids.add(bladeUser.getUserId());
		}
		String scopeValue = dataScope.getScopeValue();
		//扩展自定义查询语句
		if(StringUtil.isNotBlank(scopeValue)) {
			params.putAll(BeanUtil.toMap(bladeUser));
			scopeValue = PlaceholderUtil.getDefaultResolver().resolveByMap(dataScope.getScopeValue(), params);
			System.err.println("scopeValue $:" + scopeValue);
			// 红名单设置
			if(scopeValue.contains("#")){
				scopeValue = PlaceholderUtil.getResolver("#{", "}")
					.resolveByRule(scopeValue, placeholderValue ->scopeModelHandler.getRedUser(placeholderValue));

				System.err.println("scopeValue #:" + scopeValue);
			}
			whereSql += scopeValue;
		}

		String resultSql = StringUtil.format("select {} from ({}) scope " + whereSql,
			Func.toStr(dataScope.getScopeField(), "*"), originalSql,
			dataScope.getScopeColumn(), StringUtil.join(ids));

		if(DataScopeEnum.CUSTOM_STATISTICS == scopeTypeEnum){
			String originalSql1 = originalSql.substring(0,originalSql.lastIndexOf("WHERE")) +" {} and "
				+ originalSql.substring(originalSql.lastIndexOf("WHERE")+5,originalSql.length());

			resultSql = StringUtil.format(originalSql1, whereSql);
		}

		return resultSql;
	}

	private void getChildrenDeptId(List<Long> deptIds,Set<Long> childrenAllDeptId){
		for(Long deptId : deptIds){
			List<Long> deptIdList = scopeModelHandler.getDeptAncestors(deptId);
			if(deptIdList==null || deptIdList.isEmpty()) {
				return;
			}
			childrenAllDeptId.addAll(deptIdList);
			getChildrenDeptId(deptIdList,childrenAllDeptId);
		}

	}

	/**
	 * 获取当前用户下级所有部门
	 * @param bladeUser
	 * @return
	 */
	private List<Long> getDeptAncestors(BladeUser bladeUser){
		List<Long> deptIds = Func.toLongList(bladeUser.getDeptId());

		List<Long> childrenDeptId = new ArrayList<>();
		childrenDeptId.add(-1L);
		deptIds.forEach(deptId -> {
			List<Long> deptIdList = scopeModelHandler.getDeptAncestors(deptId);
			if(deptIdList!=null) {
				childrenDeptId.addAll(deptIdList);
			}
		});

		return childrenDeptId;
	}

	/**
	 * 获取当前本部门及下级所有部门
	 * @param bladeUser
	 * @return
	 */
	private List<Long> getDeptAncestorsAll(BladeUser bladeUser){
		List<Long> deptIds = Func.toLongList(bladeUser.getDeptId());

		List<Long> childrenDeptId = getDeptAncestors(bladeUser);

		childrenDeptId.addAll(deptIds);
		return childrenDeptId;
	}


//	public static List<Long> getRedUser(String d){
//		System.err.println(d);
//		String RED_USER_CACHE_ANCESTORS = "red:users:";
//
//		List userIds =  null; //CacheUtil.get(SYS_CACHE, RED_USER_CACHE_ANCESTORS, d, List.class);
//		if(userIds==null){
//			userIds = new ArrayList();
//		}
//		if(userIds.isEmpty()){
//			userIds.add(-1);
//		}
//		return userIds;
//	}
	public static void main(String[] args) {

//		String originalSql = "select count(id) from ratutech_work_order_task r where r.is_deleted = 0 and r.work_order_type = 1 and r.tenant_id = '038750'";
//		String whereSql = "where r.handle_user= ${userId} or r.handle_dept  in (${childrenAllDeptId})   and ${deptHead}=2 and ";
//
//		String originalSql1 = originalSql.substring(0,originalSql.lastIndexOf("WHERE")) +" {} "
//			+ originalSql.substring(originalSql.lastIndexOf("WHERE")+5,originalSql.length());
//
//		String resultSql = StringUtil.format(originalSql1, whereSql);
//		System.err.println(resultSql);
//
//		System.err.println(StringUtil.join(Func.toLongList("3333,4444,555")));
//		String scopeValue = "where  FIND_IN_SET(${userId}, #{customer})  and scope.create_user = ${userId}  and scope.create_dept in (${childrenAllDeptId})  ";
//		Map params = new HashMap();
//		params.put("userId","1");
//		params.put("childrenAllDeptId","2,3");
//		scopeValue = PlaceholderUtil.getDefaultResolver().resolveByMap(scopeValue, params);

		// 红名单设置
//		if(scopeValue.contains("#")){
//			scopeValue = PlaceholderUtil.getResolver("#{", "}")
//				.resolveByRule(scopeValue, placeholderValue -> StringUtil.join(getRedUser(placeholderValue)));
//		}
//		whereSql += scopeValue;


//		System.err.println(scopeValue);
		String de1 = "234234234234234,123123123123";
		List<Long> lon = Func.toLongList(de1);
		String de = Func.join(lon,"','");

		Map params = new HashMap();
		params.put("deptIds", String.format("'%s'",de));
		System.err.println(params.get("deptIds"));
		String whereSql = " where 1=1 ";
		String scopeValue = " dept_id in (${deptIds}) ";

		scopeValue = PlaceholderUtil.getDefaultResolver().resolveByMap(scopeValue, params);

		System.err.println(scopeValue);
//		Map params = new HashMap();
//		params.put("create_user", "234234234234234");
//
//		scopeValue = PlaceholderUtil.getDefaultResolver().resolveByMap(scopeValue, params);
//
//		scopeValue = PlaceholderUtil.getResolver("#{", "}")
//			.resolveByRule(scopeValue, placeholderValue -> StringUtil.join(scopeModelHandler.getRedUser(placeholderValue)));
//
//		whereSql +=scopeValue;

		System.err.println(whereSql);

	}
//
//	public static List<Long> getRedUser(String str){
//		List<Long> d = new ArrayList<>();
//		d.add(234234L);
//		d.add(234235L);
//		return d;
//	}


}
