package com.ay.jfds.dev.service;

import java.util.ArrayList;
import java.util.List;

import com.ay.framework.common.CommonTree;
import com.ay.framework.common.CommonTreeNode;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.util.ObjectUtil;
import com.ay.jfds.dev.pojo.Menu;

/**
 * 菜单树TreeService
 * 
 * @author py
 *
 */
public class MenuEntTreeService extends CommonTree {
	
	private static MenuEntTreeService instance = null;
	
	private MenuEntTreeService() {}
	
	public static MenuEntTreeService getInstance() {
		synchronized (MenuEntTreeService.class) {
			if (null == instance) {
				instance = new MenuEntTreeService();
			}
		}
		return instance;
	}

	@Override
	protected CommonTreeNode transform(Object info) {
		CommonTreeNode node = new CommonTreeNode();
		if (info instanceof Menu) {
			Menu dd = (Menu) info;
			node.setNodeId(dd.getMenuId());
			if (dd.getMenuSjbh().trim().equals("0")) {
				node.setParentId("-1");
			} else {
				node.setParentId(dd.getMenuSjbh());
			}
			node.setType("Menu");
			node.setQtip(node.getType() + dd.getMenuName());
			node.setText(dd.getMenuName());
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
        root.setText("菜单权限管理树");
        return root;
    }
    
    private List<Menu> findAllMenu() {
    	MenuService menuService = this.getMenuService();
    	return menuService.getAllEntMenu();
    }
    
    private MenuService getMenuService() {
    	return SpringContextHolder.getBean("menuService");
    }
	
    public void reloadMenuTree() {
    	List<Object> nodes = new ArrayList<Object>();
    	try {
    		nodes.add(this.generateRootNode());
    		nodes.addAll(this.findAllMenu());
    		super.reload(nodes, null);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
