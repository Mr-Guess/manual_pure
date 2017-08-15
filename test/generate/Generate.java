package generate;

import generate.tool.ClassUtil;
import generate.tool.GenerateTool;
import generate.tool.PropertiesTool;
import generate.tool.TemplateFactory;
import generate.tool.XmlHandler;
import generate.tool.template.TemplateType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.annotation.PersistenceIgnore;
import com.ay.framework.annotation.MultiLineField;
import com.ay.framework.core.pojo.BasePojo;

/**
 * @Description
 * @date 2013-3-12
 * @author WangXin
 */
public class Generate
{
    private static Logger log = LoggerFactory.getLogger(Generate.class);

    @Test
    public void generate()
    {
    	try {
    		String packageName = "com.yk";
            Set<Class<?>> classes = ClassUtil.getClasses(packageName);
            for (Class<?> c : classes)
            {
                if (c.isAnnotationPresent(ChineseName.class))
                {
                    log.info("已找到需要生成的实体类：" + c.getName());
                    doGenerate(c);
                }
            }
            log.info("生成完成！（请注释已生成实体类上的注解，停止不必要的生成）");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

    /**
     * 开始操作
     * 
     * @param c
     * @return void
     */
    private void doGenerate(Class<?> c)
    {
        String classFullName = c.getName();
        String packageName = classFullName.substring(0,
                classFullName.lastIndexOf("."));
        boolean hasPackage = checkHasPackage(packageName);
        String generatePackage = getGeneratePackageName(packageName, hasPackage);

        String className = classFullName.substring(classFullName
                .lastIndexOf(".") + 1);
        String lowerClassName = Character.toLowerCase(className.charAt(0))
                + className.substring(1);
        String tableName = GenerateTool.entityNameToDBName(lowerClassName);
        List<String> fieldNames = new ArrayList<String>();
        List<String> dbNames = new ArrayList<String>();
        addFieldList(c, fieldNames, dbNames);
        Map<String, String> chineseNameMap = new HashMap<String, String>();
        Map<String, Boolean> multiLineMap = new HashMap<String, Boolean>();
        judgeClassAnnotation(c, chineseNameMap, tableName);
        judgeFieldAnnotation(c, chineseNameMap, multiLineMap);
        Object[] arguments = new Object[]
        { tableName, classFullName, className, fieldNames, dbNames,
                chineseNameMap, generatePackage, lowerClassName, multiLineMap,
                hasPackage };
        generateOperation(arguments, hasPackage, generatePackage,
                className);
        alterOperation(generatePackage, hasPackage, className,
                chineseNameMap.get(tableName) + "模块");
    }

    /**
     * 验证是否在实体的分层包中，判断是否需要加上分层包
     * 
     * @param packageName
     * @return
     * @return boolean
     */
    private boolean checkHasPackage(String packageName)
    {
        if (packageName.endsWith("pojo") || packageName.endsWith("entity")
                || packageName.endsWith("model"))
        {
            return true;
        }
        return false;
    }

    /**
     * 获取生成包的路径
     * 
     * @param packageName
     * @param hasPackage
     * @return
     * @return String
     */
    private String getGeneratePackageName(String packageName, boolean hasPackage)
    {
        if (hasPackage)
        {
            return packageName.substring(0, packageName.lastIndexOf("."));
        }
        return packageName;
    }

    /**
     * 添加到属性列表
     * 
     * @param c
     * @param chineseNameMap
     * @param multiLineMap
     * @return void
     */
    private void addFieldList(Class<?> c, List<String> fieldNames,
            List<String> dbNames)
    {
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields)
        {
            if(f.isAnnotationPresent(PersistenceIgnore.class)) continue;
            log.debug(f.getName());
            log.debug(GenerateTool.fieldNameToDBName(f.getName()));
            fieldNames.add(f.getName());
            dbNames.add(GenerateTool.fieldNameToDBName(f.getName()));
        }
        Class<?> superClass = c.getSuperclass();
        if (superClass != null && superClass != BasePojo.class)
        {
            addFieldList(superClass, fieldNames, dbNames);
        }
    }

    /**
     * 判断类注解
     * 
     * @param c
     * @param chineseNameMap
     * @param tableName
     * @return void
     */
    private void judgeClassAnnotation(Class<?> c,
            Map<String, String> chineseNameMap, String tableName)
    {
        if (c.isAnnotationPresent(ChineseName.class))
        {
            ChineseName cn = c.getAnnotation(ChineseName.class);
            String tempCn = cn.value().trim().isEmpty() ? tableName : cn
                    .value();
            chineseNameMap.put(tableName, tempCn);
        }
        else
        {
            chineseNameMap.put(tableName, tableName);
        }
    }

    /**
     * 判断属性注解
     * 
     * @param c
     * @param chineseNameMap
     * @param multiLineMap
     * @return void
     */
    private void judgeFieldAnnotation(Class<?> c,
            Map<String, String> chineseNameMap,
            Map<String, Boolean> multiLineMap)
    {
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields)
        {
            log.debug(f.getName());
            log.debug(GenerateTool.fieldNameToDBName(f.getName()));
            if (f.isAnnotationPresent(MultiLineField.class))
            {
                multiLineMap.put(f.getName(), true);
            }
            else
            {
                multiLineMap.put(f.getName(), false);
            }
            if (!f.isAnnotationPresent(ChineseName.class))
            {
                chineseNameMap.put(f.getName(), f.getName());
                continue;
            }
            ChineseName fcn = f.getAnnotation(ChineseName.class);
            String value = fcn.value().trim().isEmpty() ? f.getName() : fcn
                    .value();
            chineseNameMap.put(f.getName(), value);
        }
        Class<?> superClass = c.getSuperclass();
        if (superClass != null && superClass != BasePojo.class)
        {
            judgeFieldAnnotation(superClass, chineseNameMap, multiLineMap);
        }
    }

    /**
     * 生成文件操作
     * 
     * @param arguments
     * @param hasPackage
     * @param generatePackage
     * @param className
     * @return void
     */
    private void generateOperation(Object[] arguments, boolean hasPackage,
            String generatePackage, String className)
    {
        Map<TemplateType, String> fileStringMap = new HashMap<TemplateType, String>();
        Map<TemplateType, String> fileNameMap = new HashMap<TemplateType, String>();
        TemplateType[] types = TemplateType.values();
        for (TemplateType type : types)
        {
            String fileString = TemplateFactory.newInstance(type).generate(
                    arguments);
            String fileNameString = null;
            switch (type)
            {
            case Sql:
                fileNameString = GenerateTool
                        .packageToPath(!hasPackage ? generatePackage
                                : generatePackage + ".sqlMapper")
                        + className + ".sql";
                break;
            case SqlMysql:
                fileNameString = GenerateTool
                        .packageToPath(!hasPackage ? generatePackage
                                : generatePackage + ".sqlMapper")
                        + className + "-Mysql.sql";
                break;
            case SqlMapper:
                fileNameString = GenerateTool
                        .packageToPath(!hasPackage ? generatePackage
                                : generatePackage + ".sqlMapper")
                        + className + "Mapper.xml";
                break;
            case Dao:
                fileNameString = GenerateTool
                        .packageToPath(!hasPackage ? generatePackage
                                : generatePackage + ".dao")
                        + className + "Dao.java";
                break;
            case Service:
                fileNameString = GenerateTool
                        .packageToPath(!hasPackage ? generatePackage
                                : generatePackage + ".service")
                        + className + "Service.java";
                break;
            case Action:
                fileNameString = GenerateTool
                        .packageToPath(!hasPackage ? generatePackage
                                : generatePackage + ".action")
                        + className + "Action.java";
                break;
            case Jsp:
                fileNameString = System.getProperty("user.dir")
                        + "/WebRoot/jsp/generate/" + className + ".jsp";
                break;
            case SqlMappeMysql:
            	fileNameString = GenerateTool
                .packageToPath(!hasPackage ? generatePackage
                        : generatePackage + ".sqlMapper")
                + className + "Mapper-mySql.xml";
            	break;
            case SqlOralce:
            	 fileNameString = GenerateTool
                 .packageToPath(!hasPackage ? generatePackage
                         : generatePackage + ".sqlMapper")
                 + className + "-oracle.sql";
            	break;
            case SqlMapperOracle:
	            	fileNameString = GenerateTool
	                .packageToPath(!hasPackage ? generatePackage
	                        : generatePackage + ".sqlMapper")
	                + className + "Mapper-oracle.xml";
            	break;
            default:
                log.error("unknown type!!!");
                return;
            }
            GenerateTool.writeFile(fileString, fileNameString);
        }
    }

    /**
     * 修改struts、sqlMapper、spring配置文件
     * 
     * @param generatePackage
     * @param hasPackage
     * @param className
     * @param comments
     *            注释
     * @return void
     */
    private void alterOperation(String generatePackage, Boolean hasPackage,
            String className, String comments)
    {
        PropertiesTool pt = PropertiesTool
                .newInstance("generate/generate.properties");
        String strutsXmlFile = pt.readValue("strutsXmlFile");
        String springXmlFile = pt.readValue("springXmlFile");
        String sqlMapperXmlFile = pt.readValue("sqlMapperXmlFile");
        String sqlMapperXmlFileMysql = pt.readValue("sqlMapperXmlFileMysql");
        XmlHandler.alterSSIXml(strutsXmlFile, springXmlFile, sqlMapperXmlFile,sqlMapperXmlFileMysql,
                generatePackage, hasPackage, className, comments);
    }
}





