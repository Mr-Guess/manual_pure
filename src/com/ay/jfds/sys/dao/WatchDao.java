package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.jfds.sys.pojo.Watch;
import com.ay.framework.core.dao.BaseDao;
import com.ay.framework.shiro.strategy.StrategyChain;
import com.ay.framework.shiro.strategy.StrategyParam;

public class WatchDao extends BaseDao<Watch> {
	@Override
	public String getEntityName() {
		return "Watch";
	}
	@Override
	public String getTableName() {
		return "TB_WATCH";
	}
	
	public List<Watch> getAllTable() {
		return (List<Watch>) getSqlMapClientTemplate().queryForList(getEntityName() + ".getAllTable");
	}

	
	public List<Watch> getAllEntTable() {
		return (List<Watch>) getSqlMapClientTemplate().queryForList(getEntityName() + ".getAllEntTable");
	}
	
	/**
	 * 判断是否存在
	 * 
	 * @param id
	 * @return
	 */
	public int isExistCount(String id) {
		return (Integer) getSqlMapClientTemplate().queryForObject(getEntityName() + ".isExistCount", id);
	}

}