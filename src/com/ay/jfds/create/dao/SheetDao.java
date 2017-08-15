package com.ay.jfds.create.dao;

import com.ay.jfds.create.pojo.Sheet;
import com.ay.framework.core.dao.BaseDao;

public class SheetDao extends BaseDao<Sheet> {
	@Override
	public String getEntityName() {
		return "Sheet";
	}
	@Override
	public String getTableName() {
		return "TB_SHEET";
	}
	/**
	 * 判断表是否存在
	 * @param tbName
	 * @return
	 */
	public boolean isExits(String tbName){
		return (Integer)this.getSqlMapClientTemplate().queryForObject(getEntityName()+".isExits",tbName)==0;
	}
	
	
}