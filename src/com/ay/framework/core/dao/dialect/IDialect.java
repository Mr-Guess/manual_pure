package com.ay.framework.core.dao.dialect;

/**
 * 改造sql接口
 * 
 * @author lushigai
 * @version 1.0
 * @Date:2012-9-10
 */
public interface IDialect {
	/**
	 * 返回是否支持物理分页
	 * 
	 * @return 是否支持物理分页
	 */
	boolean supportsLimit();

	/**
	 * 返回封装好的物理分页
	 * 
	 * @param sql
	 *            要分页的sql语句
	 * @param hasOffset
	 *            是否物理分页
	 * @return 封装好的sql语句
	 */
	String getLimitString(String sql, boolean hasOffset);

	/**
	 * 返回封装好的物理分页
	 * 
	 * @param sql
	 *            要分页的sql语句
	 * @param offset
	 *            从第几条开始
	 * @param limit
	 *            每次查几条
	 * @return 封装好的sql语句
	 */
	String getLimitString(String sql, int offset, int limit);

	/**
	 * 转换属性字段类型为数据库的列类型
	 * 
	 * @param javaType
	 *            javatype
	 * @return string
	 */
	String getSqlType(String javaType);

	/**
	 * 
	 * @param sqlType
	 *            sqlType
	 * @return string
	 */
	String getJavaType(String sqlType);

	/**
	 * 根据不同的数据库得到不同的alter table sql
	 * 
	 * @return
	 */
	String alterTableColumn();
}
