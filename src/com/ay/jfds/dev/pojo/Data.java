package com.ay.jfds.dev.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ay.framework.core.pojo.BasePojo;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 元数据
 * 
 * @author PS
 * 
 */
@SuppressWarnings("all")
public class Data extends BasePojo {
	/** 类别ID */
	@JsonIgnore
	private String typeId;

	/** 数据编码 */
	private String dataCode;

	/** 数据名称 */
	private String dataName;

	/** 数据顺序 */
	private int dataOrder;

	/** 父节点ID */
	private String parentId;

	private String name;
	/**数据描述*/
	private String dataDesc;
	private List<Data> datas;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public int getDataOrder() {
		return dataOrder;
	}

	public void setDataOrder(int dataOrder) {
		this.dataOrder = dataOrder;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getDataDesc() {
		return dataDesc;
	}

	public void setDataDesc(String dataDesc) {
		this.dataDesc = dataDesc;
	}
	public Data() {
	}

	public Data(String dataCode, String dataName) {
		this.dataCode = dataCode;
		this.dataName = dataName;
	}

	public List<Data> getDatas() {
		return datas==null?new ArrayList<Data>():datas;
	}

	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}
	public void setDatas(Data data){
		this.datas.add(data);
	}
}
