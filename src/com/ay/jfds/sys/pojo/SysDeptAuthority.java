package com.ay.jfds.sys.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.annotation.Strategy;
import com.ay.framework.core.pojo.BasePojo;

/** 
 * @项目名称:jfds2.0
 * @类名:SysDeptAuthority.java 
 * @描述: 部门监管行业权限类
 * @创建人: 周伟 
 * @创建时间: 2014年10月31日上午11:30:28 
 * @修改人: 
 * @修改时间: 
 * @修改备注：
 * @version 2.0
 */
//@ChineseName("部门监管行业")
@Strategy(open = false)
public class SysDeptAuthority extends BasePojo {
	
	/**
	 * 时间：2014年10月31日 上午11:34:15
	 * 作者：周伟
	 */
	private static final long serialVersionUID = 1L;

	private String bmId;
	
	@ChineseName("监管行业")
	private String jghy;
	
	@ChineseName("地区码")
	private String dqm;
	
	@ChineseName("隶属关系")
	private String lsgx;
	
	@ChineseName("权限代码")
	private String qxdm;
	
	@ChineseName("节点类型(0:不能操作 1:已授权 2:未授权)")
	private String jdlx;

	public String getBmId() {
		return bmId;
	}

	public void setBmId(String bmId) {
		this.bmId = bmId;
	}

	public String getJghy() {
		return jghy;
	}

	public void setJghy(String jghy) {
		this.jghy = jghy;
	}

	public String getDqm() {
		return dqm;
	}

	public void setDqm(String dqm) {
		this.dqm = dqm;
	}

	public String getQxdm() {
		return qxdm;
	}

	public void setQxdm(String qxdm) {
		this.qxdm = qxdm;
	}

	public String getJdlx() {
		return jdlx;
	}

	public void setJdlx(String jdlx) {
		this.jdlx = jdlx;
	}

	public String getLsgx() {
		return lsgx;
	}

	public void setLsgx(String lsgx) {
		this.lsgx = lsgx;
	}
	
}
