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
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/validator.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtilwithSubmit.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

   $(document).ready(function () {
		 grid = new Grid('${ctx}/reporting/reportingAction!list?reporting.owner=${user_id}','data_list');
		 grid.loadGrid();
		 crud = new Crud({
				 grid:grid,
				 addFormObject:$('#curdaddForm'),
				 updateFormObject:$('#curdupdateForm'),
				 reViewFormObject:$('#curdReViewForm'),
				 searchFormObject:$('#searchForm'),
				 entityName:'reporting',
				 moduleName:'工作汇报', 
				 dialogCss:{width:'900px',height:'auto'},
				 url:'${ctx}/reporting/reportingAction',
				 beforeSubmit:function(){
					 $("#addNowUser").val("${user_id}");
					 $("#updateNowUser").val("${user_id}");
					 return true;
				 }
				});
		 
		
		 $('#deptTreeWin').window({
             width:382,
             height:400,
             modal:true,
             closed:true,
             onOpen:function(){
             	$(".window-mask").css("height","100%");
             }
         });
		 tree = new Tree('${ctx}/dept/deptAction!reloadDeptTree.action', 'dept_tree', 'tree_search', 'key', doSearch);
         tree.loadTree();
		});
        function gridFormatterLength(value, rowData, rowIndex) {
        	if(value==null || value == '' || value == 'undefined')
        		return '';
    		if(value.length > 25)
    			return value.substring(0, 25) + '...';
    		return value;
    	}
        
        function doSearch(id, name, type) {
            $('#deptTreeWin').window('close');
            $('#addDeptId').val(id);
            $('#addDeptName').val(name);
            $('#addDeptId').val(id);
            $('#updateDeptName').val(name);
        }
        
        function showDeptTreeWin() {
            $('#deptTreeWin').window('open');
        }

        function gridFormatter(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var url = "";
        	if(rowData.steps == 3){
	        	url += "<input type='button' class='btn1' onclick='crud.seeReView(\"" + rowId + "\");' value='查看'/>&nbsp;&nbsp;";
        	}else{
	        	url += "<input type='button' class='btn1' onclick='crud.view(\"" + rowId + "\");' value='查看'/>&nbsp;&nbsp;";
        	}
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
	
	&nbsp;&nbsp;汇报人:&nbsp;&nbsp;<input type="text" name="reporting.reporter"/>
	&nbsp;&nbsp;部门:&nbsp;&nbsp;<input type="text" name="reporting.reporter"/>
	&nbsp;&nbsp;汇报类型:&nbsp;&nbsp;<input type="text" name="reporting.reportType"/>
	&nbsp;&nbsp;
	</form>
<div>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="crud.search();">查询</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', plain:true" onclick="cleanFunction();">清空</a>
	</div>
</div>

<div style="height: auto;">
    <table id="data_list" title = "工作汇报" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true">
        <thead>
        <tr>
            <th align="center" field="autoTitle" width="150" formatter="gridFormatterLength" >汇报标题</th>
            <th align="center" field="title" width="150" formatter="gridFormatterLength" >副标题</th>
            <th align="center" field="reporter" width="150" formatter="gridFormatterLength" >汇报人</th>
            <th align="center" field="departmentName" width="150" formatter="gridFormatterLength" >部门</th>
            <th align="center" field="reportTime" width="150" formatter="gridFormatterLength" >汇报时间</th>
            <th align="center" field="reportType" width="150" formatter="gridFormatterLength" >汇报类型</th>
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
			<input type="hidden" id="addDeptId" name="reporting.department"/>
			<input type="hidden" id="addNowUser" name="nowUser"/>
			<input type="hidden" name="reporting.informre"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报标题:</td>
							<td class="table_con" colspan="3">
							<input type="text" class="easyui-validatebox" name="reporting.title"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						<tr>
							<td class="table_nemu_new" width="20%">汇报人:</td>
							<td class="table_con">
							<input type="text" class="easyui-validatebox" name="reporting.reporter"
							data-options="validType:'maxByteLength[100]'" />
							</td>
							<td class="table_nemu_new" width="20%">部门:</td>
							<td class="table_con">
							<input class="easyui-vlidatebox" id="addDeptName" name="reporting.departmentName" readonly="readonly" onclick="showDeptTreeWin();" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%">汇报类型:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.reportType"
							data-options="validType:'maxByteLength[100]'" />
							</td>
							<td class="table_nemu_new" width="20%" width = "20%">汇报时间:</td>
							<td class="table_con" width="30%">
							<input type="text" name="reporting.reportTime" class="easyui-my97 easyui-validatebox"
								data-options="readOnly:true,dateFmt:'yyyy-MM-dd'"/>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报内容:</td>
							<td class="table_con" colspan="3">
							<textarea style="width: 80%;height: 200px;" class="easyui-validatebox" name="reporting.context" ></textarea>
							</td>
						</tr>
				</table>
			</div>
		</form>
	</div>
	
	</div>
</div>

<!-- 修改窗口 -->
	<div style="display: none;">
		 <div id="curdupdateForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="updateForm" method="post">
			<input type="hidden" name="reporting.id"/>
			<input type="hidden" id="updateDeptId" name="reporting.department"/>
			<input type="hidden" id="updateNowUser" name="nowUser"/>
			<input type="hidden" name="reporting.informre"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报标题:</td>
							<td class="table_con" colspan="3">
							<input type="text" class="easyui-validatebox" name="reporting.title"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						<tr>
							<td class="table_nemu_new" width="20%">汇报人:</td>
							<td class="table_con">
							<input type="text" class="easyui-validatebox" name="reporting.reporter"
							data-options="validType:'maxByteLength[100]'" />
							</td>
							<td class="table_nemu_new" width="20%">部门:</td>
							<td class="table_con">
							<input class="easyui-vlidatebox" id="updateDeptName" name="reporting.departmentName" readonly="readonly" onclick="showDeptTreeWin();" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%">汇报类型:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="reporting.reportType"
							data-options="validType:'maxByteLength[100]'" />
							</td>
							<td class="table_nemu_new" width="20%" width = "20%">汇报时间:</td>
							<td class="table_con" width="30%">
							<input type="text" name="reporting.reportTime" class="easyui-my97 easyui-validatebox"
								data-options="readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'"/>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报内容:</td>
							<td class="table_con" colspan="3">
							<textarea style="width: 80%;height: 200px;" class="easyui-validatebox" name="reporting.context" ></textarea>
							</td>
						</tr>
				</table>
			</div>
		</form>
	</div>
	
	</div>
</div>
	
	<!-- 部门树展现 -->
	<div style="display: none">
		<div id="deptTreeWin" iconCls="icon-save" title="部门树">
			<div id="p" class="easyui-panel" title="&nbsp;&nbsp;&nbsp;部门树"
				fit="true" tools="#tt" border="false" plain="true">
				<div id="tree_search" style="display: none">
					关键字:&nbsp;&nbsp;&nbsp;<input type="text" id="key" name="key"
						style="width: 80px; height: 25px; margin-top: 5px;" />&nbsp;&nbsp;&nbsp;<a
						href="#" class="easyui-linkbutton" iconCls="icon-search"
						plain="true" onclick="tree.searchNode();">搜索</a>
				</div>
				<div id="dept_tree" class="ztree"></div>
			</div>
			<div id="tt">
				<a href="#" class="icon-treeSearch"
					onclick="javascript:tree.changeTreeSearchPanelShow();" title="搜索"></a>
				<a href="#" class="icon-treeRefresh"
					onclick="javascript:tree.loadTree();" title="刷新"></a> <a href="#"
					class="icon-treeAdd"
					onclick="javascript:tree.expandAllNode(false);" title="收缩所有"></a> <a
					href="#" class="icon-treeDelete"
					onclick="javascript:tree.expandAllNode(true);" title="展开所有"></a>
			</div>
		</div>
	</div>
	
	
	<div style="display: none;">
		 <div id="curdReViewForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="reViewForm" method="post">
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">汇报标题:</td>
							<td class="table_con" colspan="3">
								<span name="reporting.title"></span>
							</td>
						</tr>
						<tr>
							<td class="table_nemu_new" width="20%">汇报人:</td>
							<td class="table_con">
								<span name="reporting.reporter"></span>
							</td>
							<td class="table_nemu_new" width="20%">部门:</td>
							<td class="table_con">
								<span name="reporting.departmentName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%">汇报类型:</td>
							<td class="table_con" width="30%">
								<span name="reporting.reportType"></span>
							</td>
							<td class="table_nemu_new" width="20%" >汇报时间:</td>
							<td class="table_con" width="30%">
								<span name="reporting.reportTime"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" >汇报内容:</td>
							<td class="table_con" colspan="3">
								<span name="reporting.context"></span>
							</td>
						</tr>
						<tr>
							<td class="table_nemu_new" colspan="4">批示内容</td>
						</tr>
						<tr>
							<td class="table_nemu_new" width="20%" >批示:</td>
							<td class="table_con" colspan="3">
								<span name="reporting.reType"></span>
							</td>
						</tr>
						<tr>
							<td class="table_nemu_new" width="20%" >领导意见:</td>
							<td class="table_con" colspan="3">
								<span name="reporting.recontext"></span>
							</td>
						</tr>
				</table>
			</div>
		</form>
	</div>
	
	</div>
</body>
</html>