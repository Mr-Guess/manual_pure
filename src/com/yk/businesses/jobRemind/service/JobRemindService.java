package com.yk.businesses.jobRemind.service;

import com.yk.businesses.jobRemind.dao.JobRemindDao;
import com.yk.businesses.jobRemind.pojo.JobRemind;

import java.util.List;

import com.ay.framework.core.service.BaseService;

public class JobRemindService extends BaseService<JobRemind, JobRemindDao> {

	public List<JobRemind> getRemind(String id,String deptId){
		return dao.getRemind(id, deptId);
	}
}