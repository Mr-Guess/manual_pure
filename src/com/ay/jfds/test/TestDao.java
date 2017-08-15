package com.ay.jfds.test;

import com.ay.jfds.test.Test;
import com.ay.framework.core.dao.BaseDao;

public class TestDao extends BaseDao<Test> {
	@Override
	public String getEntityName() {
		return "Test";
	}
	@Override
	public String getTableName() {
		return "TB_TEST";
	}

}