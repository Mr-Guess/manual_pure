package com.ay.jfds.icon.service;

import java.util.List;

import com.ay.jfds.icon.dao.SysIconDao;
import com.ay.jfds.icon.pojo.SysIcon;
import com.ay.framework.core.service.BaseService;

public class SysIconService extends BaseService<SysIcon, SysIconDao> {
	public String add(SysIcon icon){
		return this.getDao().autoInsert(icon);
	}
	
	public SysIcon getByIconNo(String iconNo){
		return this.getDao().getByIconNo(iconNo);
	}
	
	public List<SysIcon> findAll(){
		return this.getDao().findAll();
	}
}