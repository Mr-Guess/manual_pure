package com.ay.jfds.create.service;

import java.util.List;
import java.util.Map;

import com.ay.jfds.create.dao.ColumnsDao;
import com.ay.jfds.create.pojo.Columns;
import com.ay.framework.core.service.BaseService;

public class ColumnsService extends BaseService<Columns, ColumnsDao> {
	public boolean isExits(Map map){
		return this.getDao().isExits(map);
	}
	public List<Columns> queryForSheet(String sheetId){
		return this.getDao().queryForSheet(sheetId);
	}
	
}