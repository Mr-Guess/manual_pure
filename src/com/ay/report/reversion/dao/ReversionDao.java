package com.ay.report.reversion.dao;

import com.ay.report.reversion.pojo.Reversion;
import com.ay.framework.core.dao.BaseDao;

public class ReversionDao extends BaseDao<Reversion> {
	@Override
	public String getEntityName() {
		return "Reversion";
	}
	@Override
	public String getTableName() {
		return "TB_REVERSION";
	}

}