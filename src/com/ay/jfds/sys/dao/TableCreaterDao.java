package com.ay.jfds.sys.dao;

import com.ay.jfds.sys.pojo.TableCreater;
import com.ay.framework.core.dao.BaseDao;

public class TableCreaterDao extends BaseDao<TableCreater> {
	@Override
	public String getEntityName() {
		return "TableCreater";
	}
	@Override
	public String getTableName() {
		return "TB_TABLE_CREATER";
	}

}