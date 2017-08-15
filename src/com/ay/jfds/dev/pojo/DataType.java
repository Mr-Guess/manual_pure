package com.ay.jfds.dev.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ay.framework.core.pojo.BasePojo;

/**
 * 元数据类别
 * 
 * @author PS
 * 
 */
public class DataType extends BasePojo {

	/** 类别名称 */
	private String typeName;
	
	private List<Data> datas;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	

	public List<Data> getDatas() {
		return datas==null?datas=new ArrayList<Data>():datas;
	}

	public void setDatas(List<Data> datas) {
		if(datas==null)datas=new ArrayList<Data>();
		this.datas = datas;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public DataType() {
		
	}

	public DataType(String id,String typeName) {
		this.setId(id);
		this.typeName = typeName;
	}
	
}
