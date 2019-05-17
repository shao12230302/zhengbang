package com.zb.byb.service.impl;

import com.zb.byb.mapper.WxAppTokenMapper;
import com.zb.byb.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private WxAppTokenMapper tokenMapper;

    @Override
    public void updateWxAccessToken(String appId, String token, String appSecret) {
        String fid = tokenMapper.getAppConfig(appId);
        if (StringUtils.isEmpty(fid)) {
            tokenMapper.addWxAccessToken(appId, token, appSecret);
        } else {
            tokenMapper.updateWxAccessToken(appId, token);
        }
    }
}
