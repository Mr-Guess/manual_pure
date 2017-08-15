package com.ay.jfds.sys.squser;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

/** 
 * @项目名称:zhaj2.0
 * @类名:Squser.java 
 * @描述:社区账号配置
 * @创建人: 雷远亮 
 * @创建时间: 2015年4月7日下午1:59:02 
 * @修改人: 
 * @修改时间: 
 * @修改备注：
 * @version 2.0
 */
//@ChineseName("社区账号配置")
public class Squser extends BasePojo {
	@ChineseName("城市代码")
	private String cityCode;
	@ChineseName("市")
	private String cityName;
	@ChineseName("区代码")
	private String areaCode;
	@ChineseName("区名称")
	private String areaName;
	@ChineseName("街道代码")
	private String streetCode;
	@ChineseName("街道名称")
	private String streetName;
	@ChineseName("社区代码")
	private String communityCode;
	@ChineseName("社区名称")
	private String communityName;
	@ChineseName("社区负责人")
	private String sqfzr;
	@ChineseName("联系电话")
	private String telphone;
	@ChineseName("邮箱")
	private String email;
	@ChineseName("固定电话")
	private String telNo;
	@ChineseName("预留字段a")
	private String parama;
	@ChineseName("预留字段b")
	private String paramb;
	@ChineseName("预留字段c")
	private String paramc;
	@ChineseName("预留字段d")
	private String paramd;
	@ChineseName("预留字段e")
	private String parame;
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getStreetCode() {
		return streetCode;
	}
	public void setStreetCode(String streetCode) {
		this.streetCode = streetCode;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getCommunityCode() {
		return communityCode;
	}
	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getSqfzr() {
		return sqfzr;
	}
	public void setSqfzr(String sqfzr) {
		this.sqfzr = sqfzr;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getParama() {
		return parama;
	}
	public void setParama(String parama) {
		this.parama = parama;
	}
	public String getParamb() {
		return paramb;
	}
	public void setParamb(String paramb) {
		this.paramb = paramb;
	}
	public String getParamc() {
		return paramc;
	}
	public void setParamc(String paramc) {
		this.paramc = paramc;
	}
	public String getParamd() {
		return paramd;
	}
	public void setParamd(String paramd) {
		this.paramd = paramd;
	}
	public String getParame() {
		return parame;
	}
	public void setParame(String parame) {
		this.parame = parame;
	}
}
