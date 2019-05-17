package com.zb.byb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.*;
import com.zb.byb.entity.EquipmentApply;
import com.zb.byb.service.EquipmentApplyService;
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

/**
 * 设备申请
 * 作者：谢李
 */
@Service
public class EquipmentApplyServiceImpl implements EquipmentApplyService {
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public String saveInfo(EquipmentApply info) throws Exception {
        if (info == null) {
            throw new Exception("无法保存");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("custId", info.getCustId());
        map.put("source", Commonconst.WX_Flag);
        map.put("data", info);
        String data = JSONObject.fromObject(map).toString();
        String jsonBackStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SAVE_EQUIPMENTRECBILL);
        return JsonPluginsUtil.isRequestSuccessBackId(jsonBackStr);
    }

    @Override
    public List<EntrustInfo> queryEntrustList(EquipmentApply info) throws Exception {
        if (info == null) {
            throw new Exception("无法查询");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("custId", info.getCustId());
        map.put("source", Commonconst.WX_Flag);
        map.put("data", info);
        String data = JSON.toJSONString(map);
        String jsonBackStr = backTransmitUtil.invokeFunc(data, MethodName.Method_Name_queryEntrust);
        //{"code":"0000","count":1,"data":[{"id":"vKYTT1wJTV+A7XdlVys=","idcard":"测试idcard","isDefault":true,"name":"测试","phone":"测试phone"}],"msg":"查询成功!"}
        return JsonPluginsUtil.jsonToBeanList(jsonBackStr, EntrustInfo.class);
    }

    @Override
    public EquipmentApply queryListInitData(String custId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("custId", custId);

        map.put("source", Commonconst.WX_Flag);
        EquipmentApply info = new EquipmentApply();
        info.setCustId(custId);
        map.put("data", info);

        // 要传入数据进行转化
        String data = JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_COUNT_EQUIPMENTRECBILL);
        //{"code":"0000","data":0,"msg":"查询成功!"}
        JSONObject jsonObject = JSONObject.fromObject(jsonData);
        if (!"0000".equals(jsonObject.getString("code"))) {
            throw new Exception("后台查询出错");
        }
        info.setEquipAmt((Double) jsonObject.getDouble("data"));
        return info;
    }

    @Override
    public List<EquipmentApply> queryInfoRecordList(EquipmentApply info) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("custId", info.getCustId());
        map.put("source", Commonconst.WX_Flag);
        map.put("data", info);

        // 要传入数据进行转化
        String data = JSON.toJSONString(map);
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_EQUIPMENTRECBILL);
        //{"code":"0000","count":1,"data":[{"billStatus":"审核","billStatusIndex":"30","bizDate":"2019-04-28","custId":"Va4AAACPobTMns7U","custName":"陈帮平","entrys":[{"amount":836,"id":"Va4AAAieujjcx7W2","materialId":"Va4AAAGrS/ZECefw","materialName":"臭氧消毒机","model":"臭氧消毒机","price":836,"qty":1,"unit":"台"}],"equipAmt":0,"isEntrust":false,"rcordId":"Va4AAAieujeZvJQc","serviceId":"Va4AAAAbxwL4nGYi","serviceName":"弋阳服务部","state":1}],"msg":"查询成功!"}
        List<EquipmentApply> equipmentApplies = JsonPluginsUtil.jsonToBeanList(jsonData, EquipmentApply.class);
        //设备领用rcordid进行base64加密
        for (int i = 0; i < equipmentApplies.size(); i++) {
            EquipmentApply equipmentApply = equipmentApplies.get(i);
            equipmentApply.setRcordId(Image2Base64Util.getBase64Encoder(equipmentApply.getRcordId()));
        }
        return equipmentApplies;
    }

    @Override
    public EquipmentApply queryInfoById(String rcordId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("source", Commonconst.WX_Flag);
        EquipmentApply info = new EquipmentApply();
        info.setRcordId(rcordId);
        map.put("data", info);

        // 要传入数据进行转化
        String data = JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_VIEW_EQUIPMENTRECBILL);
        // {"code":"0000","data":{"actEntrys":[],"billStatus":"审核","billStatusIndex":"30","bizDate":"2019-04-28","custId":"Va4AAACPobTMns7U","custName":"陈帮平","entrys":[{"amount":836,"id":"Va4AAAieujjcx7W2","materialId":"Va4AAAGrS/ZECefw","materialName":"臭氧消毒机","model":"臭氧消毒机","price":836,"qty":1,"unit":"台"},{"amount":836,"id":"Va4AAAieujjcx7W2","materialId":"Va4AAAGrS/ZECefw","materialName":"臭氧消毒机","price":836,"qty":1}],"equipAmt":0,"isEntrust":false,"rcordId":"Va4AAAieujeZvJQc","serviceId":"Va4AAAAbxwL4nGYi","serviceName":"弋阳服务部","signerImgs":[{"img":"http://10.88.1.10:8080/share.cgi/201904291447053050zengneng_20190429144705726.base64?ssid=03x0LOR&fid=03x0LOR&path=%2F99BC941C%2F20190429&filename=201904291447053050zengneng_20190429144705726.base64&openfolder=normal&ep="}],"state":2},"msg":"查询成功!"}
        //设备查询view方法----{"code":"0000","data":{"actEntrys":[],"billStatus":"保存","billStatusIndex":"10","bizDate":"2019-05-07","custId":"Va4AAAO6drnMns7U","custName":"胡亿龙","entrys":[],"equipAmt":0,"isEntrust":false,"rcordId":"Va4AAAiiofiZvJQc","serviceId":"Va4AAAAZJeX4nGYi","serviceName":"洋河服务部","signerUrl":[{"img":"http://10.88.1.10:8080/share.cgi/201905071859250210zengnengSIGNER_20190507185925489.jpg?ssid=03x0LOR&fid=03x0LOR&path=%2F99BC941C%2F20190506&filename=201905071859250210zengnengSIGNER_20190507185925489.jpg&openfolder=normal&ep="}],"state":2},"msg":"查询成功!"}
        return JsonPluginsUtil.jsonToBean(jsonData, EquipmentApply.class);
    }

    @Override
    public String deleteInfoById(String rcordId) throws Exception {
        Map<String, Object> map = new HashMap<>();
//        map.put("custId", Commonconst.CustId);
        map.put("source", Commonconst.WX_Flag);
        EquipmentApply info = new EquipmentApply();
        info.setRcordId(rcordId);
        map.put("data", info);

        // 要传入数据进行转化
        String data = JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_DELETE_EQUIPMENTRECBILL);
        return jsonData;
    }

    @Override
    public List<Equipment> searchEquipment(String keyword, String custId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("keyword", keyword);
        param.put("custId", custId);
        map.put("source", Commonconst.WX_Flag);
        map.put("data", param);
        // 要传入数据进行转化
        String data = JSON.toJSONString(map);
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_EQUIPMENT);
        return JsonPluginsUtil.jsonTOList(jsonData, Equipment.class);
    }

    @Override
    public String signerEquipApply(EquipmentApply equipmentApply) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("source", Commonconst.WX_Flag);
        map.put("data", equipmentApply);
        // 要传入数据进行转化
        String data = JSON.toJSONString(map);
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SIGNER_EQUIPMENTRECBILL);
        return jsonData;
    }
}
