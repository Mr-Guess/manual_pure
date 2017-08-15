package com.ay.jfds.dev.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.dev.pojo.Menu;
import com.ay.jfds.dev.pojo.MenuOpt;
@SuppressWarnings("all")
public class MenuDao extends BaseDao<Menu> {

	@Override
	public String getEntityName() {
		return "menu";
	}

	// 菜单标签对应的权限值为8
	private static final byte menuRwde = 8;

	/**
	 * 根据菜单级别，获取本级别的所有菜单
	 * 
	 * @param menu_jb
	 *            菜单级别
	 * @param menuList
	 * @return
	 **/

	public List<Menu> getMenu(String menu_sjbh, String menu_jb,
			List<Menu> menuList) {
		List<Menu> list = new Vector<Menu>();
		for (Menu menu : menuList) {
			if ((menu.getMenuJb() != null && menu.getMenuJb().equals(menu_jb))
					&& (menu.getMenuSjbh() != null && menu.getMenuSjbh()
							.equals(menu_sjbh))) {
				list.add(menu);
			}
		}
		return list;
	}
	
	public List<Menu> getAllMenuByUser(String menuId){
		return (List<Menu>)this.getSqlMapClientTemplate().queryForList(getEntityName() + ".findMenuByUser", menuId);
	}

	/**
	 * 前台 根据上级菜单的编号,获得该上级菜单下面对应的级别的子菜单,用于导航框架菜单生成
	 * 
	 * @param menu_sjbh
	 * @param menu_jb
	 * @return
	 * **/

	public String getNextAllMenu(String menu_sjbh, String menu_jb,
			List<Menu> menuList) {
		String itemstr = "";
		String pre = " ";
		// MenuController control=new MenuController();
		Subject subject = SecurityUtils.getSubject();
		StringBuffer menuXML = new StringBuffer();
		for (Menu menu : menuList) {
			if (subject.isPermitted("admin")) {
				if ((menu.getMenuJb() != null && menu.getMenuJb().equals(
						menu_jb))
						&& (menu.getMenuSjbh() != null && menu.getMenuSjbh()
								.equals(menu_sjbh))) {
					// /if(haveQx(menu.getMenuId())){
					if (menu.getMenuName() != null
							&& menu.getMenuName().trim().length() > 0) {
						menuXML.append("<item remark = ");
						menuXML.append("\"" + menu.getMenuName() + "\"");
						menuXML.append(pre);
					}

					if (menu.getMenuUrl() != null
							&& menu.getMenuUrl().trim().length() > 0) {
						menuXML.append("onclick = ");
						// menuXML.append("\"" + menu.getMenuUrl()+"\"");
						menuXML.append("\"show(" + "'" + menu.getMenuId() + "'"
								+ ");menu_onclick(" + "'" + menu.getMenuUrl()
								+ "','" + menu.getMenuType() + "'" + ")\"");
						menuXML.append(pre);
					}

//					if (menu.getMenuIcon() != null
//							&& menu.getMenuIcon().trim().length() > 0) {
//						menuXML.append("ico = ");
//						menuXML.append("\"" + menu.getMenuIcon() + "\"");
//						menuXML.append(pre);
//					}

					// 获取下个子菜单列表,如果其还存在下级子菜单则继续循环,否则结束
					int child_jb = Integer.parseInt(menu_jb) + 1;
					List<Menu> list = this.getMenu(menu.getMenuId(),
							String.valueOf(child_jb), menuList);

					if (list != null && list.size() > 0) {
						menuXML.append(">");
						menuXML.append(this.getNextAllMenu(menu.getMenuId(),
								String.valueOf(child_jb), menuList));
						menuXML.append("</item>");
					} else {
						menuXML.append("/>");
					}
					// }
				}
				continue;
			}
			StringBuilder permissionStr = new StringBuilder();
			if (menu.getMenuUrl() == null
					|| menu.getMenuUrl().trim().equals("")) {
				permissionStr.append(menu.getMenuId()).append(":null");
			} else {
				permissionStr.append(menu.getMenuId()).append(":")
						.append(menu.getMenuUrl());
			}
			if (!subject.isPermitted(permissionStr.toString())) {
				continue;
			}
			if ((menu.getMenuJb() != null && menu.getMenuJb().equals(menu_jb))
					&& (menu.getMenuSjbh() != null && menu.getMenuSjbh()
							.equals(menu_sjbh))) {
				// /if(haveQx(menu.getMenuId())){
				if (menu.getMenuName() != null
						&& menu.getMenuName().trim().length() > 0) {
					menuXML.append("<item remark = ");
					menuXML.append("\"" + menu.getMenuName() + "\"");
					menuXML.append(pre);
				}

				if (menu.getMenuUrl() != null
						&& menu.getMenuUrl().trim().length() > 0) {
					menuXML.append("onclick = ");
					// menuXML.append("\"" + menu.getMenuUrl()+"\"");
					menuXML.append("\"show(" + "'" + menu.getMenuId() + "'"
							+ ");menu_onclick(" + "'" + menu.getMenuUrl()
							+ "','" + menu.getMenuType() + "'" + ")\"");
					menuXML.append(pre);
				}

//				if (menu.getMenuIcon() != null
//						&& menu.getMenuIcon().trim().length() > 0) {
//					menuXML.append("ico = ");
//					menuXML.append("\"" + menu.getMenuIcon() + "\"");
//					menuXML.append(pre);
//				}

				// 获取下个子菜单列表,如果其还存在下级子菜单则继续循环,否则结束
				int child_jb = Integer.parseInt(menu_jb) + 1;
				List<Menu> list = this.getMenu(menu.getMenuId(),
						String.valueOf(child_jb), menuList);

				if (list != null && list.size() > 0) {
					menuXML.append(">");
					menuXML.append(this.getNextAllMenu(menu.getMenuId(),
							String.valueOf(child_jb), menuList));
					menuXML.append("</item>");
				} else {
					menuXML.append("/>");
				}
				// }
			}
		}
		// System.out.println("menuXML info: "+menuXML.toString());
		return itemstr = menuXML.toString();
	}

	/**
	 * 后台 根据上级菜单的编号，获得该上级菜单下面对应级别的子菜单,用于菜单管理界面 菜单树的生成
	 * 
	 * @param menu_sjbh
	 *            上级菜单的编号
	 * @param menu_jb
	 *            菜单级别
	 * @return
	 */
	public String getNextAllMenuConfig(String menu_sjbh, String menu_jb,
			List<Menu> menuList) {
		String itemstr = "";
		String pre = " ";
		String strImg0 = "leaf.gif";
		String strImg1 = "folderOpen.gif";
		String strImg2 = "folderClosed.gif";
		StringBuffer menuXml = new StringBuffer();
		for (Menu menu_new : menuList) {
			if ((menu_new.getMenuJb() != null && menu_new.getMenuJb().equals(
					menu_jb))
					&& (menu_new.getMenuSjbh() != null && menu_new
							.getMenuSjbh().equals(menu_sjbh))) {

				if (menu_new.getMenuName() != null
						&& menu_new.getMenuName().trim().length() > 0) {
					menuXml.append("<item text=\"" + menu_new.getMenuName()
							+ "\" id=\"" + menu_new.getMenuId() + "\" im0=\""
							+ strImg0 + "\"  im1=\"" + strImg1 + "\" im2=\""
							+ strImg2 + "\">");

					// 增加节点上的其他字段信息

					menuXml.append("<userdata name=\"menu_name\">"
							+ menu_new.getMenuName() + "</userdata>");
					menuXml.append("<userdata name=\"menu_jb\">"
							+ menu_new.getMenuJb() + "</userdata>");
					menuXml.append("<userdata name=\"menu_sjbh\">"
							+ menu_new.getMenuSjbh() + "</userdata>");
					menuXml.append("<userdata name=\"menu_path\">"
							+ menu_new.getMenuPath() + "</userdata>");
					menuXml.append("<userdata name=\"menu_url\">"
							+ menu_new.getMenuUrl() + "</userdata>");
					menuXml.append("<userdata name=\"menu_order\">"
							+ menu_new.getMenuOrder() + "</userdata>");
					menuXml.append("<userdata name=\"menu_switch\">"
							+ menu_new.getMenuSwitch() + "</userdata>");
					menuXml.append("<userdata name=\"menu_info\">"
							+ menu_new.getMenuInfo() + "</userdata>");
					menuXml.append("<userdata name=\"menu_type\">"
							+ menu_new.getMenuType() + "</userdata>");
					menuXml.append("<userdata name=\"iconNo\">"
							+ menu_new.getIconNo() + "</userdata>");
					menuXml.append("<userdata name=\"menu_kind\">"
						+ menu_new.getMenuKind() + "</userdata>");
					menuXml.append("<userdata name=\"menu_module\">"
							+ menu_new.getMenuModule() + "</userdata>");
					if(menu_new.getMenuTableName()!=null){
						menuXml.append("<userdata name=\"menu_table_name\">"
								+ menu_new.getMenuTableName() + "</userdata>");
					}

				}

				// 获取下个子菜单列表，如果其还存在下级子菜单则继续循环，否则结束item
				int child_jb = Integer.parseInt(menu_jb) + 1;
				List<Menu> list = getMenu(menu_new.getMenuId(),
						String.valueOf(child_jb), menuList);
				if (list != null && list.size() > 0) {
					menuXml.append(getNextAllMenuConfig(menu_new.getMenuId(),
							String.valueOf(child_jb), menuList));
					menuXml.append("</item>");
				} else {
					menuXml.append("</item>");
				}
			}
		}
		return itemstr = menuXml.toString();
	}

	/**
	 * 获取菜单表中 当前最大的菜单编号
	 * 
	 * @param 无参数
	 * @return
	 */

	public int getMaxMenuId() {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				this.getEntityName() + ".getMaxMenuId");
	}

	/**
	 * 更新菜单顺序
	 * 
	 * @param menuId
	 * @return
	 */

	public boolean updateOrder(Menu menu) {
		int rows = getSqlMapClientTemplate().update(
				getEntityName() + ".updateOrder", menu);
		return rows == 1;
	}
	
	/**
	 * 后台
	 * 获取所有企业菜单
	 * @return
	 */
	public List<Menu> getAllEntMenu() {
		List<Menu> list = (List<Menu>)getSqlMapClientTemplate().queryForList(getEntityName() + ".findEntMenu");
		return list;
	}

	/**
	 * 根据menuId来查找到此Menu下面的所有的操作按钮
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("all")
	public List<MenuOpt> getMenuOptByMenuId(String id) {
		List<MenuOpt> menuOptList = (List<MenuOpt>) getSqlMapClientTemplate()
				.queryForList(getEntityName() + ".getMenuOptByMenuId", id);
		return menuOptList;
	}

	/**
	 * 获得同级最大的编号
	 * 
	 * @param sjbh
	 * @return
	 */
	public Integer getMaxOrderBySjbh(String sjbh) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				getEntityName() + ".getMaxOrderBySjbh", sjbh);
	}
	/**
	 * 查询不同模块
	 * @param map
	 * @return
	 */
	public List<Menu> findBykind(String kind){
		Map map=new HashMap();
		map.put("kind", kind);
		return this.getSqlMapClientTemplate().queryForList(getEntityName()+".findBykind",map);
	}
	
	public List<Menu> findFirstChild(Map map){
		return this.getSqlMapClientTemplate().queryForList(getEntityName()+".findFirstChild",map);
	}

	@Override
	public String getTableName() {
		return "dev_menu";
	}
}
