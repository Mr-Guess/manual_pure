package com.ay.jfds.dev.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.jfds.core.CustomTemplate;
import com.ay.jfds.dev.pojo.MenuOpt;
import com.ay.jfds.dev.service.MenuOptService;

/**
 * 菜单权限管理
 * 
 * @DateTime: 2012-9-27
 * @author lushigai
 * @version 1.0
 */
public class MenuOptAction {
	private MenuOptService menuOptService;//菜单权限接口
	private String optCode;//菜单权限编码
	private String optName;//菜单权限名称
	private String optType;//菜单权限类型
	private String detail;//菜单权限数据
	private MenuOpt menuOpt;
	private File templateFile;//模板文件
	private String templateFileFileName; //模板文件名
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	private List menuOptList;
	private Integer menuId;
	private String menuName;
	
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	
//    public String list(){
//    	Map<String,Object> map = new HashMap<String ,Object>();
//    	map.put("menuId", menuId);
////    	MenuOpt opt = menuOptService.getById("1");
////    	opt.setMenuId(44);
////    	menuOptService.update(opt);
////    	menuOptService.insert(opt);
//        //menuOptService.insertOpt();
//    	menuOptList = menuOptService.query(map);
//    	return "menuOptList";
//	
//	}
    
    public void list(){
    	DataStore<MenuOpt> dataStore = new DataStore<MenuOpt>();
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("menuId", menuId);
    	List<MenuOpt> menuOptList=menuOptService.query(map);
    	if(menuOptList!=null){
	    	dataStore.setRows(menuOptList);
	    	dataStore.setTotal(new Long(menuOptList.size()));
	    	System.out.println(dataStore);
	    	Struts2Utils.renderJson(dataStore, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
    	}
    }
    
    /**
	 * 上传文件
	 * @return
	 */
	public String uploadFile(){
		
		String seq = File.separator;
		/*
		HttpServletRequest req = ServletActionContext.getRequest();		
		
		
		InputStream pis = ServletActionContext.getServletContext().getResourceAsStream(seq+"WEB-INF"+seq+"templateUrl.properties");
		Properties pros = new Properties();
		try {
			pros.load(pis);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String savePath = pros.getProperty("uploadUrl"); 
		File fileSavePath = new File(ServletActionContext.getServletContext().getRealPath("/") + File.separator + savePath);
		if(!fileSavePath.exists()){
			fileSavePath.mkdirs();
		}
		*/
		MenuOpt mo = menuOptService.getById(menuOpt.getId());
		if (this.templateFile != null && templateFileFileName!=null) {
			String relativeFilePath = seq
					+ "freemarker"
					+ seq
					+ templateFileFileName.substring(0,
							templateFileFileName.lastIndexOf(".")) + ".ftl";
			String templateFilePath = ServletActionContext.getServletContext()
					.getRealPath("/")
					+ "freemarker"
					+ seq
					+ templateFileFileName.substring(0,
							templateFileFileName.lastIndexOf(".")) + ".ftl";
			templateFileFileName = relativeFilePath;
			
			//取出所有menuId下的按钮列表
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("menuId", mo.getMenuId());
			List<MenuOpt> menuOptList=menuOptService.query(map);
			
			CustomTemplate.generateTemplateFile(templateFile, templateFilePath,
					mo.getMenuId());
			request.setAttribute("templateName", templateFileFileName);
			mo.setDesignPageUrl(relativeFilePath);
		}
		mo.setDisplayType(menuOpt.getDisplayType());
		mo.setOpenType(menuOpt.getOpenType());
		menuOptService.update(mo);
		return "upload";
	}
	
	/**
	 * 更新
	 */
	public void updateMenuOpt(){
		boolean flag = false;
		if(this.menuOpt!=null){
			MenuOpt mo = menuOptService.getById(this.menuOpt.getId());
			if(null!=menuOpt.getFuncName()&&!"".equals(menuOpt.getFuncName())){
				mo.setFuncName(this.menuOpt.getFuncName());
			}
			if(null!=menuOpt.getFuncPara()&&!"".equals(menuOpt.getFuncPara())){
				mo.setFuncPara(this.menuOpt.getFuncPara());
			}
			if(null!=menuOpt.getFuncContent()&&!"".equals(menuOpt.getFuncContent())){
				mo.setFuncContent(this.menuOpt.getFuncContent());
			}
			mo.setOpenType(this.menuOpt.getOpenType());
			mo.setDisplayType(this.menuOpt.getDisplayType());
			if(!this.menuOpt.getFormId().trim().isEmpty()){
				mo.setFormId(this.menuOpt.getFormId());
			}
			mo.setUrlPara(this.menuOpt.getUrlPara());
			flag = menuOptService.update(mo);
		}
		if(flag){
			operateShow(true,"操作成功");
			return;
		}else{
			operateShow(false, "操作失败");
		}
	}
    
    public void addMenuOpt(){
    	System.out.println("detail:"+detail+"; menuId="+menuId);
    	menuOptService.addMenuOpt(detail,menuId);
    	Map<String,Object> map = new HashMap<String ,Object>();
    	map.put("menuId", menuId);
    	menuOptList = menuOptService.query(map);
    	request.setAttribute("status", "success");
    	try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * 返回操作状态
	 * @param isOk
	 */
	private void operateShow(boolean isOk, String msg){
		OperateInfo operateInfo = new OperateInfo();
		if(isOk){
			operateInfo.setOperateMessage(msg);
			operateInfo.setOperateSuccess(true);
		}else{
			operateInfo.setOperateMessage(msg);
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
		operateInfo = null;
		json = null;
	}
	
    public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public List getMenuOptList() {
		return menuOptList;
	}

	public void setMenuOptList(List menuOptList) {
		this.menuOptList = menuOptList;
	}

	public MenuOptService getMenuOptService() {
		return menuOptService;
	}

	public void setMenuOptService(MenuOptService menuOptService) {
		this.menuOptService = menuOptService;
	}

	public String getOptCode() {
		return optCode;
	}

	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public MenuOpt getMenuOpt() {
		return menuOpt;
	}

	public void setMenuOpt(MenuOpt menuOpt) {
		this.menuOpt = menuOpt;
	}

	public File getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(File templateFile) {
		this.templateFile = templateFile;
	}

	public String getTemplateFileFileName() {
		return templateFileFileName;
	}

	public void setTemplateFileFileName(String templateFileFileName) {
		this.templateFileFileName = templateFileFileName;
	}
	
}
