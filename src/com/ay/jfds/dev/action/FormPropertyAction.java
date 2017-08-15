package com.ay.jfds.dev.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ay.framework.bean.DataStore;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.framework.util.JacksonUtil;
import com.ay.jfds.dev.dto.FormPropertyDTO;
import com.ay.jfds.dev.pojo.DevForm;
import com.ay.jfds.dev.pojo.DevFormProperty;
import com.ay.jfds.dev.pojo.Entity;
import com.ay.jfds.dev.pojo.EntityField;
import com.ay.jfds.dev.service.EntityFieldService;
import com.ay.jfds.dev.service.EntityService;
import com.ay.jfds.dev.service.FormPropertyService;
import com.ay.jfds.dev.service.FormService;

@SuppressWarnings("all")
public class FormPropertyAction extends BaseAction{
	
	private FormPropertyService formPropertyService;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String[] ids;
	
	private String viewName;
	private String formId;
	/** 表单更新数据*/
	private String updated;
	private String id;
	
	
	
	private CacheManager cacheManager;
	private Cache cache;
	private final static String CACHE_NAME = "coreCache";
	/**
	 * 分页获取表单
	 */
	public void pageList() {
//		DataStore<FormPropertyDTO> dataStore = new DataStore<FormPropertyDTO>();
//		Page pageTemp = new Page();
//		//当前页 
//		int intPage = Integer.parseInt((page == null || "0".equals(page)) ? "1" : page);
//		//每页显示条数  
//		int number = Integer.parseInt((rows == null || "0".equals(rows)) ? "10" : rows);
//		int start = (intPage -1) * number;
//		pageTemp.setCurrentPage(intPage);
//		pageTemp.setRecPerPage(number);
//		pageTemp.setFrom(start);
		Map paramMap = new HashMap();
		//setFormType(formId);
		
		//设置查询条件
		paramMap.put("viewName", viewName);
		paramMap.put("formId", formId);
//		Page resultPage = formPropertyService.pageQueryDTO(paramMap,pageTemp);
//		List<FormPropertyDTO> resultList = (List<FormPropertyDTO> ) resultPage.getCollection();
//		setIsMust(resultList);
//		dataStore.setTotal(new Long(resultPage.getCount()));
//		dataStore.setRows(resultList);
		List<FormPropertyDTO> list = formPropertyService.pageQueryDTO(paramMap);
		Struts2Utils.renderJson(list, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	/**
	 * 设置是否必输
	 * @param resultList
	 */
	private void setIsMust(List<FormPropertyDTO> resultList) {
		EntityFieldService efs = SpringContextHolder.getBean(EntityFieldService.class);
		for(FormPropertyDTO fpd :resultList){
			String eId = fpd.getFieldId();
			EntityField ef = efs.getById(eId);
			if("0".equals(ef.getIsNull())){
				fpd.setIsMust("1");
			}
		}
	}

	/**
	 * 接受提交的数据并操作
	 */
	public void commit(){
		initCache();
		FormService formService = SpringContextHolder.getBean(FormService.class);
		DevForm devForm = null;
		List<Map<String,String>> updatedList = JacksonUtil.json2Java(updated);
		DevFormProperty devFormProperty = null;
		for(Map<String,String> map:updatedList){
			devFormProperty = (DevFormProperty)BeanUtil.Map2Bean(DevFormProperty.class, map);
			devFormProperty.setId(map.get("id"));
			String width = devFormProperty.getWidth();
			String str = width.replaceAll("[$]", "\\%");
			devFormProperty.setWidth(str);
			formPropertyService.update(devFormProperty);
		}
		devForm = formService.getById(devFormProperty.getFormId());
		cache.remove(devForm.getMenuId());
		Struts2Utils.renderText("ok", EncodingHeaderUtil.HEADERENCODING);
	}
	
	/**
	 * 请求获得关联表的字段名
	 */
	public void fieldCodeList(){
		System.out.println(formId);
		FormService fs = SpringContextHolder.getBean(FormService.class);
		DevForm df = fs.getById(formId);
		String isRelation = df.getIsRelation();
		if("1".equals(isRelation)){
			String entityId = df.getEntityId();
			EntityService es = SpringContextHolder.getBean(EntityService.class);
			Entity enty = es.getById(entityId);
			List<EntityField> efList = es.getFieldByEntityId(enty.getParentId());
			Struts2Utils.renderJson(efList, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
		}
	}
	
	private void setFormType(String id) {
		FormService fs = SpringContextHolder.getBean(FormService.class);
		DevForm df = fs.getById(id);
		ServletActionContext.getRequest().setAttribute("formType", df.getInitType());
	}
	public FormPropertyService getFormPropertyService() {
		return formPropertyService;
	}
	public void setFormPropertyService(FormPropertyService formPropertyService) {
		this.formPropertyService = formPropertyService;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	@Autowired
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	public void initCache() {
		cache = cacheManager.getCache(CACHE_NAME);
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
}
