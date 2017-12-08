package com.yk.businesses.reimburse.dao;

import com.yk.businesses.reimburse.pojo.Reimburse;

import java.util.List;

import com.ay.framework.core.dao.BaseDao;

public class ReimburseDao extends BaseDao<Reimburse> {
	@Override
	public String getEntityName() {
		return "Reimburse";
	}
	@Override
	public String getTableName() {
		return "TB_REIMBURSE";
	}
	
	public List<Reimburse> getMyList(String id){
		return (List<Reimburse>)getSqlMapClientTemplate().queryForList(
				getEntityName() + ".getMyList", id);
	}
	
	public int getMyListCount(String id){
		return (Integer)getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".myListcount", id);
	}

}