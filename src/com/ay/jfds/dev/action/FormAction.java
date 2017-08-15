package com.ay.jfds.dev.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.jfds.dev.dto.CombotreeDTO;
import com.ay.jfds.dev.dto.FormDTO;
import com.ay.jfds.dev.pojo.DevForm;
import com.ay.jfds.dev.pojo.DevFormProperty;
import com.ay.jfds.dev.pojo.Entity;
import com.ay.jfds.dev.pojo.EntityField;
import com.ay.jfds.dev.pojo.Menu;
import com.ay.jfds.dev.service.EntityFieldService;
import com.ay.jfds.dev.service.EntityService;
import com.ay.jfds.dev.service.FormPropertyService;
import com.ay.jfds.dev.service.FormService;
import com.ay.jfds.dev.service.MenuService;

public class FormAction extends BaseAction{
	
	private FormService formService;
	private EntityService entityService;
	public EntityService getEntityService() {
		return entityService;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	private String page;
	private String rows;
	private String sort;
	private String order;
	private String formName;
	private String entityCode;
	private String[] ids;
	private DevForm devForm;
	private Menu menu;
	/**
	 * 分页获取表单
	 */
	public void pageList() {
		DataStore<FormDTO> dataStore = new DataStore<FormDTO>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || "0".equals(page)) ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || "0".equals(rows)) ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = new HashMap();
		
		//设置查询条件
		paramMap.put("formName", formName);
		paramMap.put("entityCode", entityCode);
		
		Page resultPage = formService.getFormDTOList(paramMap,pageTemp);
		List<FormDTO> resultList = (List<FormDTO> ) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		Struts2Utils.renderJson(dataStore, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	/**修改表单*/
	public void updateForm(){
		EntityFieldService efs = SpringContextHolder.getBean(EntityFieldService.class);
		FormPropertyService fps = SpringContextHolder.getBean(FormPropertyService.class);
		OperateInfo opf = new OperateInfo();
		try{
			formService.update(devForm);
			List<EntityField> efList = efs.getAllEntityField(devForm.getEntityId());
			for(EntityField ef :efList){
				DevFormProperty dfp = new DevFormProperty();
				dfp.setFieldId(ef.getId());
				fps.update(dfp);
			}
			opf.setOperateSuccess(true);
			opf.setOperateMessage("修改成功");
		}catch(Exception e){
			opf.setOperateSuccess(false);
			opf.setOperateMessage("修改失败");
			e.printStackTrace();
		}
		String json=new JsonMapper().toJson(opf);
		Struts2Utils.renderText(json, EncodingHeaderUtil.HEADERENCODING);			
	}
	
	/** 添加表单*/
	public void addForm(){
		OperateInfo opf = new OperateInfo();
		if(this.formService.isExists(this.devForm.getFormName())){
			operateShow(false, "添加失败，表单已经存在");
			return;
		}
		EntityService es = SpringContextHolder.getBean(EntityService.class);
		Entity ety = es.getById(this.devForm.getEntityId());
		if(ety.getParentId()!=null){
			Entity fety = es.getById(ety.getParentId());
			this.devForm.setIsRelation("1");
			this.devForm.setParentCode(fety.getEntityCode());
		}else{
			this.devForm.setIsRelation("0");
		}
		
		try{
			formService.insert(devForm);		
			getFormProperty();
			operateShow(true, "添加成功");
			return;
		}catch(Exception e){
			String errMsg = e.getCause().getMessage();
			errMsg = errMsg.substring(errMsg.lastIndexOf("Exception:"));
			operateShow(false, "添加失败,失败原因:"+errMsg);
			e.printStackTrace();
			return;
		}	
	}
	
	/**
	 * 将combotree对象以json形式返回
	 */
	public void getMenuCombotree(){
		MenuService ms = SpringContextHolder.getBean(MenuService.class);
		List<Menu> menuList = ms.findAll();
		List list = getCombotree(menuList);
		Struts2Utils.renderJson(list, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	/**
	 * 获得菜单combotree对象
	 * @param menuList
	 * @return
	 */
	private List getCombotree(List<Menu> menuList){
		List list = new ArrayList();
		for(Menu m1:menuList){
			if("1".equals(m1.getMenuJb())){
				CombotreeDTO cbd = new CombotreeDTO();
				cbd.setId(m1.getMenuId());
				cbd.setText(m1.getMenuName());
				List childrenList = new ArrayList();
				for(Menu m2:menuList){
					if(m2.getMenuSjbh().equals(m1.getMenuId())){
						CombotreeDTO cbd2 = new CombotreeDTO();
						cbd2.setId(m2.getMenuId());
						cbd2.setText(m2.getMenuName());
						childrenList.add(cbd2);
						cbd.setChildren(childrenList);
						cbd2 = null;
						m2 = null;
					}					
				}
				
				list.add(cbd);
				cbd = null;
				m1 = null;
			}
		}
		return list;
	}
	
	/**
	 * 根据表单映射的实体获得所有字段的映射关系,用来实时同步表单和实体的字段
	 * @return
	 */
	public void getFormProperty(){
		EntityFieldService efs = SpringContextHolder.getBean(EntityFieldService.class);
		FormPropertyService fps = SpringContextHolder.getBean(FormPropertyService.class);
		//获得此表单对应的表字段列表
		DevForm df = formService.getById(devForm.getId());		
		String tempEntityId = df.getEntityId();
		List<EntityField> efList = efs.getAllEntityField(tempEntityId);
		List<DevFormProperty> fpList = fps.getFormPropertyByFormId(df.getId());
		//获得当前表单关联的表对象
		Entity entity = efs.getEntityById(tempEntityId); 
		//关联外键的字段名
		String parentKeyField = null;
		if(entity.getParentId()!=null||!"".equals(entity.getParentId())){
			//如果表对象有父表，则取出父表，将外键名得到
			Entity parentEntity = efs.getEntityById(entity.getParentId());
			if(parentEntity!=null){
				parentKeyField = parentEntity.getEntityCode()+"_id";
			}			
		}
		//如果关联的实体没有字段，则删除表单中的字段属性
		if(efList==null||efList.size()==0){
			for(DevFormProperty dfp : fpList){
				fps.delete(dfp.getId());
			}
		}
		//生成对应的表单属性
		for(int i=0;i<efList.size();i++){
			EntityField ef = efList.get(i);
			boolean flag = false;
			
			//遍历此表单当前所有的表单属性，判断表单属性是否关联了表的字段
			for(int j=0;j<fpList.size();j++){
				DevFormProperty dfp = fpList.get(j); 
				//判断表单属性是否关联表字段
				//如果已经有关联（存在）则更新
				if(dfp.getFieldId().equals(ef.getId())){
					flag = true;
					dfp = field2Property(dfp, ef);
					fps.update(dfp);
					break;
				}
				//如果不关联，则判断表单是否关联当前表
				EntityField ef1 = efs.getById(dfp.getFieldId());
				if(ef1!=null){					
					String oeId = ef1.getEntityId();
					//如果不关联当前表则删除此表单属性
					if(!tempEntityId.equals(oeId)){
						//删除不关联的表单属性
						fps.delete(dfp.getId());
					}				
				}
			}
			if(!flag){				
				DevFormProperty dfp = new DevFormProperty();
				dfp = field2Property(dfp, ef);
				//设置默认显示
				dfp.setIsView("0");
				dfp.setEditType("1");
				dfp.setAlgin("0");
				dfp.setIsSearch("0");
				dfp.setRequired("0");
				dfp.setWidth("150");
				dfp.setAlgin("1");
				fps.insert(dfp);
			}
			
		}
	}
	
	/**
	 * 设置表单属性默认值
	 * @param dfp
	 * @param ef
	 * @return
	 */
	private DevFormProperty field2Property(DevFormProperty dfp, EntityField ef){
		dfp.setFormId(this.devForm.getId());
		dfp.setFormOrder(new Integer(ef.getEntityOrder()));
		dfp.setFieldId(ef.getId());
		dfp.setDefaultValue(ef.getDefaultValue());
		if("foreign_Key".equals(ef.getFieldName())){
			dfp.setViewName(ef.getFieldCode());
			dfp.setIsRelation("1");
			System.out.println(dfp);
		}else if(dfp.getViewName()==null||"".equals(dfp.getViewName())){
			dfp.setViewName(ef.getFieldName());
		}
		return dfp;
	}
	
	/**
	 * 返回操作状态
	 * @param isOk
	 */
	private void operateShow(boolean isOk, String msg){
		OperateInfo operateInfo = new OperateInfo();
		if(isOk){
			operateInfo.setOperateMessage(msg);
			operateInfo.setOperateSuccess(true);
		}else{
			operateInfo.setOperateMessage(msg);
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
		operateInfo = null;
		json = null;
	}
	
	/**
	 * 获得表单对象的菜单对象
	 */
	public void getMenuByForm(){
		MenuService ms = SpringContextHolder.getBean(MenuService.class);
		DevForm df = formService.getById(devForm.getId());
		Menu menu = ms.getById(df.getMenuId());
		Struts2Utils.renderJson(menu, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	/**
	 * 将表单对应的菜单插入
	 */
	public void setMenu(){
		OperateInfo opf = new OperateInfo();
		MenuService ms = SpringContextHolder.getBean(MenuService.class);
//		String iconUrl = this.menu.getMenuIcon();
//		if(iconUrl!=null){
//			iconUrl = iconUrl.replace("..", "");
//		}
		DevForm df = formService.getById(devForm.getId());
		Menu sjMenu = ms.getById(this.menu.getMenuSjbh());
		Menu nmenu = new Menu();
		nmenu.setMenuJb(String.valueOf(Integer.valueOf(sjMenu.getMenuJb())+1));
		nmenu.setMenuSjbh(this.menu.getMenuSjbh());
//		nmenu.setMenuIcon(iconUrl);
		nmenu.setMenuName(this.menu.getMenuName());
		String menuOrder = String.valueOf(ms.getMaxOrder(this.menu.getMenuSjbh())+1);
		nmenu.setMenuOrder(menuOrder);
		nmenu.setMenuSwitch("1");
		nmenu.setMenuType("1");
		String menuId = String.valueOf(ms.getMaxMenuId()+1);
		nmenu.setMenuUrl("core/coreAction?menuId="+menuId);
		nmenu.setMenuId(menuId);
		String menuPath = sjMenu.getMenuPath()+","+menuId;
		nmenu.setMenuPath(menuPath);
		//判断表单对应的实体类是否为视图类型
		Entity entity = entityService.getById(df.getEntityId());
		if(null!=entity.getEntityType()&&entity.getEntityType().equals("1")){
			ms.insertViewMenu(nmenu);
		}else{
			ms.insert(nmenu);
		}
		df.setMenuId(menuId);
		if(formService.update(df)){
			opf.setOperateSuccess(true);
			opf.setOperateMessage("操作成功");
		}else{
			opf.setOperateSuccess(false);
			opf.setOperateMessage("操作失败");
		}
		String json=new JsonMapper().toJson(opf);
		Struts2Utils.renderText(json, EncodingHeaderUtil.HEADERENCODING);
	}
	
	
	/**批量删除表单*/
	public void deleteForm(){
		OperateInfo opf = new OperateInfo();
		FormPropertyService fps = SpringContextHolder.getBean(FormPropertyService.class);
		MenuService ms = SpringContextHolder.getBean(MenuService.class);
		try{

			DevForm df = null;
			for(String id : ids){
				df = this.formService.getById(id);
				String tempMenuId = df.getMenuId();
				if(tempMenuId!=null&&!"".equals(tempMenuId)){
					ms.delete(tempMenuId);
				}
				this.formService.delete(id);
			}
			fps.clear();
			opf.setOperateSuccess(true);
			opf.setOperateMessage("删除成功");
		}catch(Exception e){
			opf.setOperateSuccess(false);
			opf.setOperateMessage("删除失败");
			e.printStackTrace();
		}
		String json=new JsonMapper().toJson(opf);
		Struts2Utils.renderText(json, EncodingHeaderUtil.HEADERENCODING);
	}
	
	/**
	 * 获得所有表单列表
	 */
	public void formList(){
		List<DevForm> list = this.formService.findAll();
		Struts2Utils.renderJson(list, EncodingHeaderUtil.HEADERENCODING, EncodingHeaderUtil.CACHEENCODING);
	}
	
	public FormService getFormService() {
		return formService;
	}
	public void setFormService(FormService formService) {
		this.formService = formService;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public DevForm getDevForm() {
		return devForm;
	}

	public void setDevForm(DevForm devForm) {
		this.devForm = devForm;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	
}
