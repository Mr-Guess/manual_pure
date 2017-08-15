package com.ay.framework.core.dao.dialect;

/**
 * oracle关系映射类型
 * 
 * @author lushigai
 * @version 1.0
 * @Date:2012-9-10
 */
public final class OracleMappedType
{
    /** SQL类型 */
    private static String[] sqlTypes  = {
        "number",
        "number",
        "number",
        "numeric",
        "varchar2",
        "char",
        "date",
        "timestamp",
        "text",
        "blob"};
    /** 对应的Java类型 */
    private static String[] javaTypes = {
        "int",
        "short",
        "long",
        "numeric",
        "string",
        "char",
        "date",
        "datetime",
        "clob",
        "byte[]"};

    /**
     * @param javaType javaType
     * @return string
     */
    public static String getSqlType(String javaType)
    {
        int index = 0;
        for (String name : javaTypes)
        {
            if (name.equals(javaType))
            {
                break;
            }
            index++;
        }
        return sqlTypes[index];
    }

    /**
     * @param sqlType sqlType
     * @return string
     */
    public static String getJavaType(String sqlType)
    {
        int index = 0;
        for (String name : sqlTypes)
        {
            if (name.equals(sqlType))
            {
                break;
            }
            index++;
        }
        return javaTypes[index];
    }
}
