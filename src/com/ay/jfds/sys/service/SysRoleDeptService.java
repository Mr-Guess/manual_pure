package com.ay.jfds.sys.service;

import com.ay.framework.core.service.BaseService;
import com.ay.jfds.sys.dao.SysRoleDao;
import com.ay.jfds.sys.dao.SysRoleDeptDao;
import com.ay.jfds.sys.pojo.SysRoleDept;

/**
 * 角色部门关系表service 的封装
 * 
 * @author lushigai
 * @see SysRoleDao
 */
public class SysRoleDeptService extends BaseService<SysRoleDept, SysRoleDeptDao> {
	//新增角色部门关系
	public boolean addRoleDept(String roleDeptsStr,String roleId){
		this.dao.deleteByRoleId(String.valueOf(roleId));
    	String[] roleDepts = roleDeptsStr.split(";");
    	for(int i = 0 ;i<roleDepts.length;i++){
    		String deptId = roleDepts[i];
    		SysRoleDept sysRoleDept = new SysRoleDept();
    		sysRoleDept.setDeptId(deptId);
    		sysRoleDept.setRoleId(roleId);
    		this.dao.insert(sysRoleDept);
    	}
    	return true;
	}
}
