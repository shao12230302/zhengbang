package com.zb.byb.service.impl;

import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.FeedRecord;

import com.zb.byb.entity.Pigwash;
import com.zb.byb.service.FeedRecordService;
import com.zb.byb.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class FeedRecordServiceImpl implements FeedRecordService {
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public String addFeedRecord(FeedRecord feedRecord, String userId) throws Exception{

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        //map.put("openId", Commonconst.OpenId);
        map.put("custId", userId);
        map.put("source", Commonconst.WX_Flag);
        map.put("data",feedRecord);
        String data= JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SAVE_SUPPLIESBILL);
        return JsonPluginsUtil.isRequestSuccessBackId(jsonStr);
    }


    @Override
    public List<FeedRecord> queryFeedRecordList(String userId,FeedRecord feedRecord) throws Exception{
        if (C.checkNullOrEmpty(userId))
            return new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("custId",userId);//养户id
        map.put("source","WECHAT");//微信
        map.put("data",feedRecord);//参数QUERY_
        String data=JSONObject.fromObject(map).toString();
        String jsonBack=backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_SUPPLIESBILL);
        if(!"0000".equals(JSONObject.fromObject(jsonBack).getString("code"))){
            return null;
        }
        JSONArray jsonObject=JSONObject.fromObject(jsonBack).getJSONArray("data");
       // {"code":"0000","data":[{"billState":"保存","billStateIndex":"10","feedDate":"2019-04-19","feedList":[{"batchId":"QOKuwU+4Q5uVQ5msWQNUVEMbbjA=","batchName":"001","columnQty":0,"consumeQty":112,"feedId":"Va4AAAAJLFb1CZfS","feedName":"保育料","id":"Va4AAAib16PHfMLr"}],"rcordId":"Va4AAAib16Kzx1nH","state":1}],"msg":"查询成功!"}
        //List<FeedRecord> list = JSONArray.toList(jsonObject, FeedRecord.class);
        List<FeedRecord> list= com.alibaba.fastjson.JSONArray.parseArray(jsonObject.toString(),FeedRecord.class);

        //对饲喂记录rcordId进行base64加密
        for (int i=0;i<list.size();i++){
            FeedRecord feedRecord1=list.get(i);
            feedRecord1.setRcordId(Image2Base64Util.getBase64Encoder(feedRecord1.getRcordId()));
        }
        return list;
    }

    @Override
    public FeedRecord queryFeedRecordbyRcordId(String rcordId) throws Exception {
        if (C.checkNullOrEmpty(rcordId))
            return new FeedRecord();
        Map<String, Object> map = new HashMap<>();
        map.put("source", Commonconst.WX_Flag);
        FeedRecord  feedRecord = new FeedRecord();
        feedRecord.setRcordId(rcordId);
        map.put("data", feedRecord);
        String data=JSONObject.fromObject(map).toString();
        String jsonBack=backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_VIEW_SUPPLIESBILL);
        //{"code":"0000","data":[{"billState":"保存","billStateIndex":"10","feedDate":"2019-04-19","feedList":[{"batchId":"QOKuwU+4Q5uVQ5msWQNUVEMbbjA=","batchName":"001","columnQty":0,"consumeQty":112,"feedId":"Va4AAAAJLFb1CZfS","feedName":"保育料","id":"Va4AAAib16PHfMLr"}],"rcordId":"Va4AAAib16Kzx1nH","state":1}],"msg":"查询成功!"}
        if(!"0000".equals(JSONObject.fromObject(jsonBack).getString("code"))){
            return null;
        }
        JSONObject jsonObject=JSONObject.fromObject(jsonBack).getJSONObject("data");

        /*FeedRecord feedRecord1 = (FeedRecord)JSONObject.toBean(jsonObject, FeedRecord.class);*/

        /*赋值*/

        FeedRecord feedRecord1=new FeedRecord();
        feedRecord1.setBillStatus(jsonObject.getString("billStatus"));
        feedRecord1.setBillStatusIndex(jsonObject.getString("billStatusIndex"));
        feedRecord1.setFeedDate(jsonObject.getString("feedDate"));
        feedRecord1.setRcordId(jsonObject.getString("rcordId"));
        Object o =JSONObject.fromObject(jsonObject).get("feedList");
        List<Pigwash> list = (List<Pigwash>) o;
        feedRecord1.setFeedList(list);
        return feedRecord1;
    }

    //{"code":"0000","data":{"billState":"save","billStateIndex":"10","feedDate":"2019-04-16","feedList":[{"consumeQty":30,"feedId":"Va4AAAiadCHHfMLr","id":"Va4AAAibjXHHfMLr"}],"rcordId":"Va4AAAibjXCzx1nH","state":1,"thePack":0},"msg":"查询成功!"}
    @Override
    public List<Pigwash> pigwashList(String batchId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject data=new JSONObject();
        data.put("batchid",batchId);
        map.put("data",data);
        String dataStr=JSONObject.fromObject(map).toString();
//        System.out.println(dataStr);
        String jsonBack= backTransmitUtil.newInvokeFunc(dataStr, "selectFeedVarieties");
        if(!"0000".equals(JSONObject.fromObject(jsonBack).getString("code"))){
            return null;
        }
        JSONArray pigwashList=JSONObject.fromObject(jsonBack).getJSONObject("feeding").getJSONArray("data");//feeding->data数组
        return JSONArray.toList(pigwashList,Pigwash.class);
    }

    @Override
    public String cancleFeedRecord(String rcordId) throws Exception {
        if (C.checkNullOrEmpty(rcordId))
            return null;
        Map<String, Object> map = new HashMap<>();
        map.put("source", Commonconst.WX_Flag);
        FeedRecord  feedRecord = new FeedRecord();
        feedRecord.setRcordId(rcordId);
        map.put("data", feedRecord);
        String data=JSONObject.fromObject(map).toString();
        String jsonBack=backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_DELETE_SUPPLIESBILL);
        return  jsonBack;
    }

}
