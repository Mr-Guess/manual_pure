<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>借款申请</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

   $(document).ready(function () {
		 grid = new Grid('${ctx}/borrow/borrowAction!list','data_list');
		 grid.loadGrid();
		 crud = new Crud({
				 grid:grid,
				 addFormObject:$('#curdaddForm'),
				 viewFormObject:$('#curdviewForm'),
				 updateFormObject:$('#curdupdateForm'),
				 searchFormObject:$('#searchForm'),
				 entityName:'borrow',
				 moduleName:'借款申请', 
				 dialogCss:{width:'900px',height:'auto'},
				 url:'${ctx}/borrow/borrowAction'
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
	
	&nbsp;&nbsp;借款人:&nbsp;&nbsp;<input type="text" name="borrow.borrower"/>
	&nbsp;&nbsp;借款人ID:&nbsp;&nbsp;<input type="text" name="borrow.borrowerId"/>
	&nbsp;&nbsp;借款事由:&nbsp;&nbsp;<input type="text" name="borrow.reason"/>
	&nbsp;&nbsp;借款金额:&nbsp;&nbsp;<input type="text" name="borrow.amount"/>
	&nbsp;&nbsp;申请时间:&nbsp;&nbsp;<input type="text" name="borrow.prayTime"/>
	&nbsp;&nbsp;doner:&nbsp;&nbsp;<input type="text" name="borrow.doner"/>
	&nbsp;&nbsp;within:&nbsp;&nbsp;<input type="text" name="borrow.within"/>
	&nbsp;&nbsp;step:&nbsp;&nbsp;<input type="text" name="borrow.step"/>
	
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
    <table id="data_list" title = "借款申请" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true">
        <thead>
        <tr>
			
            <th align="center" field="borrower" width="150" formatter="gridFormatterLength" >借款人</th>
            <th align="center" field="borrowerId" width="150" formatter="gridFormatterLength" >借款人ID</th>
            <th align="center" field="reason" width="150" formatter="gridFormatterLength" >借款事由</th>
            <th align="center" field="amount" width="150" formatter="gridFormatterLength" >借款金额</th>
            <th align="center" field="prayTime" width="150" formatter="gridFormatterLength" >申请时间</th>
            <th align="center" field="doner" width="150" formatter="gridFormatterLength" >doner</th>
            <th align="center" field="within" width="150" formatter="gridFormatterLength" >within</th>
            <th align="center" field="step" width="150" formatter="gridFormatterLength" >step</th>
            <th align="center" field="id" width="120" formatter="gridFormatter">操作</th>         
        </tr>
        </thead>
    </table>
</div>

	<!-- 添加窗口 -->
	<div style="display: none;">
		 <div id="curdaddForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="addForm" method="post">
			<input type="hidden" name="borrow.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">借款人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.borrower"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">借款人ID:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.borrowerId"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">借款事由:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.reason"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">借款金额:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.amount"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">申请时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.prayTime"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">doner:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.doner"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">within:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.within"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">step:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.step"
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
			<input type="hidden" name="borrow.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="updateDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">借款人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.borrower"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">借款人ID:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.borrowerId"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">借款事由:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.reason"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">借款金额:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.amount"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">申请时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.prayTime"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">doner:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.doner"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">within:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.within"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">step:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="borrow.step"
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
			<input type="hidden" name="borrow.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="viewDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">借款人:</td>
							<td class="table_con" width="30%">
							<span name="borrow.borrower"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">借款人ID:</td>
							<td class="table_con" width="30%">
							<span name="borrow.borrowerId"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">借款事由:</td>
							<td class="table_con" width="30%">
							<span name="borrow.reason"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">借款金额:</td>
							<td class="table_con" width="30%">
							<span name="borrow.amount"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">申请时间:</td>
							<td class="table_con" width="30%">
							<span name="borrow.prayTime"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">doner:</td>
							<td class="table_con" width="30%">
							<span name="borrow.doner"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">within:</td>
							<td class="table_con" width="30%">
							<span name="borrow.within"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">step:</td>
							<td class="table_con" width="30%">
							<span name="borrow.step"></span>
							</td>
						</tr>
						
				</table>
			</div>
		</form>
	</div>
	
</div>
	
</body>
</html>