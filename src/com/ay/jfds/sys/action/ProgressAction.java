package com.ay.jfds.sys.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.ay.framework.core.action.BaseAction;
import com.opensymphony.xwork2.ActionContext;

public class ProgressAction extends BaseAction{

	private InputStream process;
	
	public String execute(){
		String processStr = (String)ActionContext.getContext().getSession().get("currentUploadStatus");
		if(processStr != null){
			process = new BufferedInputStream(new ByteArrayInputStream(processStr.getBytes()));
		}else{
			process = new BufferedInputStream(new ByteArrayInputStream("0".getBytes()));
		}
		if("100".equals(processStr)){
			ActionContext.getContext().getSession().put("currentUploadStatus", null);
//			process = new BufferedInputStream(new ByteArrayInputStream("0".getBytes()));
		}
		return SUCCESS;
	}

	public InputStream getProcess() {
		return process;
	}

	public void setProcess(InputStream process) {
		this.process = process;
	}
	
}
