package com.ay.test.bean;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionManager {
	private final String URL = "jdbc:sqlserver://10.0.0.50:1435;DatabaseName=ayjava_jfds";
	private final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	private static DBConnectionManager instance = null;
	private Connection connection = null;
	
	public static DBConnectionManager getInstance() {
		if (instance == null) {
			synchronized (DBConnectionManager.class) {
				instance = new DBConnectionManager();
			}
		}
		return instance;
	}
	
	private DBConnectionManager() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, "sa", "sasa");
		} catch (Exception e) {
		}
		
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public static void main(String[] args) {
		System.out.println(DBConnectionManager.getInstance().getConnection());
	}
}
