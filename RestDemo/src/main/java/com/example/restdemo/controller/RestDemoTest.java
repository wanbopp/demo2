package com.example.restdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.restdemo.entity.Employee;
import com.sun.net.httpserver.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
public class RestDemoTest {
    @Autowired
    private static RestTemplate restTemplate;

    /**单参数
     * 参数拼到路径上，使用@Valid接收
     * restTemplate.getForObject(url,Boolean.class);
     * @return
     */
    @RequestMapping("/aim1")
    public String test1() {
        String url = "http://localhost:8888/test1?name=";
        String name = "a.exe";
        Boolean res = restTemplate.getForObject(url+name,Boolean.class);
        return JSONObject.toJSONString(res);
    }
    /**单参数  使用{}，自动获取参数列表中的
     * 参数拼到路径上，使用@Valid接收
     * restTemplate.getForObject(url,Boolean.class);
     * @return
     */
    @RequestMapping("/aim2")
    public String test2() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("C", new ArrayList<>());
        String url = "http://localhost:8888/test3";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.queryParams(map).build().encode().toUri();
        String s = JSONObject.toJSONString(map);
        Map map1 = restTemplate.getForObject(url+"?C={C}",Map.class,map);
        System.out.println("map1 = " + map1);
        return JSONObject.toJSONString(map1);
//        new Stack<>()
    }

//    使用POST  传递对象 返回也为对象 注意
    @RequestMapping(value = "/aim3",produces = "application/json;charset=UTF-8")
    public String  test3(){
        Employee employee = new Employee();
        employee.setName("zhangshan");
        employee.setBadgeCode("01234567891");
        employee.setGender("1");
        System.out.println("employee = " + employee);
        String url = "http://localhost:8888/test4";
        return JSONObject.toJSONString( restTemplate.postForObject(url, employee, String.class));

    }

    public static void main(String[] args) {
//        RestTemplate restTemplate1 = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.set("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE2Nzg5Mjg4MzA4ODAsInVpZCI6NTgwMjM2NjkwNjEyMTYxLCJ1c2VyTmFtZSI6Iua1i-ivleS4k-WRmCIsImlhdCI6MTY3ODg0MjQzMDg4MCwib3JnSWQiOjU4MDIzNjM2NjAzMjgzNSwidXNlckNvZGUiOiI4ODAwMDAwMCIsImF1dGhvcml0aWVzIjpbIuaAu-mDqOi0ouWKoeWKqeeQhiIsIuaAu-mDqOS8muWRmOi1hOi0qOWuoeaguOS4juS8muWRmOeuoeeQhuS4juaLjeWNlui_kOiQpSIsIuWIhuWFrOWPuOi_kOiQpeWylyIsIuaJi-e7reWylyIsIuWIhuaAuyIsIuaAu-mDqOi0ouWKoeWvuei0pueuoeeQhuWylyIsIuS8muWRmOeuoeeQhiIsIui2hee6p-euoeeQhuWRmCIsIuWfuuehgOaKpeihqCIsIuaIkOS6pOS_oeaBr-e7tOaKpCIsIuWIhuWFrOWPuOi0ouWKoeafpeivoiIsIuS8muWRmOe7j-eQhiIsIuaAu-WFrOWPuOWPkeaLjeWylyIsIuW6k-euoeWylyIsIuaAu-WFrOWPuOeuoeeQhuWRmCIsIuWIhuWFrOWPuOe7vOWQiOWylyIsIuaAu-mDqOebkeaLjSIsIuacjeWKoeWMheW8gOmAmiIsIuezu-e7n-i_kOe7tCIsIuaKpeS7t-euoeeQhuW4iCIsIuaKpeS7t-W4iCIsIuS8muWRmOS4k-WRmCIsIuaAu-mDqOS4iuaLjeWuoeaguCIsIui_h-aIt-S4k-WRmCIsIuS4muWKoeWylyIsIuaLjeWNluaXpeW_l-WPiueKtuaAgeafpeivoiIsIuernuS7t-aXpeW_l-afpeivoiIsIue7n-iuoeWylyJdLCJqd3RJRCI6ImI0ZjhhYmM4LTQ4N2YtNDczYy1hMTFkLTYyNzlkYWEzNDViMCJ9.H8F-UX1gL5SwobKoQUy0sQ_rGeFY98TwjIsQpvw1e4k");
//        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
//        multiValueMap.add("bbpCode","bbp202212281530002248144");
//        multiValueMap.add("carStatusName","已拍卖");
//        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(multiValueMap,headers);
//        String s = restTemplate1.postForObject("http://127.0.0.1:8081/quoted/send/result", objectHttpEntity, String.class);
//        System.out.println("s = " + s);

        RestTemplate restTemplate1 = new RestTemplate();
        String url = "http://127.0.0.1:8081/quoted/send/result/{bbpCode}/{carStatusName}";
        HashMap<String, String> map = new HashMap<>();
        map.put("bbpCode","bbp202212281530002248144");
        map.put("carStatusName","委托失败");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","token.eyJleHQiOjE2Nzg5Mjg4MzA4ODAsInVpZCI6NTgwMjM2NjkwNjEyMTYxLCJ1c2VyTmFtZSI6Iua1i-ivleS4k-WRmCIsImlhdCI6MTY3ODg0MjQzMDg4MCwib3JnSWQiOjU4MDIzNjM2NjAzMjgzNSwidXNlckNvZGUiOiI4ODAwMDAwMCIsImF1dGhvcml0aWVzIjpbIuaAu-mDqOi0ouWKoeWKqeeQhiIsIuaAu-mDqOS8muWRmOi1hOi0qOWuoeaguOS4juS8muWRmOeuoeeQhuS4juaLjeWNlui_kOiQpSIsIuWIhuWFrOWPuOi_kOiQpeWylyIsIuaJi-e7reWylyIsIuWIhuaAuyIsIuaAu-mDqOi0ouWKoeWvuei0pueuoeeQhuWylyIsIuS8muWRmOeuoeeQhiIsIui2hee6p-euoeeQhuWRmCIsIuWfuuehgOaKpeihqCIsIuaIkOS6pOS_oeaBr-e7tOaKpCIsIuWIhuWFrOWPuOi0ouWKoeafpeivoiIsIuS8muWRmOe7j-eQhiIsIuaAu-WFrOWPuOWPkeaLjeWylyIsIuW6k-euoeWylyIsIuaAu-WFrOWPuOeuoeeQhuWRmCIsIuWIhuWFrOWPuOe7vOWQiOWylyIsIuaAu-mDqOebkeaLjSIsIuacjeWKoeWMheW8gOmAmiIsIuezu-e7n-i_kOe7tCIsIuaKpeS7t-euoeeQhuW4iCIsIuaKpeS7t-W4iCIsIuS8muWRmOS4k-WRmCIsIuaAu-mDqOS4iuaLjeWuoeaguCIsIui_h-aIt-S4k-WRmCIsIuS4muWKoeWylyIsIuaLjeWNluaXpeW_l-WPiueKtuaAgeafpeivoiIsIuernuS7t-aXpeW_l-afpeivoiIsIue7n-iuoeWylyJdLCJqd3RJRCI6ImI0ZjhhYmM4LTQ4N2YtNDczYy1hMTFkLTYyNzlkYWEzNDViMCJ9.H8F-UX1gL5SwobKoQUy0sQ_rGeFY98TwjIsQpvw1e4k");
        // 创建一个请求实体对象，并设置请求头
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> forObject = restTemplate1.exchange(url, HttpMethod.GET,entity,String.class,map);


    }



}
