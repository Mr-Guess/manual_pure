package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.sys.pojo.DepartmentIndustry;

@SuppressWarnings(value="all")
public class DepartmentIndustryDao extends BaseDao<DepartmentIndustry> {

	@Override
	public String getTableName() {
		return "TB_DEPARTMENT_INDUSTRY";
	}

	@Override
	public String getEntityName() {
		return "departmentIndustry";
	}

	public List<DepartmentIndustry> findExistIndustry(String parentId) {
		return this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findExistIndustry", parentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Data> findNotExistIndustry(Map param) {
		return this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findNotExit",param);
	}
	
	public List<Map> findIndustryAndAddressByUserId(String userId) {
		return this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findIndustryAndAddress", userId);
	}
	
	public List<String> findAddrMatch(String userId) {
		return this.getSqlMapClientTemplate().queryForList(getEntityName()+ ".findAddrMatch",userId);
	}
}
