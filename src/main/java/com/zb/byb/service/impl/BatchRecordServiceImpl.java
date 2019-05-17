package com.zb.byb.service.impl;

import com.zb.byb.entity.Batch;
import com.zb.byb.entity.BatchRecord;
import com.zb.byb.service.BatchRecordService;
import com.zb.byb.util.BackTransmitUtil;
import com.zb.byb.util.Image2Base64Util;
import com.zb.byb.util.JsonPluginsUtil;
import com.zb.byb.util.MethodName;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class BatchRecordServiceImpl implements BatchRecordService {
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public BatchRecord viewBatchRecord(String batchId, String userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("rcordId",batchId);
        map.put("custId",userId);//养户id
        map.put("source","WECHAT");//微信
        map.put("data",params);//参数QUERY_
        String data=JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_VIEW_BATCHRECORD);
        return JsonPluginsUtil.jsonToBean(jsonStr,BatchRecord.class);
    }

    @Override
    public List<Batch> getBatchList(String userId, Batch batch) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        //param.put("batchId",batchId);
        map.put("custId",userId);//养户id
        map.put("source","WECHAT");//微信
        map.put("data",batch);//参数
//        map.put("pageNumber", 1);
//        map.put("pageSize", 100);

        String data= JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data,MethodName.METHOD_NAME_QUERY_ALL_BATCHI);
        //加密
        List<Batch> list=JsonPluginsUtil.jsonTOList(jsonStr,Batch.class);
        for (int i = 0; i < list.size(); i++) {
            Batch batch1 = list.get(i);
            batch1.setId(Image2Base64Util.getBase64Encoder(batch1.getId()));
        }
        return list;
    }

}
