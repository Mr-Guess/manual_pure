package com.ay.framework.core.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.dao.DataAccessException;

import com.ay.framework.core.pojo.BasePojo;
import com.ay.framework.shiro.strategy.StrategyChain;
import com.ay.framework.shiro.strategy.StrategyParam;
import com.ay.framework.util.DateUtil;
import com.ay.framework.util.StringUtil;
import com.ay.framework.util.collenction.ListUtil;
import com.ay.jfds.icon.pojo.SysIcon;
import com.ay.jfds.sys.dto.UserDTO;
import com.ay.jfds.sys.pojo.SysRoleData;
import com.ay.jfds.sys.pojo.User;

/**
 * 该抽象类封装了改造后的分页功能，所有的实现类都必须继承该类
 * 
 * @DateTime: 2012-9-10
 * @author lushigai
 * @version 1.0 修改人：lijie 修改时间：2013-1-21 修改内容：批量删除方法重写
 */
@SuppressWarnings("all")
public abstract class BaseDao<T extends BasePojo> extends BaseDaoSupport {
	/**
	 * 根据主键更新实体
	 * 
	 * @param entity
	 *            entity
	 * @return 更新的记录数是否为1
	 */
	public boolean update(T entity) {
		String userId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id");
		entity.setUpdateTime(DateUtil.getDateTime());
		entity.setUpdated(userId);
		int rows = getSqlMapClientTemplate().update(
				getEntityName() + ".update", entity);
		return rows == 1;
	}

	/**
	 * 根据主键删除
	 * 
	 * @param id
	 *            id
	 * @return 删除的记录数是否为1
	 */
	public boolean delete(Serializable id) {
		int rows = getSqlMapClientTemplate().delete(
				getEntityName() + ".delete", id);
		return rows == 1;
	}

	/**
	 * 根据id查找所有
	 */

	@SuppressWarnings("unchecked")
	public List<T> findPeop(List ids) {
		return (List<T>) getSqlMapClientTemplate().queryForList(
				getEntityName() + ".findPeop", ids);
	}

	/**
	 * 根据主键批量删除 lijie 2013-1-21 利用ibatis配置文件的iterate
	 * 
	 * @param @param ids
	 * @param @return   
	 * @return boolean   
	 * @throws
	 */
	public boolean deleteByIds(String[] ids) {
		List list = ListUtil.fromArray(ids);
		int rows = getSqlMapClientTemplate().delete(
				getEntityName() + ".deleteList", list);
		return rows >= 1;
		// StringBuilder sb = new StringBuilder();
		// for (String id : ids) {
		// sb.append("'").append(id).append("',");
		// }
		// if (sb.lastIndexOf(",") != -1) {
		// sb.deleteCharAt(sb.lastIndexOf(","));
		// }
		// int rows = getSqlMapClientTemplate().delete(
		// getEntityName() + ".deleteList", sb.toString());
		// return rows >= 1;
	}

	/**
	 * 根据主键批量删除自定义
	 * 
	 * @param @param ids
	 * @param @return   
	 * @return boolean   
	 * @throws
	 */
	public boolean deleteSmsByIds(String[] ids) {
		List list = ListUtil.fromArray(ids);
		int rows = getSqlMapClientTemplate().delete(
				getEntityName() + ".rollSmsList", list);
		return rows >= 1;
	}

	/**
	 * 插入记录，返回设置了id的对象
	 * 
	 * @param entity
	 *            entity
	 * @return T
	 */
	public T insert(T entity) {
		String userId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id");
		UserDTO user = (UserDTO) SecurityUtils.getSubject().getSession().getAttribute("user");
		if (entity.getId() == null || entity.getId().trim().equals("")) {
			String id = generateId();
			entity.setId(id);
		}
		if (null == entity.getStatus() || entity.getStatus().trim().equals("")) {
			entity.setStatus("1");
		}
		if (null == entity.getCreated()
				|| entity.getCreated().trim().equals("")) {
			entity.setCreated(userId==null?entity.getId():userId);//设置创建者如果user_id为空创建者就是当前用户
		}
		if (null == entity.getCreateTime()) {
			entity.setCreateTime(DateUtil.getDateTime());
		}
		if (null == entity.getUpdated()
				|| entity.getUpdated().trim().equals("")) {
			entity.setUpdated(userId);
		}
		if (null == entity.getUpdateTime()) {
			entity.setUpdateTime(DateUtil.getDateTime());
		}
		//给BasePojo子类加上属性
		if(entity!=null&&user!=null && entity.getClass()!=User.class) {
			BasePojo basePojo = (BasePojo) entity;
			basePojo.setOrg(user.getDeptId());
			basePojo.setOrgTree(user.getOrgTree() + "-" + user.getId());		}
		try {
			Object object=getSqlMapClientTemplate().insert(getEntityName() + ".insert", entity);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * 查找所有记录
	 * 
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) getSqlMapClientTemplate().queryForList(
				getEntityName() + ".findAll");
	}

	/**
	 * 查找所有记录
	 * 
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAlls(Map map) {
		return (List<T>) getSqlMapClientTemplate().queryForList(
				getEntityName() + ".findAll", map);
	}

	/**
	 * 查询带符合条件的所有记录.
	 * 
	 * @param map
	 *            the map
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<T> query(Map map) {
		return (List<T>) getSqlMapClientTemplate().queryForList(
				getEntityName() + ".find", map);
	}

	/**
	 * 
	 * @param map
	 *            查询分页的参数
	 * @param from
	 *            一页显示多少
	 * @param prePageNum
	 *            页码
	 * @return list 查询出来的list对象
	 */
	@SuppressWarnings("unchecked")
	public List<T> pageQuery(Map map, int from, int prePageNum) {
		String tableName = getTableName();
		StrategyParam param = new StrategyParam(map, tableName);
		StrategyChain.newInstance().doStrategy(param);
		return (List<T>) getSqlMapClientTemplate().queryForList(
				getEntityName() + ".find", map, from, prePageNum);
	}

	/**
	 * 查找状态为0的
	 * 
	 * @param map
	 * @param from
	 * @param prePageNum
	 * @return
	 */
	public List<T> pageQuerys(Map map, int from, int prePageNum) {
		String tableName = getTableName();
		Map<String, SysRoleData> roleMap = (Map<String, SysRoleData>) SecurityUtils
				.getSubject().getSession().getAttribute("user_data_role");
		SysRoleData userRoleData = roleMap.get(tableName);
		// 获得用户的 类型 ID 部门ID
		String userType = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("usertype");
		String userId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id");
		String userDeptId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_dept_id");
		// 当传入进来的查询map为空时，应该new出一个对象出来
		if (map == null) {
			map = new HashMap();
		}

		StringBuilder roleDataUserFilter = new StringBuilder();
		StringBuilder roleDataDeptFilter = new StringBuilder();
		if (userRoleData != null) {
			if (userRoleData.getDeptIds() != null) {
				if (userType != null && userType.equals("3")) {
					if (userRoleData.getDeptIds() != null) {
						roleDataDeptFilter.append(",'").append(userDeptId)
								.append("'");
					}
				}
			}
		}

		// 任何一个用户都可以看到自己添加的数据
		if (userRoleData != null && userRoleData.getUserIds() != null) {
			if (!userRoleData.getUserIds().equals("''")) {
				roleDataUserFilter.append(userRoleData.getUserIds());
			}
		}
		roleDataUserFilter.append(",'").append(userId).append("'");
		if (roleDataUserFilter.indexOf(",") == 0) {
			roleDataUserFilter.deleteCharAt(0);
		}
		if (roleDataDeptFilter.indexOf(",") == 0) {
			roleDataDeptFilter.deleteCharAt(0);
		}
		if (roleDataUserFilter.equals("") || roleDataUserFilter.equals("''")) {
			map.put("created", null);
		} else {
			map.put("created", roleDataUserFilter.toString());
		}
		if (roleDataDeptFilter.equals("") || roleDataDeptFilter.equals("''")) {
			map.put("deptId", null);
		} else {
			map.put("deptId", roleDataDeptFilter.toString());
		}

		// 如果这里是超级用户，那么就可以看到所有的数据
		if (userType != null && userType.equals("0")) {
			map.put("created", null);
			map.put("deptId", null);
		}
		return (List<T>) getSqlMapClientTemplate().queryForList(
				getEntityName() + ".reFind", map, from, prePageNum);
	}

	/**
	 * @param map
	 *            map
	 * @return int
	 */
	public int count(Map map) {
		String tableName = getTableName();
		StrategyParam param = new StrategyParam(map, tableName);
		StrategyChain.newInstance().doStrategy(param);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".count", map);
	}

	/**
	 * @param id
	 *            id
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T getById(Serializable id) {
		return (T) getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".getById", id);
	}

	/**
	 * @param map
	 *            map
	 * @return int
	 */
	public int validate(Map map) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				this.getEntityName() + ".validate", map);
	}

	/**
	 * 获取生成的主键标识
	 * 
	 * @return 主键标识
	 */
	public String generateId() {
		return StringUtil.getUUID();
		// return (Long)
		// getSqlMapClientTemplate().queryForObject(KEY_FETCH_STMT,
		// getEntityName());
		// HashMap m = new HashMap();
		// getSqlMapClientTemplate().queryForObject(KEY_FETCH_STMT, m);
		// return (Long) m.get("retid");
	}

	/**
	 * 根据应用版本id删除数据.
	 */
	public void deleteByAppId(Long appId) {
		this.getSqlMapClientTemplate().delete(
				getEntityName() + ".deleteByAppId", appId);
	}

	public String initData(Long appId) {
		String table = getEntityName();
		String sql = String.format(" select * from %s where app_id = %s ",
				table, appId);
		return this.initData(sql, table);
	}

	/**
	 * <pre>
	 * 
	 * 根据查询的结果集生成初始化SQL数据.
	 * 
	 * @param sql   查询SQL语句
	 * @param table 查询表名
	 * @return the  插入数据SQL
	 * </pre>
	 */
	public String initData(String sql, String table) {
		StringBuffer insertSql = new StringBuffer("");
		Connection con = null;
		Statement stat = null;
		ResultSet rest = null;
		ResultSetMetaData data = null;
		try {
			con = this.getSqlMapClient().getDataSource().getConnection();
			stat = con.createStatement();
			rest = stat.executeQuery(sql);
			data = rest.getMetaData();
			while (rest.next()) {
				insertSql.append("insert into " + table);
				insertSql.append(" (");
				String values = "";
				String columnName = "";
				for (int i = 1; i <= data.getColumnCount(); i++) {
					columnName += data.getColumnName(i) + ",";
					int type = data.getColumnType(i);
					Object obj = null;
					switch (type) {
					case java.sql.Types.DATE:
					case java.sql.Types.TIMESTAMP:
						obj = rest.getObject(i);
						if (null == obj) {
							values += null + ",";
						} else {
							String str = String.valueOf(obj).replace("'", "''");
							values += "'" + str + "'" + ",";
						}
						break;
					case java.sql.Types.BIGINT:
					case java.sql.Types.DOUBLE:
					case java.sql.Types.DECIMAL:
					case java.sql.Types.FLOAT:
					case java.sql.Types.INTEGER:
					case java.sql.Types.NUMERIC:
						values += String.valueOf(rest.getObject(i)) + ",";
						break;
					case java.sql.Types.CHAR:
					case java.sql.Types.NCHAR:
					case java.sql.Types.VARCHAR:
					case java.sql.Types.NVARCHAR:
						obj = rest.getObject(i);
						if (null == obj) {
							values += null + ",";
						} else {
							String str = String.valueOf(obj).replace("'", "''");
							values += "'" + str + "'" + ",";
						}
						break;
					case java.sql.Types.BOOLEAN:
						values += String.valueOf(rest.getObject(i)) + ",";
						break;
					case java.sql.Types.NULL:
						values += null + ",";
						break;
					default:
						throw new RuntimeException("无法处理的数据类型.");
					}
				}
				if (values.length() > 0) {
					columnName = columnName.substring(0,
							columnName.length() - 1);
					values = values.substring(0, values.length() - 1);
					values = values.replace("\r\n", " "); // 将换行转换为空格
				}
				insertSql.append(columnName);
				insertSql.append(")");
				insertSql.append(" values ");
				insertSql.append("(");
				insertSql.append(values);
				insertSql.append(");");
				insertSql.append("\n");
			}
		} catch (SQLException e) {
			System.out.println("生成初始化SQL数据出现异常!");
			e.printStackTrace();
		} finally {
			try {
				if (null != rest) {
					rest.close();
				}
				if (null != stat) {
					stat.close();
				}
				if (null != con) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return insertSql.toString();
	}

	public abstract String getTableName();

}
