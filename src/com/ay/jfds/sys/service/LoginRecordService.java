package com.ay.jfds.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.jfds.sys.dao.LoginRecordDao;
import com.ay.jfds.sys.dto.UserDTO;
import com.ay.jfds.sys.pojo.LoginRecord;
import com.ay.framework.core.service.BaseService;

public class LoginRecordService extends BaseService<LoginRecord, LoginRecordDao> {
	
	/**
	 * 判断用户当天是否已登录
	 * 时间：2014年12月8日 下午2:59:32
	 * 作者：杨丰智
	 */
	public boolean getCount(Map map){
	
		Integer num=this.getDao().getcount(map);
		if (num<=0) {
			return true;
		} 
		return false;
	}
	/**
	 * 勤度监管部门统计
	 * 时间：2014年12月9日 下午4:23:35
	 * 作者：杨丰智
	 * @param map
	 * @return
	 */
	public List getBydeptId(Map map ){
		
		return this.dao.getBydeptId(map);
	}
	/**
	 * 部门下人员登录统计
	 * 时间：2014年12月9日 下午5:00:09
	 * 作者：杨丰智
	 * @param map
	 * @return
	 */
	public List getdeptTj(Map map ){
		
		return this.dao.getdeptTj(map);
	}
	/**
	 * 查询用户的出勤记录
	 * 时间：2014年12月10日 上午11:29:20
	 * 作者：杨丰智
	 * @param map
	 * @return
	 */
	public List getByUserId(Map map ){
		
		return this.dao.getByUserId(map);
	}
}