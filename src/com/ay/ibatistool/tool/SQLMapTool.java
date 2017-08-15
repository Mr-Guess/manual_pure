package com.ay.ibatistool.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @author lushigai
 * 
 */
public class SQLMapTool {
	private ArrayList<String[]> fields;
	private String xmlName;
	private String className;
	private String packge;
	private Document doc;
	private Element root;
	private String tableName;
	private String src="src/";
	private String replace="true";
	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public ArrayList<String[]> getFields() {
		return fields;
	}

	public void setFields(ArrayList<String[]> fields) {
		this.fields = fields;
	}

	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	public String getPackge() {
		return packge;
	}

	public void setPackge(String packge) {
		this.packge = packge;
	}
	
	public void save() {
		try {
			doc = DocumentHelper.createDocument();
			setRoot();
			//addResultMap();
			String beanName=className.substring(className.lastIndexOf(".")+1);
			addCondition();
			addInsert("insert");
			addDelete("delete");
			addGetById("getById");
			addUpdate("update");
			addFindAll("findAll");
			addSelect("find");
			addCount("count");
			
			
			
			
			createFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(packge.replace(".", "/") + "/" + xmlName
					+ ".xml create faild");
		}
	}
	
	/**
	 * 格式化字段名称
	 * @param filed
	 * @return
	 */
	private String formatFiledString(String filed){
		String [] fileds = filed.split("_");
		String retFiled = "";
		int i = 0;
		for(String f : fileds){
			if(i == 0){
				f = f.substring(0, 1).toLowerCase() + f.substring(1, f.length());
			}else{
				f = f.substring(0, 1).toUpperCase() + f.substring(1, f.length());
			}
			retFiled = retFiled + f;
			i++;
		}
		return retFiled;
	}
	
	private String formatFiledStringLower(String filed){
		String [] fileds = filed.split("_");
		String retFiled = "";
		int i = 0;
		for(String f : fileds){
			if(i == 0){
				f = f.substring(0, f.length()).toLowerCase();
			}else{
				f = f.substring(0, 1).toUpperCase() + f.substring(1, f.length()).toLowerCase();
			}
			retFiled = retFiled + f;
			i++;
		}
		return retFiled;
	}

	private void setRoot() {
		doc.setXMLEncoding("UTF-8");
		doc.addDocType("sqlMap", "-//ibatis.apache.org//DTD SQL Map 2.0//EN",
				"http://ibatis.apache.org/dtd/sql-map-2.dtd");

		doc.addComment("add by iBatisTool "+tableName);
		
		root = doc.addElement("sqlMap");
		root.addAttribute("namespace", xmlName);
	}

	public void addResultMap() {
		Element e = root.addElement("resultMap");
		e.addAttribute("id", "result");
		e.addAttribute("class", className);
		int i = 0;
		for (Iterator<String[]> iterator = fields.iterator(); iterator
				.hasNext();) {
			i++;
			String[] field = (String[]) iterator.next();
			// <result property="id" column="userid" columnIndex="1" />
			Element ce = e.addElement("result");
			ce.addAttribute("property", formatFiledString(field[1]));
			//ce.addAttribute("property", field[1]);//edit by maxy
			ce.addAttribute("column", field[1].toUpperCase());
			//ce.addAttribute("columnIndex", String.valueOf(i));
		}

	}

	public void addSelect(String name) {
		root.addComment(" add method "+name );

		Element e = root.addElement("select");
	//	e.addAttribute("id", tableName + "." + name);
		e.addAttribute("id",name);
		e.addAttribute("resultClass", className);
		e.addAttribute("parameterClass", "java.util.Map");
	//	e.addAttribute("method", name);
		StringBuffer text = new StringBuffer("\r\n \t\tselect ");
		for (Iterator<String[]> iterator = fields.iterator(); iterator
				.hasNext();) {
			String[] field = (String[]) iterator.next();
			String columnName = field[3];
			if(columnName.contains("_")){
				text.append("\r\n\t\t\t\t"  + columnName + " as "+formatFiledString(columnName) +",");
			}else{
				text.append("\r\n\t\t\t\t"  + columnName + ",");
			}
		}
		text.append("_@_");
		text.append("\r\n\t\t\tfrom\r\n\t\t\t" + tableName);
		text.append("\r\n  \t\twhere \r\n \t \t\t status='1' ");

		e.setText(text.toString().replaceAll(",_@_", ""));
		Element el = e.addElement("include");
		el.addAttribute("refid", xmlName+".mapWhereClause");
		
		
	}
	
	public void addFindAll(String name) {
		root.addComment(" add method " +name);

		Element e = root.addElement("select");
	//	e.addAttribute("id", tableName + "." + name);
		e.addAttribute("id",name);
		e.addAttribute("resultClass", className);
		e.addAttribute("parameterClass", className);
	//	e.addAttribute("method", name);
		StringBuffer text = new StringBuffer("\r\n \t\tselect ");
		for (Iterator<String[]> iterator = fields.iterator(); iterator
				.hasNext();) {
			String[] field = (String[]) iterator.next();
			if(field[3].contains("_")){
				text.append("\r\n\t\t\t\t"  + field[3] + " as "+formatFiledString(field[3]) +",");
			}else{
				text.append("\r\n\t\t\t\t"  + field[3] + ",");
			}
		}
		text.append("_@_");
		text.append("\r\n\t\t\tfrom\r\n\t\t\t" + tableName);
		text.append("\r\n  \t\twhere \r\n \t \t\t status='1'");
		e.setText(text.toString().replaceAll(",_@_", ""));
	}
	
	public void addCount(String name) {
		root.addComment(" add method " +name);

		Element e = root.addElement("select");
	//	e.addAttribute("id", tableName + "." + name);
		e.addAttribute("id",name);
		e.addAttribute("resultClass", "java.lang.Integer");
		e.addAttribute("parameterClass", "java.util.Map");
		//e.addAttribute("method", name);
		StringBuffer text = new StringBuffer("\r\n \t\tselect count(*) ");
		text.append("\r\n\t\t\tfrom\r\n\t\t\t" + tableName);
		text.append("\r\n  \t\twhere \r\n \t \t\t status='1' ");
		e.setText(text.toString().replaceAll(",_@_", ""));
		
		Element el = e.addElement("include");
		el.addAttribute("refid", xmlName+".mapWhereClause");
	}
	
	public void addGetById(String name) {
		root.addComment(" add method " +name);

		Element e = root.addElement("select");
	//	e.addAttribute("id", tableName + "." + name);
		e.addAttribute("id",name);
		e.addAttribute("resultClass", className);
		e.addAttribute("parameterClass", "java.lang.String");
	//	e.addAttribute("method", name);
		StringBuffer text = new StringBuffer("\r\n \t\tselect ");
		for (Iterator<String[]> iterator = fields.iterator(); iterator
				.hasNext();) {
			String[] field = (String[]) iterator.next();
			if(field[3].contains("_")){
				text.append("\r\n\t\t\t\t"  + field[3] + " as "+formatFiledString(field[3]) +",");
			}else{
				text.append("\r\n\t\t\t\t"  + field[3] + ",");
			}
		}
		text.append("_@_");
		text.append("\r\n\t\t\tfrom\r\n\t\t\t" + tableName);
		
		e.setText(text.toString().replaceAll(",_@_", ""));
		
		text = new StringBuffer("\r\n  \t\twhere \r\n \t \t\t");
		String[] field = (String[])fields.get(0);
		text.append(field[3]+"=#"+formatFiledString(field[1])+"#");
		e.addText(text.toString());
		
	}

	public void addInsert(String name) {
		root.addComment(" add method "+name );
		Element e = root.addElement("insert");
		//	e.addAttribute("id", tableName + "." + name);
		e.addAttribute("id",name);
		e.addAttribute("parameterClass", className);
	//	e.addAttribute("method", name);
		StringBuffer text = new StringBuffer("\r\n  \t\tinsert into  "
				+ tableName + "(");
		for (Iterator<String[]> iterator = fields.iterator(); iterator
				.hasNext();) {
			String[] field = (String[]) iterator.next();
			text.append("\r\n\t\t\t\t" + field[1].toUpperCase() + ",");
		}
		text.append("_@_");
		text.append(") values(");
		for (Iterator<String[]> iterator = fields.iterator(); iterator
				.hasNext();) {
			String[] field = (String[]) iterator.next();
			text.append("\r\n\t\t\t\t" + "#" + formatFiledString(field[1]) + "#,");
		}
		text.append("_@_)");
		e.setText(text.toString().replaceAll(",_@_", ""));
	}
	public void addUpdate(String name) {
		root.addComment(" add method "+name );
		Element e = root.addElement("update");
		//	e.addAttribute("id", tableName + "." + name);
		e.addAttribute("id",name);
		e.addAttribute("parameterClass", className);
//		e.addAttribute("method", name);
		StringBuffer text = new StringBuffer("\r\n\t\tupdate   "
				+ tableName +" ");
		e.setText(text.toString().replaceAll(",_@_", ""));
		
		
		Element el = e.addElement("dynamic");
		el.addAttribute("prepend", "set");
		for (Iterator<String[]> iterator = fields.iterator(); iterator
				.hasNext();) {
			String[] field = (String[]) iterator.next();
			Element ele = el.addElement("isNotEmpty");
			ele.addAttribute("prepend", ",");
			ele.addAttribute("property",formatFiledString(field[1]));
			ele.setText(" "+field[3]+"=#"+formatFiledString(field[1])+"#");
			
		}
		
//		el = e.addElement("dynamic");
//		el.addAttribute("prepend", "where");
//		for (Iterator<String[]> iterator = fields.iterator(); iterator
//				.hasNext();) {
//			String[] field = (String[]) iterator.next();
//			Element ele = el.addElement("isNotNull");
//			ele.addAttribute("prepend", "and");
//			ele.addAttribute("property",field[1]);
//			ele.setText(field[3]+"=#"+field[1]+"#");
//			
//		}
		text = new StringBuffer("\r\n  \t\twhere \r\n \t \t\t");
		String[] field = (String[])fields.get(0);
		text.append(field[3]+"=#"+formatFiledString(field[1])+"#");
		e.addText(text.toString());
	}
	//Condition
	
	public void addCondition() {
		root.addComment(" add Condition "+tableName );
		Element e = root.addElement("sql");
		e.addAttribute("id", "mapWhereClause");
		Element el = e.addElement("isParameterPresent");
		//el.addAttribute("prepend", "where");
		for (Iterator<String[]> iterator = fields.iterator(); iterator
				.hasNext();) {
			String[] field = (String[]) iterator.next();
			Element ele = el.addElement("isNotEmpty");
			ele.addAttribute("prepend", "and");
			ele.addAttribute("property",formatFiledString(field[1]));
			ele.setText(" "+field[3]+"=#"+formatFiledString(field[1])+"#");
			
		}
	}
	public void addDelete(String name) {
		root.addComment(" add method "+name );
		Element e = root.addElement("delete");
//		e.addAttribute("method", name);
		//	e.addAttribute("id", tableName + "." + name);
		e.addAttribute("id",name);
		e.addAttribute("parameterClass", "java.lang.String");
		StringBuffer text = new StringBuffer("\r\n\t\tupdate "
				+ tableName + " set status='0' ");
		e.setText(text.toString().replaceAll(",_@_", ""));
		
		text = new StringBuffer("\r\n  \t\twhere \r\n \t \t\t");
		String[] field = (String[])fields.get(0);
		text.append(field[3]+"=#"+formatFiledString(field[1])+"#");
		e.addText(text.toString());
		
	}
	
	private void createFile() throws IOException {
		String fileName = src+packge.replace(".", "/") + "/" + xmlName + "Mapper.xml";
		File pFile = new File(src+packge.replace(".", "/"));
		if (!pFile.isDirectory()) {
			pFile.mkdirs();
		}
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();

		} else {
			if(replace.equals("true"))file.delete();
			else throw new RuntimeException(fileName+" has allready being!");
		}

		OutputFormat format = new OutputFormat("\t", true);

		XMLWriter writer = new XMLWriter(new FileWriter(new File(fileName)),
				format);
		writer.write(doc);
		writer.close();
		System.out.println("create begin: "+fileName);

	}

	public static void main(String[] args) {
		SQLMapTool tool = new SQLMapTool();
		tool.setPackge("com.test.xml");
		tool.setXmlName("t_uer");
		tool.save();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}
