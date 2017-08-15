package com.ay.jfds.sys.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.common.ITreeService;
import com.ay.jfds.sys.service.DepartmentUserTreeService;
import com.ay.jfds.sys.service.SysRoleDeptService;
import com.ay.jfds.sys.service.SysRoleGroupService;
import com.ay.jfds.sys.service.SysRoleMenuOptService;
import com.ay.jfds.sys.service.SysRoleService;
import com.ay.jfds.sys.service.SysRoleUserService;
import com.ay.jfds.sys.service.SysUserGroupService;
import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.jfds.sys.pojo.SysGroup;
import com.ay.jfds.sys.pojo.SysRole;
import com.ay.jfds.sys.pojo.SysRoleDept;
import com.ay.jfds.sys.pojo.SysRoleGroup;
import com.ay.jfds.sys.pojo.SysRoleMenuOpt;
import com.ay.jfds.sys.pojo.SysRoleUser;
import com.ay.jfds.sys.pojo.SysUserGroup;
import com.ay.jfds.sys.pojo.User;
import com.ay.jfds.sys.service.SysGroupService;

/**
 * 角色权限管理
 * 
 * @author lushigai
 * 
 */
@SuppressWarnings("all")
public class SysRoleAction extends BaseAction {
	private SysRole sysRole;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String roleName;
	private String id;
	private String roleId;
	private String roleDeptsStr;
	private String deptRoleId;
	private String usersStr;
	private String ids;
	private String userRoleId;
	private String groupsStr;
	private String groupRoleId;
	private String menuOptsStr;
	private String menuOptRoleId;
	private SysRoleService sysRoleService;
	private SysRoleDeptService sysRoleDeptService;
	private SysRoleUserService sysRoleUserService;
	private SysRoleGroupService sysRoleGroupService;
	private SysGroupService sysGroupService;
	private SysRoleMenuOptService sysRoleMenuOptService;
	
	/* 用户ID数组 */
	private List<String> userIdList = new ArrayList<String>();
	
	public SysRoleMenuOptService getSysRoleMenuOptService() {
		return sysRoleMenuOptService;
	}

	public void setSysRoleMenuOptService(
			SysRoleMenuOptService sysRoleMenuOptService) {
		this.sysRoleMenuOptService = sysRoleMenuOptService;
	}

	public SysGroupService getSysGroupService() {
		return sysGroupService;
	}

	public void setSysGroupService(SysGroupService sysGroupService) {
		this.sysGroupService = sysGroupService;
	}

	public SysRoleGroupService getSysRoleGroupService() {
		return sysRoleGroupService;
	}

	public void setSysRoleGroupService(SysRoleGroupService sysRoleGroupService) {
		this.sysRoleGroupService = sysRoleGroupService;
	}

	public SysRoleUserService getSysRoleUserService() {
		return sysRoleUserService;
	}

	public void setSysRoleUserService(SysRoleUserService sysRoleUserService) {
		this.sysRoleUserService = sysRoleUserService;
	}

	public SysRoleDeptService getSysRoleDeptService() {
		return sysRoleDeptService;
	}

	public void setSysRoleDeptService(SysRoleDeptService sysRoleDeptService) {
		this.sysRoleDeptService = sysRoleDeptService;
	}

	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}


	public String getGroupsStr() {
		return groupsStr;
	}

	public String getMenuOptsStr() {
		return menuOptsStr;
	}

	public void setMenuOptsStr(String menuOptsStr) {
		this.menuOptsStr = menuOptsStr;
	}

	public String getMenuOptRoleId() {
		return menuOptRoleId;
	}

	public void setMenuOptRoleId(String menuOptRoleId) {
		this.menuOptRoleId = menuOptRoleId;
	}

	public void setGroupsStr(String groupsStr) {
		this.groupsStr = groupsStr;
	}

	public String getGroupRoleId() {
		return groupRoleId;
	}

	public void setGroupRoleId(String groupRoleId) {
		this.groupRoleId = groupRoleId;
	}

	public String getUsersStr() {
		return usersStr;
	}

	public void setUsersStr(String usersStr) {
		this.usersStr = usersStr;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getDeptRoleId() {
		return deptRoleId;
	}

	public void setDeptRoleId(String deptRoleId) {
		this.deptRoleId = deptRoleId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleDeptsStr() {
		return roleDeptsStr;
	}

	public void setRoleDeptsStr(String roleDeptsStr) {
		this.roleDeptsStr = roleDeptsStr;
	}

	public SysRole getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public void addSysRole() {
		OperateInfo operateInfo = new OperateInfo();
		try {
			sysRoleService.insert(sysRole);
			operateInfo.setOperateMessage("添加角色成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			operateInfo.setOperateMessage("添加角色失败");
			operateInfo.setOperateSuccess(false);
		}

		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	public void getByIdDTO() {
		OperateInfo operateInfo = new OperateInfo();
		SysRole sysRole = sysRoleService.getById(id);
		Struts2Utils.renderJson(sysRole, EncodingHeaderUtil.HEADERENCODING);
	}

	public void updateSysRole() {
		OperateInfo operateInfo = new OperateInfo();
		SysRole sysRoleNew = sysRoleService.getById(id);
		sysRoleNew.setRoleName(sysRole.getRoleName());
		sysRoleNew.setDescription(sysRole.getDescription());
		boolean flag = sysRoleService.update(sysRoleNew);
		if (flag) {
			operateInfo.setOperateMessage("更新角色成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新角色失败");
			operateInfo.setOperateSuccess(false);
		}

		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	public void deleteSysRole() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = sysRoleService.delete(sysRole);
		if (flag) {
			operateInfo.setOperateMessage("删除角色成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("删除角色失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 分页查询用户组信息
	 */
	public void pageList() {
		DataStore<SysRole> dataStore = new DataStore<SysRole>();
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
		Map paramMap =BeanUtil.Bean2Map(sysRole);
		Page resultPage = null;
		try {
			resultPage = sysRoleService.pageQuery(paramMap, pageTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<SysRole> resultList = (List<SysRole>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}

	public void getById() {
		SysRole sysRole = sysRoleService.getById(id);
		Struts2Utils.renderJson(sysRole, EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}

	public void deleteByIds() {
		String[] idsParam = ids.split("[,]");
		OperateInfo operateInfo = new OperateInfo();
		List<String> params=Arrays.asList(idsParam);//转换成list
		//设置不能删除的ID
		String readOnlyIds[]=new String[]{"712aed3c80504cb99fd59062b865b0f5","ddd7c6f1e7904ea19995835e187bd521"};
		for(String read:readOnlyIds){
			for(String id:idsParam){
				if(read.equals(id)){
					params.remove(read);
				}
			}
		}
		idsParam=params.toArray(idsParam);
		boolean flag = sysRoleService.delete(idsParam);
		if (flag) {
			operateInfo.setOperateMessage("删除角色成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("删除角色失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	public void reloadDeptUserTree() {
		DepartmentUserTreeService departmentUserTreeService = DepartmentUserTreeService
				.getInstance();
		departmentUserTreeService.reloadDepartmentUserTree();
		String treeJson = treeService.generateJsonCheckboxTree(
				departmentUserTreeService, false);
		Struts2Utils.renderJson(treeJson);
	}

	/**
	 * 保存部门树信息
	 */
	public void updateRoleDept() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = sysRoleDeptService.addRoleDept(roleDeptsStr, deptRoleId);
		if (flag) {
			operateInfo.setOperateMessage("部门授权成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("部门授权失败");
			operateInfo.setOperateSuccess(false);
		}

		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 显示部门树信息
	 */
	public void getDeptIds() {
		OperateInfo operateInfo = new OperateInfo();
		Map map = new HashMap();
		map.put("roleId", roleId);
		List<SysRoleDept> list = sysRoleDeptService.query(map);
		String deptIds = "";
		for (int i = 0; i < list.size(); i++) {
			SysRoleDept roleDept = list.get(i);
			deptIds = deptIds + roleDept.getDeptId() + ";";
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(deptIds);
	}

	/**
	 * 更新部门人员树信息
	 */
	public void updateRoleUser() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag  = false;
		if(usersStr!= null && usersStr.indexOf(";")!=-1){
			flag = sysRoleUserService.addRoleUser(usersStr.split(";"), userRoleId);
		}else{
			flag = sysRoleUserService.addRoleUser(userIdList.toArray(), userRoleId);
		}
//		boolean flag = sysRoleUserService.addRoleUser(usersStr, "51f0366b43b24cea903db809007aacd0");
		if (flag) {
			operateInfo.setOperateMessage("用户授权成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("用户授权失败");
			operateInfo.setOperateSuccess(false);
		}

		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 显示部门人员树信息
	 */
	public void getUserIds() {
		OperateInfo operateInfo = new OperateInfo();
		Map map = new HashMap();
		map.put("roleId", roleId);
		List<SysRoleUser> list = sysRoleUserService.query(map);
		String userIds = "";
		for (int i = 0; i < list.size(); i++) {
			SysRoleUser sysRoleUser = list.get(i);
			userIds = userIds + sysRoleUser.getUserId() + ";";
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(userIds);
	}

	/**
	 * 获取所有用户组数据
	 */
	public void getGroups() {
		OperateInfo operateInfo = new OperateInfo();
		List<SysGroup> list = sysGroupService.findAll();
		String groups = "";
		for (int i = 0; i < list.size(); i++) {
			SysGroup sysGroup = list.get(i);
			groups = groups + sysGroup.getId() + "," + sysGroup.getGroupName()
					+ ";";
		}
		Struts2Utils.renderText(groups);
	}

	/**
	 * 显示用户组信息
	 */
	public void getGroupIds() {
		OperateInfo operateInfo = new OperateInfo();
		Map map = new HashMap();
		map.put("roleId", roleId);
		List<SysRoleGroup> list = sysRoleGroupService.query(map);
		String groupIds = "";
		for (int i = 0; i < list.size(); i++) {
			SysRoleGroup sysRoleGroup = list.get(i);
			groupIds = groupIds + sysRoleGroup.getGroupId() + ";";
		}
		Struts2Utils.renderText(groupIds);
	}

	/**
	 * 更新用户组信息
	 */
	public void updateRoleGroup() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = sysRoleGroupService.addRoleGroup(groupsStr, groupRoleId);
		if (flag) {
			operateInfo.setOperateMessage("用户组授权成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("用户组授权失败");
			operateInfo.setOperateSuccess(false);
		}

		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 显示菜单权限信息
	 */
	public void getMenuOptIds() {
		OperateInfo operateInfo = new OperateInfo();
		Map map = new HashMap();
		map.put("roleId", roleId);
		List<SysRoleMenuOpt> list = sysRoleMenuOptService.query(map);
		String menuOptIds = "";
		for (int i = 0; i < list.size(); i++) {
			SysRoleMenuOpt sysRoleMenuOpt = list.get(i);
			menuOptIds = menuOptIds + sysRoleMenuOpt.getMenuOptId() + ";";
		}
		Struts2Utils.renderText(menuOptIds);
	}

	/**
	 * 更新菜单权限关系信息
	 */
	public void updateRoleMenuOpt() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = sysRoleMenuOptService.addRoleMenuOpt(menuOptsStr,menuOptRoleId);
		if (flag) {
			operateInfo.setOperateMessage("设置菜单权限成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("设置菜单权限失败");
			operateInfo.setOperateSuccess(false);
		}

		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	private ITreeService treeService;

	public ITreeService getTreeService() {
		return treeService;
	}

	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<String> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}
	
	
}
