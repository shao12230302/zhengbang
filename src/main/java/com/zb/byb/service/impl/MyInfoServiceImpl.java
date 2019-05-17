package com.zb.byb.service.impl;

import com.zb.byb.service.MyInfoService;
import com.zb.byb.util.BackTransmitUtil;
import com.zb.byb.util.MethodName;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class MyInfoServiceImpl implements MyInfoService {
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public String viewMyInfo(String openId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map.put("openId",openId);
        map.put("data",map1);
        String data= JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_VIEW_CUSTINFO);

        if(!"0000".equals(JSONObject.fromObject(jsonStr).get("code"))){
            throw new Exception("查看个人信息失败："+JSONObject.fromObject(jsonStr).get("msg"));
        }
        return jsonStr;
        //{"code":"0000","data":{"cflevel":"A","cfpigpen":"吉林省松原市宁江区那噶岱村东三合屯","cfraisestate":"2","cfraisestateText":"销户","cfwinternum":"718","fcell":"15164435858","fkhsj":"2017-04-05","fname":"吕利","ftelno":"15543876557","id":"Va4AAAA+/JHMns7U","manager":"左立园","servicedep":"宁江服务部"},"msg":"查询成功!"}
    }
}
