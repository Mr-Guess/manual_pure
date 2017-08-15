package com.ay.jfds.sys.service;

import com.ay.framework.common.CommonTree;
import com.ay.framework.common.CommonTreeNode;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.util.ObjectUtil;
import com.ay.jfds.sys.pojo.Department;
import com.ay.jfds.sys.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门人员树
 * 
 * @author zxy
 *
 */
public class DepartmentUserTreeService extends CommonTree {

    private static DepartmentUserTreeService instance = null;

    private DepartmentUserTreeService() {

    }

    public static DepartmentUserTreeService getInstance() {
        synchronized (DepartmentUserTreeService.class) {
            if (null == instance) {
                instance = new DepartmentUserTreeService();
            }
        }
        return instance;
    }

    /**
     * 当CommonTreeService向下调用子类的这个方法的时候会进行相应的判断
     *
     * @param info
     * @return
     */
    @Override
    protected CommonTreeNode transform(Object info) {
        CommonTreeNode node = new CommonTreeNode();
        if (info instanceof Department) {
            Department dd = (Department) info;
            node.setNodeId(dd.getId());
            node.setParentId(dd.getParentId());
            node.setType("Department");
            node.setQtip(node.getType() + dd.getDeptName());
            node.setText(dd.getDeptName());
            node.setBindData(ObjectUtil.attributesToMap(dd));
        } else if (info instanceof CommonTreeNode) {
            node = (CommonTreeNode) info;
        } else if (info instanceof User) {
            User dd = (User) info;
            node.setNodeId(dd.getId());
            node.setParentId(dd.getDeptId());
            node.setType("User");
            node.setQtip(node.getType() + dd.getUserName());
            node.setText(dd.getUserName());
            node.setBindData(ObjectUtil.attributesToMap(dd));
        }
        return node;
    }

    private CommonTreeNode generateRootNode() {
        CommonTreeNode root = new CommonTreeNode();
        root.setNodeId("-1");
        root.setType("Root");
        root.setParentId("0");
        root.setText("组织机构");
        return root;
    }
    
    private CommonTreeNode entRootNode() {
        CommonTreeNode root = new CommonTreeNode();
        root.setNodeId("-1");
        root.setType("Root");
        root.setParentId("0");
        root.setText("企业类型");
        return root;
    }

    private DepartmentService getDepartmentService() {
        return SpringContextHolder.getBean("deptService");
    }
    
    private List<Department> findAllEnt() {
        DepartmentService departmentService = this.getDepartmentService();
        return departmentService.findAllEntDept(new Department());
    }

    private UserService getUserService() {
        return SpringContextHolder.getBean("userService");
    }

    private List<Department> findAllDepartment() {
        DepartmentService departmentService = this.getDepartmentService();
        return departmentService.findAll();
    }

    private List<User> findAllUser() {
        UserService userService = this.getUserService();
        return userService.findAll();
    }
    
    private List<User> findAllEntUser() {
        UserService userService = this.getUserService();
        return userService.getAllentUser();
    }

    public void reloadDepartmentUserTree() {
        List<Object> nodes = new ArrayList<Object>();
        try {
            nodes.add(this.generateRootNode());
            nodes.addAll(this.findAllDepartment());
            nodes.addAll(this.findAllUser());
            super.reload(nodes, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reloadDepartmentEntUserTree() {
        List<Object> nodes = new ArrayList<Object>();
        try {
        	nodes.add(this.entRootNode());
            nodes.addAll(this.findAllEnt());
            nodes.addAll(this.findAllEntUser());
            super.reload(nodes, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
