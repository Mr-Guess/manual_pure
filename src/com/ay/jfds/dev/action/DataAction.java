package com.ay.jfds.dev.action;

import java.util.ArrayList;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.shiro.SecurityUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.common.ITreeService;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.data.DataUtils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.framework.util.StringUtil;
import com.ay.jfds.dev.dto.DataDTO;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.service.DataService;
import com.ay.jfds.dev.service.DataTreeService;

/**
 * 元数据管理
 * 
 * @author PS
 */
@SuppressWarnings("all")
public class DataAction extends BaseAction {
	private DataService dataService;
	private Data data;
	private DataDTO dataDTO;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String dataName;
	private String dataCode;
	private String typeId;
	private String id;
	private String ids;
	private String parentId;
	private ITreeService treeService;
	private String dataOrder;
	private String idParam;
	private static Logger logger = LoggerFactory.getLogger(DataAction.class);
	private static final String CACH_NAME = "dataCache";
	private Cache cache;
	private CacheManager coreEhcacheManager;
	private String dataTypeName;
	private String level;

	/**
	 * 增加元数据
	 */
	public void addData() {
		OperateInfo operateInfo = new OperateInfo();
		try {
			if (StringUtil.isNullOrEmpty(data.getParentId())) {
				data.setParentId("-1");
			}
			dataService.insert(data);
			operateInfo.setOperateMessage("增加元数据成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			logger.error("{}增加元数据的时候发生系统错误{}", this, e);
			e.printStackTrace();
			operateInfo.setOperateMessage("增加元数据失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 更新元数据
	 */
	public void updateData() {
		OperateInfo operateInfo = new OperateInfo();
		data.setUpdated((String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id"));
		data.setUpdateTime(new Date());
		Data updateData = dataService.getById(data.getId());
		updateData.setDataName(data.getDataName());
		updateData.setDataCode(data.getDataCode());
		updateData.setDataOrder(data.getDataOrder());
		updateData.setDataDesc(data.getDataDesc());
		try {
			boolean flag = dataService.update(updateData);
			if (flag) {
				operateInfo.setOperateMessage("更新元数据成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("更新元数据失败");
				operateInfo.setOperateSuccess(false);
			}
		} catch (Exception e) {
			logger.error("{}更新元数据时候发生未知错误{}", this, e);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 删除元数据
	 */
	public void deleteData() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = dataService.delete(data);
		if (flag) {
			Log.info(operateInfo);
			operateInfo.setOperateMessage("删除元数据成功");
			operateInfo.setOperateSuccess(true);
		} else {
			Log.info("删除元数据失败");
			operateInfo.setOperateMessage("删除元数据失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	/**
	 * 通过typeId和dataCode查询
	 */
	public void findCodeAndType(){
		Map map=new HashMap();
		map.put("dataCode", dataCode);
		map.put("typeId",typeId);
		List<Data> datas=dataService.findCodeAndType(map);
		String json=new JsonMapper().toJson(datas);
		Struts2Utils.renderText(json);
	}
	/**
	 * 通过code或者type查询一条数据
	 */
	public void getByCode(){
		Map map=BeanUtil.Bean2Map(data);
		if(data.getTypeId().equals("null")){
			map.remove("typeId");
		}
		Data data=dataService.getByCode(map);
		Struts2Utils.renderJson(data);
	}

	/**
	 * 分页查询所有的元数据，但是没有关联元数据类型,后面会写一个关联元数据类型的分页查询
	 */
	public void pageList() {
		DataStore<Data> dataStore = new DataStore<Data>();
		Page pageTemp = new Page();
		// 当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页显示条数 
		int number = Integer.parseInt((rows == null || rows == "0") ? "10"
				: rows);
		int start = (intPage - 1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = new HashMap();
		paramMap.put("dataName", dataName);
		Page resultPage = dataService.pageQuery(paramMap, pageTemp);
		List<Data> resultList = (List<Data>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}

	/**
	 * 查询DTO 将 元数据类型dataType 关联起来
	 */
	public void pageListDTO() {
		DataStore<DataDTO> dataStore = new DataStore<DataDTO>();
		Page pageTemp = new Page();
		// 当前页
		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10"
				: rows);
		int start = (intPage - 1);
		pageTemp.setCurrentPage(start);
		pageTemp.setRecPerPage(number);
		Map paramMap = new HashMap();
		paramMap.put("dataName", dataName);
		paramMap.put("typeId", typeId);
		Page resultPage = dataService.pageDTOQuery(paramMap, pageTemp);
		List<DataDTO> resultList = (List<DataDTO>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void findByType(){
		/*Map map=new HashMap();
		map.put("typeId", typeId);
		map.put("parentId", parentId);
		List<Data> datas=dataService.findByType(map);*/
		List<Data> datas=DataUtils.getByTypeTree(typeId);
		String json=new JsonMapper().toJson(datas);
		Struts2Utils.renderText(json);
		
	}

	public void getByIdDTO() {
		DataDTO dataDTO = dataService.getDataDTOById(id);
		Struts2Utils.renderJson(dataDTO, EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}

	public void getById() {
		Data data = null;
		data = dataService.getById(id);
		Struts2Utils.renderJson(data, EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}

	/**
	 * 删除多个元数据
	 */
	public void deleteByIds() {
		List<String> listPara = dataService.removeList(id);
		for (Iterator<String> itr = listPara.iterator(); itr.hasNext();) {
			String idpara = itr.next();
			OperateInfo operateInfo = new OperateInfo();
			boolean flag = dataService.delete(idpara);
			if (flag) {
				operateInfo.setOperateMessage("删除元数据属性成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("删除元数据属性失败");
				operateInfo.setOperateSuccess(false);
			}
			String json = new JsonMapper().toJson(operateInfo);
			Struts2Utils.renderText(json);
		}
	}

	public void deleteDataById() {
		OperateInfo operateInfo = new OperateInfo();
		try {
			boolean flag = dataService.delete(id);
			if (flag) {
				operateInfo.setOperateMessage("删除成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("删除失败");
				operateInfo.setOperateSuccess(false);
			}
		} catch (Exception e) {
			logger.error("{}删除元数据时发生系统错误{}", this, e);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 根据parentId 查找子元数据
	 */
	public void findChildList() {
		List<Data> list = dataService.findChildlist(parentId);
		String dataStr = new JsonMapper().toJson(list);
		Struts2Utils.renderText(dataStr);
	}

	/**
	 * 根据parentId 查找子元数据
	 */
	public void findChildPage() {
		DataStore<Data> dataStore = new DataStore<Data>();
		Page pageTemp = new Page();
		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		int number = Integer.parseInt((rows == null || rows == "0") ? "1"
				: rows);
		int start = (intPage - 1) * number;
		pageTemp.setCurrentPage(start);
		pageTemp.setRecPerPage(number);
		Map paramMap = new HashMap();
		paramMap.put("dataName", dataName);
		paramMap.put("dataCode", dataCode);
		paramMap.put("dataOrder", dataOrder);
		Page resultPage = dataService.findChildData(parentId, pageTemp);
		List<Data> list = (List<Data>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(list);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}

	public void saveOrUpdate() {
		OperateInfo operateInfo = new OperateInfo();
		try {
			if (StringUtil.isNullOrEmpty(data.getId())) {
				Map checkMap = new HashMap();
				checkMap.put("dataCode", this.data.getDataCode());
				checkMap.put("parentId", this.data.getParentId());
				checkMap.put("dataName", this.data.getDataName());
				checkMap.put("typeId", this.data.getTypeId());
				checkMap.put("dataDesc", this.data.getDataDesc());
				if (dataService.checkWord(checkMap)) {
					operateInfo.setOperateMessage("已经存在相同的数据类型，请不要重复添加");
					operateInfo.setOperateSuccess(false);
					String json = new JsonMapper().toJson(operateInfo);
					Struts2Utils.renderText(json);
					return;
				}
				Data data = new Data();
				data.setCreated((String) SecurityUtils.getSubject()
						.getSession().getAttribute("user_id"));
				data.setCreateTime(new Date());
				data.setDataName(this.data.getDataName());
				data.setDataCode(this.data.getDataCode());
				data.setDataOrder(this.data.getDataOrder());
				data.setParentId(this.data.getParentId());
				data.setTypeId(this.data.getTypeId());
				data.setDataDesc(this.data.getDataDesc());
				if (this.data.getParentId() == null
						|| this.data.getParentId() == "") {
					operateInfo.setOperateSuccess(false);
					operateInfo.setOperateMessage("请选择父节点");
				}
				dataService.insert(data);
				operateInfo.setOperateMessage("保存成功");
				operateInfo.setOperateSuccess(true);
			} else {
				Data metaData = dataService.getById(data.getId());
				metaData.setUpdated((String) SecurityUtils.getSubject()
						.getSession().getAttribute("user_id"));
				metaData.setUpdateTime(new Date());
				metaData.setId(metaData.getId());
				metaData.setDataCode(data.getDataCode());
				metaData.setDataOrder(data.getDataOrder());
				metaData.setDataName(data.getDataName());
				metaData.setDataDesc(data.getDataDesc());
				Map checkUpdateMap = new HashMap();
				checkUpdateMap.put("dataName", data.getDataName());
				checkUpdateMap.put("dataCode", data.getDataCode());
				checkUpdateMap.put("id", data.getId());
				checkUpdateMap.put("dataDesc", data.getDataDesc());
				if (dataService.checkUpdateWord(checkUpdateMap)) {
					operateInfo.setOperateMessage("已经存在相同的数据类型，请不要重复添加");
					operateInfo.setOperateSuccess(false);
					String json = new JsonMapper().toJson(operateInfo);
					Struts2Utils.renderText(json);
					return;
				}
				boolean flag = dataService.update(metaData);
				if (flag) {
					operateInfo.setOperateSuccess(true);
					operateInfo.setOperateMessage("更新成功");
				} else {
					operateInfo.setOperateSuccess(false);
					operateInfo.setOperateMessage("更新失败");
				}
			}
			// 注意此处，如果是在IE下的话，就使用这样的方式
			// 当response.setContextType("text/html")或者为text等等
			// 在IE下，是不会被识别出来的。IE会当做文件下载来处理
		} catch (Exception e) {
			logger.error("{}发生系统错误{}", this, e);
			operateInfo.setOperateMessage("系统发生错误");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);

	}

	/**
	 * Just for zxy
	 * 
	 * @param idParam
	 */
	public void getDataByType() {
		List<Data> list = dataService.getDataByType(idParam);
		String jsonObj = new JsonMapper().toJson(list);
		Struts2Utils.renderJson(list, EncodingHeaderUtil.CACHEENCODING,
				EncodingHeaderUtil.HEADERENCODING);
	}

	public void getSelectDataByTypeName() throws UnsupportedEncodingException {
		String type = new String(dataTypeName.getBytes("iso-8859-1"), "GBK");
		List<DataDTO> dataList = dataService.getSelectDataByTypeName(type);
		Struts2Utils.renderJson(dataList);
	}

	public void findParentData() {
		Data data = dataService.findParentData(id);
		String josobj = new JsonMapper().toJson(data);
		Struts2Utils.renderJson(data);
	}

	/**
	 * 载入树
	 * 
	 * @param
	 */
	public void reloadDataTree() {
		String treeJson = null;
		// Object cacheForTreJson = getCacheObjByKey(idParam);
		/*
		 * if (cacheForTreJson != null) { treeJson = (String) cacheForTreJson;
		 * Struts2Utils.renderJson(treeJson); logger.info("从缓存中得到对象"); return; }
		 */
		DataTreeService dataTreeService = DataTreeService.getInstance();
		dataTreeService.reloadDataTree(idParam);
		treeJson = treeService.generateJsonCheckboxTree(dataTreeService, false);
		// putCacheObj(idParam, treeJson);
		Struts2Utils.renderJson(treeJson);
	}

	public void findDataByTypeFirstLevel() {
		List<Data> data = dataService.findDataByTypeFirstLevel(typeId);
		String dataStr = new JsonMapper().toJson(data);
		Struts2Utils.renderText(dataStr);
	}

	public void findDataByTypeNameFirstLevel()
			throws UnsupportedEncodingException {
		String type = new String(dataTypeName.getBytes("iso-8859-1"), "GBK");
		List<Data> data = dataService.findDataByTypeNameFirstLevel(type);
		String dataStr = new JsonMapper().toJson(data);
		Struts2Utils.renderText(dataStr);
	}

	public void findAllChildren() {
		List<Data> data = dataService.findAllChilden(parentId);
		Struts2Utils.renderJson(data, EncodingHeaderUtil.HEADERENCODING,
					EncodingHeaderUtil.CACHEENCODING);
	}

	public void findGridLevel() {
		List<Data> data = dataService.findGridByLevel(level);
		Struts2Utils.renderJson(data);
	}

	/** 根据当前数据获得级别链 */
	public void getDataChain() {
		Data data = dataService.getById(id);
		List<String> dataList = new ArrayList<String>();
		dataList.add("0");
		setDataChain(data, dataList);
		dataList.add(data.getId());
		System.out.println(dataList);
		Struts2Utils.renderJson(dataList, EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}

	/**
	 * 递归
	 * 
	 * @param data
	 * @param dataList
	 *            return pid
	 */
	private void setDataChain(Data data, List<String> dataList) {
		if (null != data) {
			String pid = data.getParentId();
			if (pid != null && !pid.equals("-1")) {
				Data pData = dataService.getById(pid);
				setDataChain(pData, dataList);
				dataList.add(pData.getId());
			}
		}
	}

	public DataService getDataService() {
		return dataService;
	}

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public DataDTO getDataDTO() {
		return dataDTO;
	}

	public void setDataDTO(DataDTO dataDTO) {
		this.dataDTO = dataDTO;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public ITreeService getTreeService() {
		return treeService;
	}

	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}

	public String getDataOrder() {
		return dataOrder;
	}

	public void setDataOrder(String dataOrder) {
		this.dataOrder = dataOrder;
	}

	public String getIdParam() {
		return idParam;
	}

	public void setIdParam(String idParam) {
		this.idParam = idParam;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DataAction.logger = logger;
	}

	public void initCache() {
		cache = coreEhcacheManager.getCache(CACH_NAME);
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	

	/*
	 * public void putCacheObj(String key, Object value) { Element element = new
	 * Element(key, value); cache.put(element); }
	 * 
	 * public Object getCacheObjByKey(String key) { initCache(); Element element
	 * = cache.get(key); if (null == element) { return null; } return
	 * element.getObjectValue(); }
	 * 
	 * public CacheManager getCoreEhcacheManager() { return coreEhcacheManager;
	 * }
	 * 
	 * @Autowired public void setCoreEhcacheManager(CacheManager
	 * coreEhcacheManager) { this.coreEhcacheManager = coreEhcacheManager; }
	 */

}
