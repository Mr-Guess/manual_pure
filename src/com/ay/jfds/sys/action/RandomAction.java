package com.ay.jfds.sys.action;

import java.io.ByteArrayInputStream;

import com.ay.framework.bean.OperateInfo;
import com.ay.framework.util.RandomNumUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RandomAction extends ActionSupport {
	private ByteArrayInputStream inputStream;

	public String execute() throws Exception {
		RandomNumUtil rdnu = RandomNumUtil.Instance();
		OperateInfo operateInfo = new OperateInfo();
		this.setInputStream(rdnu.getImage());// 取得带有随机字符串的图片
		ActionContext.getContext().getSession().put("random", rdnu.getString());// 取得随机字符串放入HttpSession
		operateInfo.setOperateSuccess(true);
		return SUCCESS;
	}

	private void setInputStream(ByteArrayInputStream image) {
		this.inputStream = inputStream;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

}
