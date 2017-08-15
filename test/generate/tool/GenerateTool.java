package generate.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @Description 生成工具类
 * @date 2013-2-20
 * @author WangXin
 */
public class GenerateTool {
	private static Logger log = LoggerFactory.getLogger(GenerateTool.class);
	private GenerateTool()
	{
	}
	/**
	 * 写文件
	 * 
	 * @param fileString
	 * @param fileName
	 * @return void
	 */
	public static void writeFile(String fileString, String fileName) {
		try {
			File file = new File(fileName);
			File pfile = file.getParentFile();
			if (!pfile.isDirectory()) {
				pfile.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
				//FileWriter writer = new FileWriter(file);
				FileOutputStream fileWriter = new FileOutputStream(file);
				OutputStreamWriter optw = new OutputStreamWriter(fileWriter, "UTF-8");
				BufferedWriter bw = new BufferedWriter(optw);
				bw.write(fileString);
				bw.flush();
				bw.close();
				log.info("已生成{}文件", fileName);
			} else {
				log.warn("已存在{}文件,未生成", fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 实体类名转为数据库表名sonName --> TB_SON_NAME
	 * @param fieldName
	 * @return
	 * @return String
	 */
	public static String entityNameToDBName(String entityName) {
	    return "TB_" + fieldNameToDBName(entityName);
	}
	
	/**
	 * 属性名转为数据库字段名sonName --> SON_NAME
	 * @param fieldName
	 * @return
	 * @return String
	 */
	public static String fieldNameToDBName(String fieldName) {
		String DBName = "";
		int start = 0;
		for(int i = 0; i < fieldName.length(); i++) {
			char c = fieldName.charAt(i);
			if(Character.isUpperCase(c)) {
				DBName += fieldName.substring(start, i).toLowerCase() + "_";
				start = i;
			}
		}
		DBName += fieldName.substring(start).toLowerCase();
		return DBName.toUpperCase();
	}
	/**
	 * package路径转为文件路径 com.wx.test --> src/com/wx/text/
	 * @param fileString
	 * @param packageName
	 * @param name
	 * @throws IOException
	 * @return void
	 */
	public static String packageToPath(String packageName) {
		return "src/" + packageName.replace(".", "/") + "/";
	}
	
	/**
	 * package路径转为文件路径 com.wx.test --> src/com/wx/text/
	 * @param fileString
	 * @param packageName
	 * @param name
	 * @throws IOException
	 * @return void
	 */
	public static String packageToPathDeath(String packageName) {
		PropertiesTool pt = PropertiesTool.newInstance("generate/generate.properties");
    	String projectPath = pt.readValue("projectUrl");
		return projectPath + packageName.replace(".", "/") + "/";
	}
}

