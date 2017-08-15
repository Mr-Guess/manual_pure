package com.ay.jfds.dev.pojo;

import com.ay.framework.core.pojo.BasePojo;

public class Icon   extends BasePojo{
	private static final long serialVersionUID = 1L;
	
	//图标编号
	private String icon_id;
	//图标名称
	private String icon_name;
	//图标路径
	private String icon_url;
	//图标说明
	private String icon_remark;
	//图标上传日期
	private String creation_date;
	//图标文件类型
	private String icon_type;
	//图标所属类别
	private String icon_leibie;
	
	public String getIcon_leibie() {
		return icon_leibie;
	}
	public void setIcon_leibie(String iconLeibie) {
		icon_leibie = iconLeibie;
	}
	public String getIcon_type() {
		return icon_type;
	}
	public void setIcon_type(String iconType) {
		icon_type = iconType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getIcon_id() {
		return icon_id;
	}
	public void setIcon_id(String iconId) {
		icon_id = iconId;
	}
	public String getIcon_name() {
		return icon_name;
	}
	public void setIcon_name(String iconName) {
		icon_name = iconName;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String iconUrl) {
		icon_url = iconUrl;
	}
	public String getIcon_remark() {
		return icon_remark;
	}
	public void setIcon_remark(String iconRemark) {
		icon_remark = iconRemark;
	}
	public String getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(String creationDate) {
		creation_date = creationDate;
	}
	
	public String toStriong(){
		return "id="+this.icon_id+",url="+this.icon_url+",type="+this.icon_type+",leibie="+this.icon_leibie;
	}
}
