package com.ay.jfds.sys.dao;

import com.ay.jfds.sys.pojo.SysGridSystem;
import com.ay.framework.core.dao.BaseDao;

public class SysGridSystemDao extends BaseDao<SysGridSystem> {
	@Override
	public String getEntityName() {
		return "SysGridSystem";
	}
	@Override
	public String getTableName() {
		return "TB_SYS_GRID_SYSTEM";
	}

}