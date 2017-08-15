package com.ay.jfds.create.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.jfds.create.pojo.Columns;
import com.ay.jfds.create.service.ColumnsService;
import com.ay.jfds.create.tools.CreateTable;
import com.ay.jfds.create.tools.Jdbc;
import com.ay.jfds.create.tools.Tools;
import com.ay.framework.core.action.BaseAction;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("all")
public class ColumnsAction extends BaseAction {
	private ColumnsService columnsService;
	private Columns col;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	private String oldColumns;
	private String log;
	
	public void add() {
		OperateInfo operateInfo = new OperateInfo(true);
		try {
			if(!columnsService.isExits(BeanUtil.Bean2Map(col))){
				operateInfo.setOperateMessage("列已经存在");
				operateInfo.setOperateSuccess(false);	
			}else{
				CreateTable.addColumns(col);	//添加一列
				columnsService.insert(col);
				operateInfo.setOperateMessage("添加成功");
				operateInfo.setOperateSuccess(true);	
			}
		} catch (Exception e) {
			operateInfo.setOperateMessage("添加失败"+e.getMessage());
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	/**
	 * 列名是否存在
	 */
	public void isExits(){
		boolean isExits=columnsService.isExits(BeanUtil.Bean2Map(col));
		Struts2Utils.renderText(isExits+"");
	}
	
	public void update() {
		OperateInfo operateInfo = new OperateInfo();
		Columns columns=columnsService.getById(col.getId());
		try {
			CreateTable.updateColumns(col, columns.getColumnEN());
			boolean flag = columnsService.update(col);
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void delete() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = false;
		try {
			if (ids.contains(",")) {
				String idsParam[] = ids.split(",");
				for(String id:idsParam){
					Columns columns=columnsService.getById(id);
					CreateTable.deleteColumns(columns);
				}
				flag = columnsService.delete(idsParam);
			} else {
				Columns columns=columnsService.getById(ids);
				CreateTable.deleteColumns(columns);
				flag = columnsService.delete(ids);
			}
				operateInfo.setOperateMessage("删除成功");
				operateInfo.setOperateSuccess(true);
		} catch (SQLException e) {
			e.printStackTrace();
			operateInfo.setOperateMessage("删除失败"+e.getMessage());
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);		
	}
	/**
	 * 保存或者修该
	 */
	public void saveOrUpdate(){
		if(log.equals("add")){
			add();
		}else if(log.equals("update")){
			update();
		}
	}
	
	public void list() {
		DataStore<Columns> dataStore = new DataStore<Columns>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = BeanUtil.Bean2Map(col);
		Page resultPage = columnsService.pageQuery(paramMap, pageTemp);
		List<Columns> resultList = (List<Columns>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void getById() {
		Struts2Utils.renderJson(columnsService.getById(id),
				EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);		
	}
	public String exp(){
		String where = " where 1=1 ";
		 Map map = BeanUtil.Bean2Map(col);
		if (map != null){
			for (Object o : map.keySet()){
				where += " and " + o.toString() + " like '%" + map.get(o) + "%' ";
			}
		}
		excelQuerySql = "		select * from sys_user" + where;
		System.out.println(excelQuerySql);
		excelSheetName = "测试名称";
		excelHeads = new String[]{ "名字1", "名字2", "名字3" };
		return "exp";
	}	
	
	public Columns getCol() {
		return col;
	}
	
	public void setCol(Columns col) {
		this.col = col;
	}
	public ColumnsService getColumnsService() {
		return columnsService;
	}

	public void setColumnsService(ColumnsService columnsService) {
		this.columnsService = columnsService;
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
	public String getOldColumns() {
		return oldColumns;
	}
	public void setOldColumns(String oldColumns) {
		this.oldColumns = oldColumns;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
}
