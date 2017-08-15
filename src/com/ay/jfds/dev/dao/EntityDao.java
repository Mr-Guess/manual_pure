package com.ay.jfds.dev.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.dev.dto.EntityDTO;
import com.ay.jfds.dev.dto.EntityJsonDTO;
import com.ay.jfds.dev.pojo.Entity;
import com.ay.jfds.dev.pojo.EntityField;

/**
 * 表结构dao层
 * 
 * @author wuguowen
 * @datetime 2012-10-18
 * @version 1.0
 */
@SuppressWarnings("all")
public class EntityDao extends BaseDao<Entity> {

	@Override
	public String getEntityName() {
		return "entity";
	}

	/**
	 * 根据id获得表结构实体
	 * 
	 * @param id
	 * @return EntityTable
	 */
	public Entity getEntityTableById(String id) {
		return (Entity) getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".getById", id);
	}

	/**
	 * 获得所有分布式的表实体
	 * 
	 * @return List<EntityTable>
	 */
	public List<Entity> findAllEntityTable(int from, int prePageNum) {
		return (List<Entity>) getSqlMapClientTemplate().queryForList(
				getEntityName() + ".findAll", from, prePageNum);
	}
	
	/**
	 * 执行输入的sql语句
	 * @param sql
	 */
	public void executeSql(String sql){
		getSqlMapClientTemplate().update(getEntityName()+ ".statementSql",sql);
	}
	
	/**
	 * 根据查询条件获得分布式实体
	 * @param map
	 * @param from
	 * @param prePageNum
	 * @return List<Entity>
	 */
	public List<Entity> findByClause(Map map,int from,int prePageNum){
		System.out.println(map);
		return (List<Entity>) getSqlMapClientTemplate().queryForList(
				getEntityName() + ".find",map,from,prePageNum);
	}
	
	/**
	 * 获得表名为entityCode在dev_entity表中的数量
	 * @param entityCode
	 * @return Integer
	 */
	public Integer isEntityExist(String entityCode){
		return (Integer)getSqlMapClientTemplate().queryForObject(getEntityName() + ".isEntityExist", entityCode);
	}
	
	/**
	 * 根据etityId删除field
	 * @param entityId
	 */
	public void deleteFieldByEntityId(String entityId){
		getSqlMapClientTemplate().delete(getEntityName() + ".deleteFieldByEntityId", entityId);
	}
	
	/**
	 * 获得实体列表的json对象
	 * @return
	 */
	public List<EntityJsonDTO> getEntityJson(Map map){
		return (List<EntityJsonDTO>)this.getSqlMapClientTemplate().queryForList(getEntityName() + ".getEntityJson", map);
	}
	
	/**
	 * 根据entityId获得所有field
	 * @param entityId
	 * @return
	 */
	public List<EntityField> getFieldByEntityId(String entityId){
		return (List<EntityField>)getSqlMapClientTemplate().queryForList(getEntityName() + ".getFieldByEntityId", entityId);
	}
	
	/**
	 * 根据表名判断表在数据库中的真实数量
	 * @param entityCode
	 * @return
	 */
	public Integer isTrueExist(String entityCode){
		return (Integer) getSqlMapClientTemplate().queryForObject(getEntityName()+ ".isTrueExist", entityCode);
	}
	
	/**
	 * 根据视图名获得视图下所有的字段信息
	 * @param viewCode
	 * @return
	 */
	public List<EntityField> getViewField(String viewCode){
		return (List<EntityField>)this.getSqlMapClientTemplate().queryForList(getEntityName()+ ".getViewField", viewCode);
	}
	
	/**
	 * 根据条件查询出单个表对象
	 * @param map
	 * @return
	 */
	public Entity getEntityByCode(String entityCode){
		return (Entity) this.getSqlMapClientTemplate().queryForObject(getEntityName() + ".findEntityByCode", entityCode);
	}
	
	/**
	 * 根据id获得子表list
	 * @param parentId
	 * @return
	 */
	public List<Entity> getChildrenEntity(String parentId){
		return (List<Entity>) this.getSqlMapClientTemplate().queryForList(getEntityName() + ".getChildren", parentId);
	}
	
	/**
	 * 获得所有entityDTO
	 * @return
	 */
	public List<EntityDTO> getAllEntityDTO(){
		return (List<EntityDTO>) this.getSqlMapClientTemplate().queryForList(getEntityName()+ ".getEntityDTO");
	}
	
	/**
	 * 根据条件获得所有Entity
	 * @param map
	 * @return
	 */
	public List<Entity> find(Map map){
		return (List<Entity>) this.getSqlMapClientTemplate().queryForList(getEntityName() + ".find", map);
	}

	@Override
	public String getTableName() {
		return "dev_entity";
	}
}
