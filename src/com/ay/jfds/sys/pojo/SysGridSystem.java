package com.ay.jfds.sys.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

/** 
 * @项目名称:jfds2.0
 * @类名:SysGridSystem.java 
 * @描述: 网格体系对象
 * @创建人: 周伟 
 * @创建时间: 2014年11月18日下午3:48:33 
 * @修改人: 
 * @修改时间: 
 * @修改备注：
 * @version 2.0
 */

//@ChineseName("网格体系")
public class SysGridSystem extends BasePojo {
	
	@ChineseName("主要负责人")
	private String zyfzr;
	
	@ChineseName("主要负责人安全职责")
	private String zyfzrAqzz;
	
	@ChineseName("分管负责人")
	private String fgfzr;
	
	@ChineseName("分管负责人安全职责")
	private String fgfzrAqzz;
	
	@ChineseName("党政挂点领导")
	private String dzgdld;
	
	@ChineseName("党政挂点领导安全职责")
	private String dzgdldAqzz;
	
	@ChineseName("部门挂点领导")
	private String bmgdld;
	
	@ChineseName("部门挂点领导安全职责")
	private String bmgdldAqzz;
	
	@ChineseName("属地监管责任")
	private String sdjgzr;
	
	@ChineseName("目标责任书")
	private String mbzrs;
	
	@ChineseName("关联部门ID")
	private String bmid;

	public String getZyfzr() {
		return zyfzr;
	}

	public void setZyfzr(String zyfzr) {
		this.zyfzr = zyfzr;
	}

	public String getZyfzrAqzz() {
		return zyfzrAqzz;
	}

	public void setZyfzrAqzz(String zyfzrAqzz) {
		this.zyfzrAqzz = zyfzrAqzz;
	}

	public String getFgfzr() {
		return fgfzr;
	}

	public void setFgfzr(String fgfzr) {
		this.fgfzr = fgfzr;
	}

	public String getFgfzrAqzz() {
		return fgfzrAqzz;
	}

	public void setFgfzrAqzz(String fgfzrAqzz) {
		this.fgfzrAqzz = fgfzrAqzz;
	}

	public String getDzgdld() {
		return dzgdld;
	}

	public void setDzgdld(String dzgdld) {
		this.dzgdld = dzgdld;
	}

	public String getDzgdldAqzz() {
		return dzgdldAqzz;
	}

	public void setDzgdldAqzz(String dzgdldAqzz) {
		this.dzgdldAqzz = dzgdldAqzz;
	}

	public String getBmgdld() {
		return bmgdld;
	}

	public void setBmgdld(String bmgdld) {
		this.bmgdld = bmgdld;
	}

	public String getBmgdldAqzz() {
		return bmgdldAqzz;
	}

	public void setBmgdldAqzz(String bmgdldAqzz) {
		this.bmgdldAqzz = bmgdldAqzz;
	}

	public String getSdjgzr() {
		return sdjgzr;
	}

	public void setSdjgzr(String sdjgzr) {
		this.sdjgzr = sdjgzr;
	}

	public String getMbzrs() {
		return mbzrs;
	}

	public void setMbzrs(String mbzrs) {
		this.mbzrs = mbzrs;
	}

	public String getBmid() {
		return bmid;
	}

	public void setBmid(String bmid) {
		this.bmid = bmid;
	}
	
}
