package com.ay.report.reporting.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

@ChineseName("工作汇报")
public class Reporting extends BasePojo{

	@ChineseName("回报标题")
	private String title;
	
	@ChineseName("汇报人")
	private String reporter;
	
	//汇报人部门
	private String department;
	private String departmentName;
	
	@ChineseName("汇报时间")
	private String reportTime;
	
	@ChineseName("汇报类型")
	private String reportType;
	
	@ChineseName("汇报内容")
	private String context;
	
	@ChineseName("审阅人")
	private String checker;
	
	@ChineseName("拥有者")
	private String owner;
	
	@ChineseName("当前步骤")
	private String steps;
	
	@ChineseName("经办人")
	private String informre;
	private String ownerName;

	// 检查人标记
	private String checkerinob;
	private String checkerAnd;
	private String reportinf;
	
	// 自动生成标题
	private String autoTitle;
	
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getReportinf() {
		return reportinf;
	}

	public void setReportinf(String reportinf) {
		this.reportinf = reportinf;
	}

	public String getCheckerAnd() {
		return checkerAnd;
	}

	public void setCheckerAnd(String checkerAnd) {
		this.checkerAnd = checkerAnd;
	}

	public String getAutoTitle() {
		return autoTitle;
	}

	public void setAutoTitle(String autoTitle) {
		this.autoTitle = autoTitle;
	}

	public String getCheckerinob() {
		return checkerinob;
	}

	public void setCheckerinob(String checkerinob) {
		this.checkerinob = checkerinob;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
 
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public String getInformre() {
		return informre;
	}

	public void setInformre(String informre) {
		this.informre = informre;
	}
	
	
}
