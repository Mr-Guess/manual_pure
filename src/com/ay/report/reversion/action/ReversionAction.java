package com.ay.report.reversion.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.jfds.sys.pojo.User;
import com.ay.jfds.sys.service.UserService;
import com.ay.netEasy.CommonFunction;
import com.ay.netEasy.im.ConnectToApi;
import com.ay.netEasy.push.AttachMsg;
import com.ay.report.reversion.pojo.Reversion;
import com.ay.report.reversion.service.ReversionService;
import com.ay.framework.core.action.BaseAction;

@SuppressWarnings("all")
public class ReversionAction extends BaseAction {
	private ReversionService reversionService;
	private UserService userService;
	private Reversion reversion;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	private String owner;
	private String reportingId;
	
	public void add() {
		OperateInfo operateInfo = new OperateInfo(true);
		try {
			reversionService.insert(reversion);
			User user = userService.getById(owner);
			JSONObject jo = new JSONObject(new AttachMsg("reversion", reportingId, "广泰集团", "[工作汇报]您的工作汇报已有最新批复，快去查看吧。",CommonFunction.getRandomInt()));
			ConnectToApi.pushMessage(user.getAccount(), jo.toString());
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
		boolean flag = reversionService.update(reversion);
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
			flag = reversionService.delete(idsParam);
		} else {
			flag = reversionService.delete(ids);
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
		DataStore<Reversion> dataStore = new DataStore<Reversion>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = BeanUtil.Bean2Map(reversion);
		Page resultPage = reversionService.pageQuery(paramMap, pageTemp);
		List<Reversion> resultList = (List<Reversion>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void reportRevList(){
		Map paramMap = BeanUtil.Bean2Map(reversion);
		List<Reversion> list = reversionService.findRepRev(paramMap);
		Struts2Utils.renderJson(list,
				EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}
	
	public void getById() {
		Struts2Utils.renderJson(reversionService.getById(id),
				EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);		
	}

	
    public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getOwner() {
	return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Reversion getReversion() {
		return reversion;
	}

	public void setReversion(Reversion reversion) {
		this.reversion = reversion;
	}

	public ReversionService getReversionService() {
		return reversionService;
	}

	public void setReversionService(ReversionService reversionService) {
		this.reversionService = reversionService;
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

	public String getReportingId() {
		return reportingId;
	}

	public void setReportingId(String reportingId) {
		this.reportingId = reportingId;
	}
	
	
}
