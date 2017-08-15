package com.ay.framework.shiro;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ay.framework.bean.UserInfo;

/**
 * 用户的监听
 * 
 * @author zxy
 * 
 */
public class UserHttpSessionListener implements HttpSessionListener,
		HttpSessionAttributeListener {

	private static Log log = LogFactory.getLog(UserHttpSessionListener.class);

	/**
	 * session创建时什么都不做
	 */
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		// System.out.println("------sessionCreated---------------");
		log.info(" sessionCreated  sessionId = "
				+ httpSessionEvent.getSession().getId());
	}

	/**
	 * session失效时
	 */
	@SuppressWarnings("rawtypes")
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		// 用户退出，删除在线用户列表中保存的用户session
		if (null != session
				.getAttribute(ApplicationSessionSchema.SESSION_ONLINE_INFO)) {
			ApplicationSessionSchema.userOnlineMap.remove(session.getId());
			checkUserOnline(session, false);
			// System.out.println("------sessionDestroyed------ONLINE_USER---------");
		}

		log.info("被销毁的HttpSession的id为：" + session.getId() + ",创建时间为："
				+ new java.sql.Timestamp(session.getCreationTime())
				+ ",默认超时间隔为：" + session.getMaxInactiveInterval()
				+ "session,最后一次访问服务器的时间为："
				+ new java.sql.Timestamp(session.getLastAccessedTime())
				+ "，总在线时间为："
				+ (session.getLastAccessedTime() - session.getCreationTime())
				+ "ms,总存活时间为："
				+ (System.currentTimeMillis() - session.getCreationTime())
				+ "ms, 超时 : "
				+ (System.currentTimeMillis() - session.getLastAccessedTime())
				+ "毫秒");
		Enumeration names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			log.info("in session : " + name + " == "
					+ session.getAttribute(name));
		}
	}

	/**
	 * 调用session.setAttribute(ApplicationSessionSchema.USER_INFO,"****")时，
	 * 添加用户信息到列表中
	 */
	public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
		String name = httpSessionBindingEvent.getName();

		HttpSession session = httpSessionBindingEvent.getSession();
		if (ApplicationSessionSchema.SESSION_ONLINE_INFO.equalsIgnoreCase(name)) {
			UserInfo userInfo = (UserInfo) session
					.getAttribute(ApplicationSessionSchema.SESSION_ONLINE_INFO);
			ApplicationSessionSchema.userOnlineMap.put(session.getId(),
					userInfo);
			log.info(" ---用户  userName : " + userInfo.getUserName()
					+ ", sessionId :" + userInfo.getSessionId() + " 登陆");
			checkUserOnline(session, true);

			// System.out.println("------attributeAdded------ONLINE_USER---------");
		}
	}

	/**
	 * 调用session.removeAttribute(ApplicationSessionSchema.USER_INFO,"****")时，
	 * 删除列表中用户信息
	 */
	public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
		String name = httpSessionBindingEvent.getName();
		HttpSession session = httpSessionBindingEvent.getSession();
		if (ApplicationSessionSchema.SESSION_ONLINE_INFO.equalsIgnoreCase(name)) {
			ApplicationSessionSchema.userOnlineMap.remove(session.getId());

			checkUserOnline(session, false);

			// System.out.println("------attributeRemoved------ONLINE_USER---------");
		}
	}

	/**
	 * 调用更改ApplicationSessionSchema.USER_INFO属性值时同时更改列表中用户信息
	 */
	public void attributeReplaced(
			HttpSessionBindingEvent httpSessionBindingEvent) {
		String name = httpSessionBindingEvent.getName();
		HttpSession session = httpSessionBindingEvent.getSession();
		if (ApplicationSessionSchema.SESSION_ONLINE_INFO.equalsIgnoreCase(name)) {
			Object obj = (Object) session
					.getAttribute(ApplicationSessionSchema.SESSION_ONLINE_INFO);
			ApplicationSessionSchema.userOnlineMap.put(session.getId(), obj);

			// System.out.println("------attributeReplaced------ONLINE_USER---------");
		}
	}

	/**
	 * 用户是否在线，对Application中的操作
	 * 
	 * @param session
	 * @param isOnline
	 */
	@SuppressWarnings("unchecked")
	public void checkUserOnline(HttpSession session, boolean isOnline) {
		List<UserInfo> userOnlineList = (List<UserInfo>) session
				.getServletContext().getAttribute(
						ApplicationSessionSchema.APPLIACTION_ONLINE_INFO);
		// 这里可以对ScriptSession进行操作，以便用户在登录过之后进行通知操作
		/*
		Map<String, Map<String, ScriptSession>> map = (Map<String, Map<String, ScriptSession>>) session
				.getServletContext().getAttribute(
						ApplicationSessionSchema.DWR_ONLINE_INFO);
		*/
		if (userOnlineList == null) {
			userOnlineList = ApplicationSessionSchema.userOnlineList;
		}
		if (isOnline) {
			UserInfo userInfo = (UserInfo) session
					.getAttribute(ApplicationSessionSchema.SESSION_ONLINE_INFO);
			userOnlineList.add(userInfo);

		} else {
			for (Iterator<UserInfo> iterator = userOnlineList.iterator(); iterator
					.hasNext();) {
				UserInfo userInfo = (UserInfo) iterator.next();
				if (userInfo.getSessionId().equals(session.getId())) {
					log.info(" ---用户  userName : " + userInfo.getUserName()
							+ ", sessionId :" + userInfo.getSessionId() + " 退出");
					iterator.remove();
//					map.remove(userInfo.getUserId());
					break;
				}
			}

		}
		// 在Application中添加用户信息
		session.getServletContext().setAttribute(
				ApplicationSessionSchema.APPLIACTION_ONLINE_INFO,
				userOnlineList);

	}
}
