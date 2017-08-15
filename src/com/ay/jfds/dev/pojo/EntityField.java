package com.ay.jfds.dev.pojo;

import com.ay.framework.core.pojo.BasePojo;

/**
 * 表结构属性实体类
 * @author wuguowen
 * 
 */
public class EntityField extends BasePojo {

	/** 实体id */
	private String entityId;
	/** 字段编码 */
	private String fieldCode;
	/** 字段名称 */
	private String fieldName;
	/** 序号 */
	private String entityOrder;
	/** 默认值 */
	private String defaultValue;
	/** 数据类型 */
	private String fieldType;
	/** 字段长度 */
	private String fieldLength;
	/** 是否为主键 */
	private String isPk;
	/** 是否为空 */
	private String isNull;
	/** 控件类型*/
	private String controlType;
	/** 元数据id*/
	private String dataTypeId;
	
	
	/** get，set方法 */
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getEntityOrder() {
		return entityOrder;
	}
	public void setEntityOrder(String entityOrder) {
		this.entityOrder = entityOrder;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}
	public String getIsPk() {
		return isPk;
	}
	public void setIsPk(String isPk) {
		this.isPk = isPk;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		if("no".equalsIgnoreCase(isNull)){
			this.isNull = "0";
		}else if("yes".equalsIgnoreCase(isNull)){
			this.isNull = "1";
		}else{
			this.isNull = isNull;
		}		
	}
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public String getDataTypeId() {
		return dataTypeId;
	}
	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
	}
	@Override
	public String toString() {
		return "EntityField [Id=" + super.getId() + "]";
	}
	
	

	
	
}
