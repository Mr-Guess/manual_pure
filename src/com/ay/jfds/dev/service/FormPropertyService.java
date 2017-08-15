package com.ay.jfds.dev.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.service.BaseService;
import com.ay.jfds.dev.dao.FormPropertyDao;
import com.ay.jfds.dev.dto.FormFieldDTO;
import com.ay.jfds.dev.dto.FormPropertyDTO;
import com.ay.jfds.dev.pojo.DevFormProperty;

public class FormPropertyService extends BaseService<DevFormProperty,FormPropertyDao>{
	
	/**
	 * 根据条件获得dto分页数据
	 * @param map
	 * @param page
	 * @return page
	 */
	public Page pageQueryDTO(Map map, Page page){
		page.setCount(this.dao.countDTO(map));
		List<FormPropertyDTO> list = this.dao.pageQueryDTO(map, page.getFrom(), page.getRecPerPage());
		page.setCollection(list);
		return page;
	}
	
	/**
	 * 根据条件获得dto
	 * @param map
	 * @return
	 */
	public List<FormPropertyDTO> pageQueryDTO(Map map){
		List<FormPropertyDTO> list = this.dao.pageQueryDTO(map);
		return list;
	}
	
	/**
	 * 清理脏数据
	 */
	public void clear(){
		this.dao.clear();
	}
	
	/**
	 * 根据formId获得所有formField对象
	 * @param formId 
	 * @return FormFieldDTO
	 */
	public List<FormFieldDTO> getFormFieldByFormId(String formId){
		return this.dao.getFormFieldByFormId(formId);
	}
	
	/**
	 * 根据formId获得对应的DevFormProperty的list
	 * @param formId
	 * @return
	 */
	public List<DevFormProperty> getFormPropertyByFormId(String formId){
		Map map = new HashMap();
		map.put("formId", formId);
		return (List<DevFormProperty>)this.dao.findAll(map);
	}
}
