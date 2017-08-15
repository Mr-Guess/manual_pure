package com.ay.framework.common;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.util.JsonUtil;
import com.ay.framework.util.StringUtil;

public class TreeService implements ITreeService {
	private Logger log = LoggerFactory.getLogger(TreeService.class);

	/*
	 * (non-Javadoc)
	 * @see com.ay.framework.common.ITreeService#generateJsonCheckboxTree(com.ay.framework.common.CommonTree, java.lang.Boolean)
	 */
	public String generateJsonCheckboxTree(CommonTree tree, Boolean isCheck) {
		StringBuilder json = new StringBuilder();
		json.append("{\"items\":[");
		CommonTreeNode root = tree.getRootNode();
		if (root != null) {
			//添加root
			json.append("{");
			json.append("\"id\":\"").append(root.getNodeId()).append("\",");
			json.append("\"pid\":\"").append(root.getParentId()).append("\",");
			json.append("\"name\":\"").append(root.getText()).append("\",");
			json.append("\"text\":\"").append(root.getText()).append("\",");
			json.append("\"nodeType\":\"").append(root.getType()).append("\",");
			json.append("\"isHidden\":").append(root.isHidden()).append(",");
			json.append("\"open\":").append(root.isOpen()).append(",");
			json.append("\"icon\":\"").append(StringUtil.clean(root.getIcon())).append("\",");
			json.append("\"qtip\":\"").append(root.getQtip()).append("\",");
			if (isCheck) {
				json.append("\"checked\":").append(root.isChecked())
						.append(",");
			}
			if (root.isLeaf()) {
				json.append("\"leaf\":").append(true).append(",");
				json.append("\"children\":[],");
			} else {
				json.append("\"leaf\":").append(false).append(",");
				json.append("\"children\":[")
						.append(recursion(root, isCheck)).append("],");
			}
			json.append("\"attributes\":")
					.append(JsonUtil.map2json((Map<?, ?>) root
							.getBindData())).append("");
			// 当有连接时，在这里添加连接的标签。
			// json.append("'href':'").append("#").append("'");
			json.append("},");
			if (json.charAt(json.length() - 1) == ',') {
				json.deleteCharAt(json.length() - 1);
			}
		}
		json.append("]}");
		if (log.isDebugEnabled()) {
			log.debug(json.toString());
		}
		return json.toString();
	}

	public String recursion(CommonTreeNode nodes, Boolean isCheck) {
		StringBuilder json = new StringBuilder();
		List<CommonTreeNode> childrens = nodes.getChildren();
		for (CommonTreeNode node : childrens) {
			json.append("{");
			json.append("\"id\":\"").append(node.getNodeId()).append("\",");
			json.append("\"pid\":\"").append(node.getParentId()).append("\",");
			json.append("\"name\":\"").append(node.getText()).append("\",");
			json.append("\"text\":\"").append(node.getText()).append("\",");
			json.append("\"nodeType\":\"").append(node.getType()).append("\",");
			json.append("\"isHidden\":").append(node.isHidden()).append(",");
			json.append("\"open\":").append(node.isOpen()).append(",");
			json.append("\"icon\":\"").append(StringUtil.clean(node.getIcon())).append("\",");
			json.append("\"qtip\":\"").append(node.getQtip()).append("\",");
			if (isCheck) {
				json.append("\"checked\":").append(node.isChecked())
						.append(",");
			}
			if (node.isLeaf()) {
				json.append("\"leaf\":").append(true).append(",");
				json.append("\"children\":[],");
			} else {
				json.append("\"leaf\":").append(false).append(",");
				json.append("\"children\":[")
						.append(recursion(node, isCheck)).append("],");
			}
			json.append("\"attributes\":")
					.append(JsonUtil.map2json((Map<?, ?>) node
							.getBindData())).append("");
			// json.append("'href':'").append("#").append("'");
			json.append("},");
		}
		if (json.charAt(json.length() - 1) == ',') {
			json.deleteCharAt(json.length() - 1);
		}
		return json.toString();
	}
	
	public String recursion(CommonTreeNode nodes) {
		StringBuilder json = new StringBuilder();
		List<CommonTreeNode> childrens = nodes.getChildren();
		for (CommonTreeNode node : childrens) {
			json.append("{");
			json.append("\"id\":\"").append(node.getNodeId()).append("\",");
			json.append("\"text\":\"").append(node.getText()).append("\",");
			if (node.isLeaf()) {
				json.append("\"children\":[]");
			} else {
				json.append("\"children\":[")
						.append(recursion(node)).append("]");
			}
			json.append("},");
		}
		if (json.charAt(json.length() - 1) == ',') {
			json.deleteCharAt(json.length() - 1);
		}
		return json.toString();

	}

	/*
	 * (non-Javadoc)
	 * @see com.ay.framework.common.ITreeService#generateJsonComboTree(com.ay.framework.common.CommonTree)
	 */
	public String generateJsonComboTree(CommonTree tree) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		CommonTreeNode root = tree.getRootNode();
		if (root != null) {
			//添加root
			json.append("{");
			json.append("\"id\":\"").append(root.getNodeId()).append("\",");
			json.append("\"text\":\"").append(root.getText()).append("\",");
			if (root.isLeaf()) {
				json.append("\"children\":[]");
			} else {
				json.append("\"children\":[")
						.append(recursion(root)).append("]");
			}
			// 当有连接时，在这里添加连接的标签。
			// json.append("'href':'").append("#").append("'");
			json.append("},");
			if (json.charAt(json.length() - 1) == ',') {
				json.deleteCharAt(json.length() - 1);
			}
		}
		json.append("]");
		if (log.isDebugEnabled()) {
			log.debug(json.toString());
		}
		return json.toString();
	}
}
