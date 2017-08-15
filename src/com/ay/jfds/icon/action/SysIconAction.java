package com.ay.jfds.icon.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.DateUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.framework.util.ImageMinCutUtil;
import com.ay.framework.util.ImgCutUtil;
import com.ay.jfds.icon.pojo.SysIcon;
import com.ay.jfds.icon.service.SysIconService;
import com.ay.jfds.sys.dto.UserDTO;
import com.ay.framework.core.action.BaseAction;

@SuppressWarnings("all")
public class SysIconAction extends BaseAction {
	private SysIconService sysIconService;
	private SysIcon icon;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	private File img;
	private String imgFileName;
	
	public void add() {
		OperateInfo operateInfo = new OperateInfo(true);
		setIconByte();
		try {
			sysIconService.insert(icon);
			operateInfo.setOperateMessage("添加成功");
			operateInfo.setOperateSuccess(true);
	
		} catch (Exception e) {
			operateInfo.setOperateMessage("添加失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void myAdd(){
		OperateInfo operateInfo = new OperateInfo(true);
		setIconByte();
		String userId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id");
		UserDTO user = (UserDTO) SecurityUtils.getSubject().getSession().getAttribute("user");
		if(icon!=null){
			icon.setStatus("1");
			icon.setCreated(userId==null?icon.getId():userId);
			icon.setUpdated(userId);
			icon.setCreateTime(DateUtil.getDateTime());
			icon.setUpdateTime(DateUtil.getDateTime());
		}
		try {
			String iconNo=sysIconService.add(icon);
			operateInfo.setOperateMessage("添加成功");
			operateInfo.setOperateSuccess(true);
			operateInfo.setUuid(iconNo);
		} catch (Exception e) {
			operateInfo.setOperateMessage("添加失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void update() {
		OperateInfo operateInfo = new OperateInfo();
		setIconByte();	//设置文件与转换文件
		boolean flag = sysIconService.update(icon);
		if (flag) {
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void delete() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = false;
		if (ids.contains(",")) {
			String idsParam[] = ids.split(",");
			flag = sysIconService.delete(idsParam);
		} else {
			flag = sysIconService.delete(ids);
		}
		if (flag) {
			operateInfo.setOperateMessage("删除成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("删除失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);		
	}
	
	public void list() {
		DataStore<SysIcon> dataStore = new DataStore<SysIcon>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = BeanUtil.Bean2Map(icon);
		Page resultPage = sysIconService.pageQuery(paramMap, pageTemp);
		List<SysIcon> resultList = (List<SysIcon>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void getById() {
		Struts2Utils.renderJson(sysIconService.getById(id),
				EncodingHeaderUtil.HEADERENCODING,
				EncodingHeaderUtil.CACHEENCODING);		
	}
	public String exp(){
			String where = " where 1=1 ";
			 Map map = BeanUtil.Bean2Map(icon);
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
	
	public void setIconByte(){
		try {
			icon.setIcon(img==null?null:ImageMinCutUtil.fileTobyte(img));
			int pointIndex = imgFileName.lastIndexOf('.');
			String fileType = null;
			if(pointIndex !=-1){
				fileType = imgFileName.substring(pointIndex+1);
			}
			icon.setType(fileType);
			icon.setName(imgFileName.substring(0,pointIndex));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SysIcon getIcon() {
		return icon;
	}

	public void setIcon(SysIcon icon) {
		this.icon = icon;
	}

	public SysIconService getSysIconService() {
		return sysIconService;
	}

	public void setSysIconService(SysIconService sysIconService) {
		this.sysIconService = sysIconService;
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

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}
	
}
