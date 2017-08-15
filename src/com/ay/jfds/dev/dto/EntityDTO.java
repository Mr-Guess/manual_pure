package com.ay.jfds.dev.dto;

import java.io.Serializable;

/**
 * 表对象DTO，用来转换tree结构
 * @author wgw
 *
 */
public class EntityDTO implements Serializable{
	
	/** 表id*/
	private String id;	
	/** 实体编码，表英文名*/
	private String entityCode;
	/** 实体名称，表中文名*/
	private String entityName;
	/** 表单数 */
	private String formCount;
	/** 主表id*/
	private String _parentId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getFormCount() {
		return formCount;
	}
	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}
	public String get_parentId() {
		return _parentId;
	}
	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}
	
	

}
