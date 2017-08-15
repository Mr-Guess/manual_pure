package com.ay.jfds.sys.pojo;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ay.framework.core.pojo.BasePojo;

/**
 * 用户实体类
 */
//@SuppressWarnings("serial")
public class User extends BasePojo {
	/** 用户 */
	private String account;
	
	/** 密码 */
	private String password;
	
	/** 用户名 */
	private String userName;
	/** 职务 */
    private String business;
	
	/** department id */
	private String deptId;
	/** 部门名称 **/
	private String deptName;
	/** 用户类型 0 为超级管理员 1 为其它用户 */
	private String userType;
	
	/** 皮肤类型 */
	private String skinType;
	
	/** 登录时间 */
	private Date loginTime;
	
	/** 最后一次登录时间 */
	private Date lastLoginTime;
	
	/** 空白的密码没有进行过加密处理 */
	// 这个属性是不会持久化到数据库里面的
	// user.plainPassword
	private String plainPassword;
	
	/** 用户下所具有的salt */
	private String salt;
	
	private int loseCount;
	/**
	 * 部门结构（上上级部门id-上级部门id)
	 */
	
	/**
	 * 网易云APP
	 */
	private String token;
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public int getLoseCount() {
		return loseCount;
	}

	public void setLoseCount(int loseCount) {
		this.loseCount = loseCount;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getSkinType() {
		return skinType;
	}

	public void setSkinType(String skinType) {
		this.skinType = skinType;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getBusiness()
    {
        return business;
    }

    public void setBusiness(String business)
    {
        this.business = business;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
