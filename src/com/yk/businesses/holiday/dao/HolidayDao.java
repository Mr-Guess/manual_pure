package com.yk.businesses.holiday.dao;

import com.yk.businesses.holiday.pojo.Holiday;

import java.util.List;

import com.ay.framework.core.dao.BaseDao;

public class HolidayDao extends BaseDao<Holiday> {
	@Override
	public String getEntityName() {
		return "Holiday";
	}
	@Override
	public String getTableName() {
		return "TB_HOLIDAY";
	}
	
	public List<Holiday> getMyList(String id){
		return (List<Holiday>)getSqlMapClientTemplate().queryForList(
				getEntityName() + ".getMyList", id);
	}
	
	public int getMyListCount(String id){
		return (Integer)getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".myListcount", id);
	}

}