package com.ay.jfds.dev.dto;

import java.util.ArrayList;
import java.util.List;

public class EntityDataType {

	private List<String> yTypeList = new ArrayList<String>();
	
	private List<String> nTypeList = new ArrayList<String>();
	
	

	public EntityDataType() {
		yTypeList.add("Binary");
		yTypeList.add("Varbinary");
		yTypeList.add("Char");
		yTypeList.add("Varchar");
		yTypeList.add("Nchar");
		yTypeList.add("Nvarchar");
		yTypeList.add("Decimal");
		yTypeList.add("Float");
		
		nTypeList.add("Int");
		nTypeList.add("Smallint");
		nTypeList.add("Money");
		nTypeList.add("Datetime");
		nTypeList.add("Smalldatetime");
		nTypeList.add("Bit");
		nTypeList.add("Cursor");
		nTypeList.add("Text");
		nTypeList.add("Int");
	}

	public List<String> getyTypeList() {
		return yTypeList;
	}

	public void setyTypeList(List<String> yTypeList) {
		this.yTypeList = yTypeList;
	}

	public List<String> getnTypeList() {
		return nTypeList;
	}

	public void setnTypeList(List<String> nTypeList) {
		this.nTypeList = nTypeList;
	}
	
	
}
