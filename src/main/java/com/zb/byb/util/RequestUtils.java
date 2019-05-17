package com.zb.byb.util;

import com.zb.byb.common.C;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * request工具类
 */
public class RequestUtils {

    /**
     * 通过request获取参数
     * @param request
     * @param name
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
        if (C.checkNullOrEmpty(name))
            return null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName()))
                    return cookie.getValue();
            }
        }
        return null;
    }
}