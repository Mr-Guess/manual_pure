package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.sys.pojo.SysGroup;

/**
 * 用户dao封装
 * 
 * @author lushigai
 *
 */
@SuppressWarnings("all")
public class SysGroupDao extends BaseDao<SysGroup> {

	@Override
	public String getEntityName() {
		return "SysGroup";
	}

	@Override
	public String getTableName() {
		return "sys_group";
	}
}
