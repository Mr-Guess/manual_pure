package com.ay.jfds.create.pojo;

import java.util.ArrayList;
import java.util.List;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @项目名称:jfds2.0
 * @类名称:Sheet
 * @类描述:创建表
 * @创建人:雷远亮
 * @创建时间:2014年6月23日11:05:08
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @version 2.0
 */
@SuppressWarnings("all")
//@ChineseName("表信息")
public class Sheet extends BasePojo{
	@ChineseName("英文表名")
	private String nameEn;
	@ChineseName("中文名称")
	private String nameCh;
	@ChineseName("是否启用")
	private String enable;
	private List<Columns> columns;
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getNameCh() {
		return nameCh;
	}
	public void setNameCh(String nameCh) {
		this.nameCh = nameCh;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	public List<Columns> getColumns() {
		return columns;
	}
	public void setColumns(List<Columns> columns) {
		this.columns = columns;
	}
	/**
	 * 重写set 添加一个
	 * @param columns
	 */
	
	public void setColumns(Columns columns){
		if(this.columns==null){
			this.columns=new ArrayList<Columns>();
		}
		this.columns.add(columns);
	}
	
}
