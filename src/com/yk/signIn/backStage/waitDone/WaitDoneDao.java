package com.yk.signIn.backStage.waitDone;

import com.yk.signIn.backStage.waitDone.WaitDone;

import java.util.List;

import com.ay.framework.core.dao.BaseDao;

public class WaitDoneDao extends BaseDao<WaitDone> {
	@Override
	public String getEntityName() {
		return "WaitDone";
	}
	@Override
	public String getTableName() {
		return "TB_WAIT_DONE";
	}

	public List<WaitDone> getSequenceJob(){
		return (List<WaitDone>)getSqlMapClientTemplate().queryForList(getEntityName() + ".getSequenceJob");
	}
}