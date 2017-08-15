package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.sys.pojo.SysRoleData;

/**
 * 角色数据权限DAO层
 * 
 * @author zxy
 *
 */
@SuppressWarnings("all")
public class SysRoleDataDao extends BaseDao<SysRoleData> {

	@Override
	public String getEntityName() {
		return "SysRoleData";
	}

	@Override
	public String getTableName() {
		return "sys_role_data";
	}

	/**
	 * 分页查找角色下面所管辖的所有table数据权限
	 * 
	 * @param map 查询条件
	 * @param from 开始位置
	 * @param prePageNum 页码
	 * @return List的SysRoleData对象
	 */
	public List<SysRoleData> findByRoleId(String roleId, int from, int prePageNum) {
		return (List<SysRoleData>) getSqlMapClientTemplate().queryForList(getEntityName() + ".findByRoleId", roleId, from, prePageNum);
	}
	
	/**
	 * 或者分页总数量的 方法
	 * 
	 * @param roleId
	 * @return
	 */
	public int countByRoleId(String roleId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(getEntityName() + ".countByRoleId", roleId);
	}
}
