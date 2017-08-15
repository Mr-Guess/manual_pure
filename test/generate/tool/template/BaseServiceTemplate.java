package generate.tool.template;

import java.util.*;

public class BaseServiceTemplate implements ITemplate
{
  protected static String nl;
  public static synchronized BaseServiceTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    BaseServiceTemplate result = new BaseServiceTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import ";
  protected final String TEXT_3 = ".";
  protected final String TEXT_4 = "Dao;" + NL + "import ";
  protected final String TEXT_5 = ";" + NL + "import com.ay.framework.core.service.BaseService;" + NL + "" + NL + "public class ";
  protected final String TEXT_6 = "Service extends BaseService<";
  protected final String TEXT_7 = ", ";
  protected final String TEXT_8 = "Dao> {" + NL + "\t" + NL + "}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	Object[] o=(Object[])argument;
	String classFullName = o[1].toString();
	String className = o[2].toString();
	String generatePackage = o[6].toString();
	boolean hasPackage = (Boolean)o[9];

    stringBuffer.append(TEXT_1);
    stringBuffer.append(generatePackage);
    stringBuffer.append(!hasPackage?"":".service");
    stringBuffer.append(TEXT_2);
    stringBuffer.append(generatePackage);
    stringBuffer.append(!hasPackage?"":".dao");
    stringBuffer.append(TEXT_3);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(classFullName);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}
