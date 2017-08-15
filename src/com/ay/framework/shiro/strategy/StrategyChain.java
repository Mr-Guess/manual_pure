package com.ay.framework.shiro.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
 * @Description
 * @date 2013-6-19
 * @author WangXin
 */
public class StrategyChain {
    private List<IStrategy> strategys = new ArrayList<IStrategy>();
    private StrategyChain() {
	strategys = SystemStrategy.getStrategys();
    }
    public static StrategyChain newInstance() {
	return new StrategyChain();
    }
    private int index = -1;
    private IStrategy nextStrategy() {
	index++;
	if(strategys != null && index < strategys.size()) {
	    return strategys.get(index);
	}
	return null;
    }

    public void doStrategy(StrategyParam param) {
	IStrategy strategy = nextStrategy();
	if(strategy != null)
	    strategy.doStrategy(param, this);
    }
}

