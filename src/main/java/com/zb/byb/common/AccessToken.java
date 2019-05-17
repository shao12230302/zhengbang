package com.zb.byb.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信通用接口凭证
 * 
 */
public class AccessToken {
	// 获取到的凭证
	@JsonProperty("access_token")
	private String token;
	// 凭证有效时间，单位：秒
	@JsonProperty("expires_in")
	private int expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
