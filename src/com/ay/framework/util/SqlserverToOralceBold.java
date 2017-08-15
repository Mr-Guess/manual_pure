package com.ay.framework.util;

import java.io.*;
import java.sql.*;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 此类是sqlserver 二进制导入到oracle数据库
 * @author Elivs
 *
 */
public class SqlserverToOralceBold {
	  
	private static final String id = "3";  
  
  
    static {  
        try {  
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("加载驱动成功");
        } catch (ClassNotFoundException e) {  
        	e.printStackTrace();
        } 
    }  
  
    public static Connection getConnection() throws SQLException {  
    	System.out.println("连接成功sqlserver");
        return DriverManager.getConnection("jdbc:sqlserver://10.0.0.50:1435;DatabaseName=ayjava_sx_base",  
                "sa", "sasa");  
    }  
  
    /** 
     * 保存图片到oracle库中 
     *  
     * @throws SQLException 
     * @throws IOException 
     */  
    public void save_blob() throws SQLException, IOException {  
        PreparedStatement preparedStatement = null;  
        Connection connection = null;  
        ResultSet resultSet = null;  
        try {  
  
            /* 先保存空blob */  
            connection = getConnection();  
            connection.setAutoCommit(false);// 必须设置为false，即开启事务！  
  
            preparedStatement = connection  
                    .prepareStatement("INSERT INTO testblob(id,image) VALUES(?,?)");  
  
            preparedStatement.setString(1, id);  
            // preparedStatement.setBlob(2, oracle.sql.BLOB.empty_lob());//empty_lob()已过期  
            preparedStatement.setBlob(2, oracle.sql.BLOB.empty_lob());// 先存一个空blob,此方法在驱动jar包中  
  
            // log.info(preparedStatement.executeUpdate() > 0 ? "保存空blob成功" : "保存空blob失败");  
  
            if (preparedStatement.executeUpdate() > 0) {  
                System.out.println("保存空blob成功，方法继续！");  
            } else {  
            	 System.out.println("保存空blob失败，方法返回！");  
                connection.rollback();  
                return;  
            }  
  
            /* 正式保存文件 */  
            preparedStatement = connection.prepareStatement("SELECT image FROM testblob WHERE id = ? FOR UPDATE");// 须加for update，锁定该行，直至该行被修改完毕，保证不产生并发冲突  
            preparedStatement.setString(1, id);  
  
            resultSet = preparedStatement.executeQuery();  
  
            while (true == resultSet.next()) {  
                BufferedInputStream bufferedInputStream = null;  
                try {  
                    bufferedInputStream = new BufferedInputStream(  
                            new FileInputStream(  
                                    "src/main/java/cn/com/songjy/test/db/Jellyfish.jpg"));// 需要保存的文件路径  
  
                    Blob image = resultSet.getBlob("image");  
  
                    byte[] buffer = new byte[1024];// 每次读取/写入1k  
  
                    for (int len = 0; (len = bufferedInputStream.read(buffer)) > 0;) {  
                        image.setBytes(image.length() + 1, buffer, 0, len);// Blob第一个字节（byte）的位置是从1开始，所以需要+1  
                    }  
                } finally {  
                    if (null != bufferedInputStream)  
                        bufferedInputStream.close();  
                }  
  
            }  
  
            connection.commit();// 开启了事物，必须手动提交  
  
        } finally {  
  
            if (null != preparedStatement)  
                preparedStatement.close();  
  
            if (null != connection)  
                connection.close();  
        }  
    }  
  
    /** 
     * 从oracle库中获取图片 
     *  
     * @throws SQLException 
     * @throws IOException 
     */  
    public void query_blog() throws SQLException, IOException {  
        PreparedStatement preparedStatement = null;  
        Connection connection = null;  
        ResultSet resultSet = null;  
  
        try {  
            connection = getConnection();  
            preparedStatement = connection  
                    .prepareStatement("SELECT image FROM sys_icon");  
  
  
            resultSet = preparedStatement.executeQuery();  
  
            while (true == resultSet.next()) {  
                BufferedInputStream bufferedInputStream = null;  
                BufferedOutputStream bufferedOutputStream = null;  
                try {  
                    bufferedInputStream = new BufferedInputStream(  
                            resultSet.getBinaryStream("image"));  
  
                    bufferedOutputStream = new BufferedOutputStream(  
                            new FileOutputStream(  
                                    "src/main/java/cn/com/songjy/test/db/1.jpg"));// 读取后的保存图片路径  
  
                    byte[] buffer = new byte[1024];  
  
                    for (int len = 0; (len = bufferedInputStream.read(buffer)) > 0;) {  
                        bufferedOutputStream.write(buffer, 0, len);  
                        // bufferedOutputStream.flush();//关闭流时会自动刷新  
                    }  
                } finally {  
                    if (null != bufferedOutputStream)  
                        bufferedOutputStream.close();  
                    if (null != bufferedInputStream)  
                        bufferedInputStream.close();  
                }  
            }  
        } finally {  
  
            if (null != resultSet)  
                resultSet.close();  
  
            if (null != preparedStatement)  
                preparedStatement.close();  
  
            if (null != connection)  
                connection.close();  
  
        }  
    }  
  
    public static void main(String[] args) throws SQLException, IOException {  
    	ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
    	System.out.println(context);
    	
    }  
}  
