package com.example.piccdemo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/6 11:15
 * @注释
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackDto {


    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 报价单id
     */
    private Long inquriyId;

    /**
     * 车牌号
     */
    private String plate;

    /**
     * 车架号
     */
    private String vin;

    /**
     * 委托方
     */
    private String client;

    /**
     * 委托方机构名称
     */
    private String clientOrgan;

    /**
     * 车辆分公司
     */
    private String comBranch;

    /**
     * 车型
     */
    private String vehkindName;

    /**
     * 提车时间(车辆出库时间)
     */
    private LocalDateTime pickupTime;

    /**
     * 过户截止时间
     */
    private LocalDateTime transferEndTime;
    /**
     * 过户截止时间-查询条件-结束时间
     */
    private LocalDateTime transferEndQueryTime;

    /**
     * 上次跟踪时间
     */
    private LocalDateTime latestTrackTime;

    /**
     * 买家id
     */
    private Long buyerId;

    /**
     * 买家姓名
     */
    private String buyerName;

    /**
     * 买家手机号
     */
    private String buyerPhone;
    /**
     * 买家所属分公司
     */
    private String buyerComBranchId;
    /**
     * 买家所属会员经理姓名
     */
    private String managerName;
    /**
     * 买家所属会员经理手机号
     */
    private String managerPhone;

    /**
     * 会员所属分公司code
     */
    private String memberFilialeCode;

    /**
     * 车主电话
     */
    private String carOwnerPhone;

    /**
     * 车主姓名
     */
    private String carOwnerName;

    /**
     * 车辆品牌
     */
    private String brandName;

    /**
     * 车辆编码
     */
    private String vehicleCode;

    /**
     * 竞拍成功时间
     */
    private LocalDateTime auctionSuccessDate;
    /**
     * 竞拍成功时间-查询条件-结束时间
     */
    private LocalDateTime auctionSuccessEndDate;


    /**
     * 过户类型   0:过户;1:解体;
     */
    private String transferType;


    /**
     * 跟踪完成  0 未完成  1 完成
     */
    private String trackComplete;


    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private String overDate;

    /**
     * 尾款支付时间
     */
    private LocalDateTime finalPayment;

    /**
     * 00财险待反馈 01财险反馈过户通过 02 财险反馈过户不通过  03 分总待审核 04 分总审核不通过
     */
    private String trackStatus;

    /**
     * 过户专员名称
     */
    private String tracker;

    /**
     * 过户专员id
     */
    private Long trackerId;
    /**
     * 过户专员电话
     */
    private String trackerPhone;

    /**
     * 是否为代办过户  0 否  1 是
     */
    private String agencyTrans;


    private String comment;
    /**
     * 过户时间
     */
    private LocalDateTime transferTime;

    /**
     * 1 已反馈 0 未反馈
     */
    private String feedbackStatus;
    private LocalDateTime feedbackTime;

    /**
     * 是否本地成交 1是  2否
     */
    private String localDeal;
    /**
     * 成交金额
     */
    private BigDecimal auctionDealPrice;
    /**
     * 车辆包名称
     */
    private String packageName;
    /**
     * 1:本地会员成交车辆，2:外地会员成交车辆
     */
    private String localMemberDeal;
    /**
     * 成交过去天数
     */
    private int dealOverDate;
    /**
     * 过户超期天数
     */
    private int trackOverDate;

    /**
     * 列表类型
     */
    private String listType;

    //时间节点
    private String lessTwentyDays;

    private String overTwentyDays;

    private String overFortyDays;

    private String overSixtyDays;
    /**
     * 备注
     */
    private String remark;


}


