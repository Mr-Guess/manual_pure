package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.sys.pojo.SysRoleDept;

/**
 * 角色部门关系表dao封装
 * 
 * @author lushigai
 *
 */
@SuppressWarnings("all")
public class SysRoleDeptDao extends BaseDao<SysRoleDept> {

	@Override
	public String getEntityName() {
		return "SysRoleDept";
	}
	
	/**
     * 根据角色id删除
     * 
     * @param id
     * @return 删除的记录数是否为1
     */
    public boolean deleteByRoleId(String roleId)
    {
        int rows = getSqlMapClientTemplate().delete(getEntityName() + ".deleteByRoleId", roleId);
        return rows == 1;
    }

	@Override
	public String getTableName() {
		return "sys_role_dept";
	}
}
