package com.ay.framework.util;



import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ay.framework.core.pojo.BasePojo;

public class ObjectUtil {

	private static Log log = LogFactory.getLog(ObjectUtil.class.getName());

	private static List<Field> getInheritedFields(Class<?> type) {
		List<Class<?>> allClasses = new ArrayList<Class<?>>();
		Class<?> tmp = type;
		while (tmp != null && tmp != Object.class) {
			allClasses.add(tmp);
			tmp = tmp.getSuperclass();
		}

		List<Field> result = new ArrayList<Field>();

		for (int i = allClasses.size() - 1; i > -1; i--) {
			tmp = allClasses.get(i);
			Field[] fields = tmp.getDeclaredFields();
			for (Field field : fields) {
				if (!field.isSynthetic() && !Modifier.isStatic(field.getModifiers())) {
					result.add(field);
				}
			}
		}

		return result;
	}

	
	public static Map<String, Object> attributesToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Field> field = getInheritedFields(obj.getClass());
		for (Field f : field) {
			// 对于每个属性，获取属性名
			String varName = f.getName();
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = f.isAccessible();
				// 修改访问控制权限
				f.setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = f.get(obj);
				if (o instanceof BasePojo){
					if (log.isDebugEnabled()) {
						log.debug(Modifier.toString(f.getModifiers()));
					}
					continue;
				}
				// 恢复访问控制权限
				f.setAccessible(accessFlag);

				if (log.isDebugEnabled()) {
					log.debug(Modifier.toString(f.getModifiers()) + "	"
							+ varName + " = " + o);
				}
				map.put(varName, o);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
