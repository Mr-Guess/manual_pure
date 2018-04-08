package com.yk.businesses.sendingCheckRecord.pojo;

import com.ay.framework.annotation.ChineseName;

@ChineseName("打卡记录")
public class SendingCheckRecord {

	@ChineseName("记录月份")
	private String recordDate;
	
	@ChineseName("发布日期")
	private String releaseTime;

	@ChineseName("记录月上班时间")
	private String onTime;
	
	@ChineseName("记录月下班时间")
	private String outTime;
	
	
	public String getOnTime() {
		return onTime;
	}

	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	
}
