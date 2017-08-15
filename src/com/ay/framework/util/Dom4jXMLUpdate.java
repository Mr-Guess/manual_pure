package com.ay.framework.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * sql server条件查询语句 转换成myslq和oracle字符串拼接 懒人模式
 * @author Thor
 *
 */
@SuppressWarnings("all")
public class Dom4jXMLUpdate {
	/**
	 * 读取文件 修改
	 * @param filePath 路径
	 * @throws DocumentException
	 */
	public static void saxXML(String filePath) throws DocumentException{
    	SAXReader reader=new SAXReader();
    	Document   document = reader.read(new File(filePath));
    	 Element root = document.getRootElement();
    	 listOracle(root); 
    	 /** 将document中的内容写入文件中 */  
         XMLWriter writer;
         /* String newFilePath=filePath.replace("-mySQL.xml", "-oracle.xml");
         if(newFilePath.indexOf("-mySql")!=-1){
        	 newFilePath=filePath.replace("-mySql.xml", "-oracle.xml");
         }
         System.out.println("新名字:"+newFilePath);*/
		try {
			writer = new XMLWriter(new FileWriter(new File(filePath)));
			 writer.write(document);  
	         writer.close();  
	         System.out.println("转换成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	/**
	 * 读取所有Mapper的路径
	 * @param filePath
	 * @return
	 * @throws DocumentException
	 */
	public static List<String> saxXmlPath(String filePath) throws DocumentException{
		SAXReader reader=new SAXReader();
    	Document   document = reader.read(new File(filePath));
    	Element root = document.getRootElement();
    	List<String> xmlPaths=new ArrayList<String>();
    	foreach(root,xmlPaths);
    	return xmlPaths;
	}
	/**
	 * 递归读取
	 * @param node
	 * @param xmlPaths
	 */
	public static void foreach(Element node,List<String> xmlPaths){
		if(node==null)return;
		Iterator<Element> nodes = node.elementIterator();  
		for (Iterator it = nodes; it.hasNext();) {
			Element elm = (Element) it.next(); 
			String param=elm.attributeValue("resource");
			if(param!=null){
				xmlPaths.add("F:\\地市级项目2.0\\src\\".concat(param.replace("/","\\")));
			}
			foreach(elm,xmlPaths);
		}
		
	}
	
    /**
     * 更改
     * @param node sql 修该成mysql
     */
	public static void listElement(Element node){
    	if(node==null)return;
    	Iterator<Element> nodes = node.elementIterator();  
    	for (Iterator it = nodes; it.hasNext();) {    
    			Element elm = (Element) it.next(); 
    			String param=elm.attributeValue("property");
    			if(param!=null){
    				String prttem=elm.getText();
    				if(prttem.indexOf("%")!=-1&&prttem.indexOf("concat")==-1){
    					int begin=prttem.indexOf("%")-1;
    					int end=prttem.lastIndexOf("%")+2;
    					String newPrttem=prttem.substring(0,begin);
    					String newPrttem1=prttem.substring(end,prttem.length());
    					String tiaojian="concat('%',#"+param+"#,'%')";
    					String sumTiaojiao=newPrttem.concat(tiaojian).concat(newPrttem1);
    					elm.setText(sumTiaojiao);
    				}
    			}
    			listElement(elm);
    	}
    }
	/**
	 * 修改成Oracle
	 * @param node
	 */
	public static void listOracle(Element node){
		if(node==null)return;
    	Iterator<Element> nodes = node.elementIterator();  
    	for (Iterator it = nodes; it.hasNext();) {    
    			Element elm = (Element) it.next(); 
    			String param=elm.attributeValue("property");
    			if(param!=null){
    				String prttem=elm.getText();
    				if(prttem.indexOf("CONCAT")!=-1||prttem.indexOf("concat")!=-1){
    					int begin=prttem.indexOf("CONCAT")-1;
    					if(begin==-2){
    						begin=prttem.indexOf("concat")-1;
    					}
    					int end=prttem.lastIndexOf("'%'")-2;
    					String newPrttem=prttem.substring(0,begin);
    					String newPrttem1=prttem.substring(end,prttem.length()-2);
    					String tiaojian=" '%'||#"+param+"#||'%'";
    					String sumTiaojiao=newPrttem.concat(tiaojian);
    					System.out.println(sumTiaojiao);
    					elm.setText(sumTiaojiao);
    				}
    			}
    			listOracle(elm);
    	}
	}
        /** 
         * 格式化XML文档,并解决中文问题 
         *  
         * @param filename 
         * @return 
         */  
        public static  int  formatXMLFile(String filename) {  
            int returnValue = 0;  
            try {  
                SAXReader saxReader = new SAXReader();  
                Document document = saxReader.read(new File(filename));  
                XMLWriter writer = null;  
                /** 格式化输出,类型IE浏览一样 */  
                OutputFormat format = OutputFormat.createPrettyPrint();  
                /** 指定XML编码 */  
                format.setEncoding("GBK");  
                writer = new XMLWriter(new FileWriter(new File(filename)), format);  
                writer.write(document);  
                writer.close();  
                /** 执行成功,需返回1 */  
                returnValue = 1;  
      
            } catch (Exception ex) {  
                ex.printStackTrace();  
            }  
            return returnValue;  
      
        }  
      
      
    public static void main(String[] args) {
		try {
			String xmlPath= "E:\\xml\\sqlMapConfig-oracle.xml";  
			List<String> xmlPaths=Dom4jXMLUpdate.saxXmlPath(xmlPath);
			int leng=xmlPaths.size();
			System.out.println("一共"+leng+"个待转换");
			for (String string : xmlPaths) {
				Dom4jXMLUpdate.saxXML(string);
				System.out.println("剩余"+(leng--)+"个");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
