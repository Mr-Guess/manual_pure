package com.ay.jfds.sys.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.jfds.sys.pojo.SysRoleData;
import com.ay.jfds.sys.service.SysRoleDataService;
import com.ay.jfds.sys.service.SysRoleService;

/**
 * 数据权限管理Action
 * 
 * @author zxy
 * 
 */
@SuppressWarnings("all")
public class SysRoleDataAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(SysRoleDataAction.class);
	private SysRoleData sysRoleData;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String roleId;
	private String roleDeptStr;
	private String userRoleStr;
	private SysRoleDataService sysRoleDataService;
	private SysRoleService sysRoleService;
	private String tablesId;
	private String roleDataId;
	
	/**
	 * 根据role id来查找到下面所有的table数据权限分页action
	 */
	public void pageListByRoleId() {
		DataStore<SysRoleData> dataStore = new DataStore<SysRoleData>();
		Page pageTemp = new Page();
		// 当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页显示条数 
		int number = Integer.parseInt((rows == null || rows == "0") ? "10"
				: rows);
		int start = (intPage - 1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Page resultPage = sysRoleDataService.findByRoleId(roleId, pageTemp);
		List<SysRoleData> resultList = (List<SysRoleData>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	/**
	 * 添加选中的表
	 */
	public void addTables() {
		String[] tablesIds = tablesId.split("[,]");
		OperateInfo operateInfo = new OperateInfo();
		try {
			for (int i=0; i<tablesIds.length; i++) {
				SysRoleData roleData = new SysRoleData();
				roleData.setTableName(tablesIds[i]);
				roleData.setRoleId(roleId);
				sysRoleDataService.insert(roleData);
			}
			operateInfo.setOperateMessage("选择表成功");
			operateInfo.setOperateSuccess(true);
		}
		catch (Exception e) {
			logger.error("系统权限数据选择表时发生错误:{}", e);
			operateInfo.setOperateMessage("选择失败");
			operateInfo.setOperateSuccess(false);
		}
		String operateInfoJson = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(operateInfoJson);
	}
	
	/**
	 * 得到下面已经搞定的表单
	 */
	public void getUsersId() {
		SysRoleData roleData = sysRoleDataService.getById(roleDataId);
		String userIds = roleData.getUserIds();
		if (userIds == null) {
			userIds = "";
		} else {
			userIds = userIds.replaceAll("[']", "");
			userIds = userIds.trim();
			userIds = userIds.replaceAll("[,]", ";");
		}
		Struts2Utils.renderText(userIds);
	}
	
	public void getDeptIds() {
		SysRoleData roleData = sysRoleDataService.getById(roleDataId);
		String deptIds = roleData.getDeptIds();
		if (deptIds == null) {
			deptIds = "";
		} else {
			deptIds = deptIds.replaceAll("[']", "");
			deptIds = deptIds.trim();
			deptIds = deptIds.replaceAll("[,]", ";");
		}
		Struts2Utils.renderText(deptIds);
	}
	
	public void updateDeptIds() {
		SysRoleData roleData = sysRoleDataService.getById(roleDataId);
		OperateInfo operateInfo = new OperateInfo();
		try {
			StringBuffer roleDeptStrResult = new StringBuffer();
			for (String roleDept : roleDeptStr.split("[,]")) {
				roleDeptStrResult.append("'").append(roleDept).append("',");
			}
			if (roleDeptStrResult.lastIndexOf(",") != -1) {
				roleDeptStrResult.deleteCharAt(roleDeptStrResult.lastIndexOf(","));
			}
			roleData.setDeptIds(roleDeptStrResult.toString());
			sysRoleDataService.update(roleData);
			operateInfo.setOperateMessage("授权部门表数据权限成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			operateInfo.setOperateMessage("授权部门表数据权限失败");
			operateInfo.setOperateSuccess(false);
		}
		String operateJson = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(operateJson);
		
	}
	
	public void updateUserIds() {
		SysRoleData roleData = sysRoleDataService.getById(roleDataId);
		OperateInfo operateInfo = new OperateInfo();
		try {
			StringBuffer roleUserStrResult = new StringBuffer();
			for (String roleUser : userRoleStr.split("[,]")) {
				roleUserStrResult.append("'").append(roleUser).append("',");
			}
			if (roleUserStrResult.lastIndexOf(",") != -1) {
				roleUserStrResult.deleteCharAt(roleUserStrResult.lastIndexOf(","));
			}
			roleData.setUserIds(roleUserStrResult.toString());
			sysRoleDataService.update(roleData);
			operateInfo.setOperateMessage("授权用户表数据权限成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			operateInfo.setOperateMessage("授权用户表数据权限失败");
			operateInfo.setOperateSuccess(false);
		}
		String operateJson = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(operateJson);
	}

	public SysRoleData getSysRoleData() {
		return sysRoleData;
	}

	public void setSysRoleData(SysRoleData sysRoleData) {
		this.sysRoleData = sysRoleData;
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleDeptStr() {
		return roleDeptStr;
	}

	public void setRoleDeptStr(String roleDeptStr) {
		this.roleDeptStr = roleDeptStr;
	}

	public String getUserRoleStr() {
		return userRoleStr;
	}

	public void setUserRoleStr(String userRoleStr) {
		this.userRoleStr = userRoleStr;
	}

	public SysRoleDataService getSysRoleDataService() {
		return sysRoleDataService;
	}

	public void setSysRoleDataService(SysRoleDataService sysRoleDataService) {
		this.sysRoleDataService = sysRoleDataService;
	}

	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	public String getTablesId() {
		return tablesId;
	}

	public void setTablesId(String tablesId) {
		this.tablesId = tablesId;
	}

	public String getRoleDataId() {
		return roleDataId;
	}

	public void setRoleDataId(String roleDataId) {
		this.roleDataId = roleDataId;
	}

	
}
