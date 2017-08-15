package com.ay.jfds.sys.pojo;

import java.sql.Date;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class SysRole extends BasePojo {

	/** 角色名称 */
	private String roleName;

	/** 角色描述 */
	private String description;
	
	/** 角色类型 */
	private String roleType;
	
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}