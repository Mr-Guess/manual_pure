package com.ay.jfds.sys.dao;

import com.ay.jfds.sys.pojo.SystemConfigure;
import com.ay.framework.core.dao.BaseDao;

public class SystemConfigureDao extends BaseDao<SystemConfigure> {
	@Override
	public String getEntityName() {
		return "SystemConfigure";
	}
	@Override
	public String getTableName() {
		return "TB_SYSTEM_CONFIGURE";
	}

}