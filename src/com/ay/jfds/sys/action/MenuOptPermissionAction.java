package com.ay.jfds.sys.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.jfds.dev.pojo.Menu;
import com.ay.jfds.dev.pojo.MenuOpt;
import com.ay.jfds.dev.service.MenuService;

/**
 * 对权限进行控制
 * 
 * @author zxy
 *
 */
public class MenuOptPermissionAction {
	private String menuId;
	
	private String menuUrl;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	
	private MenuService getMenuService() {
		return SpringContextHolder.getBean(MenuService.class);
	}
	
	/**
	 * 查询这个用户在这个Menu下的，所有能操作的MenuOpt
	 * 这里应该传入Menu的id
	 */
	public void generateMenuOpt() {
		Subject currentUser = SecurityUtils.getSubject();
		MenuService menuService = this.getMenuService();
		Menu menu = menuService.getById(menuId);
		List<MenuOpt> menuOpts = menuService.getMenuOptByMenuId(menuId);
		List<MenuOpt> hasPermissionMenuOpt = new ArrayList<MenuOpt>();
		// 对其下所有有权限的MenuOperation进行操作
		for (MenuOpt opt : menuOpts) {
			
			// 如果为超级管理员，那么就可以直接得到这个操作按钮的权限
			if (currentUser.isPermitted(menuId + ":" + menu.getMenuUrl() + ":" + opt.getOptCode()) || currentUser.isPermitted("admin")) {
				hasPermissionMenuOpt.add(opt);
			}
		}
		String jsonObj = new JsonMapper().toJson(hasPermissionMenuOpt);
		Struts2Utils.renderText(jsonObj);
	}
}
