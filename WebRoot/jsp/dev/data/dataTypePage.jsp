<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>元数据类型管理</title>
<!--把GridUtil的JS 引入进来 -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<!-- <script type="text/javascript" src="${ctx}/js/GridUtilNoToolBar.js"></script> -->
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript">
var grid = null;
var parameter = null;

//点击查询按钮时候的操作
function searchFunction() {
    $('#searchWin').window('open');
    //clearSearchForm();
}

//提交查询表单
function searchForm() {
	$('#searchForm').form({
		url:'${ctx}/dataType/dataTypeAction!pageList.action',
		success: function(data) {
			var data = eval('(' + data + ')');
			closeAllWin();
			grid.reloadGridByData(data);
		}
	});
	$('#searchForm').submit();
	$('#searchWin').window('close');
}

//点击添加按钮的时候的操作
function addFunction() {
    $('#addWin').window('open');
    clearAddForm();
}

//提交添加菜单请求
function addSubmitForm(){
    $('#addForm').form({
        url:"${ctx}/dataType/dataTypeAction!addDataType.action",
        method: 'POST',
        success: function(data){
            var data = eval('(' + data + ')');
            closeAllWin();
            if(data.operateSuccess){
                $.messager.show({
                    title: '成功',
                    msg:data.operateMessage,
                    timeout: 2000,
                    showType:'slide'
                });
                grid.reloadGrid();
            } else {
                $.messager.show({
                    title: '失败',
                    msg: data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
            }
        }
    });
    $('#addForm').submit();
}

//提交更新菜单请求
function updateSubmitForm() {
    $('#updateForm').form({
        url:"${ctx}/dataType/dataTypeAction!updateDataType.action",
        success: function (data) {
            closeAllWin();
            var data = eval('(' + data + ')');
            if(data.operateSuccess){
                $.messager.show({
                    title: '成功',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                grid.reloadGrid();
            } else {
                $.messager.show({
                    title: '失败',
                    msg: data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
            }
        }
    });
    $('#updateForm').submit();
}

//删除查询表单中的内容
function clearSearchForm() {
    $('#searchTypeName').val('');
}


//删除添加表单中的内容
function clearAddForm() {
    $('#addTypeName').val('');
    $('#addId').val('');
}

// 删除更新表单中的数据
function clearUpdateForm() {
    $('#updateTypeName').val('');
    $('#updateId').val('');
}


function clearViewForm() {
    $('#viewTypeName').val('');
    $('#viewId').val('');
}


//填充更新表单中的内容
function fillUpdateForm(id) {
    $.ajax({
        url:'${ctx}/dataType/dataTypeAction!getById.action?id=' + id,
        method:'POST',
        success: function(data){
            $('#updateId').text(data.id);
            $('#updateTypeName').val(data.typeName);
            $('#goBackId').val(data.id);
        }
    });
}

// 填充查看菜单的内容
function fillViewForm(id) {
    $.ajax({
        url:'${ctx}/dataType/dataTypeAction!getById.action?id=' + id,
        method:'POST',
        success:function (data) {
        	$('#viewId').text(data.id);
            $('#viewTypeName').text(data.typeName);
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
                for (i=0; i<rows.length; i++) {r
                	if(rows[i].typeName == '地区码'){
                		$.messager.alert('警告','地区码不可删除！','warning');
                		return;
                	}
                	if(rows[i].typeName == '行业'){
                		$.messager.alert('警告','行业不可删除！','warning');
                		return;
                	}
                	if(rows[i].typeName == '经济类型'){
                		$.messager.alert('警告','经济类型不可删除！','warning');
                		return;
                	}
                    ids.push(rows[i].id);
                }
                $.ajax({
                    url:'${ctx}/dataType/dataTypeAction!deleteByIds?ids=' + ids,
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
function settingFunction(rowId) {
    //parameter = rowId;
    //alert(parameter);
    window.location.href="${ctx}/jsp/dev/data/dataPage.jsp?idParam=" + rowId;
    

}

//view function
function viewFunction(rowId) {
	$('#viewWin').window('open');
	clearViewForm();
	fillViewForm(rowId);
}

$(document).ready(
        function () {
            $('#addWin').window({
                width:382,
                height:400,
                model:true
            });
            $('#updateWin').window({
                width:382,
                height:400,
                model:true
            });
            $('#viewWin').window({
                width:382,
                height:400,
                model:true
            });
            $('#searchWin').window({
                width:350,
                height:200,
                model:true
            });
            $('#settingWin').window({
                width:382,
                height:485,
                model:true
            });
            $('#addWin').window('close');
            $('#updateWin').window('close');
            $('#viewWin').window('close');
            $('#searchWin').window('close');
            $('#settingWin').window('close');
            grid = new Grid('${ctx }/dataType/dataTypeAction!pageList.action', 'data_list');
            grid.loadGrid();
        });

	function closeAllWin(){
	    $('#addWin').window('close');
	    $('#updateWin').window('close');
	    $('#viewWin').window('close');
	    $('#searchWin').window('close');
	    $('#settingWin').window('close');
	}	
	
function gridFormatter(value, rowData, rowIndex) {
	var rowId = rowData.id;
	var url = ""
	<shiro:hasPermission name="46:update">
	+"<input type='button' onclick='editFunction(\""+rowId+"\");' value='修改' class='btn1' />&nbsp;&nbsp;"
	</shiro:hasPermission>
	<shiro:hasPermission name="46:view">
	+"<input type='button' onclick='viewFunction(\""+rowId+"\");' value='查看' class='btn1' />&nbsp;&nbsp;"
	</shiro:hasPermission>
	<shiro:hasPermission name="46:custom1">
	+"<input type='button' onclick='settingFunction(\""+rowId+"\");' value='设置属性' class='btn1' />&nbsp";
	</shiro:hasPermission>
	return url;
}

</script>
</head>
<body>
	<div id='toolbar'>
		<shiro:hasPermission name="46:add">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="addFunction()">添加</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="46:delete">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="removeFunction()">删除 </a>
		</shiro:hasPermission>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="searchFunction()"> 查询 </a>
	</div>
	<div style="height: 80%">
		<table id="data_list" title='元数据'  width="100%" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true">
			<thead>
				<tr>
					<th field="typeName" width="50%" title="数据类型">元数据类型</th>
					<th field="id" width="50%" title="实体id" formatter="gridFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<!--添加窗口 -->
	<div id="addWin" iconCls="icon-save" title="增加元数据类型">
		<form style="padding: 10px 20px 10px 85px;" id="addForm">
			<p>
				数据类型:<input class="easyui-validatebox" id="addTypeName"
					name="dataType.typeName"
					data-options="required:true, validType:'length[1,255]'" />
			</p>
			<p>
				编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:<input
					class="easyui-validatebox" id="addId" name="dataType.id" />
			</p>
			<input type="hidden" name="dataType.status" value="1" />
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="addSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!--更新窗口-->
	<div id="updateWin" iconCls="icon-save" title="更新元数据">
		<form style="padding: 10px 20px 10px 85px;" id="updateForm">
			<p>
				数据类型:<input class="easyui-validatebox" id="updateTypeName"
					name="dataType.typeName"
					data-options="required:true, validType:'length[1,255]'" />
			</p>
			<p>
				编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:<label
					class="easyui-validatebox" id="updateId"></label>
			</p>
			<input type="hidden" id="goBackId" name="dataType.id">
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="updateSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!-- 查看窗口 -->

	<div id="viewWin" iconCls="icon-save" title="查看元数据">
		<form style="padding: 10px 20px 10px 85px">
			<p>
				数据类型:<label class="easyui-validatebox" id="viewTypeName"></label>
			</p>
			<p>
				编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:<label
					class="easyui-validatebox" id="viewId" /></label>
			</p>
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">关闭</a>
			</div>
		</form>
	</div>

	<!-- 查询窗口 -->
	<div id="searchWin" iconCls="icon-edit" title="搜索元数据类型">
		<form style="padding: 10px 20px 10px 50px" id="searchForm">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>数据类型:</td>
					<td><input class="easyui-validatebox" id="searchTypeName"
						name="dataType.typeName" data-options="validType:'length[1,255]'"></td>
				</tr>
			</table>

			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="searchForm();return false;">查询</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>
</body>
</body>
</html>