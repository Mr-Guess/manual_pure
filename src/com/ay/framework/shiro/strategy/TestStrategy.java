package com.ay.framework.shiro.strategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ay.framework.annotation.Strategy;

/**
 * @Description
 * @date 2013-6-19
 * @author WangXin
 */
@Strategy(open = false)
public class TestStrategy extends SimpleStrategy {
	private static final Log log = LogFactory.getLog(TestStrategy.class);

	@Override
	public void doStrategy(StrategyParam param, StrategyChain chain) {
		log.info(param.getTableName() + ":" + param.getMap().toString());
		chain.doStrategy(param);
	}

}
