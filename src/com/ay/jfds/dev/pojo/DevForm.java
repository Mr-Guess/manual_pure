package com.ay.jfds.dev.pojo;

import java.sql.Date;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class DevForm extends BasePojo {

	/** 表单名称 */
	private String formName;

	/** 对应的实体表id */
	private String entityId;

	/** 初始化类型 ，0:列表，1:详情 */
	private String initType;

	/** 菜单id */
	private String menuId;
	
	/** 是否有关联,0:没有关联，1:有关联*/
	private String isRelation;
	
	/** 父表名*/
	private String parentCode;
	

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormName() {
		return this.formName;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityId() {
		return this.entityId;
	}

	public void setInitType(String initType) {
		this.initType = initType;
	}

	public String getInitType() {
		return this.initType;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getIsRelation() {
		return isRelation;
	}

	public void setIsRelation(String isRelation) {
		this.isRelation = isRelation;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	@Override
	public String toString() {
		return "DevForm [formName=" + formName + ", entityId=" + entityId
				+ ", initType=" + initType + ", id =" + this.getId() + "]";
	}

}