package com.ay.framework.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TrimMapUtil {
	
	/**
	 * 过滤map条件值的空格
	 * @param map
	 */
	public static Map trimMap(Map map){
		if(map!=null){
			Set set = map.entrySet();
			Map newMap = new HashMap();
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				Entry entry = (Entry)iter.next();
				String key = (String)entry.getKey();
				if(entry.getValue()!=null){
					String value = (String)entry.getValue();
					value = value.trim();
					newMap.put(key, value);
				}
			}
			return newMap;
		}
		return null;
	}

	/**
	 * 过滤掉查询条件中的空格
	 * @param map
	 * @return
	 */
	public static Map filterCauseStr(Map map){
		if(map!=null){
			Map newMap = new HashMap();
			Set set = map.entrySet();
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				Entry entry = (Entry)iter.next();
				String key = (String)entry.getKey();
				if(entry.getValue()!=null){
					
				}
			}
			return newMap;
		}
		return null;
	}
	
//	/**
//	 * 让查询条件适合数据库
//	 * @param str
//	 * @return
//	 */
//	public static String filterStr(String str){
//		if(str!=null&&!str.isEmpty()){
//			char[] chars = str.toCharArray();
//			List<Character> list = new ArrayList<Character>();
//			for(int i = 0;i<chars.length;i++){
//				if(Character.isUpperCase(chars[i])){
//					list.add('_');
//					list.add(Character.toLowerCase(chars[i]));
//					continue;
//				}
//				list.add(chars[i]);
//			}
//			char[] chars2 = new char[list.size()];
//			for(int i =0;i<list.size();i++){
//				chars2[i] = list.get(i);
//			}
//			return String.valueOf(chars2);
//		}
//		return str;
//		
//	}
	
	/**
	 * 让查询条件适合数据库
	 * @param str
	 * @return
	 */
	public static String filterStr(String str){
		StringBuilder sb = new StringBuilder();
		if(str!=null){
			for(int i = 0;i<str.length();i++){
				char c = str.charAt(i);
				if(Character.isUpperCase(c)){
					sb.append('_').append(Character.toLowerCase(c));
					continue;
				}
				sb.append(c);
			}
			return sb.toString();
		}
		return str;
	}
}
