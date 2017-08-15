<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色权限管理</title>
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/TreeCheckBox.js"></script>
<script type="text/javascript"
	src="${ctx}/js/tree/MenuPermissionTreeWithCheckBox.js"></script>
<style>
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
	border: 0px;
	background: #E3F1FA;
	font-size: 13px;
	color: #222222;
	text-align: right;
	line-height: 28px;
	padding-right: 15px;
	width: 40%
}

.table_nemu span {
	font-size: 15px;
	color: red;
	font-weight: bold;
}

.table_con {
	border: 0px;
	background: #fff;
	line-height: 28px;
	padding-left: 15px;
	width: 60%
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
        var key = null;
        var tree = null;
        var deptUserTree = null;
        var menuOptTree = null;
        var roleDataIdParam2 = "";
        
     // 当树被点击的时候所触发的事件
        function doSearch(id, name, type) {
        }

        // 当点击添加按钮时做的操作
        function addFunction() {
            $('#addWin').window('open');
            clearAddForm();
            $('#roleType').val(1);
        }
        
     // 提交添加菜单请求
        function addSubmitForm() {
            $('#addForm').form({
            	url:'${ctx}/sysRole/sysRole!addSysRole.action',
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
        //提交更新菜单请求
        function updateSubmitForm() {
            $('#updateForm').form({
            	url:'${ctx}/sysRole/sysRole!updateSysRole.action',
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
            $('#addRoleName').val('');
            $('#addDescription').val('');
        }

        // 添加更新表单中的数据
        function clearUpdateForm() {
            $('#id').val('');
            $('#updateRoleName').val('');
            $('#updateDescription').val('');
        }
        function clearViewForm() {
            $('#viewRoleName').val('');
            $('#viewDescription').val('');
        }

        // 填充更新表单中的数据
        function fillUpdateForm(id) {
            $.ajax({
                url:'${ctx}/sysRole/sysRole!getByIdDTO.action?id=' + id,
                method:'POST',
                success:function (data) {
                    $('#id').val(data.id);
                    $('#updateRoleName').val(data.roleName);
                    $('#updateDescription').val(data.description);
                }
            });
        }

        // 填充查看菜单的内容
        function fillViewForm(id) {
            $.ajax({
                url:'${ctx}/sysRole/sysRole!getByIdDTO.action?id=' + id,
                method:'POST',
                success:function (data) {
                    $('#viewRoleName').text(data.roleName);
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
                        	url:'${ctx}/sysRole/sysRole!deleteByIds?ids=' + ids,
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
        //查看
        function viewFunction(rowId) {
            $('#viewWin').window('open');
            clearViewForm();
            fillViewForm(rowId);
        }
        //修改
        function editFunction(rowId) {
            $('#updateWin').window('open');
            clearUpdateForm();
            fillUpdateForm(rowId);
        }
        function onCheck(obj){
	       	var mtree=menuOptTree.getZTreeObj();
	       	mtree.checkNode(mtree.getNodeByTId(obj.parentNode.getAttribute('id')),true,true);
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
                    $('#deptTreeWin').window({
                        width:382,
                        height:485,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#deptUserTreeWin').window({
                        width:382,
                        height:485,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#roleGroupWin').window({
                        width:382,
                        height:485,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#menuOptTreeWin').window({
                        width:500,
                        height:500,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#roleDataWin').window({
                    	width: 500,
                    	height: 500,
                    	modal: true,
                    	onOpen:function() {
                    		$('.window-mask').css("height", "100%");
                    	}
                    });
                    
                    $('#selectTableWin').window({
                    	width: 800,
                    	height: 500,
                    	modal: true,
                    	onOpen: function() {
                    		$('.window-mask').css('height', '100%');
                    	}
                    });
                    closeAllWin();
                    grid = new Grid('${ctx}/sysRole/sysRole!pageList.action?sysRole.roleType=1','data_list');
                    grid.loadGrid();
                    tree = new Tree('${ctx}/dept/deptAction!reloadDeptTree.action', 'dept_tree', 'tree_search', 'key', doSearch);
                    tree.loadTree();
                    deptUserTree = new Tree('${ctx}/sysGroup/sysGroup!reloadDeptUserTree.action', 'dept_user_tree', 'dept_user_tree_search', 'key', doSearch);
                    deptUserTree.loadTree();
//                     menuOptTree = new MenuTree('${ctx}/menu/menuAction!menuTreeLoad.action', '${ctx}/menu/menuAction!getMenuOptByMenuId.action', 'menu_opt_tree', 'menu_opt_tree_search', 'key', doSearch);
//                     menuOptTree.loadTree();
                });

        function closeAllWin() {
            $('#addWin').window('close');
            $('#updateWin').window('close');
            $('#viewWin').window('close');
            $('#deptTreeWin').window('close');
            $('#deptUserTreeWin').window('close');
            $('#searchWin').window('close');
            $('#roleGroupWin').window('close');
            $('#menuOptTreeWin').window('close');
            $('#roleDataWin').window('close');
            $('#selectTableWin').window('close');    
        }
        
      //显示菜单权限树
        function showMenuOptTreeWin(roleId) {
    	  $.ajax({
    		  url:'${ctx}/menu/menuAction!menuTreeInition',
    		  success:function(data){
    			  //console.log(data);
    			  var sdata = eval('('+data+')');
    			  console.log(sdata);
	        	$('#treeGridMenu').treegrid({
	        		data:sdata,
	                idField:'id',
	                treeField:'name',
	                columns:[[
	                    {title:'菜单名称',field:'name',width:180},
	                    {field:'level',title:'菜单级别',width:60},
	                    {title:'权限',field:'id',formatter:roleParamFormatter()}
	                ]]
	            });
    		  }
    	  });
           $('#menuOptTreeWin').window('open');
        }
      function roleParamFormatter(){
    	  return "这里放权限";
      }
      	var roleIdParam = '';
      	// 显示数据权限
      	function showRoleDataWin(roleId) {
      		$.loding();
      		roleIdParam = roleId;
      		$('#role_data_list').datagrid({
      			title: '角色管理表名',
      			iconCls: 'icon-edit',
      			nowrap: false,
      			striped: true,
      			border: true,
      			collapsible: false,
      			fit: true,
      			url: '${ctx}/sysRoleData/sysRoleData!pageListByRoleId.action?roleId=' + roleId,
      			remoteSort: false,
      			idField: 'id',
      			singleSelect: false,
      			pagination: true,
      			fitColumns: true,
      			frozenColumns:[[{
      				field: 'ck',
      				checkbox: true
      			}]],
      			toolbar: [{
      				text: '添加',
      				iconCls: 'icon-add',
      				handler: showAddTableWin
      			}, {
      				text: '删除',
      				iconCls: 'icon-remove',
      				handler: deleteTable
      			}],
      			onLoadSuccess: function(){
    	            function bindRowsEvent(){
    	                var panel = $('#role_data_list').datagrid('getPanel');
    	                var rows = panel.find('tr[datagrid-row-index]');
    	                rows.unbind('click').bind('click',function(e){
    	                    return false;
    	                });
    	                rows.find('div.datagrid-cell-check input[type=checkbox]').unbind().bind('click', function(e){
    	                    var index = $(this).parent().parent().parent().attr('datagrid-row-index');
    	                    if ($(this).attr('checked')){
    	                        $('#role_data_list').datagrid('selectRow', index);
    	                    } else {
    	                        $('#role_data_list').datagrid('unselectRow', index);
    	                    }
    	                    e.stopPropagation();
    	                });
    	            }
    	            setTimeout(function(){
    	                bindRowsEvent();
    	            }, 1);    
    	    	}
      		});
      		
      		var pageSettings = {
      				pageSize: 10,
      				pageList: [10, 15, 20, 30],
      				beforePageText: '第',
      				afterPageText: '页 共{pages} 页',
      				displayMsg: '当前显示{from} - {to} 条记录  共{total}条记录'
      		};
      		$('#role_data_list').pagination(pageSettings);
      		$('#roleDataWin').window('open');
      		$.loded();
      	}
        function checkByMenuOptIds(menuOptStr){
        	var menuOptIds =  menuOptStr.split(";");
        	for(var i=0;i<menuOptIds.length;i++){
        		if(null!=menuOptIds[i]&&menuOptIds[i]!=''){
        			var obj = $('input[optId="'+menuOptIds[i]+'"]');
        			if(obj.length>0){
        				//obj[0].checked=true;
        				obj[0].click();
        			}
        			
        		}
        	}
        }
        //保存菜单权限信息
        function saveMenuOpt(){
        	var nodes = menuOptTree.getCheckBoxCheckedValue();
        	var menuOpts = [];
        	for(var i= 0;i<nodes.length;i++){
        		menuOpts.push(nodes[i]);
        	}
         	var menuOptsStr = menuOpts.join(";");
         	$('#menuOptsStr').val(menuOptsStr);
         	submitMenuOpt(menuOptsStr,$('#menuOptRoleId').val());
        }
        function submitMenuOpt(menuOptsStr,menuOptRoleId) {
            $.post('${ctx}/sysRole/sysRole!updateRoleMenuOpt.action',
            	{'menuOptsStr':menuOptsStr,'menuOptRoleId':menuOptRoleId},
                function(data) {
                	closeAllWin();
                	var data = eval('(' + data + ')');
                    if (data.operateSuccess) {
                    	$.messager.alert('成功',data.operateMessage); 
                        grid.reloadGrid();
                    } else {
                    	$.messager.alert('失败',data.operateMessage); 
                    }
                }
            );
        }
      //显示部门树
        function showDeptTreeWin(roleId) {
        	tree.cancelAllNodes();
        	$('#deptRoleId').val(roleId);
        	$.ajax({
            	url:'${ctx}/sysRole/sysRole!getDeptIds?roleId=' + roleId,
                method:'POST',
                success:function(data) {
                	if(data!=null)
                		checkByRoleIds(data);
                }
            });
        	$('#roleDeptSave').show();
        	$('#roleDataDeptSave').hide();
            $('#deptTreeWin').window('open');
        }  
              
        function checkByRoleIds(deptStr){
        	var deptIds =  deptStr.split(";");
        	for(var i=0;i<deptIds.length;i++){
        		if(null!=deptIds[i] && deptIds!=''){
	        		var zTree = tree.getZTreeObj();
	        		var obj = zTree.getNodeByParam("id", deptIds[i], null);
	        		if(obj!=null)
	        		zTree.checkNode(obj, true, true);
        			//tree.checkNodeById(deptIds[i]);
        		}
        	}
        }
        
        function saveRoleDataDept() {
        	var nodes = tree.getCheckedNodes();
        	var roleDepts = [];
        	for(var i= 0;i<nodes.length;i++){
        		if(nodes[i].leaf==true){
        			roleDepts.push(nodes[i].id);
        		}
        	}
         	var roleDeptStr = roleDepts.join(",");
         	//alert(roleDataIdParam2);
         	$.ajax({
         		url: '${ctx}/sysRoleData/sysRoleData!updateDeptIds.action',
         		data:{'roleDeptStr':roleDeptStr, 'roleDataId': roleDataIdParam2},
         		method:'POST',
         		success:function(data) {
         			data = eval('(' + data + ')');
         			if (data.operateSuccess) {
         				$.messager.alert('提示', data.operateMessage);
         				$('#deptTreeWin').window('close');
         			} else {
         				$.messager.alert('警告', data.operateMessage);
         			}
         		}
         	});
        }
        
        function saveRoleDataUser() {
        	var nodes = deptUserTree.getCheckedNodes();
        	var users = [];
        	for(var i= 0;i<nodes.length;i++){
        		if(nodes[i].nodeType=='User'){
        			users.push(nodes[i].id);
        		}
        	}
         	var userRoleStr = users.join(",");
         	$.ajax({
         		url: '${ctx}/sysRoleData/sysRoleData!updateUserIds.action',
         		data:{'userRoleStr':userRoleStr, 'roleDataId': roleDataIdParam2},
         		method:'POST',
         		success:function(data) {
         			data = eval('(' + data + ')');
         			if (data.operateSuccess) {
         				$.messager.alert('提示', data.operateMessage);
         				$('#deptUserTreeWin').window('close');
         			} else {
         				$.messager.alert('警告', data.operateMessage);
         			}
         		}
         	});
        }
        //保存部门树信息
        function saveRoleDept(){
        	var nodes = tree.getCheckedNodes();
        	var roleDepts = [];
        	for(var i= 0;i<nodes.length;i++){
        		if(nodes[i].leaf==true){
        			roleDepts.push(nodes[i].id);
        		}
        	}
         	var roleDeptsStr = roleDepts.join(";");
         	$('#roleDeptsStr').val(roleDeptsStr);
         	submitRoleDept();
        }
        function submitRoleDept() {
            $('#roleDeptForm').form({
            	url:'${ctx}/sysRole/sysRole!updateRoleDept.action',
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
            $('#roleDeptForm').submit();
        }
        
        //保存部门人员树
        function setPersons(){
        	var nodes = deptUserTree.getCheckedNodes();
        	var users = [];
        	for(var i= 0;i<nodes.length;i++){
        		if(nodes[i].nodeType=='User'){
        			users.push(nodes[i].id);
        		}
        	}
         	var usersStr = users.join(";");
         	$('#usersStr').val(usersStr);
         	submitUsers();
        }
        function submitUsers() {
            $('#roleUserForm').form({
            	url:'${ctx}/sysRole/sysRole!updateRoleUser.action',
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
            $('#roleUserForm').submit();
        }
      //显示部门人员树
        function showDeptUserTreeWin(roleId) {
        	deptUserTree.cancelAllNodes();
        	//alert(roleId);
        	roleDataIdParam2 = roleId;
        	$('#userRoleId').val(roleId);
        	$.ajax({
            	url:'${ctx}/sysRole/sysRole!getUserIds?roleId=' + roleId,
                method:'POST',
                success:function(data) {
                	
                	if(null!=data){
                		checkByUserIds(data);
                	}
                }
            });
        	$('#roleUserSave').show();
        	$('#roleDataUserSave').hide();
            $('#deptUserTreeWin').window('open');
        }   
      
      	function showDataDeptUserTreeWin(roleDataId) {
      		roleDataIdParam2 = roleDataId;
      		deptUserTree.cancelAllNodes();
      		$.ajax({
      			url:'${ctx}/sysRoleData/sysRoleData!getUsersId?roleDataId=' + roleDataId,
      			method:'POST',
      			success:function(data) {
      				if (null != data) {
      					checkByUserIds(data);
      				}
      			}
      		});
      		$('#deptUserTreeWin').window('open');
      		$('#roleDataUserSave').show();
      		$('#roleUserSave').hide();
      	}
      	
      	function showDataDeptTreeWin(roleDataId) {
        	tree.cancelAllNodes();
        	roleDataIdParam2 = roleDataId;
        	$.ajax({
            	url:'${ctx}/sysRoleData/sysRoleData!getDeptIds?roleDataId=' + roleDataId,
                method:'POST',
                success:function(data) {
                	if(data!=null)
                		checkByRoleIds(data);
                }
            });
            $('#deptTreeWin').window('open');
            $('#roleDataDeptSave').show();
            $('#roleDeptSave').hide();
        }     
        function checkByUserIds(userStr){
        	var userIds =  userStr.split(";");
        	for(var i=0;i<userIds.length;i++){
        		if(null!=userIds[i]){
        			//alert(userIds[i]);
	        		var zTree = deptUserTree.getZTreeObj();
	        		var obj = zTree.getNodeByParam("id", userIds[i], null);
	        		if(obj!=null)
	        		zTree.checkNode(obj, true, true);
        			//deptUserTree.checkNodeById(userIds[i]);
        		}
        	}
        }
       //显示用户组授权窗口
        function showRoleGroupWin(roleId) {
        	$.ajax({
            	url:'${ctx}/sysRole/sysRole!getGroups',
                method:'POST',
                success:function(data) {
                	var roleGroupStr = "<table>";
                	var groups = data.split(";");
                	for(var i=0;i<groups.length;i++){ 
                		if(groups[i]!=null&&groups[i]!=''){
                			var group = groups[i].split(',');
                			roleGroupStr = roleGroupStr+"<tr><td><input type='checkbox' id='"+group[0]+"' name='c' value='"+group[0]+"'>"+group[1]+"</td></tr>";
                		}
                	}
                	roleGroupStr = roleGroupStr + "</table><br><input type='button' value='保存' onclick='saveRoleGroup();'>";
                	$("#roleGroupList").html(roleGroupStr);
                }
            });
        	$('#groupRoleId').val(roleId);
        	$.ajax({
            	url:'${ctx}/sysRole/sysRole!getGroupIds?roleId=' + roleId,
                method:'POST',
                success:function(data) {
                	if(null!=data&&data!=''){
                		var datas = data.split(";");
                    	for(var j=0;j<datas.length;j++){
                    		if(null!=datas[j]&&datas[j]!=''){
                    			var groupId = datas[j];
                        		document.getElementById(groupId).checked = true;
                    		}
                    	}
                	}
                }
            });
            $('#roleGroupWin').window('open');
        }  
       //保存用户组信息
        function saveRoleGroup(){
        	var checkbox=document.getElementsByName("c"); 
        	var groups = [];
            for(var i=0;i<checkbox.length;i++){
                if(checkbox[i].checked){
                    groups.push(checkbox[i].value);
               }
            }  
        	var groupsStr = groups.join(";");
         	$('#groupsStr').val(groupsStr);
         	submitRoleGroup();
        }
        function submitRoleGroup() {
            $('#roleGroupForm').form({
            	url:'${ctx}/sysRole/sysRole!updateRoleGroup.action',
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
            $('#roleGroupForm').submit();
        }
		//查询
		function clearSearchForm() {
			$('#searchRoleName').val('');
		}
		function searchFunction(){
			$('#searchWin').window('open');
			//clearSearchForm();
		}
		function searchForm(){
			$('#searchForm').form({
				url:'${ctx}/sysRole/sysRole!pageList.action',
				success: function(data) {
					var data = eval('( ' + data + ' )');
					closeAllWin();
					grid.reloadGridByData(data);
				}
			});
			$('#searchForm').submit();
            $('#searchWin').window('close');
		}
		function clearSearchForm(){
			$('#searchRoleName').val('');
			searchForm();
		}
		//添加操作栏按钮
		function gridFormatter(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var url = ""
        	<shiro:hasPermission name="54:view">
        	+"<input type='button' onclick=\"viewFunction('"+rowId+"');\" class='btn1' value='查看' rowId='"+rowId+"'/>&nbsp;&nbsp;"
        	</shiro:hasPermission>
        	<shiro:hasPermission name="54:update">
        	+"<input type='button' onclick=\"editFunction('"+rowId+"');\"  class='btn1' value='修改'  rowId='"+rowId+"'/>&nbsp;&nbsp;"
        	</shiro:hasPermission>
        	<shiro:hasPermission name="54:custom1">
        	+"<input type='button' onclick=\"showMenuOptTreeWin('"+rowId+"');\" class='btn1'  value='设置菜单权限'  rowId='"+rowId+"'/>&nbsp;&nbsp;"
        	</shiro:hasPermission>
        	/* <shiro:hasPermission name="54:custom5">
        	+"<input type='button' onclick=\"showRoleDataWin('"+rowId+"');\" class='btn1'  value='设置数据权限'  rowId='"+rowId+"'/>&nbsp;&nbsp;"
        	</shiro:hasPermission> */
        	<shiro:hasPermission name="54:custom2">
        	+"<input type='button' onclick=\"showDeptUserTreeWin('"+rowId+"')\"  class='btn1' value='用户授权'  rowId='"+rowId+"'/>&nbsp;&nbsp;"
        	</shiro:hasPermission>
        	//<shiro:hasPermission name="54:custom3">
        	//+"<input type='button' onclick='showRoleGroupWin(this)' value='用户组授权'  rowId='"+rowId+"'/>&nbsp;&nbsp;"
        	//</shiro:hasPermission>		
        	//<shiro:hasPermission name="54:custom4">
        	//+"<input type='button' onclick=\"showDeptTreeWin('"+rowId+"');\" class='btn1'  value='部门授权'  rowId='"+rowId+"'/>";
        	//</shiro:hasPermission>
        	return url;
        }
		
		function roleDataGridFormatter(value, rowData, rowIndex) {
			var rowId = rowData.id;
			var url = "<input type='button' onclick=\"showDataDeptUserTreeWin('" + rowId + "');\" class='btn1' value='用户授权' rowId='" + rowId +"'/>&nbsp;&nbsp;"
					+ "<input type='button' onclick=\"showDataDeptTreeWin('" + rowId + "');\" class='btn1' value='部门授权' rowId='" + rowId +"'/>&nbsp;&nbsp;";
			return url;
		}
		
		function showAddTableWin() {
			$('#data_tree').treegrid({
				iconCls:'icon-save',
				striped : true,
        		border : true,
				height:350,
				width:"100%",
				nowrap: false, 
				rownumbers: true,
				animate:false,
				collapsible:true,
				singleSelect:false,
        		loadMsg:"正在努力拉取数据中...",
				url:'${ctx}/entity/entityAction!treeGrid.action',
				idField:'id',
				treeField:'entityCode',
				frozenColumns:[[
	                {title:'表名称',field:'entityCode',width:400}
				]],
				columns:[[
					{field:'entityName',title:'表含义',width:150,rowspan:2,align:'center'}
				]],
				toolbar: [{
      				text: '选中表',
      				iconCls: 'icon-add',
      				handler: selectTheTable
      			}],
				onContextMenu: function(e,row){
					e.preventDefault();
					var checks = document.getElementsByName("entityId");
					for(var i=0;i<checks.length;i++){						
						var ck = checks[i];
						if(ck.checked){
							ck.checked=false;
						}
						if(ck.id=="checkbox_"+row.id){
							ck.checked=true;
						}
					}
					$('#mm').menu('show', {
						left: e.pageX,
						top: e.pageY
					});
				}
			});
			$('#selectTableWin').window('open');
		}
		
		function deleteTable() {
			
		}
		
		function selectTheTable() {
			var arrar = $('#data_tree').treegrid('getSelections');
			var selectTablesId = new Array();
			for (var i=0; i<arrar.length; i++) {
				selectTablesId.push(arrar[i].entityCode);
			}
			$.ajax({
				url: '${ctx}/sysRoleData/sysRoleData!addTables.action',
				data: {'tablesId': selectTablesId.toString(), 'roleId':  roleIdParam},
				type: 'POST',
				async: true,
				success: function(dataObj) {
					var dataObj = eval('(' + dataObj + ')');
					if (dataObj.operateSuccess) {
						$.messager.alert("提示", dataObj.operateMessage);
						$('#selectTableWin').window('close');
						$('#tree_data').treegrid('reload');
					} else {
						$.messager.alert('警告', dataObj.operateMessage);
					}
				}
				
			});
		}

		
		function doSearchUser(id, name, type){
			
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
	<div id="toolbar">
		<form id="searchForm" method="post" style="display: inline;">
			<input type="hidden" value="1" name="sysRole.roleType" />
			&nbsp;&nbsp;角色名称:&nbsp;&nbsp;<input class="easyui-validatebox"
				id="searchRoleName" name="sysRole.roleName" onblur="searchForm();" onkeyup="searchForm();" /> 
<!-- 				&nbsp;&nbsp;<a -->
<!-- 				href="javascript:void(0)" class="easyui-linkbutton" -->
<!-- 				iconCls="icon-search" onclick="searchForm();">查询</a> &nbsp;&nbsp;<a -->
<!-- 				href="javascript:void(0);" class="easyui-linkbutton" -->
<!-- 				iconCls="icon-clear" onclick="clearSearchForm();">清空</a> -->
		</form>
	<div class="search_add">
		<shiro:hasPermission name="54:add">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-add', plain:true"
				onclick="addFunction();">添加</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="54:delete">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove', plain:true"
				onclick="removeFunction();">删除</a>
		</shiro:hasPermission>
		&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', plain:true" onclick="clearSearchForm();">清空</a>
	</div>
	</div>
	<div style="height: 72%">

		<table id="data_list" title = "角色权限管理" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true" >
			<thead>
				<tr>
					<th field="roleName" width="30%" title="角色名称" align="center">角色名称</th>
					<!--  <th field="description" width="30%" title="角色描述">角色描述</th>-->
					<th field="id" width="70%" title="操作" formatter="gridFormatter"
						align="center">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 添加窗口 -->
	<div id="addWin" iconCls="icon-save" title="添加角色信息">
		<form style="padding: 10px 20px 10px 20px;" id="addForm" method="post">
			<input type="hidden" name="sysRole.roleType" id="roleType" />
			<table cellpadding="5px" style="font-size: 12px;" cellspacing="1"
				border="0" bgcolor="#aed0ea">
				<tr>
					<td class="table_nemu">角色名称:</td>
					<td class="table_con"><input class="easyui-validatebox"
						id="addRoleName" name="sysRole.roleName"
						data-options="required: true,validType:'length[0,25]',invalidMessage:'输入的内容不能超过25个字符'"
						missingMessage="请输入角色名称" /></td>
				</tr>
				<tr>
					<td class="table_nemu">角色描述</td>
					<td class="table_con"><input class="easyui-validatebox"
						id="addDescription" name="sysRole.description"
						data-options="required: true,validType:'length[0,100]',invalidMessage:'输入的内容不能超过100个字符'"
						missingMessage="请输入角色描述" /></td>
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

	<!-- 更新窗口 -->
	<div id="updateWin" iconCls="icon-save" title="修改角色信息">
		<form style="padding: 10px 20px 10px 20px;" id="updateForm"
			method="post">
			<input type="hidden" id="id" name="id" />
			<table cellpadding="5px" style="font-size: 12px;" cellspacing="1"
				border="0" bgcolor="#aed0ea">
				<tr>
					<td class="table_nemu">角色名称:</td>
					<td class="table_con"><input class="easyui-validatebox"
						id="updateRoleName" name="sysRole.roleName"
						data-options="required: true,validType:'length[0,25]',invalidMessage:'输入的内容不能超过25个字符'"
						missingMessage="请输入角色名称" /></td>
				</tr>
				<tr>
					<td class="table_nemu">角色描述</td>
					<td class="table_con"><input class="easyui-validatebox"
						id="updateDescription" name="sysRole.description"
						ata-options="required: true,validType:'length[0,100]',invalidMessage:'输入的内容不能超过100个字符'"
						missingMessage="请输入角色描述" /></td>
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

	<!-- 查看窗口 -->
	<div id="viewWin" iconCls="icon-save" title="查看角色信息">
		<form style="padding: 10px 20px 10px 20px;">
			<table cellpadding="5px" style="font-size: 12px;" width="80%"
				align="center" cellspacing="1" border="0" bgcolor="#aed0ea">
				<tr>
					<td class="table_nemu">角色名称:</td>
					<td class="table_con"><label class="easyui-validatebox"
						id="viewRoleName" name="sysRole.roleName"></label></td>
				</tr>
				<tr>
					<td class="table_nemu">角色描述</td>
					<td class="table_con"><label class="easyui-validatebox"
						id="viewDescription" name="sysRole.description"></label></td>
				</tr>
			</table>
			<br />
			<div align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!-- 查询窗口 -->
	<!-- 
<div id="searchWin" iconCls="icon-save" title="角色信息查询"> 
    <form style="padding: 10px 20px 10px 20px;" id="searchForm">
    	<table cellpadding="5px" style="font-size:12px;" width="80%" align="center" cellspacing="1"  border="0" bgcolor="#aed0ea">
	   	<tr>
        <td class="table_nemu">角色名称:</td><td class="table_con"><input class="easyui-validatebox" id="searchRoleName" name="sysRole.roleName" /></td>
        </tr>
		</table>
		<br/>
        <div align="center">
            <a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="searchForm();return false;">查询</a>
            <a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="closeAllWin();return false;">取消</a>
        </div>
    </form>
</div>
 -->
	<!-- 菜单权限树展现 -->
	<div id="menuOptTreeWin" iconCls="icon-save" title="设置菜单权限">
		<table id="treeGridMenu" style="width:600px;height:400px"></table>
	</div>
	<form style="height: 0%; weight: 0%" id="roleMenuOptForm">
		<input type="hidden" id="menuOptsStr" name="menuOptsStr" /> <input
			type="hidden" id="menuOptRoleId" name="menuOptRoleId" />
	</form>

	<!-- 部门树展现 -->
	<div id="deptTreeWin" iconCls="icon-save" title="部门授权">
		<div id="p" class="easyui-panel" title="部门树" fit="true" tools="#tt"
			border="false" plain="true">
			<div id="tree_search" style="display: none">
				关键字:<input type="text" id="key" name="key" style="width: 80px;" /><a
					href="#" class="easyui-linkbutton" iconCls="icon-search"
					plain="true" onclick="tree.searchNode();">搜索</a>
			</div>
			<div id="dept_tree" class="ztree"></div>
			<br>
			<p align="center" id="roleDeptSave">
				<a href="#" class="easyui-linkbutton" icon="icon-ok" value="保存"
					onclick="saveRoleDept();">保存</a>
			</p>
			<p align="center" id="roleDataDeptSave">
				<a href="#" class="easyui-linkbutton" icon="icon-ok" value="保存"
					onclick="saveRoleDataDept();">保存</a>
			</p>
		</div>
	</div>
	<form style="height: 0%; weight: 0%" id="roleDeptForm">
		<input type="hidden" id="roleDeptsStr" name="roleDeptsStr" /> <input
			type="hidden" id="deptRoleId" name="deptRoleId" />
	</form>
	<!-- 部门人员树展现 -->
	<div id="deptUserTreeWin" iconCls="icon-save" title="用户授权">
		<div id="p" class="easyui-panel" title="部门人员树" fit="true" tools="#tt"
			border="false" plain="true">
			<div id="dept_user_tree_search" style="display: none">
				关键字:<input type="text" id="key" name="key" style="width: 80px;" /><a
					href="#" class="easyui-linkbutton" iconCls="icon-search"
					plain="true" onclick="tree.searchNode();">搜索</a>
			</div>
			<div id="dept_user_tree" class="ztree"></div>
			<br>
			<p align="center" id="roleUserSave">
				<a href="#" class="easyui-linkbutton" icon="icon-ok" value="保存"
					onclick="setPersons();">保存</a>
			</p>
			<p align="center" id="roleDataUserSave">
				<a href="#" class="easyui-linkbutton" icon="icon-ok" value="保存"
					onclick="saveRoleDataUser();">保存</a>
			</p>
		</div>
	</div>
	<form style="height: 0%; weight: 0%" id="roleUserForm">
		<input type="hidden" id="usersStr" name="usersStr" /> <input
			type="hidden" id="userRoleId" name="userRoleId" />
	</form>
	<!-- 用户组授权窗口 -->
	<div id="roleGroupWin" iconCls="icon-save" title="用户组授权">
		<form id="roleGroupForm" style="padding: 10px 20px 10px 10px;">
			<div id="roleGroupList" algin="left"></div>
			<input type="hidden" id="groupsStr" name="groupsStr" /> <input
				type="hidden" id="groupRoleId" name="groupRoleId" />
		</form>
	</div>

	<!-- 数据权限设置 -->
	<div id="roleDataWin" iconCls="icon-save" title="数据权限设置">
		<table id="role_data_list">
			<thead>
				<tr>
					<th field="tableName" width="30%" title="表名" align="center">表名</th>
					<!--  <th field="description" width="30%" title="角色描述">角色描述</th>-->
					<th field="id" width="70%" title="操作"
						formatter="roleDataGridFormatter" align="center">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<div id="selectTableWin">
		<table id="data_tree"></table>
	</div>
</body>
</html>