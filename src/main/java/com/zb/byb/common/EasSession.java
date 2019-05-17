package com.zb.byb.common;

import EASLogin.EASLoginProxyServiceLocator;
import EASLogin.EASLoginSoapBindingStub;
import client.WSContext;
import com.zb.byb.config.EasConfig;
import com.zb.byb.util.Resource;
import org.apache.axis.message.SOAPHeaderElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class EasSession {
    private static final Logger logger = LoggerFactory.getLogger(EasSession.class);
    @Autowired
    private EasConfig easConfig;
    private String sessionId;
    private long lastAccessTime = 0;

    public synchronized String getSessionId() throws Exception{
        if (sessionId == null) {
            sessionId = getSessionIdFromEas();
            lastAccessTime = System.currentTimeMillis();
        } else if ((System.currentTimeMillis() - lastAccessTime) > 10 * 60 * 1000) {
            sessionId = getSessionIdFromEas();
            lastAccessTime = System.currentTimeMillis();
        }
        return sessionId;
    }

    public synchronized String getSessionIdDirect() throws Exception{
        sessionId = getSessionIdFromEas();
        lastAccessTime = System.currentTimeMillis();
        return sessionId;
    }

    private String getSessionIdFromEas() throws Exception{
        EASLoginProxyServiceLocator locator = new EASLoginProxyServiceLocator();
        URL url = new URL(easConfig.getLoginUrl());
        EASLoginSoapBindingStub soap = new EASLoginSoapBindingStub(url, locator);
        //设置头部
        soap.setHeader(new SOAPHeaderElement("http://login.webservice.bos.kingdee.com", "SessionId", ""));
        //获取sessionId
        logger.info("userName:" + easConfig.getUserName());
        logger.info("password:" + easConfig.getPassword());
        logger.info("password:" + Resource.SLN_NAME);
        logger.info("dcName:" + easConfig.getDcName());
        logger.info("LANGUAGE:" + Resource.LANGUAGE);
        logger.info("DB_TYPE:" + Resource.DB_TYPE);
        WSContext ctx = soap.login(easConfig.getUserName(), easConfig.getPassword(), Resource.SLN_NAME, easConfig.getDcName(), Resource.LANGUAGE, Resource.DB_TYPE);
        String sessionId = ctx.getSessionId();
        logger.info("Get new sessionId from EAS: " + sessionId);
        return sessionId;
    }
}
