package com.ay.jfds.sys.pojo;

import java.sql.Date;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class SysRoleUser extends BasePojo  { 
    private String roleId;
    private String userId;

    public void setRoleId(String roleId){
    	this.roleId=roleId;
    }
    public String getRoleId(){
    	return	this.roleId;
    }
    public void setUserId(String userId){
    	this.userId=userId;
    }
    public String getUserId(){
    	return	this.userId;
    }

}