package com.ay.jfds.create.pojo;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;
/**
 * 
 * @项目名称:jfds2.0
 * @类名称:Mobule
 * @类描述:模块
 * @创建人:雷远亮
 * @创建时间:2014年7月2日15:38:32
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @version 2.0
 */
@SuppressWarnings("serial")
//@ChineseName("模块分类")
public class Mobule extends BasePojo{
	@ChineseName("模块分类")
	private String type;	
	@ChineseName("模块Code")
	private String mobuleId;
	@ChineseName("模块名")
	private String mobuleName;
	@ChineseName("模块描述")
	private String mobuleDesc;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMobuleId() {
		return mobuleId;
	}
	public void setMobuleId(String mobuleId) {
		this.mobuleId = mobuleId;
	}
	public String getMobuleName() {
		return mobuleName;
	}
	public void setMobuleName(String mobuleName) {
		this.mobuleName = mobuleName;
	}
	public String getMobuleDesc() {
		return mobuleDesc;
	}
	public void setMobuleDesc(String mobuleDesc) {
		this.mobuleDesc = mobuleDesc;
	}
}
