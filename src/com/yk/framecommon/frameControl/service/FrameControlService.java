package com.yk.framecommon.frameControl.service;

import com.yk.framecommon.frameControl.dao.FrameControlDao;
import com.yk.framecommon.frameControl.pojo.FrameControl;
import com.ay.framework.core.service.BaseService;

public class FrameControlService extends BaseService<FrameControl, FrameControlDao> {
	
	public FrameControl getNextFrame(String id){
		return dao.getNextFrame(id);
	}
	
	public FrameControl getFirstFrame(String id){
		return dao.getFirstFrame(id);
	}
	
	public FrameControl getBackFrame(String id){
		return dao.getBackFrame(id);
	}
	
}