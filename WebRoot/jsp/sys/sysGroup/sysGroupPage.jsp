<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户组管理</title>

<script type="text/javascript" src="${ctx}/js/GridUtilNoToolBar.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/TreeCheckBox.js"></script>
<script type="text/javascript">
        var grid = null;
        var key = null;
        var tree = null;
        
     // 当树被点击的时候所触发的事件
        function doSearch(id, name, type) {
        }

        // 当点击添加按钮时做的操作
        function addFunction() {
            $('#addWin').window('open');
            clearAddForm();
        }
        
     // 提交添加菜单请求
        function addSubmitForm() {
            $('#addForm').form({
            	url:'${ctx}/sysGroup/sysGroup!addSysGroup.action',
                method:'POST',
                success:function (data) {
                	var data = eval('(' + data + ')');
					closeAllWin();
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
            $('#addForm').submit();
        }
        
        function updateSubmitForm() {
            $('#updateForm').form({
            	url:'${ctx}/sysGroup/sysGroup!updateSysGroup.action',
                success:function (data) {
                	closeAllWin();
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
            $('#updateForm').submit();
        }

        // 清除添加表单中的数据
        function clearAddForm() {
            $('#addGroupName').val('');
            $('#addDescription').val('');
        }

        // 添加更新表单中的数据
        function clearUpdateForm() {
            $('#id').val('');
            $('#updateGroupName').val('');
            $('#updateDescription').val('');
        }
        function clearViewForm() {
            $('#viewGroupName').val('');
            $('#viewDescription').val('');
        }

        // 填充更新表单中的数据
        function fillUpdateForm(id) {
            $.ajax({
                url:'${ctx}/sysGroup/sysGroup!getByIdDTO.action?id=' + id,
                method:'POST',
                success:function (data) {
                    $('#id').val(data.id);
                    $('#updateGroupName').val(data.groupName);
                    $('#updateDescription').val(data.description);
                }
            });
        }

        // 填充查看菜单的内容
        function fillViewForm(id) {
            $.ajax({
                url:'${ctx}/sysGroup/sysGroup!getByIdDTO.action?id=' + id,
                method:'POST',
                success:function (data) {
                    $('#viewGroupName').text(data.groupName);
                    $('#viewDescription').text(data.description);
                }
            });
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
                        	url:'${ctx}/sysGroup/sysGroup!deleteByIds?ids=' + ids,
                            method:'POST',
                            success:function(data) {
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
                    }
                });

            }

        }
        //查看
        function viewFunction(obj) {
            $('#viewWin').window('open');
            clearViewForm();
            fillViewForm(obj.rowId);
        }
        //修改
        function editFunction(obj) {
            $('#updateWin').window('open');
            clearUpdateForm();
            fillUpdateForm(obj.rowId);
        }
        
        $(document).ready(
                function () {
                    $('#addWin').window({
                        width:382,
                        height:400,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#updateWin').window({
                        width:382,
                        height:400,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#viewWin').window({
                        width:382,
                        height:400,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#searchWin').window({
                        width:382,
                        height:400,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#deptUserTreeWin').window({
                        width:382,
                        height:400,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#addWin').window('close');
                    $('#updateWin').window('close');
                    $('#viewWin').window('close');
                    $('#deptUserTreeWin').window('close');
                    $('#searchWin').window('close');
                    grid = new Grid('用户组列表', 'icon-edit',
                            '${ctx}/sysGroup/sysGroup!pageList.action',
                            'data_list', addFunction, editFunction,
                            viewFunction, removeFunction,searchFunction);
                    grid.loadGrid();
                    tree = new Tree('${ctx}/sysGroup/sysGroup!reloadDeptUserTree.action', 'dept_tree', 'tree_search', 'key', doSearch);
                    tree.loadTree();
                });

        function closeAllWin() {
            $('#addWin').window('close');
            $('#updateWin').window('close');
            $('#viewWin').window('close');
            $('#deptUserTreeWin').window('close');
            $('#searchWin').window('close');
        }
        
        function showDeptUserTreeWin(obj) {
        	tree.cancelAllNodes();
        	var groupId = obj.rowId;
        	document.getElementById("groupId").value = groupId;
        	$.ajax({
            	url:'${ctx}/sysGroup/sysGroup!getUserIds?groupId=' + groupId,
                method:'POST',
                success:function(data) {
                	checkByUserIds(data);
                }
            });
            $('#deptUserTreeWin').window('open');
        }
        
        function checkByUserIds(userStr){
        	var userIds =  userStr.split(";");
        	for(var i=0;i<userIds.length;i++){
        		if(null!=userIds[i]){
        			tree.checkNodeById(userIds[i]);
        		}
        	}
        }
        
        
        function gridFormatter(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var url = ""
        	<shiro:hasPermission name="49:view">
        	+"<input type='button' onclick='viewFunction(this);' value='查看' rowId='"+rowId+"'/>&nbsp;&nbsp;"
        	</shiro:hasPermission>
        	<shiro:hasPermission name="49:update">
        	+"<input type='button' onclick='editFunction(this);' value='修改'  rowId='"+rowId+"'/>&nbsp;&nbsp;"
        	</shiro:hasPermission>
        	<shiro:hasPermission name="49:custom1">
        	+"<input type='button' onclick='showDeptUserTreeWin(this)' value='设置成员'  rowId='"+rowId+"'/>";
        	</shiro:hasPermission>
        	return url;
        }
        
        function setPersons(){
        	var nodes = tree.getCheckedNodes();
        	var users = [];
        	for(var i= 0;i<nodes.length;i++){
        		if(nodes[i].nodeType=='User'){
        			users.push(nodes[i].id);
        		}
        	}
         	var usersStr = users.join(";");
         	document.getElementById("usersStr").value = usersStr;
         	submitUsers();
        }
        //提交用户组成员
        function submitUsers() {
            $('#userGroupForm').form({
            	url:'${ctx}/sysGroup/sysGroup!updateUserGroup.action',
                success:function (data) {
                	closeAllWin();
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
            $('#userGroupForm').submit();
        }

		function searchFunction(){
			$('#searchWin').window('open');
			//clearSearchForm();
		}
		
		function clearSearchForm() {
			$('#searchGroupName').val ('');
		}
		function searchForm(){
		$('#searchForm').form({
			url:'${ctx}/sysGroup/sysGroup!pageList.action',
			success: function (data) {
				var data = eval('('+data+')');
				closeAllWin();
				grid.reloadGridByData(data);
			}
		});
		$('#searchForm').submit();
        $('#searchWin').window('close');
		}
		
		


</script>
</head>
<body>

	<div class="panel-header" style="width: 100%;">
		<div class="panel-title panel-with-icon">用户组列表</div>
		<div class="panel-icon icon-edit"></div>
		<div class="panel-tool"></div>
	</div>
	<div class="datagrid-toolbar">
		<shiro:hasPermission name="49:add">
			<a href="javascript:addFunction()" style="float: left;"
				class="l-btn l-btn-plain"> <span class="l-btn-left"> <span
					class="l-btn-text icon-add" style="padding-left: 20px;">添加</span>
			</span>
			</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="49:delete">
			<a href="javascript:removeFunction()" style="float: left;"
				class="l-btn l-btn-plain"> <span class="l-btn-left"> <span
					class="l-btn-text icon-remove" style="padding-left: 20px;">删除</span>
			</span>
			</a>
		</shiro:hasPermission>
		<a href="javascript:searchFunction()" style="float: left;"
			class="l-btn l-btn-plain"> <span class="l-btn-left"> <span
				class="l-btn-text icon-search" style="padding-left: 20px;">查询</span>
		</span>
		</a>
	</div>
	<div style="height: 80%">

		<table id="data_list">
			<thead>
				<tr>
					<th field="groupName" width="150" title="用户组名称">用户组名称</th>
					<th field="description" width="150" title="用户组描述">用户组描述</th>
					<th field="id" width="120" title="操作" formatter="gridFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 添加窗口 -->
	<div id="addWin" iconCls="icon-save" title="添加用户组信息">

		<form style="padding: 10px 20px 10px 85px;" id="addForm">
			<p>
				用户组名称:<input class="easyui-validatebox" id="addGroupName"
					name="sysGroup.groupName" data-options="required: true"
					missingMessage="请输入用户组名称" />
			</p>

			<p>
				用户组描述:<input class="easyui-validatebox" id="addDescription"
					name="sysGroup.description" data-options="required: true"
					missingMessage="请输入用户组描述" />
			</p>

			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="addSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>

	</div>

	<!-- 更新窗口 -->
	<div id="updateWin" iconCls="icon-save" title="修改用户组信息">
		<form style="padding: 10px 20px 10px 85px;" id="updateForm">
			<input type="hidden" id="id" name="id" />

			<p>
				用户组名称:<input class="easyui-validatebox" id="updateGroupName"
					name="sysGroup.groupName" data-options="required: true"
					missingMessage="请输入用户组名称" />
			</p>

			<p>
				用户组描述:<input class="easyui-validatebox" id="updateDescription"
					name="sysGroup.description" data-options="required: true"
					missingMessage="请输入用户组描述" />
			</p>

			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="updateSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!-- 查看窗口 -->
	<div id="viewWin" iconCls="icon-save" title="查看用户组信息">
		<form style="padding: 10px 20px 10px 85px;">
			<p>
				用户组名称:<label class="easyui-validatebox" id="viewGroupName"
					name="sysGroup.groupName"></label>
			</p>
			<p>
				用户组描述:<label class="easyui-validatebox" id="viewDescription"
					name="sysGroup.description"></label>
			</p>
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!-- 查询窗口 -->
	<div id="searchWin" iconCls="icon-save" title="用户组信息查询">
		<form style="padding: 10px 20px 10px 85px;" id="searchForm">
			<p>
				用户组名称:<input class="easyui-validatebox" id="searchGroupName"
					name="sysGroup.groupName" />
			</p>
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="searchForm();return false;">查询</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!-- 部门人员树展现 -->

	<div id="deptUserTreeWin" iconCls="icon-save" title="设置成员">
		<div id="p" class="easyui-panel" title="部门人员树" fit="true" tools="#tt"
			border="false" plain="true">
			<div id="tree_search" style="display: none">
				关键字:<input type="text" id="key" name="key" style="width: 80px;" /><a
					href="#" class="easyui-linkbutton" iconCls="icon-search"
					plain="true" onclick="tree.searchNode();">搜索</a>
			</div>
			<div id="dept_tree" class="ztree"></div>
			<br> <input type="button" value="保存" onclick="setPersons();">
		</div>
		<div id="tt">
			<a href="#" class="icon-treeSearch"
				onclick="javascript:tree.changeTreeSearchPanelShow();" title="搜索"></a>
			<a href="#" class="icon-treeRefresh"
				onclick="javascript:tree.loadTree();" title="刷新"></a> <a href="#"
				class="icon-treeAdd" onclick="javascript:tree.expandAllNode(false);"
				title="收缩所有"></a> <a href="#" class="icon-treeDelete"
				onclick="javascript:tree.expandAllNode(true);" title="展开所有"></a>
		</div>
	</div>
	<form style="height: 0%; weight: 0%" id="userGroupForm">
		<input type="hidden" id="usersStr" name="usersStr" /> <input
			type="hidden" id="groupId" name="groupId" />
	</form>
</body>
</html>