package com.ay.jfds.dev.service;

import java.util.ArrayList;
import java.util.List;

import com.ay.framework.common.CommonTree;
import com.ay.framework.common.CommonTreeNode;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.util.ObjectUtil;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.pojo.DataType;

/**
 * @author ps 元数据的树
 */
public class DataTreeService extends CommonTree {
	private static DataTreeService instance = null;

	private DataTreeService() {

	}

	public static DataTreeService getInstance() {
		synchronized (DataTreeService.class) {
			if (null == instance) {
				instance = new DataTreeService();
			}
		}
		return instance;
	}

	@Override
	protected CommonTreeNode transform(Object info) {
		CommonTreeNode node = new CommonTreeNode();
		if (info instanceof Data) {
			Data dd = (Data) info;
			if (dd.getParentId().equals("-1"))
				node.setOpen(true);
			else
				node.setOpen(false);
			node.setNodeId(dd.getId());
			node.setParentId(dd.getParentId());
			node.setType("data");
			node.setQtip(dd.getDataName());
			node.setText(dd.getDataName());
			node.setBindData(ObjectUtil.attributesToMap(dd));
		} else if (info instanceof CommonTreeNode) {
			node = (CommonTreeNode) info;
		}
		return node;
	}

	public CommonTreeNode generateRootNode(String name) {
		CommonTreeNode root = new CommonTreeNode();
		root.setNodeId("-1");
		root.setType("root");
		root.setText(name);
		root.setParentId("0");
		root.setOpen(true);
		return root;
	}

	private List<Data> findAllData() {
		DataService dataService = this.getDataService();
		return dataService.findAll();
	}

	private List<DataType> findAllDataType() {
		DataTypeService dataTypeService = this.getDataTypeService();
		return dataTypeService.findAll();
	}

	/**
	 * 根据数据类型筛选出Data
	 * 
	 * @param idParam
	 * @return
	 */
	private List<Data> findData(String idParam) {
		DataService dataService = this.getDataService();
		return dataService.getDataByType(idParam);
	}

	private DataService getDataService() {
		return SpringContextHolder.getBean("dataService");
	}

	private DataTypeService getDataTypeService() {
		return SpringContextHolder.getBean("dataTypeService");
	}

	/**
	 * 这边做的是根据筛选的DATA展示树
	 * 
	 * @param idParam
	 */
	public void reloadDataTree(String idParam) {
		List<Object> nodes = new ArrayList<Object>();
		try {
			nodes.add(this.generateRootNode(this.getDataTypeService()
					.getById(idParam).getTypeName()));
			nodes.addAll(this.findData(idParam));
			super.reload(nodes, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
