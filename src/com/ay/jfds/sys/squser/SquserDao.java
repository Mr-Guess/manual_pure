package com.ay.jfds.sys.squser;

import com.ay.jfds.sys.squser.Squser;
import com.ay.framework.core.dao.BaseDao;

public class SquserDao extends BaseDao<Squser> {
	@Override
	public String getEntityName() {
		return "Squser";
	}
	@Override
	public String getTableName() {
		return "TB_SQUSER";
	}

}