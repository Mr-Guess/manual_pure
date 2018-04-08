<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线打卡</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

   $(document).ready(function () {
		 grid = new Grid('${ctx}/onlinCheckin/onlinCheckinAction!list','data_list');
		 grid.loadGrid();
		 crud = new Crud({
				 grid:grid,
				 addFormObject:$('#curdaddForm'),
				 viewFormObject:$('#curdviewForm'),
				 updateFormObject:$('#curdupdateForm'),
				 searchFormObject:$('#searchForm'),
				 entityName:'onlinCheckin',
				 moduleName:'在线打卡', 
				 dialogCss:{width:'900px',height:'auto'},
				 url:'${ctx}/onlinCheckin/onlinCheckinAction'
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
	
	&nbsp;&nbsp;checkinUser:&nbsp;&nbsp;<input type="text" name="onlinCheckin.checkinUser"/>
	&nbsp;&nbsp;checkeinTime:&nbsp;&nbsp;<input type="text" name="onlinCheckin.checkeinTime"/>
	&nbsp;&nbsp;checkinAddress:&nbsp;&nbsp;<input type="text" name="onlinCheckin.checkinAddress"/>
	&nbsp;&nbsp;picUrl:&nbsp;&nbsp;<input type="text" name="onlinCheckin.picUrl"/>
	&nbsp;&nbsp;checkinType:&nbsp;&nbsp;<input type="text" name="onlinCheckin.checkinType"/>
	
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
    <table id="data_list" title = "在线打卡" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true">
        <thead>
        <tr>
			
            <th align="center" field="checkinUser" width="150" formatter="gridFormatterLength" >checkinUser</th>
            <th align="center" field="checkeinTime" width="150" formatter="gridFormatterLength" >checkeinTime</th>
            <th align="center" field="checkinAddress" width="150" formatter="gridFormatterLength" >checkinAddress</th>
            <th align="center" field="picUrl" width="150" formatter="gridFormatterLength" >picUrl</th>
            <th align="center" field="checkinType" width="150" formatter="gridFormatterLength" >checkinType</th>
            <th align="center" field="id" width="120" formatter="gridFormatter">操作</th>         
        </tr>
        </thead>
    </table>
</div>

	<!-- 添加窗口 -->
	<div style="display: none;">
		 <div id="curdaddForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="addForm" method="post">
			<input type="hidden" name="onlinCheckin.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">checkinUser:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="onlinCheckin.checkinUser"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">checkeinTime:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="onlinCheckin.checkeinTime"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">checkinAddress:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="onlinCheckin.checkinAddress"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">picUrl:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="onlinCheckin.picUrl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">checkinType:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="onlinCheckin.checkinType"
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
			<input type="hidden" name="onlinCheckin.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="updateDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">checkinUser:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="onlinCheckin.checkinUser"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">checkeinTime:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="onlinCheckin.checkeinTime"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">checkinAddress:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="onlinCheckin.checkinAddress"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">picUrl:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="onlinCheckin.picUrl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">checkinType:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="onlinCheckin.checkinType"
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
			<input type="hidden" name="onlinCheckin.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="viewDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">checkinUser:</td>
							<td class="table_con" width="30%">
							<span name="onlinCheckin.checkinUser"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">checkeinTime:</td>
							<td class="table_con" width="30%">
							<span name="onlinCheckin.checkeinTime"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">checkinAddress:</td>
							<td class="table_con" width="30%">
							<span name="onlinCheckin.checkinAddress"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">picUrl:</td>
							<td class="table_con" width="30%">
							<span name="onlinCheckin.picUrl"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">checkinType:</td>
							<td class="table_con" width="30%">
							<span name="onlinCheckin.checkinType"></span>
							</td>
						
				</table>
			</div>
		</form>
	</div>
	
</div>
	
</body>
</html>