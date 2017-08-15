package com.ay.framework.core.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.core.utils.spring.SpringContextHolder;

/**
 * 数据库连接工具类封装
 * 采用饿汉式的写法
 * 
 * @author zxy
 *
 */
public class DBConnectionUtil {
	private static DBConnectionUtil instance = new DBConnectionUtil();
	private static Logger logger = LoggerFactory.getLogger(DBConnectionUtil.class);
	
	private DBConnectionUtil() {
	}
	
	public synchronized static DBConnectionUtil getInstance() {
		return instance;
	}
	
/*	public static DBConnectionUtil getInstance() {
		if (instance == null) {
			synchronized (DBConnectionUtil.class) {
				instance = new DBConnectionUtil();
			}
		}
		return instance;
	}*/
	
	public Connection getConnection() throws SQLException {
		DataSource dataSource = SpringContextHolder.getBean("dataSource");
		return dataSource.getConnection();
	}
	
	public void close(Connection connection, Statement statement) {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			logger.error("关闭数据库连接对象Connection发生错误:{}", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
					statement = null;
				}
			} catch (Exception e2) {
				logger.error("关闭数据库Statement发生错误:{}", e2);
			}
		}
	}
	
	public void close(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
		} catch (Exception e) {
			logger.error("关闭ResultSet对象时发生错误:{}", e);
		} finally {
			close(connection, statement);
		}
	}
}
