package com.ay.jfds.sys.service;

import com.ay.framework.core.service.BaseService;
import com.ay.jfds.sys.dao.SysRoleMenuOptDao;
import com.ay.jfds.sys.pojo.SysRoleDept;
import com.ay.jfds.sys.pojo.SysRoleMenuOpt;

/**
 * 角色菜单权限关系service 的封装
 * 
 * @author lushigai
 * @see SysRoleDao
 */
public class SysRoleMenuOptService extends BaseService<SysRoleMenuOpt, SysRoleMenuOptDao> {
	//新增菜单权限关系
	public boolean addRoleMenuOpt(String menuOptsStr,String roleId){
		this.dao.deleteByRoleId(String.valueOf(roleId));
    	String[] menuOpts = menuOptsStr.split(";");
    	for(int i = 0 ;i<menuOpts.length;i++){
    		if(null!=menuOpts[i]&&!menuOpts[i].equals("")){
    			String menuOptId = menuOpts[i];
        		SysRoleMenuOpt sysRoleMenuOpt = new SysRoleMenuOpt();
        		sysRoleMenuOpt.setMenuOptId(menuOptId);
        		sysRoleMenuOpt.setRoleId(roleId);
        		this.dao.insert(sysRoleMenuOpt);
    		}
    	}
    	return true;
	}
}
