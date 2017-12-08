package com.yk.businesses.statistics.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

//@ChineseName("统计通用类")
public class Statistics extends BasePojo{

	private String shown;
	
	private String value;
	
	private String num;

	public String getShown() {
		return shown;
	}

	public void setShown(String shown) {
		this.shown = shown;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	
	
}
