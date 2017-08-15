package com.ay.jfds.sys.pojo;

import java.sql.Date;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class SysUserGroup extends BasePojo {

	/** 用户ID */
	private String userId;

	/** 用户组ID */
	private String groupId;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupId() {
		return this.groupId;
	}

}