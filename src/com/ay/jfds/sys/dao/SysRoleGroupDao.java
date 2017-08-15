package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.sys.pojo.SysRoleGroup;

/**
 * 角色用户组关系表dao封装
 * 
 * @author lushigai
 *
 */
@SuppressWarnings("all")
public class SysRoleGroupDao extends BaseDao<SysRoleGroup> {

	@Override
	public String getEntityName() {
		return "SysRoleGroup";
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
		return "sys_role_group";
	}
}
