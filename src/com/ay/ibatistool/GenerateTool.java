package com.ay.ibatistool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ay.ibatistool.db.DBUtil;
import com.ay.ibatistool.tool.AlterXmlTool;
import com.ay.ibatistool.tool.BeanTool;
import com.ay.ibatistool.tool.DaoTool;
import com.ay.ibatistool.tool.GenerateFileTool;
import com.ay.ibatistool.tool.SQLMapTool;
import com.ay.ibatistool.tool.XMLTool;



public class GenerateTool {
	static {
		XMLTool.load("../test.xml");
	}

	public static void main(String[] args) {
		GenerateTool.create();
	}

	@SuppressWarnings("unchecked")
	public static void create() {

		String driverClassName = XMLTool.getElementAttribute("/config/jdbc",
				"jdbc.driverClassName");
		String url = XMLTool.getElementAttribute("/config/jdbc", "jdbc.url");
		String username = XMLTool.getElementAttribute("/config/jdbc",
				"jdbc.username");
		String password = XMLTool.getElementAttribute("/config/jdbc",
				"jdbc.password");
		DBUtil.getConnection(driverClassName, url, username, password);

		// String tableName="T_RES_ALBUMINFO";
		List<String> tableList = XMLTool
				.getElementTexts("/config/tables/table");
		if (tableList.size() == 0) {
			tableList = DBUtil.getAllTable();
		}
		String packageName = XMLTool.getElementText("/config/set/package");
		String javabackage = packageName+".pojo";
		String src = XMLTool.getElementText("/config/set/src");
		String replace = XMLTool.getElementText("/config/set/replace");
		String createSequence = XMLTool.getElementText("/config/set/createSequence");
		
		String strutsXmlFile = XMLTool.getElementText("/config/set/strutsXmlFile");
		String sqlMapperXmlFile = XMLTool.getElementText("/config/set/sqlMapperXmlFile");
		String springXmlFile = XMLTool.getElementText("/config/set/springXmlFile");
		String jspFile = XMLTool.getElementText("/config/set/jspFile");
		//@SuppressWarnings("unused")
		//String daoPkg = XMLTool.getElementText("/config/set/daopackage");
		//@SuppressWarnings("unused")
		//String implPkg = XMLTool.getElementText("/config/set/implpackage");
		String xmlbackage = packageName+".sqlMapper";
		for (Iterator iterator = tableList.iterator(); iterator.hasNext();) {
			String tableName = (String) iterator.next();
			//String desc = DBUtil.getTableDesc(username, tableName);
			LinkedList<String[]> linkedList = DBUtil.getFeild(tableName);
			String sequenceName="S_"+tableName;
			String name = getName(tableName);
			String beanName = name;
			String xmlName = name;
			String className = javabackage + "." + beanName;
			String daoName = name + "Dao";
			String implName = name + "Impl";
			String xmlPath = "./" + src + xmlbackage.replace(".", "/") + "/"
					+ xmlName + ".xml";
			HashSet<String> set = new HashSet<String>();

			ArrayList<String[]> list = new ArrayList<String[]>();
			for (Iterator<String[]> it = linkedList.iterator(); it.hasNext();) {
				String[] strings = (String[]) it.next();
				set.add(strings[0]);
				strings[0] = strings[0]
						.substring(strings[0].lastIndexOf(".") + 1);
				list.add(strings);
			}
			
			
			try {
				if(createSequence.equals("true")){
					DBUtil.createSequence(sequenceName);
				}
				//生成bean文件
				GenerateTool.createBean(replace, src, beanName, javabackage, "", list, set);
				//生成XML文件
				GenerateTool.createXml(replace, src, xmlName, tableName, className, xmlbackage, list);
				//生成dao、service、action文件
				GenerateFileTool.generate(packageName, name, tableName);
				//修改struts、sqlMapper、spring配置文件
				AlterXmlTool.alterSSIXml(strutsXmlFile, sqlMapperXmlFile, springXmlFile, packageName, name);
				//生成jsp文件
				GenerateFileTool.generateJSP(packageName, name, list,jspFile);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}

		DBUtil.closeConnection();
	}

	public static void createBean(String replace, String src, String beanname,
			String javapack, String desc, ArrayList<String[]> list,
			HashSet<String> set) {
		BeanTool tool = new BeanTool();
		tool.setReplace(replace);
		tool.setBeanName(beanname);
		tool.setBeanPkg(javapack);
		tool.setImportClaess(set);
		tool.setDesc(desc);
		tool.setFields(list);
		tool.setSrc(src);
		tool.save();
		// System.out.println(name);
	}

	private static String getName(String name) {
		String[] names = name.toLowerCase().split("_");
		name = "";

		for (int i = 0; i < names.length; i++) {
			name = name + names[i].substring(0, 1).toUpperCase()
					+ names[i].substring(1);
		}
		return name;
	}
	public static void createSequence(String sequenceName){
		System.out.println("create begin: "+sequenceName);
		DBUtil.createSequence(sequenceName);
	}
	public static void createXml(String replace, String src, String xmlName,
			String tableName, String className, String xmlpack,
			ArrayList<String[]> list) {
		SQLMapTool tool = new SQLMapTool();
		tool.setPackge(xmlpack);
		tool.setXmlName(xmlName);
		tool.setTableName(tableName);
		tool.setFields(list);
		tool.setClassName(className);
		tool.setSrc(src);
		tool.setReplace(replace);
		tool.save();
	}
	
}
