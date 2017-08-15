package com.ay.jfds.sys.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.annotation.PersistenceIgnore;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 部门表
 */
@SuppressWarnings("serial")
public class Department extends BasePojo {
	/** 部门的父类 */
	private String parentId;

	/** 部门名称 */
	private String deptName;
	private String name;
	/** 排序 */
	private int deptOrder;
	/**
	 * 上级部门结构
	 */
	private String deptTree;
	
	/**
	 * 树父类类型(1为政府部门2为企业类型) 
	 */
	private String parentType;
	
	/**
	 * 类型表单
	 */
	private String deptUrl;
	
	/**
	 * 部门属地
	 */
	private String province;
	private String city;
	private String area;
	private String street;
	
	private String provinceTxt;
	private String cityTxt;
	private String areaTxt;
	private String streetTxt;
	
	public String getProvinceTxt() {
		return provinceTxt;
	}

	public void setProvinceTxt(String provinceTxt) {
		this.provinceTxt = provinceTxt;
	}

	public String getCityTxt() {
		return cityTxt;
	}

	public void setCityTxt(String cityTxt) {
		this.cityTxt = cityTxt;
	}

	public String getAreaTxt() {
		return areaTxt;
	}

	public void setAreaTxt(String areaTxt) {
		this.areaTxt = areaTxt;
	}

	public String getStreetTxt() {
		return streetTxt;
	}

	public void setStreetTxt(String streetTxt) {
		this.streetTxt = streetTxt;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDeptUrl() {
		return deptUrl;
	}

	public void setDeptUrl(String deptUrl) {
		this.deptUrl = deptUrl;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	

	public int getDeptOrder() {
		return deptOrder;
	}

	public void setDeptOrder(int deptOrder) {
		this.deptOrder = deptOrder;
	}

	public String getDeptTree() {
		return deptTree;
	}

	public void setDeptTree(String deptTree) {
		this.deptTree = deptTree;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
