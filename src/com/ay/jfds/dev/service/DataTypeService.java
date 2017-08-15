package com.ay.jfds.dev.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.service.BaseService;
import com.ay.jfds.dev.dao.DataTypeDao;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.pojo.DataType;

/**
 * 元数据类型service 封装
 * 
 * @author PS
 * 
 */
public class DataTypeService extends BaseService<DataType, DataTypeDao> {

	/**
	 * 查询类型下的所有元数据
	 * 
	 * @param dataType
	 * @param page
	 * @return
	 */
	public Page findAllDataInType(DataType dataType, Page page) {
		page.setCount(this.count(new HashMap()));
		List<Data> list = this.getDao().findAllDataInType(dataType,
				page.getFrom(), page.getRecPerPage());
		page.setCollection(list);
		return page;
	}

	/**
	 * 根据ID 查找类型下所有元数据
	 * 
	 * @param id
	 * @param page
	 * @return
	 */
	public Page findAllDataInTypeById(String id, Page page) {
		page.setCount(this.count(new HashMap()));
		List<Data> list = this.getDao().findAllDataInType(id, page.getFrom(),
				page.getRecPerPage());
		page.setCollection(list);
		return page;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteDataByType(String id) {
		return this.getDao().deleteDataByType(id);
	}

	/**
	 * 检查是否数据库重复
	 * 
	 * @param typeName
	 * @return
	 */
	public boolean checkWord(String typeName) {
		int integer = this.getDao().findWordsByWords(typeName);
		if (integer != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 检查编码是否重复
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkWordById(String id) {
		int integer = this.getDao().findWordsById(id);
		if (integer != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据条件获得所有元数据类型 by wgw
	 * 
	 * @param map
	 * @return
	 */
	public List<DataType> findAllDataType(Map map) {
		return this.getDao().findAll(map);
	}
	/**
	 * 查询所有不为0的
	 * @return
	 */
	public List<DataType> findAllDataTypes(){
		return this.getDao().findAllDataTypes();
	}

	/**
	 * 根据条件获取某个数据类型
	 * 
	 * @param map
	 * @return
	 */
	public DataType findDataType(Map map) {
		return this.getDao().find(map);
	}

	/**
	 * 查询所有元数据类型，带分页 by wgw
	 * 
	 * @param map
	 * @param page
	 * @return
	 */
	public Page findAllDataType(Map map, Page page) {
		page.setCount(this.dao.count(map));

		List<DataType> list = this.dao.pageQuery(map, page.getFrom(),
				page.getRecPerPage());
		page.setCollection(list);
		return page;
	}
}
