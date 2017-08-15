package com.ay.report.reporting.dao;

import com.ay.report.reporting.dto.ReportingDto;
import com.ay.report.reporting.pojo.Reporting;

import java.io.Serializable;

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
}