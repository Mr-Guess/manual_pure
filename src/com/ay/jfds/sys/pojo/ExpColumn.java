package com.ay.jfds.sys.pojo;
/** 
 * @项目名称:zhaj2.0
 * @类名:ExpColumn.java 
 * @描述：导出实体类
 * @创建人: 雷远亮 
 * @创建时间: 2015年4月22日下午2:57:41 
 * @修改人: 
 * @修改时间: 
 * @修改备注：
 * @version 2.0
 */
public class ExpColumn {
	//列名
	private String fieldname;
	//通用类
	private Comment comment;
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
}
