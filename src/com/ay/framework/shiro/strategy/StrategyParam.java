package com.ay.framework.shiro.strategy;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * @Description 策略中的处理对象
 * @date 2013-6-19
 * @author WangXin
 */
public class StrategyParam {
    private Map map;
    private String tableName;
    
	public StrategyParam(Map map, String tableName) {
		super();
		this.map = map;
		this.tableName = tableName;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
    
}