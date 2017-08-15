package com.ay.framework.shiro.strategy;

import com.ay.framework.annotation.Strategy;

/**
 * @Description 最先进入的策略
 * @date 2013-7-2
 * @author WangXin
 */
@Strategy
public class FirstStrategy extends SimpleStrategy {

	@Override
	public void doStrategy(StrategyParam param, StrategyChain chain) {
		String tableName = param.getTableName();
		if (tableName == null || tableName.equals("DEV_MENU")
				|| tableName.equals("SYS_PARAMETER")) { // 系统初始化读取的表
			return;// 不执行后面的所有策略
		}
		chain.doStrategy(param);
	}

	@Override
	public Integer getOrder() {
		return Integer.MIN_VALUE;
	}

}
