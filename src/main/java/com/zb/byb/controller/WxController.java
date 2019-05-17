package com.zb.byb.controller;

import com.zb.byb.common.*;
import com.zb.byb.util.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * 微信相关
 */
@RestController
@RequestMapping("/api")
public class WxController {
    private static final Logger logger = LoggerFactory.getLogger(WxController.class);
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.token}")
    private String wxToken;

    @GetMapping("/GetWXConfig")
    public Map<String,String> getWXConfig(@RequestParam("url")String url) {
        AccessToken accessToken = WxCache.getInstance().getAccessToken();
        Ticket ticket = WxCache.getInstance().getTicket();

            if (ticket != null && accessToken != null) {
                Map<String, String> map = SignUtils.makeWXTicket(appId, ticket.getTicket(), url);
                map.put("acesstoken", accessToken.getToken());
                map.put("ticket", ticket.getTicket());
                return map;
            }else {
                return null;
            }
    }

    @PostMapping("/WxService")
    public void handlePost() {

    }

    @GetMapping("/WxService")
    public String validate(@RequestParam("signature") String signature, @RequestParam("timestamp")String timestamp, @RequestParam("nonce")String nonce, @RequestParam("echostr")String echostr) {
        logger.info("-----开始校验签名-----");
        logger.info("-----signature-----" + signature);
        logger.info("-----timestamp-----" + timestamp);
        logger.info("-----nonce-----" + nonce);
        logger.info("-----echostr-----" + echostr);
        /**
         * 将token、timestamp、nonce三个参数进行字典序排序 并拼接为一个字符串
         */
        String sortStr = sort(wxToken, timestamp, nonce);
        /**
         * 字符串进行shal加密
         */
        String mySignature = shal(sortStr);
        /**
         * 校验微信服务器传递过来的签名 和 加密后的字符串是否一致, 若一致则签名通过
         */
        if (!"".equals(signature) && !"".equals(mySignature) && signature.equals(mySignature)) {
            logger.info("-----签名校验通过-----");
            return echostr;
        } else {
            logger.info("-----校验签名失败-----");
            return "";
        }
    }

    /**
     * 参数排序
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    private String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 字符串进行shal加密
     *
     * @param str
     * @return
     */
    private String shal(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
