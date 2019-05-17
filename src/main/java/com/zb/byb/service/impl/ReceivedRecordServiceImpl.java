package com.zb.byb.service.impl;

import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.ReceivedDetail;
import com.zb.byb.entity.ReceivedRecord;
import com.zb.byb.service.ReceivedRecordService;
import com.zb.byb.util.BackTransmitUtil;
import com.zb.byb.util.JsonPluginsUtil;
import com.zb.byb.util.MethodName;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReceivedRecordServiceImpl implements ReceivedRecordService {
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public ReceivedRecord getReceivedList( ReceivedRecord receivedRecord) throws Exception {

        Map<String, Object> map = new HashMap<>();
        //map.put("custId",custId);
        map.put("source", Commonconst.WX_Flag);
        map.put("data", receivedRecord);
        // 要传入数据进行转化
        String data = JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_APPLYRECORD);
        //获取数据明细
        List<ReceivedDetail> details=JsonPluginsUtil.jsonTOList(jsonData, ReceivedDetail.class);
        receivedRecord.setDetails(details);
        return receivedRecord;
    }
}
