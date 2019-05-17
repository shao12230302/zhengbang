package com.zb.byb.service.impl;

import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.SaleNotice;
import com.zb.byb.service.SaleNoticeService;
import com.zb.byb.util.BackTransmitUtil;
import com.zb.byb.util.Image2Base64Util;
import com.zb.byb.util.JsonPluginsUtil;
import com.zb.byb.util.MethodName;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zb.byb.common.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author: shaoys
 * @Mail: 415992946@qq.com
 * @Date: Created in 10:02 2019/5/5
 **/
@Service
public class SaleNoticeServiceImpl implements SaleNoticeService {
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public List<SaleNotice> getSaleRecordList(String id) throws Exception {
        if (C.checkNullOrEmpty(id))
            return new ArrayList<>();

        SaleNotice saleNotice = new SaleNotice();
        saleNotice.setCustId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("custId",id);
        map.put("data",saleNotice);
        map.put("source", Commonconst.WX_Flag);
        map.put("pageNumber","1");
        map.put("pageSize","1000");

        // 要传入数据进行转化
        String data= JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_QUERY_SALE);
        //销售通知base64加密
        List<SaleNotice> saleNotices=JsonPluginsUtil.jsonToBeanList(jsonData, SaleNotice.class);
        for (int i=0;i<saleNotices.size();i++){
            SaleNotice saleNotice1=saleNotices.get(i);
            saleNotice1.setRcordId(Image2Base64Util.getBase64Encoder(saleNotice1.getRcordId()));
        }
        return saleNotices;
    }

    @Override
    public SaleNotice getQuerySale(String id) throws Exception {
        if (C.checkNullOrEmpty(id))
            return new SaleNotice();

        SaleNotice saleNotice = new SaleNotice();
        saleNotice.setRcordId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("data", saleNotice);
        map.put("source", Commonconst.WX_Flag);
        // 要传入数据进行转化
        String data= JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_VIEW_SALE);
        return JsonPluginsUtil.jsonToBean(jsonData, SaleNotice.class);
    }
}
