package com.zb.byb.service.impl;

import com.zb.byb.common.C;
import com.zb.byb.entity.*;
import com.zb.byb.service.FeedApplyService;
import com.zb.byb.util.BackTransmitUtil;
import com.zb.byb.util.Image2Base64Util;
import com.zb.byb.util.JsonPluginsUtil;
import com.zb.byb.util.MethodName;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeedApplyServiceImpl implements FeedApplyService {
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public String feedApply(FeedApply feedApply, String userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("custId", userId);//养户id
        map.put("source", "WECHAT");//微信
        map.put("data", feedApply);//参数QUERY_
        String data = JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SAVE_PICKINGAPPLY);
        return JsonPluginsUtil.isRequestSuccessBackId(jsonStr);
        //{"code":"0000","id":"Va4AAAic0ajq+f3A","msg":"生成成功!"}
    }

    @Override
    public List<FeedApply> queryFeedApply(FeedApply feedApply, String userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("custId", userId);//养户id
        map.put("source", "WECHAT");//微信
        map.put("data", feedApply);//参数QUERY_
        String data = JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_PICKINGAPPLY);
//        System.out.println("领料申请,查询query方法---" + jsonStr);

        List<FeedApply> feedApplies = JsonPluginsUtil.jsonTOList(jsonStr, FeedApply.class);
        //对领料rcordId进行base64加密
        for (int i = 0; i < feedApplies.size(); i++) {
            FeedApply feedApply1 = feedApplies.get(i);
            feedApply1.setRcordId(Image2Base64Util.getBase64Encoder(feedApply1.getRcordId()));
        }
        return feedApplies;
    }

    @Override
    public FeedApply viewFeedApply(String rcordId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("rcordId", rcordId);
        //map.put("custId",userId);//养户id
        map.put("source", "WECHAT");//微信
        map.put("data", map1);//参数QUERY_
        String data = JSONObject.fromObject(map).toString();
        String jsonBack = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_VIEW_PICKINGAPPLY);
        return JsonPluginsUtil.jsonToBean(jsonBack, FeedApply.class);
        //{"code":"0000","data":{"actEntrys":[],"applydate":"2019-05-08","batchId":"vZksPXqEQaeWlNnOX/o5V1Kx0pw=","batchName":"胡亿龙002","billStatus":"保存","billStatusIndex":"10","curcnt":"0","curday":"0","custid":"Va4AAAO6drnMns7U","number":"SLSQ1905080006","pickDetail":[{"bclybs":3,"feedId":"Va4AAAAJLFP1CZfS","feedName":"代奶粉","id":"Va4AAAijA6MOXrKS","kg":120,"quotadiff":0,"quotarecevenum":3.00,"sumreceve":0.00}],"pigfarmerCode":"YHDA180717001","plandate":"2019-05-16","rcordId":"Va4AAAijA6Lq+f3A","signerUrl":[],"state":1,"type":0},"msg":"查询成功!"}
    }

    @Override
    public String singer(FeedApply feedApply) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("source", "WECHAT");//微信
        map.put("data", feedApply);
        String data = JSONObject.fromObject(map).toString();
        String jsonBack = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SIGNER_PICKINGAPPLY);
        return jsonBack;
    }

    public String cancleFeedApply(String rcordId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("rcordId", rcordId);
        //map.put("custId",userId);//养户id
        map.put("source", "WECHAT");//微信
        map.put("data", map1);//参数QUERY_
        String data = JSONObject.fromObject(map).toString();
        String jsonBack = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_DELETE_PICKINGAPPLY);
        return jsonBack;
    }

    @Override
    public List<LiLiaoInfo> getFeedList(FeedApply feedApply) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("source", "WECHAT");//微信
        map.put("data", feedApply);
        String data = JSONObject.fromObject(map).toString();
        String jsonBack = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_FEED);
        return toFeedList(jsonBack);
    }

    @Override
    public List<Driver> getDriverList(FeedApply feedApply, String custId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // Map<String, Object> param = new HashMap<>();
        map.put("custId", custId);//养户id
        map.put("source", "WECHAT");//微信
        map.put("data", feedApply);
        String data = JSONObject.fromObject(map).toString();
        String jsonBack = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_DRIVER);
        return JsonPluginsUtil.jsonTOList(jsonBack, Driver.class);
        //{"code":"0000","count":2,"data":[{"id":"Va4AAAidCKPDf2W2","isDefault":false,"name":"张三","number":"432243","phone":"423121"},{"id":"Va4AAAidCKTDf2W2","isDefault":false,"name":"李四","number":"2431412","phone":"1243124"}],"msg":"查询成功!"}

    }

    /**
     * 转饲料列表
     *
     * @param jsonStr json字符串
     * @return
     */
    private List<LiLiaoInfo> toFeedList(String jsonStr){
        // 通过Data 字段获取数据
        //JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        if (C.checkNullOrEmpty(jsonArray))
            return null;
        List<LiLiaoInfo> list = com.alibaba.fastjson.JSONArray.parseArray(jsonArray.toString(), LiLiaoInfo.class);
        return list;
    }

}