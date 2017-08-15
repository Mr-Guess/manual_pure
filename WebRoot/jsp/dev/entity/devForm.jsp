<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/GridUtilNoToolBar.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/validator.js"></script>
<title>定义表单</title>
<script type="text/javascript">
	var grid = null;
	var dl = $('#data_list');
	var initType=[{val:0,text:'列表'},{val:1,text:'详情'}];
	
	$(document).ready(function(){
		$("#updateWin").window({
			width:382,
            height:400,
            modal:true,
            onOpen:function(){
            	$(".window-mask").css("height","100%");
            }
		});
		$("#addWin").window({
			width:382,
            height:400,
            modal:true,
            onOpen:function(){
            	$(".window-mask").css("height","100%");
            }
		});
		$("#menuWin").window({
			width:382,
			height:285,
			modal:true,
            onOpen:function(){
            	$(".window-mask").css("height","100%");
            }
		});
		$("#searchWin").window({
			width:382,
			height:222,
			modal:true,
            onOpen:function(){
            	$(".window-mask").css("height","100%");
            }
		});
				
		$("#updateFormName").validatebox({
			required:true,
			validType:"alphanum"
		});
		$("#updateInitType").combobox({
			data:initType,
			valueField:'val',
			textField:'text',
			required:true,
			editable:false
		});
		$("#updateEntityId").combobox({
			url:'${ctx}/entity/entityAction!getEntityJson.action',
			valueField:'id',
			textField:'entityCode',
			required:true,
			editable:false
		});
		$("#addFormName").validatebox({
			required:true,
			validType:"alphanum"
		});
		$("#addInitType").combobox({
			data:initType,
			valueField:'val',
			textField:'text',
			required:true,		
			editable:false
		});
		$("#addEntityId").combobox({
			url:'${ctx}/entity/entityAction!getEntityJson.action?',
			valueField:'id',
			textField:'entityCode',
			required:true,
			editable:false
		});
		$("#menuSjbh").combotree({
			url:"${ctx}/form/formAction!getMenuCombotree.action",
			required: true
		});
		closeAllWin();
		grid = new Grid('定义表单', 'icon-edit',
				'${ctx}/form/formAction!pageList.action',
				'data_list', addFunction, null,
                null, null, null);
		grid.loadGrid();
		
	});
	
	function delFunction(){
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
            $.messager.confirm('警告', '是否要删除该表单?', function (r) {
                if (r) {
                	var ids="";
                    var i = 0;
                    for (i=0; i<rows.length; i++) {
                       
                        if(i!=0){
                        	 ids+="&ids="+rows[i].id;
                        }else{
                        	ids+="ids="+rows[i].id;
                        }
                    }
                    $.ajax({
                        url:'${ctx}/form/formAction!deleteForm.action?' + ids,
                        method:'POST',
                        success:function(data) {
                        	data = eval("("+data+")");
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
	
	/**关闭所有的窗口*/
	function closeAllWin(){
		$("#addWin").window('close');
		$("#updateWin").window('close');
		$("#menuWin").window('close');
		$('#searchWin').window('close');
	}
	
	/**打开添加表单*/
	function addFunction(){
		clearWin();
		$("#addWin").window('open');
	}
	
	function clearWin(){
		$("#addFormName").val("");
		$("#addEntityId").val("");
		$("#addInitType").val("");
		$("#addEntityId").combobox("setValue", "");
		$("#addInitType").combobox("setValue", "");
	}
	
	/**打开修改表单*/
	function updateFunction(obj){
		setFunction(obj);
		$("#updateWin").window("open");
	}
	
	/**提交更新表单*/
	function updateSubmitForm() {
        $('#updateForm').form({
            url:'${ctx}/form/formAction!updateForm.action',
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
	
	/**提交添加表单*/
	function addSubmitForm(){
		$('#addForm').form({
            url:'${ctx}/form/formAction!addForm.action',
            success:function (data) {
            	data = eval("("+data+")");
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
	/**修改按钮*/
	function setFunction(obj){
		var rowId = $(obj).attr("rowId");	
		var row = $("#data_list").datagrid('getRows')[rowId];
		document.getElementById("updateFormName").value = row.formName;
		$("#updateEntityId").combobox("setValue",row.entityId);
		$("#updateInitType").combobox("setValue",row.initType);
		document.getElementById("updateFormId").value = row.id;
	}
	
	/** 设置表单属性*/
	function setProperty(obj){
		var rowId = $(obj).attr("rowId");
		var index = $(obj).attr("rowIndex");
		var formName = $("#data_list").datagrid("getRows")[index].formName;
		var formType = $(obj).attr("formType");
		$.ajax({
			url:"${ctx}/form/formAction!getFormProperty.action",
			type:"post",
			data:"devForm.id="+rowId+"&devForm.initType="+formType,
			success:function(ret){
				var form = $("<form>").attr("action","devFormProperty.jsp").attr("method", "post").appendTo($("body"));
				form.append($("<input name='formId' />").attr("value", rowId));
				form.append($("<input name='formType' />").attr("value", formType));
				form.append($("<input name='formName' />").attr("value", formName));
				form.submit();
// 				document.location.href="devFormProperty.jsp?formId="+rowId+"&formType="+formType+"&formName="+formName;
			}
		});
		
	}
	
	/**生成操作按钮*/
	function operateFunction(value, rowData, rowIndex){
		var rowId = rowData.id;
		var formType = rowData.initType;
    	var rowName = rowData.entityCode;
    	var url = ""
    	<shiro:hasPermission name="57:update">
    	+"<input type='button' onclick='updateFunction(this);'value='修改'    rowId='"+rowIndex+"' rowName='"+rowName+"'/>&nbsp;&nbsp;"
    	</shiro:hasPermission>
    	<shiro:hasPermission name="57:custom1">
    	+"<input type='button' onclick='setProperty(this);' value='设置属性' formType='"+formType+"' rowIndex='"+rowIndex+"'  rowId='"+rowId+"' rowName='"+rowName+"'/>&nbsp;&nbsp;"
    	</shiro:hasPermission>
    	<shiro:hasPermission name="57:custom2">
    	+"<input type='button' onclick='setMenu(this);' value='生成菜单'  rowId='"+rowId+"' rowName='"+rowName+"'/>&nbsp;&nbsp;"
    	</shiro:hasPermission>
    	;
    	return url;
	}
	
	/** 设置菜单*/
	function setMenu(obj){
		var id = $(obj).attr("rowId");
		$.ajax({
			url:"${ctx}/form/formAction!getMenuByForm.action",
			data:"devForm.id="+id,
			dataType:"json",
			success:function(ret){
				fillMenu(ret,id);
			}
		});
		$("#menuWin").window('open');
		
	}
	
	/** 充填菜单设置*/
	function fillMenu(o,id){
		clearMenu();
		if(Boolean(o)){
			$("#imgDiv").show();
			$("#menuName").val(o.menuName).attr("readonly","readonly");
			$('#menuSjbh').combotree('setValue', o.menuSjbh);
			$("#menuSjbh").combotree("disable");
			$("#imgOne").val(o.menuIcon).attr("readonly","readonly");
			$("#imgShow").attr("src","${ctx}/"+o.menuIcon);
			$("#menuUrl").attr("value",o.menuUrl);
			$("#menuId").val(o.menuId);
			$("#selectImgButton").hide();
			$("#menuCancel").hide();
			$("#menuSubmit").hide();
		}
		$("#menuUrl").attr("readonly","readonly");
		$("#_formId").val(id);
	}
	
	/** 清除菜单数据*/
	function clearMenu(){
		$("#imgDiv").hide();
		$("#selectImgButton").show();
		$("#menuCancel").show();
		$("#menuSubmit").show();
		$("#menuName").val("");
		$('#menuSjbh').combotree('setValue', "");
		$("#imgOne").val("");
		$("#imgShow").attr("src", null);
		$("#menuId").val("");
		$("#menuSjbh").combotree("enable");
		$("#menuUrl").val("");
	}
	
	/** 渲染显示*/
	function initTypeView(value, rowData, rowIndex){
		for(var s in initType){
			if(initType[s].val==value){
				return initType[s].text;
			}
		}
		
	}
	
	/** 搜索方法*/
	function searchFunction(){
		$("#searchWin").window("open");
	}
	
	/** 选择图标*/
	function selectImages(){
		window.open('../../../icon/iconAction!list.action?operflag=menuSelect&time='+new Date(),'_blank','top=0,left=0,width=710,height=500');
	}
	
	/** 提交查询表单*/
    function searchSubmitForm(){
    	//提交查询表单
   	    var formName = $("#searchFormName").val();
   	    var entityCode = $("#searchEntityCode").val();
   	    $.post(
   	    	'${ctx}/form/formAction!pageList.action',
   	    	{"entityCode":entityCode, "formName":formName},
   	    	function(ret){
		        $("#data_list").datagrid("loadData",ret);
   	    	}
   	    );
   	    $('#searchWin').window('close');
    }
	
	function submitMenu(){
		var src = $("#imgOne").attr("src");
		$("#imgOne").attr("src", src);
		$('#menuForm').form({
            url:'${ctx}/form/formAction!setMenu.action',
            success:function (data) {
            	data = eval("("+data+")");
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
        $('#menuForm').submit();
	}
</script>
</head>
<body>
	<div class="panel-header" style="width: 100%;">
		<div class="panel-title panel-with-icon">表单列表</div>
		<div class="panel-icon icon-edit"></div>
		<div class="panel-tool"></div>
	</div>
	<div class="datagrid-toolbar">
		<shiro:hasPermission name="57:add">
			<a href="javascript:addFunction()" style="float: left;"
				class="l-btn l-btn-plain"> <span class="l-btn-left"> <span
					class="l-btn-text icon-add" style="padding-left: 20px;">添加</span>
			</span>
			</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="57:delete">
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
	<div style="height: 93%">
		<table id="data_list">
			<thead>
				<tr>
					<th field="formName" width="150" title="" align="center">表单名称</th>
					<th field="entityCode" width="150" title="" align="center">表或视图名称</th>
					<th field="initType" width="150" title="" align="center"
						formatter="initTypeView">初始化状态</th>
					<th field="id" width="450" title="" formatter="operateFunction">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 添加窗口 -->
	<div id="addWin" iconCls="icon-save" title="添加表单">

		<form style="padding: 10px 20px 10px 85px;" id="addForm" method="post">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>表单名称</td>
					<td><input id="addFormName" name="devForm.formName" /></td>
				</tr>
				<tr>
					<td>表或视图名称</td>
					<td><input id="addEntityId" name="devForm.entityId"
						style="width: 155px" /></td>
				</tr>
				<tr>
					<td>初始化状态</td>
					<td><input id="addInitType" name="devForm.initType"
						style="width: 155px" /></td>
				</tr>
			</table>
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="addSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!-- 搜索窗口 start-->
	<div id="searchWin" iconCls="icon-save" title="搜索表单">
		<form style="padding: 10px 20px 10px 85px;">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>表&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;名:</td>
					<td><input class="easyui-validatebox" id="searchFormName"
						name="formName" /></td>
				</tr>
				<tr>
					<td>表或试图名:</td>
					<td><input class="easyui-validatebox" id="searchEntityCode"
						name="entityCode" /></td>
				</tr>
			</table>
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-search"
					onclick="searchSubmitForm();">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin()">取消</a>
			</div>
		</form>
	</div>
	<!-- 搜索窗口 -->

	<!-- 更新窗口 -->
	<div id="updateWin" iconCls="icon-save" title="修改表单">
		<form style="padding: 10px 20px 10px 85px;" id="updateForm"
			method="post">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>表单名称</td>
					<td><input id="updateFormName" name="devForm.formName" /></td>
				</tr>
				<tr>
					<td>表或视图名称</td>
					<td><input id="updateEntityId" name="devForm.entityId"
						style="width: 155px" /></td>
				</tr>
				<tr>
					<td>初始化状态</td>
					<td><input id="updateInitType" name="devForm.initType"
						style="width: 155px" /></td>
				</tr>
			</table>
			<div style="padding: 0px;">
				<input id="updateFormId" type="hidden" class="easyui-validatebox"
					name="devForm.id" value="" /> <a href="#" class="easyui-linkbutton"
					icon="icon-ok" onclick="updateSubmitForm();return false;">确定</a> <a
					href="#" class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!-- 菜单树 -->
	<div id="menuWin" iconCls="icon-edit" title="生成菜单">
		<form id="menuForm" action="">
			<table cellpadding="5px" style="font-size: 12px">
				<tr>
					<td>菜单名称</td>
					<td><input id="menuName" name="menu.menuName" /></td>
				</tr>
				<tr>
					<td>上级菜单</td>
					<td><input id="menuSjbh" name="menu.menuSjbh"
						style="width: 155px" /></td>
				</tr>
				<tr>
					<td>菜单路径</td>
					<td><input id="menuUrl" name="menu.menuUrl" /></td>
				</tr>
				<tr>
					<td class="tdInfo1">显示图标：</td>
					<td class="tdInfo2"><div ID="imgDiv" style="display: none">
							<img border="0" ID="imgShow" width="62" height="51" />
						</div> <input type="hidden" ID="imgOne" name="menu.menuIcon" /><input
						id="selectImgButton" type="button" value="选择"
						onClick="selectImages();" /></td>
				</tr>
				<tr>
					<td><a id="menuSubmit" href="#" class="easyui-linkbutton"
						icon="icon-ok" onclick="submitMenu();">确定</a></td>
					<td><a id="menuCancel" href="#" class="easyui-linkbutton"
						icon="icon-cancel" onclick="closeAllWin();">取消</a></td>
				</tr>
			</table>
			<div style="padding: 0px;">
				<input id="menuId" type="hidden" name="menu.menuId" value="" /> <input
					id="_formId" type="hidden" name="devForm.id" value="" />
			</div>
		</form>
	</div>
</body>
</html>