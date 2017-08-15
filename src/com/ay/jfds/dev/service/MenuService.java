package com.ay.jfds.dev.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.service.BaseService;
import com.ay.jfds.dev.dao.MenuDao;
import com.ay.jfds.dev.dao.MenuOptDao;
import com.ay.jfds.dev.pojo.Menu;
import com.ay.jfds.dev.pojo.MenuOpt;
public class MenuService extends BaseService<Menu, MenuDao>{
	private MenuOptDao menuOptDao;//菜单权限dao

	public MenuOptDao getMenuOptDao() {
		return menuOptDao;
	}

	public void setMenuOptDao(MenuOptDao menuOptDao) {
		this.menuOptDao = menuOptDao;
	}

	/**
	 *根据菜单级别，获取本级别的所有菜单 
	 * @param menu_jb 菜单级别
	 * @param menuList
	 * @return
	 **/
	public List<Menu> getMenu(String menu_sjbh,String menu_jb,List<Menu> menuList){
		return this.getDao().getMenu(menu_sjbh, menu_jb, menuList);
	}
	
	public List<Menu> getAllMenuByUser(String userId){
		return dao.getAllMenuByUser(userId);
	}
	
	/**
	 * 前台
	 * 根据上级菜单的编号,获得该上级菜单下面对应的级别的子菜单,用于导航框架菜单生成 
	 * @param menu_sjbh 
	 * @param menu_jb
	 * @return
	 * **/
	public String getNextAllMenu(String menu_sjbh,String menu_jb,List<Menu> menuList){
		return this.getDao().getNextAllMenu(menu_sjbh, menu_jb, menuList);
	}
	
	/**
	 * 后台
	 * 根据上级菜单的编号，获得该上级菜单下面对应级别的子菜单,用于菜单管理界面 菜单树的生成  
	 * @param menu_sjbh  上级菜单的编号
	 * @param menu_jb  菜单级别
	 * @return
	 */
	public String getNextAllMenuConfig(String menu_sjbh, String menu_jb, List<Menu> menuList) {
		return this.getDao().getNextAllMenuConfig(menu_sjbh, menu_jb, menuList);
	}
	/**
	 * 根据上级菜单 获得该上级菜单下面的一级菜单
	 * @return
	 */
	public List<Menu> findFirstChild(Map map){
		return this.getDao().findFirstChild(map);
	}
	/**
	 * 后台
	 * 获取所有企业菜单
	 * @return
	 */
	public List<Menu> getAllEntMenu() {
		return this.getDao().getAllEntMenu();
	}
	
	
	/**
	 * 获取菜单表中 当前最大的菜单编号
	 * @param 无参数 
	 * @return
	 */
	public int getMaxMenuId()
    {
        return this.getDao().getMaxMenuId();
    }
	
	/**
	 * 更新菜单顺序
	 * @param menuId
	 * @return
	 */
	
	public boolean updateOrder(Menu menu)
    {
        return this.getDao().updateOrder(menu);
    }
	/**
	 * 按照模块查询
	 * @return
	 */
	public List<Menu> findByKind(String kind){
		return this.getDao().findBykind(kind);
	}
	
	/**
	 * 重写insert方法，插入菜单记录的同时，要初始化一条菜单权限的数据（基本查看的菜单权限）
	 * @param menu
	 * @return
	 */
	public Menu insert(Menu menu){
		Menu menuNew = this.dao.insert(menu);
		Map<String,Object> map = new HashMap<String ,Object>();
    	map.put("optType", "0");
    	map.put("menuId", menuNew.getMenuId());
    	List list = menuOptDao.query(map);
    	String path = menu.getMenuPath();
    	String[] paths = path.split(",");
    	if(list.size()==0&&paths.length==3){
    		MenuOpt opt = new MenuOpt();
    		opt.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt.setOptCode("baseview");
    		opt.setOptName("基本查看");
    		opt.setOptType("0");
    		opt.setOptOrder("1");
    		menuOptDao.insert(opt);
    		
    		MenuOpt opt2 = new MenuOpt();
    		opt2.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt2.setOptCode("add");
    		opt2.setOptName("新增");
    		opt2.setOptType("3");
    		opt2.setOptOrder("2");
    		menuOptDao.insert(opt2);
    		
    		MenuOpt opt3 = new MenuOpt();
    		opt3.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt3.setOptCode("delete");
    		opt3.setOptName("删除");
    		opt3.setOptType("2");
    		opt3.setOptOrder("3");
    		menuOptDao.insert(opt3);
    		
    		MenuOpt opt4 = new MenuOpt();
    		opt4.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt4.setOptCode("update");
    		opt4.setOptName("修改");
    		opt4.setOptType("1");
    		opt4.setOptOrder("4");
    		menuOptDao.insert(opt4);
    		
    		MenuOpt opt5 = new MenuOpt();
    		opt5.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt5.setOptCode("view");
    		opt5.setOptName("查看");
    		opt5.setOptType("1");
    		opt5.setOptOrder("5");
    		menuOptDao.insert(opt5);
    		
    		MenuOpt opt6 = new MenuOpt();
    		opt6.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt6.setOptCode("exp");
    		opt6.setOptName("导出");
    		opt6.setOptType("3");
    		opt6.setOptOrder("6");
    		menuOptDao.insert(opt6);
    		
//    		MenuOpt opt7 = new MenuOpt();
//    		opt7.setMenuId(Integer.valueOf(menuNew.getMenuId()));
//    		opt7.setOptCode("imp");
//    		opt7.setOptName("导入");
//    		opt7.setOptType("3");
//    		opt7.setOptOrder("7");
//    		menuOptDao.insert(opt7);
    		
    		MenuOpt opt8 = new MenuOpt();
    		opt8.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt8.setOptCode("print");
    		opt8.setOptName("打印");
    		opt8.setOptType("4");
    		opt8.setOptOrder("7");
    		menuOptDao.insert(opt8);
    	}
		return menuNew ;
	}
	
	/**
	 * 视图类型的菜单新增方法
	 * @param menu
	 * @return
	 */
	public Menu insertViewMenu(Menu menu){
		Menu menuNew = this.dao.insert(menu);
		Map<String,Object> map = new HashMap<String ,Object>();
    	map.put("optType", "0");
    	map.put("menuId", menuNew.getMenuId());
    	List list = menuOptDao.query(map);
    	String path = menu.getMenuPath();
    	String[] paths = path.split(",");
    	if(list.size()==0&&paths.length==3){
    		MenuOpt opt = new MenuOpt();
    		opt.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt.setOptCode("baseview");
    		opt.setOptName("基本查看");
    		opt.setOptType("0");
    		opt.setOptOrder("1");
    		menuOptDao.insert(opt);
    		
    		MenuOpt opt5 = new MenuOpt();
    		opt5.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt5.setOptCode("view");
    		opt5.setOptName("查看");
    		opt5.setOptType("1");
    		opt5.setOptOrder("2");
    		menuOptDao.insert(opt5);
    		
    		MenuOpt opt6 = new MenuOpt();
    		opt6.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt6.setOptCode("exp");
    		opt6.setOptName("导出");
    		opt6.setOptType("3");
    		opt6.setOptOrder("3");
    		menuOptDao.insert(opt6);
    		
    		MenuOpt opt8 = new MenuOpt();
    		opt8.setMenuId(Integer.valueOf(menuNew.getMenuId()));
    		opt8.setOptCode("print");
    		opt8.setOptName("打印");
    		opt8.setOptType("4");
    		opt8.setOptOrder("4");
    		menuOptDao.insert(opt8);
    	}
		return menuNew ;
	}
	
	/**
	 * 重写delete方法，删除菜单记录的同时，要删除菜单权限的所有数据
	 * @param menuId
	 * @return
	 */
	public boolean deleteMenuAndOpt(String menuId){
		this.dao.delete(menuId);;
		menuOptDao.deleteByMenuId(menuId);
		return true ;
	}
	
	/**
	 * 根据MenuId来查找此Menu下的所有操作按钮
	 * 
	 * @param id
	 * @return
	 */
	public List<MenuOpt> getMenuOptByMenuId(String id) {
		List<MenuOpt> menuList = this.getDao().getMenuOptByMenuId(id);
		return menuList;
	}
	
	/**
	 * 获得所有Menu
	 * @return List
	 */
	public List<Menu> findAllMenu(){
		return this.getDao().findAll();
	}
	
	/**
	 * 获得同级最大编号
	 * @param sjbh
	 * @return
	 */
	public Integer getMaxOrder(String sjbh){
		Integer order = this.getDao().getMaxOrderBySjbh(sjbh);
		if(order==null){
			return 1;
		}
		return order;
	}

}
