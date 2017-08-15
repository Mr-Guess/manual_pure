package com.ay.framework.core.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.ay.framework.core.dao.BaseDao;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.pojo.BasePojo;

// TODO: 需要补充填写 Javadoc
/**
 * 注入和获取日志操作对象，所有的实现类都必须继承该类.
 * 
 * @param <T>
 *            *
 * @param <D>
 *            *
 * @DateTime: 2012-9-10
 * @author lushigai
 * @version 1.0
 */
@SuppressWarnings("all")
public abstract class BaseService<T extends BasePojo, D extends BaseDao<T>> {

	/** dao实现类. */
	protected D dao;
	/** 模型id. */
	protected Long modelId;

	/**
	 * Gets the model id.
	 * 
	 * @return the model id
	 */
	public Long getModelId() {
		return modelId;
	}

	/**
	 * Sets the model id.
	 * 
	 * @param modelId
	 *            the new model id
	 */
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public D getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao
	 *            the new dao
	 */
	public void setDao(D dao) {
		this.dao = dao;
	}

	/**
	 * Update.
	 * 
	 * @param entity
	 *            the entity
	 * @return true, if successful
	 */
	public boolean update(T entity) {
		return dao.update(entity);
	}

	/**
	 * Insert.
	 * 
	 * @param entity
	 *            the entity
	 * @return the t
	 */
	public T insert(T entity) {
		return dao.insert(entity);
	}

	/**
	 * 批量删除（废弃）
	 * 
	 * @param ids
	 *            the ids
	 * @return true, if successful
	 * @deprecated
	 */
	public boolean delete(Serializable[] ids) {
		boolean isSuccess = true;
		for (Serializable id : ids) {
			if (!delete(id)) {
				isSuccess = false;
				break;
			}
		}
		return isSuccess;
	}

	/**
	 * 批量删除（用） lijie 2013-1-21
	 * 
	 * @param @param ids
	 * @param @return   
	 * @return boolean   
	 */
	public boolean deleteByIds(String[] ids) {
		return dao.deleteByIds(ids);
	}

	/**
	 * 批量删除自定义 lijie 2013-1-21
	 * 
	 * @param @param ids
	 * @param @return   
	 * @return boolean   
	 */
	public boolean deleteSmsByIds(String[] ids) {
		return dao.deleteSmsByIds(ids);
	}

	/**
	 * 单条记录删除
	 * 
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public boolean delete(Serializable id) {
		return dao.delete(id);
	}

	/**
	 * Find all.
	 * 
	 * @return the list< t>
	 */
	public List<T> findAll() {
		return dao.findAll();
	}

	/**
	 * Find all.
	 * 
	 * @return the list< t>
	 */
	public List<T> findAll(Map map) {
		return dao.findAlls(map);
	}
	
	
	/**
	 * 
	 */
	
	public List<T> findPeop(List ids) {
		return dao.findPeop(ids);
	}

	/**
	 * Query.
	 * 
	 * @param map
	 *            the map
	 * @return the list< t>
	 */
	public List<T> query(Map map) {
		return dao.query(map);
	}

	/**
	 * Gets the by id.
	 * 
	 * @param id
	 *            the id
	 * @return the by id
	 */
	public T getById(Serializable id) {
		return dao.getById(id);
	}

	/**
	 * 分页查询.
	 * 
	 * @param map
	 *            the map
	 * @param page
	 *            the page
	 * @return the page
	 */
	public Page pageQuery(Map map, Page page) {
        	String userType = (String) SecurityUtils.getSubject().getSession()
        		.getAttribute("usertype");
        	String userId = (String) SecurityUtils.getSubject().getSession()
        		.getAttribute("user_id");
        	if (map == null) {
        	    map = new HashMap();
        	}
		try {
			page.setCount(this.count(map));
			List<T> list = dao.pageQuery(map, page.getFrom(), page
					.getRecPerPage());
			page.setCollection(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 分页查询不可用的状态
	 * 
	 * @param map
	 * @param page
	 * @return
	 */

	public Page pageQuerys(Map map, Page page) {
		try {
			page.setCount(this.count(map));
			List<T> list = dao.pageQuerys(map, page.getFrom(), page
					.getRecPerPage());
			page.setCollection(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;

	}

	/**
	 * Count.
	 * 
	 * @param map
	 *            the map
	 * @return the int
	 */
	public int count(Map map) {
		return dao.count(map);
	}

	/**
	 * 根据传入的参数，查询统计结果数量，参数名称为待校验字段的名称.
	 * 
	 * @param map
	 *            the map
	 * @return the int
	 */
	public int validate(Map map) {
		return dao.validate(map);
	}
}
