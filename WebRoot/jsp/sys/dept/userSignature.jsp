<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签名照维护</title>

<script type="text/javascript" src="${ctx}/js/GridUtilNoToolBar.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/validator.js"></script>
<style type="text/css">
.top {
	border: 1px solid #aed0ea;
	background: #f2f5f7 url('images/ui-bg_glass_100_e4f1fb_1x400.png')
		repeat-x;
	line-height: 28px;
	padding-left: 15px;
	font-size: 14px;
	font-weight: bold;
	color: #222222;
}

.table_title {
	border: 1px solid #cccccc;
	background: #efefef;
	font-size: 14px;
	font-weight: bold;
	color: #2779aa;
	line-height: 28px;
	padding-left: 15px;
}

.table_nemu {
	font-size: 13px;
	border: 0px;
	background: #EAF4FC;
	line-height: 28px;
	padding-left: 15px;
	width: 180px;
	text-align: left;
	padding-right: 10px;
}

.table_con {
	font-size: 13px;
	border: 0px;
	background: #fff;
	line-height: 28px;
	padding-left: 15px;
	width: 65%;
}

.btn1 {
	BORDER: #65b5e4 1px solid;
	PADDING-RIGHT: 6px;
	PADDING-LEFT: 6px;
	FONT-SIZE: 12px;
	FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,
		StartColorStr=#ffffff, EndColorStr=#d7ebf9);
	CURSOR: hand;
	COLOR: black;
	PADDING-TOP: 3px;
}

#tb {
	line-height: 32px;
	height: 35px;
	padding: 10px;
	font-size: 12px;
	font-weight: bold;
	color: #2779aa;
}

.search_add {
	margin: 5px 0;
}
</style>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;
        
        //验证输入方法
        $(function (){
            //设置text输入框需要验证输入类型
            $('input[type=text]').validatebox();
        })
        
        $.extend($.fn.validatebox.defaults.rules, {  
            equals: {  
                validator: function(value,param){  
                    return value == $(param[0]).val();  
                },  
                message: '两次输入密码不一样.'  
            }  
        }); 
        
     // 验证密码长度
        $.extend($.fn.validatebox.defaults.rules, {  
            minLength: {  
                validator: function(value, param){  
                    return value.length >= param[0];  
                },  
                message: '密码长度不能小于8位.'  
            }  
        });  

        // 当树被点击的时候所触发的事件
        function doSearch(id, name, type) {
            $('#deptTreeWin').window('close');
            $('#addDeptId').val(id);
            $('#addDeptName').val(name);
            $('#updateDeptId').val(id);
            $('#updateDeptName').val(name);
        }
        
        //查询
        function searchFunction() {
        	 $('#searchWin').window('open');
        	   // clearSearchForm();
        }
        function expFunction() {
        	//window.location='${ctx}/user/userAction!exp';
        	$('#searchForm').attr('action','${ctx}/user/userAction!exp');
        	//$('#searchForm').attr('target','_blank');
        	document.getElementById("searchForm").submit();
       	}
        function impFunction() {
        	$('#excelWin').window('open');
       	}
        function printWin(){
			$("#printContent").val($("#viewWin").outerHTML());
			printForm.submit();        	
        }
        
        function searchForm() {                       
          //提交查询表单
			 $('#searchForm').form({
			     url:'${ctx}/user/userAction!pageList.action',
			     success:function (data) {
			     	var data = eval('(' + data + ')');
					closeAllWin();
			        grid.reloadGridByData(data);
			     }
			 });
			 $('#searchForm').submit();
			$('#searchWin').window('close');
        }
        function clearSearchForm() {
        	$('#searchAccount').val('');
        	$('#searchUserName').val('');
        }
        
        

        // 当点击添加按钮时做的操作
        function addFunction() {
            $('#addWin').window('open');
            clearAddForm();
        }

        // 提交添加菜单请求
        function addSubmitForm() {
            $('#addForm').form({
                url:'${ctx}/user/userAction!addUser.action',
                method:'POST',
                success:function (data) {
                	var data = eval('(' + data + ')');
					closeAllWin();
                    if (data.operateSuccess) {
                    	$.messager.alert('成功',data.operateMessage); 
                        grid.reloadGrid();
                    } else {
                    	$.messager.alert('失败',data.operateMessage); 
                    }
                }
            });
            $('#addForm').submit();
        }

        function updateSubmitForm() {
            $('#updateForm').form({
                url:'${ctx}/user/userAction!updateUser.action',
                success:function (data) {
                	closeAllWin();
                	var data = eval('(' + data + ')');
                    if (data.operateSuccess) {
                    	$.messager.alert('成功',data.operateMessage); 
                        grid.reloadGrid();
                    } else {
                    	$.messager.alert('失败',data.operateMessage); 
                    }
                }
            });
            $('#updateForm').submit();
        }

        // 清除添加表单中的数据
        function clearAddForm() {
            $('#addAccount').val('');
            $('#addPassword').val('');
            $('#rpwd').val('');
            $('#addUserName').val('');
            $('#addDeptId').val('');
            $('#addDeptName').val('');
        }

        // 清除更新表单中的数据
        function clearUpdateForm() {
            $('#updateId').val('');
            $('#updateAccount').val('');
            $('#updatePassword').val('');
            $('#updateUserName').val('');
            $('#updateDeptId').val('');
            $('#updateDeptName').val('');
            $('#updateUserStatus').val('');
        }

        function clearViewForm() {
            $('#viewAccount').val('');
            $('#viewPassword').val('');
            $('#viewUserName').val('');
            $('#viewDeptName').val('');
            $('#viewUserStatus').val('');
        }

        // 填充更新表单中的数据
        function fillUpdateForm(id) {
            $.ajax({
                url:'${ctx}/user/userAction!getByIdDTO.action?id=' + id+"&usertype="+$("#usertype").val(),
                method:'POST',
                success:function (data) {
                    $('#updateId').val(data.id);
                    $('#updateAccount').text(data.account);
                    $('#updatePassword').val("password");
                    $('#updateUserName').val(data.userName);
                    $('#updateDeptId').val(data.deptId);
                    $('#updateDeptName').val(data.deptName);
                    $('#updateUserStatus').combobox('setValue', data.status);
                     if(data.business == null)
                    {
                     $('#updateBusiness').combobox('setValue', '');
                    }
                    else
                    {
                     $('#updateBusiness').combobox('setValue', data.business);
                    }
                     
                }
            });
        }

        // 填充查看菜单的内容
        function fillViewForm(id) {
            $.ajax({
                url:'${ctx}/user/userAction!getByIdDTO.action?id=' + id+"&usertype="+$("#usertype").val(),
                method:'POST',
                success:function (data) {
                    $('#viewAccount').text(data.account);
                    $('#viewPassword').text(data.password);
                    $('#viewUserName').text(data.userName);
                    $('#viewDeptName').text(data.deptName);
                    if(data.business == null)
                    {
                     $('#viewBusiness').text('');
                    }
                    else
                    {
                     $('#viewBusiness').text(data.business);
                    }
                   
                    if (data.status == 1) {
                    $('#viewUserStatus').text('正常');
                    } else {
                    	$('#viewUserStatus').text('锁定');
                    }
                }
            });
        }

        function editFunction(rowId) {
            $('#updateWin').window('open');
            clearUpdateForm();
            fillUpdateForm(rowId);
        }


        function removeFunction() {
            var rows = grid.getSelectNodes();
            if (rows.length == 0) {
                $.messager.show({
                    title:'提示',
                    msg:'请选择要删除的行',
                    timeout:2000,
                    showType:'slide'
                });
                return;
            } else {
                var ids = new Array();
                $.messager.confirm('警告', '是否要删除该记录?', function (r) {
                    if (r) {
                        var i = 0;
                        for (i=0; i<rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url:'${ctx}/user/userAction!deleteByIds?ids=' + ids,
                            method:'POST',
                            success:function(data) {
                            	var data = eval('(' + data + ')');
                                if (data.operateSuccess) {
                                	$.messager.alert('成功',data.operateMessage); 
                                    grid.reloadGrid();
                                } else {
                                	$.messager.alert('失败',data.operateMessage); 
                                }
                            }
                        });
                    }
                });

            }

        }


        function viewFunction(rowId) {
            $('#viewWin').window('open');
            clearViewForm();
            fillViewForm(rowId);
        }

        function showDeptTreeWin() {
            $('#deptTreeWin').window('open');
        }
        $(document).ready(
                function () {
                    $('#addWin').window({
                        width:382,
                        height:400,
                        modal:true,
                        closed:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#updateWin').window({
                        width:382,
                        height:400,
                        modal:true,
                        closed:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#viewWin').window({
                        width:382,
                        height:400,
                        modal:true,
                        closed:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#searchWin').window({
                        width:382,
                        height:400,
                        model:true,
                        closed:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#excelWin').window({
                        width:500,
                        height:200,
                        model:true,
                        closed:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
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
                    grid = new Grid('用户列表', 'icon-edit',
                            '${ctx}/user/userAction!pageList.action?usertype=${param.usertype}',
                            'data_list', addFunction, editFunction,
                            viewFunction, removeFunction, searchFunction);
                    grid.loadGrid();
                    tree = new Tree('${ctx}/dept/deptAction!reloadDeptTree.action', 'dept_tree', 'tree_search', 'key', doSearch);
                    tree.loadTree();
                });

        function closeAllWin() {
            $('#addWin').window('close');
            $('#updateWin').window('close');
            $('#viewWin').window('close');
            $('#searchWin').window('close');
            $('#excelWin').window('close');
        }
        
        function gridFormatter(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var url = ""
            <shiro:hasPermission name="48:view">
             url += "<a title=\"查看附件\" onclick='attachmentFunction(\""+rowId+"\");'  class='btn2'><img src=\"${ctx}/images/selectAttachment.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;";
            </shiro:hasPermission>
        	return url;
        }
        
        
           //调用附件页面
	function attachmentFunction(rowId) {
			$('#attachmentFrame').attr('src', '${ctx}/jsp/sys/fileUpload/uploadFile.jsp?relationId=' + rowId);
			$('#viewAttachmentForm').dialog({
				title:'附件管理',
				modal:true,
				draggable:false,
				minimizable:true,
				maximizable:true,
				maximized:true,
				buttons:[{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function() {
						$('#viewAttachmentForm').dialog('close');
					}
				}]
			});
	}
           
       function gridFormatterIMG(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var url = "";

        	 url="<img src='${ctx}/getUploadFileAdd/getUploadFileAdd!getImage?getAddressById="+rowId+"'alt='图片' width='100px' height='40px' onerror='displayImg(this)'/>";
        	return url;
       }
       function displayImg(myObject){
    	   myObject.src="${ctx}/images/errornull.jpg";
       }
           
</script>
</head>
<body>

	<div class="panel-header" style="width: 100%;">
		<div class="panel-title panel-with-icon">用户列表</div>
		<div class="panel-icon icon-edit"></div>
		<div class="panel-tool"></div>
	</div>
	<div class="datagrid-toolbar" id="tb"
		style="padding-left: 0px; height: 30px;">
		<div>
			<form id="searchForm" method="post" style="display: inline;">
				<!--  &nbsp;&nbsp;登录名:&nbsp;&nbsp;<input class="easyui-validatebox" id="searchAccount" name="user.account" data-options="validType:'length[1,25]'"> -->
				&nbsp;&nbsp;姓名:&nbsp;&nbsp;<input class="easyui-validatebox"
					id="searchUserName" name="user.userName"
					data-options="validType:'length[1,25]'"> &nbsp;&nbsp;<a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-search" onclick="searchForm();">查询</a> &nbsp;&nbsp;<a
					href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-clear" onclick="clearSearchForm();">清空</a> <input
					type="hidden" id="usertype" name="usertype"
					value="${param.usertype}" />
			</form>
		</div>
	</div>
	<div class="search_add">
		<!-- <shiro:hasPermission name="48:add">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="addFunction();">添加</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="48:delete">		
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="removeFunction();">删除</a>
	</shiro:hasPermission>-->
	</div>
	<div style="height: 74%;">
		<table id="data_list" style="display: none">
			<thead>
				<tr>
					<th field="deptName" width="28%" title="部门" align="center">部门</th>
					<th field="userName" width="28%" title="姓名" align="center">姓名</th>
					<th field="getAddressById" width="28%" title="图片"
						formatter="gridFormatterIMG" align="center">图片</th>
					<th field="id" width="16%" title="用户id" formatter="gridFormatter"
						align="center">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 添加窗口 -->
	<div style="display: none">
		<div id="addWin" iconCls="icon-save" title="添加用户信息">
			<form style="padding: 10px 20px 10px 20px;" id="addForm"
				method="post">
				<input type="hidden" name="user.userType" value="${param.usertype}" />
				<input type="hidden" name="user.deptId" id="addDeptId" />
				<table cellpadding="5px" style="font-size: 12px;" cellspacing="1"
					border="0" bgcolor="#aed0ea">
					<tr>
						<td class="table_nemu">登录名:</td>
						<td class="table_con"><input class="easyui-validatebox"
							type="text" validtype="codechick" id="addAccount"
							name="user.account" data-options="required: true"
							missingMessage="请输入帐号" /></td>
					</tr>
					<tr>
						<td class="table_nemu">密 码:</td>
						<td class="table_con"><input style="width: 155px"
							class="easyui-validatebox" type="password" id="addPassword"
							name="user.plainPassword"
							data-options="required: true, validType:'minLength[8]'"
							missingMessage="请输入登录密码" /></td>
					</tr>
					<tr>
						<td class="table_nemu">确认密码：</td>
						<td class="table_con"><input style="width: 155px" id="rpwd"
							name="rpwd" type="password" class="easyui-validatebox" required
							validType="equals['#addPassword']" /></td>
					</tr>
					<tr>
						<td class="table_nemu">姓名：</td>
						<td class="table_con"><input class="easyui-validatebox"
							id="addUserName" name="user.userName"
							data-options="required: true,validType:'length[0,25]',invalidMessage:'输入的内容不能超过25个字符'"
							missingMessage="请输入用户姓名" /></td>
					</tr>
					<tr>
						<td class="table_nemu">单位：</td>
						<td class="table_con"><input class="easyui-vlidatebox"
							id="addDeptName" readonly="readonly" onclick="showDeptTreeWin();" /></td>
					</tr>
					<tr>
						<td class="table_nemu">职务：</td>
						<td class="table_con"><input class="easyui-combobox"
							id="addBusiness" name="user.business"
							data-options="valueField: 'id',textField: 'value',data: [{id: '科员',value: '科员'},{id: '科长',value: '科长'},{id: '副局长',value: '副局长'},{id: '局长',value: '局长'}],panelHeight:'auto',width:'100',editable:false" /></td>
					</tr>
				</table>
				<br />
				<div align="center">
					<a href="#" class="easyui-linkbutton" icon="icon-ok"
						onclick="addSubmitForm();return false;">确定</a> <a href="#"
						class="easyui-linkbutton" icon="icon-cancel"
						onclick="closeAllWin();return false;">取消</a>
				</div>

			</form>
		</div>
	</div>

	<!-- 更新窗口 -->
	<div style="display: none">
		<div id="updateWin" iconCls="icon-save" title="更新用户信息">
			<form style="padding: 10px 20px 10px 20px;" id="updateForm"
				method="post">
				<input type="hidden" id="updateId" name="user.id" /> <input
					type="hidden" name="user.deptId" id="updateDeptId" /> <input
					type="hidden" name="user.userType" value="${param.usertype}" />
				<table cellpadding="5px" style="font-size: 12px;" cellspacing="1"
					border="0" bgcolor="#aed0ea">
					<tr>
						<td class="table_nemu">登录名:</td>
						<td class="table_con"><label type="text"
							class="easyui-validatebox" id="updateAccount" name="user.account"></label></td>
					</tr>
					<tr>
						<td class="table_nemu">密 码:</td>
						<td class="table_con"><input style="width: 155px"
							class="easyui-validatebox" type="password" id="updatePassword"
							name="user.plainPassword"
							data-options="required: true, validType:'minLength[8]'" /></td>
					</tr>
					<tr>
						<td class="table_nemu">姓名:</td>
						<td class="table_con"><input class="easyui-validatebox"
							id="updateUserName" name="user.userName"
							data-options="required: true,validType:'length[0,25]',invalidMessage:'输入的内容不能超过25个字符'" /></td>
					</tr>
					<tr>
						<td class="table_nemu">状态：</td>
						<td class="table_con"><input style="width: 155px"
							class="easyui-combobox" id="updateUserStatus" name="user.status"
							data-options="valueField:'val',textField:'text', editable:false, data:[{val:'1',text:'正常'},{val:'2', text:'锁定'}]" /></td>
					</tr>
					<c:if test="${param.usertype!='3'}">
						<tr>
							<td class="table_nemu">单位：</td>
							<td class="table_con"><input class="easyui-vlidatebox"
								id="updateDeptName" readonly onclick="showDeptTreeWin();" /></td>
						</tr>
					</c:if>
					<tr>
						<td class="table_nemu">职务：</td>
						<td class="table_con"><input class="easyui-combobox"
							id="updateBusiness" name="user.business"
							data-options="valueField: 'id',textField: 'value',data: [{id: '科员',value: '科员'},{id: '科长',value: '科长'},{id: '副局长',value: '副局长'},{id: '局长',value: '局长'}],panelHeight:'auto',width:'100',editable:false" /></td>
					</tr>
				</table>
				<br />
				<div align="center">
					<a href="#" class="easyui-linkbutton" icon="icon-ok"
						onclick="updateSubmitForm();return false;">确定</a> <a href="#"
						class="easyui-linkbutton" icon="icon-cancel"
						onclick="closeAllWin();return false;">取消</a>
				</div>
			</form>
		</div>
	</div>

	<!-- 查看窗口 -->
	<div style="display: none">
		<div id="viewWin" iconCls="icon-save" title="查看用户信息">
			<form style="padding: 10px 20px 10px 20px;">
				<table cellpadding="5px" style="font-size: 12px;" cellspacing="1"
					border="0" bgcolor="#aed0ea">
					<tr>
						<td class="table_nemu">登录名:</td>
						<td class="table_con"><label class="easyui-validatebox"
							id="viewAccount" name="user.account"></label></td>
					</tr>
					<tr>
						<td class="table_nemu">姓名:</td>
						<td class="table_con"><label class="easyui-validatebox"
							id="viewUserName" name="user.userName"></label></td>
					</tr>
					<tr>
						<td class="table_nemu">状态：</td>
						<td class="table_con"><label class="easyui-validatebox"
							id="viewUserStatus" name="user.status"></label></td>
					</tr>
					<c:if test="${param.usertype!='3'}">
						<tr>
							<td class="table_nemu">单位：${param.usertype}</td>
							<td class="table_con"><label class="easyui-validatebox"
								id="viewDeptName"></label></td>
						</tr>
					</c:if>
					<tr>
						<td class="table_nemu">职务：</td>
						<td class="table_con"><label class="easyui-validatebox"
							id="viewBusiness" name="user.business"></label></td>
					</tr>
				</table>
				<br />
				<div align="center">
					<a href="#" class="easyui-linkbutton" icon="icon-print"
						onclick="printWin();return false;">打印</a> <a href="#"
						class="easyui-linkbutton" icon="icon-cancel"
						onclick="closeAllWin();return false;">取消</a>
				</div>
			</form>
		</div>
	</div>

	<!-- 部门树展现 -->
	<div style="display: none">
		<div id="deptTreeWin" iconCls="icon-save" title="部门树">
			<div id="p" class="easyui-panel" title="部门树" fit="true" tools="#tt"
				border="false" plain="true">
				<div id="tree_search" style="display: none">
					关键字:<input type="text" id="key" name="key" style="width: 80px;" /><a
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
	<div id="excelWin" iconCls="icon-save" title="上传Excel文件">
		<form name="excelForm" method="post" enctype="multipart/form-data"
			action="${ctx}/user/userAction!imp"
			style="padding: 10px 20px 10px 20px;" target="excelFrame">
			上传Excel文件<input type="file" name="excelFile" id="excelFile" /> <input
				type="submit" value="上传" />
		</form>
		<iframe id="excelFrame" name="excelFrame" frameborder="0" width="100%"
			height="100"></iframe>
	</div>
	<form name="printForm" method="post" action="${ctx}/common/print.jsp"
		target="_blank">
		<input type="hidden" name="printContent" id="printContent" />
	</form>

	<!--附件层 -->
	<div style="display: none;">
		<div id="viewAttachmentForm" style="width: 960px; height: 400px;"
			data-options="iconCls:'icon-save'">
			<iframe frameborder="0" id="attachmentFrame" width="100%"
				height="98%" scrolling-x="no"></iframe>
		</div>
	</div>
</body>
</html>