package com.ay.jfds.dev.dto;

import java.util.ArrayList;
import java.util.List;

public class CombotreeDTO {

	private String id;
	private String text;
	
	private List children;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	
	
}
