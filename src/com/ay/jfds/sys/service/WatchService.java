package com.ay.jfds.sys.service;

import java.util.ArrayList;
import java.util.List;

import com.ay.jfds.sys.dao.WatchDao;
import com.ay.jfds.sys.pojo.Watch;
import com.ay.framework.core.service.BaseService;

public class WatchService extends BaseService<Watch, WatchDao> {
	
	public List<String[]> getAllTable() {
		List<Watch> lis = dao.getAllTable();
		List<String[]> rtn = new ArrayList<String[]>();
		String saveName = "";
		for (int i = 0; i < lis.size(); i++) {
			Watch e = lis.get(i);
			String name = e.getTableName();
			if(name == null || name.toLowerCase().indexOf("dev") != -1 || name.toLowerCase().indexOf("sys") != -1){
				continue;
			}
			if(saveName.indexOf(e.getName()) != -1){
				continue;
			}
			saveName += e.getName();
			String[] s = new String[2];
			s[0] = e.getName();
			s[1] = e.getTableName();
			rtn.add(s);
		}
		
		return rtn;
	}
	
	public List<String[]> getAllEntTable(){
		List<Watch> lis = dao.getAllEntTable();
		List<String[]> rtn = new ArrayList<String[]>();
		for (int i = 0; i < lis.size(); i++) {
			Watch e = lis.get(i);
			String name = e.getTableName();
			if(name == null || name.toLowerCase().indexOf("dev") != -1 || name.toLowerCase().indexOf("sys") != -1){
				continue;
			}
			String[] s = new String[2];
			s[0] = e.getName();
			s[1] = e.getTableName();
			rtn.add(s);
		}
		return rtn;
	}
	
	public boolean isExistCount(String id){
		int isExist = dao.isExistCount(id);
		boolean rtn = false;
		if(isExist>0){
			rtn = true;
		}
		return rtn;
	}
}