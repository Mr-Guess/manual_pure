package com.ay.jfds.sys.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.sys.pojo.Department;
import com.ay.jfds.sys.pojo.User;

/**
 * 部门DAO
 * 
 * @author zxy
 *
 */
@SuppressWarnings("all")
public class DepartmentDao extends BaseDao<Department> {

	@Override
	public String getEntityName() {
		return "dept";
	}
	
	/**
	 * 查找一个部门下面的所有子部门
	 * 
	 * @param department
	 * @return
	 */
	public List<Department> findAllChildDept(Department department) {
		return (List<Department>) this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findAllChildDept", department.getId());
	}
	
	/**
	 * 根据名称查找部门
	 * 
	 * @param department
	 * @return
	 */
	public Department getDeptByName(String deptName) {
		return (Department)this.getSqlMapClientTemplate().queryForObject(getEntityName() + ".getByDeptName", deptName);
	}
	
	/**
	 * 查找一个部门下面的所有子部门
	 * 
	 * @param department
	 * @return
	 */
	public List<Department> findAllEntDept(Department department) {
		return (List<Department>) this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findAllEntDept", department.getId());
	}
	
	public List<Department> findAllDeptEntDept(Department department) {
		return (List<Department>) this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findAllDeptEntDept", department.getId());
	}
	
	/**
	 * 查找这个部门下的所有员工用户
	 * 
	 * @param department
	 * @return
	 */
	public List<User> findAllDepartmentUser(Department department, int from, int prePageNum) {
		return (List<User>) this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findDeptsUser", department, from, prePageNum);
	}
	
	/**
	 * 根据id来找这个部门下的所有员工
	 * 
	 * @param deptId
	 * @param from
	 * @param prePageNum
	 * @return
	 */
	public List<User> findAllDepartmentUser(String id, int from, int prePageNum) {
		return (List<User>) this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findDeptUserById", id, from ,prePageNum);
	}
	
	public boolean deleteUserByDept(String id) {
		int rows = this.getSqlMapClientTemplate().delete(getEntityName() + ".deleteUserByDept", id);
		if (rows >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public String getTableName() {
		return "sys_dept";
	}
	/**
	 * 查询区下面所有部门
	 * @param department
	 * @return
	 */
	public List<Department> findByDistrict(Map map) {
		return this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findByAddress", map);
	}
	/**
	 * 查询非区
	 * @param map
	 * @return
	 */
	public List<Department> findByArea(Map map){
		return this.getSqlMapClientTemplate().queryForList(getEntityName()+".findByArea",map);
	}
	/**
	 * 获取区名称
	 * @param code
	 * @return
	 */
	public String getAreaName(String code){
		return (String) this.getSqlMapClientTemplate().queryForObject(getEntityName()+".getAreaName",code);
	}
	
	/**
	 * 查询企业部门
	 * @return
	 */
	public List<Department> findGovDept(){
		return this.getSqlMapClientTemplate().queryForList(getEntityName()+".findGovDept");
	}
	
	public List<Department> findByParentId(String id){
		return this.getSqlMapClientTemplate().queryForList(getEntityName()+".findByParentId",id);
	}
	
	public List<Department> findByUserTree(String userTrees){
		return this.getSqlMapClientTemplate().queryForList(getEntityName()+".getByUserTree",userTrees);
	}
}
