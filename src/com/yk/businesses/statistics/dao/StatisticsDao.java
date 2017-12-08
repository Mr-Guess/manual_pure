package com.yk.businesses.statistics.dao;

import com.yk.businesses.statistics.pojo.Statistics;

import java.util.List;

import com.ay.framework.core.dao.BaseDao;

public class StatisticsDao extends BaseDao<Statistics> {
	@Override
	public String getEntityName() {
		return "Statistics";
	}
	@Override
	public String getTableName() {
		return "TB_STATISTICS";
	}
	
	public List<Statistics> statisticReport(){
		return (List<Statistics>)this.getSqlMapClientTemplate().queryForList(getEntityName() + ".getUsersDept");
	}
	
	public List<Statistics> statisticReporter(){
		return (List<Statistics>)this.getSqlMapClientTemplate().queryForList(getEntityName() + ".statisticReporter");
	}
	
	public int getNeedReporter(){
		return Integer.parseInt((String) this.getSqlMapClientTemplate().queryForObject(getEntityName() + ".getNeedReporter"));
	}

	
}