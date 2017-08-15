package com.ay.jfds.sys.action;

import com.ay.framework.bean.OperateInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CheckAction extends ActionSupport {
	/** 客户输入的图片验证码 */
	private String str;

	public String execute() {
		String str2 = (String) (ActionContext.getContext().getSession()
				.get("random"));// 取得session保存中的字符串
		OperateInfo operateInfo = new OperateInfo();
		// 下面就是将session中保存验证码字符串与客户输入的验证码字符串对比了
		if (str2.equals(this.getStr())) {
			operateInfo.setOperateSuccess(true);
			return SUCCESS;
		} else {
			operateInfo.setOperateSuccess(false);
			return LOGIN;
		}
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}