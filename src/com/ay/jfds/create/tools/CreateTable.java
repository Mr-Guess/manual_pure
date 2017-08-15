package com.ay.jfds.create.tools;

import java.sql.SQLException;

import com.ay.jfds.create.pojo.Columns;
import com.ay.jfds.create.pojo.Sheet;
/**
 * 创建表类
 * @author Elivs
 *
 */
public class CreateTable {
	/**
	 * 新建表
	 * @param sheet 表信息
	 * @throws SQLException 结果
	 */
	public static void createTable(Sheet sheet) throws Exception{
		String sql=Tools.createTable(sheet);
		Jdbc.execute(sql);
		CreateTable.createDesc(sheet);
	}
	/**
	 * 创建说明
	 * @param sheet 表
	 * @throws SQLException 
	 */
	private static void createDesc(Sheet sheet) throws SQLException{
		String sql=Tools.getAddDesc(sheet);
		Jdbc.execute(sql);
	}
	/**
	 * 删除表
	 * @param sheet 表信息
	 * @throws SQLException 结果
	 */
	public static void deleteTable(Sheet sheet) throws SQLException{
		String sql=Tools.delSheet(sheet);
		Jdbc.execute(sql);
	}
	/**
	 * 删除列名
	 * @param columns 列信息
	 * @throws SQLException 结果
	 */
	public static void deleteColumns(Columns columns) throws SQLException{
		String sql=Tools.delColumns(columns);
		Jdbc.execute(sql);
	}
	/**
	 * 添加一列
	 * @param columns 列信息
	 * @throws SQLException 结果
	 */
	public static void addColumns(Columns columns) throws Exception{
		String sql=Tools.addColumns(columns);
		Jdbc.execute(sql);
	}
	/**
	 * 修改一列
	 * @param columns 新列名
	 * @param oldName	原列名
	 * @throws SQLException 结果
	 */
	public static void updateColumns(Columns columns,String oldName) throws Exception{
		//如果当前列之修该了类型 就不需要修该列名
		if(columns.getColumnEN().equals(oldName)){
			Jdbc.execute(Tools.updateColumnsType(columns));
		}else{
			String sql=Tools.updateColumns(columns, oldName);
			Jdbc.execute(sql);
			Jdbc.execute(Tools.updateColumnsType(columns));
		}
	}
	
	public static void updateTable(Sheet sheet,String oldSheetName) throws SQLException{
		if(sheet.getNameEn().equals(oldSheetName))return;
		String sql=Tools.updateTable(sheet, oldSheetName);
		Jdbc.execute(sql);
	}
}
