package com.ay.jfds.sys.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import sun.awt.AppContext;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.bean.UserInfo;
import com.ay.framework.common.ITreeService;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.shiro.ApplicationSessionSchema;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.framework.util.StringUtil;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.service.DataService;
import com.ay.jfds.sys.dto.TreeDTO;
import com.ay.jfds.sys.dto.UserDTO;
import com.ay.jfds.sys.pojo.Department;
import com.ay.jfds.sys.pojo.DepartmentIndustry;
import com.ay.jfds.sys.pojo.User;
import com.ay.jfds.sys.service.DepartmentIndustryService;
import com.ay.jfds.sys.service.DepartmentService;
import com.ay.jfds.sys.service.DepartmentTreeService;
import com.ay.jfds.sys.service.DepartmentUserTreeService;
import com.ay.jfds.sys.service.UserService;

/**
 * 部门的action
 * 
 * @author zxy
 * 
 */
@SuppressWarnings("all")
public class DepartmentAction extends BaseAction {
	private DepartmentService deptService;
	private UserService userService;
	private Department dept;
	private int page;
	private int rows;
	private String sort;
	private String order;
	private String deptName;
	private String parentId;
	private String id;
	private String ids;
	private ITreeService treeService;
	private String isOrArea;
	private String deptTree;

	private final static Logger logger = LoggerFactory
			.getLogger(DepartmentAction.class);

	public void addDept() {
		OperateInfo operateInfo = new OperateInfo();
		try {
			Department dd = deptService.insert(dept);
			operateInfo.setOperateMessage("添加部门成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			operateInfo.setOperateMessage("添加部门失败");
			operateInfo.setOperateSuccess(false);
		}
		Struts2Utils.renderJson(operateInfo, EncodingHeaderUtil.HEADERENCODING);

	}

	public void updateDept() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = deptService.update(dept);
		// sysDeptIndustryService.insert(dept);
		if (flag) {
			operateInfo.setOperateMessage("更新部门成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新部门失败");
			operateInfo.setOperateSuccess(false);
		}
		Struts2Utils.renderJson(operateInfo, EncodingHeaderUtil.HEADERENCODING);
	}

	public void deleteDept() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = deptService.delete(dept);
		if (flag) {
			operateInfo.setOperateMessage("删除部门成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("删除部门失败");
			operateInfo.setOperateSuccess(false);
		}
		Struts2Utils.renderJson(operateInfo, EncodingHeaderUtil.HEADERENCODING);
	}

	public void deleteDeptById() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = deptService.delete(id);
		// sysDeptIndustryService.deleteByDeptId(id);
		if (flag) {
			boolean userDelFlag = deptService.deleteUserByDept(id);
			operateInfo.setOperateMessage("删除部门成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("删除部门失败");
			operateInfo.setOperateSuccess(false);
		}
		Struts2Utils.renderJson(operateInfo, EncodingHeaderUtil.HEADERENCODING);
	}
	/**
	 * 获取所有的部门列表
	 * @return void
	 */
	public void findAll() {
		List<Department> depts = deptService.query(null);
		Struts2Utils.renderText(new JsonMapper().toJson(depts));
	}
	/**
	 * 获取所有的部门&用户列表
	 * @return void
	 */
	public void findAllDeptUser() {
		List list = deptService.findAll();
		List users = userService.findUserDto();
		list.addAll(users);
		Struts2Utils.renderText(new JsonMapper().toJson(list));
	}
	
	
	/**
	 * 获取所有的部门&用户列表
	 * @return void
	 */
	public void findAllEntDeptUser() {
		List list = deptService.findAllDeptEntDept(new Department());
		Struts2Utils.renderText(new JsonMapper().toJson(list));
	}
	
	public void findChildrenUser(){
		List list = userService.findChildrenUserDto(id);
		Struts2Utils.renderText(new JsonMapper().toJson(list));
	}

	public void pageList() {
		DataStore<Department> dataStore = new DataStore<Department>();
		Page pageTemp = new Page();
		pageTemp.setCurrentPage(this.page);
		pageTemp.setRecPerPage(this.rows);
		Map paramMap = new HashMap();
		paramMap.put("deptName", deptName);
		paramMap.put("parentId", parentId);
		Page resultPage = deptService.pageQuery(paramMap, pageTemp);
		List<Department> resultList = (List<Department>) resultPage
				.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		Struts2Utils.renderJson(dataStore, EncodingHeaderUtil.HEADERENCODING);
	}
	
	public void reloadDeptUserTree()
    {
		UserService userService=SpringContextHolder.getBean(UserService.class);;
	    UserInfo userInfo=(UserInfo) session.get(ApplicationSessionSchema.SESSION_ONLINE_INFO);	//获取当前登录用户
	    User user=userService.getById(userInfo.getUserId());	//查询当前登录用户的部门
	    DepartmentTreeService departmentTreeService = DepartmentTreeService
                .getInstance();
	    if(!user.getAccount().equals("administrator")){
		    userTree(user.getDeptId());	//获取当前登录用户的上级部门树
	        departmentTreeService.reloadUserTree(deptTree);
	    }else{
	    	departmentTreeService.reloadGovDept();
	    }
        String treeJson = treeService.generateJsonCheckboxTree(
        		departmentTreeService, false);
        Struts2Utils.renderJson(treeJson);
    }

	public void pageListUser() {
		DataStore<User> dataStore = new DataStore<User>();
		Page pageTemp = new Page();
		pageTemp.setCurrentPage(this.page);
		pageTemp.setRecPerPage(this.rows);
		Page resultPage = deptService.findAllDepartmentUser(id, pageTemp);
		List<User> list = (List<User>) resultPage.getCollection();
		dataStore.setRows(list);
		dataStore.setTotal(new Long(resultPage.getCount()));
		Struts2Utils.renderJson(dataStore, EncodingHeaderUtil.HEADERENCODING);
	}
	
	public void userTree(String id){
		Department dept=deptService.getById(id);
		if(!"-1".equals(dept.getParentId())){
			deptTree+=","+dept.getId();
			userTree(dept.getParentId());
		}else{
			deptTree+=","+dept.getId();
			deptTree=deptTree.substring(1, deptTree.length());
		}
	}

	public void getById() {
		Department department = deptService.getById(id);
		Struts2Utils.renderJson(department, EncodingHeaderUtil.HEADERENCODING);
	}
	
	public void getDeptByName() {
		Department department = deptService.getDeptByName(id);
		Struts2Utils.renderJson(department, EncodingHeaderUtil.HEADERENCODING);
	}

	public void saveOrUpdate() {
		OperateInfo operateInfo = new OperateInfo();
		if (StringUtil.isNullOrEmpty(dept.getParentId())) dept.setParentId("-1");
		if (StringUtil.isNullOrEmpty(dept.getId())) {
			if(deptService.getDeptByName(dept.getDeptName()) != null){
				operateInfo.setOperateMessage("表单已存在");
				operateInfo.setOperateSuccess(true);
			}else{
				try {
					Department dd = deptService.insert(dept);
					operateInfo.setOperateMessage("保存成功");
					operateInfo.setOperateSuccess(true);
				} catch (Exception e) {
					e.printStackTrace();
					operateInfo.setOperateMessage("保存失败");
					operateInfo.setOperateSuccess(false);
				}
			}
		} else {
			boolean flag = deptService.update(dept);
			if (flag) {
				operateInfo.setOperateMessage("更新成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("更新失败");
				operateInfo.setOperateSuccess(false);
			}
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	public void deleteByIds() {
		String idsParam[] = ids.split("[,]");
		boolean flag = deptService.delete(idsParam);
		boolean userDelFlag = true;
		OperateInfo operateInfo = new OperateInfo();
		for (String idParam : idsParam) {
			userDelFlag = deptService.deleteUserByDept(idParam);
		}
		if (flag && userDelFlag) {
			operateInfo.setOperateMessage("删除部门成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("删除部门失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	/**
	 * reload tree 方法
	 */
	public void reloadDeptTree() {
		DepartmentTreeService departmentTreeService = DepartmentTreeService
				.getInstance();
		if(dept != null && dept.getParentType()!=null && !dept.getParentType().equals("")){
			if(dept.getParentType().equals("1")){
				departmentTreeService.reloadDepartmentTree();
			}
			if(dept.getParentType().equals("2")){
				departmentTreeService.reloadEntTree();
			}
		}else{
			departmentTreeService.reloadDepartmentTree();
		}
		String treeJson = treeService.generateJsonCheckboxTree(
				departmentTreeService, false);
		logger.info(treeJson);
		Struts2Utils.renderJson(treeJson);
	}
	
	public void  reloadAreaTree(){
		DepartmentTreeService departmentTreeService=DepartmentTreeService.getInstance();
		Map map=BeanUtil.Bean2Map(dept);
		if("1".equals(isOrArea)){
			departmentTreeService.reloadAreaDeptTree(map);
		}else{
			departmentTreeService.reloadDtpeTree(map);
		}
		String treeJson=treeService.generateJsonCheckboxTree(departmentTreeService,false);
		logger.info(treeJson);
		Struts2Utils.renderJson(treeJson);
	}
	
	/**
	 * reload tree 方法
	 */
	public void reloadDeptEntTree() {
		DepartmentTreeService departmentTreeService = DepartmentTreeService
				.getInstance();
				departmentTreeService.reloadEntTree();
		String treeJson = treeService.generateJsonCheckboxTree(
				departmentTreeService, false);
		logger.info(treeJson);
		Struts2Utils.renderJson(treeJson);
	}
	/**
	 * 重载部门ComboTree json生成
	 */
	public void reloadDeptComboTree() {
		DepartmentTreeService departmentTreeService = DepartmentTreeService
				.getInstance();
		departmentTreeService.reloadDepartmentTree();
		String treeJson = treeService
				.generateJsonComboTree(departmentTreeService);
		logger.info(treeJson);
		Struts2Utils.renderJson(treeJson);
	}
	/**
	 * 获得出来管理员的所有部门
	 */
	public void reloadGovDept(){
		DepartmentTreeService departmentTreeService=DepartmentTreeService.getInstance();
		departmentTreeService.reloadGovDept();
		String treeJson=treeService.generateJsonCheckboxTree(departmentTreeService,false);
		logger.info(treeJson);
		Struts2Utils.renderJson(treeJson);
	}
	public void findByParentId(){
		List<Department> depts=deptService.findByParentId(id);
		String json=new JsonMapper().toJson(depts);
		Struts2Utils.renderText(json);
	}
	
	/**
	 * 获取手机端展示用户列表
	 */
	public void findDeptUser(){
		List<Department> list = deptService.findAll();
		List<UserDTO> users = userService.findUserDto();
		TreeDTO treedto = new TreeDTO<>();
		Department dpt = null;
		for(Department d : list){
			if(d.getParentId().equals("-1")){
				dpt = d;
				break;
			}
		}
		
		treedto.setObj(dpt);
		treedto.setType("company");
		treedto.setChildren(this.setChildrenIem(dpt, list,users));
		Struts2Utils.renderText(new JsonMapper().toJson(treedto));
	}
	
	public List<TreeDTO> setChildrenIem(Department department,List<Department> departmentList,List<UserDTO> userList){
		List<TreeDTO> list = new ArrayList<>();
		for(Department d : departmentList){
			if(d.getParentId().equals(department.getId())){
				TreeDTO td = new TreeDTO<>();
				td.setObj(d);
				td.setType("department");
				td.setChildren(this.setChildrenIem(d, departmentList,userList));
				list.add(td);
			}
		}
		for(UserDTO u : userList){
			if(u.getDeptId().equals(department.getId())){
				TreeDTO td = new TreeDTO<>();
				td.setObj(u);
				td.setType("user");
				td.setChildren(null);
				list.add(td);
			}
		}
		return list;
	}
	
	public DepartmentService getDeptService() {
		return deptService;
	}

	public void setDeptService(DepartmentService deptService) {
		this.deptService = deptService;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	public ITreeService getTreeService() {
		return treeService;
	}

	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}
	public UserService getUserService()
    {
        return userService;
    }

    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

	public String getIsOrArea() {
		return isOrArea;
	}

	public void setIsOrArea(String isOrArea) {
		this.isOrArea = isOrArea;
	}

	public static Logger getLogger() {
		return logger;
	}


}
