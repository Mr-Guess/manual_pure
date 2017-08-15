package com.ay.jfds.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.service.BaseService;
import com.ay.jfds.sys.dao.DepartmentDao;
import com.ay.jfds.sys.pojo.Department;
import com.ay.jfds.sys.pojo.User;

/**
 * 部门service封装
 * 
 * @author zxy
 * 
 */
@SuppressWarnings("all")
public class DepartmentService extends BaseService<Department, DepartmentDao> {
	public List<Department> findAllChildDept(Department department) {
		return this.getDao().findAllChildDept(department);
	}
	
	public List<Department> findAllEntDept(Department department) {
		return this.getDao().findAllEntDept(department);
	}
	
	public List<Department> findAllDeptEntDept(Department department) {
		return this.getDao().findAllDeptEntDept(department);
	}

	public Page findAllDepartmentUser(Department department, Page page) {
		page.setCount(this.count(new HashMap()));
		List<User> list = this.getDao().findAllDepartmentUser(department, page.getFrom(),  page.getRecPerPage());
		page.setCollection(list);
		return page;
	}

	public Page findAllDepartmentUser(String id, Page page) {
		page.setCount(this.count(new HashMap()));
		List<User> list = this.getDao().findAllDepartmentUser(id, page.getFrom(),  page.getRecPerPage());
		page.setCollection(list);
		return page;
	}

	public boolean deleteUserByDept(String id) {
		return this.getDao().deleteUserByDept(id);
	}
	
	public Department getDeptByName(String name){
		return this.getDao().getDeptByName(name);
	}
	
	public List<Department> findByArea(Map map){
		return this.getDao().findByArea(map);
	}
	
	public List<Department> findByDistrict(Map map){
		return this.getDao().findByDistrict(map);
	}
	public String getAreaName(String code){
		return this.getDao().getAreaName(code);
	}
	public List<Department> findGovDept(){
		return this.getDao().findGovDept();
	}
	public List<Department> findByParentId(String id){
		return this.getDao().findByParentId(id);
	}
	public List<Department> findByUserTree(String userTrees){
		return this.getDao().findByUserTree(userTrees);
	}
}
