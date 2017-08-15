package com.yk.businesses.jobRemind.dao;

import com.yk.businesses.jobRemind.pojo.JobRemind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;

public class JobRemindDao extends BaseDao<JobRemind> {
	@Override
	public String getEntityName() {
		return "JobRemind";
	}
	@Override
	public String getTableName() {
		return "TB_JOB_REMIND";
	}
	
	public List<JobRemind> getRemind(String id,String deptId){
		Map<String, String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put("deptId", deptId);
		return  (List<JobRemind>)getSqlMapClientTemplate().queryForList(getEntityName() + ".getRemind",map);
	}

}