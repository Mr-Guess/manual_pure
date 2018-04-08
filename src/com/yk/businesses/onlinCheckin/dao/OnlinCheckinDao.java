package com.yk.businesses.onlinCheckin.dao;

import com.yk.businesses.onlinCheckin.pojo.OnlinCheckin;
import com.ay.framework.core.dao.BaseDao;

public class OnlinCheckinDao extends BaseDao<OnlinCheckin> {
	@Override
	public String getEntityName() {
		return "OnlinCheckin";
	}
	@Override
	public String getTableName() {
		return "TB_ONLIN_CHECKIN";
	}

}