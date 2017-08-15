package com.ay.jfds.icon.dao;

import java.util.List;

import com.ay.jfds.icon.pojo.SysIcon;
import com.ay.framework.core.dao.BaseDao;

public class SysIconDao extends BaseDao<SysIcon> {
	@Override
	public String getEntityName() {
		return "SysIcon";
	}
	@Override
	public String getTableName() {
		return "SYS_ICON";
	}
	/*返回当前插入的对象*/
	public String autoInsert(SysIcon icon){
		return this.getSqlMapClientTemplate().insert(getEntityName()+".myInsert",icon).toString();
	}
	
	public SysIcon getByIconNo(String iconNo){
		return (SysIcon) this.getSqlMapClientTemplate().queryForObject(getEntityName()+".getByIconNo",iconNo);
	}
	
	public List<SysIcon> findAll(){
		return this.getSqlMapClientTemplate().queryForList(getEntityName()+".findAll");
	}
}