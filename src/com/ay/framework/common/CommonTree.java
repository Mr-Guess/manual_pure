package com.ay.framework.common;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 树
 * 
 * @author zxy
 *
 */
public abstract class CommonTree {
	protected static Log log = LogFactory.getLog(CommonTree.class);

	private Map<String, CommonTreeNode> treeNodeMaps = new Hashtable<String, CommonTreeNode>();

	private CommonTreeNode root;

	protected void reload(List<Object> nodes, Object rootNode) {
		log.info("tree will start reload all data");
		boolean hasRoot = false;
		synchronized (this) {
			treeNodeMaps.clear();
			root = null;

			List<CommonTreeNode> treeNodes = new ArrayList<CommonTreeNode>();
			for (int i = 0; i < nodes.size(); i++) {
				CommonTreeNode node = this.transform(nodes.get(i)); // transform
				treeNodes.add(node);
				node.setTree(this);
				treeNodeMaps.put(node.getNodeId().trim(), node);
			}
			if (rootNode != null) {
				CommonTreeNode node = this.transform(rootNode);
				node.setType("Root");
				treeNodes.add(node);
				node.setTree(this);
				treeNodeMaps.put(node.getNodeId().trim(), node);

				root = node;
				hasRoot = true;
			}
			for (int i = 0; i < treeNodes.size(); i++) {
				CommonTreeNode node = (CommonTreeNode) treeNodes.get(i);
				String parentId = node.getParentId().trim();

				if (hasRoot == false && this.isRootNode(node)) {
					if (root == null) {
						root = node;
					} else {
						log.error("find more then one root node. ignore.");
					}
				} else {
					CommonTreeNode parent = (CommonTreeNode) treeNodeMaps
							.get(parentId);
					if (parent != null) {
						parent.addChild(node);
						node.setParent(parent);
					} else {
						log.warn("node [id=" + node.getNodeId() + " text="
								+ node.getText() + " parentid="
								+ node.getParentId()
								+ "]: missing parent node.");
					}
				}
			}
		}

		if (root == null) {
			log.error("the root node is not be defined");
		}
	}

	protected boolean isRootNode(CommonTreeNode node) {
		if (node.getParentId() == null || "0".equals(node.getParentId())
				|| "null".equals(node.getParentId())) {
			return true;
		} else {
			return false;
		}
	}

	public CommonTreeNode getRootNode() {
		return root;
	}
	
	public void setRootNode(CommonTreeNode node) {
		this.root = node;
	}

	public CommonTreeNode getTreeNode(String nodeId) {
		return (CommonTreeNode) treeNodeMaps.get(nodeId);
	}

	public void addTreeNode(CommonTreeNode node) {
		synchronized (this) {
			treeNodeMaps.put(node.getNodeId(), node);

			String parentId = node.getParentId();
			if (StringUtils.isNotBlank(parentId)) {
				CommonTreeNode parent = getTreeNode(parentId);
				if (parent != null) {
					parent.addChild(node);
					node.setParent(parent);
				} else {
					log.error("parent cannot be found: " + node.getParentId());
				}
			} else {
				if (root == null) {
					root = node;
				} else {
					log.error("find more then one root node. ignore.");
				}
			}
		}
	}

	public void deleteTreeNode(String nodeId) {
		synchronized (this) {
			CommonTreeNode node = getTreeNode(nodeId);
			if (node == null)
				throw new IllegalArgumentException(nodeId + " cannot be found.");

			if (node.getParent() == null) {
				root = null;
				treeNodeMaps.clear();
				log.warn("the root node has been removed.");
			} else {
				node.getParent().getChildren().remove(node);

				treeNodeMaps.remove(nodeId);
				List<CommonTreeNode> children = node.getAllChildren();
				for (int i = 0; i < children.size(); i++) {
					CommonTreeNode n = (CommonTreeNode) children.get(i);
					treeNodeMaps.remove(n.getNodeId());
				}
			}
		}
	}

	protected abstract CommonTreeNode transform(Object info);
}
