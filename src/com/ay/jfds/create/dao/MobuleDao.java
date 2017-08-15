package com.ay.jfds.create.dao;

import com.ay.jfds.create.pojo.Mobule;
import com.ay.framework.core.dao.BaseDao;

public class MobuleDao extends BaseDao<Mobule> {
	@Override
	public String getEntityName() {
		return "Mobule";
	}
	@Override
	public String getTableName() {
		return "sys_MOBULE";
	}

}