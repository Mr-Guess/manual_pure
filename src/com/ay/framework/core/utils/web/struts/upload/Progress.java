package com.ay.framework.core.utils.web.struts.upload;

/**
 * 上传下载状态对象
 * @author wgw
 * @date 2013-3-12
 */
public class Progress {

	private String items;
	
	private String process;
	
	private String status;

	public Progress() {
		super();
	}

	public Progress(String items, String process, String status) {
		super();
		this.items = items;
		this.process = process;
		this.status = status;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
