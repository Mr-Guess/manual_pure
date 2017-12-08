<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假申请</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

   $(document).ready(function () {
		 grid = new Grid('${ctx}/holiday/holidayAction!list','data_list');
		 grid.loadGrid();
		 crud = new Crud({
				 grid:grid,
				 addFormObject:$('#curdaddForm'),
				 viewFormObject:$('#curdviewForm'),
				 updateFormObject:$('#curdupdateForm'),
				 searchFormObject:$('#searchForm'),
				 entityName:'holiday',
				 moduleName:'请假申请', 
				 dialogCss:{width:'900px',height:'auto'},
				 url:'${ctx}/holiday/holidayAction'
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
	
	&nbsp;&nbsp;申请人:&nbsp;&nbsp;<input type="text" name="holiday.prayEr"/>
	&nbsp;&nbsp;申请人ID:&nbsp;&nbsp;<input type="text" name="holiday.prayErId"/>
	&nbsp;&nbsp;请假时间:&nbsp;&nbsp;<input type="text" name="holiday.prayDays"/>
	&nbsp;&nbsp;请假理由:&nbsp;&nbsp;<input type="text" name="holiday.reason"/>
	&nbsp;&nbsp;开始时间:&nbsp;&nbsp;<input type="text" name="holiday.fromDays"/>
	&nbsp;&nbsp;结束时间:&nbsp;&nbsp;<input type="text" name="holiday.toDays"/>
	&nbsp;&nbsp;请假类型:&nbsp;&nbsp;<input type="text" name="holiday.type"/>
	&nbsp;&nbsp;doner:&nbsp;&nbsp;<input type="text" name="holiday.doner"/>
	&nbsp;&nbsp;within:&nbsp;&nbsp;<input type="text" name="holiday.within"/>
	
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
    <table id="data_list" title = "请假申请" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true">
        <thead>
        <tr>
			
            <th align="center" field="prayEr" width="150" formatter="gridFormatterLength" >申请人</th>
            <th align="center" field="prayErId" width="150" formatter="gridFormatterLength" >申请人ID</th>
            <th align="center" field="prayDays" width="150" formatter="gridFormatterLength" >请假时间</th>
            <th align="center" field="reason" width="150" formatter="gridFormatterLength" >请假理由</th>
            <th align="center" field="fromDays" width="150" formatter="gridFormatterLength" >开始时间</th>
            <th align="center" field="toDays" width="150" formatter="gridFormatterLength" >结束时间</th>
            <th align="center" field="type" width="150" formatter="gridFormatterLength" >请假类型</th>
            <th align="center" field="doner" width="150" formatter="gridFormatterLength" >doner</th>
            <th align="center" field="within" width="150" formatter="gridFormatterLength" >within</th>
            <th align="center" field="id" width="120" formatter="gridFormatter">操作</th>         
        </tr>
        </thead>
    </table>
</div>

	<!-- 添加窗口 -->
	<div style="display: none;">
		 <div id="curdaddForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="addForm" method="post">
			<input type="hidden" name="holiday.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">申请人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.prayEr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">申请人ID:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.prayErId"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">请假时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.prayDays"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">请假理由:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.reason"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">开始时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.fromDays"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">结束时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.toDays"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">请假类型:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.type"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">doner:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.doner"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">within:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.within"
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
			<input type="hidden" name="holiday.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="updateDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">申请人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.prayEr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">申请人ID:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.prayErId"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">请假时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.prayDays"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">请假理由:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.reason"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">开始时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.fromDays"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">结束时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.toDays"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">请假类型:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.type"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">doner:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.doner"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">within:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="holiday.within"
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
			<input type="hidden" name="holiday.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="viewDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">申请人:</td>
							<td class="table_con" width="30%">
							<span name="holiday.prayEr"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">申请人ID:</td>
							<td class="table_con" width="30%">
							<span name="holiday.prayErId"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">请假时间:</td>
							<td class="table_con" width="30%">
							<span name="holiday.prayDays"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">请假理由:</td>
							<td class="table_con" width="30%">
							<span name="holiday.reason"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">开始时间:</td>
							<td class="table_con" width="30%">
							<span name="holiday.fromDays"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">结束时间:</td>
							<td class="table_con" width="30%">
							<span name="holiday.toDays"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">请假类型:</td>
							<td class="table_con" width="30%">
							<span name="holiday.type"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">doner:</td>
							<td class="table_con" width="30%">
							<span name="holiday.doner"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">within:</td>
							<td class="table_con" width="30%">
							<span name="holiday.within"></span>
							</td>
						
				</table>
			</div>
		</form>
	</div>
	
</div>
	
</body>
</html>