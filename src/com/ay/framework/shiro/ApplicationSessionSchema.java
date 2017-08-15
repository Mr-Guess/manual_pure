package com.ay.framework.shiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.bean.UserInfo;

/**
 * application session中的内容
 * 
 * @author zxy
 * 
 */
public class ApplicationSessionSchema {
	public static final String APPLIACTION_ONLINE_INFO = "application_online_info";
	/**
	 * 在线信息
	 */
	public static final String SESSION_ONLINE_INFO = "session_online_info";

	/**
	 * Dwr在线用户
	 */
	public static final String DWR_ONLINE_INFO = "dwr_online_info";

	public final static String APPLICATION_MENU = "application_menu";
	public final static String SESSION_USER_PMSTREE = "session_user_pmstree";
	public final static String SESSION_USER_PMSSYSTEMCODES = "session_user_pmssystemcodes";

	public final static String SESSION_USER_PMSDATA = "session_user_pmsdata";
	/**
	 * 在线用户的信息Map
	 */
	public static Map<String, Object> userOnlineMap = new HashMap<String, Object>();

	/**
	 * 在线用户list
	 */
	public static List<UserInfo> userOnlineList = new ArrayList<UserInfo>();
}
