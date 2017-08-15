package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.jfds.sys.pojo.LoginRecord;
import com.ay.framework.core.dao.BaseDao;

public class LoginRecordDao extends BaseDao<LoginRecord> {
	@Override
	public String getEntityName() {
		return "LoginRecord";
	}
	@Override
	public String getTableName() {
		return "TB_LOGIN_RECORD";
	}

	/**
	 * 判断用户当天是否已登录
	 * 时间：2014年12月8日 下午2:59:32
	 * 作者：杨丰智
	 */
	public int getcount(Map map){
		
		return (Integer) getSqlMapClientTemplate().queryForObject(getEntityName()+".getcount",map);
	}
	
	/**
	 * 勤度监管部门统计
	 * 时间：2014年12月9日 下午4:21:59
	 * 作者：杨丰智
	 * @param map
	 * @return
	 */
	public List getBydeptId(Map map ){
		
		return this.getSqlMapClientTemplate().queryForList(getEntityName() + ".getBydeptId",map);
	}
	
	/**
	 * 部门下人员登录统计
	 * 时间：2014年12月9日 下午4:58:59
	 * 作者：杨丰智
	 * @param map 部门ID
	 * @return
	 */
	public List getdeptTj(Map map ){
		
		return this.getSqlMapClientTemplate().queryForList(getEntityName() + ".getdeptTj",map);
	}
	
	/**
	 * 查询用户的出勤记录
	 * 时间：2014年12月10日 上午11:26:22
	 * 作者：杨丰智
	 * @param map 用户id 月份
	 * @return
	 */
	public List getByUserId(Map map ){
		
		return this.getSqlMapClientTemplate().queryForList(getEntityName() + ".getByUserId",map);
	}
}