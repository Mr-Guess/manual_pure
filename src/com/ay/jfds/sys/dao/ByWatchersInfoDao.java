package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.jfds.sys.pojo.ByWatchersInfo;
import com.ay.framework.core.dao.BaseDao;
import com.ay.framework.shiro.strategy.StrategyChain;
import com.ay.framework.shiro.strategy.StrategyParam;

public class ByWatchersInfoDao extends BaseDao<ByWatchersInfo> {
	@Override
	public String getEntityName() {
		return "ByWatchersInfo";
	}
	@Override
	public String getTableName() {
		return "TB_BY_WATCHERS_INFO";
	}
	
	/**
	 * 
	 * @param map
	 *            查询分页的参数
	 * @param from
	 *            一页显示多少
	 * @param prePageNum
	 *            页码
	 * @return list 查询出来的list对象
	 */
	public List<ByWatchersInfo> pageQueryBywatchers(Map map, int from, int prePageNum) {
		String tableName = getTableName();
		StrategyParam param = new StrategyParam(map, tableName);
		StrategyChain.newInstance().doStrategy(param);
		return (List<ByWatchersInfo>) getSqlMapClientTemplate().queryForList(
				getEntityName() + ".findByWatchers", map, from, prePageNum);
	}
	
	/**
	 * @param map
	 *            map
	 * @return int
	 */
	public int countBywatchers(Map map) {
		String tableName = getTableName();
		StrategyParam param = new StrategyParam(map, tableName);
		StrategyChain.newInstance().doStrategy(param);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".countBywatchers", map);
	}

}