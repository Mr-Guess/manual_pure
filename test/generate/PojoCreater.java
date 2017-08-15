package generate;

import generate.tool.GenerateTool;
import generate.tool.PropertiesTool;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;
import com.ay.jfds.create.pojo.Columns;
import com.ay.jfds.create.pojo.Sheet;
import com.ay.jfds.create.tools.Tools;

public class PojoCreater {

	private static Logger log = LoggerFactory.getLogger(PojoCreater.class);
	
	public static void createJavaBean(Sheet sheet){
		
		// 获得配置文件中项目根目录
		PropertiesTool pt = PropertiesTool.newInstance("generate/generate.properties");
		//String relativelyPath = System.getProperty("user.dir");
		String relativelyPath = pt.readValue("projectUrl");
		
		// 待创建类名称
		String creatingname = sheet.getNameEn();
		
		// 待创建类中文名称
		String chineseTitle = sheet.getNameCh();
		
		// 待创建类字段
		List<Columns> list = sheet.getColumns();
		
		// 首字母改为小写生成包名
		String packageName = toLowerCaseFirstOne(creatingname);
		
		// 将首字母改为大写生成类名
		String className = toUpperCaseFirstOne(creatingname);
		
		// 生成类所在包
		String path = relativelyPath+"/src/com/ay/"+packageName+"/pojo";
		String packageSrc = "com.ay."+packageName+".pojo";
		
		// 拼接Java类代码
		String NL = System.getProperties().getProperty("line.separator");
		final String TEXT_1 = "package "+packageSrc;
		final String TEXT_2 = ";" + NL +  NL + NL;
		final String TEXT_3 = "import com.ay.framework.annotation.ChineseName; " + NL + " import com.ay.framework.core.pojo.BasePojo;"+ NL;
		final String TEXT_4 = "/**" + NL + "* " + NL + "* @项目名称:jfds2.0" + NL + "* @类名称:"+ creatingname + NL + "* @类描述:" + chineseTitle + ";" + NL + "* @创建人:" + NL + "* @创建时间:2014年4月15日 16:00:08" + NL + "* @修改人:" + NL + "* @修改时间:" + NL + "* @修改备注:" + NL + "* @version 2.0" + NL + "*/"+ NL;
		final String TEXT_5 = "@SuppressWarnings(\"all\")"+ NL;
		final String TEXT_6 = "@ChineseName(\"" + chineseTitle + "\")" + NL;
		final String TEXT_7 = "public class " + className + " extends BasePojo{" + NL + NL +NL;
		final String TEXT_8 = "}";
		
		
		// 循环拼接Java类代码并写入属性
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(TEXT_1);
		stringBuffer.append(TEXT_2);
		stringBuffer.append(TEXT_3);
		stringBuffer.append(TEXT_4);
		stringBuffer.append(TEXT_5);
		stringBuffer.append(TEXT_6);
		stringBuffer.append(TEXT_7);
		
		// 开始拼接对象字段
		for (Columns colu : list) {
			String text_column_chineseName = "\t@ChineseName(\"" + colu.getColumnCH() + "\")" +NL;
			String text_column_enName = "\tprivate ";
			if(colu.getType().equals("varbinary")){
				text_column_enName += "byte[] ";
			}else{
				text_column_enName += "String ";
			}
			text_column_enName += colu.getColumnEN()+";";
			text_column_enName += NL+NL;
			
			stringBuffer.append(text_column_chineseName);
			stringBuffer.append(text_column_enName);
			
		}
		
		// 拼接属性get set方法
		for (Columns colu : list) {
			String text_column_get ="\tpublic ";
			String text_column_set ="\tpublic void ";
			if(colu.getType().equals("varbinary")){
				text_column_get = text_column_get +"byte[] get" + toUpperCaseFirstOne(colu.getColumnEN()) + "(){" + NL +"\t\treturn " + colu.getColumnEN() + ";" + NL +"\t}"+NL+NL;
				text_column_set = text_column_set + "set" + toUpperCaseFirstOne(colu.getColumnEN()) + "(byte[] " + colu.getColumnEN() + "){" + NL + "\t\tthis." + colu.getColumnEN() + " = " + colu.getColumnEN()+";" + NL + "\t}"+NL+NL;
			}else{
				text_column_get = text_column_get +"String get" + toUpperCaseFirstOne(colu.getColumnEN()) + "(){" + NL +"\t\treturn " + colu.getColumnEN() + ";" + NL +"\t}"+NL+NL;
				text_column_set = text_column_set + "set" + toUpperCaseFirstOne(colu.getColumnEN()) + "(String " + colu.getColumnEN() + "){" + NL + "\t\tthis." + colu.getColumnEN() + " = " + colu.getColumnEN()+";" + NL + "\t}"+NL+NL;
			}
			
			stringBuffer.append(text_column_get);
			stringBuffer.append(text_column_set);
		}
		
		stringBuffer.append(TEXT_8);
		String fileNameString = path+"/"+className+".java";
		
		String iroi = stringBuffer.toString();
		
		String encode = getEncoding(iroi);
		
		String kyo = "";
		try {
			if(!encode.equals("UTF-8")){
				byte[] bs = iroi.getBytes(encode);
				kyo = new String(bs , "UTF-8");
			}else{
				kyo = iroi;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		GenerateTool.writeFile(iroi, fileNameString);
		
//		GenerateOnline generate = new GenerateOnline();
//		generate.generate();
	}
	
	//首字母转小写
    public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    //获取字符串编码格式
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }
    
    public static void main(String[] args) {
		Sheet sheet = new Sheet();
		
		sheet.setNameEn("testCreater");
		sheet.setNameCh("测试代码生成");
		
		List<Columns> list = new ArrayList<Columns>();
		
		Columns col = new Columns();
		col.setColumnEN("name");
		col.setColumnCH("名字");
		col.setType("String");
		list.add(col);
		
		Columns cols = new Columns();
		cols.setColumnEN("code");
		cols.setColumnCH("代码");
		cols.setType("String");
		list.add(cols);
		
		sheet.setColumns(list);
		
		createJavaBean(sheet);
		
	}
    
}
