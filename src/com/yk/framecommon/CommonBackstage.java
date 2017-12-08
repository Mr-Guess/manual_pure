package com.yk.framecommon;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class CommonBackstage {

	/**
	 * 通用读取properties方法，用于明确指定读取属性时使用
	 * @param inputStream
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public static Map<String,String> getProperties(InputStream inputStream) throws Exception{
		Map<String, String> outMap = new HashMap<String,String>();
		Properties properties = new Properties();
		properties.load(inputStream);
		//获取所有内容方法不被需要，直接指定读取
		Iterator<String> it=properties.stringPropertyNames().iterator();
		while(it.hasNext()){
			String key=it.next();
			outMap.put(key, new String(properties.getProperty(key).getBytes("ISO8859-1"),"UTF-8"));
			//System.out.println(key+":"+new String(properties.getProperty(key).getBytes("ISO8859-1"),"UTF-8"));
		}
		return outMap;
	}
	
}
