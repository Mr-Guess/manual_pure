package com.ay.jfds.sys.pojo;

import java.sql.Date;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class SysRoleGroup extends BasePojo  { 
    private String roleId;
    private String groupId;

    public void setRoleId(String roleId){
    	this.roleId=roleId;
    }
    public String getRoleId(){
    	return	this.roleId;
    }
    public void setGroupId(String groupId){
    	this.groupId=groupId;
    }
    public String getGroupId(){
    	return	this.groupId;
    }

}