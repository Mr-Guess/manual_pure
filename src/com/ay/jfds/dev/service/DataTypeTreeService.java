package com.ay.jfds.dev.service;

import java.util.ArrayList;
import java.util.List;

import com.ay.framework.common.CommonTree;
import com.ay.framework.common.CommonTreeNode;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.util.ObjectUtil;
import com.ay.jfds.dev.pojo.DataType;

/**
 * 元数据类型树
 * 
 * @author PS
 * 
 */
public class DataTypeTreeService extends CommonTree {

	private static DataTypeTreeService instance = null;

	private DataTypeTreeService() {

	}

	public static DataTypeTreeService getInstance() {
		synchronized (DataTypeTreeService.class) {
			if (null == instance) {
				instance = new DataTypeTreeService();
			}
		}
		return instance;
	}

	@Override
	protected CommonTreeNode transform(Object info) {
		CommonTreeNode node = new CommonTreeNode();
		if (info instanceof DataType) {
			DataType dd = (DataType) info;
			node.setNodeId(dd.getId());
			node.setType("DataType");
			node.setQtip(node.getType() + dd.getTypeName());
			node.setText(dd.getTypeName());
			node.setBindData(ObjectUtil.attributesToMap(dd));
		} else if (info instanceof CommonTreeNode) {
			node = (CommonTreeNode) info;
		}
		return node;
	}

	private CommonTreeNode generateRootNode() {
		CommonTreeNode root = new CommonTreeNode();
		root.setNodeId("-1");
		root.setType("root");
		root.setText("元数据类别树");
		return root;
	}

	private List<DataType> findAllDataType() {
		DataTypeService dataTypeService = this.getDataTypeService();
		return dataTypeService.findAll();
	}

	private DataTypeService getDataTypeService() {
		return SpringContextHolder.getBean("dataTypeService");
	}

	public void reloadDataTypeTree() {
		List<Object> nodes = new ArrayList<Object>();
		try {
			nodes.add(this.generateRootNode());
			nodes.addAll(this.findAllDataType());
			super.reload(nodes, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
