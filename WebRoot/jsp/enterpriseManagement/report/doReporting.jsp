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
		 grid = new Grid('${ctx}/reporting/reportingAction!list?reporting.checkerinob=1&reporting.checker=${user.deptId}','data_list');
		 grid.loadGrid();
		 crud = new Crud({
				 grid:grid,
				 addFormObject:$('#curdaddForm'),
				 updateFormObject:$('#curdupdateForm'),
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
		 
		 $('#reversion').dialog({
			 	title:"审阅意见",
			 	width:400,
	            height:210,
			 	modal:true,
				draggable:true,
				closed:true,
				buttons:[{
					text:'保存',
					iconCls:'icon-save',
					handler:function() {
						$("#reversion").find('form').form('submit',{
							url: "${ctx}/reporting/reportingAction!instRev",
					        success:function(data) {
					        	$("#reversion").dialog("close");
					        	var data = eval('(' + data + ')');
					            if (data.operateSuccess) {
					                $.messager.show({
					                    title:'成功',
					                    msg:data.operateMessage,
					                    timeout:2000,
					                    showType:'slide'
					                });
					                grid.reloadGrid();
					            } else {
					                $.messager.show({
					                    title:'失败',
					                    msg:data.operateMessage,
					                    timeout:2000,
					                    showType:'slide'
					                });
					            }
					        }
					    });
						crud.viewFormObject.dialog("close");
					}
				}, {
					text:'取消',
					iconCls:'icon-cancel',
					handler:function() {
						$("#reversion").dialog("close");
					}
				}]
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
        
        //审阅回调函数
        function doReversion(data){
        	$("#reId").val(data.id);
        	$("#reUser").val("${user_id}");
        	$("#reIn").val(data.informre);
        	$("#reversion").dialog("open");
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
        	var step = rowData.step;
        	var url = "";
        	if(step == 1){
        		url += "<input type='button' class='btn1' onclick='crud.view(\"" + rowId + "\");' value='查看'/>&nbsp;&nbsp;";
	        	url += "<input type='button' class='btn1' onclick='crud.update(\"" + rowId + "\");' value='修改'/>&nbsp;&nbsp;";
        	}else{
        		url += "<input type='button' class='btn1' onclick='crud.reView(\"" + rowId + "\");' value='审阅'/>&nbsp;&nbsp;";
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
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="crud.add();">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="crud.remove();">删除</a>
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
							<select name="reporting.reportType" class="easyui-combobox" panelHeight="auto" editable="false" >
										<option value="日报">日报</option>
										<option value="月报">月报</option>
									</select>
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
	<div style="display: none;" >
		<div id="reversion" style="width: 50%; height: 150px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
			<form id="reversionForm" method="post">
				<input id="reId" type="hidden" name="reporting.id"/>
				<input id="reUser" type="hidden" name="nowUser"/>
				<input id="reIn" type="hidden" name="reporting.informre"/>
				<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
					<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
						<tr>
								<td class="table_con">
									<select name="reType" class="easyui-combobox" panelHeight="auto" editable="false" width="80%">
										<option value="已阅">已阅</option>
										<option value="已阅，并附意见">已阅，并附意见</option>
									</select>
								</td>
						</tr>
						<tr>
								<td class="table_con">
									<textarea style="width: 80%;height: 50px;" name="context" ></textarea>
								</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</body>
</html>