package com.ay.jfds.sys.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

/** 
 * @项目名称:公共框架
 * @类名:SystemConfigure.java 
 * @描述: 系统参数配置表
 * @创建人: 周伟 
 * @创建时间: 2015年7月27日下午2:02:16 
 * @修改人: 
 * @修改时间: 
 * @修改备注:
 * @version 3.0
 */
//@ChineseName("系统参数")
public class SystemConfigure extends BasePojo {
	
	@ChineseName("参数码")
	private String dataCode;
	
	@ChineseName("参数名称")
	private String dataName;
	
	@ChineseName("参数值")
	private String dataValue;
	
	@ChineseName("参数描述")
	private String dataDescribe;
	
	/*
	 * 0:系统参数 只可维护dataValue、不可删除
	 * 1:非系统参数 可维护、可删除 
	 */
	@ChineseName("参数类型")
	private String dataType;

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataDescribe() {
		return dataDescribe;
	}

	public void setDataDescribe(String dataDescribe) {
		this.dataDescribe = dataDescribe;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
}
