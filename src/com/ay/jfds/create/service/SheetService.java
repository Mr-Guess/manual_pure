package com.ay.jfds.create.service;

import com.ay.jfds.create.dao.SheetDao;
import com.ay.jfds.create.pojo.Sheet;
import com.ay.framework.core.service.BaseService;

public class SheetService extends BaseService<Sheet, SheetDao> {
	public boolean isExits(String tbName){
		return getDao().isExits(tbName);
	}
	
}