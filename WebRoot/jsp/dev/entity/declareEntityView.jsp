<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定义视图</title>

<script type="text/javascript" src="${ctx}/js/GridUtilNoToolBar.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/validator.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

        // 当点击添加按钮时做的操作
        function addFunction() {
            $('#addWin').window('open');
            clearAddForm();
        }

        // 提交添加菜单请求
        function addSubmitForm() {
        	if($("#addEntityName").validatebox("isValid")&&$("#addViewSql").validatebox("isValid")&&$("#addEntityCode").validatebox("isValid")){
        		$('#addForm').form({
                    url:'${ctx}/entity/entityAction!addEntity.action',
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
            
        }

        function updateSubmitForm() {
            $('#updateForm').form({
                url:'${ctx}/entity/entityAction!updateEntity.action',
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
        
        function searchSubmitForm(){
        	/* //提交查询表单
       	    var entityCode = $("#searchEntityCode").val();
       	    var entityName = $("#searchEntityName").val();
       	    grid = new Grid('视图列表', 'icon-edit',
                       '${ctx}/entity/entityAction!pageList.action?entityType=1&entityCode='+entityCode+'&entityName='+entityName,
                       'data_list', addFunction, editFunction,
                       viewFunction, removeFunction,searchFunction);
            grid.loadGrid(); */
            $('#searchForm').form({
            	url:'${ctx}/entity/entityAction!pageList.action',
            	success: function(data) {
            		var data = eval('(' + data + ')');
            		closeAllWin();
            		grid.reloadGridByData(data);
            	}
            });
            $('#searchForm').submit();
       	    $('#searchWin').window('close');
        }

        // 清除添加表单中的数据
        function clearAddForm() {
            $('#addEntityCode').val('');
            $('#addEntityName').val('');
            $('#addViewSql').val(' ');
        }

        // 添加更新表单中的数据
        function clearUpdateForm() {
            $('#updateId').val('');
            $('#updateAccount').val('');
            $('#updatePassword').val('');
            $('#updateUserName').val('');
            $('#updateDeptId').val('');
        }

        function clearViewForm() {
            $('#viewAccount').val('');
            $('#viewPassword').val('');
            $('#viewUserName').val('');
            $('#viewDeptName').val('');
        }

        // 填充更新表单中的数据
        function fillUpdateForm() {
            var rows = grid.getSelectNode();
            $.ajax({
                url:'${ctx}/entity/entityAction!getEntityById.action?id=' + rows.id,
                method:'POST',
                success:function (data) {
                    $('#updateEntityId').val(data.id);
                    $('#updateEntityCode').val(data.entityCode);
                    $('#updateEntityName').val(data.entityName);
                }
            });
        }

        // 填充查看菜单的内容
        function fillViewForm(row) {
        	$('#viewEntityId').val(row.id);
            $('#viewEntityCode').val(row.entityCode);
            $('#viewEntityName').val(row.entityName);
           	$("#viewSql").val(row.viewSql);
        }

        function editFunction() {
            var rows = grid.getSelectNodes();
            if (rows.length == 0) {
                $.messager.show({
                    title:'警告',
                    msg:'请选择一列数据进行修改操作!',
                    timeout:2000,
                    showType:'slide'
                });
                return;
            }
            if (rows.length > 1) {
                $.messager.show({
                    title:'警告',
                    msg:'每次更改数据只能选择一条数据!',
                    timeout:2000,
                    showType:'slide'
                });

                return;
            }
            $('#updateWin').window('open');
            clearUpdateForm();
            fillUpdateForm();
        }


        function delFunction() {
            var rows = grid.getSelectNodes();
            var notDeleted = new Array();
            if (rows.length == 0) {
                $.messager.show({
                    title:'提示',
                    msg:'请选择要删除的行',
                    timeout:2000,
                    showType:'slide'
                });
                return;
            } else {
                $.messager.confirm('警告', '是否要删除该记录?', function (r) {
                    if (r) {
                    	var ids="";
                        var i = 0;
                        for (i=0; i<rows.length; i++) {
                           
//                             if(i!=0){
//                             	 ids+="&ids="+rows[i].id;
//                             }else{
//                             	ids+="ids="+rows[i].id;
//                             }
                        	if(rows[i].formCount==0){
                         	   if(ids!=""){
                               	 ids+="&ids="+rows[i].id;
                               }else{
                               	ids+="ids="+rows[i].id;
                               } 
                            }else{
                         	   notDeleted.push(rows[i]);
                            }  
                        }
                        if(ids==""){
                        	if(notDeleted.length){
                        		var messages = "";
                        		if(notDeleted.length){
                            		for(var aa in notDeleted){
                            			messages +=notDeleted[aa].entityCode+",";
                            		}
                            		messages+="表无法删除，已有表单关联！";
                            	}
                        		$.messager.alert("警告", messages);
                        	}
                        	return;
                        }
                        $.ajax({
                            url:'${ctx}/entity/entityAction!deleteEntity?' + ids+"&entityType=1",
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


        function viewFunction(obj) {
        	var index = obj.rowId;
            var row = $("#data_list").datagrid("getRows")[index];
            
            $('#viewWin').window('open');
            clearViewForm();
            fillViewForm(row);
        }

        function showDeptTreeWin() {
            $('#deptTreeWin').window('open');
        }
        $(document).ready(
                function () {
                	$("#addEntityCode").blur(function(e){
                		$("#codeLabel").text("");
                		$("#codeLabel").text($(this).attr("value"));
                	});
                	$('#fieldListWin').window({
                        width:920,
                        height:500,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#addWin').window({
                        width:450,
                        height:400,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#updateWin').window({
                        width:450,
                        height:400,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#viewWin').window({
                        width:450,
                        height:400,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#deptTreeWin').window({
                        width:450,
                        height:485,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#searchWin').window({
                        width:450,
                        height:400,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#addWin').window('close');
                    $('#updateWin').window('close');
                    $('#viewWin').window('close');
                    $('#deptTreeWin').window('close');
                    $('#searchWin').window("close");
                    $("#fieldListWin").window("close");
                    grid = new Grid('实体表列表', 'icon-edit',
                            '${ctx}/entity/entityAction!pageList.action?entityType=1',
                            'data_list', addFunction, editFunction,
                            viewFunction, removeFunction, searchFunction);
                    grid.loadGrid();
                });
        
        //搜索框
        function searchFunction(){
        	$("#searchWin").window("open");
        }
        
      

        function closeAllWin() {
            $('#addWin').window('close');
            $('#updateWin').window('close');
            $('#viewWin').window('close');
            $('#searchWin').window('close');
            $("#fieldListWin").window("close");
        }
        
        function updFunction(obj) {
            $('#updateWin').window('open');
            clearUpdateForm();
            $.ajax({
                url:'${ctx}/entity/entityAction!getEntityById.action?id=' + obj.rowId,
                method:'POST',
                success:function (data) {
                    $('#updateEntityId').val(data.id);
                    $('#updateEntityCode').val(data.entityCode);
                    $('#updateEntityName').val(data.entityName);
                }
            });
        }
        function removeFunction(obj){
        	$.messager.confirm('警告', '是否要删除该记录?', function (r) {
                if (r) {
                	var ids="ids="+obj.rowId;
                    
                    $.ajax({
                        url:'${ctx}/entity/entityAction!deleteEntity?' + ids,
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
       	function setFunction(obj){
       		var rowId=$(obj).attr("rowId");
       		var rowName=$(obj).attr("rowName");
       		window.location.href="declareEntityField.jsp?entityId="+rowId+"&entityCode="+rowName;
       	}
        function gridFormatter(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var rowName = rowData.entityCode;
        	var url = ""
        	<shiro:hasPermission name="44:view">
        	+"<input type='button' onclick='viewFunction(this);' value='查看'  rowId='"+rowIndex+"' rowName='"+rowName+"'/>"
        	</shiro:hasPermission>
        	<shiro:hasPermission name="44:custom1">
        	+"<input type='button' onclick='viewField(this);' value='查看属性'  rowId='"+rowId+"' rowName='"+rowName+"'/>";
        	</shiro:hasPermission>
        	return url;
        }
        
        /** 获得字段列表*/
        function viewField(obj){
        	var entityId=$(obj).attr("rowId");
        	$("#field_list").datagrid({
        		title:'视图字段',
   	        	iconCls:'icon-edit',
        		width:900,
				height:450,
				nowrap: true,
				autoRowHeight: false,
				striped: true,
				collapsible:true,
				url:"${ctx}/entity/entityFieldAction!pageList.action?entityId="+entityId,
				singleSelect:true,
				rownumbers:false,
				sortName:"entityOrder",
				sortOrder:"asc"
        	});
        	$("#fieldListWin").window("open");
        }
        
        /** 渲染isNull列*/
        function showIsNull(val, row, index){
        	if(val=='1'){
        		return "是";
        	}else{
        		return "否";
        	}
        }
</script>
</head>
<body>

	<div class="panel-header" style="width: 100%;">
		<div class="panel-title panel-with-icon">视图列表</div>
		<div class="panel-icon icon-edit"></div>
		<div class="panel-tool"></div>
	</div>
	<div class="datagrid-toolbar">
		<shiro:hasPermission name="44:add">
			<a href="javascript:addFunction()" style="float: left;"
				class="l-btn l-btn-plain"> <span class="l-btn-left"> <span
					class="l-btn-text icon-add" style="padding-left: 20px;">添加</span>
			</span>
			</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="44:delete">
			<a href="javascript:delFunction()" style="float: left;"
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
					<th field="entityCode" width="150" title="实体名称" align="center">视图名称</th>
					<th field="entityName" width="150" title="实体含义" align="center">视图含义</th>
					<th field="id" width="120" title="实体id" align="center"
						formatter="gridFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 添加窗口 -->
	<div id="addWin" iconCls="icon-save" title="添加视图">

		<form style="padding: 10px 20px 10px 85px;" id="addForm" method="post">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>视图名</td>
					<td><input class="easyui-validatebox" id="addEntityCode"
						name="entity.entityCode"
						data-options="required:true, validType:'codechick'" /></td>

				</tr>
				<tr>
					<td>视图含义</td>
					<td><input class="easyui-validatebox" id="addEntityName"
						name="entity.entityName"
						data-options="required:true, validType:'length[1,255]'" /></td>
				</tr>
				<tr>
					<td>创建视图语句</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<div>
							create view [<label id="codeLabel">视图名</label>] as
						</div> <textarea rows="10" cols="33" class="easyui-validatebox"
							id="addViewSql" name="entity.viewSql"
							data-options="required:true, validType:'minLength[20]'"></textarea>
					</td>
				</tr>
			</table>
			<div style="padding: 0px;">
				<input type="hidden" name="entity.entityType" value="1" /> <a
					href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="addSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>

	</div>

	<!-- 更新窗口 -->
	<div id="updateWin" iconCls="icon-save" title="">
		<form style="padding: 10px 20px 10px 85px;" id="updateForm"
			method="post">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>实体名</td>
					<td><input class="easyui-validatebox" id="updateEntityCode"
						name="entity.entityCode" /></td>
				</tr>
				<tr>
					<td>实体含义</td>
					<td><input class="easyui-validatebox" id="updateEntityName"
						name="entity.entityName" /></td>
				</tr>
			</table>
			<input type="hidden" name="entity.id" id="updateEntityId" />
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="updateSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!-- 查看窗口 -->
	<div id="viewWin" iconCls="icon-save" title="查看视图">
		<form style="padding: 10px 20px 10px 85px;">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>视图名</td>
					<td><input class="easyui-validatebox" id="viewEntityCode"
						name="entity.entityCode" /></td>
				</tr>
				<tr>
					<td>视图含义</td>
					<td><input class="easyui-validatebox" id="viewEntityName"
						name="entity.entityName" /></td>
				</tr>
				<tr>
					<td>创建视图语句</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2"><textarea readonly="readonly" rows="10"
							cols="30" class="easyui-validatebox" id="viewSql"
							name="entity.viewSql"></textarea></td>
				</tr>
			</table>
			<input type="hidden" name="entity.id" id="viewEntityId" />


			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!-- 字段列表窗口 -->
	<div id="fieldListWin" iconCls="icon-search" title="视图字段信息">
		<table id="field_list" loadMsg="正在努力拉取数据中...">
			<thead>
				<tr>
					<th field="entityOrder" width="120" title="实体名称" align="center">序号</th>
					<th field="fieldCode" width="120" title="实体名称" align="center">字段名称</th>
					<th field="fieldName" width="150" title="实体含义" align="center">字段含义</th>
					<th field="fieldType" width="120" title="实体类型" align="center">字段类型</th>
					<th field="fieldLength" width="120" title="实体类型" align="center">字段长度</th>
					<th field="defaultValue" width="120" title="实体类型" align="center">缺省值</th>
					<th field="isNull" width="150" title="实体类型" align="center"
						formatter="showIsNull">可否为空</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 搜索窗口 -->
	<div id="searchWin" iconCls="icon-search" title="搜索视图">
		<form style="padding: 10px 20px 10px 85px;" id="searchForm">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>视&nbsp;&nbsp;图&nbsp;&nbsp;名:</td>
					<td><input class="easyui-validatebox" id="searchEntityCode"
						name="entity.entityCode" /></td>
				</tr>
				<tr>
					<td>视图含义:</td>
					<td><input class="easyui-validatebox" id="searchEntityName"
						name="entity.entityName" /></td>
				</tr>
			</table>
			<input type="hidden" name="entity.id" id="viewEntityId" />


			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-search"
					onclick="searchSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

</body>
</html>