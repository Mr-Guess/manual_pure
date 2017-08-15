<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员信息维护</title>

<!--<link rel="stylesheet" type="text/css" href="${ctx}/css/bus.css">-->
<!--<link rel="stylesheet" type="text/css" href="${ctx}/js/bootstrap/css/bootstrap.css">-->
<!--<link rel="stylesheet" type="text/css" href="${ctx}/js/bootstrap/css/bootstrap-modal.css">-->
<!--<link rel="stylesheet" type="text/css" href="${ctx}/js/bootstrap/css/add_new.css">-->
<link rel="stylesheet" href="${ctx}/css/newCrud.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/js/DataGridUtilNoCheckbox.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/SelectUtil.js"></script>
<script type="text/javascript">
        var dgrid = null;
        var grid = null;
        var curd = null;
		$(document).ready(function(){
			dgrid = new Grid('${ctx}/user/userAction!pageList.action?usertype=${param.usertype}','userdata_list');
            grid = new Grid('${ctx}/userInfo/userInfoAction!list','data_list');
            grid.setting = {
            	title : '',//去除title
				iconCls : 'icon-edit',
				nowrap : false,
				striped : true,
				border : true,
				collapsible : false,
				fit : true,
				fitColumns:true,
				url : '${ctx}/userInfo/userInfoAction!list',
				remoteSort : false,
				idField : 'id',
				singleSelect : false,
				pagination : true,
				rownumbers : true,
				frozenColumns : [ [ {
					field : 'ck',
					checkbox : true
				} ] ]
            };
            dgrid.loadGrid();
		    grid.loadGrid();
			crud = new Crud({
				grid:grid, 
				addFormObject:$('#curdaddForm'),
				searchFormObject:$('#searchForm'),
				entityName:'userInfo',
				moduleName:'人员',
				url:'${ctx}/userInfo/userInfoAction',
				beforeSubmit:function() {
					 return true;
				}
			});
			$('#addWin').window({
                  width:382,
                  height:400,
                  resizable:false,
                  maximizable:false,
                  modal:true,
                  closed:true,
                  onOpen:function(){
                  	$(".window-mask").css("height","100%");
                  }
             });
			$('#addWin').window('close');
			
			
		});
		function chooseFunction(rowId,name,deptName,business){
			$('#name').val(name);
			$('#goId').val(rowId);
			$('#deptName').val(deptName);
			$('#bus').combobox('setValue',business);
			$('#addWin').window('close');
		}
		function usergridFormatter(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var name = rowData.userName;
        	var deptName = rowData.deptName;
        	var business = rowData.business;
        	var url = "";
        	url +="<input type='button' onclick='chooseFunction(\""+rowId+"\",\""+name+"\",\""+deptName+"\",\""+business+"\");' class='btn1' value='选择' />&nbsp;&nbsp;"
        	return url;
        }
        function clear() {
			Crud.clearForm($('#addForm'));
		}
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
        	<shiro:hasPermission name="${menuId}:view">
        	url += "<a title=\"查看\" onclick='crud.view(\""+rowId+"\");' class='btn2'><img src=\"${ctx}/images/select.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;";
        	</shiro:hasPermission>
        	<shiro:hasPermission name="${menuId}:update">
        	url += "<a title=\"修改\" onclick='crud.update(\""+rowId+"\");' class='btn2'><img src=\"${ctx}/images/update.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;";
        	</shiro:hasPermission>
        	return url;
        }
        function showList(){
        	$('#addWin').window('open');
        }
        function setGosexMail(){
        	$('#goSex').val('男');
        }
        function sexGosexFemail(){
        	$('#goSex').val('女');
        }
        
        function searchUser() {                       
          //提交查询表单
			 $('#searchUser').form({
			     url:'${ctx}/user/userAction!pageList.action',
			     success:function (data) {
			     	var data = eval('(' + data + ')');
			        dgrid.reloadGridByData(data);
			     }
			 });
			 $('#searchUser').submit();
        }
        function clearSearchUser() {
        	$('#searchUserName').val('');
        }
        
        var oldV = "";
        // 直接搜索
        function upKey(value){
        	if(value != oldV){
        		searchUser();
        		oldV = value;
        	}
        }
</script>
</head>
<body>
	<div class="panel-header" style="width: 100%;">
		<div class="panel-title panel-with-icon">网格人员配置信息列表</div>
		<div class="panel-icon icon-edit"></div>
		<div class="panel-tool"></div>
	</div>
	<div class="datagrid-toolbar" id="tb"
		style="padding-left: 0px; height: 10px;">
		<form id="searchForm" method="post" style="display: inline;">
			<div style="margin-top: -8px;">
				&nbsp;&nbsp;姓名:&nbsp;&nbsp;<input type="text" name="userInfo.name" />
				&nbsp;&nbsp;职务:&nbsp;&nbsp; <select class="easyui-combobox"
					name="userInfo.duties" style="width: 100px;"
					data-options="readOnly:true" panelHeight="auto">
					<option value="">--请选择--</option>
					<option value="科员">科员</option>
					<option value="副科长">副科长</option>
					<option value="科长">科长</option>
					<option value="副处长">副处长</option>
					<option value="处长">处长</option>
					<option value="副局长">副局长</option>
					<option value="局长">局长</option>
				</select> &nbsp;&nbsp;职称:&nbsp;&nbsp; <select type="text"
					class="easyui-combobox" name="userInfo.title" panelHeight="auto">
					<option value="">--请选择--</option>
					<option value="初级">初级</option>
					<option value="中级">中级</option>
					<option value="高级">高级</option>
				</select> &nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-search" onclick="crud.search();">查询</a> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-clear" onclick="crud.clearSearch();">清空</a>
			</div>
		</form>
	</div>
	<div class="search_add">
		<shiro:hasPermission name="${menuId}:add">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-add', plain:true" onclick="crud.add();">添加</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="${menuId}:delete">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-delete', plain:true"
				onclick="crud.remove();">删除</a>
		</shiro:hasPermission>
	</div>
	<div style="height: 500px">
		<table id="data_list" loadMsg="正在努力拉取数据中..." fit="true"
			fitColumns="true">
			<thead>
				<tr>
					<th align="center" field="name" width="150"
						formatter="gridFormatterLength">姓名</th>
					<th align="center" field="sex" width="150"
						formatter="gridFormatterLength">性别</th>
					<th align="center" field="duties" width="150"
						formatter="gridFormatterLength">职务</th>
					<th align="center" field="title" width="150"
						formatter="gridFormatterLength">职称</th>
					<th align="center" field="mobilephone" width="150"
						formatter="gridFormatterLength">手机</th>
					<th align="center" field="cellphone" width="150"
						formatter="gridFormatterLength">办公室电话</th>
					<th align="center" field="id" width="120" formatter="gridFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="display: none;">
		<div id="curdaddForm" style="width: 70%; height: 450px;"
			data-options="iconCls:'icon-save',modal:true,
    			minimizable:true,maximizable:true,collapsible:true,draggable:false">
			<form id="addForm" method="post">
				<input type="hidden" name="userInfo.userid" id="goId" /> <input
					type="hidden" name="userInfo.id" />
				<div style="margin: 0 auto;" align="center" name="addDiv">
					<table class="detailTable" cellpadding="5px"
						style="font-size: 12px;" cellspacing="1" border="0"
						bgcolor="#aed0ea" width="95%">
						<tr>
							<th class="table_nemu_new" colspan="4">人员信息设置</th>
						</tr>
						<tr>
							<td class="table_nemu_new">排列序号:</td>
							<td class="table_con"><input type="text"
								class="easyui-numberbox" name="userInfo.sortid"
								data-options="validType:'maxByteLength[100]',required:true" />
							</td>

							<td class="table_nemu_new">姓名:</td>
							<td class="table_con"><input type="text"
								class="easyui-validatebox" name="userInfo.name" id="name"
								readonly="readonly" data-options="required:true"
								onclick="showList()" /></td>
						</tr>

						<tr>
							<td class="table_nemu_new">性别:</td>
							<td class="table_con"><select class="easyui-combobox"
								name="userInfo.sex" style="width: 100px;"
								data-options="required:true,readOnly:true" panelHeight="auto">
									<option value="男">男</option>
									<option value="女">女</option>
							</select></td>

							<td class="table_nemu_new">出生年月:</td>
							<td class="table_con"><input type="text"
								name="userInfo.borndate" readonly="readonly"
								class="easyui-my97 easyui-validatebox"
								data-options="readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'" /></td>
						</tr>

						<tr>
							<td class="table_nemu_new">学历:</td>
							<td class="table_con"><select class="easyui-combobox"
								name="userInfo.record" style="width: 100px;"
								data-options="readOnly:true" panelHeight="auto">
									<option value="小学">小学</option>
									<option value="初中">初中</option>
									<option value="高中">高中</option>
									<option value="中专">中专</option>
									<option value="大专">大专</option>
									<option value="本科">本科</option>
									<option value="研究生">研究生</option>
									<option value="博士生">博士生</option>
									<option value="博士后">博士后</option>
							</select></td>

							<td class="table_nemu_new">专业:</td>
							<td class="table_con"><input type="text"
								class="easyui-validatebox" name="userInfo.profesion"
								style="width: 400px; height: 100px;" /></td>
						</tr>

						<tr>
							<td class="table_nemu_new">职务:</td>
							<td class="table_con">
								<!-- 							<select id="cc" class="easyui-combobox" name="userInfo.duties" style="width:100px;" panelHeight="auto"> -->
								<!-- 								<option value="科员">科员</option> --> <!-- 								<option value="副科长">副科长</option> -->
								<!-- 								<option value="科长">科长</option> --> <!-- 								<option value="副处长">副处长</option> -->
								<!-- 								<option value="处长">处长</option> --> <!-- 								<option value="副局长">副局长</option> -->
								<!-- 								<option value="局长">局长</option> --> <!-- 							</select> -->
								<input id="bus" class="easyui-combobox" name="userInfo.duties"
								style="width: 100px;" panelHeight="auto"
								data-options="
									valueField: 'id',
									textField: 'value',
									data: [{id: '科员',value: '科员'},{id: '副科长',value: '副科长'},{id: '科长',value: '科长'},{id: '副处长',value: '副处长'},{id: '处长',value: '处长'},{id: '副局长',value: '副局长'},{id: '局长',value: '局长'}]" />
							</td>
							<td class="table_nemu_new">工作部门:</td>
							<td class="table_con"><input type="text"
								class="easyui-validatebox" id="deptName"
								name="userInfo.department" style="width: 400px; height: 100px;"
								readonly="readonly" /></td>
						</tr>
						<tr>
							<td class="table_nemu_new">职称:</td>
							<td class="table_con"><select type="text"
								class="easyui-combobox" name="userInfo.title" panelHeight="auto">
									<option value="初级">初级</option>
									<option value="中级">中级</option>
									<option value="高级">高级</option>
							</select></td>

							<td class="table_nemu_new">手机:</td>
							<td class="table_con"><input type="text"
								class="easyui-numberbox" name="userInfo.mobilephone"
								style="width: 400px; height: 100px;" /></td>
						</tr>
						<tr>
							<td class="table_nemu_new">办公室电话:</td>
							<td class="table_con"><input type="text"
								class="easyui-numberbox" name="userInfo.cellphone"
								style="width: 400px; height: 100px;" /></td>
							<td class="table_nemu_new">电子邮件:</td>
							<td class="table_con"><input type="text"
								class="easyui-validatebox" name="userInfo.email"
								style="width: 400px; height: 100px;" /></td>
						</tr>
						<tr>
							<td class="table_nemu_new">安全职责:</td>
							<td class="table_con" colspan="3"><textarea
									class="easyui-validatebox" name="userInfo.saftyDuty"
									style="width: 400px; height: 100px;"
									style="width: 400px; height: 100px"></textarea></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	<div style="display: none;">
		<div style="height: 50%;" id="addWin" title="用户列表">
			<div>
				<form id="searchUser" method="post" style="display: inline;">
					&nbsp;&nbsp;姓名:&nbsp;&nbsp;<input class="easyui-validatebox"
						id="searchUserName" name="user.userName" width="177px"
						onkeyup="upKey(this.value)">
					<!--        &nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchUser();">查询</a>-->
					<!--		&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearSearchUser();">清空</a>-->
					<input type="hidden" id="usertype" name="usertype"
						value="${param.usertype}" />
				</form>
			</div>
			<table id="userdata_list" style="display: none">
				<thead>
					<tr>
						<th field="userName" width="30%" title="姓名" align="center">姓名</th>
						<th field="id" width="20%" title="用户id"
							formatter="usergridFormatter" align="center">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>