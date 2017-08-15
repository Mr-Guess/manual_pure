package com.ay.framework.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonUtil {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private JacksonUtil(){
		
	}

	public static List<Map<String,String>> json2Java(String json){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(json!=null&&!"".equals(json)){
			try {
				//将json转化为java对象
				list = objectMapper.readValue(json, list.getClass());
			} catch (Exception e){
				e.printStackTrace();
				return list;
			}
		}
		return list;
	}
	
	public static <T> List<T> jsonToJava(String json){
		ObjectMapper objectMapper=new ObjectMapper();
		JsonGenerator jsonGenerator =null;
		try {
			jsonGenerator=objectMapper.getJsonFactory().createJsonGenerator(System.out,JsonEncoding.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<T> list = new ArrayList<T>();
		try {
			//将json转化为java对象
			list = objectMapper.readValue(json, list.getClass());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return list;
	}
	
	/**
	 * 根据json字符串转化为相应的java对象
	 * @param json 字符串
	 * @param clz 目标对象的class
	 * @return
	 */
	public static <T> T jsonToJava(String json, Class<T> clz){
		T obj = null;
		try {
			obj = objectMapper.readValue(json, clz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
