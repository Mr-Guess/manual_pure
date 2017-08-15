package com.ay.jfds.dev.service;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.service.BaseService;
import com.ay.jfds.dev.dao.EntityFieldDao;
import com.ay.jfds.dev.pojo.Entity;
import com.ay.jfds.dev.pojo.EntityField;

/**
 * 表结构属性service层
 * 
 * @author wuguowen
 * @datetime 2012-10-18
 * @version 1.0
 */
public class EntityFieldService extends
		BaseService<EntityField, EntityFieldDao> {

	/**
	 * 分页获得对应表下所有属性字段
	 * 
	 * @param page
	 * @return
	 */
	public Page pageAllEntityField(Map map, Page page) {
		page.setCount(this.count(map));
		String entityId = map.get("entityId") == null ? null : map.get(
				"entityId").toString();
		List<EntityField> list = null;
		if (entityId != null) {
			list = dao.findAllEntityTable(entityId, page.getFrom(),
					page.getRecPerPage());
		}
		page.setCollection(list);
		return page;
	}
	
	/**
	 * 执行传入的sql语句
	 *
	 * @param sql
	 */

	public void createTable(String sql) {

		dao.createTable(sql);
	}
	
	/**
	 * 删除对应表下所有属性
	 * @param entityId
	 */
	public void deleteByEntityId(String entityId){
		dao.deleteByEntityId(entityId);
	}
	
	/**
	 * 获得对应表下所有属性字段
	 * @param id
	 * @return
	 */
	public List<EntityField> getAllEntityField(String id){
		return dao.findAllEntityField(id);
	}
	
	/**
	 * 插入实体表
	 * @param entity
	 * @return
	 */
	public Entity addEntity(Entity entity){
		return dao.insertEntity(entity);
	}
	
	/**
	 * 删除实体表
	 * @param id
	 */
	public void deleteEntity(String id){
		dao.deleteEntity(id);
	}
	
	/**
	 * 删除实体表
	 * @param entity
	 */
	public int updateEntity(Entity entity){
		return dao.updateEntity(entity);
	}
	
	/**
	 * 获得实体表
	 * @param id
	 * @return
	 */
	public Entity getEntityById(String id){
		return dao.getEntityById(id);
	}
	
	/**
	 * 获得表名为entityCode的表的数量
	 * @param entityCode
	 * @return Integer
	 */
	public Integer getCountByEntityCode(String entityCode){
		return dao.isEntityExist(entityCode);
	}
}
