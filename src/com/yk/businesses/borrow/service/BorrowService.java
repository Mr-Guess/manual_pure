package com.yk.businesses.borrow.service;

import com.yk.businesses.borrow.dao.BorrowDao;
import com.yk.businesses.borrow.pojo.Borrow;

import java.util.List;

import com.ay.framework.core.service.BaseService;

public class BorrowService extends BaseService<Borrow, BorrowDao> {
	
	public List<Borrow> getMyList(String id){
		return dao.getMyList(id);
	}
	
	public int getMyListCount(String id){
		return dao.getMyListCount(id);
	}
}