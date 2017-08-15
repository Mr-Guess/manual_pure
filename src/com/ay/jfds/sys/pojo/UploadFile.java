package com.ay.jfds.sys.pojo;

import com.ay.framework.core.pojo.BasePojo;

/**
 * 上传文件对象
 * @author wgw
 * @date 2013-3-12
 */
@SuppressWarnings("all")
public class UploadFile extends BasePojo{
	
	/**
	 * 关联对象的id
	 */
	private String relationId;
	
	/**
	 * 真实地址
	 */
	private String realPath;
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 文件备注
	 */
	private String remark;
	
	/**
	 * 关联模块的表名
	 */
	private String relationTable;

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRelationTable() {
		return relationTable;
	}

	public void setRelationTable(String relationTable) {
		this.relationTable = relationTable;
	}



	
	
}
