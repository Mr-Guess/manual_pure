package com.yk.businesses.workStation.service;

import java.util.ArrayList;
import java.util.List;

import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.report.reporting.dao.ReportingDao;
import com.ay.report.reporting.pojo.Reporting;
import com.yk.businesses.borrow.dao.BorrowDao;
import com.yk.businesses.borrow.pojo.Borrow;
import com.yk.businesses.holiday.action.HolidayAction;
import com.yk.businesses.holiday.dao.HolidayDao;
import com.yk.businesses.holiday.pojo.Holiday;
import com.yk.businesses.reimburse.dao.ReimburseDao;
import com.yk.businesses.reimburse.pojo.Reimburse;
import com.yk.businesses.workStation.pojo.WorkStation;

public class WorkStationService{
	public List<WorkStation> getWorkStationDataByUser(String id){
		ReportingDao reportingDao = SpringContextHolder.getBean("reportingDao");
		ReimburseDao reimburseDao= SpringContextHolder.getBean("reimburseDao");
		BorrowDao borrowDao= SpringContextHolder.getBean("borrowDao");
		HolidayDao holidayDao = SpringContextHolder.getBean("holidayDao");
		// 工作汇报工作台数据
		WorkStation<Reporting> reportingWork = new WorkStation();
		List<Reporting> reportingList = reportingDao.getMyList(id);
		int reportingMumber = reportingDao.getMyListCount(id);
		reportingWork.setModle("reporting");
		reportingWork.setNumber(reportingMumber+"");
		reportingWork.setShowData(reportingList);
		
		//报销申请工作台数据
		WorkStation<Reimburse> reimburseWork = new WorkStation();
		List<Reimburse> reimburseList = reimburseDao.getMyList(id);
		int reimburseNumber = reimburseDao.getMyListCount(id);
		reimburseWork.setModle("reimburse");
		reimburseWork.setNumber(reimburseNumber+"");
		reimburseWork.setShowData(reimburseList);
		
		//借款申请工作台数据
		WorkStation<Borrow> borrowWork = new WorkStation();
		List<Borrow> borrowList = borrowDao.getMyList(id);
		int borrowNumber = borrowDao.getMyListCount(id);
		borrowWork.setModle("borrow");
		borrowWork.setNumber(borrowNumber+"");
		borrowWork.setShowData(borrowList);
		
		//请假工作台数据
		WorkStation<Holiday> holidayWork = new WorkStation();
		List<Holiday> holidayList = holidayDao.getMyList(id);
		int holidayNumber = holidayDao.getMyListCount(id);
		holidayWork.setModle("holiday");
		holidayWork.setNumber(holidayNumber+"");
		holidayWork.setShowData(holidayList);
		
		//组装列表数据
		List<WorkStation> returnList = new ArrayList<WorkStation>();
		returnList.add(reportingWork);
		returnList.add(reimburseWork);
		returnList.add(borrowWork);
		returnList.add(holidayWork);
		return returnList;
	}

}