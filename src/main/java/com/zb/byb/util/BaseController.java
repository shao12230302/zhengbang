package com.zb.byb.util;

import com.zb.byb.common.C;
import com.zb.byb.common.Constants;
import com.zb.byb.service.MyInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：谢李
 */
public class BaseController {
    @Autowired
    private MyInfoService myInfoService;

    /**
     * 获取当前微信登录的openid
     * @param request
     * @return
     */
    public String getCurrentOpenId(HttpServletRequest request)
    {
        return  RequestUtils.getCookieByName(request, Constants.OPEN_ID);
    }

    public Map<String, Object> getUserInfo(String openId)
    {
        if (C.checkNullOrEmpty(openId))
            return new HashMap<String, Object>();

        try{
            String backJsonData = myInfoService.viewMyInfo(openId);
            return JsonPluginsUtil.jsonToMap(backJsonData);

        }
        catch (Exception e)
        {

        }

        return new HashMap<String, Object>();
    }


}
