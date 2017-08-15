package com.ay.framework.core.dao.dialect;

/**
 * sqlserver关系映射类型
 * 
 * @author lushigai
 * @version 1.0
 * @Date:2012-9-10
 */
public final class SqlServerMappedType
{
    /** SQL类型 */
    private static String[] sqlTypes  = {
        "int",
        "smallint",
        "bigint",
        "numeric",
        "nvarchar",
        "nchar",
        "datetime",
        "datetime",
        "text",
        "varbinary"};
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
        "blob"};

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
