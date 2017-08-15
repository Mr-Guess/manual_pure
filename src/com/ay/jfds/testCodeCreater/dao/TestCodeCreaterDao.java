package com.ay.jfds.testCodeCreater.dao;

import com.ay.jfds.testCodeCreater.pojo.TestCodeCreater;
import com.ay.framework.core.dao.BaseDao;

public class TestCodeCreaterDao extends BaseDao<TestCodeCreater> {
	@Override
	public String getEntityName() {
		return "TestCodeCreater";
	}
	@Override
	public String getTableName() {
		return "TB_TEST_CODE_CREATER";
	}

}