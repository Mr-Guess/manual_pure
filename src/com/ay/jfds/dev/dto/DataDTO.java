package com.ay.jfds.dev.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 元数据DTO为了做显示作用,可以关联数据类型避免二次查询
 * 
 * @author PS
 * 
 */
@SuppressWarnings("serial")
public class DataDTO implements Serializable {
	/** 元数据id */
	private String id;

	/** 类别id */
	private String typeId;

	/** 数据编码 */
	private String dataCode;

	/** 数据名称 */
	private String dataName;

	/** 数据顺序 */
	private int dataOrder;

	/** 删除标识 */
	private String status;

	/** 创建人 */
	private String created;

	/** 创建时间 */
	private Date createTime;

	/** 更新人 */
	private String updated;

	/** 更新时间 */
	private Date updateTime;

	/** 元数据类别名 */
	private String typeName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
