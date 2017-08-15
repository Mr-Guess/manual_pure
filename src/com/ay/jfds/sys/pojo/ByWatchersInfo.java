package com.ay.jfds.sys.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

//@ChineseName("被监控者信息")
public class ByWatchersInfo extends BasePojo {

	@ChineseName("被监控者名称")
	private String byWatchersName;
	
	@ChineseName("被监控者编号")
	private String byWatchersId;
	
	@ChineseName("监控信息编号")
	private String watchId;
	
	@ChineseName("监控类型")
	private String byWatchersType;

	public String getByWatchersType() {
		return byWatchersType;
	}

	public void setByWatchersType(String byWatchersType) {
		this.byWatchersType = byWatchersType;
	}

	public String getByWatchersName() {
		return byWatchersName;
	}

	public void setByWatchersName(String byWatchersName) {
		this.byWatchersName = byWatchersName;
	}

	public String getByWatchersId() {
		return byWatchersId;
	}

	public void setByWatchersId(String byWatchersId) {
		this.byWatchersId = byWatchersId;
	}

	public String getWatchId() {
		return watchId;
	}

	public void setWatchId(String watchId) {
		this.watchId = watchId;
	}
	
	
}
