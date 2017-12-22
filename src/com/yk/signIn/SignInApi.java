package com.yk.signIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.yk.signIn.backStage.waitDone.WaitDone;
import com.yk.signIn.backStage.waitDone.WaitDoneService;

/**
 * 打卡机接口
 * @author yzbyp
 *
 */
public class SignInApi extends BaseAction{
	
	public void post(){
		String s = "post";
		System.out.println("走了post");
		Struts2Utils.renderText(s);
	}
	
	public void get(){
		String s = "get";
		System.out.println("走了get");
//		WaitDoneService waitDoneService = SpringContextHolder.getBean("waitDoneService");
//		List<WaitDone> list = waitDoneService.getSequenceJob();
//		
//		//改造待办列表
//		for(WaitDone wd : list){
//			Map<String,String> map = new HashMap<String,String>();
//			
//		}
//		
//		Map<String, Object> sendData = new HashMap<String, Object>();
//		sendData.put("status", "1");
//		sendData.put("info", "ok");
//		
//		
//		
//		List<List<Map<String, String>>> sendDataList = new ArrayList<List<Map<String, String>>>();
//		List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
//		
//		
//		
//		Struts2Utils.renderText(s);
	}
}
