package com.zb.byb.com.zb.byb;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class TestMain {

	public static void main(String[] args) throws Exception {
		/*RestTemplate restTemplate = (RestTemplate) SpringContextUtil.getBean(org.springframework.web.client.RestTemplate.class);
		StringBuilder url = new StringBuilder("http://AUTH/login?userName=用户名&password=密码");
		Map<String,String> uriVariables = new HashMap<String,String>();

		ResponseEntity<Map> r =  restTemplate.postForObject(url.toString(), null, ResponseEntity.class, uriVariables);
		if(r.getStatus()==200){
			token = (String) r.getData().get(Global.AUTHORIZE_TOKEN);
			redisUtil.set(key, token, Global.TOKEN_EXPIRE);
		}else{
			logger.error("获取管理员token失败");
		}*/
	}

}
