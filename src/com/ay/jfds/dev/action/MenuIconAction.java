package com.ay.jfds.dev.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.ay.framework.core.dao.pagination.DialectFactory;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.data.DataUtils;
import com.ay.framework.util.OracleLob;
import com.ay.jfds.create.tools.Property;
import com.ay.jfds.icon.pojo.SysIcon;
import com.ay.jfds.icon.service.SysIconService;
import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class MenuIconAction extends ActionSupport{
	private String menuId;
	private ByteArrayInputStream inputStream;	//按钮输出图标
	
	public String show() throws Exception{
		   HttpServletResponse response=null;
		   ServletOutputStream out = null;
		   InputStream in = null;
		   byte[] img=DataUtils.getIcon(menuId);
		   //SysIcon icon=SpringContextHolder.getBean(SysIconService.class).getByIconNo(menuId);
		   //if(icon==null)return null;
		  // img=icon.getIcon();
		   if(img==null)return null;
		   try {
		        response=ServletActionContext.getResponse();
		        response.setContentType("text/html;charset=UTF-8");
		        //二进制输出流
		        response.setContentType( "multipart/form-data" );          
		        //得到输出流
		        out = response.getOutputStream();          
		        //得到Blob类实例，使用.getBinaryStream()得到输入流。
		        if(img!=null){
			        //从输入流读取数据到输出流
			        out.write( img);
			        //强制刷新输出流
			        out.flush();
		        }
		   } catch ( IOException e ) {
		        e.printStackTrace();
		   } catch ( Exception e ) {
		        e.printStackTrace();
		   } finally {
		        if ( in != null ) {
		             try {
		                  in.close();
		             } catch ( IOException e ) {
		                  e.printStackTrace();
		             }
		        }
		        if ( out != null )  {
		             out.close();
		        }
		   }
		   return null; 
	}
	/**
	 * 获得文件编码格式
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	 public static String codeString(byte[] fileName) throws Exception{
	        BufferedInputStream bin = new BufferedInputStream(
	        new FileInputStream(OracleLob.byte2File(fileName, "E:\\images", "ss")));
	        int p = (bin.read() << 8) + bin.read();
	        String code = null;
	        switch (p) {
	            case 0xefbb:
	                code = "UTF-8";
	                break;
	            case 0xfffe:
	                code = "Unicode";
	                break;
	            case 0xfeff:
	                code = "UTF-16BE";
	                break;
	            default:
	                code = "GBK";
	        }
	        System.out.println(p);
	         
	        return code;
	    }
	 
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
	public void setInputStream(byte[] by,int begin,int len) {
		this.inputStream = new ByteArrayInputStream(by, begin, len);
	}

}
