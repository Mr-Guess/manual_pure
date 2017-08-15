package com.ay.jfds.create.tools;

import com.ay.jfds.create.pojo.Columns;
import com.ay.jfds.create.pojo.Sheet;
/**
 * 创建表生成Sql语句 工具类 支持 sql server
 * @author Elivs
 *
 */
public class Tools {
	/**
	 * 创建表语句
	 * @param sheet 表单
	 * @return sql
	 * @throws Exception 
	 */
	public static String createTable(Sheet sheet) throws Exception{
		StringBuffer sql=new StringBuffer();
		if(sheet==null)return null;
		sql.append("create table ");
		sql.append(sheet.getNameEn().toUpperCase());//表名
		sql.append("( \n");
		for(Columns col:sheet.getColumns()){
			sql.append(col.getColumnEN()+" ");
			sql.append(getType(col.getType(),col.getLength()));
			sql.append(isNotNull(col.getIsNull()));
			sql.append(",\n");
		}
		sql.append(defaultSql());
		sql.append(")");
		return sql.toString();
	}
	/**
	 * 默认列
	 * @return
	 */
	private static String defaultSql(){
		StringBuffer sql=new StringBuffer();
		sql.append("ID VARCHAR(50) NOT NULL PRIMARY KEY ,\n");
		sql.append("STATUS CHAR(1) NULL DEFAULT ((1)) ,\n");
		sql.append("CREATED VARCHAR(32) NULL , \n");
		sql.append("UPDATED VARCHAR(32) NULL , \n");
		sql.append("CREATE_TIME DATETIME NULL ,\n");
		sql.append("UPDATE_TIME DATETIME NULL ,\n");
		sql.append("ORG NVARCHAR(MAX) NULL,\n");
		sql.append("ORG_TREE NVARCHAR(MAX) NULL");
		return sql.toString();
	}
	
	/**使用系统函数sp_addextendedproperty
	 * 为每个列名添加说明 和每个表添加说明
	 * @return
	 */
	public static String getAddDesc(Sheet sheet){
		StringBuffer sql=new StringBuffer();
		
		for(Columns col:sheet.getColumns()){
			sql.append("exec sp_addextendedproperty N'MS_Description',");
			sql.append("N'");
			sql.append(col.getColumnCH());//说明
			sql.append("', N'user', N'dbo', N'table', N'");
			sql.append(sheet.getNameEn());//表名
			sql.append("', N'column', N'");
			sql.append(col.getColumnEN());
			sql.append("'\n");
		}
		return sql.toString();
	}
	/**
	 * 根据当前类型
	 * @param type 类型
	 * @param length	长度
	 * @return
	 * @throws Exception 
	 */
	private static String getType(String type,String length) throws Exception{
		try {
			Integer.valueOf(length);
			if(type.equals("nvarchar")){
				return "nvarchar("+length+")";
			}
			if(type.equals("varbinary")){
				return "varbinary(MAX)";
			}
			if(type.equals("nvarcharMax")){
				return "text";
			}
			if(type.equals("dateTime")){
				return "datetime";
			}
			if(type.equals("char")){
				return "char("+length+")";
			}
			return null;
		} catch (NumberFormatException e) {
			throw new Exception("长度只能是数字");
		}
	}
	/**
	 * 删除表单
	 * @return
	 */
	public static String delSheet(Sheet sheet){
		StringBuffer sql=new StringBuffer();
		sql.append("drop table ");
		sql.append(sheet.getNameEn());
		return sql.toString();
	}
	
	/**
	 * 修改一列
	 * @param sheet
	 * @return
	 */
	public static String updateColumns(Columns columns,String oldName){
		StringBuffer sql=new StringBuffer();
		sql.append("EXEC sp_rename '");
		sql.append(columns.getSheetName()+".[");
		sql.append(oldName+"]','");
		sql.append(columns.getColumnEN()+"',");
		sql.append("'COLUMN'");
		return sql.toString();
	}
	
	/**
	 * 删除一列
	 * @return
	 */
	public static String delColumns(Columns columns){
		StringBuffer sql=new StringBuffer();
		sql.append("alter table ");
		sql.append(columns.getSheetName());
		sql.append(" drop column ");
		sql.append(columns.getColumnEN());
		return sql.toString();
	}
	/**
	 * 添加一列
	 * @param columns
	 * @return
	 * @throws Exception 
	 */
	public static String addColumns(Columns columns) throws Exception{
		StringBuffer sql=new StringBuffer();
		sql.append("ALTER TABLE ");
		sql.append(columns.getSheetName());
		sql.append(" ADD ");
		sql.append(columns.getColumnEN());
		sql.append(" "+getType(columns.getType(), columns.getLength()));
		sql.append(" "+isNotNull(columns.getIsNull()));
		return sql.toString();
	}
	
	/**
	 * 是否为空
	 * @param isNotNull
	 * @return
	 */
	public static String isNotNull(String isNotNull){
		if(isNotNull.equals("0")){
			return "not null";
		}
		return "NULL";
	}
	/**
	 * 修该列类型
	 * @param columns
	 * @return
	 * @throws Exception 
	 */
	public static String updateColumnsType(Columns columns) throws Exception{
		StringBuffer sql=new StringBuffer();
		sql.append("ALTER TABLE ");
		sql.append(columns.getSheetName());
		sql.append(" ALTER COLUMN ");
		sql.append(columns.getColumnEN());
		sql.append(" "+getType(columns.getType(), columns.getLength()));
		return sql.toString();
	}
	
	public static String updateTable(Sheet sheet,String oldSheetName){
		StringBuffer sql=new StringBuffer();
		sql.append("EXEC sp_rename ");
		sql.append(oldSheetName);
		sql.append("', '");
		sql.append(sheet.getNameEn());
		sql.append("'");
		return sql.toString();
	}
	/**
	 * 处理表名
	 * @return
	 */
	public static String toUpperCaseStr(String nameEn){
		StringBuffer newName=new StringBuffer("TB");
		if(nameEn!=null){
			String[] names=nameEn.split("(?<!^)(?=[A-Z])");
			for(String name:names){
				newName.append("_").append(name.toUpperCase());
			}
		}
		return newName.toString();
	}
}
