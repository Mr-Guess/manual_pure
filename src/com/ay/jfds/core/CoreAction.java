package com.ay.jfds.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.utils.DBConnectionUtil;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.exception.POIException;
import com.ay.framework.exception.SystemException;
import com.ay.framework.util.DBUtil;
import com.ay.framework.util.StringUtil;
import com.ay.jfds.dev.dto.FormDTO;
import com.ay.jfds.dev.dto.FormFieldDTO;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.pojo.DevForm;
import com.ay.jfds.dev.pojo.MenuOpt;
import com.ay.jfds.dev.service.DataService;
import com.ay.jfds.dev.service.FormService;
import com.ay.jfds.dev.service.MenuOptService;

/**
 * 页面jsp自动生成
 * 
 * @author zxy
 *
 */
@SuppressWarnings("all")
public class CoreAction extends BaseAction {
	/** 表的id */
	private String formId;
	
	/** 菜单的Id */
	private String menuId;
	
	/** form service 对象 */
	private FormService formService;
	private String id;
	private int page;
	private int rows;
	private String sort;
	private String order;
	private String searchMap;
	private String ids;
	private DataService dataService;
	
	private static Logger logger = LoggerFactory.getLogger(CoreAction.class);
	private MenuOptService menuOptService;
	private List list;
	private int totalNumber;
	private static final String CACHE_NAME = "coreCache";
	private Cache cache;
	private CacheManager cacheManager;
	private String parentTableParam;
	
	
	private String batchSql;
	private String filterParameterNames;
	private String filterParameterValues;
	private String childTableParam;
	
	/**
	 * 转向页面的初始工作
	 */
	@Override
	public String execute() {
		if (!SecurityUtils.getSubject().isPermitted(menuId + ":baseView")) {
			return "noPermission";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		Object cache4ListDTOs = getCacheObjByKey(menuId);
		List<FormFieldDTO> listDTOs = new ArrayList<FormFieldDTO>();
		List<FormFieldDTO> listTemp = null;
		if (null == cache4ListDTOs) {
			listTemp = formService.getFormField(menuId);
			putCacheObj(menuId, listTemp);
			logger.info("从数据库得到的ListDTO对象{}", listTemp);
		} else {
			listTemp = (List<FormFieldDTO>) cache4ListDTOs;
			logger.info("从缓存中得到ListDTO对象{}", listTemp);
		}
		for (FormFieldDTO formFieldDTO : listTemp) {
			listDTOs.add(formFieldDTO);
		}
		String tableName = formService.getEntityCode(menuId);
		Map<String, String> parentMap = formService.getParentInfo(tableName);
		String parentCode = null;
		boolean realted = parentMap != null && parentMap.get("isRelation").equals("1") ? true : false;
		if (realted) {
			parentCode = parentMap.get("parentCode");
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("menuId", menuId);
		
		// 这里的数据量不是太大，而且不会频繁的进行数据库的查询工作，故：不再向缓存里加入数据了
		List<MenuOpt> menuOptList = menuOptService.query(map);
		Collections.sort(listDTOs);
		String viewName = null;
		viewName = listDTOs.get(0).getViewName();
		String formName = formService.getFormName(menuId);
		for (FormFieldDTO dto : listDTOs) {
			String fieldCode = dto.getFieldCode();
			// 如果存在公共的属性那么就将其remove掉
			if ("id".equals(fieldCode) || "create_time".equals(fieldCode) 
					|| "created".equals(fieldCode) || "update_time".equals(fieldCode) 
					|| "updated".equals(fieldCode)) {
				listDTOs.remove(dto);
			}
		}
		for (FormFieldDTO dto : listDTOs) {
			if (dto.getEditType() != null && !dto.getEditType().equals("2")) {
				if (dto.getControlType() != null && dto.getControlType().equals("onecheckboxtype")) {
					List<Data> list = dataService.getDataByType(dto.getDataTypeId());
					StringBuilder radioString = new StringBuilder();
					for (Data data : list) {
						radioString.append(data.getDataName()).append("<input type=\"radio\" name=\"" + dto.getFieldCode() + "\" value=\"" + data.getId() +  "\" />");
					}
					request.setAttribute(dto.getFieldCode(), radioString.toString());
				} else if (dto.getControlType() != null && dto.getControlType().equals("multicheckboxtype")) {
					List<Data> list = dataService.getDataByType(dto.getDataTypeId());
					StringBuilder checkboxString = new StringBuilder();
					for (Data data : list) {
						checkboxString.append("<input type=\"checkbox\" value=\"").append(data.getId()).append("\" title=\"").append(data.getDataName()).append("\" />");
					}
					request.setAttribute(dto.getFieldCode(), checkboxString.toString());
				}
			}
		}
		List<String> listChild = formService.getChildrenCode(tableName);
		List<ChildListDTO> listChildDTO = new ArrayList<ChildListDTO>();
		/*
		 * 对子表进行遍历得到子表的信息
		 * 生成ChildListDTO，以便进行页面对子表的Grid的展现操作
		 */
		if (listChild != null) {
			for (String listChildTableName : listChild) {
				List<FormFieldDTO> formDTOList = formService.getFormFieldByCode(listChildTableName);
				ChildListDTO dtoTemp = new ChildListDTO();
				dtoTemp.setFormDTO(formDTOList);
				dtoTemp.setTableName(listChildTableName);
				listChildDTO.add(dtoTemp);
			}
		}
		request.setAttribute("childList", listChildDTO);
		request.setAttribute("menuOptList", menuOptList);
		request.setAttribute("listDTO", listDTOs);
		request.setAttribute("menuId", menuId);
		request.setAttribute("formName", formName);
		request.setAttribute("parentName", parentCode);
		for (MenuOpt menuOpt : menuOptList) {
			if(menuOpt.getOptCode().equals("baseview")){
				if(menuOpt.getDisplayType()!=null && menuOpt.getDisplayType().equals("1") 
						&& menuOpt.getDesignPageUrl()!=null && !menuOpt.getDesignPageUrl().isEmpty()){
					freemarkerPath = menuOpt.getDesignPageUrl().replaceAll("\\\\", "/");
					pageList();
					return "freemarker";
				}
				break;
			}
		}
		return "corePage";
	}
	
	/**
	 * 保存方法
	 */
	public void save() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Object cache4ListDTOs = getCacheObjByKey(menuId);
		List<FormFieldDTO> listDTOs = null;
		if (null == cache4ListDTOs) {
			listDTOs = formService.getFormField(menuId);
			putCacheObj(menuId, listDTOs);
			logger.info("从数据库得到的ListDTO对象{}", listDTOs);
		} else {
			listDTOs = (List<FormFieldDTO>) cache4ListDTOs;
			logger.info("从缓存得到的ListDTO对象{}", listDTOs);
		}
		String tableName = listDTOs.get(0).getEntityCode();
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("insert into ").append(tableName).append("(id, create_time, created, status");
		Map<String, Object> insertParam = new HashMap<String, Object>();
		for (FormFieldDTO dto : listDTOs) {
			insertSql.append(", ").append(dto.getFieldCode());
			insertParam.put(dto.getFieldCode(), request.getParameter(dto.getFieldCode()));
		}
		// 对insert 的sql 语句进行封装操作
		insertSql.append(") values(?, ?, ?, ?");
		for (int i=1; i<=listDTOs.size(); i++) {
			insertSql.append(", ?");
		}
		insertSql.append(")");
		// log发出的insert语句
		logger.info(insertSql.toString());
		Connection connection = null;
		PreparedStatement statement = null;
		OperateInfo operateInfo = new OperateInfo();
		try {
			connection = DBConnectionUtil.getInstance().getConnection();
			statement = connection.prepareStatement(insertSql.toString());
			/*for (Iterator<Map.Entry<String, Object>> iterator=insertParam.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, Object> entry = iterator.next();
				
			}*/
			String uuid = StringUtil.getUUID();
			statement.setObject(1, uuid);
			String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			statement.setObject(2, createTime);
			statement.setObject(3, SecurityUtils.getSubject().getSession().getAttribute("user_id"));
			statement.setObject(4, 1);
			int flag = 5;
			for (FormFieldDTO dto : listDTOs) {
				statement.setObject(flag, insertParam.get(dto.getFieldCode()));
				flag++;
			}
			boolean result = statement.execute();
			if (!result) {
				operateInfo.setOperateMessage("添加成功");
				operateInfo.setOperateSuccess(true);
			}
		} catch (SQLException e) {
			logger.error("{} 获得Connection 对象时失败:{}", this, e);
			operateInfo.setOperateMessage("添加失败");
			operateInfo.setOperateSuccess(false);
		} finally {
			DBConnectionUtil.getInstance().close(connection, statement);
		}
		String jsonObj = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(jsonObj);
	}
	
	/**
	 * 根据多个id进行删除操作
	 */
	public void deleteByIds() {
		String tableName = formService.getEntityCode(menuId);
		StringBuilder deleteSql = new StringBuilder();
		deleteSql.append("update ").append(tableName).append(" set status=0 where id in(");
		OperateInfo operateInfo = new OperateInfo();
		Connection connection = null;
		Statement statement = null;
		String idsParam[] = ids.split("[,]");
		for (String temp : idsParam) {
			deleteSql.append("'").append(temp).append("',");
		}
		deleteSql.deleteCharAt(deleteSql.lastIndexOf(","));
		deleteSql.append(")");
		Log.info(deleteSql.toString());
		try {
			connection = DBConnectionUtil.getInstance().getConnection();
			statement = connection.createStatement();
			boolean result = statement.executeUpdate(deleteSql.toString()) > 0 ? true : false;
			if (result) {
				operateInfo.setOperateMessage("删除成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("删除失败");
				operateInfo.setOperateSuccess(false);
			}
		} catch (Exception e) {
			Log.error("在进行删除数据时发生错误:{}", e);
			operateInfo.setOperateMessage("删除成功");
			operateInfo.setOperateSuccess(true);
		} finally {
			DBConnectionUtil.getInstance().close(connection, statement);
		}
		String jsonObj = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(jsonObj);
	}
	
	/**
	 * 删除方法
	 */
	public void delete() {
		String tableName = formService.getEntityCode(menuId);
		StringBuilder deleteSql = new StringBuilder();
		// 这里为假删除
		deleteSql.append("update ").append(tableName).append(" set status=0 where id=?");
		OperateInfo operateInfo = new OperateInfo();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		// log发出的删除语句(假删除)
		logger.info(deleteSql.toString());
		try {
			connection = DBConnectionUtil.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(deleteSql.toString());
			boolean result = preparedStatement.executeUpdate() > 0 ? true : false;
			if (!result) {
				operateInfo.setOperateMessage("删除成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("删除失败");
				operateInfo.setOperateSuccess(false);
			}
		} catch (Exception e) {
			logger.error("{} 获得Connection 对象时失败:{}", this, e);
			operateInfo.setOperateMessage("删除失败");
			operateInfo.setOperateSuccess(false);
		} finally {
			DBConnectionUtil.getInstance().close(connection, preparedStatement);
		}
		String jsonObj = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(jsonObj);
	}
	
	/**
	 * 更新方法
	 */
	public void update() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String tableName = formService.getEntityCode(menuId);
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update ").append(tableName);
		updateSql.append(" set update_time=?, updated=?");
		
		Object cache4ListDTOs = getCacheObjByKey(menuId);
		List<FormFieldDTO> listDTOs = null;
		if (null == cache4ListDTOs) {
			listDTOs = formService.getFormField(menuId);
			putCacheObj(menuId, listDTOs);
			logger.info("从数据库得到的ListDTO对象{}", listDTOs);
		} else {
			listDTOs = (List<FormFieldDTO>) cache4ListDTOs;
			logger.info("从缓存得到的ListDTO对象{}", listDTOs);
		}
		Map<String, Object> insertParam = new HashMap<String, Object>();
		for (FormFieldDTO dto : listDTOs) {
			String fieldCode = dto.getFieldCode();
			if ("id".equals(fieldCode) || "create_time".equals(fieldCode) 
					|| "created".equals(fieldCode) || "update_time".equals(fieldCode) 
					|| "updated".equals(fieldCode)) {
				listDTOs.remove(dto);
			}
		}
		Connection connection = null;
		PreparedStatement statement = null;
		OperateInfo operateInfo = new OperateInfo();
		
		// 拼装update 的sql 语句
		for (FormFieldDTO dto : listDTOs) {
			updateSql.append(", ").append(dto.getFieldCode()).append("=?");
			insertParam.put(dto.getFieldCode(), request.getParameter(dto.getFieldCode()));
		}
		updateSql.append(" where id=?");
		// log发出的更新语句
		logger.info(updateSql.toString());
		try {
			connection = DBConnectionUtil.getInstance().getConnection();
			statement = connection.prepareStatement(updateSql.toString());
			statement.setObject(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			statement.setObject(2, SecurityUtils.getSubject().getSession().getAttribute("user_id"));
			int flag = 3;
			for (FormFieldDTO dto : listDTOs) {
				Object obj = request.getParameter(dto.getFieldCode());
				if (obj == null) {
					obj = "";
				}
				statement.setObject(flag, obj);
				flag++;
			}
			statement.setObject(flag, id);
			boolean result = statement.executeUpdate() > 0 ? true : false;
			if (result) {
				operateInfo.setOperateMessage("更新成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("更新失败");
				operateInfo.setOperateSuccess(false);
			}
		} catch (Exception e) {
			logger.error("更新数据的时候发生错误:{}", e);
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		} finally {
			DBConnectionUtil.getInstance().close(connection, statement);
		}
		String jsonObj = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(jsonObj);
		
		
	}
	
	/**
	 * 进行分页算法的实现
	 */
	public void pageList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String tableName = formService.getEntityCode(menuId);
		page = (page == 0) ? 1 : page;
		rows = (rows == 0) ? 10 : rows;
		int intPage = page;
		int number = rows;
		int start = (intPage - 1) * number;
		
		// 获取过滤Map
		Map<String,String> filterMap = getFilterParameterMap(filterParameterNames, filterParameterValues);

		// 得到FormFieldDTO对象
		Object cache4ListDTOs = getCacheObjByKey(menuId);
		List<FormFieldDTO> listDTOs = null;
		if (null == cache4ListDTOs) {
			listDTOs = formService.getFormField(menuId);
			putCacheObj(menuId, listDTOs);
			logger.info("从数据库得到的ListDTO对象{}", listDTOs);
		} else {
			listDTOs = (List<FormFieldDTO>) cache4ListDTOs;
			logger.info("从缓存得到的ListDTO对象{}", listDTOs);
		}

		// 得到父表的信息
		Map<String, String> parentMap = formService.getParentInfo(tableName);
		String parentCode = null;
		boolean realted = parentMap != null && parentMap.get("isRelation").equals("1") ? true : false;
		if (realted) {
			parentCode = parentMap.get("parentCode");
		}
		
		// 将所有有外键关联的字段信息放入到map之中
		Map<String, String> map = new HashMap<String, String>();
		for (FormFieldDTO dto : listDTOs) {
			String controlType = dto.getControlType();
			if (controlType == null)
				continue;
			if (controlType.equals("selectpersontext")) {
				map.put(dto.getFieldCode(), "sys_user:user_name");
				continue;
			} else if (controlType.equals("selectdepttext")) {
				map.put(dto.getFieldCode(), "sys_dept:dept_name");
				continue;
			} else if (controlType.equals("pulltexttype")) {
				map.put(dto.getFieldCode(), "dev_data:data_name");
				continue;
			} else if (controlType.equals("outkey_relate")) {
				map.put(dto.getFieldCode(), parentCode + ":" + dto.getRelatedCode());
			}else if (controlType.equals("corp_name")) {
				map.put(dto.getFieldCode(), "sys_user:user_name");
				continue;
			}
		}
		// 获得所有查询的参数
		String[] searchFields = null;
		if (searchMap != null && !searchMap.trim().equals("")) {
			searchMap = searchMap.substring(0, searchMap.length() - 1);
			searchFields = searchMap.split("[,]");
		} else {
			searchFields = new String[0];
		}
		Map<String, String> searchMapParam = new HashMap<String, String>();
		StringBuilder countSql = new StringBuilder();	
		countSql.append("select count(*) as total from ").append(tableName).append(" where 1=1 and status='1' ");
		for (String searchField : searchFields) {
			String[] searchFieldDetail = searchField.split("[:]");
			if (searchFieldDetail.length <= 1) {
				continue;
			}
			if (searchFieldDetail[1].length() <= 0) {
				continue;
			}
			if (searchFieldDetail[1].trim().equals("")) {
				continue;
			}
			searchMapParam.put(searchFieldDetail[0], searchFieldDetail[1]);
			countSql.append(" and ").append(searchFieldDetail[0]).append(" like '%").append(searchFieldDetail[1]).append("%' ");
		}
		// 封装查询数据条数的语句
		
		// log发出的查询所以数据条数的语句
		logger.info(countSql.toString());
		Map<String, Object> pageListMap = getPageListSql(intPage, number, start, tableName, searchMapParam , filterMap, order, sort);
		String pageListSql = (String) pageListMap.get("pageListSql");
		
		for (String s : filterMap.keySet()) {
			countSql.append(" and ").append(s).append(" = '").append(filterMap.get(s)).append("' ");
		}
//		List<String> listSearch = (List<String>) pageListMap.get("listSearch");
		StringBuilder dataStore = new StringBuilder();
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statementCount = null;
		ResultSet resultSet = null;
		ResultSet resultSetCount = null;
		try {
			connection = DBConnectionUtil.getInstance().getConnection();
			statementCount = connection.prepareStatement(countSql.toString());
			statement = connection.prepareStatement(pageListSql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			int flag = 1;
//			for (String searchFieldName : listSearch) {
//				statement.setObject(flag, request.getParameter(searchFieldName));
//				statementCount.setObject(flag, request.getParameter(searchFieldName));
//			}
			resultSetCount = statementCount.executeQuery();
			
			// 先拼装现在数据的总数
			while (resultSetCount.next()) {
				dataStore.append("{\"total\":").append(resultSetCount.getInt(1));
				totalNumber = resultSetCount.getInt(1);
			}
			DBConnectionUtil.getInstance().close(null, statementCount, resultSetCount);
			
			resultSet = statement.executeQuery();
			
			// 封装其它数据json字符串
			generatePageListJson(resultSet, dataStore, tableName, map);
			if(freemarkerPath!=null){
				resultSet.beforeFirst();
				list = generatePageList(resultSet, tableName);
			}
		} catch (Exception e) {
			logger.error("在进行分页时发生了Exception:{}", e);
		} finally {
			DBConnectionUtil.getInstance().close(connection, statement, resultSet);
		}
		if(freemarkerPath==null)
			Struts2Utils.renderText(dataStore.toString());
	}
	
	/**
	 * 根据 ResultSet来获得分页的json数据
	 * 
	 * @param resultSet
	 * @param dataStore
	 * @throws SQLException 
	 */
	private void generatePageListJson(ResultSet resultSet, StringBuilder dataStore, String tableName, Map<String, String> relatedMap) throws SQLException {
		dataStore.append(",\"rows\":[");
		int flag = 1;
		Connection connection = DBConnectionUtil.getInstance().getConnection();
		Statement statement = null;
		ResultSet resultSet2 = null;
		while (resultSet.next()) {
			dataStore.append("{");
			
			for (String fieldName : this.getMetaData(tableName)) {
				dataStore.append("\"").append(fieldName).append("\":");
				Object value = resultSet.getObject(fieldName);
				if (value == null) {
					dataStore.append("\"").append("\",");
					continue;
				} else {
					if (relatedMap.containsKey(fieldName)) {
						String relatInfo = relatedMap.get(fieldName);
						String[] relateInfos = relatInfo.split("[:]");
						String relatTableName = relateInfos[0];
						String relatFieldName = relateInfos[1];
						String selectRelateSql = "select " + relatFieldName + " from " + relatTableName + " where id='" + value.toString() + "'";
						statement = connection.createStatement();
//						preparedStatement.setString(1, value.toString());
//						logger.info(selectRelateSql);
//						resultSet2 = preparedStatement.executeQuery();
						resultSet2 = statement.executeQuery(selectRelateSql);
						boolean flag2 = false;
						while (resultSet2.next()) {
							flag2 = true;
							Object temp = resultSet2.getObject(relatFieldName);
							if (temp == null) {
								dataStore.append("\"").append("\",");
								continue;
							}
							dataStore.append("\"").append(resultSet2.getObject(relatFieldName).toString()).append("\",");
						}
						if (!flag2) {
							dataStore.append("\"").append("\",");
						}
						continue;
					} else {
						dataStore.append("\"").append(value.toString()).append("\",");
					}
				}
			}			
			dataStore.deleteCharAt(dataStore.lastIndexOf(","));
			dataStore.append("},");
			flag++;
		}
		if (1 != flag) {
			dataStore.deleteCharAt(dataStore.lastIndexOf(","));
		}
		dataStore.append("]}");
		DBConnectionUtil.getInstance().close(connection, statement, resultSet2);
	}
	
	/**
	 * 将数据存放到List<Map>中
	 * @param resultSet
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @return List
	 */
	private List generatePageList(ResultSet resultSet, String tableName) throws SQLException {
		int flag = 1;
		List list = new ArrayList<Map>();
		while (resultSet.next()) {
			Map map = new HashMap<String,Object>();
			for (String fieldName : this.getMetaData(tableName)) {
				Object value = resultSet.getObject(fieldName);
				map.put(fieldName, value);
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 根据不同的数据库系统，来做出不同的分页操作
	 * 
	 * @param intPage
	 * @param number
	 * @param start
	 * @param tableName
	 * @return
	 */
	private Map<String, Object> getPageListSql(int intPage, int number, int start, String tableName, Map<String, String> searchMap , Map<String, String> filterMap, String orderby, String orderM) {
		// 当执行分页的时候肯定会有Cache在进行缓存的，这里不再进行二次判断了
		Object cache4ListDTOs = getCacheObjByKey(menuId);
		List<FormFieldDTO> listDTOs = null;
		listDTOs = (List<FormFieldDTO>) cache4ListDTOs;
		Map<String, String> searchMapK = new HashMap<String, String>();
		for (Iterator<FormFieldDTO> iterator=listDTOs.iterator(); iterator.hasNext();) {
			FormFieldDTO dto = iterator.next();
			if (dto.getIsSearch() == null || dto.getIsSearch().trim().equals("0")) {
				continue;
			}
			
			if (dto.getSearchType().trim().equals("0")) {
				searchMapK.put(dto.getFieldCode(), "like");
			} else if (dto.getSearchType().trim().equals("1")) {
				searchMapK.put(dto.getFieldCode(), "=");
			} else if (dto.getSearchType().trim().equals("2")) {
				searchMapK.put(dto.getFieldCode(), "<");
			} else if (dto.getSearchType().trim().equals("3")) {
				searchMapK.put(dto.getFieldCode(), "<=");
			} else if (dto.getSearchType().trim().equals("4")) {
				searchMapK.put(dto.getFieldCode(), ">");
			} else {
				searchMapK.put(dto.getFieldCode(), ">=");
			}
		}
		
//		List<String> list = new ArrayList<String>();
		// 做条件查询的语句，因为这里的Map在进行EntrySet方法操作的时候会造成无序的状态，故这里要记录一下顺序
		StringBuilder searchPart = new StringBuilder();
		for (Iterator<Map.Entry<String, String>> iterator=searchMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = iterator.next();
			String key = entry.getKey();
			String value = entry.getValue();
			
			String searchType = searchMapK.get(key);
			if (searchType.equals("like")) {
				searchPart.append(" and ").append(key).append(" like '%").append(value).append("%'");
			} else {
				searchPart.append(" and ").append(key).append(" ").append(searchType).append(" ").append("'").append(value).append("'");
			}
		}
		StringBuilder pageListSql = new StringBuilder();
		pageListSql.append("select top ").append(number).append(" * from ").append(tableName).append(" where id not in")
				   .append("( select top ").append(start).append(" id from ").append(tableName).append(" where status = 1 ")
				   .append(searchPart)
				   .append(" order by create_time desc )")
				   .append(" and status=1 ");
		for (String s : filterMap.keySet()) {
			pageListSql.append(" and ").append(s).append(" = '").append(filterMap.get(s)).append("' ");
		}
		
		pageListSql.append(searchPart);
		if (orderby == null || orderM == null) {
			pageListSql.append(" order by create_time desc");
		} else {
			pageListSql.append(" order by ").append(orderM).append(" ").append(orderby);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pageListSql", pageListSql.toString());
		logger.info(pageListSql.toString());
//		resultMap.put("listSearch", list);
		return resultMap;
	}
	public String unionForm() {
		if(formId==null)
			throw new SystemException("表单id不存在");
		DevForm df = formService.getById(formId);
		if(df == null)
			throw new SystemException("表单不存在");
		menuId = df.getMenuId();
		if(menuId == null || menuId.isEmpty())
			throw new SystemException("表单未生成菜单");
		return execute();
	}
	
	
	/**
	 * 根据id查找到一条数据
	 */
	public String getById() {
		String tableName = formService.getEntityCode(menuId);
		StringBuilder json = new StringBuilder();
		StringBuilder selectSql = new StringBuilder();
		selectSql.append("select * from ").append(tableName).append(" where id=?");
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Map map=new HashMap();
		// log发出的查询语句
		logger.info(selectSql.toString());
		try {
			connection = DBConnectionUtil.getInstance().getConnection();
			statement = connection.prepareStatement(selectSql.toString());
			statement.setObject(1, id);
			resultSet = statement.executeQuery();
			json.append("[");
			while (resultSet.next()) {
				
				// 一行一行将Result数据遍历出来再进行json类型的封装，这里只应该为一条数据
				json.append("{");
				for (String fieldName : this.getMetaData(tableName)) {
					json.append("\"").append(fieldName).append("\":");
					Object valueObj = resultSet.getObject(fieldName);
					if (valueObj == null) {
						json.append("\"\",");
					} else {
						json.append("\"").append(resultSet.getObject(fieldName).toString()).append("\",");
						map.put(fieldName, valueObj);
					}
				}
				json.deleteCharAt(json.lastIndexOf(","));
				json.append("},");
			}
			json.deleteCharAt(json.lastIndexOf(","));
			json.append("]");
		} catch (SQLException e) {
			logger.error("查询数据时发生Exception:{}", e);
		} finally {
			DBConnectionUtil.getInstance().close(connection, statement, resultSet);
		}
		if(freemarkerPath == null){
			Struts2Utils.renderText(json.toString());
			return null;
		}
		request.setAttribute("m", map);
		return "freemarker";
	}
	
	/**
	 * 只为获得数据库表里面的列名与配置出来的表列名进行对比
	 * 
	 * @param tableName
	 * @return
	 */
	private List<String> getMetaData(String tableName) {
		Connection con = null;
		PreparedStatement pst = null;
		List<String> list = new ArrayList<String>();
		try {
			con = DBConnectionUtil.getInstance().getConnection();
			pst = con.prepareStatement("select * from "
					+ tableName + " where 1=2");
			ResultSetMetaData rsd=pst.executeQuery().getMetaData();
			for (int i = 0; i < rsd.getColumnCount(); i++) {
				list.add(rsd.getColumnName(i+1));
			}
			return list;
		} catch (Exception e) {
			logger.error("获得列名时发生错误:{}", e);
		} finally {
			DBConnectionUtil.getInstance().close(con, pst);
		}
		return list;
	}
	public String batchOperation() throws SQLException{
		if(ids==null || batchSql==null)
			throw new SystemException("缺少参数");
		String idsParam[] = ids.split("[,]");
		int count = 0;
		for (char c : batchSql.toCharArray()) {
			if (c == '?')
				count++;
		}
		if(count!=1)
			throw new SQLException("批量语句参数数目不正确");
		OperateInfo operateInfo = new OperateInfo();
		try {
			executeBatchSQL(batchSql, idsParam);
		} catch (SQLException e) {
			operateInfo.setOperateMessage("操作失败");
			operateInfo.setOperateSuccess(true);
			String jsonObj = new JsonMapper().toJson(operateInfo);
			Struts2Utils.renderText(jsonObj);
			throw e;
		}
		operateInfo.setOperateMessage("操作成功");
		operateInfo.setOperateSuccess(true);
		String jsonObj = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(jsonObj);
		return null;
	}
	private int[] executeBatchSQL(String batchSql,Object[] params) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		con = DBConnectionUtil.getInstance().getConnection();
		ps = con.prepareStatement(batchSql);
		for (Object s : params) {
			ps.setObject(1, s);
			ps.addBatch();
		}
		int[] nums=ps.executeBatch();
		DBConnectionUtil.getInstance().close(con, ps);
		return nums;
	}
	/**
	 * 获取需要进行过滤的Map
	 * @param filterParameterNames
	 * @param filterParameterValues
	 * @return
	 * @return Map<String,String>
	 */
	private Map<String,String> getFilterParameterMap(String filterParameterNames,String filterParameterValues){
		Map<String,String> map = new HashMap<String,String>();
		if(filterParameterNames==null || filterParameterValues==null)
			return map;
		String[] names = filterParameterNames.split("[,]");
		String[] values = filterParameterValues.split("[,]");
		if(names.length!=values.length)
			return map;
		for (int i=0; i<names.length; i++) {
			if(names[i].trim().isEmpty() || values[i].trim().isEmpty())
				continue;
			map.put(names[i], values[i]);
		}
		return map;
	}
	/**
	 * 获得所有查询的参数
	 * @return
	 * @return Map<String,String>
	 */
	private Map<String,String> getSearchParameterMap(String searchMap){
		String[] searchFields = null;
		if (searchMap != null && !searchMap.trim().equals("")) {
			searchMap = searchMap.substring(0, searchMap.length() - 1);
			searchFields = searchMap.split("[,]");
		} else {
			searchFields = new String[0];
		}
		Map<String, String> searchMapParam = new HashMap<String, String>();
		for (String searchField : searchFields) {
			String[] searchFieldDetail = searchField.split("[:]");
			if (searchFieldDetail.length <= 1) {
				continue;
			}
			if (searchFieldDetail[1].length() <= 0) {
				continue;
			}
			if (searchFieldDetail[1].trim().equals("")) {
				continue;
			}
			searchMapParam.put(searchFieldDetail[0], searchFieldDetail[1]);
		}
		return searchMapParam;
	}
	/**
	 * 导出操作
	 * 
	 * @return 导出的结果值
	 */
	public String exp() {
		StringBuilder sql = new StringBuilder();
		Object cache4ListDTOs = getCacheObjByKey(menuId);
		List<FormFieldDTO> list = null;
		if (null == cache4ListDTOs) {
			list = formService.getFormField(menuId);
			putCacheObj(menuId, list);
			logger.info("从数据库得到的ListDTO对象{}", list);
		} else {
			list = (List<FormFieldDTO>) cache4ListDTOs;
			logger.info("从缓存得到的ListDTO对象{}", list);
		}
		String tableName = formService.getEntityCode(menuId);
		// 得到父表的信息
		Map<String, String> parentMap = formService.getParentInfo(tableName);
		String parentCode = null;
		boolean realted = parentMap != null && parentMap.get("isRelation").equals("1") ? true : false;
		if (realted) {
			parentCode = parentMap.get("parentCode");
		}
		sql.append("select ");
		excelHeads = new String[list.size()];
		// 加上多个字段关联sys_user，sys_dept，dev_data的判断
		int selectpersontext = 0 ,selectdepttext = 0 ,pulltexttype = 0 ,outkey_relate = 0;
		StringBuilder afterFrom = new StringBuilder();
		for (int i=0; i<list.size(); i++) {
			String controlType = list.get(i).getControlType();
			if (controlType == null){
				//sql.append(tableName).append(".").append(list.get(i).getFieldCode()).append(", ");
			} else if (controlType.equals("selectpersontext")) {
				sql.append("sys_user").append(selectpersontext).append(".user_name, ");
				afterFrom.append(" left join ").append(" sys_user ")
					.append(" sys_user").append(selectpersontext).append(" on sys_user")
					.append(selectpersontext).append(".id = ").append(tableName)
					.append(".").append(list.get(i).getFieldCode());
				selectpersontext++;
			} else if (controlType.equals("selectdepttext")) {
				sql.append("sys_dept").append(selectdepttext).append(".dept_name, ");
				afterFrom.append(" left join ").append(" sys_dept ")
					.append(" sys_dept").append(selectdepttext).append(" on sys_dept")
					.append(selectdepttext).append(".id = ").append(tableName)
					.append(".").append(list.get(i).getFieldCode());
				selectdepttext++;
			} else if (controlType.equals("pulltexttype")) {
				sql.append("dev_data").append(pulltexttype).append(".data_name, ");
				afterFrom.append(" left join ").append(" dev_data ")
					.append(" dev_data").append(pulltexttype).append(" on dev_data")
					.append(pulltexttype).append(".id = ").append(tableName)
					.append(".").append(list.get(i).getFieldCode());
				pulltexttype++;
			} else if (controlType.equals("outkey_relate")) {
				if(outkey_relate>0){
					SystemException e =new SystemException("outkey_relate唯一");
					logger.error(e.getMessage(),e);
					throw e;					
				}
				sql.append(parentCode).append(".").append(list.get(i).getRelatedCode()).append(", ");
				afterFrom.append(" left join ").append(parentCode).append(" on ")
					.append(parentCode).append(".id = ").append(tableName)
					.append(".").append(parentCode).append("_id");
				outkey_relate++;
			} else {
				sql.append(tableName).append(".").append(list.get(i).getFieldCode()).append(", ");
			}
			excelHeads[i] = list.get(i).getViewName();
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(" from ").append(tableName);
		sql.append(afterFrom);
		sql.append(" where 1=1 ").append(" and ").append(tableName).append(".status = '1' ");
		Map<String,String> filterMap = getFilterParameterMap(this.filterParameterNames, this.filterParameterValues);
		Map<String,String> searchMap = getSearchParameterMap(this.searchMap);
		for (String s : filterMap.keySet()) {
			sql.append(" and ").append(s).append(" = '").append(filterMap.get(s)).append("' ");
		}
		for (String s : searchMap.keySet()) {
			sql.append(" and ").append(s).append(" like '%").append(searchMap.get(s)).append("%' ");
		}
		sql.append(" ORDER BY ").append(tableName).append(".create_time DESC");
		logger.info("导出sql：{}",sql);
		excelQuerySql = sql.toString();
		excelSheetName = formService.getFormName(menuId);
		return "exp";
	}
	
	/**
	 * 导入操作
	 * 
	 * @return 导入的页面
	 */
	public String imp() {
		String tableName = formService.getEntityCode(menuId);
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(tableName).append("(");
		Object cache4ListDTOs = getCacheObjByKey(menuId);
		List<FormFieldDTO> list = null;
		if (null == cache4ListDTOs) {
			list = formService.getFormField(menuId);
			putCacheObj(menuId, list);
			logger.info("从数据库得到的ListDTO对象{}", list);
		} else {
			list = (List<FormFieldDTO>) cache4ListDTOs;
			logger.info("从缓存得到的ListDTO对象{}", list);
		}
		for (FormFieldDTO dto : list) {
			sql.append(dto.getFieldCode()).append(", ");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") values(");
		for (int i=1; i<=list.size(); i++) {
			sql.append("?, ");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(")");
		excelInsertSql = sql.toString();
		return "imp";
	}
	
	/**
	 * 当一张表有外键进行关联的时候，用的是Combobox来进行操作的
	 * 
	 * table:fieldCode
	 */
	public void getComboboxData() {
		String tableName = parentTableParam.split("[:]")[0];
		String fieldCodeName = parentTableParam.split("[:]")[1];
		StringBuilder comboJson = new StringBuilder();
		/*
		 * [
		 * {"id":"6710f1d1d1804cceaaa3308843c61317","typeId":"8e5f469d68f74279a2cfb4cff409f9d4"},
		 * {"id":"da1c026ca6294ec6b569ea357b592d80","typeId":"8e5f469d68f74279a2cfb4cff409f9d4"}
		 * ]
		 */
		comboJson.append("[");
		String sql = "select id, " + fieldCodeName + " from " + tableName;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		logger.info(sql);
		try {
			connection = DBConnectionUtil.getInstance().getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				Object value = resultSet.getObject(fieldCodeName);
				if (value == null) {
					continue;
				}
				comboJson.append("{\"id\":\"")
				.append(id).append("\",").append("\"value\":\"").append(value.toString()).append("\"},");
			}
			
			if (comboJson.charAt(comboJson.length() - 1) == ',') {
				comboJson.deleteCharAt(comboJson.lastIndexOf(","));
			}
			comboJson.append("]");
		} catch (Exception e) {
			logger.error("外键关联获取数据的时候出问题:{}", e);
		} finally {
			DBConnectionUtil.getInstance().close(connection, statement, resultSet);
		}
		
		Struts2Utils.renderJson(comboJson.toString());
	}
	
	/**
	 * 自动化页面更新前的时候进行的操作
	 */
	public void updateComboDataByValueId() {
		String tableName = parentTableParam.split("[:]")[0];
		String fieldCodeName = parentTableParam.split("[:]")[1];
		String queryId = parentTableParam.split("[:]")[2];
		String sql = "select " + fieldCodeName + " from " + tableName + " where id=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String resultString = "";
		try {
			connection = DBConnectionUtil.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, queryId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Object value = resultSet.getObject(fieldCodeName);
				if (value != null) {
					resultString = value.toString();
				}
			}
		} catch (Exception e) {
			logger.error("在更新前获得子表的显示值时发生Exception:{}", e);
		} finally {
			DBConnectionUtil.getInstance().close(connection, preparedStatement, resultSet);
		}
		
		Struts2Utils.renderText(resultString);
		
	}
	
	/**
	 * 子表的pageList操作
	 */
	public void coreChildPageList() {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Statement countStatement = null;
		ResultSet countResultSet = null;
		StringBuilder dataStore = new StringBuilder();
		String tableName = childTableParam.split("[:]")[0];
		String parentDataId = childTableParam.split("[:]")[1];
		List<FormFieldDTO> listDTOs = formService.getFormFieldByCode(tableName);
		
		Map<String, String> map = new HashMap<String, String>();
		String parentFieldName = "";
		for (FormFieldDTO dto : listDTOs) {
			String controlType = dto.getControlType();
			if (controlType.equals("selectpersontext")) {
				map.put(dto.getFieldCode(), "sys_user:user_name");
				continue;
			} else if (controlType.equals("selectdepttext")) {
				map.put(dto.getFieldCode(), "sys_dept:dept_name");
				continue;
			} else if (controlType.equals("pulltexttype")) {
				map.put(dto.getFieldCode(), "dev_data:data_name");
				continue;
			} else if (controlType.equals("outkey_relate")) {
				parentFieldName = dto.getFieldCode();
			}
		}
		try {
			connection = DBConnectionUtil.getInstance().getConnection();
			countStatement = connection.createStatement();
			String countSql = "select count(*) from " + tableName + " where " + parentFieldName + "='" + parentDataId + "'";
			logger.info("子表分页数据总数sql:{}", countSql);
			countResultSet = countStatement.executeQuery(countSql);
			while (countResultSet.next()) {
				dataStore.append("{\"total\":").append(countResultSet.getInt(1));
			}
			DBConnectionUtil.getInstance().close(null, countStatement, countResultSet);
			
			String sql = "select * from " + tableName + " where " + parentFieldName + "='" + parentDataId + "'";
			logger.info("子表分页sql语句:{}", sql);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			generatePageListJson(resultSet, dataStore, tableName, map);
		} catch (Exception e) {
			logger.error("在子表分页的过程中发生了Exception:{}", e);
		} finally {
			DBConnectionUtil.getInstance().close(connection, statement, resultSet);
		}
		//childTableId
		Struts2Utils.renderJson(dataStore.toString());
	}
	
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public FormService getFormService() {
		return formService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
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
	
	public String getSearchMap() {
		return searchMap;
	}
	
	public void setSearchMap(String searchMap) {
		this.searchMap = searchMap;
	}

	public DataService getDataService() {
		return dataService;
	}

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public MenuOptService getMenuOptService() {
		return menuOptService;
	}

	public void setMenuOptService(MenuOptService menuOptService) {
		this.menuOptService = menuOptService;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public String getBatchSql() {
		return batchSql;
	}

	public void setBatchSql(String batchSql) {
		this.batchSql = batchSql;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	@Autowired
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	private void initCache() {
		cache = cacheManager.getCache(CACHE_NAME);
	}
	
	public Object getCacheObjByKey(String key) {
		initCache();
		Element element = cache.get(key);
		if (null == element) {
			return null;
		}
		return element.getObjectValue();
	}
	
	public void putCacheObj(String key, Object value) {
		Element element = new Element(key, value);
		cache.put(element);
	}

	public String getParentTableParam() {
		return parentTableParam;
	}

	public void setParentTableParam(String parentTableParam) {
		this.parentTableParam = parentTableParam;
	}

	public String getFilterParameterNames() {
		return filterParameterNames;
	}

	public void setFilterParameterNames(String filterParameterNames) {
		this.filterParameterNames = filterParameterNames;
	}

	public String getFilterParameterValues() {
		return filterParameterValues;
	}

	public void setFilterParameterValues(String filterParameterValues) {
		this.filterParameterValues = filterParameterValues;
	}

	public String getChildTableParam() {
		return childTableParam;
	}

	public void setChildTableParam(String childTableParam) {
		this.childTableParam = childTableParam;
	}

	
}
