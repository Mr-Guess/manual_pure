package com.ay.ibatistool.tool.template;

import java.util.List;

public class ServiceTemplate
{
  protected static String nl;
  public static synchronized ServiceTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ServiceTemplate result = new ServiceTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".service;" + NL + "" + NL + "import com.ay.framework.core.service.BaseService;" + NL + "import ";
  protected final String TEXT_3 = ".dao.";
  protected final String TEXT_4 = "Dao;" + NL + "import ";
  protected final String TEXT_5 = ".pojo.";
  protected final String TEXT_6 = ";" + NL + "" + NL + "/**" + NL + " * " + NL + " * @author" + NL + " *" + NL + " */" + NL + "public class ";
  protected final String TEXT_7 = "Service extends BaseService<";
  protected final String TEXT_8 = ", ";
  protected final String TEXT_9 = "Dao> {" + NL + "\t" + NL + "}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(((List)argument).get(0).toString());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(((List)argument).get(0).toString());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(((List)argument).get(1).toString());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(((List)argument).get(0).toString());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(((List)argument).get(1).toString());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(((List)argument).get(1).toString());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(((List)argument).get(1).toString());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(((List)argument).get(1));
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}
