package com.ay.framework.core.dao.dialect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mysql分页
 * 
 * @author leiyuanliang
 * @version 1.0
 * @Date:2014年7月11日12:59:26
 */
public class MySqlDialect implements IDialect {
	/**
	 * 日志信息
	 */
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	/**
	 * sql结束符号
	 */
	protected static final String SQL_END_DELIMITER = ";";
	/**
	 * sql结束符号
	 */
	final int sqlAddChar = 40;

	/**
	 * 返回封装好的物理分页
	 * 
	 * @param sql
	 *            要分页的sql语句
	 * @param hasOffset
	 *            是否物理分页
	 * @return 封装好的sql语句
	 */
	public String getLimitString(String sql, boolean hasOffset) {
		return "";
	}

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
	public String getLimitString(String sql, int offset, int limit) {
		sql = trim(sql);
		StringBuffer sb = new StringBuffer(sql.length() + sqlAddChar);
		sb.append(sql);
		String orderSql = "";
		sql = sql.toLowerCase();
		if (sql.indexOf("order by") == -1) {
			orderSql = " order by id ASC";
		} 
		String pageSql = sql+orderSql+" limit "+offset+","+limit;
		LOGGER.info(pageSql);
		return pageSql;
	}

	/**
	 * 是否支持物理分页
	 * 
	 * @return 是否支持物理分页
	 */
	public boolean supportsLimit() {
		return true;
	}

	/**
	 * 格式化分页sql
	 * 
	 * @param sql
	 *            被格式化的sql
	 * @return 格式化后的sql
	 */
	private String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0,
					sql.length() - 1 - SQL_END_DELIMITER.length());
		}
		return sql;
	}
	@Override
	public String getJavaType(String sqlType) {
		return MySqlMappedType.getJavaType(sqlType);
	}

	public String MySqlMappedType(String javaType) {
		return MySqlMappedType.getSqlType(javaType);
	}

	public String alterTableColumn() {
		return "My Sql alter table sql";
	}

	@Override
	public String getSqlType(String javaType) {
		return MySqlMappedType.getSqlType(javaType);
	}

}
