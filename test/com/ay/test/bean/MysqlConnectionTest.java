package com.ay.test.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlConnectionTest {

	private static String Driver = "com.mysql.jdbc.Driver";
	private static String Url = "jdbc:mysql://10.0.0.191:3306/ayjava_sx_base?user=root&password=123456";
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName(Driver);
		Connection con = DriverManager.getConnection(Url);
		if (con != null) {
			System.out.println("success");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.getResultSet();
		}
		System.out.println(con);
	}
}
