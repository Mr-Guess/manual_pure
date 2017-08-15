package com.ay.ibatistool.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * @Description 
 * @date 2012-11-01
 * @author WangXin
 */



public class AlterXmlTool {
	

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		String packageName="com.ay.jfds.dev";
		String name="Test";
		//alterStrutsXml("resource/struts/test.xml",packageName, name);
		//alterSqlMapperXml("resource/test.xml",packageName, name);
		alterSpringXml("resource/spring/test.xml",packageName, name);		
	}
	
	/**
	 * 修改struts、spring、sqlMapper配置文件
	 * @param strutsXmlFile
	 * @param sqlMapperXmlFile
	 * @param springXmlFile
	 * @param packageName
	 * @param name
	 * @return void
	 */
	public static void alterSSIXml(String strutsXmlFile,String sqlMapperXmlFile,String springXmlFile,String packageName,String name){
		alterStrutsXml(strutsXmlFile,packageName, name);
		alterSqlMapperXml(sqlMapperXmlFile,packageName, name);
		alterSpringXml(springXmlFile,packageName, name);
	}
	
	/**
	 * 修改struts配置文件
	 * @param fileName
	 * @param packageName
	 * @param name
	 * @return void
	 */
	private static void alterStrutsXml(String fileName,String packageName,String name){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		String nameStr=name.substring(0,1).toLowerCase()+name.substring(1);
		try {
			DocumentBuilder db=factory.newDocumentBuilder();
			Document xmldoc=db.parse(new File(fileName));
			Element root=xmldoc.getDocumentElement();
			if(!root.getNodeName().equalsIgnoreCase("struts"))
				throw new IOException("文件非标准struts配置文件");
			NodeList nodeList=root.getElementsByTagName("package");
			for(int i=0;i<nodeList.getLength();i++){
				Element e=(Element) nodeList.item(i);
				if(e.getAttribute("name").equals(nameStr)){
					//throw new SAXException("struts配置文件已经存在"+nameStr);
					System.out.println("struts配置文件已经存在"+nameStr);
					return;
				}
			}
			Element packageElement =xmldoc.createElement("package");
			packageElement.setAttribute("name", nameStr);
			packageElement.setAttribute("extends", "struts-default");
			packageElement.setAttribute("namespace", "/"+nameStr);
			Element actionElement =xmldoc.createElement("action");
			actionElement.setAttribute("name", nameStr+"Action");
			actionElement.setAttribute("class", packageName+".action."+name+"Action");
			packageElement.appendChild(actionElement);
			root.appendChild(packageElement);
			saveXml(fileName,xmldoc);
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改Spring 配置文件
	 * @param fileName
	 * @param packageName
	 * @param name
	 * @return void
	 */
	private static void alterSpringXml(String fileName,String packageName,String name){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		String nameStr=name.substring(0,1).toLowerCase()+name.substring(1);
		try {
			DocumentBuilder db=factory.newDocumentBuilder();
			Document xmldoc=db.parse(new File(fileName));
			Element root=xmldoc.getDocumentElement();
			if(!root.getNodeName().equalsIgnoreCase("beans"))
				throw new IOException("文件非标准spring配置文件");
			NodeList nodeList=root.getElementsByTagName("bean");
			for(int i=0;i<nodeList.getLength();i++){
				Element e=(Element) nodeList.item(i);
				if(e.getAttribute("id").equals(nameStr+"Dao")){
					//throw new SAXException("spring配置文件已经存在"+nameStr+"Dao");
					System.out.println("spring配置文件已经存在"+nameStr);
					return;
				}
			}
			Element daoElement =xmldoc.createElement("bean");
			daoElement.setAttribute("id", nameStr+"Dao");
			daoElement.setAttribute("class", packageName+".dao."+name+"Dao");
			daoElement.setAttribute("parent", "baseDao");
			Element serviceElement =xmldoc.createElement("bean");
			serviceElement.setAttribute("id", nameStr+"Service");
			serviceElement.setAttribute("class", packageName+".service."+name+"Service");
			Element childElement =xmldoc.createElement("property");
			childElement.setAttribute("name", "dao");
			childElement.setAttribute("ref", nameStr+"Dao");
			serviceElement.appendChild(childElement);
			root.appendChild(daoElement);
			root.appendChild(serviceElement);
			saveXml(fileName,xmldoc);
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改sqlMapper配置文件
	 * @param fileName
	 * @param packageName
	 * @param name
	 * @return void
	 */
	private static void alterSqlMapperXml(String fileName,String packageName,String name){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db=factory.newDocumentBuilder();
			Document xmldoc=db.parse(new File(fileName));
			Element root=xmldoc.getDocumentElement();
			if(!root.getNodeName().equalsIgnoreCase("sqlMapConfig"))
				throw new IOException("文件非标准sqlMap配置文件");
			String sourceStr=packageName.replace(".", "/") + "/sqlMapper/" + name + "Mapper.xml";
			NodeList nodeList=root.getElementsByTagName("sqlMap");
			for(int i=0;i<nodeList.getLength();i++){
				Element e=(Element) nodeList.item(i);
				if(e.getAttribute("resource").equals(sourceStr)){
					//throw new SAXException("sqlMapper配置文件已经存在"+sourceStr);
					System.out.println("sqlMapper配置文件已经存在"+sourceStr);
					return;
				}
			}
			Element sqlMapElement =xmldoc.createElement("sqlMap");
			sqlMapElement.setAttribute("resource", sourceStr);
			root.appendChild(sqlMapElement);
			saveXml(fileName,xmldoc);
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将Document输出到文件
	 * @param fileName
	 * @param doc
	 * @return void
	 */
	private static void saveXml(String fileName, Document doc) {
        TransformerFactory transFactory=TransformerFactory.newInstance();
        try {
        	doc.setXmlStandalone(true);
            Transformer transformer = transFactory.newTransformer();
            if(doc.getDoctype()!=null){
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.DOCTYPE_PUBLIC, doc.getDoctype().getPublicId());     
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.DOCTYPE_SYSTEM, doc.getDoctype().getSystemId()); 
            }
            transformer.setOutputProperty("indent", "yes");

            DOMSource source=new DOMSource();
            source.setNode(doc);
            StreamResult result=new StreamResult();
            result.setOutputStream(new FileOutputStream(fileName));
            
            transformer.transform(source, result);
            System.out.println("已修改："+fileName);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }   
    }


	
}
