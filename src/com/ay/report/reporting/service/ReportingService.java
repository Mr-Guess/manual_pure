package com.ay.report.reporting.service;

import com.ay.report.reporting.dao.ReportingDao;
import com.ay.report.reporting.dto.ReportingDto;
import com.ay.report.reporting.pojo.Reporting;
import com.ay.framework.core.service.BaseService;

public class ReportingService extends BaseService<Reporting, ReportingDao> {

	public ReportingDto seeReview(String id){
		return dao.seeReview(id);
	}
}