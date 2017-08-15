package com.ay.jfds.dev.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;


import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.dev.pojo.DevStatistics;

/**
 * @Description 
 * @date 2012-10-26
 * @author WangXin
 */
public class DevStatisticsDao extends BaseDao<DevStatistics> {
	private static Logger logger = LoggerFactory.getLogger(DevStatisticsDao.class);
	@Override
	public String getEntityName() {
		return "devStatistics";
	}
	
	public CategoryDataset getCategoryDataset(String sql,boolean transposed) throws SQLException{
		Connection con=null;
		try {
			con=getSqlMapClientTemplate().getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return executeCategoryQuery(con, sql,transposed);
	}
	public PieDataset getPieDataset(String sql) throws SQLException{
		Connection con=null;
		try {
			con=getSqlMapClientTemplate().getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return executePieQuery(con, sql);
	}
	
	private CategoryDataset executeCategoryQuery(Connection con, String query,boolean transposed) throws SQLException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();

            int columnCount = metaData.getColumnCount();

            if (columnCount < 2) {
            	SQLException e =new SQLException("传入的数据集少于两列");
            	logger.error(e.getMessage(),e);
                throw e;
            }
            while (resultSet.next()) {
                Comparable rowKey = resultSet.getString(1);
                for (int column = 2; column <= columnCount; column++) {
                    Comparable columnKey = metaData.getColumnName(column);
                    int columnType = metaData.getColumnType(column);
                    switch (columnType) {
                        case Types.TINYINT:
                        case Types.SMALLINT:
                        case Types.INTEGER:
                        case Types.BIGINT:
                        case Types.FLOAT:
                        case Types.DOUBLE:
                        case Types.DECIMAL:
                        case Types.NUMERIC:
                        case Types.REAL: {
                            Number value = (Number) resultSet.getObject(column);
                            if(!transposed)
                            	dataset.addValue(value, rowKey, columnKey);
                            else
                            	dataset.addValue(value,columnKey, rowKey);
                            break;
                        }
                        case Types.DATE:
                        case Types.TIME:
                        case Types.TIMESTAMP: {
                            Date date = (Date) resultSet.getObject(column);
                            Number value = new Long(date.getTime());
                            if(!transposed)
                            	dataset.addValue(value, rowKey, columnKey);
                            else
                            	dataset.addValue(value,columnKey, rowKey);
                            break;
                        }
                        case Types.CHAR:
                        case Types.VARCHAR:
                        case Types.LONGVARCHAR: {
                            String string
                                = (String) resultSet.getObject(column);
                            try {
                                Number value = Double.valueOf(string);
                                if(!transposed)
                                	dataset.addValue(value, rowKey, columnKey);
                                else
                                	dataset.addValue(value,columnKey, rowKey);
                            }
                            catch (NumberFormatException e) {
                            	e.printStackTrace();
                            }
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
            return dataset;
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (Exception e) {
                	e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (Exception e) {
                	e.printStackTrace();
                }
            }
            if(con != null)
            	con.close();
        }
    }
	
	public PieDataset executePieQuery(Connection con, String query) throws SQLException {
		DefaultPieDataset dataset =new DefaultPieDataset();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();

            int columnCount = metaData.getColumnCount();
            if (columnCount != 2) {
            	SQLException e =new SQLException("饼图传入的数据集必须为两列");
            	logger.error(e.getMessage(),e);
            	throw e;
            }

            int columnType = metaData.getColumnType(2);
            double value;
            while (resultSet.next()) {
                Comparable key = resultSet.getString(1);
                key = key == null?"":key;
                switch (columnType) {
                    case Types.NUMERIC:
                    case Types.REAL:
                    case Types.INTEGER:
                    case Types.DOUBLE:
                    case Types.FLOAT:
                    case Types.DECIMAL:
                    case Types.BIGINT:
                        value = resultSet.getDouble(2);
                        dataset.setValue(key, value);
                        break;

                    case Types.DATE:
                    case Types.TIME:
                    case Types.TIMESTAMP:
                        Timestamp date = resultSet.getTimestamp(2);
                        value = date.getTime();
                        dataset.setValue(key, value);
                        break;
                    default:
                        break;
                }
            }
            return dataset;
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (Exception e) {
                	e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (Exception e) {
                	e.printStackTrace();
                }
            }
            if(con != null)
            	con.close();
        }
    }

	@Override
	public String getTableName() {
		return "dev_statistics";
	}
}

