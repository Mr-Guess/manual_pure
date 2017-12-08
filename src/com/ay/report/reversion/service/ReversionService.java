package com.ay.report.reversion.service;

import com.ay.report.reversion.dao.ReversionDao;
import com.ay.report.reversion.pojo.Reversion;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.service.BaseService;

public class ReversionService extends BaseService<Reversion, ReversionDao> {
	
	public List<Reversion> findRepRev(Map map){
		return dao.findRepRev(map);
	}
}