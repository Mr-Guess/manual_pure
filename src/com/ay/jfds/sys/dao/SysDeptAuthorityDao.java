package com.ay.jfds.sys.dao;

import java.util.HashMap;
import java.util.Map;

import com.ay.jfds.sys.pojo.SysDeptAuthority;
import com.ay.framework.core.dao.BaseDao;

public class SysDeptAuthorityDao extends BaseDao<SysDeptAuthority> {
	@Override
	public String getEntityName() {
		return "SysDeptAuthority";
	}
	@Override
	public String getTableName() {
		return "TB_SYS_DEPT_AUTHORITY";
	}

	public String callGenerEntDept(String bmId){
		Map<String, String> map = new HashMap<String, String>();
		map.put("BMID_IN", bmId);
		getSqlMapClientTemplate().queryForList(
				getEntityName() + ".callGenerEntDept", map);
		return null;
	}
	
	/**
	 * 根据部门修改子部门的jdlx为0
	 * 时间：2014年11月10日 下午3:09:49
	 * 作者：周伟
	 * @param sysDeptAuthority
	 */
	public void updateChildJdlx(SysDeptAuthority sysDeptAuthority){
		int rows = getSqlMapClientTemplate().update(
				getEntityName() + ".updateChildJdlx", sysDeptAuthority);
	}
}