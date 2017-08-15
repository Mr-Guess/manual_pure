package com.ay.framework.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.jfds.dev.pojo.Data;
import com.ay.jfds.dev.pojo.DataType;
import com.ay.jfds.dev.service.DataService;
import com.ay.jfds.dev.service.DataTypeService;

/**
 * 通过读取文件 自动插入元数据 自动扫描
 * @author Thor
 *
 */
public class DateAutoInsert {
	private static List<DataType> dataTypes=new ArrayList<DataType>();
	/**一级菜单*/
	private static final String LEVEL1="level1";
	/**一级菜单*/
	private static final String LEVEL2="level2";
	/**一级菜单*/
	private static final String LEVEL3="level3";
	/**四级菜单*/
	private static final String LEVEL4="level4";
	/**
	 * 
	 * @param path txt路径
	 */
	public static void readTxt(String path){
		DataType dataType=new DataType();
		dataType.setId("yjjyzblb");
		dataType.setTypeName("应急救援装备类别");
		dataTypes.add(dataType);
		try {
            String encoding="GBK";
            File file=new File(path);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                Data level1=null;	//一级菜单
                Data level2=null;	//二级菜单
                Data level3=null;
                int order1=1;		//一级排序
                int order2=1;		//二级排序	
                int order3=1;		//三级排序
                int order4=1;
                int count=1;
                while((lineTxt = bufferedReader.readLine()) != null){
                	if(lineTxt.indexOf(LEVEL1)!=-1){
                		level1=autoData(lineTxt,LEVEL1);
                		level1.setDataOrder(order1);
                		level1.setParentId("-1");
                		level1.setTypeId(dataTypes.get(0).getId());
                		level1.setId(StringUtil.getUUID());
                		System.out.println("leve1"+level1);
                		order2=1; //下一个菜单 排序归位
                		order1++;
                		SpringContextHolder.getBean(DataService.class).insert(level1);
                	}else if(lineTxt.indexOf(LEVEL2)!=-1){
                		
            			level2=autoData(lineTxt, LEVEL2);
            			level2.setDataOrder(order2);
                		level2.setParentId(level1.getId());
                		level2.setTypeId(dataTypes.get(0).getId());
                		level2.setId(StringUtil.getUUID());
                		System.out.println("leve2"+level2);
                		order2++;
                		order3=1;
                		SpringContextHolder.getBean(DataService.class).insert(level2);
                		
                	}else if(lineTxt.indexOf(LEVEL3)!=-1){
            			level3=autoData(lineTxt, LEVEL3);
            			level3.setDataOrder(order3);
            			level3.setParentId(level2.getId());
            			level3.setTypeId(dataTypes.get(0).getId());
                		System.out.println("leve3"+level3);
                		order3++;
                		order4=1;
                    	SpringContextHolder.getBean(DataService.class).insert(level3);
                		
                	}else{
            			Data data=autoData(lineTxt, LEVEL4);
            			data.setDataOrder(order4);
            			data.setParentId(level3.getId());
            			data.setTypeId(dataTypes.get(0).getId());
                		System.out.println("leve4"+data);
                		order4++;
                    	SpringContextHolder.getBean(DataService.class).insert(data);
                		
                	}
                	System.out.println(count++);
                }
                read.close();
		    }else{
		        System.out.println("找不到指定的文件");
		    }
	    } catch (Exception e) {
	        System.out.println("读取文件内容出错");
	        e.printStackTrace();
	    }
	}
	
	public static Data autoData(String lineTxt,String level){
		int index=lineTxt.indexOf(level);
		String code=lineTxt.substring(0,lineTxt.indexOf(level)-1);
		String typeName=lineTxt.substring(index+level.length()+1,lineTxt.length());
		code="ZBLB"+code;
		return new Data(code,typeName);
	}
	
	public static void main(String[] args) {
		DateAutoInsert.readTxt("E:\\datas\\data6.txt");
	}
	
}
