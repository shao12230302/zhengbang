package com.zb.byb.service.impl;

import com.zb.byb.entity.BindInfo;
import com.zb.byb.entity.Introducer;
import com.zb.byb.entity.ServiceDept;
import com.zb.byb.entity.UserInfo;
import com.zb.byb.service.LoginService;
import com.zb.byb.util.BackTransmitUtil;
import com.zb.byb.util.JsonPluginsUtil;
import com.zb.byb.util.MethodName;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    /**
     * 绑定用户
     *
     * @param userInfo 用户信息
     * @param openId   唯一id
     * @return
     * @throws Exception
     */
    @Override
    public boolean bind(BindInfo userInfo, String openId) throws Exception {

        if (openId == null || openId.length() == 0) {
            throw new Exception("未获取到openId");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", userInfo);
        map.put("openId", openId);
        String data = JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_BIND_CUSTINFO);
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        String status = jsonObject.getString("code");
        if(!"0000".equals(status)){
            throw new Exception(jsonObject.getString(jsonObject.getString("msg")));
        }
        return true;
    }

    @Override
    public String getCheckCode(String mobile) {
        String token = getToken();
        if (token == null || token.length() == 0 || mobile == null || mobile.length() == 0) {
            return "no";
        }
        String url = "http://service.zhengbang.com/SERVICE/message/send-code/" + mobile;
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication", token);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        try {
            ResponseEntity<String> r = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
            //<200,{"status":401,"message":[{"code":"401","message":"用户今天获取验证码超过5次！"}],"data":null,"date":1556191674470},{Server=[nginx/1.9.9], Date=[Thu, 25 Apr 2019 11:27:54 GMT], Content-Type=[application/json;charset=UTF-8], Transfer-Encoding=[chunked], Connection=[keep-alive], Vary=[Accept-Encoding]}>
            //<200,{"status":200,"message":null,"data":"4247","date":1556192780776},{Server=[nginx/1.9.9], Date=[Thu, 25 Apr 2019 11:46:21 GMT], Content-Type=[application/json;charset=UTF-8], Transfer-Encoding=[chunked], Connection=[keep-alive], Vary=[Accept-Encoding]}>
            String body = r.getBody();
            if (200 != JSONObject.fromObject(body).getInt("status")) {
                throw new Exception("获取验证码失败：" + JSONObject.fromObject(body).getString("message"));
            }
            return "ok";
        } catch (Exception e1) {
            return "noo";
        }
    }

    @Override
    public boolean check(String phone, String code) {
        String token = getToken();
        if (token == null || token.length() == 0) {
            return false;
        }
        StringBuffer url = new StringBuffer("http://service.zhengbang.com/SERVICE/message/validate-code?phone=" + phone + "&code=" + code);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication", token);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> r = restTemplate.exchange(url.toString(), HttpMethod.POST, requestEntity, String.class);
        String jsonback = r.getBody();
        int code1 = JSONObject.fromObject(jsonback).getInt("status");
        if (200 == code1) {
            return true;
        }
        return false;
    }

    private String getToken() {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder("http://service.zhengbang.com/AUTH/login?userName=YHFWH_ADMIN&password=123456");
        ResponseEntity<String> r = restTemplate.exchange(url.toString(), HttpMethod.POST, null, String.class);
        String jsonback = r.getBody();
        String token = JSONObject.fromObject(jsonback).getJSONObject("data").getString("Authentication");
        return token;
    }


    /**
     * 解绑
     *
     * @param openId 唯一id
     * @return
     * @throws Exception
     */
    @Override
    public String unBind(String openId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("openId", openId);
        String data = JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_UNBIND_CUSTINFO);
        return jsonStr;
    }

    /**
     * 登入
     *
     * @param userInfo 用户信息
     * @param openId
     * @return
     * @throws Exception
     */
    @Override
    public String login(UserInfo userInfo, String openId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("source", "WECHAT");//微信
        map.put("data", userInfo);
        map.put("openId", openId);
        String data = JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_BIND_CUSTINFO);
        return jsonStr;
    }


    @Override
    public String register(UserInfo userInfo, String openId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("source", "WECHAT");//微信
        map.put("openId", openId);//微信id
        map.put("data", userInfo);//开户信息
        String data = JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SAVE_CUSTSTART);
        return jsonStr;
    }

    //
    @Override
    public List<Introducer> getIntroducer(Introducer introducer) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("source", "WECHAT");//微信
        map.put("data", introducer);//
        String data = JSONObject.fromObject(map).toString();
        String jsonBack = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_INTRODUCER_CUSTSTART);
        if (!"0000".equals(JSONObject.fromObject(jsonBack).getString("code"))) {
            return null;
        }
        JSONArray jsonObject = JSONObject.fromObject(jsonBack).getJSONArray("data");
        // {"code":"0000","data":[{"billState":"保存","billStateIndex":"10","feedDate":"2019-04-19","feedList":[{"batchId":"QOKuwU+4Q5uVQ5msWQNUVEMbbjA=","batchName":"001","columnQty":0,"consumeQty":112,"feedId":"Va4AAAAJLFb1CZfS","feedName":"保育料","id":"Va4AAAib16PHfMLr"}],"rcordId":"Va4AAAib16Kzx1nH","state":1}],"msg":"查询成功!"}
        //List<FeedRecord> list = JSONArray.toList(jsonObject, FeedRecord.class);
        List<Introducer> list = com.alibaba.fastjson.JSONArray.parseArray(jsonObject.toString(), Introducer.class);
        return list;
    }

    @Override
    public List<ServiceDept> getServiceDept(ServiceDept serviceDept) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("source", "WECHAT");//微信
        map.put("data", serviceDept);//
        String data = JSONObject.fromObject(map).toString();
        String jsonBack = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_SERVICE);
        return JsonPluginsUtil.jsonTOList(jsonBack, ServiceDept.class);
    }
}
