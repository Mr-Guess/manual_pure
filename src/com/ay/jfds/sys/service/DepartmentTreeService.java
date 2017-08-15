package com.ay.jfds.sys.service;

import com.ay.framework.common.CommonTree;
import com.ay.framework.common.CommonTreeNode;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.util.ObjectUtil;
import com.ay.jfds.sys.pojo.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

/**
 * 部门树
 * 
 * @author zxy
 *
 */
@SuppressWarnings("all")
public class DepartmentTreeService extends CommonTree {
	
	private static DepartmentTreeService instance = null;
	
	private DepartmentTreeService() {
	}
	
	public static DepartmentTreeService getInstance() {
		synchronized (DepartmentTreeService.class) {
			if (null == instance) {
				instance = new DepartmentTreeService();
			}
		}
		return instance;
	}

	@Override
	protected CommonTreeNode transform(Object info) {
        CommonTreeNode node = new CommonTreeNode();
        if (info instanceof Department) {
            Department dd = (Department) info;
            if (dd.getParentId() != "-1")
            	node.setOpen(false);
            else
            	node.setOpen(true);
            node.setNodeId(dd.getId());
            node.setParentId(dd.getParentId());
            node.setType("Department");
            node.setQtip(node.getType() + dd.getDeptName());
            node.setText(dd.getDeptName());
            node.setBindData(ObjectUtil.attributesToMap(dd));
        } else if (info instanceof CommonTreeNode) {
        	node = (CommonTreeNode) info;
        }
		return node;
	}

    private CommonTreeNode generateRootNode() {
        CommonTreeNode root = new CommonTreeNode();
        root.setNodeId("-1");
        root.setType("Root");
        root.setParentId("0");
        root.setText("部门树");
        return root;
    }
    
    
    private CommonTreeNode entRoot(){
    	CommonTreeNode root=new CommonTreeNode();
    	root.setNodeId("-1");
        root.setType("Root");
        root.setParentId("0");
        root.setText("企业类型");
    	return root;
    }
    
    private CommonTreeNode district() {
        CommonTreeNode root = new CommonTreeNode();
        root.setNodeId("-1");
        root.setType("Root");
        root.setParentId("0");
        root.setText("监管部门");
        return root;
    }
    
    

    private List<Department> findAllDepartment() {
        DepartmentService departmentService = this.getDepartmentService();
        return departmentService.findAll();
    }
    
    private List<Department> findGovDept(){
    	return this.getDepartmentService().findGovDept();
    }
    
    private List<Department> findByArea(Map map){
    	return this.getDepartmentService().findByArea(map);
    }
    private List<Department> findByUserTree(String userTrees){
    	return this.getDepartmentService().findByUserTree(userTrees);
    }
    
    private List<Department> findAllEnt() {
        DepartmentService departmentService = this.getDepartmentService();
        return departmentService.findAllEntDept(new Department());
    }
    
    private Department getDept(String id){
    	return getDepartmentService().getById(id);
    }
    private List<Department> findByDistrict(Map map){
    	return this.getDepartmentService().findByDistrict(map);
    }

    private DepartmentService getDepartmentService() {
        return SpringContextHolder.getBean("deptService");
    }
	
    private void filter() {
    	String usertype = (String) SecurityUtils.getSubject().getSession().getAttribute("usertype");
    	if(usertype==null || !usertype.equals("2")){
    		return;
    	}
        String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("user_id");
        String deptId = SpringContextHolder.getBean(UserService.class).getById(userId).getDeptId();
        CommonTreeNode root = super.getRootNode();
        List<CommonTreeNode> children = root.getAllChildren();
        for(CommonTreeNode node :children) {
        	if(node.getNodeId()!=null && node.getNodeId().equals(deptId)) {
//        		root = node;
        		super.setRootNode(node);
        		return;
        	}
        }
    }
    
    public void reloadUserTree(String userTrees){
    	List<Object> nodes=new ArrayList<Object>();
    	try {
    		 nodes.add(this.generateRootNode());
    		 nodes.addAll(this.findByUserTree(userTrees));
    	     super.reload(nodes, null);
    	     filter();
    	 }catch (Exception e) {
             e.printStackTrace();
         }
    }
    
    public void reloadDepartmentTree() {
        List<Object> nodes = new ArrayList<Object>();
        try {
            nodes.add(this.generateRootNode());
            nodes.addAll(this.findAllDepartment());
            super.reload(nodes, null);
            filter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void reloadDeptTree() {
        List<Object> nodes = new ArrayList<Object>();
        try {
            nodes.add(this.generateRootNode());
            nodes.addAll(this.findAllEnt());
            super.reload(nodes, null);
            filter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getAreaName(String code){
    	return this.getDepartmentService().getAreaName(code);
    }
    
    private CommonTreeNode getAddressNodes(String map) {
        CommonTreeNode root = new CommonTreeNode();
        root.setNodeId("-1");
        root.setType("Root");
        root.setParentId("0");
        root.setText(map);
        return root;
    }
    public void reloadDtpeTree(Map map){
    	List<Object> nodes=new ArrayList<Object>();
	    try {
	    	//nodes.addAll(checkRootNodes(this.findByDistrict(map)));//获得区下面部门树 包含父节点
	    	nodes.addAll(checkRootNodes(this.findByDistrict(map)));//获得区下面部门树 不包含父节点
	    	nodes.add(this.getAddressNodes(getAreaName(map.get("area").toString())));//树的根节点
	    	super.reload(nodes, null);
	    	filter();
    	} catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reloadGovDept(){
    	List<Object> nodes=new ArrayList<Object>();
        try {
            nodes.add(this.district());
            nodes.addAll(this.findGovDept());
            super.reload(nodes, null);
            filter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void reloadEntTree() {
        List<Object> nodes = new ArrayList<Object>();
        try {
            nodes.add(this.entRoot());
            nodes.addAll(this.findAllEnt());
            super.reload(nodes, null);
            filter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public void reloadAreaDeptTree(Map map){
    	List<Object> nodes=new ArrayList<Object>();
    	try{
    		nodes.add(this.district());
    		nodes.addAll(this.findByArea(map));
    		super.reload(nodes, null);
    		filter();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	/**
	 * 检查是否有父节点重新组装
	 * @param depts
	 * @return
	 */
	private List<Department> checkRootNodes(List<Department> depts){
		List<Department> deparList=depts;
		for(int i=0;i<depts.size();i++){
			Department dept=depts.get(i);
			if(!dept.getParentId().equals("-1")){
				Department addDept=addRoot(depts,dept.getParentId());
				if(addDept!=null){
					//deparList.add(addDept);//查询父节点并添加
					Department depart=setNodesParentId(depts,dept);
					deparList.remove(dept);//删除
					deparList.add(depart);//重新添加
				}
			}
		}
		return deparList;
	}
	
	//查询父节点
	private Department addRoot(List<Department> depts,String parentId){
		boolean is=false;
		for(Department d:depts){
			if(d.getId().equals(parentId)){
				is=true;break;//判断是否找到父节点
			}
		}
		if(!is){
			return getDept(parentId);
		}
		return null;
	}
	
	//重新设置父节点
	private Department setNodesParentId(List<Department> depts,Department dept){
		boolean is=false;
		for(Department d:depts){
			if(d.getId().equals(dept.getParentId())){
				is=true;break;
			}
		}
		if(!is){
			dept.setParentId("-1");//改变他的父节点
		}
		return dept;
	}
}
