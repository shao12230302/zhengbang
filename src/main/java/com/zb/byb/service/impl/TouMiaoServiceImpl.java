package com.zb.byb.service.impl;

import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;

import com.zb.byb.entity.TouMiao;
import com.zb.byb.service.MyInfoService;
import com.zb.byb.service.TouMiaoService;
import com.zb.byb.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 投苗
 * 作者：谢李
 */
@Service
public class TouMiaoServiceImpl implements TouMiaoService {
    @Autowired
    private MyInfoService myInfoService;
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public String queryListByUser(String openId) throws Exception {
        return null;
    }

    @Override
    public String saveInfo(TouMiao info) throws Exception {
        if (info == null || C.checkNullOrEmpty(info.getCustId()) || info.getApplyDate() == null)
        {
            throw new Exception("无法保存");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("custId", info.getCustId());
        map.put("source", Commonconst.WX_Flag);
        map.put("data", info);

        String data = JSONObject.fromObject(map).toString();
        String jsonBackStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SAVE_PIGINGAPPLY);

        return JsonPluginsUtil.isRequestSuccessBackId(jsonBackStr);
    }

    @Override
    public TouMiao queryListInitData(String custId) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("custId", custId);
        map.put("source", Commonconst.WX_Flag);
        TouMiao queryInfo = new TouMiao();
        queryInfo.setCustId(custId);
        map.put("data", queryInfo);

        // 要传入数据进行转化
        String data= JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_VIEW_CUSTINFO);

        JSONObject jsonObject = JSONObject.fromObject(jsonData);
        String obj = jsonObject.getString(JsonPluginsUtil.Data);
        JSONObject custInfoStr = JSONObject.fromObject(obj);
        TouMiao info = new TouMiao();
        info.setCustName(custInfoStr.getString("fname"));
        info.setScope(custInfoStr.getString("cfwinternum"));

        return info;
    }

    @Override
    public TouMiao queryInfoById(String tmid) throws Exception {
        if (C.checkNullOrEmpty(tmid))
            return new TouMiao();

        Map<String, Object> map = new HashMap<>();
        // 微信入口获取数据，统一标识
        map.put("source", Commonconst.WX_Flag);
        TouMiao  queryInfo = new TouMiao();
        queryInfo.setRcordId(tmid);
        map.put("data", queryInfo);

        // 要传入数据进行转化
        String data = JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_VIEW_PIGINGAPPLY);
        return JsonPluginsUtil.jsonToBean(jsonData, TouMiao.class);
    }

    @Override
    public List<TouMiao> queryInfoRecordList(String custId, TouMiao info) throws Exception
    {
        if (C.checkNullOrEmpty(custId))
            return new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("custId", custId);
        map.put("source", Commonconst.WX_Flag);
        map.put("data", info);
        // 要传入数据进行转化
        String data= JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_PIGINGAPPLY);

        List<TouMiao> toumiaoList=JsonPluginsUtil.jsonToBeanList(jsonData, TouMiao.class);
        //对投苗rcordId进行base64加密
        for (int i=0;i<toumiaoList.size();i++){
            TouMiao toumiao=toumiaoList.get(i);
            toumiao.setRcordId(Image2Base64Util.getBase64Encoder(toumiao.getRcordId()));
        }
        return toumiaoList;
    }

    @Override
    public String singerTouMiao(TouMiao touMiao) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("source","WECHAT");//微信
        map.put("data",touMiao);
        String data=JSONObject.fromObject(map).toString();
        String jsonBack=backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SIGNER_PIGINGAPPLY);
        return jsonBack;
    }

    @Override
    public String cancleTouMiao(String rcordId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("rcordId",rcordId);
        map.put("source","WECHAT");//微信
        map.put("data",param);
        String data=JSONObject.fromObject(map).toString();
        String jsonBack=backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_DELETE_PIGINGAPPLY);
        return jsonBack;
    }


}
