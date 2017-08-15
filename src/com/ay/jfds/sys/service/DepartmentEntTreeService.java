package com.ay.jfds.sys.service;

import com.ay.framework.common.CommonTree;
import com.ay.framework.common.CommonTreeNode;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.util.ObjectUtil;
import com.ay.jfds.sys.pojo.Department;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;

/**
 * 部门树
 * 
 * @author zxy
 *
 */
public class DepartmentEntTreeService extends CommonTree {
	
	private static DepartmentEntTreeService instance = null;
	
	private DepartmentEntTreeService() {
	}
	
	public static DepartmentEntTreeService getInstance() {
		synchronized (DepartmentEntTreeService.class) {
			if (null == instance) {
				instance = new DepartmentEntTreeService();
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

    private List<Department> findAllDepartment() {
        DepartmentService departmentService = this.getDepartmentService();
        return departmentService.findAll();
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

}
