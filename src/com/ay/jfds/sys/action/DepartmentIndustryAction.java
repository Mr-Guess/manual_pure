package com.ay.jfds.sys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.framework.util.StringUtil;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.service.DataService;
import com.ay.jfds.sys.pojo.DepartmentIndustry;
import com.ay.jfds.sys.service.DepartmentIndustryService;

public class DepartmentIndustryAction extends BaseAction {

	public DataService dataService;
	public Data data;
	public String departmentId;
	public DepartmentIndustryService departmentIndustryService;
	public String parentId;
	public String existIndustry;

	private final static Logger logger = LoggerFactory
			.getLogger(DepartmentIndustryAction.class);

	/**
	 * 查找部门可以监管的行业 对于已经监管的行业，不会出现在选项列表中，防止监管重复
	 */
	@SuppressWarnings("unchecked")
	public void initDynamicIndustrySerch() {
		// 这边先查出部门已经监管的行业
		List<DepartmentIndustry> industrs = departmentIndustryService
				.findExistIndustry(departmentId);
		Map param = new HashMap();
		param.put("industrParams", industrs);
		if (StringUtil.isNullOrEmpty(parentId))
			param.put("parentId", "-1");
		else
			param.put("parentId", parentId);
		List<Data> notIndustrs = departmentIndustryService.findNotExit(param);
		Struts2Utils.renderJson(notIndustrs, EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}

	@SuppressWarnings("unchecked")
	public void dynamicIndustrySerch() {
		Map param = new HashMap();
		if (existIndustry != null && existIndustry != "" && existIndustry.length() > 0) {
			String[] industrs = existIndustry.split(";");
			param.put("industrs", industrs);
		}
		if (StringUtil.isNullOrEmpty(parentId))
			param.put("parentId", "-1");
		else
			param.put("parentId", parentId);
		List<Data> notIndustrs = departmentIndustryService.findNotExit(param);
		Struts2Utils.renderJson(notIndustrs, EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);

	}

	public List<Data> getIndustryFirst() {
		return null;
	}

	public DataService getDataService() {
		return dataService;
	}

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public DepartmentIndustryService getDepartmentIndustryService() {
		return departmentIndustryService;
	}

	public void setDepartmentIndustryService(
			DepartmentIndustryService departmentIndustryService) {
		this.departmentIndustryService = departmentIndustryService;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getExistIndustry() {
		return existIndustry;
	}

	public void setExistIndustry(String existIndustry) {
		this.existIndustry = existIndustry;
	}

}
