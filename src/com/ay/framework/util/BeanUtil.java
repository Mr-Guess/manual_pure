package com.ay.framework.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @Description 
 * @date 2012-11-2
 * @author WangXin
 */
public class BeanUtil {
	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	public static Map Bean2Map(Object entity){
		if(entity==null)
			return null;
		Method[] methods=entity.getClass().getDeclaredMethods();
		Map map=new HashedMap();
		for(Method m : methods) {
            if(m.getName().startsWith("get")){
            	//System.out.println(m.invoke(entity));
            	try {
					Object o=m.invoke(entity);
					if(o!=null){
						String s=m.getName().replaceAll("get", "");
						s=s.substring(0,1).toLowerCase()+s.substring(1);
						map.put(s, o);
					}
				} catch (IllegalArgumentException e) {
					logger.error("IllegalArgumentException", e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					logger.error("IllegalAccessException", e);
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					logger.error("InvocationTargetException", e);
					e.printStackTrace();
				}
            }
        }
		if(map.isEmpty())
			return null;
		return map;
	}
	public static Object Map2Bean(Class type,Map map){
		BeanInfo beanInfo=null;
		try {
			beanInfo = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e) {
			logger.error("IntrospectionException", e);
			e.printStackTrace();
		} // 获取类属性 
        Object obj = null;
		try {
			obj = type.newInstance();
		} catch (InstantiationException e) {
			logger.error("InstantiationException", e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException", e);
			e.printStackTrace();
		} // 创建 JavaBean 对象 
 
        // 给 JavaBean 对象的属性赋值 
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName(); 
 
            if (map.containsKey(propertyName)) { 
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。 
                Object value = map.get(propertyName); 
 
                Object[] args = new Object[1]; 
                args[0] = value; 
 
                try {
					descriptor.getWriteMethod().invoke(obj, args);
                } catch (IllegalArgumentException e) {
					logger.error("IllegalArgumentException", e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					logger.error("IllegalAccessException", e);
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					logger.error("InvocationTargetException", e);
					e.printStackTrace();
				}
            } 
        } 
        return obj; 
	}
}

