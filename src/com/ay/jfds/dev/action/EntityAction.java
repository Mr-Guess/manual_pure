package com.ay.jfds.dev.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.framework.util.JsonUtil;
import com.ay.jfds.dev.dto.EntityJsonDTO;
import com.ay.jfds.dev.pojo.Entity;
import com.ay.jfds.dev.pojo.EntityField;
import com.ay.jfds.dev.service.EntityFieldService;
import com.ay.jfds.dev.service.EntityService;

/**
 * Entity Action 对象
 * @author wgw
 *
 */
@SuppressWarnings("all")
public class EntityAction extends BaseAction{
	private EntityService entityService;
	private Entity entity;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String[] ids;
	private String entityType;
	private String entityCode;
	private String entityName;
	private String viewSql;
	private static Logger logger = LoggerFactory.getLogger(EntityAction.class);
	/** 表的类型，0:主表,1:子表*/
	private String tableType;
	
	/** 主表的id*/
	private String parentTableId;
	
	/**
	 * 分页获取实体
	 */
	public void pageList() {
		DataStore<Entity> dataStore = new DataStore<Entity>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || "0".equals(page)) ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || "0".equals(rows)) ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = BeanUtil.Bean2Map(entity);
		Page resultPage = entityService.pageQuery(paramMap,pageTemp);
		List<Entity> resultList = (List<Entity>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	/**
	 * 获得表结构的treegrid
	 */
	public void treeGrid(){
		List<Entity> list = this.entityService.findAll();
		if(this.entityCode!=null&&this.entityName!=null&&(
				!"".equals(entityCode)||!"".equals(entityName))){			
			Map map = new HashMap();
			map.put("entityCode", this.entityCode);
			map.put("entityName", this.entityName);
			map.put("entityType", "0");
			
			Map entityMap = getEntityMap(list);
			List<Entity> causeList = this.entityService.find(map);
			Set<Entity> rootSet = getTree(entityMap, causeList);			
			logger.info("检索成功");
			Struts2Utils.renderJson(rootSet, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
			return;
		}else{
			Set<Entity> root = getTree(list);
			Struts2Utils.renderJson(root, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
		}
	}

	
	/**
	 * 将所有表放入map
	 * @param list
	 * @return map
	 */
	private Map<String, Entity> getEntityMap(List<Entity> list){
		Map<String, Entity> map = new HashMap<String, Entity>();
		//将list中的所有entity放入map中
		Iterator<Entity> iter = list.iterator();
		while(iter.hasNext()){
			Entity entity =  iter.next();
			String key = entity.getId();
			map.put(key, entity);
		}
		return map;
	}
	/**
	 * 输入所有表的list，得出树对象
	 * @param list
	 * @return List
	 */
	private Set<Entity> getTree(List<Entity> list){
		Set<Entity> root = new TreeSet<Entity>();
		Map<String, Entity> map = new HashMap<String, Entity>();
		map = getEntityMap(list);
		Set<Entry<String, Entity>> entrySet = map.entrySet();
		Iterator<Entry<String, Entity>> entryIter = entrySet.iterator();
		while(entryIter.hasNext()){ 
			Entry<String, Entity> entry = entryIter.next();
			Entity entity = entry.getValue();
			String pid = entity.getParentId();
			if(pid!=null){
				Entity parent = map.get(pid);
				if(parent!=null){
					parent.getChildren().add(entity);
				}
			}else{
				root.add(entity);
			}
		}
		return root;
	}
	
	/**
	 * 获得所有检索表的根表
	 * @param map
	 * @param causeList
	 * @return TreeSet
	 */
	private Set<Entity> getTree(Map<String, Entity> map, List<Entity> causeList){
		Set<Entity> root = new TreeSet<Entity>();
		Iterator<Entity> iter = causeList.iterator();
		while(iter.hasNext()){
			Entity e = map.get(iter.next().getId());			
			root.add(findEntityTree(map, e));			
		}
		return root;
	}
	
	/**
	 * 获得当前表的根表（递归）
	 * @param map
	 * @param entity
	 * @return
	 */
	private Entity findEntityTree(Map<String, Entity> map, Entity entity){
		Entity parent = map.get(entity.getParentId());
		if(parent!=null){
			parent.getChildren().add(entity);
			return findEntityTree(map, parent);
		}
		return entity;
	}

	
	/**
	 * 根据id查找实体
	 * 
	 */
	public void getEntityById(){
		Entity entity=entityService.getById(id);
		Struts2Utils.renderJson(entity, EncodingHeaderUtil.HEADERENCODING);
	}
	
	/**
	 * 添加，同时更新数据库中的表或者视图
	 */
	public void addEntity(){
		if("1".equals(this.entity.getEntityType())){
			String viewCode = this.entity.getEntityCode();
			String viewName = this.entity.getEntityName();
			String viewSql = this.entity.getViewSql();
			String createSql = getViewSql(viewCode, viewSql);
			boolean isExist = this.entityService.isTrueExist(this.entity);
			if(isExist){
				operateShow(false, "视图已存在");
				return;
			}
			try {
				this.entityService.executeSql(createSql);
			} catch (Exception e) {
				operateShow(false, "sql语句出错");
				e.printStackTrace();
				return;
			}
			Entity view = this.entityService.insert(this.entity);
			addFieldByView(view);
			operateShow(true, "创建视图成功");
		}
	}
	
	/**
	 * 根据新视图的字段插入字段表
	 * @param view
	 */
	private void addFieldByView(Entity view){
		List<EntityField> eList = this.entityService.getViewField(view.getEntityCode());
		EntityFieldService efs = SpringContextHolder.getBean(EntityFieldService.class);
		for(EntityField ef :eList){
			ef.setEntityId(view.getId());
			ef.setFieldName("");
			efs.insert(ef);
		}
	}

	
	/***
	 * 根据表名和传入的sql语句生成创建视图语句
	 * @param viewCode
	 * @param sql
	 * @return
	 */
	private String getViewSql(String viewCode, String sql){
		String cSql = "create view "+viewCode+" as "+sql;
		System.out.println(cSql);
		return cSql;
		
	}
	
	/**
	 * 添加表信息，并创建基础表
	 */
	public void addTable(){
		if(tableType!=null&&tableType.equals("1")){
			this.entity.setParentId(parentTableId);
		}
		boolean isExist = entityService.isEntityExist(this.entity);	
		boolean isTrueExist = entityService.isTrueExist(this.entity);
		if(isExist||isTrueExist){
			operateShow(false, "表名重复");
			return;
		}
		
		boolean isOk = entityService.createBaseTable(this.entity);
		if(isOk){
			Entity resultEntity = entityService.insert(this.entity);
			setForeignKey();
			operateShow(true, "建表成功");			
			return;
		}else{
			operateShow(false, "建表失败");
			return;
		}
	}
	
	/**
	 * 设置外键属性,插入字段表中
	 */
	private void setForeignKey(){
		Entity enty = this.entityService.getById(this.entity.getParentId());
		if(enty!=null){
			String pCode = enty.getEntityCode()+"_id";
			EntityFieldService efs = SpringContextHolder.getBean(EntityFieldService.class);
			EntityField ef = new EntityField();
			ef.setControlType("outkey_relate");
			ef.setFieldCode(pCode);
			ef.setFieldName("foreign_Key");
			ef.setFieldType("varchar");
			ef.setEntityId(this.entity.getId());
			ef.setFieldLength("50");
			ef.setIsNull("1");
			ef.setEntityOrder("1");
			efs.insert(ef);
		}
	}

	
	/**
	 * 返回操作状态
	 * @param isOk
	 */
	private void operateShow(boolean isOk, String msg){
		OperateInfo operateInfo = new OperateInfo();
		if(isOk){
			operateInfo.setOperateMessage(msg);
			operateInfo.setOperateSuccess(true);
		}else{
			operateInfo.setOperateMessage(msg);
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
		operateInfo = null;
		json = null;
	}

	/**
	 * 更新实体，同时更新数据库中的表或者视图
	 */
	public void updateEntity(){
		Entity currEntity = this.entityService.getById(this.entity.getId());
		//判断输入的表名和当前系统的表名是否相同
		if(!currEntity.getEntityCode().equals(this.entity.getEntityCode())){
			//判断更新的表名是否存在
			boolean isExist = entityService.isEntityExist(this.entity);
			if(isExist){				
				operateShow(false, "表名重复,操作失败");
				return;
			}	
		}else{
			currEntity.setEntityName(this.entity.getEntityName());
			this.entityService.update(currEntity);
		}
		OperateInfo operateInfo=new OperateInfo();
		//获得需要更新的表对象
		Entity updateEntity=entityService.getById(entity.getId());
		//获得原表名和更新表名
		boolean isAlter = entityService.alterTableName(updateEntity.getEntityCode(), entity.getEntityCode());
		if(isAlter){
			updateEntity.setEntityCode(entity.getEntityCode());
			updateEntity.setEntityName(entity.getEntityName());
			boolean isOk = entityService.update(updateEntity);
			if(isOk){
				operateShow(true, "更新表成功");
				return;
			}else{
				entityService.alterTableName(entity.getEntityCode(), updateEntity.getEntityCode());
				operateShow(true, "更新表失败");
				return;
			}
		}else{
			operateShow(true, "更新表失败");
			return;
		}
		
	}
	 
	
	
	/**
	 * 根据id批量删除实体，同时更新数据库中的实体表或视图
	 */
	public void deleteEntity(){
		OperateInfo operateInfo=new OperateInfo();
		String deleteSql=dropSql(ids);
		if(deleteSql!=null){
			try{
				executeSql(deleteSql);
			}catch(Exception e){
				e.printStackTrace();
				operateShow(false, "删除失败");
				return;
			}		
			try{			
				entityService.delete(ids);
				deleteFieldByEntityId(ids);
				operateShow(true, "删除成功");
			}catch(Exception e){
				e.printStackTrace();
				operateShow(false, "删除失败");
			}
		}
		
	}
	
	/**
	 * 获得实体列表json
	 */
	public void getEntityJson(){
		Map map = new HashMap();
		map.put("entityType", entityType);
		List<EntityJsonDTO> list = this.entityService.getEntityJson(map);
		String entityJson = JsonUtil.list2json(list);
		Struts2Utils.renderJson(entityJson, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	
	
	/**
	 * 根据传入的viewsql获得视图名
	 * @param sql
	 * @return
	 */
	public String resolveViewSql(String sql){
		String[] sqls=sql.split(" ");
		if(sqls==null||sqls.length<3){
			return null;
		}
		List<String> list=new ArrayList<String>();
		for(int i=0;i<sqls.length;i++){
			if(sqls[i].length()!=0){
				list.add(sqls[i]);
			}
		}
		if("view".equalsIgnoreCase(list.get(1))&&"as".equalsIgnoreCase(list.get(3))){
			return list.get(2);
		
		}else{
			return null;
		}
	}
	
	
	
	/**
	 * 根据id拼接成删除实体sql语句
	 * @param ids
	 * @return string
	 */
	public String dropSql(String[] ids){
		String dropSql = null;
		if(ids!=null){
			dropSql="";
			if("1".equals(entityType)){
				for(int i=0;i<ids.length;i++){
					dropSql+="drop view ["+entityService.getById(ids[i]).getEntityCode()+"];";
				}
			}else if("0".equals(entityType)){
				for(int i=0;i<ids.length;i++){
					dropSql+="drop table ["+entityService.getById(ids[i]).getEntityCode()+"];";
				}
			}	
		}			
		return dropSql;		
	}
	
	
	/**
	 * 判断表名是否存在
	 * @param entityCode
	 * @return boolean
	 */
	public boolean isEntityExist(String entityCode){
		int cout=entityService.getCountByEntityCode(entityCode);
		if(cout>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据entityId删除属性表
	 * @param entityId
	 */
	private void deleteFieldByEntityId(String[] entityIds){
		for(int i=0;i<entityIds.length;i++){
			String entityId=entityIds[i];
			entityService.deleteFieldByEntityId(entityId);
		}		
	}
	
	/**
	 * 
	 * @param oldEntityCode
	 * @param newEntityCode
	 * @return String
	 */	
	public String alterSql(String oldEntityCode,String newEntityCode){
		String stateSql="ALTER   TABLE   "+oldEntityCode+"   RENAME   TO "+newEntityCode;
		return stateSql;
	}
	
	/**
	 * 执行传入的sql语句
	 * @param sql
	 */
	public void executeSql(String sql){
		entityService.executeSql(sql);
	}
	
	/**
	 * 根据entityId获得对应所有field
	 */
	public void getFieldByEntityId(){
		List<EntityField> list = entityService.getFieldByEntityId(id);
		String entityJson = JsonUtil.list2json(list);
		Struts2Utils.renderJson(entityJson, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	/**
	 * get、set方法
	 */
	public EntityService getEntityService() {
		return entityService;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getViewSql() {
		return viewSql;
	}

	public void setViewSql(String viewSql) {
		this.viewSql = viewSql;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getParentTableId() {
		return parentTableId;
	}

	public void setParentTableId(String parentTableId) {
		this.parentTableId = parentTableId;
	}
	
}
