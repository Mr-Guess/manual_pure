package com.ay.jfds.sys.service;

import com.ay.framework.core.service.BaseService;
import com.ay.jfds.sys.dao.SysRoleGroupDao;
import com.ay.jfds.sys.pojo.SysRoleGroup;

/**
 * 角色用户组关系表service 的封装
 * 
 * @author lushigai
 * @see SysRoleGroupDao
 */
public class SysRoleGroupService extends BaseService<SysRoleGroup, SysRoleGroupDao> {
	//新增角色用户组关系
	public boolean addRoleGroup(String groupsStr,String roleId){
		this.dao.deleteByRoleId(String.valueOf(roleId));
    	String[] groups = groupsStr.split(";");
    	for(int i = 0 ;i<groups.length;i++){
    		String groupId = groups[i];
    		SysRoleGroup sysRoleGroup = new SysRoleGroup();
    		sysRoleGroup.setGroupId(groupId);
    		sysRoleGroup.setRoleId(roleId);
    		this.dao.insert(sysRoleGroup);
    	}
    	return true;
	}
}
