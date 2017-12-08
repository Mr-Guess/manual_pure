<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打卡机待处理事务</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

   $(document).ready(function () {
		 grid = new Grid('${ctx}/waitDone/waitDoneAction!list','data_list');
		 grid.loadGrid();
		 crud = new Crud({
				 grid:grid,
				 addFormObject:$('#curdaddForm'),
				 viewFormObject:$('#curdviewForm'),
				 updateFormObject:$('#curdupdateForm'),
				 searchFormObject:$('#searchForm'),
				 entityName:'waitDone',
				 moduleName:'打卡机待处理事务', 
				 dialogCss:{width:'900px',height:'auto'},
				 url:'${ctx}/waitDone/waitDoneAction'
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
	
	&nbsp;&nbsp;ssid:&nbsp;&nbsp;<input type="text" name="waitDone.ssid"/>
	&nbsp;&nbsp;deviceSn:&nbsp;&nbsp;<input type="text" name="waitDone.deviceSn"/>
	&nbsp;&nbsp;doneStatus:&nbsp;&nbsp;<input type="text" name="waitDone.doneStatus"/>
	&nbsp;&nbsp;cmdCode:&nbsp;&nbsp;<input type="text" name="waitDone.cmdCode"/>
	&nbsp;&nbsp;subCmd:&nbsp;&nbsp;<input type="text" name="waitDone.subCmd"/>
	&nbsp;&nbsp;cmdReturn:&nbsp;&nbsp;<input type="text" name="waitDone.cmdReturn"/>
	&nbsp;&nbsp;sendTime:&nbsp;&nbsp;<input type="text" name="waitDone.sendTime"/>
	
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
    <table id="data_list" title = "打卡机待处理事务" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true">
        <thead>
        <tr>
			
            <th align="center" field="ssid" width="150" formatter="gridFormatterLength" >ssid</th>
            <th align="center" field="deviceSn" width="150" formatter="gridFormatterLength" >deviceSn</th>
            <th align="center" field="doneStatus" width="150" formatter="gridFormatterLength" >doneStatus</th>
            <th align="center" field="cmdCode" width="150" formatter="gridFormatterLength" >cmdCode</th>
            <th align="center" field="subCmd" width="150" formatter="gridFormatterLength" >subCmd</th>
            <th align="center" field="cmdReturn" width="150" formatter="gridFormatterLength" >cmdReturn</th>
            <th align="center" field="sendTime" width="150" formatter="gridFormatterLength" >sendTime</th>
            <th align="center" field="id" width="120" formatter="gridFormatter">操作</th>         
        </tr>
        </thead>
    </table>
</div>

	<!-- 添加窗口 -->
	<div style="display: none;">
		 <div id="curdaddForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="addForm" method="post">
			<input type="hidden" name="waitDone.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">ssid:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.ssid"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">deviceSn:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.deviceSn"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">doneStatus:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.doneStatus"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">cmdCode:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.cmdCode"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">subCmd:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.subCmd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">cmdReturn:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.cmdReturn"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">sendTime:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.sendTime"
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
			<input type="hidden" name="waitDone.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="updateDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">ssid:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.ssid"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">deviceSn:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.deviceSn"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">doneStatus:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.doneStatus"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">cmdCode:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.cmdCode"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">subCmd:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.subCmd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">cmdReturn:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.cmdReturn"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">sendTime:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="waitDone.sendTime"
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
			<input type="hidden" name="waitDone.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="viewDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">ssid:</td>
							<td class="table_con" width="30%">
							<span name="waitDone.ssid"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">deviceSn:</td>
							<td class="table_con" width="30%">
							<span name="waitDone.deviceSn"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">doneStatus:</td>
							<td class="table_con" width="30%">
							<span name="waitDone.doneStatus"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">cmdCode:</td>
							<td class="table_con" width="30%">
							<span name="waitDone.cmdCode"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">subCmd:</td>
							<td class="table_con" width="30%">
							<span name="waitDone.subCmd"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">cmdReturn:</td>
							<td class="table_con" width="30%">
							<span name="waitDone.cmdReturn"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">sendTime:</td>
							<td class="table_con" width="30%">
							<span name="waitDone.sendTime"></span>
							</td>
						
				</table>
			</div>
		</form>
	</div>
	
</div>
	
</body>
</html>