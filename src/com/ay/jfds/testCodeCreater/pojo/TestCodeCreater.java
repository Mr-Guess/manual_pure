package com.ay.jfds.testCodeCreater.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

//@ChineseName("测试代码生成器")
public class TestCodeCreater extends BasePojo {
	
	@ChineseName("测试代码字段1")
	private String showView;
	
	@ChineseName("测试代码字段2")
	private String showVideo;

	public String getShowView() {
		return showView;
	}

	public void setShowView(String showView) {
		this.showView = showView;
	}

	public String getShowVideo() {
		return showVideo;
	}

	public void setShowVideo(String showVideo) {
		this.showVideo = showVideo;
	}
	
	
}
