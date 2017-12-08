package com.yk.businesses.statistics.service;

import com.yk.businesses.statistics.dao.StatisticsDao;
import com.yk.businesses.statistics.pojo.Statistics;

import java.util.List;

import com.ay.framework.core.service.BaseService;

public class StatisticsService extends BaseService<Statistics, StatisticsDao> {
	public List<Statistics> statisticReport(){
		return dao.statisticReport();
	}
	
	public List<Statistics> statisticReporter(){
		return dao.statisticReporter();
	}
	
	public int getNeedReporter(){
		return dao.getNeedReporter();
	}
}