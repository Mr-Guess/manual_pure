package com.ay.framework.shiro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ay.jfds.sys.dto.UserDTO;
import com.ay.jfds.sys.pojo.ByWatchersInfo;
import com.ay.jfds.sys.pojo.Watch;
import com.ay.jfds.sys.service.ByWatchersInfoService;
import com.ay.jfds.sys.service.WatchService;

/** 
 * @Description 系统监控模块
 * @date 2014年3月6日
 * @author WangXin
 */
@Component
public class SystemWatch {
	private static Logger logger = LoggerFactory.getLogger(SystemWatch.class);
	private static WatchService watchService;
	private static ByWatchersInfoService byWatchersInfoService;
	private static List<Watch> watchs = null;
	private static List<ByWatchersInfo> byWatchers = null;
	private static Map<String, String[]> modulesMap = null;
	private static Map<String, String[]> byWatchersMap = null;
	
	@Resource
	public void setWatchService(WatchService watchService) {
		SystemWatch.watchService = watchService;
		
	}
	@Resource
	public void setByWatchersInfoService(ByWatchersInfoService byWatchersInfoService) {
		SystemWatch.byWatchersInfoService = byWatchersInfoService;
	}
	@PostConstruct
	public static void init() {
		synchronized (SystemWatch.class) {
			modulesMap = new HashMap<String, String[]>();
			byWatchersMap = new HashMap<String, String[]>();
			watchs = watchService.query(null);
			byWatchers = byWatchersInfoService.query(null);
			for (Watch watch : watchs) {
				modulesMap.put(watch.getId(), watch.getModules().split(","));
//				String byWatchersMaper = "";
//				for(ByWatchersInfo bi : byWatchers){
//					if(watch.getId().equals(bi.getWatchId())){
//						if(byWatchersMaper.length()>0){
//							byWatchersMaper = ",";
//						}
//						byWatchersMaper += bi.getByWatchersId();
//					}
//				}
				byWatchersMap.put(watch.getId(), watch.getByWatchers().split(","));
			}
			logger.info("watchs init complete");
		}
	}
	/**
	 * 找出当前用户适用的Watch ids
	 * @return void
	 */
	public static void findUserWatchIds() {
		Session session = SecurityUtils.getSubject().getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");
		String orgTree = user.getOrgTree();
		if(orgTree == null) return;
		if(orgTree.startsWith("-")) orgTree = orgTree.substring(1);
		String org = orgTree + "-" + user.getId();
		String[] ids = org.split("-");
		Set<String> watchIds = new HashSet<String>();
		for (Watch watch : watchs) {
			for (String id : ids) {
				if(watch.getWatchers().contains(id)) {
					watchIds.add(watch.getId());
					break;
				}
			}
		}
		session.setAttribute("watchIds", watchIds);
	}
	
	
	/**
	 * 根据具体的模块 找出当前用户 监控的orgIds
	 * @param tableName
	 * @return
	 * @return Set<String>
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> findUserModuleOrgIds(String tableName) {
		Session session = SecurityUtils.getSubject().getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");
		Set<String> watchIds = (Set<String>) session.getAttribute("watchIds");
		Set<String> orgIds = new HashSet<String>();
		if(watchIds != null) {
			for (String id : watchIds) {
				String[] modules = modulesMap.get(id);
				for (String module : modules) {
					if(module != null && module.toLowerCase().equals(tableName)) {
						String[] byWatchers = byWatchersMap.get(id);
						for (String byWatcher : byWatchers) {
							orgIds.add(byWatcher);
						}
						break;
					}
				}
			}
		}
		orgIds.add(user.getId());
		return orgIds;
	}
}

