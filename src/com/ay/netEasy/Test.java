package com.ay.netEasy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ay.framework.util.StringUtil;
import com.ay.netEasy.im.ConnectToApi;
import com.ay.netEasy.push.AttachMsg;

import jxl.write.DateTime;




public class Test {
	
	

	public static void main(String[] args) {
//		String toAccid = "wangwu";
//		try {
//			JSONObject jo = new JSONObject(new AttachMsg("reporting", StringUtil.getUUID(), "工作汇报", "3",CommonFunction.getRandomInt()));
//			String s = ConnectToApi.pushMessage(toAccid, jo.toString());
//			System.out.println(s);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		long s = 1508741189000l;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		
//		System.out.println(sdf.format(s).toString());
		
		System.out.println(1000 == 1000);
		System.out.println(100 == 100);
		System.out.println("----------------");
		Integer a = 1000,b = 1000;
		Integer c = 100, d = 100;
		System.out.println(a == b);
		System.out.println(c == d);
		
	}
	
	
	public static List getFields(Object obj) throws Exception{
		Field[] fields = obj.getClass().getDeclaredFields();
		List<String> fieldsNames = 	new ArrayList<>();
		for(int i=0;i<fields.length;i++){
			fieldsNames.add(fields[i].getName());
			
	    }
		Collections.sort(fieldsNames,Collator.getInstance(java.util.Locale.ENGLISH));
		List<String> list = new ArrayList<>();
		for(String s : fieldsNames){
			//Map<String,String> inMap = new HashMap<>();
			String firstLetter = s.substring(0, 1).toUpperCase();    
			String getter = "get" + firstLetter + s.substring(1);    
			Method method = obj.getClass().getMethod(getter, new Class[] {});    
			Object value = method.invoke(obj, new Object[] {});
			//inMap.put(s, value.toString());
			list.add(s+":"+value.toString());
		}
		return list;
	}
	
	
	
	
}
