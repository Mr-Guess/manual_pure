package com.ay.jfds.create.tools;
import java.util.Map.Entry; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.PropertyPlaceholderHelper;

import com.ay.framework.core.dao.pagination.DialectFactory;
import com.ay.framework.core.utils.spring.SpringContextHolder;
/**
 * 
 * @项目名称:jfds2.0
 * @类名称:Property
 * @类描述:读取配置文件 根据当前加载的配置读取 更具当前的datasource.type动态切换数据源  这个只能在程序跑起来才能读取 注入 测试需要手动注入
 * @创建人:雷远亮
 * @创建时间:2014年6月23日11:05:08
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @version 2.0
 */
public class Property extends PropertyPlaceholderConfigurer {
	
    private static Map<String,String> properties = new HashMap<String,String>();
    private static String[] sqlserverSources=new String[]{"datasource.driverClassName","datasource.url","datasource.userName","datasource.password","sqlMapper.path"};
    private static String[] mysqlSources=new String[]{"datasource.mysqlDriverClassName","datasource.mysqlUrl","datasource.mysqlUserName","datasource.mysqlPassword","sqlMapper.mysqlPath"};
    private static String[] oracleSources=new String[]{"datasource.oracleDriverClassName","datasource.oraclelUrl","datasource.oracleUserName","datasource.oraclePassword","sqlMapper.oraclePath"};
    protected void processProperties(  
            ConfigurableListableBeanFactory beanFactoryToProcess,  
            Properties props) throws BeansException {  
        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(  
                DEFAULT_PLACEHOLDER_PREFIX, DEFAULT_PLACEHOLDER_SUFFIX, DEFAULT_VALUE_SEPARATOR, false);
        for(Entry<Object,Object> entry:props.entrySet()){  
            String stringKey = String.valueOf(entry.getKey());  
            String stringValue = String.valueOf(entry.getValue());  
            stringValue = helper.replacePlaceholders(stringValue, props);  
            properties.put(stringKey, stringValue);  
        } 
       // reloadProperty();
        super.processProperties(beanFactoryToProcess, props);  
    }  
      
    public static Map<String, String> getProperties() { 
        return properties;  
    }  
      
    public static String getProperty(String key){
    	if(properties==null){
    		
    	}
        return properties.get(key);  
    }
    /***
     * 根据当前datasourc.type重新装载属性
     */
    private static void reloadProperty(){
    	String dataType=properties.get("datasource.type");
    	if(DialectFactory.MY_SQL.equals(dataType)){
    		for(int i=0;i<mysqlSources.length;i++){
    			properties.remove(sqlserverSources[i]);
    			properties.put(sqlserverSources[i], properties.get(mysqlSources[i]));
    		}
    	}else if(DialectFactory.ORACLE.equals(dataType)){
    		for(int i=0;i<oracleSources.length;i++){
    			properties.remove(sqlserverSources[i]);
    			properties.put(sqlserverSources[i], properties.get(oracleSources[i]));
    		}
    	}
    }
}
