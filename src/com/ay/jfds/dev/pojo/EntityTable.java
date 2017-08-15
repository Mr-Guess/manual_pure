package com.ay.jfds.dev.pojo;

import com.ay.framework.core.pojo.BasePojo;

public class EntityTable extends BasePojo {

	/** 实体编码，表英文名 */
	private String entityCode;
	/** 实体名称，表中文名 */
	private String entityName;
	/** 实体类型，0表示表 ，1表示视图 */
	private String entityType;
	/** 视图sql */
	private String viewSql;

	/* get,set方法 */
	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getViewSql() {
		return viewSql;
	}

	public void setViewSql(String viewSql) {
		this.viewSql = viewSql;
	}

}
