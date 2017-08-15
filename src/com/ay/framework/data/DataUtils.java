package com.ay.framework.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.shiro.SystemMenu;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.pojo.DataType;
import com.ay.jfds.dev.pojo.MenuVO;
import com.ay.jfds.dev.service.DataService;
import com.ay.jfds.dev.service.DataTypeService;
import com.ay.jfds.dev.service.MenuService;
import com.ay.jfds.icon.pojo.SysIcon;
import com.ay.jfds.icon.service.SysIconService;
/**
 * 加载所有元数据 并封装Map 加载所有按钮图片并放到内存当中
 * @author Thor
 *
 */
@Component
public  class DataUtils {
	private static Logger logger = LoggerFactory.getLogger(DataUtils.class);
	/**
	 * 所有datas 按照type
	 */
	public static  Map<String, Map<String,Data>> dataAll;
	public static Map<String,byte[]> menuIcons;
	private static DataService dataService;
	private static DataTypeService dataTypeService;
	private static SysIconService sysIconService;
	@Resource
	public  void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
	@Resource
	public  void setDataTypeService(DataTypeService dataTypeService) {
		this.dataTypeService = dataTypeService;
	}
	@Resource
	public void setSysIconService(SysIconService sysIconService) {
		this.sysIconService = sysIconService;
	}
	
	/**
	 * 初始化所有datas
	 */
	@PostConstruct
	public static void init(){
		dataAll=new HashMap<String, Map<String,Data>>();
		//获得所有DataType
		List<DataType> dataTypes=dataTypeService.findAllDataTypes();
		List<Data> datas=dataService.findAllDatas();
		for(DataType dataType:dataTypes){
			Map<String, Data> data=new HashMap<String, Data>();
			for(Data d:datas){
				if(dataType.getId().equals(d.getTypeId())){
					data.put(d.getDataCode(), d);
				}
			}
			dataAll.put(dataType.getId(), data);
		}
		logger.info("data init complete");
	}
	@PostConstruct
	public static void initIcon(){
		menuIcons=new HashMap<String, byte[]>();
		List<SysIcon> icons=sysIconService.findAll();
		for(SysIcon icon:icons){
			menuIcons.put(icon.getIconNo(), icon.getIcon());
		}
	}
	/**
	 * 通过编号获取当前图片
	 * @param iconNo
	 * @return
	 */
	public static byte[] getIcon(String iconNo){
		return menuIcons.get(iconNo);
	}
	/**
	 * 根据当前的typeID获得下面的所有节点 没有封装成树  需要自己手动封装
	 * @param typeId
	 * @return
	 */
	public static List<Data> getByTypeId(String typeId){
		List<Data> datas=new ArrayList<Data>();
		Map<String,Data> dataType=null;
		for(String key:dataAll.keySet()){
			if(key.equals(typeId)){
				dataType=dataAll.get(key);//找到当前的
				break;
			}
		}
		for(String key:dataType.keySet()){
			datas.add(dataType.get(key));
		}
		return datas;
	}
	/**
	 * 通过typeId和code码 获取到对应的名字 查找速度快 精确
	 * @param typeId
	 * @param code
	 * @return
	 */
	public static String getByTypeOrCode(String typeId,String code){
		Map<String,Data> data=dataAll.get(typeId);
		return data.get(code).getDataName();
	}
	/**
	 * 通过code获取对应的 name 如果code有重复 只会取到第一个 查询速稍慢度慢 不精确
	 * @param code
	 * @return
	 */
	public static String getByCode(String code){
		for(String key:dataAll.keySet()){
			for(String data:dataAll.get(key).keySet()){
				Data d=dataAll.get(key).get(data);
				if(d.getDataCode().equals(code)) 
					return d.getDataName();
			}
		}
		return "";
	}
	/**
	 * 通过typeId查询当前下面的所有parent等于-1的
	 * @return 
	 */
	public static List<Data> getByTypeTree(String typeId){
		List<Data> datas=getByTypeId(typeId);
		List<Data> list=new ArrayList<Data>();
		for(Data d:datas){
			if(d.getParentId().equals("-1")){
				list.add(d);
			}
		}
		return list;	
	}
	
	/**
	 * 递归实现多级菜单
	 * @param listTree
	 * @param datas
	 */
	private static void dataTree(List<Data> listTree,List<Data> datas,Data data){
		for(Data d:datas){
			if(d.getParentId().equals("-1")){
				//dataTree(d.getDatas(),datas);
			}
			listTree.add(d);
		}
	}
}
