package com.ay.jfds.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.service.BaseService;
import com.ay.framework.util.Digests;
import com.ay.framework.util.Encodes;
import com.ay.jfds.sys.dao.UserDao;
import com.ay.jfds.sys.dto.RoleDTO;
import com.ay.jfds.sys.dto.UserDTO;
import com.ay.jfds.sys.pojo.Department;
import com.ay.jfds.sys.pojo.SysRoleData;
import com.ay.jfds.sys.pojo.User;

/**
 * User service 的封装
 * 
 * @author zxy
 * @see UserDao
 */
@SuppressWarnings("all")
public class UserService extends BaseService<User, UserDao> {

	public static final String HASH_ALGORITHM = "SHA-1";
	/** 进行Hash迭代的次数 */
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	
	public Department getUsersDept(User user) {
		return this.getDao().getUsersDept(user);
	}

	/**
	 * 对dto分页的封装操作
	 * 
	 * @param map
	 * @param page
	 * @return
	 */
	public Page pageDTOQuery(Map map, Page page) {
		page.setCount(this.count(map));
		List<UserDTO> list = dao.pageDTOQuery(map, page.getFrom(),
				page.getRecPerPage());
		page.setCollection(list);
		return page;
	}

	public User findUserByName(String account) {
		return this.getDao().findUserByName(account);
	}
	
	/*流程相关*/
	public User getUperUser(String id){
		return dao.getUperUser(id);
	}
	
	
	/**
	 * 获取部门领导
	 * @param id:部门ID
	 * @return
	 */
	public User getDeptLeader(String id){
		return dao.getDeptLeader(id);
	}
	
	/**
	 * 获取用户所在部门
	 * @param id:用户ID
	 * @return
	 */
	public Department getUperDept(String id) {
		return dao.getUperDept(id);
	}
	
	public Department getCrossUperDept(String id) {
		return dao.getCrossUperDept(id);
	}
	
	public Department getBelongDept(String id){
		Department d = dao.getUperDept(id);
		while(d != null && d.getProvince() == null){
			d = dao.getUperDept(d.getId());
		}
		return d;
	}
	
	/*流程相关结束*/

	public UserDTO getUserDTOById(String id) {
		return this.getDao().getUserDTOById(id);
	}
	
	public UserDTO getQyUserDTOById(String id) {
		return this.getDao().getQyUserDTOById(id);
	}

	/**
	 * 根据登录名查找出当前用户
	 * 
	 * @param userName
	 * @return
	 */
	public UserDTO getUserDTOByName(String userName) {
		return this.getDao().getUserDTOByName(userName);
	}

	/**
	 * 根据id锁定用户
	 * 
	 * @param id
	 */
	public void lockUser(String id) {
		this.getDao().lockUser(id);
	}

	/**
	 * 根据id更新用户
	 * 
	 * @param id
	 */
	public void updateUserDTO(UserDTO userDTO) {
		this.getDao().updateUserDTO(userDTO);
	}

	/**
	 * 查找用户的所具有的ID
	 * 
	 * @param id
	 * @return
	 */
	public List<RoleDTO> getUserRoleDTOById(String id) {
		return this.getDao().getUserRoleDTOById(id);
	}
	
	/**
	 * 查询出来的角色数据权限
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, SysRoleData> getUserDataRoleById(String id) {
		return this.getDao().getUserDataRoleById(id);
	}
	
	public List<User> getAllentUser(){
		return this.getDao().getAllentUser();
	}
	
	public List<User> getAlltUser(){
		return this.getDao().getAlltUser();
	}
	
	public List<User> findByDept(String dept){
		return this.getDao().findByDept(dept);
	}

	/**
	 * 重写父类的更新方法，为了就是进行相应的salt算法
	 */
	@Override
	public boolean update(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			if (!user.getPlainPassword().equals("null")) {
				entryptPassword(user);
			}
		}
		return this.getDao().update(user);
	}

	/**
	 * 重写父类的插入方法，为了进行用户密码的salt算法
	 */
	@Override
	public User insert(User user) {
		// 在更新之前进行加盐算法
		entryptPassword(user);
		return this.getDao().insert(user);
	}

	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),
				salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	

	public static void main(String[] args) {
		User user = new User();
		user.setPlainPassword("11111111");
		new UserService().entryptPassword(user);
		System.out.println(user.getPassword());
		System.out.println(user.getSalt());
	}

	/**
	 * 根据用户判断是否有角色,by wgw
	 * 
	 * @param user
	 * @return
	 */
	public boolean isHasRole(UserDTO user) {
		String account = user.getAccount();
		Integer count = this.dao.isHasRole(account);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否重复注册的帐号
	 * 
	 * @param account
	 * @return
	 */
	public boolean checkAccountExist(String account, String id) {
		int integer = this.getDao().checkAccountExist(account, id);
		if (integer != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 添加企业用户
	 * @param bci
	 * @param user
	 */
/*	public void addCorpUser(BusCorpInfo bci,User user){
		BusCorpInfoService bcis = SpringContextHolder.getBean(BusCorpInfoService.class);
		String uuid = StringUtil.getUUID();
		bci.setCreated(uuid);
		bcis.insert(bci);
		
		UserService us = SpringContextHolder.getBean(UserService.class);
		user.setAccount(bci.getCorpCode());
		user.setPlainPassword(user.getPassword());
		user.setUserName(bci.getCorpName());
		user.setUserType("3");
		user.setDeptId("4c02aa65d602461399dcefec6");
		user.setId(uuid);
		user.setCreated(uuid);
		us.insert(user);
		
		SysRoleUserService sus = SpringContextHolder.getBean(SysRoleUserService.class);
		SysRoleUser sru = new SysRoleUser();
		sru.setRoleId("4e835afe792340f2a7898c2a1323f219");
		sru.setUserId(user.getId());
		sru = sus.insert(sru);
	}*/
	
	public List findUserDto() {
		return this.dao.findUserDto();
	}
	
	public List findChildrenUserDto(String id) {
		return this.dao.findChildrenUserDto(id);
	}
	
	public List findEntUserDto() {
		return this.dao.findEntUserDto();
	}
}
