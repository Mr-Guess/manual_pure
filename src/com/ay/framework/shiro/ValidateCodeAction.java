package com.ay.framework.shiro;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpSession;

import org.apache.shiro.web.session.HttpServletSession;
import org.apache.struts2.ServletActionContext;

import com.ay.framework.util.RandomNumUtil;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("all")
public class ValidateCodeAction extends ActionSupport{
	
	private ByteArrayInputStream inputStream;

	public String execute() {
		RandomNumUtil rnu = RandomNumUtil.Instance();
		this.setInputStream(rnu.getImage());
		HttpSession session = ServletActionContext.getRequest().getSession();
		String numStr=rnu.getString();
		session.setAttribute("validCode", numStr);
		return SUCCESS;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

}
