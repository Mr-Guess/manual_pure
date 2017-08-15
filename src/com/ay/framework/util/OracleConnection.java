package com.ay.framework.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import oracle.sql.BLOB;

import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.jfds.icon.pojo.SysIcon;
import com.ay.jfds.icon.service.SysIconService;

/**
 * oracle数据连接
 * @author Elivs
 *
 */
public class OracleConnection {
	static{
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","testuser","sasa");
	}
	
	public void insert(final SysIcon sysicon){
		 PreparedStatement preparedStatement = null;  
	        Connection connection = null;  
	        try {  
	            /* 先保存空blob */  
	            connection = getConnection();  
	            connection.setAutoCommit(false);// 必须设置为false，即开启事务！  
	            preparedStatement = connection  
	                    .prepareStatement("INSERT INTO sys_icon(icon_no,name,icon_desc,type,status) VALUES(?,?,?,?,?)");  
	            // preparedStatement.setBlob(2, oracle.sql.BLOB.empty_lob());//empty_lob()已过期  
	            preparedStatement.setInt(1, Integer.valueOf(sysicon.getIconNo()));
	            preparedStatement.setString(2,sysicon.getName());
	            preparedStatement.setString(3, sysicon.getIconDesc());
	            preparedStatement.setString(4,sysicon.getType());
	            preparedStatement.setString(5,sysicon.getStatus());
	            
	            // log.info(preparedStatement.executeUpdate() > 0 ? "保存空blob成功" : "保存空blob失败");  
	  
	            if (preparedStatement.executeUpdate() > 0) {  
	                System.out.println("保存空blob成功，方法继续！");  
	            } else {  
	            	 System.out.println("保存空blob失败，方法返回！");  
	                connection.rollback();  
	                return;  
	            }  
	            connection.commit();// 开启了事物，必须手动提交  
	        }catch (Exception e) {
	        	e.printStackTrace();
			}
	}
	public static void main(String[] args) {
		try {
			OracleConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
