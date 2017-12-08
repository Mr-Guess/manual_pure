package com.yk.businesses.reimburseList.dao;

import com.yk.businesses.reimburseList.pojo.ReimburseList;
import com.ay.framework.core.dao.BaseDao;

public class ReimburseListDao extends BaseDao<ReimburseList> {
	@Override
	public String getEntityName() {
		return "ReimburseList";
	}
	@Override
	public String getTableName() {
		return "TB_REIMBURSE_LIST";
	}

}