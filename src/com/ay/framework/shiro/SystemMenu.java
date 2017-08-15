package com.ay.framework.shiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ay.jfds.dev.pojo.Menu;
import com.ay.jfds.dev.pojo.MenuVO;
import com.ay.jfds.dev.service.MenuService;

/** 
 * @Description 
 * @date 2012-12-11
 * @author WangXin
 */
@Component
public class SystemMenu {
	private static Logger logger = LoggerFactory.getLogger(SystemMenu.class);
	private static MenuService menuService;
	private static List<MenuVO> top_menuVOList;
	//存放所有角色的菜单
	private static Map<String, List<MenuVO>> allMenu;
	
	@Resource
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	@PostConstruct
	public static void getMenuList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menuSwitch", "1");
		//先从数据库菜单配置表[dev_menu]中查出所有菜单
		List<Menu> menuList = menuService.query(map);
		if(menuList == null){
			return;
		}
		
		if(allMenu == null){
			allMenu = new HashMap<String, List<MenuVO>>();
		}
		
		//此方法为找出一级菜单
		List<Menu> top_menuList = menuService.getMenu("0","1", menuList);
		top_menuVOList = new ArrayList<MenuVO>();
		for (Menu menu : top_menuList) {
				MenuVO m = new MenuVO();
				m.setMenuId(menu.getMenuId());
				m.setMenuName(menu.getMenuName());
				m.setMenuUrl(menu.getMenuUrl());
				m.setMenuKind(menu.getMenuKind());
				m.setMenuModule(menu.getMenuModule());
				m.setIconNo(menu.getIconNo());
				getAllChildren(m, menuList);
				top_menuVOList.add(m);
		}
		
		logger.info("menu init complete");
	}
	
	private static void getAllChildren(MenuVO menuVO ,List<Menu> menuList){
		String parentId = menuVO.getMenuId();
		for(int i=0 ; i< menuList.size() ; i++){
			Menu menu = menuList.get(i);
			if(menu.getMenuSjbh().equals(parentId)){
				MenuVO m = new MenuVO();
				m.setMenuId(menu.getMenuId());
				m.setMenuName(menu.getMenuName());
				m.setMenuUrl(menu.getMenuUrl());
				m.setMenuKind(menu.getMenuKind());
				m.setIconNo(menu.getIconNo());
//				m.setMenuModule(menu.getMenuModule());
				getAllChildren(m, menuList);
				menuVO.getChildren().add(m);
			}
		}
	}
	public static List<MenuVO> getTop_menuVOList() {
		return top_menuVOList;
	}
	
	
}

