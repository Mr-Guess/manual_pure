package com.yk.signIn;

import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.utils.web.struts.Struts2Utils;

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
		Struts2Utils.renderText(s);
	}
}
