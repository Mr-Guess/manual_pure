package com.ay.framework.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.core.utils.DBConnectionUtil;

/** 
 * @Description 
 * @date 2012-12-8
 * @author WangXin
 */
public class DBUtil {
	private static Logger logger = LoggerFactory.getLogger(DBUtil.class);
	
	public static List<Map> queryForList(String sql,Object[] param) {
		List<Map> list = new ArrayList<Map>();
		List<String> columnNames = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBConnectionUtil.getInstance().getConnection();
			ps = null;
			rs = null;
			ps = connection.prepareStatement(sql);
			if(param != null) {
				for (int i = 0; i < param.length; i++) {
					ps.setObject(i+1, param[i]);
				}
			}
			logger.info(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsd = rs.getMetaData();
			for (int i = 0; i < rsd.getColumnCount(); i++) {
				columnNames.add(rsd.getColumnName(i+1));
			}
			while(rs.next()){
				Map map = new HashMap();
				for (String str : columnNames) {
					map.put(str, rs.getObject(str));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} finally {
			DBConnectionUtil.getInstance().close(connection, ps, rs);
		}
		return list;
	}
}

