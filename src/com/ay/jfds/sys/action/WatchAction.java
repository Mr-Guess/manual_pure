package com.ay.jfds.sys.action;

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
import com.ay.framework.shiro.SystemWatch;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.jfds.sys.pojo.ByWatchersInfo;
import com.ay.jfds.sys.pojo.Watch;
import com.ay.jfds.sys.service.ByWatchersInfoService;
import com.ay.jfds.sys.service.WatchService;
import com.ay.framework.core.action.BaseAction;

@SuppressWarnings("all")
public class WatchAction extends BaseAction {
	private WatchService watchService;
	private ByWatchersInfoService byWatchersInfoService;
	private Watch watch;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	
	public void add() {
		OperateInfo operateInfo = new OperateInfo(true);
		try {
			watchService.insert(watch);
			List<ByWatchersInfo> list = new ArrayList<ByWatchersInfo>();
			String[] byWatchers = watch.getByWatchers().split(",");
			String[] byWatchersName = watch.getByWatchersShow().split(",");
			for(int i = 0; i< byWatchers.length; i++){
				ByWatchersInfo bwi = new ByWatchersInfo();
				bwi.setByWatchersName(byWatchersName[i]);
				bwi.setByWatchersId(byWatchers[i]);
				bwi.setWatchId(watch.getId());
				list.add(bwi);
			}
			for(ByWatchersInfo bi : list){
				byWatchersInfoService.insert(bi);
			}
			operateInfo.setOperateMessage("添加成功");
			operateInfo.setOperateSuccess(true);
			SystemWatch.init();
			SystemWatch.findUserWatchIds();
		} catch (Exception e) {
			e.printStackTrace();
			operateInfo.setOperateMessage("添加失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void saveOrUpdate(){
		OperateInfo operateInfo = new OperateInfo(true);
		
		// 判断数据是否为空
		if(watch == null){
			operateInfo.setOperateMessage("数据错误！");
			operateInfo.setOperateSuccess(false);
		}else{
			// 获取ID查询是否存在，如存在则进行更新，如不存在则进行修改
			String getId = watch.getId();
			if(watchService.isExistCount(getId)){
				boolean flag = watchService.update(watch);
				byWatchersInfoService.delete(watch.getId());
				List<ByWatchersInfo> list = new ArrayList<ByWatchersInfo>();
				String[] byWatchers = watch.getByWatchers().split(",");
				String[] byWatchersName = watch.getByWatchersShow().split(",");
				for(int i = 0; i< byWatchers.length; i++){
					ByWatchersInfo bwi = new ByWatchersInfo();
					bwi.setByWatchersName(byWatchersName[i]);
					bwi.setByWatchersId(byWatchers[i]);
					bwi.setWatchId(watch.getId());
					list.add(bwi);
				}
				for(ByWatchersInfo bi : list){
					byWatchersInfoService.insert(bi);
				}
				if (flag) {
					operateInfo.setOperateMessage("更新成功");
					operateInfo.setOperateSuccess(true);
					SystemWatch.init();
					SystemWatch.findUserWatchIds();
				} else {
					operateInfo.setOperateMessage("更新失败");
					operateInfo.setOperateSuccess(false);
				}
			}else{
				try {
					watchService.insert(watch);
					List<ByWatchersInfo> list = new ArrayList<ByWatchersInfo>();
					String[] byWatchers = watch.getByWatchers().split(",");
					String[] byWatchersName = watch.getByWatchersShow().split(",");
					for(int i = 0; i< byWatchers.length; i++){
						ByWatchersInfo bwi = new ByWatchersInfo();
						bwi.setByWatchersName(byWatchersName[i]);
						bwi.setByWatchersId(byWatchers[i]);
						bwi.setWatchId(watch.getId());
						list.add(bwi);
					}
					for(ByWatchersInfo bi : list){
						byWatchersInfoService.insert(bi);
					}
					operateInfo.setOperateMessage("添加成功");
					operateInfo.setOperateSuccess(true);
					SystemWatch.init();
					SystemWatch.findUserWatchIds();
				} catch (Exception e) {
					e.printStackTrace();
					operateInfo.setOperateMessage("添加失败");
					operateInfo.setOperateSuccess(false);
				}
			}
			
			String json = new JsonMapper().toJson(operateInfo);
			Struts2Utils.renderText(json);
		}
	}
	
	public void update() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = watchService.update(watch);
		if (flag) {
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
			SystemWatch.init();
			SystemWatch.findUserWatchIds();
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
			byWatchersInfoService.delete(idsParam);
			flag = watchService.delete(idsParam);
		} else {
			byWatchersInfoService.delete(ids);
			flag = watchService.delete(ids);
		}
		if (flag) {
			operateInfo.setOperateMessage("删除成功");
			operateInfo.setOperateSuccess(true);
			SystemWatch.init();
			SystemWatch.findUserWatchIds();
		} else {
			operateInfo.setOperateMessage("删除失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);		
	}
	
	public void list() {
		DataStore<Watch> dataStore = new DataStore<Watch>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = BeanUtil.Bean2Map(watch);
		Page resultPage = watchService.pageQuery(paramMap, pageTemp);
		List<Watch> resultList = (List<Watch>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void getById() {
		Struts2Utils.renderJson(watchService.getById(id),
				EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);		
	}
	
	public void getAllTable(){
		List<String[]> lis = watchService.getAllTable();
		String json = "[";
		for (String[] string : lis) {
			if(json.lastIndexOf("}") == json.length()-1){
				json += ",";
			}
			json+="{'id':'"+string[1]+"','name':'"+string[0]+"'}";
		}
		json+="]";
		Struts2Utils.renderText(json);
	}
	
	public void getAllEntTable(){
		List<String[]> lis = watchService.getAllEntTable();
		String json = "[";
		for (String[] string : lis) {
			if(json.lastIndexOf("}") == json.length()-1){
				json += ",";
			}
			json+="{'id':'"+string[1]+"','name':'"+string[0]+"'}";
		}
		json+="]";
		Struts2Utils.renderText(json);
	}
	
	
	
    public ByWatchersInfoService getByWatchersInfoService() {
		return byWatchersInfoService;
	}

	public void setByWatchersInfoService(ByWatchersInfoService byWatchersInfoService) {
		this.byWatchersInfoService = byWatchersInfoService;
	}

	public Watch getWatch() {
		return watch;
	}

	public void setWatch(Watch watch) {
		this.watch = watch;
	}

	public WatchService getWatchService() {
		return watchService;
	}

	public void setWatchService(WatchService watchService) {
		this.watchService = watchService;
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
