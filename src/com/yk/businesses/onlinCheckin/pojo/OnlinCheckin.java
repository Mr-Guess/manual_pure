package com.yk.businesses.onlinCheckin.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

//@ChineseName("在线打卡")
public class OnlinCheckin extends BasePojo{

	private String checkinUser;
	
	private String checkeinTime;
	
	private String checkinAddress;
	
	private String picUrl;
	
	private String checkinType;
	
	private String fileName;
	
	private String contentType;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getCheckinType() {
		return checkinType;
	}

	public void setCheckinType(String checkinType) {
		this.checkinType = checkinType;
	}

	public String getCheckinUser() {
		return checkinUser;
	}

	public void setCheckinUser(String checkinUser) {
		this.checkinUser = checkinUser;
	}

	public String getCheckeinTime() {
		return checkeinTime;
	}

	public void setCheckeinTime(String checkeinTime) {
		this.checkeinTime = checkeinTime;
	}

	public String getCheckinAddress() {
		return checkinAddress;
	}

	public void setCheckinAddress(String checkinAddress) {
		this.checkinAddress = checkinAddress;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
