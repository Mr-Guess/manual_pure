<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作汇报</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

   $(document).ready(function () {
		 grid = new Grid('${ctx}/reporting/reportingAction!list','data_list');
		 grid.loadGrid();
		 crud = new Crud({
				 grid:grid,
				 addFormObject:$('#curdaddForm'),
				 viewFormObject:$('#curdviewForm'),
				 updateFormObject:$('#curdupdateForm'),
				 searchFormObject:$('#searchForm'),
				 entityName:'reporting',
				 moduleName:'工作汇报', 
				 dialogCss:{width:'900px',height:'auto'},
				 url:'${ctx}/reporting/reportingAction'
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
        
        function cleanFunction(){ 
        crud.clearSearch(); 
        crud.search(); 
        }
        
        function resizeGrid(){ 
        $('#data_list').datagrid('resize', { 
        width:function(){
        return document.body.clientWidth*0.9;
        } 
        }); 
        }
        
</script>
</head>
<body onresize="resizeGrid();">



<div style="margin: auto;">
	 <div id = "toolbar">
	<form id="searchForm" style="margin: 0;">
	
	&nbsp;&nbsp;回报标题:&nbsp;&nbsp;<input type="text" name="reporting.title"/>
	&nbsp;&nbsp;汇报人:&nbsp;&nbsp;<input type="text" name="reporting.reporter"/>
	&nbsp;&nbsp;department:&nbsp;&nbsp;<input type="text" name="reporting.department"/>
	&nbsp;&nbsp;departmentName:&nbsp;&nbsp;<input type="text" name="reporting.departmentName"/>
	&nbsp;&nbsp;汇报时间:&nbsp;&nbsp;<input type="text" name="reporting.reportTime"/>
	&nbsp;&nbsp;汇报类型:&nbsp;&nbsp;<input type="text" name="reporting.reportType"/>
	&nbsp;&nbsp;汇报内容:&nbsp;&nbsp;<input type="text" name="reporting.context"/>
	&nbsp;&nbsp;审阅人:&nbsp;&nbsp;<input type="text" name="reporting.checker"/>
	&nbsp;&nbsp;拥有者:&nbsp;&nbsp;<input type="text" name="reporting.owner"/>
	&nbsp;&nbsp;当前步骤:&nbsp;&nbsp;<input type="text" name="reporting.steps"/>
	&nbsp;&nbsp;经办人:&nbsp;&nbsp;<input type="text" name="reporting.informre"/>
	&nbsp;&nbsp;checkerinob:&nbsp;&nbsp;<input type="text" name="reporting.checkerinob"/>
	
	&nbsp;&nbsp;
	</form>
<div>
	 <shiro:hasPermission name="${menuId}:add">
		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="crud.add();">添加</a>
	 </shiro:hasPermission>
	 <shiro:hasPermission name="${menuId}:delete">
		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="crud.remove();">删除</a>
	 </shiro:hasPermission>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="crud.search();">查询</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', plain:true" onclick="cleanFunction();">清空</a>
	</div>
</div>

<div style="height: auto;">
    <table id="data_list" title = "工作汇报" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true">
        <thead>
        <tr>
			
            <th align="center" field="title" width="150" formatter="gridFormatterLength" >回报标题</th>
            <th align="center" field="reporter" width="150" formatter="gridFormatterLength" >汇报人</th>
            <th align="center" field="department" width="150" formatter="gridFormatterLength" >department</th>
            <th align="center" field="departmentName" width="150" formatter="gridFormatterLength" >departmentName</th>
            <th align="center" field="reportTime" width="150" formatter="gridFormatterLength" >汇报时间</th>
            <th align="center" field="reportType" width="150" formatter="gridFormatterLength" >汇报类型</th>
            <th align="center" field="context" width="150" formatter="gridFormatterLength" >汇报内容</th>
            <th align="center" field="checker" width="150" formatter="gridFormatterLength" >审阅人</th>
            <th align="center" field="owner" width="150" formatter="gridFormatterLength" >拥有者</th>
            <th align="center" field="steps" width="150" formatter="gridFormatterLength" >当前步骤</th>
            <th align="center" field="informre" width="150" formatter="gridFormatterLength" >经办人</th>
            <th align="center" field="checkerinob" width="150" formatter="gridFormatterLength" >checkerinob</th>
            <th align="center" field="id" width="120" formatter="gridFormatter">操作</th>         
        </tr>
        </thead>
    </table>
</div>

	<!-- 添加窗口 -->
	<div style="display: none;">
		 <div id="curdaddForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="addForm" method="post">
			<input type="hidden" name="reporting.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">回报标题:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.title"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">汇报人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.reporter"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">department:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.department"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">departmentName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.departmentName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.reportTime"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">汇报类型:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.reportType"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报内容:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.context"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">审阅人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.checker"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">拥有者:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.owner"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">当前步骤:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.steps"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经办人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.informre"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">checkerinob:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.checkerinob"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
				</table>
			</div>
		</form>
	</div>
	
	</div>
</div>
	
	<!-- 更新窗口 -->
	<div style="display: none;">
		 <div id="curdupdateForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="updateForm" method="post">
			<input type="hidden" name="reporting.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="updateDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">回报标题:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.title"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">汇报人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.reporter"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">department:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.department"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">departmentName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.departmentName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.reportTime"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">汇报类型:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.reportType"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报内容:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.context"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">审阅人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.checker"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">拥有者:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.owner"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">当前步骤:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.steps"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经办人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.informre"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">checkerinob:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.checkerinob"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
				</table>
			</div>
		</form>
	</div>
	
	</div>
	
	<!-- 查看窗口 -->
	<div style="display: none;">
		 <div id="curdviewForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="viewForm" method="post">
			<input type="hidden" name="reporting.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="viewDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">回报标题:</td>
							<td class="table_con" width="30%">
							<span name="reporting.title"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">汇报人:</td>
							<td class="table_con" width="30%">
							<span name="reporting.reporter"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">department:</td>
							<td class="table_con" width="30%">
							<span name="reporting.department"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">departmentName:</td>
							<td class="table_con" width="30%">
							<span name="reporting.departmentName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报时间:</td>
							<td class="table_con" width="30%">
							<span name="reporting.reportTime"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">汇报类型:</td>
							<td class="table_con" width="30%">
							<span name="reporting.reportType"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报内容:</td>
							<td class="table_con" width="30%">
							<span name="reporting.context"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">审阅人:</td>
							<td class="table_con" width="30%">
							<span name="reporting.checker"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">拥有者:</td>
							<td class="table_con" width="30%">
							<span name="reporting.owner"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">当前步骤:</td>
							<td class="table_con" width="30%">
							<span name="reporting.steps"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经办人:</td>
							<td class="table_con" width="30%">
							<span name="reporting.informre"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">checkerinob:</td>
							<td class="table_con" width="30%">
							<span name="reporting.checkerinob"></span>
							</td>
						</tr>
						
				</table>
			</div>
		</form>
	</div>
	
</div>
	
</body>
</html>