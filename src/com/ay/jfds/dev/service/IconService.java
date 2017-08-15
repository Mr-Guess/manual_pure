package com.ay.jfds.dev.service;



import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ay.framework.core.service.BaseService;
import com.ay.jfds.dev.dao.IconDao;
import com.ay.jfds.dev.pojo.Icon;

/**
 * 图标上传service层
 * @author wuguowen修改
 * @version 1.0
 * @datetime 2012-10-24
 *
 */
public class IconService extends BaseService<Icon, IconDao>{
	public Icon addIcon(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return this.getDao().addIcon(request, response);
	}
	
	public void delBatchIcon(HttpServletRequest request,final List<Icon> iconList)throws Exception {
		this.getDao().delBatchIcon(request, iconList);
	}
	
	/**
	 * 添加上传的图标
	 * @param iconFileName
	 * @param icon
	 * @return icon
	 */
	public Icon addIcon(String iconFileName,String icon_leibie,File icon,HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		return this.getDao().addIcon(iconFileName,icon_leibie, icon, request, response);
	}
}
