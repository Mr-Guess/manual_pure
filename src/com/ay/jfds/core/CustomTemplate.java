package com.ay.jfds.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @Description 
 * @date 2012-11-16
 * @author WangXin
 */
public class CustomTemplate {
	private static Logger logger = LoggerFactory.getLogger(CustomTemplate.class);
	public static void main(String[] args) {
		File f = new File("WebRoot/freemarker/test1.ftl");
		
	}
	/**
	 * 通过上传的模板文件生成ftl文件
	 * @param uploadFile	上传的File文件
	 * @param templateFilePath	生成的ftl文件全路径
	 * @param menuId	加shiro标签需要
	 * @return void
	 */
	public static void generateTemplateFile(File uploadFile,String templateFilePath,int menuId){
		try{
			BufferedInputStream bis = 
			new BufferedInputStream(new FileInputStream(uploadFile));
			/*byte[] buff = new byte[(int)uploadFile.length()];
			bis.read(buff);*/
			BufferedReader reader = new BufferedReader (new InputStreamReader(bis,"utf-8")); 
			FileOutputStream fos = new FileOutputStream(templateFilePath);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos,"utf-8"));
			//输出shiro标签引用
			fos.write(("<#assign shiro=JspTaglibs[\"http://shiro.apache.org/tags\"]>\n").getBytes());
			
			while(reader.ready()){
				String line = reader.readLine();
				logger.debug(line);
				line = replaceButton(line,String.valueOf(menuId));
				writer.write((line+"\n"));
			}
			/*
			String[] lines = (new String(buff)).split("\n");
			for(String line : lines){
				//logger.info(line);
				line = replaceButton(line,String.valueOf(menuId));
				fos.write((line+"\n").getBytes());
			}
			*/
			//fos.flush();
			writer.flush();
			writer.close();
			fos.close();
			reader.close();
			bis.close();
			logger.info(templateFilePath+"模板生成成功");
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	private static String replaceButton(String content,String menuId){
		return content.replaceAll("<button(.*) type=\"(.*)\" (.*)</button>", "<@shiro.hasPermission name=\""+menuId+":$2\"><button$1 type=\"$2\" $3</button></@shiro.hasPermission>");
	}
}

