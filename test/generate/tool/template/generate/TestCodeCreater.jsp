<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试代码生成器</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

   $(document).ready(function () {
		 grid = new Grid('${ctx}/testCodeCreatertestCodeCreater/testCodeCreaterAction!list','data_list');
		 grid.loadGrid();
		 crud = new Crud({
				 grid:grid,
				 addFormObject:$('#curdaddForm'),
				 searchFormObject:$('#searchForm'),
				 entityName:'testCodeCreater',
				 moduleName:'测试代码生成器', 
				 dialogCss:{width:'900px',height:'auto'},
				 url:'${ctx}/testCodeCreater/testCodeCreaterAction'
				});
		});
        function gridFormatterLength(value, rowData, rowIndex) {
        	if(value==null || value == '' || value == 'undefined')
        		return '';
    		if(value.length > 25)
    			return value.substring(0, 25) + '...';
    		return value;
    	}

        function gridFormatter(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var url = "";
        		<shiro:hasPermission name='${menuId}:view'> 
        	url += "<input type='button' class='btn1' onclick='crud.view(\"" + rowId + "\");' value='查看'/>&nbsp;&nbsp;";
        		</shiro:hasPermission>
        		<shiro:hasPermission name='${menuId}:update'>
        	url += "<input type='button' class='btn1' onclick='crud.update(\"" + rowId + "\");' value='修改'/>&nbsp;&nbsp;";
        		</shiro:hasPermission> 
        	return url;
        }
        
</script>
</head>
<body>



<div class="datagrid-toolbar" id = "tb" style="padding-left:0px; height:10px;">
	 <div>
	<form id="searchForm" style="margin: 0;">
	
	&nbsp;&nbsp;测试代码字段1:&nbsp;&nbsp;<input type="text" name="testCodeCreater.showView" />
	&nbsp;&nbsp;测试代码字段2:&nbsp;&nbsp;<input type="text" name="testCodeCreater.showVideo" />
	
	&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="crud.search()">查询</a>
	&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" onclick="crud.clearSearch();">清空</a>
	</form>
	</div>
</div>
<div class="search_add">
	 <shiro:hasPermission name="${menuId}:add">
		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="crud.add();">添加</a>
	 </shiro:hasPermission>
	 <shiro:hasPermission name="${menuId}:delete">
		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-delete', plain:true" onclick="crud.remove();">删除</a>
	 </shiro:hasPermission>
</div>
<div style="height: 74%">
    <table id="data_list" loadMsg="正在努力拉取数据中..." fit="true" fitColumns="true">
        <thead>
        <tr>
			
            <th align="center" field="showView" width="150" formatter="gridFormatterLength" >测试代码字段1</th>
            <th align="center" field="showVideo" width="150" formatter="gridFormatterLength" >测试代码字段2</th>
            <th align="center" field="id" width="120" formatter="gridFormatter">操作</th>         
        </tr>
        </thead>
    </table>
</div>

	<!-- 添加更新窗口 -->
	<div style="display: none;">
		 <div id="curdaddForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="addForm" method="post" style="width: 960px; height: 500px;">
			<input type="hidden" name="testCodeCreater.id"/>
			<div style="margin: 0 auto;" align="center" name="addDiv">
				<table class="detailTable" style="width: 80%;">
					
						<tr>
							<td class="table_nemu_new">测试代码字段1:</td>
							<td class="table_con">
							<input type="text" class="easyui-validatebox" name="testCodeCreater.showView"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new">测试代码字段2:</td>
							<td class="table_con">
							<input type="text" class="easyui-validatebox" name="testCodeCreater.showVideo"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
				</table>
			</div>
		</form>
	</div>
	
	</div>
	
</body>
</html>