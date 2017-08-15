package com.ay.framework.shiro;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.jfds.sys.service.SysParameterService;

/** 
 * @Description 
 * @date 2012-11-9
 * @author WangXin
 */
@Component
public class SystemParameter {
	private static Logger logger = LoggerFactory.getLogger(SystemParameter.class);
	/**
	 * 登录限制次数
	 */
	private static String login_limit_times;
	/**
	 * 登录限制时长
	 */
	private static String login_limit_period;
	/**
	 * 登录限制开关
	 */
	private static String login_limit_switch;
	/**
	 * 不允许账号多处同时登录开关
	 */
	private static String login_onlyone_switch;
	/**
	 * 登录验证码位数
	 */
	private static String login_authcode_digit;
	/**
	 * 登录验证码类型
	 */
	private static String login_authcode_type;
	/**
	 * 登录验证码开关
	 */
	private static String login_authcode_switch;
	
	private static SysParameterService sysParameterService;
	/**
	 * 登录限制次数
	 */
	public static String getLogin_limit_times() {
		return login_limit_times;
	}
	/**
	 * 登录限制时长
	 */
	public static String getLogin_limit_period() {
		return login_limit_period;
	}
	/**
	 * 登录限制开关
	 */
	public static String getLogin_limit_switch() {
		return login_limit_switch;
	}
	/**
	 * 不允许账号多处同时登录开关
	 */
	public static String getLogin_onlyone_switch() {
		return login_onlyone_switch;
	}
	/**
	 * 登录验证码位数
	 */
	public static String getLogin_authcode_digit() {
		return login_authcode_digit;
	}
	/**
	 * 登录验证码类型
	 */
	public static String getLogin_authcode_type() {
		return login_authcode_type;
	}
	/**
	 * 登录验证码开关
	 */
	public static String getLogin_authcode_switch() {
		return login_authcode_switch;
	}

	public static SysParameterService getSysParameterService() {
		return sysParameterService;
	}
	@Resource
	public void setSysParameterService(SysParameterService sysParameterService) {
		SystemParameter.sysParameterService = sysParameterService;
	}
	
	/**
	 * 初始化调用 和 修改参数后调用 
	 * @return void
	 */
	@PostConstruct
	public static void clearValue(){
		synchronized (SystemParameter.class) {
			/*
			login_authcode_digit=null;
			login_authcode_switch=null;
			login_authcode_type=null;
			login_limit_period=null;
			login_limit_switch=null;
			login_limit_times=null;
			login_onlyone_switch=null;
			*/
			logger.info("已加载系统参数");
			login_limit_times=sysParameterService.findValueByCode("login_limit_times").getParaValue();
			login_limit_period=sysParameterService.findValueByCode("login_limit_period").getParaValue();
			login_limit_switch=sysParameterService.findValueByCode("login_limit_switch").getParaValue();
			login_onlyone_switch=sysParameterService.findValueByCode("login_onlyone_switch").getParaValue();
			login_authcode_digit=sysParameterService.findValueByCode("login_authcode_digit").getParaValue();
			login_authcode_type=sysParameterService.findValueByCode("login_authcode_type").getParaValue();
			login_authcode_switch=sysParameterService.findValueByCode("login_authcode_switch").getParaValue();
		}
	}
	
}

