package com.ay.jfds.create.tools;
import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.SQLException;  
import java.sql.Statement;  

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sun.awt.AppContext;

/**
 * 
 * @项目名称:jfds2.0
 * @类名称:Jdbc
 * @类描述:使用JDBC创建数据表
 * @创建人:雷远亮
 * @创建时间:2014年6月23日11:05:08
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @version 2.0
 */
public class Jdbc {
	static{
		System.out.println("--------------正在加载数据库驱动----------------");
        try {
        	//Class.forName("oracle.jdbc.OracleDriver");
            Class.forName(Property.getProperty("datasource.driverClassName"));// 加载sqlserver数据驱动  
            System.out.println("----------------数据库驱动加载成功------------------");
        } catch (Exception e) {  
            System.out.println("-----------------数据库驱动加载失败------------------" + e.getMessage());  
        } 
	}
	/**
	 * 创建数据库连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		//return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","testuser","sasa");
		  return DriverManager.getConnection(  
          		Property.getProperty("datasource.url"), Property.getProperty("datasource.username"), Property.getProperty("datasource.password"));// 创建数据连接
	}
      
    public static boolean execute(String sql) throws SQLException{
    	boolean status=false;
    	Statement st=null;
    	Connection conn=null;
    		conn=getConnection();
    		st = (Statement) conn.createStatement(); //创建用于执行静态sql语句的Statement对象
    		status=st.execute(sql);
    		try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException sqle) {
				e.printStackTrace();
			}
    	}
    	return status;
    }
    /**
     * 测试
     * @param args
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException {
    	ApplicationContext context=new ClassPathXmlApplicationContext();
    	System.out.println(Jdbc.getConnection());
//    	Sheet sheet=new Sheet();
//    	sheet.setEnable("1");
//    	sheet.setNameEn("sys_sheet");
//    	sheet.setNameCh("表信息");
//    	Columns col1=new Columns();
//    	col1.setColumnEN("mobuleId");
//    	col1.setType("nvarchar");
//    	col1.setLength("255");
//    	col1.setIsNull("1");
//    	col1.setColumnCH("所属模块");
//    	Columns col2=new Columns();
//    	col2.setColumnEN("nameEn");
//    	col2.setType("nvarchar");
//    	col2.setLength("255");
//    	col2.setColumnCH("英文表名");
//    	Columns col3=new Columns();
//    	col3.setColumnEN("nameCh");
//    	col3.setType("nvarchar");
//    	col3.setLength("255");
//    	col3.setColumnCH("中文名称");
//    	Columns col4=new Columns();
//    	col4.setColumnEN("enable");
//    	col4.setType("nvarchar");
//    	col4.setLength("255");
//    	col4.setColumnCH("是否启用");
//    	sheet.setColumns(col1);
//    	sheet.setColumns(col2);
//    	sheet.setColumns(col3);
//    	sheet.setColumns(col4);
//    	String sql=null;
//		try {
//			sql = Tools.createTable(sheet);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	System.out.println(sql);
//    	String desc=Tools.getAddDesc(sheet);
//    	System.out.println(desc);
    	
	}
    
}  