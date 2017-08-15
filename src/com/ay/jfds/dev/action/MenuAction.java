package com.ay.jfds.dev.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.common.ITreeService;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.shiro.SystemMenu;
import com.ay.framework.util.ImageMinCutUtil;
import com.ay.framework.util.RandomNumUtil;
import com.ay.framework.util.StringUtil;
import com.ay.jfds.dev.pojo.Menu;
import com.ay.jfds.dev.pojo.MenuOpt;
import com.ay.jfds.dev.pojo.MenuVO;
import com.ay.jfds.dev.service.FormService;
import com.ay.jfds.dev.service.MenuEntTreeService;
import com.ay.jfds.dev.service.MenuService;
import com.ay.jfds.dev.service.MenuTreeService;
import com.lowagie.text.pdf.codec.Base64.InputStream;

/**
 * 菜单管理
 * 
 * 对MenuAction进行改造，使用Shiro进行相应的权限控制
 * 
 * @DateTime: 2012-9-10
 * @author lushigai
 * @author zxy
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("all")
public class MenuAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(MenuAction.class);
	private MenuService menuService;// 菜单接口
	private String menuId;// 菜单id
	private ITreeService treeService;
	private String menuKind;
	private String menuModule;
	private String muenuJb;//菜单级别
	private File uploadify;	//按钮图片	注意获取文件时使用get方法获取 因为使用get方法图片已经处理过
	private String uploadifyFileName;
    private InputStream downloadStream;
    private String kind;
    private String userId;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();

	/**
	 * 增加Shiro的权限控制
	 * 
	 * @throws IOException
	 */
	public void initMenu() throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		// 获取所有的可用菜单信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menu_switch", "1");
		List<Menu> menuList = menuService.query(map);

		if (menuList != null) {
			// 设置当前用户
			// getUser(request);
			StringBuffer menuXML = new StringBuffer();
			menuXML.append("<?xml version=\"1.0\" encoding=\"uft-8\"?>");
			menuXML.append("<root>");
			int menu_jb = 1;
			List<Menu> top_menuList = menuService.getMenu("0",
					String.valueOf(menu_jb), menuList);
			for (Menu menu : top_menuList) {
				if (menu.getMenuName() != null
						&& menu.getMenuName().trim().length() > 0) {
					Subject currentUser = SecurityUtils.getSubject();
					if (currentUser.isPermitted("admin")) {
						menuXML.append("<item remark = ");
						menuXML.append("\"" + menu.getMenuName() + "\"");
						menuXML.append(">");

						menuXML.append(menuService.getNextAllMenu(
								menu.getMenuId(), String.valueOf(menu_jb + 1),
								menuList));
						menuXML.append("</item>");
						continue;
					}
					// 没有url连接下的menu也是没有url以及optcode的
					StringBuilder permissionStr = new StringBuilder();
					if (menu.getMenuUrl() == null
							|| menu.getMenuUrl().equals("")) {
						permissionStr.append(menu.getMenuId()).append(":null");
					} else {
						permissionStr.append(menu.getMenuId()).append(":")
								.append(menu.getMenuUrl());
					}
					if (currentUser.isPermitted(permissionStr.toString())) {
						menuXML.append("<item remark = ");
						menuXML.append("\"" + menu.getMenuName() + "\"");
						menuXML.append(">");

						menuXML.append(menuService.getNextAllMenu(
								menu.getMenuId(), String.valueOf(menu_jb + 1),
								menuList));
						menuXML.append("</item>");
					}
				}

			}
			menuXML.append("</root>");
			logger.debug("菜单xml：："+menuXML);
			out.println(menuXML.toString());
		}
	}
	public void initMenu2() throws IOException {
		// logger.info("start");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		Subject currentUser = SecurityUtils.getSubject();
		// 获取所有的可用菜单信息
		List<MenuVO> memery_menuVOList = SystemMenu.getTop_menuVOList();
		List<MenuVO> top_menuVOList = new ArrayList<MenuVO>();
		for (MenuVO menu : memery_menuVOList) {
			String permissionStr = null;
			if (menu.getMenuUrl() == null || menu.getMenuUrl().isEmpty())
				permissionStr = menu.getMenuId() + ":null";
			else
				permissionStr = menu.getMenuId() + ":" + menu.getMenuUrl();
			if (currentUser.isPermitted(permissionStr)) {
				MenuVO m = new MenuVO();
				m.setMenuId(menu.getMenuId());
				m.setMenuName(menu.getMenuName());
				m.setMenuUrl(menu.getMenuUrl());
				m.setMenuModule(menu.getMenuModule());
				m.setMenuTableName(menu.getMenuTableName());
				getAllPermissionChildren(m, menu, currentUser);
				top_menuVOList.add(m);
			}
		}
		String json = new JsonMapper().toJson(top_menuVOList);
		// logger.info("end");
		Struts2Utils.renderText(json);
	}
	
	public void getMenuByUser() throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		Subject currentUser = SecurityUtils.getSubject();
		List<Menu> userMenu = menuService.getAllMenuByUser(userId);
		// 获取所有的可用菜单信息
		List<MenuVO> memery_menuVOList = SystemMenu.getTop_menuVOList();
		List<MenuVO> top_menuVOList = new ArrayList<MenuVO>();
		StringBuffer permissionStr = new StringBuffer();
		for(Menu menu : userMenu){
			if(menu.getMenuJb().equals("3")){
				if(permissionStr.indexOf(",") == -1){
					permissionStr.append(menu.getMenuPath()+","+menu.getMenuId());
				}else{
					permissionStr.append(","+menu.getMenuPath()+","+menu.getMenuId());
				}
			}
		}
		for (MenuVO menu : memery_menuVOList) {
			if (permissionStr.indexOf(menu.getMenuId())!=-1) {
				MenuVO m = new MenuVO();
				m.setMenuId(menu.getMenuId());
				m.setMenuName(menu.getMenuName());
				m.setMenuUrl(menu.getMenuUrl());
				m.setMenuModule(menu.getMenuModule());
				m.setMenuTableName(menu.getMenuTableName());
				getMenuChildrenByUser(m, menu, permissionStr);
				top_menuVOList.add(m);
			}
		}
		String json = new JsonMapper().toJson(top_menuVOList);
		// logger.info("end");
		Struts2Utils.renderText(json);
	}
	
	public void getMenuChildrenByUser(MenuVO parent,MenuVO menuVO,StringBuffer permissionStr){
		List<MenuVO> menuVOList = menuVO.getChildren();
		for (MenuVO menu : menuVOList) {
			if (permissionStr.indexOf(menu.getMenuId())!=-1) {
				MenuVO m = new MenuVO();
				m.setMenuId(menu.getMenuId());
				m.setMenuName(menu.getMenuName());
				m.setMenuUrl(menu.getMenuUrl());
				m.setMenuModule(menu.getMenuModule());
				m.setMenuTableName(menu.getMenuTableName());
				getMenuChildrenByUser(m, menu, permissionStr);
				parent.getChildren().add(m);
			}
		}
		
		
	}

	/**
	 * 通过kind加载企业菜单等
	 * 
	 * @throws IOException
	 * @return void
	 */
	public void loadMenu() throws IOException {
		// logger.info("start");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml");
		Subject currentUser = SecurityUtils.getSubject();
		// 获取所有的可用菜单信息
		List<MenuVO> memery_menuVOList = SystemMenu.getTop_menuVOList();
		List<MenuVO> top_menuVOList = new ArrayList<MenuVO>();
		for (MenuVO menu : memery_menuVOList) {
			String permissionStr = null;
			if (menu.getMenuUrl() == null || menu.getMenuUrl().isEmpty())
				permissionStr = menu.getMenuId() + ":null";
			else
				permissionStr = menu.getMenuId() + ":" + menu.getMenuUrl();
			if ((menuKind == null || (menuKind != null && menu.getMenuKind().contains(menuKind))) 
					&& (menuModule == null || (menuModule != null 
					&& menuModule.equals(menu.getMenuModule()))) 
					&& currentUser.isPermitted(permissionStr)) {
				MenuVO m = new MenuVO();
				m.setMenuId(menu.getMenuId());
				m.setMenuName(menu.getMenuName());
				m.setMenuUrl(menu.getMenuUrl());
				m.setMenuTableName(menu.getMenuTableName());
				m.setIconNo(menu.getIconNo());
				getAllPermissionChildren(m, menu, currentUser);	//递归获取所有子节点
				top_menuVOList.add(m);
			}
		}
		String json = new JsonMapper().toJson(top_menuVOList);
		// logger.info("end");
		Struts2Utils.renderText(json);
	}
	
	//查询用户可用的一级菜单
	public void loadFirstMeun(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml");
		Subject currentUser = SecurityUtils.getSubject();
		// 获取所有的可用菜单信息
		List<MenuVO> memery_menuVOList = SystemMenu.getTop_menuVOList();
		List<MenuVO> top_menuVOList = new ArrayList<MenuVO>();
		for (MenuVO menu : memery_menuVOList) {
			String permissionStr = null;
			if (menu.getMenuUrl() == null || menu.getMenuUrl().isEmpty())
				permissionStr = menu.getMenuId() + ":null";
			else
				permissionStr = menu.getMenuId() + ":" + menu.getMenuUrl();
			if ((menuKind == null || (menuKind != null && menu.getMenuKind().contains(menuKind))) 
					&& (menuModule == null || (menuModule != null 
					&& menuModule.equals(menu.getMenuModule()))) 
					&& currentUser.isPermitted(permissionStr)) {
				MenuVO m = new MenuVO();
				m.setMenuId(menu.getMenuId());
				m.setMenuName(menu.getMenuName());
				m.setMenuUrl(menu.getMenuUrl());
				m.setMenuTableName(menu.getMenuTableName());
				m.setIconNo(menu.getIconNo());
				top_menuVOList.add(m);
			}
		}
		String json = new JsonMapper().toJson(top_menuVOList);
		// logger.info("end");
		Struts2Utils.renderText(json);
	}
	
	/*获取所有子节点*/
	public void children(){
		//MenuVO menuVo=getMenuVos();
		Subject currentUser = SecurityUtils.getSubject();
		// 获取所有的可用菜单信息
		List<MenuVO> memery_menuVOList = SystemMenu.getTop_menuVOList();
		MenuVO top_menuVOList = null;
		for (MenuVO menu : memery_menuVOList) {
			String permissionStr = null;
			if(menu.getMenuId().equals(menuId)){
			if (menu.getMenuUrl() == null || menu.getMenuUrl().isEmpty())
				permissionStr = menu.getMenuId() + ":null";
			else
				permissionStr = menu.getMenuId() + ":" + menu.getMenuUrl();
			if ((menuKind == null || (menuKind != null && menu.getMenuKind().contains(menuKind))) 
					&& (menuModule == null || (menuModule != null 
					&& menuModule.equals(menu.getMenuModule()))) 
					&& currentUser.isPermitted(permissionStr)) {
				MenuVO m = new MenuVO();
				m.setMenuId(menu.getMenuId());
				m.setMenuName(menu.getMenuName());
				m.setMenuUrl(menu.getMenuUrl());
				m.setMenuTableName(menu.getMenuTableName());
				m.setIconNo(menu.getIconNo());
				getAllPermissionChildren(m, menu, currentUser);	//递归获取所有子节点
				top_menuVOList=m; break;
			}
			}
		}
		String json = new JsonMapper().toJson(top_menuVOList.getChildren());
		// logger.info("end");
		Struts2Utils.renderText(json);
	}
	
	/**
	 * 查询当前menu 下面的所有子Menu
	 * @param vo
	 * @return
	 */
	private MenuVO getMenuVos(){
		Subject currentUser = SecurityUtils.getSubject();
		Menu menu=menuService.getById(menuId);
		MenuVO menuVo=getMenuVo();
		Map<String,String> map=new HashMap<String, String>();
		map.put("menuSjbh", menuId);
		List<MenuVO> children=convert(menuService.findFirstChild(map));
		List<MenuVO> menuChildren=new ArrayList<MenuVO>();	//新
		for(MenuVO mvo:children){
			String permissionStr = null;
			if (menu.getMenuUrl() == null || menu.getMenuUrl().isEmpty())
				permissionStr = menu.getMenuId() + ":null";
			else
				permissionStr = menu.getMenuId() + ":" + menu.getMenuUrl();
			if ((menuKind == null || (menuKind != null && menu.getMenuKind().contains(menuKind))) 
					&& currentUser.isPermitted(permissionStr)) {
				map.clear();
				map.put("menuSjbh", mvo.getMenuId());
				List<MenuVO> chileds=convert(menuService.findFirstChild(map));
				mvo.setChildren(chileds);
				menuChildren.add(mvo);
			}
		}
		menuVo.setChildren(menuChildren);
		return menuVo;
	}
	/**
	 * 通过menuId查询menuVo
	 * @return menuVo
	 */
	private MenuVO getMenuVo(){
		Subject currentUser = SecurityUtils.getSubject();
		Menu menu=menuService.getById(menuId);
		MenuVO menuVo=new MenuVO();
		menuVo.setMenuId(menu.getMenuId());
		menuVo.setMenuKind(menu.getMenuKind());
		menuVo.setMenuModule(menu.getMenuModule());
		menuVo.setMenuName(menu.getMenuName());
		menuVo.setMenuUrl(menu.getMenuUrl());
		return menuVo;
	}
	
	public List<MenuVO> convert(List<Menu> menus){
		Subject currentUser = SecurityUtils.getSubject();
		List<MenuVO> menuVos=new ArrayList<MenuVO>();
		for(Menu menu:menus){
			String permissionStr = null;
			if (menu.getMenuUrl() == null || menu.getMenuUrl().isEmpty())
				permissionStr = menu.getMenuId() + ":null";
			else
				permissionStr = menu.getMenuId() + ":" + menu.getMenuUrl();
			if ((menuKind == null || (menuKind != null && menu.getMenuKind().contains(menuKind))) 
					&& currentUser.isPermitted(permissionStr)) {
				MenuVO menuVo=new MenuVO();
				menuVo.setMenuId(menu.getMenuId());
				menuVo.setMenuKind(menu.getMenuKind());
				menuVo.setMenuModule(menu.getMenuModule());
				menuVo.setMenuName(menu.getMenuName());
				menuVo.setMenuUrl(menu.getMenuUrl());
				menuVo.setIconNo(menu.getIconNo());
				menuVos.add(menuVo);
			}
			
		}
		return menuVos;
	}
	private void getAllPermissionChildren(MenuVO parent, MenuVO menuVO,
			Subject currentUser) {
		List<MenuVO> menuVOList = menuVO.getChildren();
		for (MenuVO menu : menuVOList) {
			String permissionStr = null;
			if (menu.getMenuUrl() == null || menu.getMenuUrl().isEmpty())
				permissionStr = menu.getMenuId() + ":null";
			else
				permissionStr = menu.getMenuId() + ":" + menu.getMenuUrl();
			if ((menuKind == null || (menuKind != null && menu.getMenuKind().contains(menuKind))) 
					&& currentUser.isPermitted(permissionStr)) {
				MenuVO m = new MenuVO();
				m.setMenuId(menu.getMenuId());
				m.setMenuName(menu.getMenuName());
				m.setMenuUrl(menu.getMenuUrl());
				m.setMenuTableName(menu.getMenuTableName());
				m.setIconNo(menu.getIconNo());
				getAllPermissionChildren(m, menu, currentUser);
				parent.getChildren().add(m);
			}
		}
	}
	
	// 初始化菜单信息树 ,后台调用
	public void initTree() throws IOException {

		// 获取菜单作为缓存
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menu_switch", "0");
		map.put("menuKind", menuKind);
		List<Menu> menuList = menuService.query(map);
		int menu_jb = 1;

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		StringBuffer menuXML = new StringBuffer();
		menuXML.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
		menuXML.append("<tree id=\"MenuTree\">");

		if (menuList != null && menuList.size() > 0) {
			// 获取所有一级菜单
			List<Menu> top_menuList = menuService.getMenu("0",
					String.valueOf(menu_jb), menuList);

			if (top_menuList != null && top_menuList.size() > 0) {
				for (int i = 0; i < top_menuList.size(); i++) {
					Menu menu = (Menu) top_menuList.get(i);
					if (menu.getMenuName() != null
							&& menu.getMenuName().trim().length() > 0) {
						if (i == 0) {
							menuXML.append("<item text=");
							menuXML.append("\""
									+ menu.getMenuName()
									+ "\" id=\""
									+ menu.getMenuId()
									+ "\" open=\"1\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" select=\"1\">");
						} else {
							menuXML.append("<item text=");
							menuXML.append("\""
									+ menu.getMenuName()
									+ "\" id=\""
									+ menu.getMenuId()
									+ "\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">");
						}
						// 增加节点上的其他字段信息
						menuXML.append("<userdata name=\"menu_name\">"
								+ menu.getMenuName() + "</userdata>");
						menuXML.append("<userdata name=\"menu_jb\">"
								+ menu.getMenuJb() + "</userdata>");
						menuXML.append("<userdata name=\"menu_sjbh\">"
								+ menu.getMenuSjbh() + "</userdata>");
						menuXML.append("<userdata name=\"menu_path\">"
								+ menu.getMenuPath() + "</userdata>");
						menuXML.append("<userdata name=\"menu_url\">"
								+ menu.getMenuUrl() + "</userdata>");
						menuXML.append("<userdata name=\"menu_order\">"
								+ menu.getMenuOrder() + "</userdata>");
						menuXML.append("<userdata name=\"menu_switch\">"
								+ menu.getMenuSwitch() + "</userdata>");
						menuXML.append("<userdata name=\"iconNo\">"
								+ menu.getIconNo() + "</userdata>");
						menuXML.append("<userdata name=\"menu_info\">"
								+ menu.getMenuInfo() + "</userdata>");
						menuXML.append("<userdata name=\"menu_type\">"
								+ menu.getMenuType() + "</userdata>");
						menuXML.append("<userdata name=\"menu_kind\">"
								+ menu.getMenuKind() + "</userdata>");
						menuXML.append("<userdata name=\"menu_module\">"
								+ menu.getMenuModule() + "</userdata>");
						if(menu.getMenuTableName()!=null){
							menuXML.append("<userdata name=\"menu_table_name\">"
									+ menu.getMenuTableName() + "</userdata>");
						}
						menuXML.append(menuService.getNextAllMenuConfig(
								menu.getMenuId(), String.valueOf(menu_jb + 1),
								menuList));
						menuXML.append("</item>");
					}
				}

			}
		} else {
			menuXML.append("<item  text=\"暂无菜单列表,请添加！\" id=\"noMenu\" open=\"1\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" select=\"1\"></item>");
		}
		menuXML.append("</tree>");
		System.out.println("---" + menuXML);
		out.println(menuXML.toString());// 菜单树 输出到前台
	}

	// 添加菜单
	public void addMenu() throws IOException {
		
		try {/*
			request.setCharacterEncoding("utf-8");// 设置取参编码格式
			response.setContentType("text/xml;charset=utf-8");// 设置输出编码格式
			
			String menuStr = readJSONString(request);
			// 转换成jsonobject
			JSONObject jsonObj = JSONObject.fromObject(menuStr);
			
			String menu_id = jsonObj.getString("menu_id");// 菜单编号
			String menu_sjbh = jsonObj.getString("menu_sjbh");// 上级编号
			String menu_name = jsonObj.getString("menu_name");// 菜单名称
			String menu_url = jsonObj.getString("menu_url");// 菜单链接地址
			String menu_type = jsonObj.getString("menu_type"); // 菜单打开方式 ，main窗口 还是
			// 新窗口打开
			String menu_kind = jsonObj.getString("menu_kind");// 菜单种类
			String menu_module = jsonObj.getString("menu_module");// 对应模块
			String menu_iconNew = " ";// 菜单图标
			String menu_icon = jsonObj.getString("menuIcon");
			String menu_info = jsonObj.getString("menu_info");// 菜单描述 信息
			String menu_order = jsonObj.getString("menu_order");// 菜单显示顺序
			String menu_path = jsonObj.getString("menu_path");// 菜单路径
			String menu_jb = jsonObj.getString("menu_jb");// 菜单级别
			String menu_switch = jsonObj.getString("menu_switch");// 菜单启用状态
			String menuTableName = jsonObj.getString("menuTableName");// 菜单对应模块表
			
			String operateFlag = jsonObj.getString("operateFlag");// 菜单操作动作，新增 或者 修改*/
			
			PrintWriter out = response.getWriter();
			if(uploadify!=null){
				if(new FileInputStream(uploadify).available()>102400*3){
					SystemMenu.getMenuList();
					out.println("图片不能超过300K");return;
				}
			}
			if (menu_id.equals("noMenu") || menu_id.equals("")) {
				operateFlag = "additem";
				menu_id = "1000000000";
				menu_order = "1";
				menu_sjbh = "0";
				menu_jb = "1";
				menu_path = "0";
			}
			
			if (operateFlag.equals("additem")) {// 新增 菜单
				Menu menu = new Menu();
				menu.setMenuId(String.valueOf((menuService.getMaxMenuId() + 1)));
				menu.setMenuName(menu_name);
				menu.setMenuUrl(menu_url);
				menu.setMenuType(menu_type);
				menu.setMenuSwitch(menu_switch);
				menu.setMenuTableName(menuTableName);
				menu.setMenuInfo(menu_info);
				menu.setMenuOrder(menu_order);
				menu.setMenuSjbh(menu_sjbh);
				menu.setMenuPath(menu_path);
				menu.setMenuJb(menu_jb);
				menu.setMenuKind(menu_kind);
				menu.setMenuModule(menu_module);
				menu.setIconNo(iconNo);
				Menu menuStampe = menuService.insert(menu);
				if (null != menuStampe) {
					SystemMenu.getMenuList();
					out.println("添加菜单信息成功！");
				} else {
					out.println("添加菜单信息失败！");
				}
			} else if (operateFlag.equals("update")) {//
				// 修改 菜单信息
				
				Menu menu = new Menu();
				menu.setMenuId(menu_id);
				menu.setMenuName(StringUtil.returnNotNull(menu_name));
				menu.setMenuUrl(StringUtil.returnNotNull(menu_url));
				menu.setMenuType(StringUtil.returnNotNull(menu_type));
				menu.setMenuSwitch(StringUtil.returnNotNull(menu_switch));
				menu.setMenuInfo(StringUtil.returnNotNull(menu_info));
				menu.setMenuKind(StringUtil.returnNotNull(menu_kind));
				menu.setMenuModule(StringUtil.returnNotNull(menu_module));
				menu.setMenuTableName(StringUtil.returnNotNull(menuTableName));
				menu.setIconNo(iconNo);
				boolean flag = menuService.update(menu);
				if (flag == true) {
					SystemMenu.getMenuList();
					out.println("修改菜单信息成功！");
				} else {
					out.println("修改菜单信息失败！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 根据菜单ID删除菜单信息
	public void delMenu() throws IOException {
		response.setContentType("text/xml;charset=utf-8");// 设置输出编码格式
		PrintWriter out = response.getWriter();
		boolean flag = menuService.deleteMenuAndOpt(menuId);
		FormService fs = SpringContextHolder.getBean(FormService.class);
		fs.removeMenuId(menuId);
		if (flag == true) {
			SystemMenu.getMenuList();
			out.println("删除菜单信息成功！");
		} else {
			out.println("删除菜单信息失败！");
		}

	}
	
	public void findByKind(){
		List<Menu> mnues=menuService.findByKind(kind);
		String json=new JsonMapper().toJson(mnues);
		Struts2Utils.renderText(json);
	}

	public void saveTreeMenuByIDs() throws IOException {
		response.setContentType("text/xml;charset=utf-8");// 设置输出编码格式
		PrintWriter out = response.getWriter();
		String menuIds = menuId;
		String[] MenuTrees = menuIds.split("&aykj&");
		for (int i = 0; i < MenuTrees.length; i++) {
			String[] menuTemps = MenuTrees[i].split("\\*aykj\\*");
			for (int j = 0; j < menuTemps.length; j++) {
				Menu tempMenuInfo = new Menu();
				tempMenuInfo.setMenuId(menuTemps[0]);// 菜单ID
				tempMenuInfo.setMenuJb(menuTemps[1]);// 菜单级别
				tempMenuInfo.setMenuSjbh(menuTemps[2]);// 菜单上级编号
				tempMenuInfo.setMenuPath(menuTemps[3]);// 菜单路径
				tempMenuInfo.setMenuOrder(menuTemps[4]);// 菜单顺序
				menuService.updateOrder(tempMenuInfo);
			}
		}
		out.println("保存顺序成功！");

	}

	// 转换JSON数据的方法
	public String readJSONString(HttpServletRequest request) {
		StringBuffer json = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return json.toString();
	}

	public void setControlByUser() {
		// String
		// username=UserManager.getUser(request.getSession()).getUsername();
		String username = "lulu";
		HttpSession session = request.getSession();
		if (session.getAttribute(username) != null) {
			session.removeValue(username);
		}
		session.setAttribute(username, menuId);
		// log.debug("当前操作模块："+username+"**************"+menuId);
	}

	/**
	 * Menu Tree的封装
	 */
	public void menuTreeLoad() {
		MenuTreeService menuTreeService = MenuTreeService.getInstance();
		menuTreeService.reloadMenuTree();
		try {
			String treeJson = treeService.generateJsonCheckboxTree(menuTreeService,
					false);
			Struts2Utils.renderJson(treeJson);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void menuTreeInition(){
		List<Menu> list = menuService.findAll();
		String json = "[";
		String secondjson = "";
		String thirdjson = "";
		for(Menu menuin : list){
			if(json.substring(json.length()-1, json.length()).equals("}")){
				json += ",";
			}
			if(menuin.getMenuJb().equals("1")){
				secondjson = "";
				for(int i = 0;i<list.size();i++){
					if(secondjson.length() > 1 && !secondjson.substring(secondjson.length()-1, secondjson.length()).equals(",")){
						secondjson += ",";
					}
					if(list.get(i).getMenuSjbh().equals(menuin.getMenuId())){
						thirdjson = "";
						for(int j = 0; j <list.size();j++){
							if(thirdjson.length() > 1 && !thirdjson.substring(thirdjson.length()-1, thirdjson.length()).equals(",")){
								thirdjson += ",";
							}
							if(list.get(j).getMenuSjbh().equals(list.get(i).getMenuId())){
								thirdjson +="{\"id\":\""+list.get(j).getMenuId()+"\",\"name\":\""+list.get(j).getMenuName()+"\",\"level\":\""+list.get(j).getMenuJb()+"\",\"children\":[]}";
							}
						}
						secondjson += "{\"id\":\""+list.get(i).getMenuId()+"\",\"name\":\""+list.get(i).getMenuName()+"\",\"level\":\""+list.get(i).getMenuJb()+"\",\"children\":["+thirdjson+"]}";
					}
				}
				json += "{\"id\":\""+menuin.getMenuId()+"\",\"name\":\""+menuin.getMenuName()+"\",\"level\":\""+menuin.getMenuJb()+"\",\"children\":["+secondjson+"]}";
			}
		}
		json += "]";
		Struts2Utils.renderText(json);
	}
	
	/**
	 * Menu Tree的封装
	 */
	public void entTreeLoad() {
		MenuEntTreeService menuTreeService = MenuEntTreeService.getInstance();
		menuTreeService.reloadMenuTree();
		try {
			String treeJson = treeService.generateJsonCheckboxTree(menuTreeService,
					false);
			Struts2Utils.renderJson(treeJson);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 根据 id获得一个Menu下面的操作按钮的Checkbox divString对象
	 */
	public void getMenuOptByMenuId() {
		List<MenuOpt> menuOptList = menuService.getMenuOptByMenuId(menuId);
		StringBuilder divStringBuilder = new StringBuilder();
		if (menuOptList.size() != 0) {
			divStringBuilder
					.append("<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		for (Iterator<MenuOpt> iterator = menuOptList.iterator(); iterator
				.hasNext();) {
			MenuOpt menuOpt = iterator.next();
			divStringBuilder.append("<input type=\"checkbox\" " + "value=\""
					+ menuOpt.getId() + "\" optId=\"" + menuOpt.getId()
					+ "\" checkboxtype=\"" + "optcheckbox" + "\" title=\""
					+ menuOpt.getOptName() + "\">" + "" + menuOpt.getOptName()
					+ "&nbsp;");
		}

		Struts2Utils.renderText(divStringBuilder.toString());
	}
	

	public ITreeService getTreeService() {
		return treeService;
	}

	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}

	public String getMenuKind() {
		return menuKind;
	}

	public void setMenuKind(String menuKind) {
		this.menuKind = menuKind;
	}

	public String getMenuModule() {
		return menuModule;
	}

	public void setMenuModule(String menuModule) {
		this.menuModule = menuModule;
	}
	public String getMuenuJb() {
		return muenuJb;
	}
	public void setMuenuJb(String muenuJb) {
		this.muenuJb = muenuJb;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		MenuAction.logger = logger;
	}

	public void setUploadify(File uploadify) {
		this.uploadify = uploadify;
	}
	
	public byte[] getImage(){
		return ImageMinCutUtil.convertImg(uploadify, uploadify.getName(), 23, 23, 1f);
	}

	public String getUploadifyFileName() {
		return uploadifyFileName;
	}

	public void setUploadifyFileName(String uploadifyFileName) {
		this.uploadifyFileName = uploadifyFileName;
	}

	public InputStream getDownloadStream() {
		return downloadStream;
	}

	public void setDownloadStream(InputStream downloadStream) {
		this.downloadStream = downloadStream;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	private String menuStr;
	private String menu_id;
	private String menu_sjbh;
	private String menu_name;
	private String menu_url;
	private String menu_type;
	private String menu_kind;
	private String menu_module;
	private String menu_info;
	private String menu_order;
	private String menu_path;
	private String menu_jb;
	private String menu_switch;
	private String menuTableName;
	private String operateFlag;
	private String iconNo;

	public String getMenuStr() {
		return menuStr;
	}

	public void setMenuStr(String menuStr) {
		this.menuStr = menuStr;
	}

	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}

	public String getMenu_sjbh() {
		return menu_sjbh;
	}

	public void setMenu_sjbh(String menu_sjbh) {
		this.menu_sjbh = menu_sjbh;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getMenu_url() {
		return menu_url;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	public String getMenu_type() {
		return menu_type;
	}

	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
	}

	public String getMenu_kind() {
		return menu_kind;
	}

	public void setMenu_kind(String menu_kind) {
		this.menu_kind = menu_kind;
	}

	public String getMenu_module() {
		return menu_module;
	}

	public void setMenu_module(String menu_module) {
		this.menu_module = menu_module;
	}

	public String getMenu_info() {
		return menu_info;
	}

	public void setMenu_info(String menu_info) {
		this.menu_info = menu_info;
	}

	public String getMenu_order() {
		return menu_order;
	}

	public void setMenu_order(String menu_order) {
		this.menu_order = menu_order;
	}

	public String getMenu_path() {
		return menu_path;
	}

	public void setMenu_path(String menu_path) {
		this.menu_path = menu_path;
	}

	public String getMenu_jb() {
		return menu_jb;
	}

	public void setMenu_jb(String menu_jb) {
		this.menu_jb = menu_jb;
	}

	public String getMenu_switch() {
		return menu_switch;
	}

	public void setMenu_switch(String menu_switch) {
		this.menu_switch = menu_switch;
	}

	public String getMenuTableName() {
		return menuTableName;
	}

	public void setMenuTableName(String menuTableName) {
		this.menuTableName = menuTableName;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public String getIconNo() {
		return iconNo;
	}

	public void setIconNo(String iconNo) {
		this.iconNo = iconNo;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
