package com.ay.ibatistool.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.ay.framework.core.pojo.BasePojo;
/**
 * 
 * @author Administrator
 *
 */
public class BeanTool {
	private Set<String> importClaess;
	private ArrayList<String[]> fields;
	private String beanName;
	private String beanPkg;
	private String desc;
	private String src="src/";
	private OutputStreamWriter  bw;
	private String replace="true";
	
	public String getReplace() {
		return replace;
	}
	public void setReplace(String replace) {
		this.replace = replace;
	}
	public Set<String> getImportClaess() {
		return importClaess;
	}
	public void setImportClaess(Set<String> importClaess) {
		this.importClaess = importClaess;
	}
	public ArrayList<String[]> getFields() {
		return fields;
	}
	public void setFields(ArrayList<String[]> fields) {
		this.fields = fields;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getBeanPkg() {
		return beanPkg;
	}
	public void setBeanPkg(String beanPkg) {
		this.beanPkg = beanPkg;
	}
	public void save(){
		try {
		createFile();
		setpackage();
		setImport();
		startClass();
		setField();
		setMethod();
		endClass();
		} catch (IOException e) {
			throw new RuntimeException(beanPkg+"."+beanName+".java create faild");
		}
	}
	private void setpackage() throws IOException{
		bw.write("package "+this.beanPkg+";\r\n\r\n");
	}
	@SuppressWarnings({ "unused", "unchecked" })
	private void setImport()throws IOException{
		for (Iterator iterator = importClaess.iterator(); iterator.hasNext();) {
			String importClass = (String) iterator.next();
			importClass = importClass.replaceAll("Timestamp", "Date");
			if(!importClass.startsWith("java.lang")){
				bw.write("import "+importClass+";\r\n");
			}
		}
		bw.write("import com.ay.framework.core.pojo.BasePojo;\r\n");
	}
	private void startClass()throws IOException{
		bw.write("\r\n");
		bw.write("/**\r\n");
		//bw.write(" *\r\n");
		bw.write(" * "+this.desc.replaceAll("\n", "<p>")+"\r\n");
		bw.write(" * @author 软件工程部产品小组\r\n");
		//bw.write(" * <p><b>create by IBatisTool " + new Date() +" \r\n");
		bw.write(" */\r\n");
		bw.write("public class "+this.beanName+" extends BasePojo "+" { \r\n");
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
	
	
	private void setField()throws IOException{
		for (Iterator<String[]> iterator = fields.iterator(); iterator.hasNext();) {
			String[] field = (String[]) iterator.next();
			String fieldName=field[1].toLowerCase();
			//setFieldAnnotation(field[2]);//edit by maxy 去掉注释
			//bw.write("    private "+field[0]+" "+fieldName+";\r\n");
			String dataType = field[0];
			if(dataType.equals("Timestamp")){
				dataType = "Date";
			}
			if(!fieldName.equals("id")&&!fieldName.equals("status")&&!fieldName.equals("created")&&!fieldName.equals("create_time")&&!fieldName.equals("updated")&&!fieldName.equals("update_time")){
				bw.write("    private "+dataType+" "+formatFiledString(fieldName)+";\r\n");//edit by maxy
			}
		}
		bw.write("\r\n");
	}
	@SuppressWarnings("unused")
	private void setFieldAnnotation(String field) throws IOException{
		bw.write("\r\n");
		bw.write("    /**\r\n");
		if(field!=null) 
			bw.write("     *  "+field.replaceAll("\n", "<p>")+"\r\n");
		bw.write("     */\r\n");
	}
	@SuppressWarnings({ "unchecked", "unused" })
	private void setParamAnnotation(ArrayList<String[]> params) throws IOException{
		
		bw.write("\r\n");
		bw.write("    /**\r\n");
		for (Iterator iterator = params.iterator(); iterator.hasNext();) {
			String[] field = (String[]) iterator.next();
			if(field[1]!=null) 
			field[1]=field[1].replaceAll("\n", "<p>");
			bw.write("     * @param "+field[0]+" "+field[1]+"\r\n");
		}
		bw.write("     */\r\n");
	}
	@SuppressWarnings("unused")
	private void setReturnAnnotation(String desc) throws IOException{
		String des="";
		if(desc!=null) 
			des=desc.replaceAll("\n", "<p>");
		bw.write("\r\n");
		bw.write("    /**\r\n");
		bw.write("     * "+des+"\r\n");
		bw.write("     * \r\n");
		bw.write("     * @return "+des+"\r\n");
		bw.write("     */\r\n");
	}
	@SuppressWarnings("unchecked")
	private void setMethod()throws IOException{
		ArrayList<String[]> li = new ArrayList<String[]>();
//		for (Iterator iterator = fields.iterator(); iterator.hasNext();) {
//			String[] field = (String[]) iterator.next();
//			li.add(new String[]{field[1].toLowerCase(),field[2],field[0]});
//		}
//		bw.write("\tpublic   "+this.beanName+"(){\r\n\r\n\r\n\t}");
//		//setParamAnnotation(li);
//		bw.write("\tpublic   "+this.beanName+"(");
//		StringBuffer params= new StringBuffer("_@_");
//		for (Iterator iterator = li.iterator(); iterator.hasNext();) {
//			String[] field = (String[]) iterator.next();
//			params.append(",String "+formatFiledString(field[0]));
//			//params.append(","+field[2]+" "+field[0]);//edit by maxy
//		}
//		bw.write(params.toString().replaceAll("_@_,", ""));
//		bw.write("){\r\n");
//		for (Iterator iterator = li.iterator(); iterator.hasNext();) {
//			String[] field = (String[]) iterator.next();
//			bw.write("\t\t\tthis."+formatFiledString(field[0])+"="+formatFiledString(field[0])+";\r\n");
//		}
//		bw.write("\t}\r\n");
		for (Iterator iterator = fields.iterator(); iterator.hasNext();) {
			String[] field = (String[]) iterator.next();
			String fieldName=field[1].toLowerCase();
			String dataType = field[0];
			if(dataType.equals("Timestamp")){
				dataType = "Date";
			}
			if(!fieldName.equals("id")&&!fieldName.equals("status")&&!fieldName.equals("created")&&!fieldName.equals("create_time")&&!fieldName.equals("updated")&&!fieldName.equals("update_time")){
				String setMethodName="set"+formatFiledString(fieldName).substring(0,1).toUpperCase()+formatFiledString(fieldName).substring(1);
				String getMethodName="get"+formatFiledString(fieldName).substring(0,1).toUpperCase()+formatFiledString(fieldName).substring(1);	
				ArrayList<String[]> list = new ArrayList<String[]>();
				list.add(new String[]{formatFiledString(fieldName),field[2]});
				//setParamAnnotation(list);
				bw.write("    public void "+setMethodName+"("+dataType+" "+formatFiledString(fieldName)+"){\r\n");
				//bw.write("    public  void "+setMethodName+"("+field[0]+" "+fieldName+"){\r\n");////edit by maxy
				bw.write("    	this."+formatFiledString(fieldName)+"="+formatFiledString(fieldName)+";\r\n    }\r\n");
				//setReturnAnnotation(field[2]);
				bw.write("    public "+dataType+" "+getMethodName+"(){\r\n");
				//bw.write("    public  "+field[0]+" "+getMethodName+"(){\r\n");//edit by maxy
				bw.write("    	return	this."+formatFiledString(fieldName)+";\r\n    }\r\n");
			}
		}
		bw.write("\r\n");
		
	}
	private void endClass()throws IOException{
		bw.write("}");
		bw.flush();
	}
	private void createFile()throws IOException{
		
		String fileName=src+beanPkg.replace(".", "/")+"/"+beanName+".java";
		File pFile = new File(src+beanPkg.replace(".", "/"));
		if(!pFile.isDirectory()){
			pFile.mkdirs();
		}	
		File file = new File(fileName);
		if(!file.exists()){
				file.createNewFile();
			
		}else
		{
			if(replace.equals("true"))file.delete();
			else throw new RuntimeException(fileName+" has allready being!");
		}
		 bw = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
		 System.out.println("create begin: "+fileName);
	}
	
	
	public static void main(String[] args) {
		BeanTool tool = new BeanTool();
		tool.setBeanName("test"+"bean");
		tool.setBeanPkg("com.xxx");
		tool.save();
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
}
