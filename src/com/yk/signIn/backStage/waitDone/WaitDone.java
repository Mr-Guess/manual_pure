package com.yk.signIn.backStage.waitDone;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

//@ChineseName("打卡机待处理事务")
public class WaitDone extends BasePojo{
	
	private String ssid;
	
	private String deviceSn;
	
	private String doneStatus;
	
	private String cmdCode;
	
	private String subCmd;
	
	private String cmdReturn;
	
	private String sendTime;
	
	private Object withinObj;
	
	public Object getWithinObj() {
		return withinObj;
	}

	public void setWithinObj(Object withinObj) {
		this.withinObj = withinObj;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public String getDoneStatus() {
		return doneStatus;
	}

	public void setDoneStatus(String doneStatus) {
		this.doneStatus = doneStatus;
	}

	public String getCmdCode() {
		return cmdCode;
	}

	public void setCmdCode(String cmdCode) {
		this.cmdCode = cmdCode;
	}

	public String getSubCmd() {
		return subCmd;
	}

	public void setSubCmd(String subCmd) {
		this.subCmd = subCmd;
	}

	public String getCmdReturn() {
		return cmdReturn;
	}

	public void setCmdReturn(String cmdReturn) {
		this.cmdReturn = cmdReturn;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
}
