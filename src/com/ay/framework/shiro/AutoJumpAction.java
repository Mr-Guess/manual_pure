package com.ay.framework.shiro;

import com.ay.framework.core.action.BaseAction;

@SuppressWarnings("all")
public class AutoJumpAction extends BaseAction {
	
	public String execute() throws Exception {
		// 获得当前菜单ID
		String menuId = request.getParameter("menuId");
		
		// 获得当前菜单对应路径
		String url = request.getParameter("url");
		
		// 储存菜单ID作为对应页面权限控制
		request.setAttribute("menuId", menuId);
		
		// 跳转页面
		request.getRequestDispatcher(url).forward(request, response);
		return null;
	}
}
