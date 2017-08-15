package com.ay.jfds.sys.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

/** 
 * @项目名称:南京智慧安监云平台
 * @类名:LoginRecord.java 
 * @描述:
 * @创建人: 杨丰智 
 * @创建时间: 2014年12月9日下午2:32:30 
 * @修改人: 
 * @修改时间: 
 * @修改备注：
 * @version 2.0
 */
//@ChineseName("用户登陆记录")
public class LoginRecord extends BasePojo{

	@ChineseName("用户Id")
	private String userId;
	@ChineseName("用户名")
	private String userName;
	@ChineseName("登陆时间")
	private String loginTime;
	@ChineseName("登陆标记")
	private String loginTag;
	@ChineseName("部门Id")
	private String deptId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginTag() {
		return loginTag;
	}
	public void setLoginTag(String loginTag) {
		this.loginTag = loginTag;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
}
