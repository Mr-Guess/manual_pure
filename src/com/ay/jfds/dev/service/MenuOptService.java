package com.ay.jfds.dev.service;


import com.ay.framework.core.service.BaseService;
import com.ay.jfds.dev.dao.MenuOptDao;
import com.ay.jfds.dev.pojo.MenuOpt;

public class MenuOptService extends BaseService<MenuOpt, MenuOptDao>{
	//测试事务回滚
	 public boolean insertOpt(){
		MenuOpt opt = this.dao.getById("1");
    	opt.setMenuId(49);
		this.dao.update(opt);//更新成功
		opt.setId("50");
		this.dao.insert(opt);//添加报错，整个事务会回滚
		return true;
	 }
	//新增菜单权限
	 public void addMenuOpt(String detail,Integer menuId){
		this.dao.deleteByMenuId(String.valueOf(menuId));
    	String[] details = detail.split(";");
    	for(int i = 0 ;i<details.length;i++){
    		String[] opts = details[i].split(",");
    		MenuOpt menuOpt = new MenuOpt();
    		for(int j = 0 ;j<opts.length;j++){
    			menuOpt.setMenuId(menuId);
    			menuOpt.setOptCode(opts[0]);
    			menuOpt.setOptName(opts[1]);
    			menuOpt.setOptUrl(opts[2]);
    			menuOpt.setOptType(opts[3]);
    			menuOpt.setOptOrder(opts[4]);
    		}
    		this.dao.insert(menuOpt);
    	}
	
	}
}
