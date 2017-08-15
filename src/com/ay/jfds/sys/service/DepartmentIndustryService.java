package com.ay.jfds.sys.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.ay.framework.core.service.BaseService;
import com.ay.framework.util.StringUtil;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.sys.dao.DepartmentIndustryDao;
import com.ay.jfds.sys.pojo.DepartmentIndustry;

public class DepartmentIndustryService extends
		BaseService<DepartmentIndustry, DepartmentIndustryDao> {

	/**
	 * 查找该部门已经关联的行业
	 * 
	 * @param parentId
	 * @return 返回行业的LIST list中只有 INDUSTRY_CODE
	 */
	public List<DepartmentIndustry> findExistIndustry(String parentId) {
		return this.dao.findExistIndustry(parentId);
	}

	/**
	 * 查询出部门尚未关联的行业
	 * 
	 * @param param
	 * @return 返回一个LIST 供下拉框使用
	 */
	public List<Data> findNotExit(Map param) {
		return this.dao.findNotExistIndustry(param);
	}

	/**
	 * 根据用户名 得到他监管的行业类型
	 * 
	 * @return
	 */
	public List<Map> findIndustryAndAddressByUserId() {
		String userId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id");
		return this.dao.findIndustryAndAddressByUserId(userId);
	}

	/**
	 * IN查询语句
	 * 
	 * @return
	 */
	public String findEnterpriseSql() {
		StringBuilder findEnterprise = new StringBuilder();
		findEnterprise
				.append("SELECT ID FROM TB_ENTERPRISE WHERE 1 = 1 AND STATUS = 1");
		findEnterprise.append(" and ( 1=2 ");
		List<Map> industry = this.findIndustryAndAddressByUserId();
		for (Map p : industry) {
			String industryCode = p.get("industryCode").toString();
			findEnterprise.append(" OR ");
			findEnterprise.append("MANAGER_TYPE like '%");
			findEnterprise.append(industryCode);
			findEnterprise.append("%'");
		}
		findEnterprise.append(" )");
		findEnterprise.append(" AND ( 1 = 2");
		for (String s : addrMatchFilter()) {
			findEnterprise.append(" OR ");
			findEnterprise.append(" ADDR_MATCH like '%");
			findEnterprise.append(s);
			findEnterprise.append("%'");
		}
		findEnterprise.append(")");
		String insql = findEnterprise.toString();
		return insql;
	}

	/**
	 * 地区码过滤
	 * 
	 * @return addrmatch
	 */
	public List<String> addrMatchFilter() {
		String userId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id");
		List<String> resultList = new LinkedList<String>();
		List<String> result = this.dao.findAddrMatch(userId);
		for (String s : result) {
			s = StringUtil.cleanZero(s);
			boolean flag = true;
			for (int i = resultList.size() - 1; i >= 0; i--) {
				if (s.contains(resultList.get(i))) {
					flag = false;
					break;
				} else if (resultList.get(i).contains(s)) {
					resultList.remove(i);
				}
			}
			if (flag) {
				resultList.add(s);
			}
		}
		return resultList;
	}
}
