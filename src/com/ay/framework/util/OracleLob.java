package com.ay.framework.util;  
  
import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.sql.Blob;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.util.Properties;  
  
import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  

import com.ay.jfds.icon.pojo.SysIcon;
  
public class OracleLob {  
  
    private static final Log log = LogFactory.getLog(OracleLob.class);  
  
    private static final String id = "3";  
  
  
    private static final Properties props = new Properties();  
  
    static {  
            try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}  
    }  
  
    public static Connection getConnection() throws SQLException {  
    	return DriverManager.getConnection("jdbc:oracle:thin:@10.0.0.246:1521:orcl","ayjava_sx","sasa");  
    }  
  
    /** 
     * 保存图片到oracle库中 
     *  
     * @throws SQLException 
     * @throws IOException 
     */  
    public  void save_blob(SysIcon sysIcon) throws SQLException, IOException {  
        PreparedStatement preparedStatement = null;  
        Connection connection = null;  
        ResultSet resultSet = null;  
        try {  
  
            /* 先保存空blob */  
            connection = getConnection();  
            connection.setAutoCommit(false);// 必须设置为false，即开启事务！  
  
            preparedStatement = connection  
                    .prepareStatement("INSERT INTO sys_icon(icon_no,name,icon_desc,type,status,icon) VALUES(?,?,?,?,?,?)");  
  
            preparedStatement.setString(1, sysIcon.getIconNo()); 
            preparedStatement.setString(2, sysIcon.getName());
            preparedStatement.setString(3, sysIcon.getIconDesc());
            preparedStatement.setString(4, sysIcon.getType());
            preparedStatement.setString(5, sysIcon.getStatus());
            
            // preparedStatement.setBlob(2, oracle.sql.BLOB.getEmptyBLOB());//getEmptyBLOB()是最新
            preparedStatement.setBlob(6, oracle.sql.BLOB.empty_lob());// 先存一个空blob,此方法在驱动jar包中  
  
            // log.info(preparedStatement.executeUpdate() > 0 ? "保存空blob成功" : "保存空blob失败");  
  
            if (preparedStatement.executeUpdate() > 0) {  
                log.info("保存空blob成功，方法继续！");  
            } else {  
                log.info("保存空blob失败，方法返回！");  
                connection.rollback();  
                return;  
            }  
  
            /* 正式保存文件 */  
            preparedStatement = connection.prepareStatement("SELECT icon FROM sys_icon WHERE ICON_NO = ? FOR UPDATE");// 须加for update，锁定该行，直至该行被修改完毕，保证不产生并发冲突  
            preparedStatement.setString(1, sysIcon.getIconNo());  
  
            resultSet = preparedStatement.executeQuery();  
  
            while (true == resultSet.next()) {  
                BufferedInputStream bufferedInputStream = null;  
                try {  
                    bufferedInputStream = new BufferedInputStream(  
                            new FileInputStream(  
                                    byte2File(sysIcon.getIcon(), "E:\\images", sysIcon.getName())));// 需要保存的文件路径  
                    Blob image = resultSet.getBlob("icon");  
  
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
                    .prepareStatement("SELECT image FROM testblob WHERE id = ?");  
  
            preparedStatement.setString(1, id);  
  
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
    
    public static File byte2File(byte[] buf, String filePath, String fileName)  
    {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try  
        {  
            File dir = new File(filePath);  
            if (!dir.exists() && dir.isDirectory())  
            {  
                dir.mkdirs();  
            }  
            file = new File(filePath + File.separator + fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(buf);  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            if (bos != null)  
            {  
                try  
                {  
                    bos.close();  
                }  
                catch (IOException e)  
                {  
                    e.printStackTrace();  
                }  
            }  
            if (fos != null)  
            {  
                try  
                {  
                    fos.close();  
                }  
                catch (IOException e)  
                {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return file;
    }  
  
    public static void main(String[] args) throws SQLException, IOException {  
       
        System.out.println(new OracleLob().getConnection());  
    }  
}  