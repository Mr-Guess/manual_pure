package com.yk.signIn.backStage.waitDone;

import com.yk.signIn.backStage.waitDone.WaitDoneDao;
import com.yk.signIn.backStage.waitDone.WaitDone;

import java.util.List;

import com.ay.framework.core.service.BaseService;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.jfds.sys.dao.UserDao;

public class WaitDoneService extends BaseService<WaitDone, WaitDoneDao> {
	
	public List<WaitDone> getSequenceJob(){
		List<WaitDone> wdl = dao.getSequenceJob();
		UserDao ud = SpringContextHolder.getBean("userDao");
		for(WaitDone wd : wdl){
			if(wd.getSubCmd().equals("user")){
				wd.setWithinObj(ud.getUserDTOById(wd.getId()));
			}
		}
		return wdl;
	}
}