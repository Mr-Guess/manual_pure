package com.ay.jfds.sys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.common.ITreeService;
import com.ay.jfds.sys.service.DepartmentUserTreeService;
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
import com.ay.jfds.sys.pojo.SysUserGroup;
import com.ay.jfds.sys.pojo.User;
import com.ay.jfds.sys.service.SysGroupService;

/**
 * 用户组管理
 * @author lushigai
 *
 */
@SuppressWarnings("all")
public class SysGroupAction extends BaseAction {
	private SysGroupService sysGroupService;
	private SysUserGroupService sysUserGroupService;
	public SysUserGroupService getSysUserGroupService() {
		return sysUserGroupService;
	}

	public void setSysUserGroupService(SysUserGroupService sysUserGroupService) {
		this.sysUserGroupService = sysUserGroupService;
	}

	public SysGroupService getSysGroupService() {
		return sysGroupService;
	}

	public void setSysGroupService(SysGroupService sysGroupService) {
		this.sysGroupService = sysGroupService;
	}

	private SysGroup sysGroup;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String groupName;
	private String id;
	private String ids;
	private String usersStr;
	private String groupId;
	
	public String getUsersStr() {
		return usersStr;
	}

	public void setUsersStr(String usersStr) {
		this.usersStr = usersStr;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void addSysGroup() {
		OperateInfo operateInfo = new OperateInfo();
		try {
			sysGroupService.insert(sysGroup);
			operateInfo.setOperateMessage("添加用户组成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			operateInfo.setOperateMessage("添加用户组失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

    public SysGroup getSysGroup() {
		return sysGroup;
	}

	public void setSysGroup(SysGroup sysGroup) {
		this.sysGroup = sysGroup;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void getByIdDTO() {
        OperateInfo operateInfo = new OperateInfo();
        SysGroup sysGroup = sysGroupService.getById(id);
        Struts2Utils.renderJson(sysGroup, EncodingHeaderUtil.HEADERENCODING);
    }
	
	public void updateSysGroup() {
		OperateInfo operateInfo = new OperateInfo();
		SysGroup sysGroupNew = sysGroupService.getById(id);
		sysGroupNew.setGroupName(sysGroup.getGroupName());
		sysGroupNew.setDescription(sysGroup.getDescription());
		boolean flag = sysGroupService.update(sysGroupNew);
		if (flag) {
			operateInfo.setOperateMessage("更新用户组成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新用户组失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void deleteSysGroup() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = sysGroupService.delete(sysGroup);
		if (flag) {
			operateInfo.setOperateMessage("删除用户组成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("删除用户组失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	/**
	 * 分页查询用户组信息
	 */
	public void pageList() {
		DataStore<SysGroup> dataStore = new DataStore<SysGroup>();
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
		Map paramMap = BeanUtil.Bean2Map(sysGroup);
		Page resultPage = sysGroupService.pageQuery(paramMap, pageTemp);
		List<SysGroup> resultList = (List<SysGroup>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void getById() {
		SysGroup sysGroup = sysGroupService.getById(id);
		Struts2Utils.renderJson(sysGroup, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	public void deleteByIds() {
		String[] idsParam = ids.split("[,]");
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = sysGroupService.delete(idsParam);
		if (flag) {
			operateInfo.setOperateMessage("删除用户成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("删除用户失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

    public void reloadDeptUserTree() {
        DepartmentUserTreeService departmentUserTreeService = DepartmentUserTreeService.getInstance();
        departmentUserTreeService.reloadDepartmentUserTree();
        String treeJson = treeService.generateJsonCheckboxTree(departmentUserTreeService, false);
        Struts2Utils.renderJson(treeJson);
    }
    
    public void reloadEntUserTree() {
        DepartmentUserTreeService departmentUserTreeService = DepartmentUserTreeService.getInstance();
        departmentUserTreeService.reloadDepartmentEntUserTree();
        String treeJson = treeService.generateJsonCheckboxTree(departmentUserTreeService, false);
        Struts2Utils.renderJson(treeJson);
    }
    
    public void updateUserGroup() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = sysUserGroupService.addUserGroup(usersStr, groupId);
		if (flag) {
			operateInfo.setOperateMessage("设置成员成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("设置成员失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
    
    public void getUserIds() {
		OperateInfo operateInfo = new OperateInfo();
		Map map = new HashMap();
		map.put("groupId", groupId);
		List<SysUserGroup> list = sysUserGroupService.query(map);
		String userIds = "";
		for(int i =0;i<list.size();i++){
			SysUserGroup userGroup = list.get(i);
			userIds = userIds + userGroup.getUserId()+";";
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(userIds);
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
}
