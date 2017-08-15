package generate.tool;

import generate.tool.template.ITemplate;
import generate.tool.template.TemplateType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * @Description 模板工厂
 * @date 2013-3-13
 * @author WangXin
 */
public class TemplateFactory
{
    private static Log log = LogFactory.getLog(TemplateFactory.class);
    private TemplateFactory() {
        
    }
    public static ITemplate newInstance(TemplateType type) {
        PropertiesTool pt = PropertiesTool.newInstance("generate/tool/template/template.properties");
        String classFullName = pt.readValue(type.toString());
        log.debug(classFullName);
        Class<?> c = null;
        try
        {
            c = Thread.currentThread().getContextClassLoader().loadClass(classFullName);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        if(!ClassUtil.isInterface(c, ITemplate.class))
            throw new ClassCastException("指定的模板类不是标准的模块类！");
        ITemplate template = null;
        try
        {
            template = (ITemplate) c.newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return template;
    }
}

