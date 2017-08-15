package com.ay.jfds.dev.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.dev.dto.FormDTO;
import com.ay.jfds.dev.pojo.DevForm;

public class FormDao extends BaseDao<DevForm>{

	@Override
	public String getEntityName() {
		return "DevForm";
	}

	@SuppressWarnings("unchecked")
	public List<FormDTO> getFormDTOList(Map map, int from, int prePageNum){
		return (List<FormDTO>) this.getSqlMapClientTemplate().queryForList(getEntityName()+ ".findAllDTO", map, from, prePageNum);
	}
	
	public DevForm getForm(Map map){
		return (DevForm) this.getSqlMapClientTemplate().queryForObject(getEntityName() + ".find", map);
	}
	
	/**
	 * 根据menuId清除form表中的menuId
	 * @param menuId
	 */
	public void removeMenuId(String menuId){
			this.getSqlMapClientTemplate().update(getEntityName()+".removeMenuId", menuId);
	}

	@Override
	public String getTableName() {
		return "dev_form";
	}
	
}
