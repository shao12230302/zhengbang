package com.zb.byb.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信通用接口凭证
 * 
 */
public class Ticket {
	// 获取到的凭证
	private String ticket;
	// 凭证有效时间，单位：秒
	@JsonProperty("expires_in")
	private int expiresIn;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
