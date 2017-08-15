package com.ay.jfds.dev.service;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.service.BaseService;
import com.ay.jfds.dev.dao.EntityDao;
import com.ay.jfds.dev.dto.EntityDTO;
import com.ay.jfds.dev.dto.EntityJsonDTO;
import com.ay.jfds.dev.pojo.Entity;
import com.ay.jfds.dev.pojo.EntityField;

/**
 * 表结构service层
 * 
 * @author wuguowen
 * @datetime 2012-10-18
 * @version 1.0
 */
public class EntityService extends BaseService<Entity, EntityDao> {

	/**
	 * 获得所有的定义表
	 * 
	 * @param page
	 * @return Page
	 */
	public Page pageAllEntity(Page page) {
		page.setCount(this.count(null));
		List<Entity> list = dao.findAllEntityTable(page.getFrom(),
				page.getRecPerPage());
		page.setCollection(list);
		return page;
	}
	/**
	 * 根据查询条件获得实体
	 * @param map
	 * @param page
	 * @return Page
	 */
	public Page pageAllEntity(Map map,Page page){
		page.setCount(this.count(map));
		List<Entity> list = dao.findByClause(map,page.getFrom(),
				page.getRecPerPage());
		page.setCollection(list);
		return page;
	}

	/**
	 * 执行传入的sql语句
	 * @param sql
	 */
	public void executeSql(String sql){
		dao.executeSql(sql);
	}

	/**
	 * 获得表名为entityCode的表的数量
	 * @param entityCode
	 * @return Integer
	 */
	public Integer getCountByEntityCode(String entityCode){
		return dao.isEntityExist(entityCode);
	}
	
	/**
	 * 根据entityId删除属性表
	 * @param entityId
	 */
	public void deleteFieldByEntityId(String entityId){
		dao.deleteFieldByEntityId(entityId);
	}
	
	/**
	 * 根据entityId获得所有field
	 * @param entityId
	 * @return List
	 */
	public List<EntityField> getFieldByEntityId(String entityId){
		return dao.getFieldByEntityId(entityId);
	}
	
	/**
	 * 获得实体列表json
	 * @return
	 */
	public List<EntityJsonDTO> getEntityJson(Map map){
		return dao.getEntityJson(map);
	}
	
	/**
	 * 根据新表对象创建基础表
	 * @param entity(表对象实体)
	 */
	public boolean createBaseTable(Entity entity) {
		String eCode = entity.getEntityCode();
		//获得主表
		Entity pEntity = this.getById(entity.getParentId());
		//主表名
		String pCode = null;
		if(pEntity!=null){
			pCode = pEntity.getEntityCode();
		}
		String constraint = "df_"+eCode+"_status";
		if(eCode!=null&&!"".equals(eCode)){
			String cSql = "create table "+eCode+" (id varchar(50) primary key, ";
			//判断主表名是否为空
			if(pCode!=null&&!"".equals(pCode)){
				cSql = cSql +"["+pCode+"_id] varchar(50) null,";
			}
			cSql += "status char(1) null, created varchar(32) null, " +
					"create_time datetime null, updated varchar(32) null, " +
					"update_time datetime null);"+			
					"alter table ["+eCode+"] add constraint ["+constraint+"] default (1) for [status]";
			dao.executeSql(cSql);
			return true;
		}else{
			return false;
		}		
	}
	
	/**
	 * 根据实体名称判断是否存在
	 * @param entityName
	 * @return true:存在 ； false:不存在
	 */
	public boolean isEntityExist(String entityCode){
		int count = this.dao.isEntityExist(entityCode);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据实体对象判断是否存在
	 * @param entityName
	 * @return true:存在 ； false:不存在
	 */
	public boolean isEntityExist(Entity entity){
		String code = entity.getEntityCode();
		return isEntityExist(code);
	}
	
	/**
	 * 根据实体名称判断是否真实存在于数据库中
	 * @param entityName
	 * @return true:存在 ； false:不存在
	 */
	public boolean isTrueExist(String entityCode){
		int count = this.dao.isTrueExist(entityCode);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据实体对象判断是否真实存在于数据库中
	 * @param entityName
	 * @return true:存在 ； false:不存在
	 */
	public boolean isTrueExist(Entity entity){
		String code = entity.getEntityCode();
		return isTrueExist(code);
	}
	

	/**
	 * 修改表名
	 * @param oldCode 现表名
	 * @param newCode 新表名
	 * @return true or false
	 */
	public boolean alterTableName(String oldCode, String newCode){
		if(oldCode!=null&&!"".equals(oldCode)&&newCode!=null&&!"".equals(newCode)){
			String alterSql = "sp_rename '"+oldCode+"' , '"+newCode+"'";
			this.dao.executeSql(alterSql);
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 根据视图名获得视图下所有的字段信息
	 * @param viewCode
	 * @return
	 */
	public List<EntityField> getViewField(String viewCode){
		return this.dao.getViewField(viewCode);
	}
	
	/**
	 * 找出表名相等的表对象
	 * @param entityCode
	 * @return
	 */
	public Entity getEntityByCode(String entityCode){
		return this.dao.getEntityByCode(entityCode);
	}
	
	/**
	 * 根据id获得子表的列表
	 * @param parentId
	 * @return
	 */
	public List<Entity> getChildrenList(String parentId){
		return this.dao.getChildrenEntity(parentId);
	}
	
	/**
	 * 获得表对象list，以entityDTO形式
	 * @return
	 */
	public List<EntityDTO> getEntityDTOList(){
		return this.dao.getAllEntityDTO();
	}
	
	/**
	 * 根据条件获得所有Entity
	 * @param map
	 * @return
	 */
	public List<Entity> find(Map map){
		return this.dao.find(map);
	}
}
