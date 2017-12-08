package com.yk.businesses.reimburse.service;

import com.yk.businesses.reimburse.dao.ReimburseDao;
import com.yk.businesses.reimburse.pojo.Reimburse;

import java.util.List;

import com.ay.framework.core.service.BaseService;

public class ReimburseService extends BaseService<Reimburse, ReimburseDao> {
	
	public List<Reimburse> getMyList(String id){
		return dao.getMyList(id);
	}
	
	public int getMyListCount(String id){
		return dao.getMyListCount(id);
	}
}