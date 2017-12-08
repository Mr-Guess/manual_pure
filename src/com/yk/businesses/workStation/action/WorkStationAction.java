package com.yk.businesses.workStation.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.report.reporting.pojo.Reporting;
import com.ay.report.reporting.service.ReportingService;
import com.yk.businesses.borrow.pojo.Borrow;
import com.yk.businesses.borrow.service.BorrowService;
import com.yk.businesses.reimburse.pojo.Reimburse;
import com.yk.businesses.reimburse.service.ReimburseService;
import com.yk.businesses.workStation.pojo.WorkStation;
import com.yk.businesses.workStation.service.WorkStationService;
import com.ay.framework.core.action.BaseAction;

@SuppressWarnings("all")
public class WorkStationAction extends BaseAction {
	private WorkStationService workStationService;
	private WorkStation workStation;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	
	public void getWorkStationDataByUser(){
		List<WorkStation> outList = workStationService.getWorkStationDataByUser(id);
		Struts2Utils.renderJson(outList);
	}
	
    public WorkStation getWorkStation() {
		return workStation;
	}

	public void setWorkStation(WorkStation workStation) {
		this.workStation = workStation;
	}

	public WorkStationService getWorkStationService() {
		return workStationService;
	}

	public void setWorkStationService(WorkStationService workStationService) {
		this.workStationService = workStationService;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
