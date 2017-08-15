package com.ay.jfds.dev.dao;

import java.io.Serializable;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.dev.pojo.MenuOpt;

public class MenuOptDao  extends BaseDao<MenuOpt> {

	@Override
	public String getEntityName()
	{
		// TODO Auto-generated method stub
		return "menuOpt";
	}
	
	/**
     * 根据菜单id删除
     * 
     * @param id
     * @return 删除的记录数是否为1
     */
    public boolean deleteByMenuId(String menuId)
    {
        int rows = getSqlMapClientTemplate().delete(getEntityName() + ".deleteByMenuId", menuId);
        return rows == 1;
    }

	@Override
	public String getTableName() {
		return "dev_menu_opt";
	}
}
