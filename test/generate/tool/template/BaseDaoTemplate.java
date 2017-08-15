package generate.tool.template;

import java.util.*;

public class BaseDaoTemplate implements ITemplate
{
  protected static String nl;
  public static synchronized BaseDaoTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    BaseDaoTemplate result = new BaseDaoTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import ";
  protected final String TEXT_3 = ";" + NL + "import com.ay.framework.core.dao.BaseDao;" + NL + "" + NL + "public class ";
  protected final String TEXT_4 = "Dao extends BaseDao<";
  protected final String TEXT_5 = "> {" + NL + "\t@Override" + NL + "\tpublic String getEntityName() {" + NL + "\t\treturn \"";
  protected final String TEXT_6 = "\";" + NL + "\t}" + NL + "\t@Override" + NL + "\tpublic String getTableName() {" + NL + "\t\treturn \"";
  protected final String TEXT_7 = "\";" + NL + "\t}" + NL + "" + NL + "}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	Object[] o=(Object[])argument;
	String tableName = o[0].toString();
	String classFullName = o[1].toString();
	String className = o[2].toString();
	String generatePackage = o[6].toString();
	boolean hasPackage = (Boolean)o[9];

    stringBuffer.append(TEXT_1);
    stringBuffer.append(generatePackage);
    stringBuffer.append(!hasPackage?"":".dao");
    stringBuffer.append(TEXT_2);
    stringBuffer.append(classFullName);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
