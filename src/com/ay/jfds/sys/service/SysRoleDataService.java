package com.ay.jfds.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.service.BaseService;
import com.ay.jfds.sys.dao.SysRoleDataDao;
import com.ay.jfds.sys.pojo.SysRoleData;

/**
 * 数据权限SERVICE
 * 
 * @author zxy
 *
 */
@SuppressWarnings("all")
public class SysRoleDataService extends BaseService<SysRoleData, SysRoleDataDao> {
	/**
	 * 权限ROLE ID来进行分页操作
	 * 
	 * @param roleId
	 * @param page
	 * @return
	 */
	public Page findByRoleId(String roleId, Page page) {
		page.setCount(this.getDao().countByRoleId(roleId));
		List<SysRoleData> list = this.getDao().findByRoleId(roleId, page.getFrom(), page.getRecPerPage());
		page.setCollection(list);
		return page;
	}
}
