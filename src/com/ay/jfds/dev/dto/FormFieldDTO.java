package com.ay.jfds.dev.dto;

/**
 * field 和 formProperty的综合对象
 * @author wgw
 *
 */
public class FormFieldDTO implements Comparable<FormFieldDTO> {

	/** 表单属性id*/
	private String formPropertyId;
	/** 表单id*/
    private String formId;
    /** 表字段id*/
    private String fieldId;
	/** 实体表id */
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
	/** 实体表名*/
	private String entityCode;
    /** 显示名*/
    private String viewName;
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
    /** 是否有关联*/
    private String isRelated;
    /** 关联的字段名*/
    private String relatedCode;
    
	public int compareTo(FormFieldDTO o) {
		if (formOrder > o.formOrder) {
			return 1;
		} else if (formOrder < o.formOrder) {
			return -1;
		}
		return 0;
	}
    
	public String getFormPropertyId() {
		return formPropertyId;
	}
	public void setFormPropertyId(String formPropertyId) {
		this.formPropertyId = formPropertyId;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
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
		this.isNull = isNull;
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
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public String getIsView() {
		return isView;
	}
	public void setIsView(String isView) {
		this.isView = isView;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getAlgin() {
		return algin;
	}
	public void setAlgin(String algin) {
		this.algin = algin;
	}
	public String getIsSearch() {
		return isSearch;
	}
	public void setIsSearch(String isSearch) {
		this.isSearch = isSearch;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getFilterValue() {
		return filterValue;
	}
	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}
	public Integer getFormOrder() {
		return formOrder;
	}
	public void setFormOrder(Integer formOrder) {
		this.formOrder = formOrder;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getIsRelated() {
		return isRelated;
	}

	public void setIsRelated(String isRelated) {
		this.isRelated = isRelated;
	}

	public String getRelatedCode() {
		return relatedCode;
	}

	public void setRelatedCode(String relatedCode) {
		this.relatedCode = relatedCode;
	}

	
}
