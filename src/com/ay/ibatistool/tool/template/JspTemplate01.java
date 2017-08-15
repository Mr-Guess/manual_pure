package com.ay.ibatistool.tool.template;

import java.util.*;

public class JspTemplate01
{
  protected static String nl;
  public static synchronized JspTemplate01 create(String lineSeparator)
  {
    nl = lineSeparator;
    JspTemplate01 result = new JspTemplate01();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"" + NL + "    pageEncoding=\"UTF-8\"%>" + NL + "<%@ include file=\"/common/taglibs.jsp\" %>" + NL + "<%@ include file=\"/common/jqueryhead.jsp\" %>" + NL + "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" + NL + "<html>" + NL + "<head>" + NL + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + NL + "<title>XXX</title>" + NL + "<script type=\"text/javascript\" src=\"${ctx}/js/GridUtil.js\"></script>" + NL + "<script type=\"text/javascript\" src=\"${ctx}/js/tree/Tree.js\"></script>" + NL + "<style>" + NL + ".top{border:1px solid #aed0ea;background:#f2f5f7 url('images/ui-bg_glass_100_e4f1fb_1x400.png') repeat-x;line-height:28px;padding-left:15px;font-size:14px;font-weight:bold;color:#222222;}" + NL + ".table_title{border:1px solid #cccccc;background:#efefef;font-size:14px;font-weight:bold;color:#2779aa;line-height:28px;padding-left:15px;}" + NL + ".table_nemu{border:0px;background:#E3F1FA;font-size:13px;color:#222222;text-align:right;line-height:28px;padding-right:15px;width:40%}" + NL + ".table_nemu span{font-size:15px;color:red;font-weight:bold;}" + NL + ".table_con{border:0px;background:#fff;line-height:28px;padding-left:15px;width:60%}" + NL + ".btn1{" + NL + "BORDER: #65b5e4 1px solid; PADDING-RIGHT: 6px;  PADDING-LEFT: 6px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#d7ebf9); CURSOR: hand; COLOR: black; PADDING-TOP: 3px; " + NL + "}   " + NL + "#tb{line-height:32px;height:35px;padding:10px;font-size:12px;font-weight:bold;color:#2779aa;}" + NL + "</style>" + NL + "<script type=\"text/javascript\">" + NL + "        var grid = null;" + NL + "        var tree = null;" + NL + "        var key = null;" + NL + "" + NL + "\t\t<shiro:hasPermission name=\"${menuId}:add\">" + NL + "        // 当点击添加按钮时做的操作" + NL + "        function addFunction() {" + NL + "            $('#addWin').window('open');" + NL + "            clearAddForm();" + NL + "        }" + NL + "        " + NL + "        // 提交添加菜单请求" + NL + "        function addSubmitForm() {" + NL + "            $('#addForm').form({" + NL + "                url:'${ctx}/";
  protected final String TEXT_2 = "/";
  protected final String TEXT_3 = "Action!add'," + NL + "                success:function (data) {" + NL + "                \tvar data = eval('(' + data + ')');" + NL + "\t\t\t\t\tcloseAllWin();" + NL + "                    if (data.operateSuccess) {" + NL + "                    \t$.sysMsg('成功', data.operateMessage);" + NL + "                        grid.reloadGrid();" + NL + "                    } else {" + NL + "                    \t$.sysMsg('失败', data.operateMessage);" + NL + "                    }" + NL + "                }" + NL + "            });" + NL + "            $('#addForm').submit();" + NL + "        }" + NL + "        " + NL + "        // 清除添加表单中的数据" + NL + "        function clearAddForm() {" + NL + "        \t$('#addWin input').val('');" + NL + "        }        " + NL + "        </shiro:hasPermission>" + NL + "        " + NL + "        <shiro:hasPermission name=\"${menuId}:update\">" + NL + "        function editFunction(obj) {" + NL + "        \t$('#addWin').window('open');" + NL + "        \tvar rowId = $(obj).attr(\"rowId\");" + NL + "            $.ajax({" + NL + "                url:'${ctx}/";
  protected final String TEXT_4 = "/";
  protected final String TEXT_5 = "Action!getById?id=' + rowId," + NL + "                method:'POST'," + NL + "                success:function (data) {" + NL + "                \t$.each(data,function(i,item){" + NL + "\t                \t$('#addWin [name=\"";
  protected final String TEXT_6 = ".'+i+'\"]').val(item);" + NL + "                    });" + NL + "                }" + NL + "            });" + NL + "        }" + NL + "        " + NL + "        function updateSubmitForm() {" + NL + "            $('#updateForm').form({" + NL + "                url:'${ctx}/";
  protected final String TEXT_7 = "/";
  protected final String TEXT_8 = "Action!update'," + NL + "                success:function (data) {" + NL + "                \tcloseAllWin();" + NL + "                \tvar data = eval('(' + data + ')');" + NL + "                    if (data.operateSuccess) {" + NL + "                    \t$.sysMsg('成功', data.operateMessage);" + NL + "                        grid.reloadGrid();" + NL + "                    } else {" + NL + "                    \t$.sysMsg('失败', data.operateMessage);" + NL + "                    }" + NL + "                }" + NL + "            });" + NL + "            $('#updateForm').submit();" + NL + "        }" + NL + "        " + NL + "        // 清除表单中的数据" + NL + "        function clearUpdateForm() {" + NL + "        \t$('#updateWin input').val('');" + NL + "        }  " + NL + "        " + NL + "        // 填充更新表单中的数据" + NL + "        function fillUpdateForm( ) {" + NL + "        \tvar rowsid;" + NL + "        \tif(typeof value==\"undefined\"){" + NL + "            \tvar rows = grid.getSelectNode();" + NL + "            \trowsid=rows.id;" + NL + "        \t}" + NL + "        \telse{" + NL + "        \t\trowsid = value;" + NL + "        \t}" + NL + "            $.ajax({" + NL + "                url:'${ctx}/";
  protected final String TEXT_9 = "/";
  protected final String TEXT_10 = "Action!getById?id=' + rowsid," + NL + "                method:'POST'," + NL + "                success:function (data) {" + NL + "                \t$.each(data,function(i,item){" + NL + "\t                \t$('#updateWin [name=\"";
  protected final String TEXT_11 = ".'+i+'\"]').val(item);" + NL + "                    });" + NL + "                }" + NL + "            });" + NL + "        }              " + NL + "        </shiro:hasPermission>" + NL + "        " + NL + "        <shiro:hasPermission name=\"${menuId}:view\">" + NL + "        function viewFunction(obj) {" + NL + "        \t/*" + NL + "        \t$('#viewWin').window('open');" + NL + "        \tclearViewForm();" + NL + "        \tfillViewForm(rowId);" + NL + "        \t*/" + NL + "        \tvar rowId = $(obj).attr(\"rowId\");" + NL + "        \t$.ajax({" + NL + "                url:'${ctx}/";
  protected final String TEXT_12 = "/";
  protected final String TEXT_13 = "Action!getById?id=' + rowId," + NL + "                method:'POST'," + NL + "                success:function (data) {" + NL + "                \t$.each(data,function(i,item){" + NL + "\t                \t$('#addWin [name=\"";
  protected final String TEXT_14 = ".'+i+'\"]').val(item);" + NL + "                    });" + NL + "                }" + NL + "            });" + NL + "        }        " + NL + "        " + NL + "        // 清除查看表单中的数据" + NL + "        function clearViewForm() {" + NL + "        \t$('#viewWin input').val('');" + NL + "        }        " + NL + "        " + NL + "        // 填充查看菜单的内容" + NL + "        function fillViewForm(value) {" + NL + "        \tvar rowsid;" + NL + "        \tif(typeof value==\"undefined\"){" + NL + "            \tvar rows = grid.getSelectNode();" + NL + "            \trowsid=rows.id;" + NL + "        \t}" + NL + "        \telse{" + NL + "        \t\trowsid = value;" + NL + "        \t}" + NL + "            $.ajax({" + NL + "                url:'${ctx}/";
  protected final String TEXT_15 = "/";
  protected final String TEXT_16 = "Action!getById?id=' + rowsid," + NL + "                method:'POST'," + NL + "                success:function (data) {" + NL + "                \t$.each(data,function(i,item){" + NL + "\t                \t$('#viewWin span[name=\"";
  protected final String TEXT_17 = ".'+i+'\"]').html(item);" + NL + "                    });" + NL + "                }" + NL + "            });" + NL + "        }        " + NL + "        </shiro:hasPermission>" + NL + "" + NL + "\t\t<shiro:hasPermission name=\"${menuId}:delete\">" + NL + "        function removeFunction() {" + NL + "            var rows = grid.getSelectNodes();" + NL + "            if (rows.length == 0) {" + NL + "            \t$.sysMsg('提示', '请选择要删除的行');" + NL + "                return;" + NL + "            } else {" + NL + "            \tvar ids = new Array();" + NL + "                $.sysConfirm('警告', '是否要删除该记录?', function (r) {" + NL + "                    if (r) {" + NL + "                    \tvar i = 0;" + NL + "                    \tfor (i=0; i<rows.length; i++) {" + NL + "                            ids.push(rows[i].id);" + NL + "                        }" + NL + "                        $.ajax({" + NL + "                            url:'${ctx}/";
  protected final String TEXT_18 = "/";
  protected final String TEXT_19 = "Action!deleteByIds?ids=' + ids," + NL + "                            method:'POST'," + NL + "                            success:function(data) {" + NL + "                            \tvar data = eval('(' + data + ')');" + NL + "                                if (data.operateSuccess) {" + NL + "\t\t\t                    \t$.sysMsg('成功', data.operateMessage);" + NL + "                                    grid.reloadGrid();" + NL + "                                } else {" + NL + "\t\t\t                    \t$.sysMsg('失败', data.operateMessage);" + NL + "                                }" + NL + "                            }" + NL + "                        });" + NL + "                    }" + NL + "                });" + NL + "" + NL + "            }" + NL + "" + NL + "        }" + NL + "        </shiro:hasPermission>" + NL + "\t\t" + NL + "        function searchSubmitForm(){" + NL + "\t\t\t //提交查询表单" + NL + "\t\t\t $('#searchForm').form({" + NL + "\t\t\t     url:'${ctx}/";
  protected final String TEXT_20 = "/";
  protected final String TEXT_21 = "Action!list'," + NL + "\t\t\t     success:function (data) {" + NL + "\t\t\t     \tvar data = eval('(' + data + ')');" + NL + "\t\t\t\t\tcloseAllWin();" + NL + "\t\t\t        grid.reloadGridByData(data);" + NL + "\t\t\t     }" + NL + "\t\t\t });" + NL + "\t\t\t $('#searchForm').submit();" + NL + "\t\t\t$('#searchWin').window('close');" + NL + "        }" + NL + "" + NL + "        $(document).ready(" + NL + "                function () {" + NL + "                \t<shiro:hasPermission name=\"${menuId}:add\">" + NL + "                    $('#addWin').window({" + NL + "                        width:382," + NL + "                        height:485," + NL + "                        modal:true" + NL + "                    });" + NL + "                    </shiro:hasPermission>" + NL + "                    <shiro:hasPermission name=\"${menuId}:view\">" + NL + "                    $('#viewWin').window({" + NL + "                        width:382," + NL + "                        height:485," + NL + "                        modal:true" + NL + "                    });" + NL + "                    </shiro:hasPermission>" + NL + "                    $('#picWin').window({" + NL + "                        width:600," + NL + "                        height:485," + NL + "                        modal:true" + NL + "                    });" + NL + "                    <shiro:hasPermission name=\"${menuId}:update\">" + NL + "                    $('#updateWin').window({" + NL + "                        width:382," + NL + "                        height:485," + NL + "                        modal:true" + NL + "                    });" + NL + "                    </shiro:hasPermission>" + NL + "                    $('#searchWin').window({" + NL + "                        width:382," + NL + "                        height:485," + NL + "                        modal:true" + NL + "                    });" + NL + "                    " + NL + "                    <shiro:hasPermission name=\"${menuId}:add\">" + NL + "                    $('#addWin').window('close');" + NL + "                    </shiro:hasPermission>" + NL + "                    <shiro:hasPermission name=\"${menuId}:view\">" + NL + "                    $('#viewWin').window('close');" + NL + "                    </shiro:hasPermission>" + NL + "                    $('#picWin').window('close');" + NL + "                    <shiro:hasPermission name=\"${menuId}:update\">" + NL + "                    $('#updateWin').window('close');" + NL + "                    </shiro:hasPermission>" + NL + "                    $('#searchWin').window('close');" + NL + "                    grid = new Grid($(\"title\").html()+'列表', 'icon-edit'," + NL + "                            '${ctx}/";
  protected final String TEXT_22 = "/";
  protected final String TEXT_23 = "Action!list'," + NL + "                            'data_list', addFunction, editFunction," + NL + "                            viewFunction, removeFunction,searchFunction);" + NL + "                    grid.loadGrid();" + NL + "                });" + NL + "        " + NL + "      \t" + NL + "" + NL + "        function closeAllWin() {" + NL + "        \t<shiro:hasPermission name=\"${menuId}:add\">" + NL + "            $('#addWin').window('close');" + NL + "            </shiro:hasPermission>" + NL + "            <shiro:hasPermission name=\"${menuId}:view\">" + NL + "            $('#viewWin').window('close');" + NL + "            </shiro:hasPermission>" + NL + "            <shiro:hasPermission name=\"${menuId}:update\">" + NL + "            $('#updateWin').window('close');" + NL + "            </shiro:hasPermission>" + NL + "            $('#searchWin').window('close');" + NL + "        }" + NL + "        " + NL + "        " + NL + "        function searchFunction(){" + NL + "        \t$(\"#searchWin\").window(\"open\");        \t" + NL + "        }" + NL + "        " + NL + "        function gridFormatter(value, rowData, rowIndex) {" + NL + "        \tvar rowId = rowData.id;" + NL + "        \tvar rowName = rowData.entityCode;" + NL + "        \tvar url = \"\"" + NL + "        \t<shiro:hasPermission name=\"${menuId}:update\">" + NL + "        \t+ \"<input type='button' class='btn1' onclick='editFunction(this);' rowId='\" + rowId + \"' value='修改'\t/>\"" + NL + "        \t</shiro:hasPermission>" + NL + "        \t<shiro:hasPermission name=\"${menuId}:view\">" + NL + "        \t+ \"&nbsp;&nbsp;<input type='button' class='btn1' onclick='viewFunction(this);' rowId='\" + rowId + \"' value='查看'\t/>&nbsp;&nbsp;\";" + NL + "        \t</shiro:hasPermission>" + NL + "        \treturn url;" + NL + "        }" + NL + "        " + NL + "</script>" + NL + "</head>" + NL + "<body>" + NL + "" + NL + "<div style=\"height: 500px\">" + NL + "    <table id=\"data_list\" class=\"easyui-datagrid\" loadMsg=\"正在努力拉取数据中...\"" + NL + "           fit=\"true\" fitColumns=\"true\">" + NL + "        <thead>" + NL + "        <tr>" + NL + "        \t";
  protected final String TEXT_24 = NL + "            <th field=\"";
  protected final String TEXT_25 = "\" width=\"150\" align=\"center\" title=\"";
  protected final String TEXT_26 = "\">";
  protected final String TEXT_27 = "</th>";
  protected final String TEXT_28 = NL + "            <th align=\"center\" field=\"id\" width=\"160\" align=\"center\" formatter=\"gridFormatter\">操作</th>         " + NL + "        </tr>" + NL + "        </thead>" + NL + "    </table>" + NL + "</div>" + NL + "" + NL + "<shiro:hasPermission name=\"${menuId}:add\">" + NL + "<!-- 添加更新查看窗口 -->" + NL + "<div id=\"addWin\" iconCls=\"icon-save\">" + NL + "" + NL + "    <form style=\"padding: 10px 20px 10px 85px;\" id=\"addForm\" method=\"post\">" + NL + "    \t<input type=\"hidden\" name=\"";
  protected final String TEXT_29 = ".id\"/>" + NL + "    \t<table cellpadding=\"5px\" style=\"font-size:12px;\">" + NL + "    \t\t";
  protected final String TEXT_30 = NL + "    \t\t<tr>" + NL + "    \t\t\t<td>";
  protected final String TEXT_31 = "</td><td><input class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_32 = ".";
  protected final String TEXT_33 = "\"/></td>\t" + NL + "    \t\t</tr>" + NL + "    \t\t";
  protected final String TEXT_34 = NL + "    \t</table>" + NL + "        <div style=\"padding: 0px;\">" + NL + "            <a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" icon=\"icon-ok\" onclick=\"addSubmitForm()\">确定</a>" + NL + "            <a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" icon=\"icon-cancel\" onclick=\"closeAllWin()\">取消</a>" + NL + "        </div>" + NL + "    </form>" + NL + "" + NL + "</div>" + NL + "</shiro:hasPermission>" + NL + "" + NL + "<shiro:hasPermission name=\"${menuId}:view\">" + NL + "<!-- 查看窗口 -->" + NL + "<div id=\"viewWin\" iconCls=\"icon-save\" title=\"查看信息\">" + NL + "    <div style=\"padding: 10px 20px 10px 85px;\">" + NL + "    \t<table cellpadding=\"5px\" style=\"font-size:12px;\">" + NL + "    \t\t";
  protected final String TEXT_35 = NL + "    \t\t<tr>" + NL + "    \t\t\t<td>";
  protected final String TEXT_36 = "</td><td><span class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_37 = ".";
  protected final String TEXT_38 = "\"></span></td>\t" + NL + "    \t\t</tr>" + NL + "    \t\t";
  protected final String TEXT_39 = NL + "    \t</table>" + NL + "        <div style=\"padding: 0px;\">" + NL + "                <a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" icon=\"icon-cancel\" onclick=\"closeAllWin()\">关闭</a>" + NL + "        </div>" + NL + "    </div>" + NL + "</div>" + NL + "</shiro:hasPermission>" + NL + "" + NL + "<!-- 搜索窗口 -->" + NL + "<div id=\"searchWin\" iconCls=\"icon-save\" title=\"查询信息\">" + NL + "    <form style=\"padding: 10px 20px 10px 85px;\"  id=\"searchForm\" method=\"post\">" + NL + "    \t<table cellpadding=\"5px\" style=\"font-size:12px;\">" + NL + "    \t\t";
  protected final String TEXT_40 = NL + "    \t\t<tr>" + NL + "    \t\t\t<td>";
  protected final String TEXT_41 = "</td><td><input class=\"easyui-validatebox\" name=\"";
  protected final String TEXT_42 = ".";
  protected final String TEXT_43 = "\"/></td>\t" + NL + "    \t\t</tr>" + NL + "    \t\t";
  protected final String TEXT_44 = NL + "    \t</table>" + NL + "        <input type=\"hidden\" name=\"entity.id\" id=\"viewEntityId\"/>" + NL + "        <div style=\"padding: 0px;\">" + NL + "        \t<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" icon=\"icon-search\" onclick=\"searchSubmitForm();\">确定</a>" + NL + "            <a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" icon=\"icon-cancel\" onclick=\"closeAllWin()\">取消</a>" + NL + "        </div>" + NL + "    </form>" + NL + "</div>" + NL + "" + NL + "</body>" + NL + "</html>";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	Object[] o=(Object[])argument; 
	List listTemp=(List<String>)o[0];
	String[] s=(String[]) (listTemp.toArray(new String[listTemp.size()]));
	List list=(List)o[1];

    stringBuffer.append(TEXT_1);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_2);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_4);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_6);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_7);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_8);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_9);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_10);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_11);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_12);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_13);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_14);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_15);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_16);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_17);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_18);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_19);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_20);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_21);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_22);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_23);
    for(Iterator i=list.iterator();i.hasNext();){ String is=i.next().toString();
    stringBuffer.append(TEXT_24);
    stringBuffer.append(is );
    stringBuffer.append(TEXT_25);
    stringBuffer.append(is );
    stringBuffer.append(TEXT_26);
    stringBuffer.append(is );
    stringBuffer.append(TEXT_27);
    }
    stringBuffer.append(TEXT_28);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_29);
    for(Iterator i=list.iterator();i.hasNext();){ String is=i.next().toString();
    stringBuffer.append(TEXT_30);
    stringBuffer.append(is );
    stringBuffer.append(TEXT_31);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_32);
    stringBuffer.append(is );
    stringBuffer.append(TEXT_33);
    }
    stringBuffer.append(TEXT_34);
    for(Iterator i=list.iterator();i.hasNext();){ String is=i.next().toString();
    stringBuffer.append(TEXT_35);
    stringBuffer.append(is );
    stringBuffer.append(TEXT_36);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(is );
    stringBuffer.append(TEXT_38);
    }
    stringBuffer.append(TEXT_39);
    for(Iterator i=list.iterator();i.hasNext();){ String is=i.next().toString();
    stringBuffer.append(TEXT_40);
    stringBuffer.append(is );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(s[2] );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(is );
    stringBuffer.append(TEXT_43);
    }
    stringBuffer.append(TEXT_44);
    return stringBuffer.toString();
  }
}
