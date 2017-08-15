package com.ay.jfds.dev.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.core.dao.BaseDaoSupport;
import com.ay.framework.exception.POIException;
import com.ay.framework.util.DateUtil;
import com.ay.framework.util.StringUtil;

/**
 * @Description
 * @date 2012-11-6
 * @author WangXin
 */
public class DataHandlerDao extends BaseDaoSupport {
	private static Logger logger = LoggerFactory.getLogger(DataHandlerDao.class);
	@Override
	public String getEntityName() {
		return null;
	}
	/**
	 * 从sql语句中获取excel文件的数据源
	 * @param query	查询语句
	 * @return
	 * @return List<String[]>
	 */
	public List<String[]> getExcelData(String query) {
		List<String[]> list = new ArrayList<String[]>();
		Connection con = null;
		try {
			con = getSqlMapClientTemplate().getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(query);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			String[] heads = new String[columnCount];
			for (int column = 1; column <= columnCount; column++) {
				heads[column - 1] = metaData.getColumnName(column);
			}
			list.add(heads);
			while (resultSet.next()) {
				String[] cols = new String[columnCount];
				for (int column = 1; column <= columnCount; column++) {
					cols[column - 1] = resultSet.getString(column);
				}
				list.add(cols);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return list;
	}
	/**
	 * 插入导入的excel文件数据
	 * @param insertSql	例：insert into sys_user(id,status,created,create_time,updated,update_time,name)
	 * values(?,?,?,?,?,?,?)前6列是定死的~~ 
	 * @param list	第一行为head 忽略
	 * @return
	 * @throws SQLException
	 * @return int[]
	 * @throws POIException 
	 */
	public int[] insertExcelDate(String insertSql, List<String[]> list)
			throws SQLException, POIException {
		int count = 0;
		for (char c : insertSql.toCharArray()) {
			if (c == '?')
				count++;
		}
		if (count < 7)
			throw new POIException("insert语句参数不足");
		if(list==null||list.size()<2)
			throw new POIException("Excel文件无数据");
		Connection con = null;
		try {
			con = getSqlMapClientTemplate().getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PreparedStatement ps = null;
		ps = con.prepareStatement(insertSql);
		String userId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id");
		System.out.println("size:"+list.size());
		System.out.println(list);
		for (int i = 1; i < list.size(); i++) {
			if(list.get(i)==null || (list.get(i)).length==0){
				continue;
			}
			if(count-6!=(list.get(i)).length)
				throw new POIException("Excel文件列数与导入的要求不符");;
			ps.setString(1, StringUtil.getUUID());
			ps.setString(2, "1");
			ps.setString(3, userId);
			ps.setDate(4, new Date(DateUtil.getDateTime().getTime()));
			ps.setString(5, userId);
			ps.setDate(6, new Date(DateUtil.getDateTime().getTime()));
			for (int j = 7; j < count + 1; j++) {
				ps.setString(j, list.get(i)[j-7]);
			}
			ps.addBatch();
		}
		int[] nums=ps.executeBatch();
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nums;

	}

}
