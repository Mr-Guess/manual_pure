package com.ay.framework.shiro;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.ay.framework.core.utils.DBConnectionUtil;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.util.Encodes;
import com.ay.jfds.dev.service.MenuService;
import com.ay.jfds.sys.dto.RoleDTO;
import com.ay.jfds.sys.pojo.SysRoleData;
import com.ay.jfds.sys.pojo.User;
import com.ay.jfds.sys.service.UserService;

/**
 * 与数据库进行连接操作
 * 
 * @author zxy
 * 
 */
@SuppressWarnings("all")
public class MyRealm extends AuthorizingRealm
{

    /**
     * 持有UserService对象，以便下面对登录验证操作
     */
    private UserService userService;

    private MenuService getMenuService()
    {
        return SpringContextHolder.getBean(MenuService.class);
    }

    /**
     * 认证回调函数, 登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException
    {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = userService.findUserByName(token.getUsername());
        if (user != null)
        {
            byte[] salt = Encodes.decodeHex(user.getSalt());
            return new SimpleAuthenticationInfo(new ShiroUser(user.getId(),
                    user.getAccount(), user.getUserName()), user.getPassword(),
                    ByteSource.Util.bytes(salt), getName());
        }
        else
        {
            return null;
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals)
    {
        ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName())
                .iterator().next();
        User user = userService.findUserByName(shiroUser.getLoginName());

        // 这里对Shiro用户进行相应的授权操作
        if (user != null)
        {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

            // 将用户下所具有的所有权限进行筛选出来（查出所有有权限的opt）《重点就在此处》
            List<RoleDTO> listRole = userService.getUserRoleDTOById(user
                    .getId());
            List<String> permissions = new ArrayList<String>();
            // 当用户为超级管理员的时候就具有所有权限，无论是不是他的下面具有别的权限
            if (user.getUserType().trim().equals("0"))
            {
                info.addStringPermission("admin");
                info.addStringPermission("*");
            }
            else
            {
                Set<String> list = new HashSet<String>();
                for (RoleDTO dto : listRole)
                {
                    permissions.add(dto.getMenuId() + ":" + dto.getMenuUrl()
                            + ":" + dto.getOptCode());
                    permissions.add(dto.getMenuId() + ":" + dto.getOptCode());
                    permissions.add(dto.getMenuId() + ":" + dto.getMenuUrl());
                    permissions.add(dto.getSjbh() + ":null");
                    // Menu menu = this.getMenuService().getById(dto.getSjbh());
                    // permissions.add(menu.getMenuSjbh() + ":null");
                    list.add(dto.getSjbh());
                }
                Connection connection = null;
                Statement statement = null;
                ResultSet rs = null;
                try
                {
                    connection = DBConnectionUtil.getInstance().getConnection();
                    StringBuilder sql = new StringBuilder(
                            "select menu_sjbh from dev_menu where menu_id in (");
                    for (String id : list)
                    {
                        sql.append("'").append(id).append("', ");
                    }
                    if (sql.lastIndexOf(",") != -1) {
                        sql.deleteCharAt(sql.lastIndexOf(","));
                    }
                    sql.append(" )");
                    statement = connection.createStatement();
                    System.out.println(sql.toString());
                    rs = statement.executeQuery(sql.toString());
                    while (rs.next())
                    {
                        permissions.add(rs.getString("menu_sjbh"));
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    DBConnectionUtil.getInstance().close(connection, statement,
                            rs);
                }

                info.addStringPermissions(permissions);
            }

            // 将用户的信息进行缓存操作，放入Session之中
            org.apache.shiro.subject.Subject currentUser = SecurityUtils
                    .getSubject();
            currentUser.getSession().setAttribute("user_id", user.getId());
            currentUser.getSession().setAttribute("user_name",
                    user.getUserName());
            currentUser.getSession().setAttribute("account", user.getAccount());
            currentUser.getSession().setAttribute("user_dept_id",
                    user.getDeptId());
            currentUser.getSession().setAttribute("usertype",
                    user.getUserType());

            Map<String, SysRoleData> map = userService.getUserDataRoleById(user
                    .getId());
            currentUser.getSession().setAttribute("user_data_role", map);
            return info;
        }
        else
        {
            return null;
        }
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher()
    {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
                UserService.HASH_ALGORITHM);
        matcher.setHashIterations(UserService.HASH_INTERATIONS);

        setCredentialsMatcher(matcher);
    }

    public UserService getUserService()
    {
        return userService;
    }

    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable
    {

        private static final long serialVersionUID = -1748602382963711884L;
        private String loginName;
        private String name;
        private String id;

        public ShiroUser(String loginName, String name)
        {
            this.loginName = loginName;
            this.name = name;
        }

        public ShiroUser(String id, String loginName, String name)
        {
            this.loginName = loginName;
            this.name = name;
            this.id = id;
        }

        public String getLoginName()
        {
            return loginName;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString()
        {
            return loginName;
        }

        public String getName()
        {
            return name;
        }
    }

}
