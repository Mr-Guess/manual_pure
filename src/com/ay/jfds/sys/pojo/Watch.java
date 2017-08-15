package com.ay.jfds.sys.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

/** 
 * @Description 
 * @date 2014年3月6日
 * @author WangXin
 */
//@ChineseName("系统监控模块")
@SuppressWarnings("serial")
public class Watch extends BasePojo {
	@ChineseName("监控者")
	private String watchers;
	private String watchersShow;
	@ChineseName("模块")
	private String modules;
	private String modulesShow;
	@ChineseName("被监控者")
	private String byWatchers;
	private String byWatchersShow;
	
	private String watchType;
	
	/**
	 * 名称，不加入持久化
	 */
	private String name;
	private String tableName;
	
	
	public String getWatchType() {
		return watchType;
	}
	public void setWatchType(String watchType) {
		this.watchType = watchType;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWatchers() {
		return watchers;
	}
	public void setWatchers(String watchers) {
		this.watchers = watchers;
	}
	public String getModules() {
		return modules;
	}
	public void setModules(String modules) {
		this.modules = modules;
	}
	public String getByWatchers() {
		return byWatchers;
	}
	public void setByWatchers(String byWatchers) {
		this.byWatchers = byWatchers;
	}
	public String getWatchersShow() {
		return watchersShow;
	}
	public void setWatchersShow(String watchersShow) {
		this.watchersShow = watchersShow;
	}
	public String getModulesShow() {
		return modulesShow;
	}
	public void setModulesShow(String modulesShow) {
		this.modulesShow = modulesShow;
	}
	public String getByWatchersShow() {
		return byWatchersShow;
	}
	public void setByWatchersShow(String byWatchersShow) {
		this.byWatchersShow = byWatchersShow;
	}
}

