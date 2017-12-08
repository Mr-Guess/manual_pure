package com.yk.framecommon.frameRecord.dao;

import com.yk.framecommon.frameRecord.pojo.FrameRecord;
import com.ay.framework.core.dao.BaseDao;

public class FrameRecordDao extends BaseDao<FrameRecord> {
	@Override
	public String getEntityName() {
		return "FrameRecord";
	}
	@Override
	public String getTableName() {
		return "TB_FRAME_RECORD";
	}

}