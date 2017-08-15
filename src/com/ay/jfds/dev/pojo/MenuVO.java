package com.ay.jfds.dev.pojo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description
 * @date 2012-12-3
 * @author WangXin
 */
public class MenuVO {
	/** 菜单编号 */
	private String menuId;
	/** 菜单名称 */
	private String menuName;
	/** 菜单链接地址 */
	private String menuUrl;
	/** 菜单对应模块表名*/
	private String menuTableName;
	@JsonIgnore
	private String menuKind;
	@JsonIgnore
	private String menuModule;
	
	private String iconNo;

	private List<MenuVO> children = new ArrayList<MenuVO>();

	
	public String getMenuTableName() {
		return menuTableName;
	}

	public void setMenuTableName(String menuTableName) {
		this.menuTableName = menuTableName;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public List<MenuVO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVO> children) {
		this.children = children;
	}

	public String getMenuKind() {
		return menuKind;
	}

	public void setMenuKind(String menuKind) {
		this.menuKind = menuKind;
	}

	public String getMenuModule() {
		return menuModule;
	}

	public void setMenuModule(String menuModule) {
		this.menuModule = menuModule;
	}
	public String getIconNo() {
		return iconNo;
	}

	public void setIconNo(String iconNo) {
		this.iconNo = iconNo;
	}
}
