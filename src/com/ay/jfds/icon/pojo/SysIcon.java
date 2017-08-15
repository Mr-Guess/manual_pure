package com.ay.jfds.icon.pojo;

import java.sql.Blob;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

@SuppressWarnings("all")
//@ChineseName("系统图标")
public class SysIcon extends BasePojo{
	@ChineseName("图标编号")
	private String iconNo;
	@ChineseName("图标名称")
	private String name;
	@ChineseName("图标")
	private byte[] icon;
	@ChineseName("图标描述")
	private String iconDesc;
	@ChineseName("图标类型")
	private String type;
	private Blob blob;
	
	public String getIconNo() {
		return iconNo;
	}
	public void setIconNo(String iconNo) {
		this.iconNo = iconNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public String getIconDesc() {
		return iconDesc;
	}
	public void setIconDesc(String iconDesc) {
		this.iconDesc = iconDesc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Blob getBlob() {
		return blob;
	}
	public void setBlob(Blob blob) {
		this.blob = blob;
	}
}
