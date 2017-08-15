<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

   $(document).ready(function () {
		 grid = new Grid('${ctx}/worker/workerAction!list','data_list');
		 grid.loadGrid();
		 crud = new Crud({
				 grid:grid,
				 addFormObject:$('#curdaddForm'),
				 viewFormObject:$('#curdviewForm'),
				 updateFormObject:$('#curdupdateForm'),
				 searchFormObject:$('#searchForm'),
				 entityName:'worker',
				 moduleName:'员工', 
				 dialogCss:{width:'900px',height:'auto'},
				 url:'${ctx}/worker/workerAction'
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
	
	&nbsp;&nbsp;员工姓名:&nbsp;&nbsp;<input type="text" name="worker.name"/>
	&nbsp;&nbsp;头像:&nbsp;&nbsp;<input type="text" name="worker.header"/>
	&nbsp;&nbsp;邮箱地址:&nbsp;&nbsp;<input type="text" name="worker.email"/>
	&nbsp;&nbsp;手机:&nbsp;&nbsp;<input type="text" name="worker.mobile"/>
	&nbsp;&nbsp;所在部门:&nbsp;&nbsp;<input type="text" name="worker.dept"/>
	&nbsp;&nbsp;职位:&nbsp;&nbsp;<input type="text" name="worker.position"/>
	&nbsp;&nbsp;状态:&nbsp;&nbsp;<input type="text" name="worker.ownerIntur"/>
	
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
    <table id="data_list" title = "员工" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true">
        <thead>
        <tr>
			
            <th align="center" field="name" width="150" formatter="gridFormatterLength" >员工姓名</th>
            <th align="center" field="header" width="150" formatter="gridFormatterLength" >头像</th>
            <th align="center" field="email" width="150" formatter="gridFormatterLength" >邮箱地址</th>
            <th align="center" field="mobile" width="150" formatter="gridFormatterLength" >手机</th>
            <th align="center" field="dept" width="150" formatter="gridFormatterLength" >所在部门</th>
            <th align="center" field="position" width="150" formatter="gridFormatterLength" >职位</th>
            <th align="center" field="ownerIntur" width="150" formatter="gridFormatterLength" >状态</th>
            <th align="center" field="id" width="120" formatter="gridFormatter">操作</th>         
        </tr>
        </thead>
    </table>
</div>

	<!-- 添加窗口 -->
	<div style="display: none;">
		 <div id="curdaddForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="addForm" method="post">
			<input type="hidden" name="worker.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">员工姓名:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.name"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">头像:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.header"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">邮箱地址:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.email"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">手机:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.mobile"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">所在部门:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.dept"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">职位:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.position"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">状态:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.ownerIntur"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
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
			<input type="hidden" name="worker.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="updateDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">员工姓名:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.name"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">头像:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.header"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">邮箱地址:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.email"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">手机:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.mobile"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">所在部门:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.dept"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">职位:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.position"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">状态:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="worker.ownerIntur"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
				</table>
			</div>
		</form>
	</div>
	
	</div>
	
	<!-- 查看窗口 -->
	<div style="display: none;">
		 <div id="curdviewForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="viewForm" method="post">
			<input type="hidden" name="worker.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="viewDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">员工姓名:</td>
							<td class="table_con" width="30%">
							<span name="worker.name"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">头像:</td>
							<td class="table_con" width="30%">
							<span name="worker.header"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">邮箱地址:</td>
							<td class="table_con" width="30%">
							<span name="worker.email"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">手机:</td>
							<td class="table_con" width="30%">
							<span name="worker.mobile"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">所在部门:</td>
							<td class="table_con" width="30%">
							<span name="worker.dept"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">职位:</td>
							<td class="table_con" width="30%">
							<span name="worker.position"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">状态:</td>
							<td class="table_con" width="30%">
							<span name="worker.ownerIntur"></span>
							</td>
						
				</table>
			</div>
		</form>
	</div>
	
</div>
	
</body>
</html>