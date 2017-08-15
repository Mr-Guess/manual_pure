package com.ay.jfds.sys.service;

import com.ay.jfds.sys.dao.SysDeptAuthorityDao;
import com.ay.jfds.sys.pojo.SysDeptAuthority;
import com.ay.framework.core.service.BaseService;

public class SysDeptAuthorityService extends BaseService<SysDeptAuthority, SysDeptAuthorityDao> {
	
	public String callGenerEntDept(String bmId){
		return dao.callGenerEntDept(bmId);
	}
	

	/**
	 * 根据部门修改子部门的jdlx为0
	 * 时间：2014年11月10日 下午3:09:49
	 * 作者：周伟
	 * @param sysDeptAuthority
	 */
	public void updateChildJdlx(SysDeptAuthority sysDeptAuthority){
		dao.updateChildJdlx(sysDeptAuthority);
	}
}