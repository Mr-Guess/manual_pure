package generate.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description properties工具
 * @date 2013-3-14
 * @author WangXin
 */
public class PropertiesTool
{
    Properties prop = new Properties();

    private PropertiesTool()
    {
    }

    public static PropertiesTool newInstance(String fileName)
    {
        PropertiesTool pt = new PropertiesTool();
        InputStream in = PropertiesTool.class.getClassLoader().getResourceAsStream(
                fileName);
        try
        {
            pt.prop.load(in);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return pt;
    }
    
    public static PropertiesTool newInstance(InputStream in)
    {
	PropertiesTool pt = new PropertiesTool();
	try
	{
	    pt.prop.load(in);
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
	return pt;
    }

    public String readValue(String key)
    {
        return prop.getProperty(key);
    }
    
    public static void main(String[] args)
    {
        PropertiesTool pt = PropertiesTool.newInstance("generate/generate.properties");
        pt.readValue("strutsXmlFile");
    }
}
