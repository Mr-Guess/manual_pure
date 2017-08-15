package com.ay.jfds.sys.dto;

/**
 * PARA DTO
 * 
 * @author PS
 * 
 */
public class ParaDTO {

	/** 登录限制次数 */
	private String login_limit_times;

	/** 登录限制时长 */
	private String login_limit_period;

	/** 登录限制开关 */
	private String login_limit_switch;

	/** 不允许账号多处同时登录开关 */
	private String login_onlyone_switch;

	/** 登录验证码位数 */
	private String login_authcode_digit;

	/** 登录验证码类型 */
	private String login_authcode_type;

	/** 登陆码开关 */
	private String login_authcode_switch;

	public String getLogin_limit_times() {
		return login_limit_times;
	}

	public void setLogin_limit_times(String login_limit_times) {
		this.login_limit_times = login_limit_times;
	}

	public String getLogin_limit_period() {
		return login_limit_period;
	}

	public void setLogin_limit_period(String login_limit_period) {
		this.login_limit_period = login_limit_period;
	}

	public String getLogin_limit_switch() {
		return login_limit_switch;
	}

	public void setLogin_limit_switch(String login_limit_switch) {
		this.login_limit_switch = login_limit_switch;
	}

	public String getLogin_onlyone_switch() {
		return login_onlyone_switch;
	}

	public void setLogin_onlyone_switch(String login_onlyone_switch) {
		this.login_onlyone_switch = login_onlyone_switch;
	}

	public String getLogin_authcode_digit() {
		return login_authcode_digit;
	}

	public void setLogin_authcode_digit(String login_authcode_digit) {
		this.login_authcode_digit = login_authcode_digit;
	}

	public String getLogin_authcode_type() {
		return login_authcode_type;
	}

	public void setLogin_authcode_type(String login_authcode_type) {
		this.login_authcode_type = login_authcode_type;
	}

	public String getLogin_authcode_switch() {
		return login_authcode_switch;
	}

	public void setLogin_authcode_switch(String login_authcode_switch) {
		this.login_authcode_switch = login_authcode_switch;
	}

}
