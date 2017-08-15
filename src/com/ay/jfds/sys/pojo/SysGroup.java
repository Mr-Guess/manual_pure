package com.ay.jfds.sys.pojo;

import java.sql.Date;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class SysGroup extends BasePojo {

	/** 用户组名称 */
	private String groupName;

	/** 用户组描述 */
	private String description;

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}