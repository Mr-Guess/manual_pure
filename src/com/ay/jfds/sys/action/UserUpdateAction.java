package com.ay.jfds.sys.action;

import java.security.Security;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.bean.OperateInfo;
import com.ay.framework.bean.UserInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.shiro.ApplicationSessionSchema;
import com.ay.framework.util.Digests;
import com.ay.framework.util.Encodes;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.jfds.dev.action.DataAction;
import com.ay.jfds.sys.dto.UserDTO;
import com.ay.jfds.sys.pojo.User;
import com.ay.jfds.sys.service.UserService;

@SuppressWarnings("all")
public class UserUpdateAction extends BaseAction {

	private UserService userService;
	private User user;
	private String userName;
	private String newPassword;
	private String oldPassword;
	private static Logger logger = LoggerFactory
			.getLogger(UserUpdateAction.class);

	// 更新个人用户密码
	public void updateUserPwd() {
		OperateInfo operateInfo = new OperateInfo();
		String userId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id");// 获得当前用户id
		if(userId == null && user!= null){
			userId = user.getId();
		}
		User currentUser = userService.getById(userId);
		// 取得SESSION中的用户密码
		String UserPassword = null;
		if(session.size()>0){
			UserPassword = ((UserInfo) session
					.get(ApplicationSessionSchema.SESSION_ONLINE_INFO))
					.getPassword();
		}else{
			UserPassword = currentUser.getPassword();
			oldPassword = entryptPassword(oldPassword, Encodes.decodeHex(currentUser.getSalt()));
		}
		if (!UserPassword.equals(oldPassword)) {
			operateInfo.setOperateMessage("原始密码错误");
			operateInfo.setOperateSuccess(false);
		} else {
			user.setUpdated((String) SecurityUtils.getSubject().getSession()
					.getAttribute("user_id"));
			currentUser.setUpdateTime(new Date());
			currentUser.setPassword(user.getPassword());
			currentUser.setPlainPassword(user.getPlainPassword());
			try {
				boolean flag = userService.update(currentUser);
				if (flag) {
					operateInfo.setOperateMessage("修改密码成功");
					operateInfo.setOperateSuccess(true);
				} else {
					operateInfo.setOperateMessage("修改密码失败");
					operateInfo.setOperateSuccess(false);
				}
			} catch (Exception e) {
				logger.error("{}更新密码的时候发生未知错误{}", this, e);
			}
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public String entryptPassword(String pwd,byte[] salt) {
		byte[] hashPassword = Digests.sha1(pwd.getBytes(),salt, 1024);
		return Encodes.encodeHex(hashPassword);
	}

	// 获得当前用户
	public void getByIdDTO() {
		String userId = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("user_id");// 获得当前用户id
		User user = userService.getById(userId);
//		UserDTO userDTO = userService.getUserDTOById(userId);
		Struts2Utils.renderJson(user, EncodingHeaderUtil.HEADERENCODING);
	}

	/* get、set方法 */
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
