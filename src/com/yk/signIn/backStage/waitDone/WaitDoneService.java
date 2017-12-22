package com.yk.signIn.backStage.waitDone;

import com.yk.signIn.backStage.waitDone.WaitDoneDao;
import com.yk.signIn.backStage.waitDone.WaitDone;

import java.util.List;

import com.ay.framework.core.service.BaseService;

public class WaitDoneService extends BaseService<WaitDone, WaitDoneDao> {
	
	public List<WaitDone> getSequenceJob(){
		return dao.getSequenceJob();
	}
}