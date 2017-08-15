package com.ay.jfds.sys.pojo;

import java.sql.Date;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class SysRoleDept extends BasePojo  { 
    private String roleId;
    private String deptId;

    public void setRoleId(String roleId){
    	this.roleId=roleId;
    }
    public String getRoleId(){
    	return	this.roleId;
    }
    public void setDeptId(String deptId){
    	this.deptId=deptId;
    }
    public String getDeptId(){
    	return	this.deptId;
    }

}