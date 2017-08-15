package com.ay.jfds.test;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

/** 
 * @Description 
 * @date 2014年3月6日
 * @author WangXin
 */
//@ChineseName("测试")
public class Test extends BasePojo {
	@ChineseName("名称")
	private String name;
	@ChineseName("风格")
	private String style;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
}

