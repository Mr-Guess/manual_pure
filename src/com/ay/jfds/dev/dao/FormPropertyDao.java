package com.ay.jfds.dev.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.dev.dto.FormFieldDTO;
import com.ay.jfds.dev.dto.FormPropertyDTO;
import com.ay.jfds.dev.pojo.DevFormProperty;

public class FormPropertyDao extends BaseDao<DevFormProperty>{

	@Override
	public String getEntityName() {
		return "DevFormProperty";
	}

	/**
	 * 分页获取表单字段dto
	 * @param map 查询条件
	 * @param from 从第几页开始
	 * @param recPage 每页显示数
	 * @return 
	 */
	public List<FormPropertyDTO> pageQueryDTO(Map map, int from, int recPage){
		return (List<FormPropertyDTO>)this.getSqlMapClientTemplate().queryForList(getEntityName()+ ".findAllDTO", map, from, recPage);
	}
	
	/**
	 * 获取表单字段
	 * @param map
	 * @return
	 */
	public List<FormPropertyDTO> pageQueryDTO(Map map){
		return (List<FormPropertyDTO>)this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findAllDTO", map);
	}
	
	/**
	 * 获得dto的数量
	 * @param map
	 * @return
	 */
	public int countDTO(Map map){
		return (Integer)this.getSqlMapClientTemplate().queryForObject(getEntityName()+ ".countDTO", map);
	}
	
	/**
	 * 清理脏数据
	 */
	public void clear(){
		this.getSqlMapClientTemplate().update(getEntityName()+ ".clear");
	}
	
	/**
	 * 根据formId获得所有formField对象
	 * @param formId 
	 * @return FormFieldDTO
	 */
	public List<FormFieldDTO> getFormFieldByFormId(String formId){
		return (List<FormFieldDTO>) this.getSqlMapClientTemplate().queryForList(getEntityName()+ ".getFormFieldByFormId", formId);
	}
	
	/**
	 * 根据formId获得所有formField对象
	 * @param formId
	 * @return
	 */
	public List<DevFormProperty> findAll(Map map){
		return (List<DevFormProperty>) this.getSqlMapClientTemplate().queryForList(getEntityName() +".findAll", map);
	}

	@Override
	public String getTableName() {
		return "dev_form_entity";
	}
}
