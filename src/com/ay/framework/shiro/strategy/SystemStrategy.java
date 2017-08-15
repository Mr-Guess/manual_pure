package com.ay.framework.shiro.strategy;

import generate.tool.ClassUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.annotation.Strategy;

/** 
 * @Description 全局的策略列表
 * @date 2013-6-21
 * @author WangXin
 */
@Component
public class SystemStrategy {
    private static final Log log = LogFactory.getLog(SystemStrategy.class);
    private static List<IStrategy> strategys = new ArrayList<IStrategy>();
    @PostConstruct
    public void init() {
	String packageName = "com.ay";
        Set<Class<?>> classes = ClassUtil.getClasses(packageName);
        for (Class<?> c : classes)
        {
            if (c.isAnnotationPresent(Strategy.class) && ClassUtil.isInterface(c, IStrategy.class))
            {
        	Strategy s = c.getAnnotation(Strategy.class);
        	if(!s.open())continue;
                IStrategy strategy = null;
		try {
		    strategy = (IStrategy) c.newInstance();
		} catch (InstantiationException e) {
		    e.printStackTrace();
		} catch (IllegalAccessException e) {
		    e.printStackTrace();
		}
                strategys.add(strategy);
                log.info("open strategy：" + c.getName());
            }
        }
        Collections.sort(strategys);
    }
    public static List<IStrategy> getStrategys() {
        return strategys;
    }
    public void setStrategys(List<IStrategy> strategys) {
        this.strategys = strategys;
    }
    
}

