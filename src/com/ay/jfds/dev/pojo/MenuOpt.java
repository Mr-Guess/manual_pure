package com.ay.jfds.dev.pojo;

import com.ay.framework.core.pojo.BasePojo;

public class MenuOpt extends BasePojo {
	/** 菜单Id */
	private int menuId;
	/** 菜单权限编码 */
	private String optCode;
	/** 菜单权限名称 */
	private String optName;
	/** 菜单权限链接地址 */
	private String optUrl;
	/** 菜单权限类型 */
	private String optType;
	/** 菜单权限排序 */
	private String optOrder;
	/** 方法名称 */
	private String funcName;
	/** 方法参数 */
	private String funcPara;
	/** 方法内容 */
	private String funcContent;
	/** 打开方式 */
	private String openType;
	/** 是否定制 */
	private String displayType;
	/** 模板地址 */
	private String designPageUrl;
	/** 关联的表单id */
	private String formId;
	/** url参数 */
	private String urlPara;

	public String getOptOrder() {
		return optOrder;
	}

	public void setOptOrder(String optOrder) {
		this.optOrder = optOrder;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getOptCode() {
		return optCode;
	}

	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptUrl() {
		return optUrl;
	}

	public void setOptUrl(String optUrl) {
		this.optUrl = optUrl;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncPara() {
		return funcPara;
	}

	public void setFuncPara(String funcPara) {
		this.funcPara = funcPara;
	}

	public String getFuncContent() {
		return funcContent;
	}

	public void setFuncContent(String funcContent) {
		this.funcContent = funcContent;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getDesignPageUrl() {
		return designPageUrl;
	}

	public void setDesignPageUrl(String designPageUrl) {
		this.designPageUrl = designPageUrl;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getUrlPara() {
		return urlPara;
	}

	public void setUrlPara(String urlPara) {
		this.urlPara = urlPara;
	}

}
