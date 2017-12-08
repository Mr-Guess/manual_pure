package com.ay.jfds.sys.dto;

import java.util.List;

/**
 * 树数据传输基类
 * @author yzbyp
 *
 */
public class TreeDTO<T> {

	private T obj;
	
	private String type;
	
	private List<TreeDTO> children;

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}
	
}
