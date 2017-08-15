package com.ay.jfds.dev.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.service.BaseService;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.jfds.dev.dao.FormDao;
import com.ay.jfds.dev.dto.FormDTO;
import com.ay.jfds.dev.dto.FormFieldDTO;
import com.ay.jfds.dev.pojo.DevForm;
import com.ay.jfds.dev.pojo.Entity;

public class FormService extends BaseService<DevForm, FormDao>{
	
	public Page getFormDTOList(Map map, Page page){
		page.setCount(this.dao.count(map));
		List<FormDTO> list = this.dao.getFormDTOList(map, page.getFrom(), page.getRecPerPage());
		page.setCollection(list);
		return page;
	}
	
	/**
	 * 根据菜单名获得List< FormFieldDTO >
	 * @param menuId
	 * @return
	 */
	public List<FormFieldDTO> getFormField(String menuId){
		Map map = new HashMap();
		map.put("menuId", menuId);
		DevForm df = this.dao.getForm(map);
		String formId = df.getId();
		FormPropertyService fps = SpringContextHolder.getBean(FormPropertyService.class);
		List<FormFieldDTO> ff = fps.getFormFieldByFormId(formId);
		return ff;
	}
	
	/**
	 * 根据表名获得List< FormFieldDTO >
	 * @param entityCode
	 * @return
	 */
	public List<FormFieldDTO> getFormFieldByCode(String entityCode){
		EntityService es = SpringContextHolder.getBean(EntityService.class);
		Entity entity = es.getEntityByCode(entityCode);
		Map causeMap = new HashMap();
		causeMap.put("entityId", entity.getId());
		DevForm df = this.dao.getForm(causeMap);
		FormPropertyService fps = SpringContextHolder.getBean(FormPropertyService.class);
		List<FormFieldDTO> ff = fps.getFormFieldByFormId(df.getId());
		return ff;
	}
	
	/**
	 * 根据menuId清除form表中的menuId
	 * @param menuId
	 */
	public void removeMenuId(String menuId){
		this.dao.removeMenuId(menuId);
	}

	/**
	 * 根据menuId获得实体名
	 * @param menuId
	 * @return
	 */
	public String getEntityCode(String menuId){
		Map map = new HashMap();
		map.put("menuId", menuId);
		DevForm df = this.dao.getForm(map);
		EntityService es = SpringContextHolder.getBean(EntityService.class);
		Entity entity = es.getById(df.getEntityId());
		return entity.getEntityCode();
	}
	
	/**
	 * 根据menuId获得formName
	 * @param menuId
	 * @return
	 */
	public String getFormName(String menuId){
		Map map = new HashMap();
		map.put("menuId", menuId);
		DevForm df = this.dao.getForm(map);
		return df.getFormName();
	}
	
	/**
	 * 根据菜单id，获得所有的子表单对象
	 * @param menuId
	 * @return
	 */
	public List<FormFieldDTO> getChildren(String menuId){
		Map map = new HashMap();
		map.put("menuId", menuId);
		DevForm df = this.dao.getForm(map);
		EntityService es = SpringContextHolder.getBean(EntityService.class);
		return null;
	}
	
	/**
	 * 根据表名返回表单父关联
	 * @param entityCode
	 * @return Map({key:"isRelation",value:"0或者1"},{key:"parentCode",value:"父表名"})
	 */
	public Map<String, String> getParentInfo(String entityCode){
		EntityService es = SpringContextHolder.getBean(EntityService.class);
		Entity entity = es.getEntityByCode(entityCode);
		String parentId = entity.getParentId();
		
		if(parentId==null||"".equals(parentId)){
			return null;
		}
		Map causeMap = new HashMap();
		causeMap.put("entityId", parentId);
		DevForm df = this.dao.getForm(causeMap);
		Map<String, String> map = new HashMap<String, String>();
		map.put("isRelation", df.getIsRelation()==null?"0":"1");
		Entity pEnty = es.getById(parentId);
		map.put("parentCode", pEnty.getEntityCode());
		causeMap = null;
		pEnty = null;
		es = null;
		entity = null;
		df = null;
		return map;
	}
	/**
	 * 根据表名返回子表名的列表
	 * @param entityCode
	 * @return
	 */
	public List<String> getChildrenCode(String entityCode){
		EntityService es = SpringContextHolder.getBean(EntityService.class);
		Entity entity = es.getEntityByCode(entityCode);
		List<Entity> list = es.getChildrenList(entity.getId());
		if(list!=null&&list.size()>0){
			List<String> li = new ArrayList<String>();
			for(Entity ety :list){
				li.add(ety.getEntityCode());
			}	
			return li;
		}		
		return null;
	}
	
	/**
	 * 判断表单是否已经存在
	 * @param formName
	 * @return
	 */
	public boolean isExists(String formName){
		Map map = new HashMap();
		map.put("formName", formName);
		Integer count = this.dao.count(map);
		if(count>0){
			return true;
		}
		return false;
	}
}
