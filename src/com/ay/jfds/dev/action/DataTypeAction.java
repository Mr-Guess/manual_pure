package com.ay.jfds.dev.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.common.ITreeService;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.framework.util.StringUtil;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.pojo.DataType;
import com.ay.jfds.dev.service.DataTypeService;
import com.ay.jfds.dev.service.DataTypeTreeService;
import org.apache.struts2.ServletActionContext;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 元数据类型的action
 * 
 * @author PS
 */
@SuppressWarnings("all")
public class DataTypeAction extends BaseAction {
	private DataTypeService dataTypeService;
	private DataType dataType;
	private Data data;
	private String dataName;
	private String page;
	private String rows;
	private String sort;
	private String typeName;
	private String id;
	private String ids;
	private ITreeService treeService;
	private static Logger logger = LoggerFactory
			.getLogger(DataTypeAction.class);

	/**
	 * 增加数据类型方法
	 */
	public void addDataType() {
		OperateInfo operateInfo = new OperateInfo();
		dataType.setCreated((String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id"));
		dataType.setCreateTime(new Date());
		if (dataTypeService.checkWord(this.dataType.getTypeName())) {
			operateInfo.setOperateMessage("已经存在相同的数据类型，请不要重复添加");
			operateInfo.setOperateSuccess(false);
			String json = new JsonMapper().toJson(operateInfo);
			Struts2Utils.renderText(json);
			return;
		}

		if (dataTypeService.checkWordById(dataType.getId())) {
			operateInfo.setOperateMessage("已经存在相同的编码，请不要重复添加");
			operateInfo.setOperateSuccess(false);
			String json = new JsonMapper().toJson(operateInfo);
			Struts2Utils.renderText(json);
			return;
		}
		
		try {
			dataTypeService.insert(dataType);
			operateInfo.setOperateMessage("添加元数据类型成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			logger.error("{}添加数据发生系统错误{}", this, e);
			e.printStackTrace();
			operateInfo.setOperateMessage("添加元数据类型失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 更新数据类型
	 */
	public void updateDataType() {
		OperateInfo operateInfo = new OperateInfo();
		dataType.setUpdated((String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id"));
		dataType.setUpdateTime(new Date());
		DataType updateDataType = dataTypeService.getById(dataType.getId());
		updateDataType.setTypeName(dataType.getTypeName());
		if (dataTypeService.checkWord(updateDataType.getTypeName())) {
			operateInfo.setOperateMessage("已经存在相同的数据类型，请不要重复修改");
			operateInfo.setOperateSuccess(false);
			String json = new JsonMapper().toJson(operateInfo);
			Struts2Utils.renderText(json);
			return;
		}
//		if (dataTypeService.checkWordById(updateDataType.getId())) {
//			operateInfo.setOperateMessage("已经存在相同的编码，请不要重复添加");
//			operateInfo.setOperateSuccess(false);
//			String json = new JsonMapper().toJson(operateInfo);
//			Struts2Utils.renderText(json);
//			return;
//		}
		try {

			boolean flag = dataTypeService.update(updateDataType);
			if (flag) {
				operateInfo.setOperateMessage("更新元数据类型成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("更新元数据类型失败");
				operateInfo.setOperateSuccess(false);
			}
		} catch (Exception e) {
			logger.error("{}更新元数据时发生系统错误{}", this, e);
			e.printStackTrace();
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 删除单个数据类型
	 */
	public void deleteDataType() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = dataTypeService.delete(dataType);
		boolean dataDelFlag = dataTypeService
				.deleteDataByType(dataType.getId());
		try {
			if (flag && dataDelFlag) {
				operateInfo.setOperateMessage("删除元数据类型成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("删除元数据类型失败");
				operateInfo.setOperateSuccess(false);
			}
		} catch (Exception e) {
			logger.error("{}删除时发生系统错误{}", this, e);
		}
		Struts2Utils.renderJson(operateInfo, EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}

	/**
	 * 删除单个数据类型 根据Id
	 */
	public void deleteDataTypeById() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = dataTypeService.delete(dataType);
		boolean dataDelFlag = dataTypeService.deleteDataByType(id);
		try {
			if (flag && dataDelFlag) {
				operateInfo.setOperateMessage("删除元数据类型成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("删除元数据失败");
				operateInfo.setOperateSuccess(false);
			}
		} catch (Exception e) {
			logger.error("{}删除时发生系统错误{}", this, e);
		}
		Struts2Utils.renderJson(operateInfo, EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}

	/**
	 * 分页
	 */
	public void pageList() {
		DataStore<DataType> dataStore = new DataStore<DataType>();
		Page pageTemp = new Page();
		// 当前页
		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10"
				: rows);
		int start = (intPage - 1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = BeanUtil.Bean2Map(dataType);
		Page resultPage = dataTypeService.pageQuery(paramMap, pageTemp);
		List<DataType> resultList = (List<DataType>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}

	/**
	 * 查看数据类别下面的元数据
	 */
	public void pageListData() {
		DataStore<Data> dataStore = new DataStore<Data>();
		Page pageTemp = new Page();
		// 当前页
		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10"
				: rows);
		int start = (intPage - 1) * number;
		pageTemp.setCurrentPage(start);
		pageTemp.setRecPerPage(number);
		Map paramMap = new HashMap();
		paramMap.put("dataName", dataName);
		Page resultPage = dataTypeService.findAllDataInTypeById(id, pageTemp);
		List<Data> list = (List<Data>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(list);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}

	public void getById() {
		DataType dataType = null;
		dataType = dataTypeService.getById(id);
		Struts2Utils.renderJson(dataType, EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);
	}

	/**
	 * 更新或保存
	 */
	public void saveOrUpdate() {
		OperateInfo operateInfo = new OperateInfo();
		DataTypeService dataTypeService = new DataTypeService();
		if (dataTypeService.checkWord(this.dataType.getTypeName())) {
			operateInfo.setOperateMessage("已经存在相同的数据类型，请不要重复添加");
			operateInfo.setOperateSuccess(false);
			String json = new JsonMapper().toJson(operateInfo);
			Struts2Utils.renderText(json);
			return;
		}
		try {
			if (StringUtil.isNullOrEmpty(dataType.getId())) {
				DataType dataType = new DataType();
				dataType.setTypeName(this.dataType.getTypeName());
				dataType.setCreated(this.dataType.getCreated());
				dataType.setUpdated(this.dataType.getUpdated());
				dataTypeService.insert(dataType);
				operateInfo.setOperateMessage("保存成功");
				operateInfo.setOperateSuccess(true);
			} else {
				DataType dataType1 = dataTypeService.getById(dataType.getId());
				dataType1.setTypeName(dataType.getTypeName());
				dataType1.setCreated(dataType.getCreated());
				dataType1.setUpdated(dataType.getUpdated());
				boolean flag = dataTypeService.update(dataType1);
				if (flag) {
					operateInfo.setOperateMessage("更新成功");
					operateInfo.setOperateSuccess(true);
				} else {
					operateInfo.setOperateSuccess(false);
					operateInfo.setOperateMessage("更新失败");
				}
			}
		} catch (Exception e) {
			logger.error("{}发生系统错误{}", this, e);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 删除多个元数据类型
	 */
	public void deleteByIds() {
		String idsParam[] = ids.split("[,]");
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = dataTypeService.deleteByIds(idsParam);
		try {
			if (flag) {
				operateInfo.setOperateMessage("删除元素类型成功");
				operateInfo.setOperateSuccess(true);
			} else {
				operateInfo.setOperateMessage("删除元素类型失败");
				operateInfo.setOperateSuccess(false);
			}
		} catch (Exception e) {
			logger.error("{}删除时发生系统错误{}", this, e);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 重载tree
	 */
	public void reloadDataTypeTree() {
		DataTypeTreeService dataTypeTreeService = DataTypeTreeService
				.getInstance();
		dataTypeTreeService.reloadDataTypeTree();
		String treeJson = treeService.generateJsonCheckboxTree(
				dataTypeTreeService, false);
		Struts2Utils.renderJson(treeJson);
	}

	public DataTypeService getDataTypeService() {
		return dataTypeService;
	}

	public void setDataTypeService(DataTypeService dataTypeService) {
		this.dataTypeService = dataTypeService;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
}
