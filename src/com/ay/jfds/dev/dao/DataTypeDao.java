package com.ay.jfds.dev.dao;

import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.pojo.DataType;

/**
 * 元数据类型dao
 * 
 * @author PS
 * 
 */
@SuppressWarnings("all")
public class DataTypeDao extends BaseDao<DataType> {

	@Override
	public String getEntityName() {
		return "dataType";
	}

	/**
	 * 查找一个元数据类别下的所有元数据
	 * 
	 * @param dataType
	 * @return
	 */
	public List<Data> findAllDataInType(DataType dataType, int from,
			int prePageNum) {
		return (List<Data>) this.getSqlMapClientTemplate().queryForList(
				getEntityName() + ".findAllDataInType", dataType, from,
				prePageNum);
	}

	/**
	 * 根据Id查找元数据类别下的所有元素
	 * 
	 * @param id
	 * @param from
	 * @param prePageNum
	 * @return
	 */
	public List<Data> findAllDataInType(String id, int from, int prePageNum) {
		return (List<Data>) this.getSqlMapClientTemplate().queryForList(
				getEntityName() + ".findAllDataInType", id, from, prePageNum);
	}
	
	public List<DataType> findAllDataTypes(){
		return this.getSqlMapClientTemplate().queryForList(getEntityName()+".findAllDataTypes");
	}

	/**
	 * 删除一个元数据类型下面的所有元数据
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteDataByType(String id) {
		int rows = this.getSqlMapClientTemplate().delete(
				getEntityName() + ".deleteDataByType", id);
		if (rows > 1) {
			return true;
		}
		return false;
	}

	/**
	 * 防止重复
	 * 
	 * @param typeName
	 * @return
	 */
	public Integer findWordsByWords(String typeName) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".findWordsByWords", typeName);
	}
	
	/**
	 * 防止编码重复
	 * 
	 * @param typeName
	 * @author zhangxiang 2013-03-05
	 * @return
	 */
	public Integer findWordsById(String id) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".findWordsById", id);
	}
	
	/**
	 * 获得所有元数据类别
	 * by wgw
	 * @param map
	 * @return
	 */
	public List<DataType> findAll(Map map){
		return (List<DataType>)this.getSqlMapClientTemplate().queryForList(getEntityName()+ ".findAll", map);				
	}
	
	/**
	 * 获得某个元数据 by wgw
	 * @param map
	 * @return
	 */
	public DataType find(Map map){
		return (DataType) this.getSqlMapClientTemplate().queryForObject(getEntityName()+ ".find", map);
	}

	@Override
	public String getTableName() {
		return "dev_data_type";
	}

}