package com.ay.report.reporting.dto;

import com.ay.framework.annotation.ChineseName;

public class ReportingDto {

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

	// 检查人标记
	private String checkerinob;
	
	@ChineseName("内容")
	private String reType;
	
	@ChineseName("详细")
	private String recontext;
	
	@ChineseName("批复人")
	private String rever ;
	
	@ChineseName("关联ID")
	private String convertId;
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
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

	public String getCheckerinob() {
		return checkerinob;
	}

	public void setCheckerinob(String checkerinob) {
		this.checkerinob = checkerinob;
	}

	public String getReType() {
		return reType;
	}

	public void setReType(String reType) {
		this.reType = reType;
	}

	public String getRecontext() {
		return recontext;
	}

	public void setRecontext(String recontext) {
		this.recontext = recontext;
	}

	public String getRever() {
		return rever;
	}

	public void setRever(String rever) {
		this.rever = rever;
	}

	public String getConvertId() {
		return convertId;
	}

	public void setConvertId(String convertId) {
		this.convertId = convertId;
	}
	
	
}
