package generate.tool;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;

/**
 * @Description　Xml配置文件修改处理类（针对此系统）
 * @date 2013-2-20
 * @author WangXin
 */
@SuppressWarnings("all")
public class XmlHandler
{
    private static Log log = LogFactory.getLog(XmlHandler.class);

    private XmlHandler()
    {

    }

    /**
     * 修改Struts、Spring、Ibatis配置文件
     * 
     * @param strutsXmlFile
     *            strutsXmlFile路径
     * @param springXmlFile
     *            springXmlFile路径
     * @param sqlMapperXmlFile
     *            sqlMapperXmlFile路径
     * @param packageName
     *            包名
     * @param hasPackage
     *            类路径产生是否分层包
     * @param className
     *            类名
     * @param comments
     *            注释
     * @return void
     */
    public static void alterSSIXml(String strutsXmlFile, String springXmlFile,
            String sqlMapperXmlFile, String sqlMapperXmlFileMysql, String packageName, boolean hasPackage,
            String className, String comments)
    {
        alterStrutsXml(strutsXmlFile, packageName, hasPackage, className,
                comments);
        alterBeansXml(springXmlFile, packageName, hasPackage, className,
                comments);
        alterSqlMapperXml(sqlMapperXmlFile, packageName, hasPackage, className,
                comments);
        alterSqlMapperXmlMysql(sqlMapperXmlFileMysql, packageName, hasPackage, className,
        		comments);
    }

    private static boolean checkRootName(Element root, String name)
    {
        if (!name.equalsIgnoreCase(root.getName()))
        {
            log.error("文件非标准" + name + "配置文件");
            return false;
        }
        return true;
    }

    /**
     * 
     * @param fileName
     *            xml路径
     * @param packageName
     *            包名
     * @param hasPackage
     *            是否包含action
     * @param className
     *            类名
     * @param comments
     *            注释
     * @return void
     */
    public static void alterStrutsXml(String fileName, String packageName,
            boolean hasPackage, String className, String comments)
    {
        String xmlRootNodeName = "struts";
        String lowerClassName = Character.toLowerCase(className.charAt(0))
                + className.substring(1);
        Document doc = XmlTool.load(fileName);
        Element root = doc.getRootElement();
        if (!checkRootName(root, xmlRootNodeName))
            return;
        List<Element> listNode = doc.selectNodes("struts/package");
        for (Element e : listNode)
        {
            if (lowerClassName.equals(e.attribute("name").getValue()))
            {
                log.warn(xmlRootNodeName + "配置文件已经存在" + lowerClassName + "Action");
                return;
            }
        }
        root.addComment(comments);
        //包根
        Element packageElement = root.addElement("package");
        packageElement.addAttribute("name", lowerClassName);
        packageElement.addAttribute("extends", "struts-default");
        packageElement.addAttribute("namespace", "/" + lowerClassName);
        //Action根
        Element actionElement = packageElement.addElement("action");
        actionElement.addAttribute("name", lowerClassName + "Action");
        actionElement.addAttribute("class", packageName + (!hasPackage ? "." : ".action.") + className + "Action");
        //result根
        Element pageElement = actionElement.addElement("result");
        pageElement.addAttribute("name", "exp");
        pageElement.addAttribute("type", "chain");
        //return根
        Element extentElement = pageElement.addElement("param");
        Element returnElement = pageElement.addElement("param");
        extentElement.addAttribute("name", "nameSpace");
        extentElement.addText("/excel");
        returnElement.addAttribute("name", "actionName");
        returnElement.addText("excel_exp");
        
        XmlTool.doc2XmlFile(doc, fileName);
    }

    /**
     * 
     * @param fileName
     *            xml路径
     * @param packageName
     *            包名
     * @param hasPackage
     *            是否包含dao等包名
     * @param className
     *            类名
     * @param comments
     *            注释
     * @return void
     */
    public static void alterBeansXml(String fileName, String packageName,
            boolean hasPackage, String className, String comments)
    {
        String xmlRootNodeName = "beans";
        String lowerClassName = Character.toLowerCase(className.charAt(0))
                + className.substring(1);
        Document doc = XmlTool.load(fileName);
        Element root = doc.getRootElement();
        if (!checkRootName(root, xmlRootNodeName))
            return;
        //这里因为有命名空间所以要加上
        HashMap nsMap=new HashMap();
        nsMap.put("bean","http://www.springframework.org/schema/beans");
        XPath xsub = doc.createXPath("//bean:bean");  
        xsub.setNamespaceURIs(nsMap);
        List<Element> listNode = xsub.selectNodes(doc);
        for (Element e : listNode)
        {
            if ((lowerClassName + "Dao").equals(e.attribute("id").getValue()))
            {
                log.warn(xmlRootNodeName + "配置文件已经存在" + lowerClassName + "Dao");
                return;
            }
        }
        root.addComment(comments);
        Element daoElement = root.addElement("bean");
        daoElement.addAttribute("id", lowerClassName + "Dao");
        daoElement.addAttribute("class", packageName
                + (!hasPackage ? "." : ".dao.") + className + "Dao");
        daoElement.addAttribute("parent", "baseDao");
        Element beanElement = root.addElement("bean");
        beanElement.addAttribute("id", lowerClassName + "Service");
        beanElement.addAttribute("class", packageName
                + (!hasPackage ? "." : ".service.") + className
                + "Service");
        Element propertyElement = beanElement.addElement("property");
        propertyElement.addAttribute("name", "dao");
        propertyElement.addAttribute("ref", lowerClassName + "Dao");
        XmlTool.doc2XmlFile(doc, fileName);
    }

    /**
     * 
     * @param fileName
     *            xml路径
     * @param packageName
     *            包名
     * @param hasPackage
     *            是否包含sqlMapper包名
     * @param className
     *            类名
     * @param comments
     *            注释
     * @return void
     */
    public static void alterSqlMapperXml(String fileName, String packageName,
            boolean hasPackage, String className, String comments)
    {
        String xmlRootNodeName = "sqlMapConfig";
        String lowerClassName = Character.toLowerCase(className.charAt(0))
                + className.substring(1);
        Document doc = XmlTool.load(fileName);
        Element root = doc.getRootElement();
        if (!checkRootName(root, xmlRootNodeName))
            return;
        String sourceStr = packageName.replace(".", "/") + (!hasPackage ? "/" : "/sqlMapper/")
                + className + "Mapper.xml";
        List<Element> listNode = doc.selectNodes("sqlMapConfig/sqlMap");
        for (Element e : listNode)
        {
            if ((sourceStr).equals(e.attribute("resource").getValue()))
            {
                log.warn(xmlRootNodeName + "配置文件已经存在" + sourceStr);
                return;
            }
        }
        root.addComment(comments);
        Element daoElement = root.addElement("sqlMap");
        daoElement.addAttribute("resource", sourceStr);
        XmlTool.doc2XmlFile(doc, fileName);
    }
    
    /**
     * 
     * @param fileName
     *            xml路径
     * @param packageName
     *            包名
     * @param hasPackage
     *            是否包含sqlMapper包名
     * @param className
     *            类名
     * @param comments
     *            注释
     * @return void
     */
    public static void alterSqlMapperXmlMysql(String fileName, String packageName,
    		boolean hasPackage, String className, String comments)
    {
    	String xmlRootNodeName = "sqlMapConfig";
    	String lowerClassName = Character.toLowerCase(className.charAt(0))
    			+ className.substring(1);
    	Document doc = XmlTool.load(fileName);
    	Element root = doc.getRootElement();
    	if (!checkRootName(root, xmlRootNodeName))
    		return;
    	String sourceStr = packageName.replace(".", "/") + (!hasPackage ? "/" : "/sqlMapper/")
    			+ className + "Mapper-mySql.xml";
    	List<Element> listNode = doc.selectNodes("sqlMapConfig/sqlMap");
    	for (Element e : listNode)
    	{
    		if ((sourceStr).equals(e.attribute("resource").getValue()))
    		{
    			log.warn(xmlRootNodeName + "配置文件已经存在" + sourceStr);
    			return;
    		}
    	}
    	root.addComment(comments);
    	Element daoElement = root.addElement("sqlMap");
    	daoElement.addAttribute("resource", sourceStr);
    	XmlTool.doc2XmlFile(doc, fileName);
    }
}
