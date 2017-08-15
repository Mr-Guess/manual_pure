package com.ay.test.bean.sys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ay.framework.common.ITreeService;
import com.ay.jfds.sys.pojo.Department;
import com.ay.jfds.sys.service.DepartmentService;
import com.ay.jfds.sys.service.DepartmentTreeService;

public class DepartServiceTest {
	private DepartmentService departmentService;
	private ITreeService treeService;
	
	public ITreeService getTreeService() {
		return treeService;
	}

	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@Before
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml", "spring/applicationContext-sys.xml");
		this.departmentService = (DepartmentService) context.getBean("deptService");
		this.treeService = (ITreeService) context.getBean("treeService");
	}
	
	@Test
	public void testAdd() {
		Department depart = new Department();
		depart.setCreated("admin");
		depart.setCreateTime(new Date());
		depart.setDeptName("测试部");
		depart.setParentId("");
		depart.setStatus("0");
		depart.setUpdated("admin");
		depart.setUpdateTime(new Date());
		depart = departmentService.insert(depart);
		Assert.assertEquals(depart.getId().length(), 32);
	}
	
	@Test
	public void testSelect() {
		Department department = departmentService.getById("6901e0f410434df1b94eb4efefd9965f");
		String createTime = new SimpleDateFormat("yyyy-MM-dd").format(department.getCreateTime());
		Assert.assertEquals(createTime, "2012-09-27");
	}
	
	@Test
	public void testAddChild() {
		Department department = new Department();
		department.setCreateTime(new Date());
		department.setDeptName("测试部下属部门3");
		department.setParentId("6901e0f410434df1b94eb4efefd9965f");
		department.setStatus("0");
		department.setUpdated("admin");
		department.setUpdateTime(new Date());
		department = departmentService.insert(department);
		Department departmentParent = new Department();
		departmentParent.setId("6901e0f410434df1b94eb4efefd9965f");
		Assert.assertEquals(4, departmentService.findAllChildDept(departmentParent).size());
		//Assert.assertEquals(department.getId(), departmentService.findAllChildDept(departmentParent).get(0).getId());
	}
	
	@Test
	public void testSelect2() {
		List<Department> list = departmentService.findAll();
		Assert.assertEquals(0, list.size());
	}
	
	@Test
	public void testDepartmentTree() {
		DepartmentTreeService departmentTreeService = DepartmentTreeService.getInstance();
//        departmentTreeService.reloadDepartmentTree(departmentService);
        String treeJson = treeService.generateJsonCheckboxTree(departmentTreeService, false);
        System.out.println(treeJson);
	}
	
	@Test
	public void testDepartSeviceFindAll() {
		List<Department> list = departmentService.findAll();
		for (Iterator<Department> iterator=list.iterator(); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
	}
}
