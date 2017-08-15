package com.ay.framework.core.dao.pagination;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.ay.framework.core.dao.dialect.IDialect;
import com.ay.framework.core.dao.dialect.MySqlDialect;
import com.ay.framework.core.dao.dialect.OracleDialect;
import com.ay.framework.core.dao.dialect.SqlServerDialect;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.jfds.create.tools.Property;

/**
 * 根据配置动态加载数据源 工厂类
 * @author lyl
 *
 */
@SuppressWarnings("all")
public class DialectFactory {
	public static final String ORACLE="oracl";
	public static final String SQL_SERVER="sqlserver";
	public static final String MY_SQL="mysql";
	private static final String SQL_TYPE=Property.getProperty("datasource.type");//获取数据库连接对象
	public static IDialect getDialect(){
		if(SQL_SERVER.equals(SQL_TYPE)){
			return new SqlServerDialect();
		}else if(MY_SQL.equals(SQL_TYPE)){
			return new MySqlDialect();
		}else{
			return new OracleDialect();
		}
	}

}
