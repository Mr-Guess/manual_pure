package com.ay.jfds.dev.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class TemplateAction extends ActionSupport{

	private File templateFile;
	private String templateFileFileName;
	
	/**
	 * 上传文件
	 * @return
	 */
	public String uploadFile(){
		HttpServletRequest req = ServletActionContext.getRequest();		
		String savePath = "upload";
		
		try {
			InputStream is=new FileInputStream(templateFile);
			File f = new File(savePath);
			if(!f.exists()){
				f.mkdirs();
			}
			
			OutputStream os=new FileOutputStream(f+File.separator+templateFileFileName);
			
			byte[] buffer = new byte[1024*8];
			int count=0;
			while((count=is.read(buffer))>0){
				os.write(buffer,0,count);
			}
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "upload";
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
