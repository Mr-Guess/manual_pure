package generate.tool.template;

import java.util.*;

public class BaseSQLMySqlTemplate implements ITemplate
{
    protected static String nl;

    public static synchronized BaseSQLMySqlTemplate create(String lineSeparator)
    {
        nl = lineSeparator;
        BaseSQLMySqlTemplate result = new BaseSQLMySqlTemplate();
        nl = null;
        return result;
    }

    public final String NL = nl == null ? (System.getProperties()
            .getProperty("line.separator")) : nl;
    protected final String TEXT_1 = "CREATE TABLE ";
    protected final String TEXT_2 = " (";
    protected final String TEXT_3 = NL;
   //protected final String TEXT_4 = " NVARCHAR(200) NULL ,--";
   //protected final String TEXT_6 = " NVARCHAR(4000) NULL ,--";
    protected final String TEXT_4 = " VARCHAR(200) NULL ,-- ";
    protected final String TEXT_6 = " text NULL ,-- ";
    protected final String TEXT_5 = NL + NL
            + "ID VARCHAR(50) NOT NULL PRIMARY KEY ," + NL
            + "STATUS CHAR(1) NULL DEFAULT '1' ," + NL
            + "CREATED VARCHAR(32) NULL ," + NL + "CREATE_TIME DATETIME NULL ,"
            + NL + "UPDATED VARCHAR(32) NULL ," + NL
            + "UPDATE_TIME DATETIME NULL, " + NL + "ORG text NULL, " + NL +"ORG_TREE text NULL, " + NL + ") ENGINE=MyISAM;";
    protected final String TEXT_7 = NL+"ALTER TABLE `";
    protected final String TEXT_8 ="` ADD FULLTEXT `fidx_";
    protected final String TEXT_9 ="_orgTree` (`ORG_TREE`);";
    public String generate(Object argument)
    {
        final StringBuffer stringBuffer = new StringBuffer();

        Object[] o = (Object[]) argument;
        String tableName = o[0].toString().toLowerCase();
        List<String> fieldNames = (List<String>) o[3];
        List<String> DBFieldNames = (List<String>) o[4];
        Map<String, String> chineseNameMap = (Map<String, String>) o[5];
        Map<String, Boolean> multiLineMap = (Map<String, Boolean>) o[8];

        stringBuffer.append(TEXT_1);
        stringBuffer.append(tableName);
        stringBuffer.append(TEXT_2);
        for (int i = 0; i < DBFieldNames.size(); i++)
        {
            String DBFieldName = DBFieldNames.get(i);
            String fieldName = fieldNames.get(i);

            stringBuffer.append(TEXT_3);
            stringBuffer.append(DBFieldName);
            boolean isMultiLine = multiLineMap.get(fieldName);
            if (!isMultiLine)
            {
                stringBuffer.append(TEXT_4);
            }
            else
            {
                stringBuffer.append(TEXT_6);
            }
            stringBuffer.append(chineseNameMap.get(fieldName));
        }
        stringBuffer.append(TEXT_5);
        stringBuffer.append(TEXT_7);
        stringBuffer.append(tableName);
        stringBuffer.append(TEXT_8);
        stringBuffer.append(tableName);
        stringBuffer.append(TEXT_9);
        return stringBuffer.toString();
    }
}
