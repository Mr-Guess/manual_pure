package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.sys.pojo.SysUserGroup;

/**
 * 用户组成员关系表dao封装
 * 
 * @author lushigai
 *
 */
@SuppressWarnings("all")
public class SysUserGroupDao extends BaseDao<SysUserGroup> {

	@Override
	public String getEntityName() {
		return "SysUserGroup";
	}
	
	/**
     * 根据用户组id删除
     * 
     * @param id
     * @return 删除的记录数是否为1
     */
    public boolean deleteByGroupId(String groupId)
    {
        int rows = getSqlMapClientTemplate().delete(getEntityName() + ".deleteByGroupId", groupId);
        return rows == 1;
    }

	@Override
	public String getTableName() {
		return "sys_user_group";
	}
}
