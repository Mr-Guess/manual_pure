package com.ay.report.reporting.dao;

import com.ay.report.reporting.dto.ReportingDto;
import com.ay.report.reporting.pojo.Reporting;

import java.io.Serializable;
import java.util.List;

import com.ay.framework.core.dao.BaseDao;

public class ReportingDao extends BaseDao<Reporting> {
	@Override
	public String getEntityName() {
		return "Reporting";
	}
	@Override
	public String getTableName() {
		return "TB_REPORTING";
	}

	public ReportingDto seeReview(String id){
			return (ReportingDto) getSqlMapClientTemplate().queryForObject(
					getEntityName() + ".reViewById", id);
	}
	
	public List<Reporting> getMyList(String id){
		return (List<Reporting>)getSqlMapClientTemplate().queryForList(
				getEntityName() + ".getMyList", id);
	}
	
	public int getMyListCount(String id){
		return (Integer)getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".myListcount", id);
	}
}