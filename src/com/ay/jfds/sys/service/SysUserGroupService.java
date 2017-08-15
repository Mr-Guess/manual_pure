package com.ay.jfds.sys.service;

import com.ay.framework.core.service.BaseService;
import com.ay.jfds.dev.pojo.MenuOpt;
import com.ay.jfds.sys.dao.SysUserGroupDao;
import com.ay.jfds.sys.pojo.SysUserGroup;

/**
 * 用户组 成员关系表service 的封装
 * 
 * @author lushigai
 * @see SysUserGroupDao
 */
public class SysUserGroupService extends BaseService<SysUserGroup, SysUserGroupDao> {
	/**
     * 根据用户组id删除
     * 
     * @param id
     * @return 删除的记录数是否为1
     */
    public boolean deleteByGroupId(String groupId){
    	return this.dao.deleteByGroupId(groupId);
    }
	//新增用户组成员
	public boolean addUserGroup(String usersStr,String groupId){
		this.dao.deleteByGroupId(String.valueOf(groupId));
    	String[] users = usersStr.split(";");
    	for(int i = 0 ;i<users.length;i++){
    		String userId = users[i];
    		SysUserGroup userGroup = new SysUserGroup();
    		userGroup.setGroupId(groupId);
    		userGroup.setUserId(userId);
    		this.dao.insert(userGroup);
    	}
    	return true;
	}
}
