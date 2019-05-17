package com.zb.byb.util;

import WSCustWechatAppFacade.WSCustWechatAppFacadeSoapBindingStub;
import WSCustWechatAppFacade.WSCustWechatAppFacadeSrvProxyServiceLocator;
import client.WSWSAppDataDealFacadeSoapBindingStub;
import client.WSWSAppDataDealFacadeSrvProxyServiceLocator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.byb.common.EasSession;
import com.zb.byb.config.EasConfig;
import org.apache.axis.message.SOAPHeaderElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
* @Function: 后台传输数据公共方法
* @Author: shaoys
* @Date: Created in 10:39 2019/5/14
**/
@Component
public class BackTransmitUtil {
    private static final Logger logger = LoggerFactory.getLogger(BackTransmitUtil.class);
    @Autowired
    private EasSession easSession;
    @Autowired
    private EasConfig easConfig;
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 公共调用方法，与后台交互数据
     * @param jsonData json格式数据
     * @param methodName 调用方法
     * @return 返回JSON格式数据
     * @throws Exception 异常
     */
    public String invokeFunc(String jsonData,String methodName) throws Exception
    {
        String sessionId = easSession.getSessionId();
        String result = invokeFuncWeChat(sessionId, jsonData, methodName);
        if ("1001".equals(getReturnCode(result))) {
            logger.info("######retry for session issue########");
            sessionId = easSession.getSessionIdDirect();
            result = invokeFuncWeChat(sessionId, jsonData, methodName);
        }
        return result;
    }


    private String invokeFuncWeChat(String sessionId, String jsonData, String methodName) throws Exception {
        WSCustWechatAppFacadeSrvProxyServiceLocator locator2 = new WSCustWechatAppFacadeSrvProxyServiceLocator();
        URL url2 = new URL(easConfig.getTaskUrl());
        WSCustWechatAppFacadeSoapBindingStub soap2 = new WSCustWechatAppFacadeSoapBindingStub(url2, locator2);
        //设置头部
        soap2.setHeader(new SOAPHeaderElement("http://login.webservice.bos.kingdee.com", "SessionId", sessionId));
        jsonData = jsonData.trim();
        jsonData = "{\"sessionId\":\"" + sessionId + "\"," + jsonData.substring(1);
        logger.info("== EAS method [" + methodName + "] request ===>>>>" + jsonData);
        String result = soap2.bybHandler(methodName, jsonData);
        logger.info("== EAS method [" + methodName + "] result ===<<<<" + result);
        return result;
    }

    /**
     * 接口调用方法
     * @param jsonData
     * @param methodName
     * @return
     * @throws Exception
     */
    public String newInvokeFunc(String jsonData,String methodName) throws Exception {
        String sessionId = easSession.getSessionId();
        String result = invokeFuncBatch(sessionId, jsonData, methodName);
        if ("1001".equals(getReturnCode(result))) {
            logger.info("######retry for session issue########");
            sessionId = easSession.getSessionIdDirect();
            result = invokeFuncBatch(sessionId, jsonData, methodName);
        }
        return result;
    }

    private String invokeFuncBatch(String sessionId, String jsonData, String methodName) throws Exception  {
        WSWSAppDataDealFacadeSrvProxyServiceLocator locator2 = new WSWSAppDataDealFacadeSrvProxyServiceLocator();
        URL url2 = new URL(easConfig.getBatchUrl());
        WSWSAppDataDealFacadeSoapBindingStub soap2 = new WSWSAppDataDealFacadeSoapBindingStub(url2, locator2);
        //设置头部
        soap2.setHeader(new SOAPHeaderElement("http://login.webservice.bos.kingdee.com", "SessionId", sessionId));
        jsonData = jsonData.trim();
        jsonData = "{\"sessionId\":\"" + sessionId + "\"," + jsonData.substring(1);
        logger.info("== EAS method [" + methodName + "] request ===>>>>" + jsonData);
        String result = soap2.getData(methodName, jsonData);
        logger.info("== EAS method [" + methodName + "] result ===<<<<" + result);
        return result;
    }

    private String getReturnCode(String json) {
        try {
            String code = objectMapper.readTree(json).get("code").asText();
            return code;
        }catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

}
