package com.ay.jfds.sys.pojo;

import com.ay.framework.core.pojo.BasePojo;

/**
 * 权限角色级权限POJO
 * 
 * @author zxy
 */
@SuppressWarnings("serial")
public class SysRoleData extends BasePojo {
	/** 角色ID */
	private String roleId;
	
	/** 表名 */
	private String tableName;
	
	/** 用户IDS */
	private String userIds;
	
	/** 部门IDS */
	private String deptIds;

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserIds() {
		return this.userIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getDeptIds() {
		return this.deptIds;
	}

}