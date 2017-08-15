package com.ay.humanResource.worker.dao;

import com.ay.humanResource.worker.pojo.Worker;
import com.ay.framework.core.dao.BaseDao;

public class WorkerDao extends BaseDao<Worker> {
	@Override
	public String getEntityName() {
		return "Worker";
	}
	@Override
	public String getTableName() {
		return "TB_WORKER";
	}

}