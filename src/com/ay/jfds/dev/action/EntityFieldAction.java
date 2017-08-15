package com.ay.jfds.dev.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.dialect.SimpleDialectServiceFactory;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.DBConnectionUtil;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.framework.util.JacksonUtil;
import com.ay.jfds.core.CoreAction;
import com.ay.jfds.dev.dao.EntityDao;
import com.ay.jfds.dev.dto.CombotreeDTO;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.pojo.DataType;
import com.ay.jfds.dev.pojo.Entity;
import com.ay.jfds.dev.pojo.EntityField;
import com.ay.jfds.dev.service.DataService;
import com.ay.jfds.dev.service.DataTypeService;
import com.ay.jfds.dev.service.EntityFieldService;
import com.ay.jfds.dev.service.EntityService;

/**
 * EntityField Action 对象
 * @author wgw
 *
 */
@SuppressWarnings("all")
public class EntityFieldAction extends BaseAction{
	private static final String[][] String = null;
	private EntityFieldService entityFieldService;
	private Entity entity;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String[] ids;
	private String entityId;
	private static Logger logger = LoggerFactory.getLogger(EntityFieldAction.class);
	/**	元数据代码，用来作为过滤条件的*/
	private String dataCode;
	
	/** 元数据类型名,用来过滤元数据类型的*/
	private String typeName;
	
	private String dataTypeId;
	
	//包含了页面对表的操作信息
	private String changesJson;
	//
	private Map<String,List<Object>> sourceEntityMap;
	
	private SimpleDialectServiceFactory dialectFactory;
	
	/**
	 * 获得实体属性传回客户端
	 */
	public void pageList(){
		DataStore<EntityField> dataStore=new DataStore<EntityField>();
		List<EntityField> resultList=entityFieldService.getAllEntityField(entityId);
		if(resultList!=null){
			dataStore.setTotal(new Long(resultList.size()));
			dataStore.setRows(resultList);
			if(dataStore.getRows()!=null){
				Struts2Utils.renderJson(dataStore, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
			}
		}
	}
	
	/**
	 * 获得所有元数据类型，以json形式返回
	 */
	public void getAllDataType(){
		DataTypeService dts = SpringContextHolder.getBean(DataTypeService.class);
		List<DataType> list = dts.findAllDataType(null);
		
		Integer index = null;		
    	for(int i=0;i<list.size();i++){
			if("控件类型".equals(list.get(i).getTypeName())){
				index = i;
			}
		}
    	if(index!=null){
    		list.remove(index);
    	}
		
		Struts2Utils.renderJson(list, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	/**
	 * 获得所有元数据类型（分页），以json形式返回
	 */
	public void getAllDataTypeCause(){

		DataStore<DataType> dataStore = new DataStore<DataType>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || "0".equals(page)) ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || "0".equals(rows)) ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);	

		Map map = new HashMap();
		map.put("typeName", this.typeName);
		
		DataTypeService dts = SpringContextHolder.getBean(DataTypeService.class);
		
		Page resultPage = dts.findAllDataType(map, pageTemp);
		List<DataType> list =(List<DataType>) resultPage.getCollection();
		
		Integer index = null;		
    	for(int i=0;i<list.size();i++){
			if("控件类型".equals(list.get(i).getTypeName())){
				index = i;
			}
		}
    	if(index!=null){
 
    		list.remove((int)index);
    	}
    	
		dataStore.setTotal((long)resultPage.getCount());
		dataStore.setRows(list);
		Struts2Utils.renderJson(dataStore, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	/**
	 * 根据元数据类型id获得所有元数据，以json list形式返回
	 */
	public void getAllDataListByType(){
		DataService ds = SpringContextHolder.getBean(DataService.class);
		DataTypeService dts = SpringContextHolder.getBean(DataTypeService.class);
		Map map = new HashMap();
		map.put("typeName", "控件类型");
		DataType dt = dts.findDataType(map);
		List<Data> list = ds.getDataByType(dt.getId());		
		Struts2Utils.renderJson(list, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	/**
	 * 根据元数据类型id获得所有元数据，以json tree形式返回
	 */
	public void getAllDataTreeByType(){
		DataService ds = SpringContextHolder.getBean(DataService.class);
		DataTypeService dts = SpringContextHolder.getBean(DataTypeService.class);
		Map map = new HashMap();
		map.put("typeName", "控件类型");
		DataType dt = dts.findDataType(map);
		List<Data> list = ds.getDataByType(dt.getId());
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("dataCode", this.dataCode );
		List pList = getCombotree(list, filterMap);
		Struts2Utils.renderJson(pList, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	/**
	 * 根据元数据生成树对象
	 * @param list
	 * @return
	 */
	public List getCombotree(List<Data> list){
		List pList = new ArrayList();
		for(Data da:list){
			if("-1".equals(da.getParentId())){
				CombotreeDTO cDto = new CombotreeDTO();				
				List chilrenList = new ArrayList();
				cDto.setId(da.getDataCode());
				cDto.setText(da.getDataName());
				for(Data da2:list){
					if(da2.getParentId().equals(da.getId())){
						CombotreeDTO cDtoc = new CombotreeDTO();
						cDtoc.setId(da2.getDataCode());
						cDtoc.setText(da2.getDataName());
						chilrenList.add(cDtoc);
					}
				}
				cDto.setChildren(chilrenList);
				pList.add(cDto);
			}
		}
		return pList;
	}
	
	/**
	 * 根据元数据和条件生成树对象
	 * @param list
	 * @param map
	 * @return
	 */
	public List getCombotree(List<Data> list, Map<String, String> map){
		List pList = new ArrayList();
		CombotreeDTO ct = new CombotreeDTO();
		ct.setId("");
		ct.setText("无");
		pList.add(ct);
		for(Data da:list){
			boolean flag ="-1".equals(da.getParentId());
			if(map.get("dataCode")!=null){
				flag = flag&&map.get("dataCode").equals(da.getDataCode());
			}
			if(flag){
				CombotreeDTO cDto = new CombotreeDTO();				
				List chilrenList = new ArrayList();
				cDto.setId(da.getDataCode());
				cDto.setText(da.getDataName());
				for(Data da2:list){
					if(da2.getParentId().equals(da.getId())){
						CombotreeDTO cDtoc = new CombotreeDTO();
						cDtoc.setId(da2.getDataCode());
						cDtoc.setText(da2.getDataName());
						chilrenList.add(cDtoc);
					}
				}
				cDto.setChildren(chilrenList);
				pList.add(cDto);
			}
		}		
		return pList;
	}
	
	/**
	 * 判断表名是否存在
	 */
	public boolean isEntityExist(String entityCode){
		int cout=entityFieldService.getCountByEntityCode(entityCode);
		if(cout>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 删除字段
	 * @param list
	 * @return
	 */
	private boolean deleteField(List<EntityField> list){
		if(list!=null&&list.size()>0){
			String foreignKey = getForeignKey();
			StringBuffer sbSql = new StringBuffer();
			for(EntityField ef :list){
				String eid = ef.getId();
				sbSql.append("alter table "+this.entity.getEntityCode() +" drop column ["+ef.getFieldCode()+"];");
			}
			String sql = sbSql.toString();
			logger.info("delSql:"+ sql);
			boolean flag = executeSqlByJDBC(sql);
			if(flag){
				for(EntityField ef:list){
					entityFieldService.delete(ef.getId());
				}
			}
			return flag;
		}		
		return true;
	}
	
	
	/**
	 * 更新字段
	 * @param list
	 * @return
	 */
	private boolean updateField(List<EntityField> list){
		if(list!=null&&list.size()>0){
			StringBuffer sbSql = new StringBuffer();
			String foreignKey = getForeignKey();
			for(EntityField ef:list){
				EntityField oldEf = entityFieldService.getById(ef.getId());
				String oldField = oldEf.getFieldCode();
				String newField = ef.getFieldCode();
				StringBuffer sql = new StringBuffer();
				if(!oldField.equals(newField)){
					sql.append("exec sp_rename '"+this.entity.getEntityCode()+".["+oldField+"]','"+newField+"','column';");
				}
				sql.append("alter table "+this.entity.getEntityCode()+" ");
				sql.append("alter column "+ef.getFieldCode()+" "+ef.getFieldType());
				if("varchar".equalsIgnoreCase(ef.getFieldType())){
					if(ef.getFieldLength()==null||"".equals(ef.getFieldLength())||"0".equals(ef.getFieldLength())){
						sql.append(" (32) ");
						ef.setFieldLength("32");
					}else{
						sql.append(" ("+ef.getFieldLength()+")");
					}
				}else{
					ef.setFieldLength(null);
				}
				String defaultValue = ef.getDefaultValue();
				if(defaultValue!=null&&!"".equals(defaultValue)){
					sql.append(" default '"+defaultValue+"'");
				}
				if("0".equals(ef.getIsNull())){
					sql.append(" not null");
				}
				sql.append(";");
				sbSql.append(sql.toString());
			}
//			String upSql = getDropFieldSql(foreignKey) + sbSql.append(getAddFieldSql(foreignKey));
			
			logger.info("updSql:"+sbSql);
			boolean flag = executeSqlByJDBC(sbSql.toString());
//			boolean flag = true;
			if(flag){
				for(EntityField ef:list){
					entityFieldService.update(ef);
				}
			}
			return flag;
		}
		return true;
	}
	
	/**
	 * 添加字段
	 * @param list
	 * @return
	 */
	private boolean insertField(List<EntityField> list){
		if(list!=null&&list.size()>0){
			StringBuffer sbSql = new StringBuffer();
			String foreignKey = getForeignKey();
			for(EntityField ef:list){
				StringBuffer sql = new StringBuffer();
				sql.append("alter table "+this.entity.getEntityCode()+" ");
				sql.append("add "+ef.getFieldCode()+" "+ef.getFieldType());
				if("varchar".equalsIgnoreCase(ef.getFieldType())){
					if(ef.getFieldLength()==null||"".equals(ef.getFieldLength())||"0".equals(ef.getFieldLength())){
						sql.append(" (20) ");
						ef.setFieldLength("20");
					}else{
						sql.append(" ("+ef.getFieldLength()+")");
					}
				}else{
					ef.setFieldLength(null);
				}
				String defaultValue = ef.getDefaultValue();
				if(defaultValue!=null&&!"".equals(defaultValue)){
					sql.append(" default ('"+defaultValue+"') ");
				}
				if("0".equals(ef.getIsNull())){
					sql.append(" not null");
				}
				sql.append(";");
				sbSql.append(sql.toString());
			}
			String addSql = sbSql.toString();
			logger.info("addSql:"+addSql);
			boolean flag = executeSqlByJDBC(addSql);
			if(flag){
				for(EntityField ef:list){
					ef.setEntityId(this.entity.getId());
					entityFieldService.insert(ef);
				}
			}
			return flag;
		}		
		return true;
	}
	
	/**
	 * 用jdbc来执行sql语句
	 * @param sql
	 * @return
	 */
	private boolean executeSqlByJDBC(String sql){
		Connection conn = null;
		Statement statement = null;
		try {
			conn = DBConnectionUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			statement = conn.createStatement();
			statement.execute(sql);
			conn.commit();
			DBConnectionUtil.getInstance().close(conn, statement);
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				DBConnectionUtil.getInstance().close(conn, statement);
				return false;
			}
			e.printStackTrace();
			DBConnectionUtil.getInstance().close(conn, statement);
			return false;
		} finally {
			DBConnectionUtil.getInstance().close(conn, statement);
		}
		return true;
	}
	
	/**
	 * 更新表结构，数据保存
	 * @param list
	 * @return
	 */
	private boolean  updateTableConstruct(List<EntityField> list){
		String tempTableName = randomName();
		String intoSql = createTempTableSql(list, tempTableName);
		EntityDao ed = SpringContextHolder.getBean(EntityDao.class);
		int count = ed.isTrueExist(tempTableName);
		if(count>0){
			intoSql = "drop table ["+tempTableName + "];" + intoSql;
		}
		intoSql += "drop table ["+this.entity.getEntityCode() + "];";
		String alterSql = "exec sp_rename '"+tempTableName+"', '"+this.entity.getEntityCode()+"';"+
					"alter table ["+this.entity.getEntityCode()+"] add constraint [pk_"+this.entity.getEntityCode()+"_id] primary key(id);"+
					"alter table ["+this.entity.getEntityCode()+"] add constraint [df_"+this.entity.getEntityCode()+"_status] default (1) for [status];";
		intoSql += alterSql;
		logger.info(intoSql);
		return executeSqlByJDBC(intoSql);
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
	 * 生成复制表语句
	 * @param list
	 * @param tableName
	 * @return
	 */
	private String createTempTableSql(List<EntityField> list, String tableName){
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select * into "+tableName+" from (select id , ");
		for(int i=0;i<list.size();i++){
			EntityField ef = list.get(i);
			String currentField = ef.getFieldCode();			
			EntityField oldEf = entityFieldService.getById(ef.getId());
			if(oldEf==null){
				selectSql.append(currentField + ", ");
				continue;
			}
			String oldField = oldEf.getFieldCode();
			if(!oldField.equals(currentField)){
				selectSql.append(oldField+" as "+currentField + ", ");
			}else{
				selectSql.append(currentField + ", ");
			}
		}
		selectSql.append("status, created, create_time, updated, update_time from ["+this.entity.getEntityCode()+"]) upd;");
		return selectSql.toString();
	}
	
	/**
	 * 根据map获得entityField对象
	 * @param map
	 * @return EntityField (字段属性实体对象)
	 */
	private EntityField map2Field(Map map){
		EntityField ef = new EntityField();
		ef.setId(map.get("id")==null?"":map.get("id").toString());
		ef.setStatus(map.get("status")==null?"":map.get("status").toString());
		ef.setCreated(map.get("created")==null?"":map.get("created").toString());
		if(map.get("createTime")!=null){
			Date createTime = new Date((Long) map.get("createTime"));
			ef.setCreateTime(createTime);
		}		
		ef.setFieldCode(map.get("fieldCode")==null?"":map.get("fieldCode").toString());
		ef.setFieldName(map.get("fieldName")==null?"":map.get("fieldName").toString());
		ef.setEntityOrder(map.get("entityOrder")==null?null:map.get("entityOrder").toString());
		ef.setDefaultValue(map.get("defaultValue")==null?"":map.get("defaultValue").toString());
		ef.setFieldType(map.get("fieldType")==null?"":map.get("fieldType").toString());
		ef.setFieldLength(map.get("fieldLength")==null?"":map.get("fieldLength").toString());
		ef.setIsNull(map.get("isNull")==null?"":map.get("isNull").toString());
		ef.setControlType(map.get("controlType")==null?"":map.get("controlType").toString());
		ef.setDataTypeId(map.get("dataTypeId")==null?"":map.get("dataTypeId").toString());
		ef.setEntityId(map.get("entityId")==null?"":map.get("entityId").toString());
		if(map.get("updateTime")!=null){
			Date updateTime = new Date((Long) map.get("updateTime"));
			ef.setCreateTime(updateTime);
		}
		ef.setUpdated(map.get("updated")==null?"":map.get("updated").toString());
		return ef;
	}
	
	/**
	 * 获得List< EntityField >对象
	 * @param mList
	 * @return
	 */
	private List<EntityField> toFieldList(List<Map> mList){		
		List<EntityField> list = new LinkedList<EntityField>();
		for(Map map :mList){
			EntityField ef = map2Field(map);
			list.add(ef);
		}
		return list;
	}
	
	/**
	 * 生成临时表名
	 * @return
	 */
	private String randomName(){
		String name = this.entity.getEntityCode();		
		Random random =new Random();
		for(int i=0;i<6;i++){
			int ren = random.nextInt(26)+65;
			char ch = (char) ren;
			name += ch;
		}
		name += "$_$";
		return name;
	}
	
	/**
	 * 根据当前的表，获得父表的id，再根据父表id获得外键字段名
	 * @return String (foreignKey:外键字段名)
	 */
	private String getForeignKey(){
		EntityService es = SpringContextHolder.getBean(EntityService.class);
		Entity ety = es.getById(this.entity.getId());
		Entity pety = es.getById(ety.getParentId());
		String foreignKey = null;
		if(pety != null){
			foreignKey = pety.getEntityCode()+"_id";
		}
		return foreignKey;
	}
	
	
	/**
	 * 更新表,客户端请求的方法(jdbc方法)
	 */
	public void updateTable(){
		Map<String, List<Map>> changes = null;
		changes = JacksonUtil.jsonToJava(changesJson, Map.class);
		String foreignKey = getForeignKey();
		
		List<Map> deleted = changes.get("deleted");
		List<Map> updated = changes.get("updated");
		List<Map> inserted = changes.get("inserted");
		List<Map> rows = changes.get("rows");
		boolean flag = true;
		if(inserted!=null&&inserted.size()!=0){
			flag = insertField(toFieldList(inserted));
			if(!flag){
				operateShow(false, "更新失败");
				return;
			}
		}
		if(deleted!=null&&deleted.size()!=0){
			flag = deleteField(toFieldList(deleted));
			if(!flag){
				operateShow(false, "更新失败");
				return;
			}
		}			
		if(updated!=null&&updated.size()!=0){
			flag = updateField(toFieldList(updated));
			if(!flag){
				operateShow(false, "更新失败");
				return;
			}
		}			
		if(rows!=null&&rows.size()!=0){			
			List<EntityField> rowList = toFieldList(rows);
			flag = updateTableConstruct(rowList);
			if(!flag){
				operateShow(false, "更新失败");
				return;
			}else{
				for(EntityField ef:rowList){
					entityFieldService.update(ef);
				}
			}
		}		
		operateShow(true, "更新成功");
		return;
	}
	
	/**
	 * 获得删除基础字段语句
	 * @param foreignKey
	 * @return String
	 */
	private String getDropFieldSql(String foreignKey){
		String tn = this.entity.getEntityCode();
		String constraint = "df_"+tn+"_status";
		String dropSql = "alter table ["+tn+"] drop constraint ["+constraint+"];"+
				"alter table ["+tn+"] drop column [status];" +
				"alter table ["+tn+"] drop column [created];" +
				"alter table ["+tn+"] drop column [create_time];" +
				"alter table ["+tn+"] drop column [updated];"+
				"alter table ["+tn+"] drop column [update_time];";
		if(foreignKey!=null&&!"".equals(foreignKey)){
			dropSql = "alter table ["+tn+"] drop column ["+foreignKey+"];" + dropSql;
		}
		return dropSql;
	}
	
	/**
	 * 获得添加基础字段语句
	 * @param foreignKey
	 * @return String
	 */
	private String getAddFieldSql(String foreignKey){
		String tn = this.entity.getEntityCode();
		String constraint = "df_"+tn+"_status";
		String creSql = "alter table ["+tn+"] add  [status] char(1)  null;"+
				"ALTER TABLE ["+tn+"] ADD CONSTRAINT ["+constraint+"] DEFAULT (1) FOR [status];"+
				"alter table ["+tn+"] add  [created] varchar(32) null;"+
				"alter table ["+tn+"] add  [create_time] datetime null;"+
				"alter table ["+tn+"] add  [updated] varchar(32) null;"+
				"alter table ["+tn+"] add  [update_time] datetime null;";
		if(foreignKey!=null&&!"".equals(foreignKey)){
			creSql = "alter table ["+tn+"] add  ["+foreignKey+"] varchar(50) null;" + creSql;
		}
		return creSql;
	}
	
	
	/**
	 * 删除表基本字段
	 */
	private void dropBaseField(String foreignKey){
		dialectFactory.alterTableColumn();
		String tn = this.entity.getEntityCode();
		String constraint = "df_"+tn+"_status";
		String dropSql = "alter table ["+tn+"] drop constraint ["+constraint+"];"+
				"alter table ["+tn+"] drop column [status];" +
				"alter table ["+tn+"] drop column [created];" +
				"alter table ["+tn+"] drop column [create_time];" +
				"alter table ["+tn+"] drop column [updated];"+
				"alter table ["+tn+"] drop column [update_time];";
		if(foreignKey!=null&&!"".equals(foreignKey)){
			dropSql = "alter table ["+tn+"] drop column ["+foreignKey+"];" + dropSql;
		}
		System.err.println(dropSql);
		this.entityFieldService.createTable(dropSql);
	}
	
	/**
	 * 添加表基本字段
	 */
	private void addBaseField(String foreignKey){
		String tn = this.entity.getEntityCode();
		String constraint = "df_"+tn+"_status";
		String creSql = "alter table ["+tn+"] add  [status] char(1)  null;"+
				"ALTER TABLE ["+tn+"] ADD CONSTRAINT ["+constraint+"] DEFAULT (1) FOR [status];"+
				"alter table ["+tn+"] add  [created] varchar(32) null;"+
				"alter table ["+tn+"] add  [create_time] datetime null;"+
				"alter table ["+tn+"] add  [updated] varchar(32) null;"+
				"alter table ["+tn+"] add  [update_time] datetime null;";
		if(foreignKey!=null&&!"".equals(foreignKey)){
			creSql = "alter table ["+tn+"] add  ["+foreignKey+"] varchar(50) null;" + creSql;
		}
		System.err.println(creSql);
		this.entityFieldService.createTable(creSql);
	}
	
	/**
	 * 删表语句
	 * @param tableName
	 * @return
	 */
	private String deleteSql(String tableName){
		String deleteSql="drop table "+tableName;
		return deleteSql;
	}

	
	/**
	 *	插入实体
	 * @param entity
	 * @return 插入后的实体
	 */
	private Entity addEntity(Entity entity){
		return entityFieldService.addEntity(entity);
	}
	
	/**
	 * 根据id删除实体
	 * @param entityId
	 */
	private void deleteEntity(String newEntityId){
//		System.out.println("delete table:"+newEntityId);
		entityFieldService.deleteEntity(newEntityId);
	}

	
	/* set,get方法*/
	public EntityFieldService getEntityFieldService() {
		return entityFieldService;
	}

	public void setEntityFieldService(EntityFieldService entityFieldService) {
		this.entityFieldService = entityFieldService;
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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getChangesJson() {
		return changesJson;
	}

	public void setChangesJson(String changesJson) {
		this.changesJson = changesJson;
	}

	public String getDataTypeId() {
		return dataTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
	}

	public Map<String, List<Object>> getSourceEntityMap() {
		return sourceEntityMap;
	}

	public void setSourceEntityMap(Map<String, List<Object>> sourceEntityMap) {
		this.sourceEntityMap = sourceEntityMap;
	}

	public SimpleDialectServiceFactory getDialectFactory() {
		return dialectFactory;
	}

	public void setDialectFactory(SimpleDialectServiceFactory dialectFactory) {
		this.dialectFactory = dialectFactory;
	}
	
}
