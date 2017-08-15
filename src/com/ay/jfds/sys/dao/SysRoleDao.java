package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.sys.pojo.SysRole;

/**
 * 角色权限管理dao封装
 * 
 * @author lushigai
 *
 */
@SuppressWarnings("all")
public class SysRoleDao extends BaseDao<SysRole> {

	@Override
	public String getEntityName() {
		return "SysRole";
	}

	@Override
	public String getTableName() {
		return "sys_role";
	}
}
