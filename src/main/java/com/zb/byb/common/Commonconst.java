package com.zb.byb.common;

import java.io.File;

/**
 *  公共使用的常量类型
 * 作者：谢李
 */
public class Commonconst
{
    public static final int LOGIN_FAIL=991;
    public static final int FailStatus = 400;
    // 设置一个固定的openid,测试数据使用
    public static final String OpenId="oIWY8wahhrID4MLw68Ks3zIb1fq0";
    public static final String WX_Flag = "WECHAT";
    public static final String TempPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
            File.separator + "resources" + File.separator + "static"+ File.separator + "static" + File.separator + "template"+ File.separator;
}
