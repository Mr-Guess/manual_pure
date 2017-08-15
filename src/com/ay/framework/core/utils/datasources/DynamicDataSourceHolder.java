package com.ay.framework.core.utils.datasources;

/**
 * 动态数据源切换
 * @author 数据库注入小组
 * @date 2013-3-5
 *
 */
public class DynamicDataSourceHolder {

	private static final ThreadLocal<String> holder = new ThreadLocal<String>();  
    
	/**
	 * 设置数据源名称，实时切换数据源
	 * @param name
	 */
    public static void putDataSourceName(String name){  
        holder.set(name);  
    }  
      
    public static String getDataSourceName(){  
        return holder.get();  
    }  
}
