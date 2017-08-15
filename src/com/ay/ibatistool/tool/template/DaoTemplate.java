package com.ay.ibatistool.tool.template;

import java.util.List;

public class DaoTemplate
{
  protected static String nl;
  public static synchronized DaoTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DaoTemplate result = new DaoTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "package ";
  protected final String TEXT_2 = ".dao;" + NL + "" + NL + "import com.ay.framework.core.dao.BaseDao;" + NL + "import ";
  protected final String TEXT_3 = ".pojo.";
  protected final String TEXT_4 = ";" + NL + "" + NL + "/**" + NL + " * " + NL + " * @author " + NL + " *" + NL + " */" + NL + "public class ";
  protected final String TEXT_5 = "Dao extends BaseDao<";
  protected final String TEXT_6 = "> {" + NL + "\t@Override" + NL + "\tpublic String getEntityName() {" + NL + "\t\treturn \"";
  protected final String TEXT_7 = "\";" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tpublic String getTableName() {" + NL + "\t\treturn \"";
  protected final String TEXT_8 = "\";" + NL + "\t}" + NL + "" + NL + "}";

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
    stringBuffer.append(((List)argument).get(1).toString());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(((List)argument).get(1).toString());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(((List)argument).get(1).toString());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(((List)argument).get(3).toString());
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}
