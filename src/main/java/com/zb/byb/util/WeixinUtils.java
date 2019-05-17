package com.zb.byb.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.byb.common.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.*;

import static com.zb.byb.util.Image2Base64Util.getBase64FromInputStream;

/**
 * 公众平台通用接口工具类
 * 
 * @author gongxunqiang
 */
public class WeixinUtils {

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 获取jsapi_ticket的接口
	public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	// 获取phone账号接口
	public final static String phone_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";

	//获取微信个人信息接口
	public final static String userid_url = "https://api.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return EquipmentApplyObject(通过EquipmentApplyObject.get(key)的方式获取EquipmentApply对象的属性值)
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		String result = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			result = buffer.toString();
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		String result = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != result) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				accessToken = objectMapper.readValue(result, AccessToken.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return accessToken;
	}



	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static String createMenu(Menu menu, String accessToken) throws IOException{

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonMenu = objectMapper.writeValueAsString(menu);

		return httpRequest(url, "POST", jsonMenu);
	}

	/**
	 * 获取jsapi_ticket
	 * 
	 * @return
	 */
	public static Ticket getJsApiTicket(String accessToken) {
		Ticket ticket = null;
		String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken);
		String json = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != json) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				ticket = objectMapper.readValue(json, Ticket.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ticket;
	}

	/**
	 * 获取phone
	 * 
	 * @param token
	 *            凭证
	 * @param userId
	 *            密钥
	 * @return
	 */
	public static String getPhone(String token, String userId) {
		String phone = "";

		String requestUrl = phone_url.replace("ACCESS_TOKEN", token).replace("USERID", userId);
		String json = httpRequest(requestUrl, "GET", null);

		// 如果请求成功
		if (null != json) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode = objectMapper.readTree(json);
				phone = jsonNode.get("mobile").asText();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return phone;
	}

	/**
	 * 企业号获取userid
	 * 
	 * @param token
	 *            凭证
	 * @param code
	 *            密钥
	 * @return
	 */
	public static String getUserId(String token, String code) {
		String userId = "";
		String requestUrl = userid_url.replace("ACCESS_TOKEN", token).replace("CODE", code);
		String json = httpRequest(requestUrl, "GET", null);

		// 如果请求成功
		if (null != json) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode = objectMapper.readTree(json);
				userId = jsonNode.get("UserId").asText();
			}catch (Exception e) {
				userId = "";
				e.printStackTrace();
			}
		}
		return userId;
	}
}