package com.ay.jfds.create.action;

import generate.PojoCreater;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.jfds.create.pojo.Columns;
import com.ay.jfds.create.pojo.Sheet;
import com.ay.jfds.create.service.ColumnsService;
import com.ay.jfds.create.service.SheetService;
import com.ay.jfds.create.tools.CreateTable;
import com.ay.jfds.create.tools.Jdbc;
import com.ay.jfds.create.tools.Tools;
import com.ay.framework.core.action.BaseAction;

@SuppressWarnings("all")
public class SheetAction extends BaseAction {
	private SheetService sheetService;
	private Sheet sheet;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	private String colls;	//所有列
	private String mobuleType;// 所属模块 
	private String mobulesee;
	private String type;
	public void add() {
		OperateInfo operateInfo = new OperateInfo(true);
		try {
			sheetService.insert(sheet);
			operateInfo.setOperateMessage("添加成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			operateInfo.setOperateMessage("添加失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	/**
	 * 创建表
	 */
	public void createTable(){
		OperateInfo operateInfo = new OperateInfo(true);
		if(!sheetService.isExits(sheet.getNameEn())){	//判断表是否存在
			operateInfo.setOperateMessage("表已经存在");
			operateInfo.setOperateSuccess(false);
		}else{
			setColmns();
			ColumnsService columnsService=SpringContextHolder.getBean(ColumnsService.class);
			try {
				CreateTable.createTable(sheet);//创建表
				for(Columns column:sheet.getColumns()){
					columnsService.insert(column);	//插入列信息
				}
				PojoCreater.createJavaBean(sheet);
				operateInfo.setOperateMessage("建表成功");
				operateInfo.setOperateSuccess(true);
			} catch (Exception e) {
				sheetService.delete(sheet.getId());
				operateInfo.setOperateMessage("建表失败"+e.getMessage());
				operateInfo.setOperateSuccess(false);
			}
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	/*
	 *判断表是否存在 
	 */
	public void isExits(){
		boolean isExits=sheetService.isExits(sheet.getNameEn());
		Struts2Utils.renderText(isExits+"");
	}
	
	public void update() {
		OperateInfo operateInfo = new OperateInfo();
		Sheet oldSheet=sheetService.getById(sheet.getId());
		try {
			CreateTable.updateTable(sheet, oldSheet.getNameEn());
			boolean flag = sheetService.update(sheet);
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
		} catch (SQLException e) {
			e.printStackTrace();
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	/**
	 * 删除
	 */
	public void delete() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = false;
		ColumnsService columnsService=SpringContextHolder.getBean(ColumnsService.class);
		try {
			if (ids.contains(",")) {
				String idsParam[] = ids.split(",");
				for(String id:idsParam){
					Sheet sheet=sheetService.getById(id);
					CreateTable.deleteTable(sheet);
					for(Columns col:columnsService.queryForSheet(sheet.getId())){
						columnsService.delete(col.getId());
					}
				}
				flag = sheetService.delete(idsParam);
			} else {
				Sheet sheet=sheetService.getById(ids);
				CreateTable.deleteTable(sheet);
				for(Columns col:columnsService.queryForSheet(sheet.getId())){
					columnsService.delete(col.getId());
				}
				flag = sheetService.delete(ids);
			}
			operateInfo.setOperateMessage("删除成功");
			operateInfo.setOperateSuccess(true);
		} catch (SQLException e) {
			operateInfo.setOperateMessage("删除失败"+e.getMessage());
			operateInfo.setOperateSuccess(false);
			e.printStackTrace();
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);		
	}
	
	public void list() {
		DataStore<Sheet> dataStore = new DataStore<Sheet>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = BeanUtil.Bean2Map(sheet);
		Page resultPage = sheetService.pageQuery(paramMap, pageTemp);
		List<Sheet> resultList = (List<Sheet>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void getById() {
		Struts2Utils.renderJson(sheetService.getById(id),
				EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);		
	}
	//解析json
	public void setColmns(){
		Sheet sh=sheetService.insert(sheet);	//插入表信息
		try {
			String temp = new String(colls.getBytes("iso-8859-1"),"UTF-8");
			JSONArray array=JSONArray.fromObject(temp);
			for(int i=0;i<array.size();i++){
				Columns column=(Columns)JSONObject.
						toBean((JSONArray.fromObject(array.toString()).
								getJSONObject(i)),Columns.class);
				column.setSheetId(sh.getId());
				sheet.setColumns(column);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public String exp(){
		String where = " where 1=1 ";
		 Map map = BeanUtil.Bean2Map(sheet);
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
    public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public SheetService getSheetService() {
		return sheetService;
	}

	public void setSheetService(SheetService sheetService) {
		this.sheetService = sheetService;
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

	public String getColls() {
		return colls;
	}

	public void setColls(String colls) {
		this.colls = colls;
	}
	public String getMobuleType() {
		return mobuleType;
	}
	public void setMobuleType(String mobuleType) {
		this.mobuleType = mobuleType;
	}
	public String getMobulesee() {
		return mobulesee;
	}
	public void setMobulesee(String mobulesee) {
		this.mobulesee = mobulesee;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
