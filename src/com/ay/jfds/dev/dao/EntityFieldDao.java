package com.ay.jfds.dev.dao;

import java.util.List;

import org.apache.shiro.SecurityUtils;

import com.ay.framework.core.dao.BaseDao;
import com.ay.framework.util.DateUtil;
import com.ay.jfds.dev.pojo.Entity;
import com.ay.jfds.dev.pojo.EntityField;

/**
 * 表结构属性dao层
 * 
 * @author wuguowen
 * @datetime 2012-10-18
 * @version 1.0
 */
@SuppressWarnings("all")
public class EntityFieldDao extends BaseDao<EntityField> {

	@Override
	public String getEntityName() {
		return "entityField";
	}

	/**
	 * 根据id获得属性字段
	 * 
	 * @param id
	 * @return EntityTable
	 */
	public EntityField getEntityTableById(String id) {
		return (EntityField) getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".getById", id);
	}

	/**
	 * 获得所有分布式的表下的属性字段
	 * 
	 * @return List<EntityTable>
	 */
	public List<EntityField> findAllEntityTable(String entityId, int from,
			int prePageNum) {
		return (List<EntityField>) getSqlMapClientTemplate().queryForList(
				getEntityName() + ".find", entityId, from, prePageNum);
	}

	/**
	 * 获得所有实体下的属性字段
	 * @param entityId
	 * @return  List<EntityField>
	 */
	public List<EntityField> findAllEntityField (String entityId){
		return (List<EntityField>)getSqlMapClientTemplate().queryForList(getEntityName()+".find",entityId);
	}
	
	/**
	 * 根据sql创建表
	 * 
	 * @param sql
	 */
	public void createTable(String sql) {
		getSqlMapClientTemplate().update(getEntityName() + ".createTable", sql);
	}
	
	/**
	 * 根据实体删除其下的所有属性
	 * @param entityId
	 */
	public void deleteByEntityId(String entityId){
		getSqlMapClientTemplate().delete(getEntityName()+".deleteByEntityId",entityId);
	}
	
	/**
	 * 插入entity数据
	 * @param entity
	 * @return Entity
	 */
	public Entity insertEntity(Entity entity){
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("user_id");
        if (entity.getId() == null || entity.getId().trim().equals(""))
        {
            String id = generateId();
            entity.setId(id);
        }
        if (null ==entity.getStatus()|| entity.getStatus().trim().equals(""))
        {
        	System.out.println("插入后的entity:"+entity);
        	entity.setStatus("1");
        }
        if (null ==entity.getCreated()|| entity.getCreated().trim().equals(""))
        {
        	entity.setCreated(userId);
        }
        if (null ==entity.getCreateTime())
        {
        	entity.setCreateTime(DateUtil.getDateTime());
        }
        if (null ==entity.getUpdated()|| entity.getUpdated().trim().equals(""))
        {
        	entity.setUpdated(userId);
        }
        if (null ==entity.getUpdateTime())
        {
        	entity.setUpdateTime(DateUtil.getDateTime());
        }
        
        getSqlMapClientTemplate().insert(getEntityName() + ".insertEntity", entity);
        return entity;
	}
	
	public void deleteEntity(String id){
		getSqlMapClientTemplate().delete(getEntityName() + ".deleteEntity",id );
	}
	
	/**
	 * 更新实体表
	 * @param entity
	 */
	public int updateEntity(Entity entity){
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("user_id");;
    	entity.setUpdateTime(DateUtil.getDateTime());
    	entity.setUpdated(userId);
		return getSqlMapClientTemplate().update(getEntityName() + ".updateEntity", entity);
	}
	
	/**
	 * 获得实体表
	 * @param id
	 * @return Entity
	 */
	public Entity getEntityById(String id){
		return (Entity)getSqlMapClientTemplate().queryForObject(getEntityName() + ".getEntityById", id);
	}
	
	/**
	 * 获得表名为entityCode的数量
	 * @param entityCode
	 * @return Integer
	 */
	public Integer isEntityExist(String entityCode){
		return (Integer)getSqlMapClientTemplate().queryForObject(getEntityName() + ".isEntityExist", entityCode);
	}

	@Override
	public String getTableName() {
		return "dev_entity_field";
	}
}
