package com.ay.framework.shiro.strategy;

import java.util.Map;

/** 
 * @Description
 * @date 2013-6-19
 * @author WangXin
 */
public interface IStrategy extends Comparable<IStrategy> {
	/**
	 * 根据条件进行过滤
	 * @param map
	 * @param chain
	 * @return void
	 */
    public void doStrategy(StrategyParam param, StrategyChain chain);
    public Integer getOrder();
}

