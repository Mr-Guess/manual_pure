package com.ay.jfds.dev.action;

import java.util.List;
import java.util.Map;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.framework.util.StringUtil;
import com.ay.jfds.dev.pojo.DevStatistics;
import com.ay.jfds.dev.service.DevStatisticsService;

/**
 * @Description 
 * @date 2012-10-26
 * @author WangXin
 */
public class DevStatisticsAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3365159605373696486L;
	private DevStatisticsService devStatisticsService;
	private DevStatistics entity;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;

	private String charttitle;
	
	public String execute(){
		freemarkerPath="/freemarker/test.ftl";
		return "freemarker";
	}
		
	public void list() {
		DataStore<DevStatistics> dataStore = new DataStore<DevStatistics>();
		Page pageTemp = new Page();
		// 当前页 
		int intPage = Integer.parseInt((page == null || "0".equals(page)) ? "1"
				: page);
		// 每页显示条数 
		int number = Integer.parseInt((rows == null || "0".equals(rows)) ? "10"
				: rows);
		int start = (intPage - 1) * number;
		pageTemp.setCurrentPage(start);
		pageTemp.setRecPerPage(number);

		// 设置查询条件
		Map paramMap = toParameterMap("charttitle");
		Page resultPage = devStatisticsService.pageQuery(paramMap, pageTemp);
		List<DevStatistics> resultList = (List<DevStatistics>) resultPage
				.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		Struts2Utils.renderJson(dataStore, EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}

	public void init() {
		Struts2Utils.renderJson(devStatisticsService.getById(id),
				EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}
	
	public void randerChart() {
		DevStatistics ds=devStatisticsService.getById(id);
		String sql=ds.getSqlcommand();
	}

	public void addOrUpdate() {
		OperateInfo operateInfo = new OperateInfo();
		if (StringUtil.isNullOrEmpty(entity.getId())) {
			devStatisticsService.insert(entity);
			operateInfo.setOperateMessage("保存成功");
			operateInfo.setOperateSuccess(true);
		} else {
			boolean flag = devStatisticsService.update(entity);
			if (flag) {
				operateInfo.setOperateMessage("更新成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateSuccess(false);
				operateInfo.setOperateMessage("更新失败");
			}
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	public void delete() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = false;
		if (ids.contains("[,]")) {
			String idsParam[] = ids.split("[,]");
			flag = devStatisticsService.delete(idsParam);
		} else {
			flag = devStatisticsService.delete(ids);
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

	public DevStatistics getEntity() {
		return entity;
	}

	public void setEntity(DevStatistics entity) {
		this.entity = entity;
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

	public DevStatisticsService getDevStatisticsService() {
		return devStatisticsService;
	}

	public void setDevStatisticsService(
			DevStatisticsService devStatisticsService) {
		this.devStatisticsService = devStatisticsService;
	}

	public String getCharttitle() {
		return charttitle;
	}

	public void setCharttitle(String charttitle) {
		this.charttitle = charttitle;
	}

}
