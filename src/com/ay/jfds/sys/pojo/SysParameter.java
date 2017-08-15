package com.ay.jfds.sys.pojo;

import com.ay.framework.core.pojo.BasePojo;

/**
 * @author PS
 * 
 * 系统参数POJO层
 */
@SuppressWarnings("all")
public class SysParameter extends BasePojo implements Comparable<SysParameter> {
	/**
	 * 参数代码
	 */
	private String paraCode;
	/**
	 * 参数名称
	 */
	private String paraName;
	/**
	 * 参数值
	 */
	private String paraValue;
	/**
	 * @return the paraCode
	 */
	public String getParaCode() {
		return paraCode;
	}
	/**
	 * @param paraCode the paraCode to set
	 */
	public void setParaCode(String paraCode) {
		this.paraCode = paraCode;
	}
	/**
	 * @return the paraName
	 */
	public String getParaName() {
		return paraName;
	}
	/**
	 * @param paraName the paraName to set
	 */
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	/**
	 * @return the paraValue
	 */
	public String getParaValue() {
		return paraValue;
	}
	/**
	 * @param paraValue the paraValue to set
	 */
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	
	public int compareTo(SysParameter o) {
		if (Integer.parseInt(this.getId()) > Integer.parseInt(o.getId())) {
			return 1;
		} else if (Integer.parseInt(this.getId()) < Integer.parseInt(o.getId())) {
			return -1;
		}
		return 0;
	}

}
