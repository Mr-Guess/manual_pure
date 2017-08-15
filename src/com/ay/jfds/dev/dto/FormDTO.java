package com.ay.jfds.dev.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FormDTO implements Serializable{

	/** 表单id*/
	private String id;
	/** 菜单id*/
	private String menuId;
	/** 表单名称 */
    private String formName;
    /** 对应的实体表id*/
    private String entityId;
    /** 初始化类型*/
    private String initType;
    /** 对应的实体表名称 */
    private String entityCode;

    public void setFormName(String formName){
    	this.formName=formName;
    }
    public String getFormName(){
    	return	this.formName;
    }
    public void setEntityId(String entityId){
    	this.entityId=entityId;
    }
    public String getEntityId(){
    	return	this.entityId;
    }
    public void setInitType(String initType){
    	this.initType=initType;
    }
    public String getInitType(){
    	return	this.initType;
    }
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
}
