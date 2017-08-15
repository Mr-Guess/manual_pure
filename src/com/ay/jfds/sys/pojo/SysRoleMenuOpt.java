package com.ay.jfds.sys.pojo;

import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class SysRoleMenuOpt extends BasePojo  { 
    private String roleId;
    private String menuOptId;

    public void setRoleId(String roleId){
    	this.roleId=roleId;
    }
    public String getRoleId(){
    	return	this.roleId;
    }
    public void setMenuOptId(String menuOptId){
    	this.menuOptId=menuOptId;
    }
    public String getMenuOptId(){
    	return	this.menuOptId;
    }

}