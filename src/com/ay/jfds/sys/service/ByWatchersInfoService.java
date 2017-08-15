package com.ay.jfds.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.ay.jfds.sys.dao.ByWatchersInfoDao;
import com.ay.jfds.sys.pojo.ByWatchersInfo;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.service.BaseService;

public class ByWatchersInfoService extends BaseService<ByWatchersInfo, ByWatchersInfoDao> {
	
	/**
	 * 分页查询.
	 * 
	 * @param map
	 *            the map
	 * @param page
	 *            the page
	 * @return the page
	 */
	public Page pageQueryByWatchers(Map map, Page page) {
        	String userType = (String) SecurityUtils.getSubject().getSession()
        		.getAttribute("usertype");
        	String userId = (String) SecurityUtils.getSubject().getSession()
        		.getAttribute("user_id");
        	if (map == null) {
        	    map = new HashMap();
        	}
		try {
			page.setCount(this.count(map));
			List<ByWatchersInfo> list = this.getDao().pageQueryBywatchers(map, page.getFrom(), page
					.getRecPerPage());
			page.setCollection(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
}