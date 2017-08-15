package generate.tool.template;

import java.util.*;

public class BaseJspTemplate implements ITemplate
{
  protected static String nl;
  public static synchronized BaseJspTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    BaseJspTemplate result = new BaseJspTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>" + NL + "<%@ include file=\"/common/taglibs.jsp\" %>" + NL + "<%@ include file=\"/common/jqueryhead.jsp\" %>" + NL + "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" + NL + "<html>" + NL + "<head>" + NL + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + NL + "<title>";
  protected final String TEXT_2 = "</title>" + NL + "" + NL + "<link rel=\"stylesheet\" type=\"text/css\" href=\"${ctx}/css/newCrud.css\">" + NL + "<script type=\"text/javascript\" src=\"${ctx}/js/DataGridUtil.js\"></script>" + NL + "<script type=\"text/javascript\" src=\"${ctx}/js/CrudUtil.js\"></script>" + NL + "<script type=\"text/javascript\">" + NL + "        var grid = null;" + NL + "        var tree = null;" + NL + "        var key = null;" + NL + "" + NL + "   $(document).ready(function () {" + NL + "\t\t grid = new Grid('${ctx}/";
  protected final String TEXT_15 = "/";
  protected final String TEXT_16 = "Action!list','data_list');" + NL + "\t\t grid.loadGrid();" + NL + "\t\t crud = new Crud({" + NL + "\t\t\t\t grid:grid," + NL + "\t\t\t\t addFormObject:$('#curdaddForm')," + NL + "\t\t\t\t viewFormObject:$('#curdviewForm')," + NL + "\t\t\t\t updateFormObject:$('#curdupdateForm')," + NL  + "\t\t\t\t searchFormObject:$('#searchForm')," + NL + "\t\t\t\t entityName:'";
  protected final String TEXT_16_1 = "";
  protected final String TEXT_16_2 = "'," + NL + "\t\t\t\t moduleName:'";
  protected final String TEXT_16_3 = "', " + NL + "\t\t\t\t dialogCss:{width:'900px',height:'auto'}," + NL + "\t\t\t\t url:'${ctx}/";
  protected final String TEXT_16_4 = "/";
  protected final String TEXT_16_5 = "Action'" + NL + "\t\t\t\t});" + NL + "\t\t});" + NL + "        function gridFormatterLength(value, rowData, rowIndex) {" + NL + "        \tif(value==null || value == '' || value == 'undefined')" + NL + "        \t\treturn '';" + NL + "    \t\tif(value.length > 25)" + NL + "    \t\t\treturn value.substring(0, 25) + '...';" + NL + "    \t\treturn value;" + NL + "    \t}" + NL + "" + NL + "        function gridFormatter(value, rowData, rowIndex) {" + NL + "        \tvar rowId = rowData.id;" + NL + "        \tvar url = \"\";" + NL + "        \t\t<shiro:hasPermission name='${menuId}:view'> " + NL + "        \turl += \"<input type='button' class='btn1' onclick='crud.view(\\\"\" + rowId + \"\\\");' value='查看'/>&nbsp;&nbsp;\";" + NL + "        \t\t</shiro:hasPermission>" + NL + "        \t\t<shiro:hasPermission name='${menuId}:update'>" + NL + "        \turl += \"<input type='button' class='btn1' onclick='crud.update(\\\"\" + rowId + \"\\\");' value='修改'/>&nbsp;&nbsp;\";" +  NL + "        \t\t</shiro:hasPermission> " +  NL + "        \treturn url;" + NL + "        }" + NL + "        " + NL + "        function cleanFunction(){ " + NL + "        crud.clearSearch(); " + NL + "        crud.search(); " + NL + "        }" + NL + "        " + NL + "        function resizeGrid(){ " + NL + "        $('#data_list').datagrid('resize', { " + NL + "        width:function(){" + NL + "        return document.body.clientWidth*0.9;" + NL + "        } " + NL + "        }); " + NL + "        }" + NL + "        " + NL + "</script>" + NL + "</head>" + NL ;
  protected final String TEXT_16_6 = "<body onresize=\"resizeGrid();\">" + NL + "" + NL + "" + NL + "" + NL + "<div style=\"margin: auto;\">" + NL + "\t <div id = \"toolbar\">" + NL + "\t<form id=\"searchForm\" style=\"margin: 0;\">" + NL + "\t";
  protected final String TEXT_17 = NL + "\t&nbsp;&nbsp;";
  protected final String TEXT_18 = ":&nbsp;&nbsp;<input type=\"text\" name=\"";
  protected final String TEXT_19 = ".";
  protected final String TEXT_20 = "\"/>";
  protected final String TEXT_21 = NL + "\t" + NL + "\t&nbsp;&nbsp;" + NL + "\t</form>" + NL + "<div>" + NL + "\t <shiro:hasPermission name=\"${menuId}:add\">" + NL + "\t\t <a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add', plain:true\" onclick=\"crud.add();\">添加</a>" + NL + "\t </shiro:hasPermission>" + NL + "\t <shiro:hasPermission name=\"${menuId}:delete\">" + NL + "\t\t <a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-remove', plain:true\" onclick=\"crud.remove();\">删除</a>" + NL +"\t </shiro:hasPermission>" + NL + "\t<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search', plain:true\" onclick=\"crud.search();\">查询</a>" +  NL + "\t<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-cancel', plain:true\" onclick=\"cleanFunction();\">清空</a>" +  NL + "\t</div>" + NL + "</div>" + NL + NL + "<div style=\"height: auto;\">" + NL + "    <table id=\"data_list\" title = \"";
  protected	final String TEXT_21_1 = "\" loadMsg=\"正在努力拉取数据中...\" toolbar=\"#toolbar\" fitColumns=\"true\">" + NL + "        <thead>" + NL + "        <tr>" + NL + "\t\t\t";
  protected final String TEXT_22 = NL + "            <th align=\"center\" field=\"";
  protected final String TEXT_23 = "\" width=\"150\" formatter=\"gridFormatterLength\" >";
  protected final String TEXT_24 = "</th>";
  protected final String TEXT_25 = NL + "            <th align=\"center\" field=\"id\" width=\"120\" formatter=\"gridFormatter\">操作</th>         " + NL + "        </tr>" + NL + "        </thead>" + NL + "    </table>" + NL + "</div>" + NL + "" + NL; 
  
  // 拼接添加窗口
  protected final String TEXT_ADDSTART = "\t<!-- 添加窗口 -->" + NL + "\t<div style=\"display: none;\">" + NL + "\t\t <div id=\"curdaddForm\" style=\"width: 70%; height: 200px;\" data-options=\"iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false\">" + NL + "\t\t<form id=\"addForm\" method=\"post\">" + NL + "\t\t\t<input type=\"hidden\" name=\"";
  protected final String TEXT_26 = ".id\"/>" + NL + "\t\t\t<div style=\"padding: 10px 20px 10px 20px;\" align=\"center\" name=\"addDiv\">" + NL + "\t\t\t\t<table class=\"detailTable\" cellpadding=\"5px\" style=\"font-size:12px;\" cellspacing=\"1\"  border=\"0\" bgcolor=\"#aed0ea\" width=\"95%\">" + NL + "\t\t\t\t\t";
  protected final String TEXT_27 = NL + "\t\t\t\t\t\t<tr>" + NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\" width = \"20%\">";
  protected final String TEXT_28 = ":</td>" + NL + "\t\t\t\t\t\t\t<td class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<input type=\"text\" class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_29 = ".";
  protected final String TEXT_30 = "\"" + NL + "\t\t\t\t\t\t\tdata-options=\"validType:'maxByteLength[100]'\" />" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_31 = NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\">";
  protected final String TEXT_32 = ":</td>" + NL + "\t\t\t\t\t\t\t<td class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<input type=\"text\" class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_33 = ".";
  protected final String TEXT_34 = "\"" + NL + "\t\t\t\t\t\t\tdata-options=\"validType:'maxByteLength[100]'\" />" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_35 = NL + "\t\t\t\t\t\t<tr>" + NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\">";
  protected final String TEXT_36 = ":</td>" + NL + "\t\t\t\t\t\t\t<td colspan=\"3\" class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<textarea class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_37 = ".";
  protected final String TEXT_38 = "\"" + NL + "\t\t\t\t\t\t\t style=\"width: 80%; height: 80px;\" data-options=\"validType:'maxByteLength[255]'\">" + NL + "\t\t\t\t\t\t\t</textarea>" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_39 = NL + "\t\t\t\t\t\t\t<td class=\"table_con\" width=\"30%\" colspan=\"2\">" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>" + NL + "\t\t\t\t\t\t<tr>" + NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\">";
  protected final String TEXT_40 = ":</td>" + NL + "\t\t\t\t\t\t\t<td colspan=\"3\" class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<textarea class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_41 = ".";
  protected final String TEXT_42 = "\"" + NL + "\t\t\t\t\t\t\t style=\"width: 80%; height: 80px;\" data-options=\"validType:'maxByteLength[255]'\">" + NL + "\t\t\t\t\t\t\t</textarea>" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>";
  protected final String TEXT_43 = NL + "\t\t\t\t</table>" + NL + "\t\t\t</div>" + NL + "\t\t</form>" + NL + "\t</div>" + NL + "\t" + NL + "\t</div>" + NL + "</div>" + NL + "\t" + NL;
  
  
  // 拼接修改窗口
  protected final String TEXT_UPDATESTART = "\t<!-- 更新窗口 -->" + NL + "\t<div style=\"display: none;\">" + NL + "\t\t <div id=\"curdupdateForm\" style=\"width: 70%; height: 200px;\" data-options=\"iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false\">" + NL + "\t\t<form id=\"updateForm\" method=\"post\">" + NL + "\t\t\t<input type=\"hidden\" name=\"";
  protected final String TEXT_26_UPDATE = ".id\"/>" + NL + "\t\t\t<div style=\"padding: 10px 20px 10px 20px;\" align=\"center\" name=\"updateDiv\">" + NL + "\t\t\t\t<table class=\"detailTable\" cellpadding=\"5px\" style=\"font-size:12px;\" cellspacing=\"1\"  border=\"0\" bgcolor=\"#aed0ea\" width=\"95%\">" + NL + "\t\t\t\t\t";
  protected final String TEXT_27_UPDATE = NL + "\t\t\t\t\t\t<tr>" + NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\" width = \"20%\">";
  protected final String TEXT_28_UPDATE = ":</td>" + NL + "\t\t\t\t\t\t\t<td class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<input type=\"text\" class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_29_UPDATE = ".";
  protected final String TEXT_30_UPDATE = "\"" + NL + "\t\t\t\t\t\t\tdata-options=\"validType:'maxByteLength[100]'\" />" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_31_UPDATE = NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\">";
  protected final String TEXT_32_UPDATE = ":</td>" + NL + "\t\t\t\t\t\t\t<td class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<input type=\"text\" class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_33_UPDATE = ".";
  protected final String TEXT_34_UPDATE = "\"" + NL + "\t\t\t\t\t\t\tdata-options=\"validType:'maxByteLength[100]'\" />" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_35_UPDATE = NL + "\t\t\t\t\t\t<tr>" + NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\">";
  protected final String TEXT_36_UPDATE = ":</td>" + NL + "\t\t\t\t\t\t\t<td colspan=\"3\" class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<textarea class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_37_UPDATE = ".";
  protected final String TEXT_38_UPDATE = "\"" + NL + "\t\t\t\t\t\t\t style=\"width: 80%; height: 80px;\" data-options=\"validType:'maxByteLength[255]'\">" + NL + "\t\t\t\t\t\t\t</textarea>" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_39_UPDATE = NL + "\t\t\t\t\t\t\t<td class=\"table_con\" width=\"30%\" colspan=\"2\">" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>" + NL + "\t\t\t\t\t\t<tr>" + NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\">";
  protected final String TEXT_40_UPDATE = ":</td>" + NL + "\t\t\t\t\t\t\t<td colspan=\"3\" class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<textarea class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_41_UPDATE = ".";
  protected final String TEXT_42_UPDATE = "\"" + NL + "\t\t\t\t\t\t\t style=\"width: 80%; height: 80px;\" data-options=\"validType:'maxByteLength[255]'\">" + NL + "\t\t\t\t\t\t\t</textarea>" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>";
  protected final String TEXT_43_UPDATE = NL + "\t\t\t\t</table>" + NL + "\t\t\t</div>" + NL + "\t\t</form>" + NL + "\t</div>" + NL + "\t" + NL + "\t</div>" + NL +  "\t" + NL;
  
  //拼接查看窗口
  protected final String TEXT_VIEWSTART = "\t<!-- 查看窗口 -->" + NL + "\t<div style=\"display: none;\">" + NL + "\t\t <div id=\"curdviewForm\" style=\"width: 70%; height: 200px;\" data-options=\"iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false\">" + NL + "\t\t<form id=\"viewForm\" method=\"post\">" + NL + "\t\t\t<input type=\"hidden\" name=\"";
  protected final String TEXT_26_VIEW = ".id\"/>" + NL + "\t\t\t<div style=\"padding: 10px 20px 10px 20px;\" align=\"center\" name=\"viewDiv\">" + NL + "\t\t\t\t<table class=\"detailTable\" cellpadding=\"5px\" style=\"font-size:12px;\" cellspacing=\"1\"  border=\"0\" bgcolor=\"#aed0ea\" width=\"95%\">" + NL + "\t\t\t\t\t";
  protected final String TEXT_27_VIEW = NL + "\t\t\t\t\t\t<tr>" + NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\" width = \"20%\">";
  protected final String TEXT_28_VIEW = ":</td>" + NL + "\t\t\t\t\t\t\t<td class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<span name=\"";
  protected final String TEXT_29_VIEW = ".";
  protected final String TEXT_30_VIEW = "\"></span>" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_31_VIEW = NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\">";
  protected final String TEXT_32_VIEW = ":</td>" + NL + "\t\t\t\t\t\t\t<td class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<span name=\"";
  protected final String TEXT_33_VIEW = ".";
  protected final String TEXT_34_VIEW = "\"></span>" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_35_VIEW = NL + "\t\t\t\t\t\t<tr>" + NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\">";
  protected final String TEXT_36_VIEW = ":</td>" + NL + "\t\t\t\t\t\t\t<td colspan=\"3\" class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<span name=\"";
  protected final String TEXT_37_VIEW = ".";
  protected final String TEXT_38_VIEW = "\"></span>" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_39_VIEW = NL + "\t\t\t\t\t\t\t<td class=\"table_con\" width=\"30%\" colspan=\"2\">" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>" + NL + "\t\t\t\t\t\t<tr>" + NL + "\t\t\t\t\t\t\t<td class=\"table_nemu_new\" width=\"20%\">";
  protected final String TEXT_40_VIEW = ":</td>" + NL + "\t\t\t\t\t\t\t<td colspan=\"3\" class=\"table_con\" width=\"30%\">" + NL + "\t\t\t\t\t\t\t<span name=\"";
  protected final String TEXT_41_VIEW = ".";
  protected final String TEXT_42_VIEW = "\"></span>" + NL + "\t\t\t\t\t\t\t</td>" + NL + "\t\t\t\t\t\t</tr>";
  protected final String TEXT_43_VIEW = NL + "\t\t\t\t</table>" + NL + "\t\t\t</div>" + NL + "\t\t</form>" + NL + "\t</div>" + NL + "\t" + NL + "</div>" + NL + "\t" + NL;
   
  
  
  // HTML结束
  protected final String TEXT_FINAL = "</body>" + NL + "</html>";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	Object[] o=(Object[])argument;
	String tableName = o[0].toString();
	List<String> fieldNames=(List<String>)o[3];
	Map<String,String> chineseNameMap=(Map<String,String>)o[5];
	String lowerClassName = o[7].toString();
	Map<String,Boolean> multiLineMap=(Map<String,Boolean>)o[8];
	boolean flag = true;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(chineseNameMap.get(tableName) );
    stringBuffer.append(TEXT_2);
    //stringBuffer.append(lowerClassName);
    /*stringBuffer.append(TEXT_3);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_14);*/
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_16_1);
    stringBuffer.append(TEXT_16_2);
    stringBuffer.append(chineseNameMap.get(tableName) );
    stringBuffer.append(TEXT_16_3);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_16_4);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_16_5);
    stringBuffer.append(TEXT_16_6);
    for(int i=0;i<fieldNames.size();i++){ 
	String fieldName = fieldNames.get(i);
	
    stringBuffer.append(TEXT_17);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_18);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_20);
    }
    stringBuffer.append(TEXT_21);
    stringBuffer.append(chineseNameMap.get(tableName));
    stringBuffer.append(TEXT_21_1);
    for(int i=0;i<fieldNames.size();i++){ 
			String fieldName = fieldNames.get(i);
			
    stringBuffer.append(TEXT_22);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_24);
    }
    stringBuffer.append(TEXT_25);
    
    // 添加窗口拼接
    stringBuffer.append(TEXT_ADDSTART);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_26);
    
					flag = true; //是否是一行开始
					for(int i=0;i<fieldNames.size();i++){ 
					String fieldName = fieldNames.get(i);
					boolean isMultiLine = multiLineMap.get(fieldName);
					
    if(flag && !isMultiLine){
    stringBuffer.append(TEXT_27);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_28);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_30);
    
							flag = !flag;
							} else if(!flag && !isMultiLine) {
						
    stringBuffer.append(TEXT_31);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_32);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_34);
    
							flag = !flag;
							} else if(flag && isMultiLine) {
						
    stringBuffer.append(TEXT_35);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_36);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_38);
    
							flag = true;
							} else if(!flag && isMultiLine) {
						
    stringBuffer.append(TEXT_39);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_40);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_42);
    
							flag = true;
							}
						
    }
    stringBuffer.append(TEXT_43);
    
    // 修改窗口拼接
    stringBuffer.append(TEXT_UPDATESTART);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_26_UPDATE);
    
					flag = true; //是否是一行开始
					for(int i=0;i<fieldNames.size();i++){ 
					String fieldName = fieldNames.get(i);
					boolean isMultiLine = multiLineMap.get(fieldName);
					
    if(flag && !isMultiLine){
    stringBuffer.append(TEXT_27_UPDATE);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_28_UPDATE);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_29_UPDATE);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_30_UPDATE);
    
							flag = !flag;
							} else if(!flag && !isMultiLine) {
						
    stringBuffer.append(TEXT_31_UPDATE);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_32_UPDATE);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_33_UPDATE);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_34_UPDATE);
    
							flag = !flag;
							} else if(flag && isMultiLine) {
						
    stringBuffer.append(TEXT_35_UPDATE);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_36_UPDATE);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_37_UPDATE);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_38_UPDATE);
    
							flag = true;
							} else if(!flag && isMultiLine) {
						
    stringBuffer.append(TEXT_39_UPDATE);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_40_UPDATE);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_41_UPDATE);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_42_UPDATE);
    
							flag = true;
							}
						
    }
    stringBuffer.append(TEXT_43_UPDATE);
    
    
    // 查看窗口拼接
    stringBuffer.append(TEXT_VIEWSTART);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_26_VIEW);
    
					flag = true; //是否是一行开始
					for(int i=0;i<fieldNames.size();i++){ 
					String fieldName = fieldNames.get(i);
					boolean isMultiLine = multiLineMap.get(fieldName);
					
    if(flag && !isMultiLine){
    stringBuffer.append(TEXT_27_VIEW);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_28_VIEW);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_29_VIEW);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_30_VIEW);
    
							flag = !flag;
							} else if(!flag && !isMultiLine) {
						
    stringBuffer.append(TEXT_31_VIEW);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_32_VIEW);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_33_VIEW);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_34_VIEW);
    
							flag = !flag;
							} else if(flag && isMultiLine) {
						
    stringBuffer.append(TEXT_35_VIEW);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_36_VIEW);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_37_VIEW);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_38_VIEW);
    
							flag = true;
							} else if(!flag && isMultiLine) {
						
    stringBuffer.append(TEXT_39_VIEW);
    stringBuffer.append(chineseNameMap.get(fieldName));
    stringBuffer.append(TEXT_40_VIEW);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_41_VIEW);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_42_VIEW);
    
							flag = true;
							}
						
    }
    stringBuffer.append(TEXT_43_VIEW);
    
    
    
    //HTML结束
    stringBuffer.append(TEXT_FINAL);
    return stringBuffer.toString();
  }
}
