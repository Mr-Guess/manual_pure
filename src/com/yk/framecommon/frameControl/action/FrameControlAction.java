package com.yk.framecommon.frameControl.action;

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
import com.yk.framecommon.frameControl.pojo.FrameControl;
import com.yk.framecommon.frameControl.service.FrameControlService;
import com.ay.framework.core.action.BaseAction;

@SuppressWarnings("all")
public class FrameControlAction extends BaseAction {
	private FrameControlService frameControlService;
	private FrameControl frameControl;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	
	public void add() {
		OperateInfo operateInfo = new OperateInfo(true);
		try {
			frameControlService.insert(frameControl);
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
		boolean flag = frameControlService.update(frameControl);
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
			flag = frameControlService.delete(idsParam);
		} else {
			flag = frameControlService.delete(ids);
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
		DataStore<FrameControl> dataStore = new DataStore<FrameControl>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = BeanUtil.Bean2Map(frameControl);
		Page resultPage = frameControlService.pageQuery(paramMap, pageTemp);
		List<FrameControl> resultList = (List<FrameControl>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void getById() {
		Struts2Utils.renderJson(frameControlService.getById(id),
				EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);		
	}
public String exp(){
		String where = " where 1=1 ";
		 Map map = BeanUtil.Bean2Map(frameControl);
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
    public FrameControl getFrameControl() {
		return frameControl;
	}

	public void setFrameControl(FrameControl frameControl) {
		this.frameControl = frameControl;
	}

	public FrameControlService getFrameControlService() {
		return frameControlService;
	}

	public void setFrameControlService(FrameControlService frameControlService) {
		this.frameControlService = frameControlService;
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
