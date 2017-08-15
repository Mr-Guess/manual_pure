package com.ay.ibatistool.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBUtil {


		private static Connection con;

		/**
		 * 加载驱动
		 * 
		 * 使用的是 SQL2005 的驱动程序因此务必将2005的驱动架包引入或置于项目根目录下
		 * 
		 * @return
		 */

		public static void getConnection(String driverClassName, String url,
				String username, String password) {
			try {
				Class.forName(driverClassName);
				con = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 关闭连接的方法
		 * 
		 * @param rs
		 * @param pstm
		 * @param con
		 */
		public static void closeConnection() {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 获得表头
		 * 
		 * @param tableName
		 * @return
		 */
		private static List<String[]>getMetaData(String tableName) {
			PreparedStatement pst=null;
			List<String[]> list = new ArrayList<String[]>();
				try {
				 pst = con.prepareStatement("select * from "
						+ tableName + " where 1=2");
				 ResultSetMetaData rsd=pst.executeQuery().getMetaData();
				 for (int i = 0; i < rsd.getColumnCount(); i++) {
					list.add(new String[]{rsd.getColumnClassName(i+1),rsd.getColumnName(i+1)});
					
				 }
				return list;
			} catch (SQLException e) {
				throw new RuntimeException("can't select table " + tableName);
			}finally{
				try {
					pst.close(); pst=null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}

		}
		/**
		 * 取得当前用户下的所有表
		 * @return
		 */
		public static List<String> getAllTable(){
			PreparedStatement pst=null;
			ResultSet rs =null;
			try {
				String sql = "select table_name from user_all_tables";

				 pst = con.prepareStatement(sql);
				 rs = pst.executeQuery();
				
				List<String> list = new ArrayList<String>();
				while(rs.next()){
				list.add(rs.getString(1));
				}
				return list;
			} catch (SQLException e) {
				throw new RuntimeException("can't select all tables ");
			}finally{
				try {
					pst.close();pst=null;
					rs.close();rs=null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
		/**
		 * 创建序列
		 * @param sequenceName
		 */
		public static void createSequence(String sequenceName)
		{
			PreparedStatement pst=null;
			 String sql = "create sequence "+sequenceName+" minvalue 1 maxvalue 9999999999999999 increment by 1 start with 1 cache 10 noorder nocycle";   
			 try {
			  pst = con.prepareStatement(sql);   
			  pst.execute(); 
			 } catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("can't create sequenceName " + sequenceName);
				} finally{
					try {
						pst.close();pst=null;
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
		}
		/**
		 * 取得表的描述
		 * @param owner
		 * @param tableName
		 * @return
		 */
		public static String getTableDescs(String owner, String tableName) {
			PreparedStatement pst=null;
			ResultSet rs =null;
			try {
				String sql = "SELECT nvl(comments,'" + tableName.toUpperCase()
						+ "')   FROM Dba_Tab_Comments" + "  where upper(owner)='"
						+ owner.toUpperCase() + "' and upper(table_name)='"
						+ tableName.toUpperCase() + "'";

				 pst = con.prepareStatement(sql);
				 rs = pst.executeQuery();
				 if(rs.next())
				return rs.getString(1);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("can't select comments " + tableName);
			} finally{
				try {
					pst.close();pst=null;
					rs.close();rs=null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			return null;
		}
		/**
		 * 取得字段的描述
		 * @param tableName
		 * @param colName
		 * @return
		 */
		private static String getColComments(String tableName, String colName) {
			PreparedStatement pst=null;
			ResultSet rs =null;
			try {
				String sql = "select   nvl(comments,'" + colName.toUpperCase()
						+ "')   from   user_col_comments   where  upper(table_name)='"
						+ tableName.toUpperCase() + "'  and  upper(column_name)='"+colName.toUpperCase()+"' ";
				 pst = con.prepareStatement(sql);
				 rs = pst.executeQuery();
				if(rs.next())
				return rs.getString(1);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("can't select comments " + tableName);
			}finally{
				try {
					pst.close();pst=null;
					rs.close();rs=null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			return null;
		}
		/**
		 * 取得所有字段
		 * @param tableName
		 * @return
		 */
		public static LinkedList<String[]> getFeild(String tableName) {
				LinkedList<String[]> list = new LinkedList<String[]>();
				//取得字段注释
				List<String[]> metaData = getMetaData(tableName);
				for (int i = 0; i < metaData.size(); i++) {
					String[] fi = new String[4];
					String filed[] =metaData.get(i);
					fi[0] = filed[0];
					fi[1] = filed[1].toLowerCase();
					fi[2] = "sdsdfsd";
					fi[3] = filed[1];
					list.add(fi);
				}
				return list;


		}

		public static void main(String[] args) {

		}
	}