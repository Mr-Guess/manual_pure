package com.ay.framework.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 注销操作
 * 
 * @author zxy
 *
 */
public class LogoutAction {
	public String logout() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			currentUser.logout();
		}
		return "login";
	}
}
