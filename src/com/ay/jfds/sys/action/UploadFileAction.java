package com.ay.jfds.sys.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.jfds.sys.pojo.UploadFile;
import com.ay.jfds.sys.service.UploadFileService;
import com.opensymphony.xwork2.ActionContext;

/**
 * @description 上传下载文件管理
 * @author wgw
 * @date 2013-3-8
 */
@SuppressWarnings("serial")
public class UploadFileAction extends BaseAction{
	private static Logger logger = LoggerFactory
			.getLogger(UploadFileAction.class);
	/**
	 * 上传文件
	 */
	private File file;
	
	/**
	 * 上传文件名
	 */
	private String fileFileName;
	
	/**
	 * 上传文件类型集合
	 */
	private String fileContentType;
	
	/**
	 * 上传下载的返回信息
	 */
	private InputStream advice;
	
	/**
	 * 文件关联id
	 */
	private String relationId;
	
	private UploadFileService uploadFileService;
	
	/**
	 * 下载时的文件名
	 */
	private String downloadFileName;
	
	/**
	 * 文件对象
	 */
	private UploadFile uploadFile;
	
	/**
	 * 下载缓存文件对象
	 */
	private InputStream downloadFile;
	
	/**
	 * 文件id
	 */
	private String fileId;
	
	
	/**
	 * 查出当前关联下的所有文件
	 */
	public void fileList(){
		List<UploadFile> list = this.uploadFileService.getFileListByRelationId(relationId);
		String json = new JsonMapper().toJson(list);
        Struts2Utils.renderText(json);
	}
	
	
	/**
	 * 上传文件
	 * @return
	 */
	public String uploadFile(){
		if(ActionContext.getContext().getSession().get("SizeLimitExceededException") != null){
			try {
				advice = new BufferedInputStream(new ByteArrayInputStream("文件大小不能超过100M!".getBytes("utf-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return "success";
		}
		ByteArrayInputStream byteArray = null;
		try {
			byteArray = new ByteArrayInputStream(("上传文件"+fileFileName+"成功").getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		advice = new BufferedInputStream(byteArray);
		WriteFile();
		return "success";
	}
	
	/**
	 * 删除文件
	 */
	public String removeFile(){
		UploadFile uf = this.uploadFileService.getById(fileId);
		//获得文件的路径
		String filePath = uf.getRealPath();
		File file = new File(filePath);
		//如果是文件则删除
		if(file.isFile()&&file.exists()){
			try {
				if(file.delete()){
					logger.info(file+"删除成功！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(file+"-！");
			}
		}else{
			try {
				advice = new BufferedInputStream(new ByteArrayInputStream("文件已经不存在！删除记录。".getBytes("utf-8")));
				//文件不存在则删除数据库中的数据
				this.uploadFileService.delete(uf.getId());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return SUCCESS;
		}
		if(this.uploadFileService.delete(uf.getId())){
			try {
				advice = new BufferedInputStream(new ByteArrayInputStream("删除文件成功！".getBytes("utf-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return SUCCESS;
		}
		try {
			advice = new BufferedInputStream(new ByteArrayInputStream("未知错误！".getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	/**
	 * 向磁盘写入文件
	 */
	private void WriteFile() {
		//获取存储上传文件的目录
		File filePath = new File(getUploadPath());
		if(!filePath.exists()){
			filePath.mkdir();
		}
		OutputStream os = null;
		InputStream is = null;
		//设置上传文件的地址
		String realPath = getRealPath(filePath);
		try {
			is = new FileInputStream(file);
			os = new FileOutputStream(realPath);
			byte[] buffer = new byte[1024*8]; 
			int length = 0;
			while((length = is.read(buffer)) > 0){
				os.write(buffer, 0, length);
			}
			UploadFile uf = new UploadFile();
			uf.setRealPath(realPath);
			uf.setFileName(fileFileName);
			uf.setRelationId(this.relationId);
			this.uploadFileService.insert(uf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 设置上传文件的磁盘地址
	 * @param filePath
	 * @return
	 */
	private String getRealPath(File filePath) {
		int pointIndex = fileFileName.lastIndexOf('.');
		String fileType = null;
		if(pointIndex !=-1){
			fileType = fileFileName.substring(pointIndex);
		}
		String proxyFileName = System.currentTimeMillis() + (relationId==null?"":relationId) + fileType;
		String realPath = filePath.toString() + File.separator +proxyFileName;
		return realPath;
	}


	/**
	 * 获得文件上传路径
	 * @return
	 */
	private String getUploadPath(){
		Properties prop = new Properties();   
		Resource resource = new ClassPathResource("/init.properties");
		String uploadPath = "";
		try {
			resource.getFile();
			InputStream in = new FileInputStream(resource.getFile());
			prop.load(in);
			uploadPath = prop.getProperty("uploadFilePath");
		} catch (IOException e1) {
			e1.printStackTrace();
			uploadPath = "upload";
		}
		return uploadPath;
	}
	
	
	
	/**
	 * 下载文件
	 * @return
	 */
	public String downloadFile(){
		UploadFile uf = this.uploadFileService.getById(fileId);
		this.downloadFileName = uf.getFileName();
		File downloadPath = new File(uf.getRealPath());
		try {
			downloadFile = new FileInputStream(downloadPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			try {
				advice = new BufferedInputStream(new ByteArrayInputStream("文件下载失败：文件不存在".getBytes("utf-8")));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			return ERROR;
		}
		return SUCCESS;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public InputStream getAdvice() {
		return advice;
	}

	public void setAdvice(InputStream advice) {
		this.advice = advice;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public UploadFileService getUploadFileService() {
		return uploadFileService;
	}

	public void setUploadFileService(UploadFileService uploadFileService) {
		this.uploadFileService = uploadFileService;
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public UploadFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public InputStream getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(InputStream downloadFile) {
		this.downloadFile = downloadFile;
	}


	public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	
	
}
