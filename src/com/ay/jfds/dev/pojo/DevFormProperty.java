package com.ay.jfds.dev.pojo;

import java.sql.Date;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class DevFormProperty extends BasePojo  { 
	/** 关联的表单id*/
    private String formId;
    /** 关联的字段id*/
    private String fieldId;
    /** 显示名*/
    private String viewName;
    /** 缺省值*/
    private String defaultValue;
    /** 编辑状态，0:读写 ，1:只读，2:隐藏*/
    private String editType;
    /** 是否必填,0:不必填，1:必填*/
    private String required;
    /** 是否显示，0:显示，1:不显示*/
    private String isView;
    /** 排序类型，0:升序，1:降序*/
    private String orderType;
    /** 列宽*/
    private String width;
    /** 对齐方式，0:右对齐，1:左对齐，2:居中*/
    private String algin;
    /** 是否查询，0:不查询，1:查询*/
    private String isSearch;
    /** 检索逻辑符，0:模糊查询，1:等于，2:小于，3:小于等于，4:大于，5:大于等于*/
    private String searchType;
    /** 过滤逻辑符，0:模糊查询，1:等于，2:小于，3:小于等于，4:大于，5:大于等于*/
    private String filterType;
    /** 过滤值*/
    private String filterValue;
    /** 序号，按序号的升序排列*/
    private Integer formOrder;
    /** 是否有关联,0:没有关联,1:有关联*/
    private String isRelation;
    /** 关联字段名*/
    private String relatedCode;

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
	@Override
	public String toString() {
		return "DevFormProperty [width=" + width + "]";
	}
	
	
	
}