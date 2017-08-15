package com.ay.jfds.dev.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.core.action.BaseAction;
import com.ay.jfds.core.CoreAction;
import com.ay.jfds.dev.dto.DataDTO;
import com.ay.jfds.dev.pojo.Icon;
import com.ay.jfds.dev.service.DataService;
import com.ay.jfds.dev.service.IconService;

/**
 * 图片管理
 * 
 * @DateTime: 2012-9-24
 * @author lushigai
 * @version 1.0
 */
public class IconAction extends BaseAction {
	private IconService iconService;//图片管理接口
	private DataService dataService;
	public DataService getDataService() {
		return dataService;
	}
	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}


	private String operflag;//操作类型
	private String icon_leibie;//图标所属类型
	private List iconList;//图标所属类型
	private List<DataDTO> leibieList;
	private int iconListSize = 0;
	
	private String uploadFileName;
	private File upload;
	private static Logger logger = LoggerFactory.getLogger(CoreAction.class);//日志

	public int getIconListSize() {
		return iconListSize;
	}
	public void setIconListSize(int iconListSize) {
		this.iconListSize = iconListSize;
	}
	public List getIconList() {
		return iconList;
	}
	public void setIconList(List iconList) {
		this.iconList = iconList;
	}
	public String getOperflag() {
		return operflag;
	}
	public void setOperflag(String operflag) {
		this.operflag = operflag;
	}
	public String getIcon_leibie() {
		return icon_leibie;
	}
	public void setIcon_leibie(String icon_leibie) {
		this.icon_leibie = icon_leibie;
	}
	public IconService getIconService() {
		return iconService;
	}
	public void setIconService(IconService iconService) {
		this.iconService = iconService;
	}
	
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}


	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	
	//获取图标列表
	public String list() {
		leibieList = dataService.getSelectDataByTypeName("图标类型");
		response.setContentType("text/html;utf-8");
		System.out.println("进来方法list");
		Map<String,Object> map = new HashMap<String,Object>();
		if(icon_leibie!=null && icon_leibie.trim().length()>0){
			map.put("icon_leibie", icon_leibie);
		}
		iconList = iconService.query(map);
		leibieList = dataService.getSelectDataByTypeName("图标类型");
		if(null!=iconList){
			iconListSize = iconList.size();
		}
		
		if("menuSelect".equals(operflag)){
			return "selected";
		}
		else {
			return "list";
		}
	}
	
	public List<DataDTO> getLeibieList() {
		return leibieList;
	}
	public void setLeibieList(List<DataDTO> leibieList) {
		this.leibieList = leibieList;
	}
	//新增后保存图标，同时上传图片文件
	public String addSave() {
		System.out.println("进来方法addSave");
		request.getParameter("filetype");
		try{
			iconService.addIcon(uploadFileName,icon_leibie,upload,request, response);
			Map<String,Object> map = new HashMap<String,Object>();
			if(icon_leibie!=null && icon_leibie.trim().length()>0){
				map.put("icon_leibie", icon_leibie);
			}
			iconList = iconService.query(map);
			leibieList = dataService.getSelectDataByTypeName("图标类型");
		}
		catch (Exception e) {
			logger.error("上传图标失败：", e);
		}
		if(null!=iconList){
			iconListSize = iconList.size();
		}
		return "list";
	}
	
	//批量删除图标，同时删除图标文件
	public String delBatch() {
		try{
			response.setContentType("text/html;utf-8");
			System.out.println("进来方法delBatch");
			String[] iconstr = request.getParameterValues("lim");
			List<Icon> iconListNew = new Vector();//只保存icon_id  icon_type
			for(int i=0; i<iconstr.length; i++){
				String[] str = iconstr[i].split(":");
				Icon icon = new Icon();
				icon.setIcon_id(str[0]);
				icon.setIcon_type(str[1]);
				iconListNew.add(icon);
			}
		
			//批量删除表中记录
			iconService.delBatchIcon(request,iconListNew);
			Map<String,Object> map = new HashMap<String,Object>();
			if(icon_leibie!=null && icon_leibie.trim().length()>0){
				map.put("icon_leibie", icon_leibie);
			}
			iconList = iconService.query(map);
			leibieList = dataService.getSelectDataByTypeName("图标类型");
			if(null!=iconList){
				iconListSize = iconList.size();
			}
		}
		catch (Exception e) {
			logger.error("批量删除图标失败：：", e);
		}
		return "list";
		
	}
    
}
