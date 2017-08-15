package com.ay.framework.common;

/**
 * Tree Service
 * 
 * @author zxy
 *
 */
public interface ITreeService {
	/**
	 * 创建CheckBox Tree
	 * 
	 * @param tree 要创建的树
	 * @param isCheck 是否是已经Check
	 * @return json类型的对象
	 */
	public String generateJsonCheckboxTree(CommonTree tree, Boolean isCheck);
	
	/**
	 * 创建ComboTree所需要的json数据
	 * 
	 * @param tree
	 * @return
	 */
	public String generateJsonComboTree(CommonTree tree);
}
