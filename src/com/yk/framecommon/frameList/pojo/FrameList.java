package com.yk.framecommon.frameList.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

//@ChineseName("流程列表")
public class FrameList extends BasePojo{

	@ChineseName("流程名称")
	private String frameName;
	
	@ChineseName("流程代码")
	private String frameCode;

	public String getFrameName() {
		return frameName;
	}

	public void setFrameName(String frameName) {
		this.frameName = frameName;
	}

	public String getFrameCode() {
		return frameCode;
	}

	public void setFrameCode(String frameCode) {
		this.frameCode = frameCode;
	}
	
}
