package com.ay.report.reversion.dao;

import com.ay.report.reversion.pojo.Reversion;

import java.util.List;
import java.util.Map;

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

	public List<Reversion> findRepRev(Map map){
		return (List<Reversion>)getSqlMapClientTemplate().queryForList(this.getEntityName() + ".findRepRev", map);
	}
}