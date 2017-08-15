package com.ay.jfds.sys.pojo;

import com.ay.framework.core.pojo.BasePojo;

/**
 * 监管机构所监管的行业类别pojo 与监管机构为1对多的关系
 * 
 * @author pusheng
 * 
 */
@SuppressWarnings("serial")
public class DepartmentIndustry extends BasePojo {

	/** 行业名 */
	private String industryName;
	/** 行业代码 */
	private String industryCode;
	/** 关联的部门名 */
	private String departmentId;

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

}
