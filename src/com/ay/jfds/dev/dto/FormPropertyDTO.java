package com.ay.jfds.dev.dto;

import java.io.Serializable;

import com.ay.framework.core.pojo.BasePojo;

public class FormPropertyDTO implements Serializable{
	private String id;
	private String formId;
    private String fieldId;
    private String viewName;
    private String defaultValue;
    private String editType;
    private String required;
    private String isView;
    private String orderType;
    private String width;
    private String algin;
    private String isSearch;
    private String searchType;
    private String filterType;
    private String filterValue;
    private String fieldType;
    private String fieldCode;
    private String isRelation;
    private String relatedCode;
    public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	private Integer formOrder;
    private String isMust;

    public void setFormId(String formId){
    	this.formId=formId;
    }
    public String getFormId(){
    	return	this.formId;
    }
    public void setFieldId(String fieldId){
    	this.fieldId=fieldId;
    }
    public String getFieldId(){
    	return	this.fieldId;
    }
    public void setViewName(String viewName){
    	this.viewName=viewName;
    }
    public String getViewName(){
    	return	this.viewName;
    }
    public void setDefaultValue(String defaultValue){
    	this.defaultValue=defaultValue;
    }
    public String getDefaultValue(){
    	return	this.defaultValue;
    }
    public void setEditType(String editType){
    	this.editType=editType;
    }
    public String getEditType(){
    	return	this.editType;
    }
    public void setRequired(String required){
    	this.required=required;
    }
    public String getRequired(){
    	return	this.required;
    }
    public void setIsView(String isView){
    	this.isView=isView;
    }
    public String getIsView(){
    	return	this.isView;
    }
    public void setOrderType(String orderType){
    	this.orderType=orderType;
    }
    public String getOrderType(){
    	return	this.orderType;
    }
    public void setWidth(String width){
    	this.width=width;
    }
    public String getWidth(){
    	return	this.width;
    }
    public void setAlgin(String algin){
    	this.algin=algin;
    }
    public String getAlgin(){
    	return	this.algin;
    }
    public void setIsSearch(String isSearch){
    	this.isSearch=isSearch;
    }
    public String getIsSearch(){
    	return	this.isSearch;
    }
    public void setSearchType(String searchType){
    	this.searchType=searchType;
    }
    public String getSearchType(){
    	return	this.searchType;
    }
    public void setFilterType(String filterType){
    	this.filterType=filterType;
    }
    public String getFilterType(){
    	return	this.filterType;
    }
    public void setFilterValue(String filterValue){
    	this.filterValue=filterValue;
    }
    public String getFilterValue(){
    	return	this.filterValue;
    }
	public Integer getFormOrder() {
		return formOrder;
	}
	public void setFormOrder(Integer formOrder) {
		this.formOrder = formOrder;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsMust() {
		return isMust;
	}
	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getIsRelation() {
		return isRelation;
	}
	public void setIsRelation(String isRelation) {
		this.isRelation = isRelation;
	}
	public String getRelatedCode() {
		return relatedCode;
	}
	public void setRelatedCode(String relatedCode) {
		this.relatedCode = relatedCode;
	}
	
	
}
