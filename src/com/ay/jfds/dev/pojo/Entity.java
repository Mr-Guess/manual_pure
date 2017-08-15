package com.ay.jfds.dev.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.ay.framework.core.pojo.BasePojo;

/**
 * 表结构实体类
 * 
 * @author wuguowen
 * @datetime 2012-10-18
 * @version 1.0
 */
public class Entity extends BasePojo implements Comparable<Entity>{

	/** 实体编码，表英文名*/
	private String entityCode;
	/** 实体名称，表中文名*/
	private String entityName;
	/** 实体类型，0表示表 ，1表示视图*/
	private String entityType;
	/** 视图sql*/
	private String viewSql;
	/** 表单数 */
	private String formCount;
	/** 主表id*/
	private String parentId;
	/** 子表*/
	private Set<Entity> children = new TreeSet<Entity>();
	
	/** get,set方法 */
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

	public String getFormCount() {
		return formCount;
	}

	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	

	public Set<Entity> getChildren() {
		return children;
	}

	public void setChildren(Set<Entity> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return super.toString()+"{id:"+this.getId()+", Code:" + entityCode + ", pid="
				+ parentId  + (children.size()==0?"":",children="+children) + "}";
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		Entity entity = (Entity)obj;		
		return this.getId().equals(entity.getId());
	}

	public int compareTo(Entity o) {
		return this.getEntityCode().compareToIgnoreCase(o.getEntityCode());
	}


}
