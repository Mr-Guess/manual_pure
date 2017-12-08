package com.yk.framecommon.frameControl.dao;

import com.yk.framecommon.frameControl.pojo.FrameControl;
import com.ay.framework.core.dao.BaseDao;

public class FrameControlDao extends BaseDao<FrameControl> {
	@Override
	public String getEntityName() {
		return "FrameControl";
	}
	@Override
	public String getTableName() {
		return "TB_FRAME_CONTROL";
	}

	public FrameControl getNextFrame(String id){
		return (FrameControl)getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".getNextFrame", id);
	}
	
	public FrameControl getFirstFrame(String id){
		return (FrameControl)getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".getFirstFrame", id);
	}
	
	public FrameControl getBackFrame(String id){
		return (FrameControl)getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".getBackFrame", id);
	}
}