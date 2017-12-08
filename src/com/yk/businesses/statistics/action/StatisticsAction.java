package com.yk.businesses.statistics.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.yk.businesses.statistics.pojo.Statistics;
import com.yk.businesses.statistics.service.StatisticsService;
import com.ay.framework.core.action.BaseAction;

@SuppressWarnings("all")
public class StatisticsAction extends BaseAction {
	private StatisticsService statisticsService;
	private Statistics statistics;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	
    public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public StatisticsService getStatisticsService() {
		return statisticsService;
	}

	public void setStatisticsService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
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
	
	
	public void statisticReport(){
		int getNeedReporter = statisticsService.getNeedReporter();
		List<Statistics> list = statisticsService.statisticReport();
		StringBuffer sb = new StringBuffer();
		for(Statistics s : list){
			sb.append(s.getValue());
			sb.append(s.getNum());
			sb.append("人，");
		}
		String out = "共计"+getNeedReporter+"人，其中"+sb.toString();
		out = out.substring(0, out.length()-1)+"。";
		Struts2Utils.renderText(out);
	}
	
	public void statisticReporter(){
		int getNeedReporter = statisticsService.getNeedReporter();
		List<Statistics> list = statisticsService.statisticReporter();
		
		StringBuffer submitedSB = new StringBuffer();
		StringBuffer unSubmitedSB = new StringBuffer();
		int countSubmit = 0;
		int countUnSub = 0;
		for(Statistics s : list){
			if(s.getValue().equals("未提交人名单")){
				unSubmitedSB.append(s.getNum()+",");
				countUnSub ++;
			}else{
				submitedSB.append(s.getNum()+",");
				countSubmit ++;
			}
		}
		

		Map<String, String> map = new HashMap<String,String>();
		
		if(countSubmit>0){
			map.put("tijiaoren", submitedSB.substring(0, submitedSB.length()-1));
		}else{
			map.put("tijiaoren", submitedSB.toString());
		}
		map.put("tijiaorenshuliang", countSubmit+"");
		
		if(countUnSub>0){ 
			map.put("weitijiaoren", unSubmitedSB.substring(0,unSubmitedSB.length()-1));
		}else{
			map.put("weitijiaoren", unSubmitedSB.toString());
		}
		map.put("weitijiaorenshuliang", countUnSub+"");
		
		map.put("total", getNeedReporter+"");
		Struts2Utils.renderJson(map);
		
	}
	
}
