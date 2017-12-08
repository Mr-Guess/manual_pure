package com.yk.framecommon.frameList.dao;

import com.yk.framecommon.frameList.pojo.FrameList;
import com.ay.framework.core.dao.BaseDao;

public class FrameListDao extends BaseDao<FrameList> {
	@Override
	public String getEntityName() {
		return "FrameList";
	}
	@Override
	public String getTableName() {
		return "TB_FRAME_LIST";
	}

}