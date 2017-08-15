package com.ay.report.reporting.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.report.reporting.pojo.Reporting;
import com.ay.report.reporting.service.ReportingService;
import com.ay.report.reversion.pojo.Reversion;
import com.ay.report.reversion.service.ReversionService;

@SuppressWarnings("all")
public class ReportingAction extends BaseAction {
	private ReportingService reportingService;
	private ReversionService reversionService;
	private Reporting reporting;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	private String nowUser;
	private String creator;
	
	// 处理中标记
	private String doing;
	
	// 批复相关
	private String reType;
	private String context;
	
	public void add() {
		OperateInfo operateInfo = new OperateInfo(true);
		try {
			reporting.setSteps("1");
			if(reporting.getReportType().equals("日报")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				String dt = sdf.format(new Date());
				String autoTitle = dt+"日报";
				reporting.setAutoTitle(autoTitle);
			}else if(reporting.getReportType().equals("月报")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
				String dt = sdf.format(new Date());
				String autoTitle = dt+"月报";
				reporting.setAutoTitle(autoTitle);
			}
			reportingService.insert(reporting);
			operateInfo.setOperateMessage("添加成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			operateInfo.setOperateMessage("添加失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	/**
	 * 提交操作
	 */
	public void addSubmit() {
		OperateInfo operateInfo = new OperateInfo(true);
		try {
			reporting.setSteps("2");
			reporting.setChecker("0700b93b1c3c4c9d86a3f0d750c79202");
			reporting.setInformre(nowUser);
			if(reporting.getReportType().equals("日报")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				String dt = sdf.format(new Date());
				String autoTitle = dt+"日报";
				reporting.setAutoTitle(autoTitle);
			}else if(reporting.getReportType().equals("月报")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
				String dt = sdf.format(new Date());
				String autoTitle = dt+"月报";
				reporting.setAutoTitle(autoTitle);
			}
			reportingService.insert(reporting);
			operateInfo.setOperateMessage("添加成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			operateInfo.setOperateMessage("添加失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void update() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = reportingService.update(reporting);
		if (flag) {
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	/**
	 * 批示
	 */
	public void instRev(){
		OperateInfo operateInfo = new OperateInfo();
		reporting.setSteps("3");
		reporting.setChecker(" ");
		String informer = reporting.getInformre();
		informer += nowUser;
		reporting.setInformre(informer);
		boolean flag = reportingService.update(reporting);
		Reversion rv = new Reversion();
		
		rv.setContext(context);
		rv.setReType(reType);
		rv.setRever(nowUser);
		rv.setConvertId(reporting.getId());
		reversionService.insert(rv);
		if (flag) {
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void seeReview(){
		Struts2Utils.renderJson(reportingService.seeReview(id),
				EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}
	
	/**
	 * 提交操作
	 */
	public void updateSubmit() {
		OperateInfo operateInfo = new OperateInfo();
		reporting.setSteps("2");
		reporting.setChecker("0700b93b1c3c4c9d86a3f0d750c79202");
		String informer = reporting.getInformre();
		informer += nowUser;
		reporting.setInformre(informer);
		boolean flag = reportingService.update(reporting);
		if (flag) {
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void delete() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = false;
		if (ids.contains(",")) {
			String idsParam[] = ids.split(",");
			flag = reportingService.delete(idsParam);
		} else {
			flag = reportingService.delete(ids);
		}
		if (flag) {
			operateInfo.setOperateMessage("删除成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("删除失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);		
	}
	
	public void list() {
		DataStore<Reporting> dataStore = new DataStore<Reporting>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		if(reporting!=null && reporting.getCheckerinob()!=null && !reporting.getCheckerinob().equals("")){
			reporting.setSteps("1");
		}else if(creator != null && !creator.equals("")){
			reporting.setCreated(creator);
		}
		Map paramMap = BeanUtil.Bean2Map(reporting);
		Page resultPage = reportingService.pageQuery(paramMap, pageTemp);
		List<Reporting> resultList = (List<Reporting>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void getById() {
		Struts2Utils.renderJson(reportingService.getById(id),
				EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);		
	}
public String exp(){
		String where = " where 1=1 ";
		 Map map = BeanUtil.Bean2Map(reporting);
		if (map != null){
			for (Object o : map.keySet()){
				where += " and " + o.toString() + " like '%" + map.get(o) + "%' ";
			}
		}
excelQuerySql = "		select * from sys_user" + where;
		System.out.println(excelQuerySql);
		excelSheetName = "测试名称";
		excelHeads = new String[]{ "名字1", "名字2", "名字3" };
		return "exp";
}	



	
    public ReversionService getReversionService() {
	return reversionService;
}

public void setReversionService(ReversionService reversionService) {
	this.reversionService = reversionService;
}

public String getReType() {
	return reType;
}

public void setReType(String reType) {
	this.reType = reType;
}

public String getContext() {
	return context;
}

public void setContext(String context) {
	this.context = context;
}

	public String getNowUser() {
	return nowUser;
}

public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

public void setNowUser(String nowUser) {
	this.nowUser = nowUser;
}

	public Reporting getReporting() {
		return reporting;
	}

	public void setReporting(Reporting reporting) {
		this.reporting = reporting;
	}

	public ReportingService getReportingService() {
		return reportingService;
	}

	public void setReportingService(ReportingService reportingService) {
		this.reportingService = reportingService;
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

	public String getDoing() {
		return doing;
	}

	public void setDoing(String doing) {
		this.doing = doing;
	}
	
	
	
}
