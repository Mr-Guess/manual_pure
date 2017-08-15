package com.ay.jfds.sys.dao;

import java.util.List;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.sys.pojo.SysParameter;

/**
 * @author PS
 * 
 *         系统参数的DAO层
 */
@SuppressWarnings("all")
public class SysParameterDao extends BaseDao<SysParameter> {
	@Override
	public String getEntityName() {
		return "sysParameter";
	}

	/**
	 * 根据paramCode 查找value
	 * 
	 * @param code
	 * @return
	 */
	public SysParameter getValueByParaCode(String code) {
		return (SysParameter) this.getSqlMapClientTemplate().queryForObject(getEntityName() +".getValueByCode", code);
		
	}

	@Override
	public String getTableName() {
		return "sys_parameter";
	}
}
