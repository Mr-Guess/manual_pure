package com.yk.businesses.borrow.dao;

import com.yk.businesses.borrow.pojo.Borrow;

import java.util.List;

import com.ay.framework.core.dao.BaseDao;

public class BorrowDao extends BaseDao<Borrow> {
	@Override
	public String getEntityName() {
		return "Borrow";
	}
	@Override
	public String getTableName() {
		return "TB_BORROW";
	}

	public List<Borrow> getMyList(String id){
		return (List<Borrow>)getSqlMapClientTemplate().queryForList(
				getEntityName() + ".getMyList", id);
	}
	
	public int getMyListCount(String id){
		return (Integer)getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".myListcount", id);
	}
	
}