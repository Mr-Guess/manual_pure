package com.ay.jfds.dev.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.service.BaseService;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.framework.util.StringUtil;
import com.ay.jfds.dev.dao.DataDao;
import com.ay.jfds.dev.dto.DataDTO;
import com.ay.jfds.dev.pojo.Data;

/**
 * data service 的封装
 * 
 * @author PS
 * 
 */
@SuppressWarnings("all")
public class DataService extends BaseService<Data, DataDao> {

	/**
	 * 对DTO的分页封装操作
	 * 
	 * @param map
	 * @param page
	 * @return
	 */
	public Page pageDTOQuery(Map map, Page page) {
		page.setCount(this.count(map));
		List<DataDTO> list = dao.pageDTOQuery(map, page.getFrom(),
				page.getRecPerPage());
		page.setCollection(list);
		return page;
	}

	/**
	 * 根据ID 查询到DATADTO
	 * 
	 * @param id
	 * @return
	 */
	public DataDTO getDataDTOById(String id) {
		return this.getDao().getDataDTOById(id);
	}

	/**
	 * 根据PARENTiD查找到子数据 实现无限极查找
	 * 
	 * @param parentId
	 * @param page
	 * @return
	 */
	public Page findChildData(String parentId, Page page) {
		page.setCount(this.count(new HashMap()));
		List<Data> list = this.getDao().findAllChildData(parentId);
		page.setCollection(list);
		return page;
	}
	
	/**
	 * 根据PARENTiD查找到子数据 实现无限极查找
	 * 
	 * @param parentId
	 * @param page
	 * @return
	 */
	public List findChildlist(String parentId) {
		List<Data> list = this.getDao().findAllChildData(parentId);
		return list;
	}
	public List<Data> findAllDatas(){
		return this.getDao().findAllData();
	}
	/**
	 * 根据TYPE ID 筛选 元数据
	 * 
	 * @param idParam
	 * @return
	 */
	public List<Data> getDataByType(String idParam) {
		return (List<Data>) this.getDao().getDataByType(idParam);
	}

	/**
	 * 防止Data 数据重复
	 * 
	 * @param dataName
	 * @param parentId
	 * @return
	 */
	public boolean checkWord(Map checkMap) {
		int integer = this.getDao().chickWordByWords(checkMap);
		if (integer != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 防止更新数据的时候变成重复数据
	 * 
	 * @param dataName
	 * @param dataCode
	 * @return
	 */
	public boolean checkUpdateWord(Map checkUpdateMap) {
		int integer = this.getDao().chickUpdateWords(checkUpdateMap);
		if (integer != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据元数据类别名称获取下拉框数据
	 * 
	 * @param typeName
	 * @return
	 */
	public List<DataDTO> getSelectDataByTypeName(String typeName) {
		return (List<DataDTO>) this.getDao().getSelectDataByTypeName(typeName);
	}

	/**
	 * 根据父id获得所有子对象
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Data> findAllChilden(String parentId) {
		return (List<Data>) this.getDao().findAllChildData(parentId);
	}

	/**
	 * 根据条件获得data
	 * 
	 * @param map
	 * @return
	 */
	public List<Data> findData(Map map) {
		return (List<Data>) this.getDao().findData(map);
	}

	/**
	 * 根据条件只查询出一个
	 * 
	 * @param map
	 * @return
	 */
	public Data findOne(Map map) {
		List<Data> list = this.getDao().findData(map);
		System.out.println(list);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}

	}

	/** 根据当前数据获得级别链 */
	public List<Data> getDataChain(String dataId) {
		Data data = this.dao.getById(dataId);
		List<Data> dataList = new ArrayList<Data>();
		if (data != null) {
			setDataChain(data, dataList);
			dataList.add(data);
		}
		return dataList;
	}
	/**
	 * 通过typeId和dataCode查询
	 * @param map
	 * @return
	 */
	public List<Data> findCodeAndType(Map map){
		return dao.findCodeAndType(map);
	}

	/**
	 * 递归
	 * 
	 * @param data
	 * @param dataList
	 *            return pid
	 */
	private void setDataChain(Data data, List<Data> dataList) {
		String pid = data.getParentId();
		if (pid != null && !pid.equals("-1")) {
			Data pData = this.dao.getById(pid);
			setDataChain(pData, dataList);
			dataList.add(pData);
		}
	}

	
	/**
	 * 递归要删除的元数据 返回 list，list里面都是要删除的Id 
	 * @param parentId
	 * @param removeList
	 */
	private void getRemoveListByPartent(String parentId, List<String> removeList) {
		List<Data> dataList = this.getDao().findAllChildData(parentId);
		if (!dataList.isEmpty()) {
			for (Data data : dataList) {
				Data dataParam = data;
				removeList.add(dataParam.getId());
				System.out.println(dataParam.getId());
				getRemoveListByPartent(data.getId(),removeList);
			}
		}
	}
	
	public List<String> removeList(String parentId) {
		Data data = this.getDao().getById(parentId);
		List<String> removeList = new ArrayList<String>();
		if (data != null) {
			getRemoveListByPartent(parentId, removeList);
			removeList.add(parentId);
			System.out.println(parentId);
		}
		return removeList;
	}
	
	public List<Data> findDataByTypeFirstLevel(String typeId) {
	    return this.getDao().findDataByTypeFirstLevel(typeId);
	}
	public List<Data> findDataByTypeNameFirstLevel(String typeName) {
	    return this.getDao().findDataByTypeNameFirstLevel(typeName);
	}
	public Data findParentData(String id){
	   return this.getDao().findParentData(id);
	}
	
	public List<Data> findGridByLevel (String level) {
		if (StringUtil.isEmpty(level))
			level = "0";
		return this.getDao().findGridByLevel(level);
	}
	
	public List<Data> findByType(Map map){
		Map paramMap = new HashMap();
		return this.getDao().findByType(map);
	}
	
	public Data getByCode(Map map){
		return this.getDao().getByCode(map);
	}
	
}
