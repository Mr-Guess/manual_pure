package com.ay.framework.shiro.strategy;

/** 
 * @Description 简单策略抽象类
 * @date 2013-7-2
 * @author WangXin
 */
public abstract class SimpleStrategy implements IStrategy {

    @Override
    public Integer getOrder() {
	return 10;
    }

    @Override
    public int compareTo(IStrategy o) {
	return getOrder().compareTo(o.getOrder());
    }

}

