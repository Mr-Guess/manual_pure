package generate.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/** 
 * @Description Xml工具
 * @date 2013-2-20
 * @author WangXin
 */
public class XmlTool
{
    private static Log log = LogFactory.getLog(XmlHandler.class);
    /** 
     * doc2String 
     * 将xml文档内容转为String 
     * @return 字符串 
     * @param document 
     */ 
    public static String doc2String(Document document) 
    { 
       String s = ""; 
       try 
       { 
            //使用输出流来进行转化 
            ByteArrayOutputStream out = new ByteArrayOutputStream(); 
            OutputFormat format = new OutputFormat("  ", true, "UTF-8"); 
            XMLWriter writer = new XMLWriter(out, format); 
            writer.write(document); 
            s = out.toString("UTF-8"); 
       }catch(Exception ex) 
       {             
            ex.printStackTrace(); 
       }       
       return s; 
    }
    /** 
     * string2Document 
     * 将字符串转为Document 
     * @return  
     * @param s xml格式的字符串 
     */ 
    public static Document string2Document(String s) 
    { 
       Document doc = null; 
       try 
       { 
            doc = DocumentHelper.parseText(s); 
       }catch(Exception ex) 
       {             
            ex.printStackTrace(); 
       } 
       return doc; 
    }
    /** 
     * doc2XmlFile 
     * 将Document对象保存为一个xml文件到本地 
     * @return true:保存成功  flase:失败 
     * @param filename 保存的文件名 
     * @param document 需要保存的document对象 
     */ 
    public static boolean doc2XmlFile(Document document,String filename) 
    { 
       boolean flag = true; 
       try 
       { 
             OutputFormat format = OutputFormat.createPrettyPrint(); 
             format.setEncoding("UTF-8"); 
             XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)),format); 
             writer.write(document); 
             writer.close();
             log.info("已修改："+ filename);
         }catch(Exception ex) 
         { 
             flag = false; 
             ex.printStackTrace(); 
         } 
         return flag;       
    }
    
    /** 
     * string2XmlFile 
     * 将xml格式的字符串保存为本地文件，如果字符串格式不符合xml规则，则返回失败 
     * @return true:保存成功  flase:失败 
     * @param filename 保存的文件名 
     * @param str 需要保存的字符串 
     */ 
    public static boolean string2XmlFile(String str,String filename) 
    { 
       boolean flag = true; 
       try 
       { 
          Document doc =  DocumentHelper.parseText(str);        
          flag = doc2XmlFile(doc,filename); 
       }catch (Exception ex) 
       { 
          flag = false; 
          ex.printStackTrace(); 
       } 
       return flag; 
    }
    
    /** 
     * load 
     * 载入一个xml文档 
     * @return 成功返回Document对象，失败返回null 
     * @param uri 文件路径 
     */ 
    public static Document load(String filename) 
    { 
       Document document = null; 
       try  
       {  
           SAXReader saxReader = new SAXReader(); 
           document = saxReader.read(new File(filename)); 
       } 
       catch (Exception ex){ 
           ex.printStackTrace(); 
       }   
       return document; 
    }
}
