package com.ay.jfds.sys.dto;

import java.io.Serializable;

/**
 * 用户权限的DTO 用于登录的时候一次性筛选出来用户具有的权限. User: PS Date: 12-10-17 Time: 上午11:45
 */
@SuppressWarnings("serial")
public class RoleDTO implements Serializable {

	private String id;

	/** 菜单路径 */
	private String menuUrl;

	/** 菜单ID */
	private String menuId;

	/** opt Code */
	private String optCode;
	
	private String sjbh;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getOptCode() {
		return optCode;
	}

	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}

	public String getSjbh() {
		return sjbh;
	}

	public void setSjbh(String sjbh) {
		this.sjbh = sjbh;
	}
	
	
}
