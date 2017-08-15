package com.ay.jfds.dev.dao;

/**
 * 版权所有:
 * @author  lushigai
 * @version 创建时间：2012-9-24
 * 类说明:图标管理操作的dao，实现对icon基本表的增删改查操作  
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.dev.pojo.DevIcon;

public class DevIconDao extends BaseDao<DevIcon> {
	/** 图标文件上传的保存路径 */
	private String PATH_DIR = "jsp/dev" + File.separator + "icon";
	/** 图标文件上传的保存路径 */
	private String UPLOAD_DIR = "upload";

	@Override
	public String getEntityName() {
		// TODO Auto-generated method stub
		return "icon";
	}

	/**
	 * 增加新图标后执行保存操作 向表中插入记录同时上传图片文件
	 * 
	 * @param request
	 *            response
	 * 
	 * @return int 1-插入操作成功；0-插入操作失败
	 */
	public DevIcon addIcon(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String uuidstr = "";
		try {
			java.util.UUID uuid = java.util.UUID.randomUUID();
			uuidstr = uuid.toString();
		} catch (Exception e) {
		}

		DevIcon icon = new DevIcon();
		icon.setIcon_id(uuidstr);
		boolean flag = false;
		// 先上传图片
		flag = uploadFile(request, icon, "add_result");
		if (flag) {
			System.out.println("上传图片成功");
		} else {
			System.out.println("上传图片失败");
		}

		// 向表中插入记录
		System.out.println(icon.toStriong());

		DevIcon index = this.insert(icon);
		if (null != index) {
			System.out.println("插入记录成功");
		} else {
			System.out.println("插入失败");
		}
		return index;
	}
	
	/**
	 * 上传图标
	 * @param iconFileName
	 * @param icon
	 * @param request
	 * @param response
	 * @return
	 */
	public DevIcon addIcon(String iconFileName,String icon_leibie,File icon,HttpServletRequest request,HttpServletResponse response){
		String uuidstr = "";
		try {
			java.util.UUID uuid = java.util.UUID.randomUUID();
			uuidstr = uuid.toString();
		} catch (Exception e) {
		}

		DevIcon im = new DevIcon();
		im.setIcon_id(uuidstr);
		boolean flag = false;	
		// 先上传图片
		flag=uploadIcon(iconFileName,icon_leibie,icon,"add_result",request,im);
		if (flag) {
			System.out.println("上传图片成功");
		} else {
			System.out.println("上传图片失败");
		}

		// 向表中插入记录
		System.out.println(im.toStriong());

		DevIcon index = this.insert(im);
		if (null != index) {
			System.out.println("插入记录成功");
		} else {
			System.out.println("插入失败");
		}
		return index;
	}

	/**
	 * 上传图标文件
	 * 
	 * @param request
	 *            im Icon图标对象 operationType 操作类型
	 *            add_result-新增后上传文件；update_result-更新后上传文件
	 * 
	 * @return boolean true-操作成功；false-操作失败
	 */
	public boolean uploadFile(HttpServletRequest request, DevIcon im,
			String operationType) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			if (processFileUpload(request, im, operationType)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 上传图标文件
	 * @param iconFileName 图标名称
	 * @param icon 图标对象
	 * @param operationType 操作类型
	 * @param request 请求对象
 	 * @return boolean true-操作成功；false-操作失败
	 */
	public boolean uploadIcon(String iconFileName,String icon_leibie,File icon,String operationType,HttpServletRequest request,DevIcon im){
		String icon_name = "";
		String filetype = "";
		String icon_remark = "";
		int itemNum = 0;
		String isupdate = "";
		String icon_id = "";
		String icon_url = "";
		String filename = "";
		filetype=iconFileName.substring(iconFileName.lastIndexOf(".")+1);
		filename=iconFileName;
		try{
			if (iconFileName.equals("filetype")) {
				filetype = iconFileName;
			} else if (iconFileName.equals("icon_name")) {
				icon_name = iconFileName;
				icon_name = icon_name == null ? "" : new String(
						icon_name.getBytes("iso8859-1"), "gb2312");
				im.setIcon_name(icon_name);
			} else if (iconFileName.equals("icon_remark")) {
				icon_remark = iconFileName;
				icon_remark = icon_remark == null ? "" : new String(
						icon_remark.getBytes("iso8859-1"), "gb2312");
				im.setIcon_remark(icon_remark);
			} else if (iconFileName.equals("icon_id")) {
				icon_id = iconFileName;
			} else if (iconFileName.equals("isupdate")) {
				isupdate = iconFileName;
			} else if (iconFileName.equals("icon_url")) {
				icon_url = iconFileName;
			} else if (iconFileName.equals("icon_leibie")) {
				icon_leibie = iconFileName;
				System.out.println("icon_leibie==" + icon_leibie);
				icon_leibie = icon_leibie == null ? "" : new String(
						icon_leibie.getBytes("iso8859-1"), "gb2312");
			}
			im.setIcon_name(iconFileName);
			im.setIcon_remark(icon_remark);
			im.setIcon_type(filetype);
			im.setIcon_leibie(icon_leibie);
			im.setIcon_url(request.getRealPath(PATH_DIR
					+ File.separator + UPLOAD_DIR)
					+ File.separator+im.getIcon_name());
			
			if ("add_result".equals(operationType)) {
				// 如果是新增后保存
				im.setIcon_url(UPLOAD_DIR + File.separator + im.getIcon_id()
						+ "." + filetype);
			} else if ("update_result".equals(operationType)) {
				// 如果是更新后保存
				im.setIcon_id(icon_id);
				im.setIcon_url(UPLOAD_DIR + File.separator + im.getIcon_id()
						+ "." + filetype);
			}
			InputStream is=new FileInputStream(icon);
			OutputStream os=new FileOutputStream(new File(request.getRealPath(PATH_DIR
								+ File.separator + UPLOAD_DIR)
								+ File.separator + im.getIcon_id()+"."+im.getIcon_type()));
			System.out.println("图标url："+request.getRealPath(PATH_DIR
								+ File.separator + UPLOAD_DIR)
								+ File.separator + im.getIcon_id()+"."+im.getIcon_type());
			byte[] buffer = new byte[1024*8];
			int count=0;
			System.out.println("开始上传");
			while((count=is.read(buffer))>0){
				os.write(buffer,0,count);
			}
			System.out.println("上传结束");
			os.close();
			is.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("上传图标失败");
			return false;
		}		
	}

	/**
	 * 上传图标文件
	 * 
	 * @param request
	 *            im Icon图标对象 operationType 操作类型
	 *            add_result-新增后上传文件；update_result-更新后上传文件
	 * 
	 * @return boolean true-操作成功；false-操作失败
	 */
	public boolean processFileUpload(HttpServletRequest request, DevIcon im,
			String operationType) {
		// System.out.println("===========================");
		// System.out.println(request.getRealPath(PATH_DIR+File.separator+UPLOAD_DIR));
		// System.out.println(request.getRequestURI());
		// System.out.println(request.getPathInfo());
		// System.out.println("===========================");

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存缓冲区，超过后写入临时文件
		factory.setSizeThreshold(10240000);
		// 设置临时文件存储位置
		factory.setRepository(new File(request.getRealPath(this.PATH_DIR
				+ File.separator + this.UPLOAD_DIR + File.separator + "temp")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置单个文件的最大上传值
		upload.setFileSizeMax(102400000);
		// 设置整个request的最大值
		upload.setSizeMax(102400000);

		String icon_name = "";
		String filetype = "";
		String icon_remark = "";
		int itemNum = 0;
		String isupdate = "";
		String icon_id = "";
		String icon_url = "";
		String filename = "";
		String icon_leibie = "";
		try {
			List items = upload.parseRequest(request);
			System.out.println("items size==" + items.size());
			// 从页面获取控件的值
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);

				if (item.isFormField()) {
					if (item.getFieldName().equals("filetype")) {
						filetype = item.getString();
					} else if (item.getFieldName().equals("icon_name")) {
						icon_name = item.getString();
						icon_name = icon_name == null ? "" : new String(
								icon_name.getBytes("iso8859-1"), "gb2312");
						im.setIcon_name(icon_name);
					} else if (item.getFieldName().equals("icon_remark")) {
						icon_remark = item.getString();
						icon_remark = icon_remark == null ? "" : new String(
								icon_remark.getBytes("iso8859-1"), "gb2312");
						im.setIcon_remark(icon_remark);
					} else if (item.getFieldName().equals("icon_id")) {
						icon_id = item.getString();
					} else if (item.getFieldName().equals("isupdate")) {
						isupdate = item.getString();
					} else if (item.getFieldName().equals("icon_url")) {
						icon_url = item.getString();
					} else if (item.getFieldName().equals("icon_leibie")) {
						icon_leibie = item.getString();
						System.out.println("icon_leibie==" + icon_leibie);
						icon_leibie = icon_leibie == null ? "" : new String(
								icon_leibie.getBytes("iso8859-1"), "gb2312");
					}
				}
			}
			im.setIcon_name(icon_name);
			im.setIcon_remark(icon_remark);
			im.setIcon_type(filetype);
			im.setIcon_leibie(icon_leibie);

			if ("add_result".equals(operationType)) {
				// 如果是新增后保存
				im.setIcon_url(UPLOAD_DIR + File.separator + im.getIcon_id()
						+ "." + filetype);
			} else if ("update_result".equals(operationType)) {
				// 如果是更新后保存
				im.setIcon_id(icon_id);
				im.setIcon_url(UPLOAD_DIR + File.separator + im.getIcon_id()
						+ "." + filetype);
				if ("1".equals(isupdate)) {
					// 如果更换了图片，则先删除原有图片后再上传新的图片，以防上传文件过多导致文件空间不够
					delFile(request.getRealPath(this.PATH_DIR + File.separator
							+ icon_url));
				}
			}

			filename = im.getIcon_id() + "." + filetype;
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);

				// 保存文件
				if (!item.isFormField() && item.getName().length() > 0) {
					// 保存文件
					System.out.println("上传中");
					File uploadedFile = new File(request.getRealPath(PATH_DIR
							+ File.separator + UPLOAD_DIR)
							+ File.separator + filename);
					item.write(uploadedFile); // 更新上传文件列表 FileUploadStatus
				}
			}

			return true;
		} catch (Exception e) {
			System.out.println("保存上传文件时发生错误:" + e.getMessage());
			return false;
		}
	}

	/**
	 * 删除上传的图标文件
	 * 
	 * @param filepath
	 *            要删除的图标的绝对路径
	 * 
	 * @return 删除文件操作是否成功
	 */
	public boolean delFile(String filepath) {
		try {
			java.io.File myDelFile = new File(filepath);
			if (myDelFile.exists()) {
				if (myDelFile.isFile()) {
					myDelFile.delete();
					return true;
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 批量删除图标信息 删除表中记录同时删除相应的图片文件
	 * 
	 * @param request
	 *            response iconList 图标列表对象(只有icon_id和icon_type赋值)
	 * 
	 * @return
	 */
	public void delBatchIcon(HttpServletRequest request,
			final List<DevIcon> iconList) throws Exception {
		String filepath = "";
		for (int i = 0; i < iconList.size(); i++) {
			DevIcon icon = (DevIcon) iconList.get(i);
			String icon_id = icon.getIcon_id();
			String icon_type = icon.getIcon_type();
			this.delete(icon_id);
			filepath = request.getRealPath(this.PATH_DIR + File.separator
					+ this.UPLOAD_DIR + File.separator + icon_id + "."
					+ icon_type);
			if (delFile(filepath)) {
				System.out.println("删除文件成功");
			} else {
				System.out.println("删除文件失败");
			}
		}
	}

	@Override
	public String getTableName() {
		return "dev_icon";
	}

}
