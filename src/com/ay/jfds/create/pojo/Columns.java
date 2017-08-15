package com.ay.jfds.create.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @项目名称:jfds2.0
 * @类名称:Sheet
 * @类描述:创建表
 * @创建人:雷远亮
 * @创建时间:2014年6月23日11:05:08
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @version 2.0
 */
@SuppressWarnings("all")
//@ChineseName("列名")
public class Columns extends BasePojo{
	@ChineseName("所属表")
	private String sheetId;
	/*表名*/
	private String sheetName;
	@ChineseName("英文名称")
	private String columnEN;
	@ChineseName("中文名称")
	private String columnCH;
	@ChineseName("字段类型")
	private String type;
	/*字段类型*/
	private String typeName;
	@ChineseName("字段长度")
	private String length;
	@ChineseName("是否关联元数据")
	private String enableData;
	@ChineseName("元数据")
	private String devData;
	@ChineseName("数据引用")
	private String adduction;
	@ChineseName("是否启用")
	private String enable;
	@ChineseName("显示顺序")
	private String order;
	@ChineseName("是否为空")
	private String isNull="0";
	@ChineseName("验证")
	private String validate;
	@ChineseName("错误提示")
	private String prompt;
	/*元数据名称*/
	private String devDataName;
	public String getSheetId() {
		return sheetId;
	}
	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}
	public String getColumnEN() {
		return columnEN;
	}
	public void setColumnEN(String columnEN) {
		this.columnEN = columnEN;
	}
	public String getColumnCH() {
		return columnCH;
	}
	public void setColumnCH(String columnCH) {
		this.columnCH = columnCH;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getEnableData() {
		return enableData;
	}
	public void setEnableData(String enableData) {
		this.enableData = enableData;
	}
	public String getDevData() {
		return devData;
	}
	public void setDevData(String devData) {
		this.devData = devData;
	}
	public String getAdduction() {
		return adduction;
	}
	public void setAdduction(String adduction) {
		this.adduction = adduction;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDevDataName() {
		return devDataName;
	}
	public void setDevDataName(String devDataName) {
		this.devDataName = devDataName;
	}
}
