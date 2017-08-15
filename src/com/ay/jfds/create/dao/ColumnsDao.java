package com.ay.jfds.create.dao;

import java.util.List;
import java.util.Map;

import com.ay.jfds.create.pojo.Columns;
import com.ay.framework.core.dao.BaseDao;

public class ColumnsDao extends BaseDao<Columns> {
	@Override
	public String getEntityName() {
		return "Columns";
	}
	@Override
	public String getTableName() {
		return "TB_COLUMNS";
	}
	/**
	 * 当前列是否存在
	 * @param nameEn
	 * @return
	 */
	public boolean isExits(Map map){
		return (Integer) getSqlMapClientTemplate().
				queryForObject
				(getEntityName()+".isExits",map)==0;
	}
	@SuppressWarnings("all")
	public List<Columns> queryForSheet(String sheetId){
		return getSqlMapClientTemplate().queryForList(getEntityName()+".queryForSheet",sheetId);
	}
}