package com.ay.ibatistool.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ay.ibatistool.tool.template.ActionTemplate;
import com.ay.ibatistool.tool.template.DaoTemplate;
import com.ay.ibatistool.tool.template.JspTemplate01;
import com.ay.ibatistool.tool.template.ServiceTemplate;


public class GenerateFileTool {
	public static void main(String[] args) {
		String packageName="com.ay.jfds.dev";
		String name="Test";
		//generate(packageName, name);
	}
	/**
	 * 生成dao、service、action层的代码java文件
	 * @param packageName 包名:com.ay.jfds.dev
	 * @param name 实体名：首字母大写
	 * @throws IOException
	 * @return void
	 */
	public static void generate(String packageName,String name, String tableName) throws IOException{
		List<String> list = new ArrayList<String>();
		list.add(packageName);
		list.add(name);
		String lowerName = name.substring(0, 1).toLowerCase() + name.substring(1);
		list.add(lowerName);
		list.add(tableName);
		String fileStringDao = new DaoTemplate().generate(list);
		writeJavaFile(fileStringDao, packageName+".dao", name+"Dao");
		String fileStringService = new ServiceTemplate().generate(list);
		writeJavaFile(fileStringService, packageName+".service", name+"Service");
		String fileStringAction = new ActionTemplate().generate(list);
		writeJavaFile(fileStringAction, packageName+".action", name+"Action");	
	}
	/**
	 * 写java文件
	 * @param fileString
	 * @param packageName
	 * @param name
	 * @throws IOException
	 * @return void
	 */
	private static void writeJavaFile(String fileString,String packageName,String name) throws IOException{
		String fileName = "src/"+packageName.replace(".", "/") + "/" + name + ".java";
		writeFile(fileString, fileName);
	}
	/**
	 * 写文件
	 * @param fileString
	 * @param fileName
	 * @return void
	 */
	private static void writeFile(String fileString,String fileName){
		System.out.println(fileName);
		try {
			File file = new File(fileName);
			File pfile=file.getParentFile();
			if(!pfile.isDirectory()){
				pfile.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(writer);
				bw.write(fileString);
				bw.flush();
				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 生成jsp文件
	 * @param packageName	包名:com.ay.jfds.dev
	 * @param name	实体名：首字母大写
	 * @param l 从数据库取出的list
	 * @param jspFilePath 从WebRoot下开始的路径：jsp/dev/test/test.jsp
	 * @throws IOException
	 * @return void
	 */
	public static void generateJSP(String packageName, String name,
			ArrayList<String[]> al,String jspFilePath) throws IOException {
		List<String> list = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();
		list.add(packageName);
		list.add(name);
		String lowerName = name.substring(0, 1).toLowerCase() + name.substring(1);
		list.add(lowerName);
		Object[] o=new Object[2];
		o[0]=list;
		//得到需要的属性列表
		for (Iterator<String[]> iterator = al.iterator(); iterator.hasNext();) {
			String[] field = (String[]) iterator.next();
			String fieldName=field[1].toLowerCase();
			if (!fieldName.equals("id") && !fieldName.equals("status")
					&& !fieldName.equals("created")
					&& !fieldName.equals("create_time")
					&& !fieldName.equals("updated")
					&& !fieldName.equals("update_time")) {
				fieldList.add(formatFiledString(fieldName));
			}
		}
		o[1]=fieldList;
		String fileStringJsp = new JspTemplate01().generate(o);
		writeFile(fileStringJsp, System.getProperty("user.dir")+"/WebRoot/"+jspFilePath);
		
	}
	/**
	 * 格式化字段名称
	 * @param filed
	 * @return
	 */
	private static String formatFiledString(String filed){
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
}
