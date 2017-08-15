<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${formName}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/core.css">
<script type="text/javascript" src="${ctx}/js/CoreGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/validator.js"></script>
<style>
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

.labelTd {
	text-align: right;
	padding-right: 15px;
}
</style>
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		maxByteLength : {
			validator : function(value, param) {
				if (value) {
					var length = value.length; 
						for(var i = 0; i < value.length; i++){ 
							if(value.charCodeAt(i) > 127){ 
								length++; 
						} 
					}
					return length<=param;
				} else {
					return true;
				}
			},
			message : '输入内容的长度太长'
		}
	});
	
/* 	$(document).ready(function() {
		$("a.datebox-current").click(function() {
			alert(new Date());
		});
	}); */
	var menuId = '${menuId}';
	var grid = null;
	<shiro:hasPermission name="${menuId}:add">
	// 增加窗口
	function addFunction() {

		// 清除数据，暂时还没有找到好的方法，也可以使用jquery进行元素的遍历操作
		<c:forEach var="dto" items="${listDTO}" varStatus="status">
			<c:if test="${dto.controlType eq 'onetext' 
				|| dto.controlType eq 'telephone' || dto.controlType eq 'multitext'}">
				$('#add${dto.fieldCode}').val('');
			</c:if>
			<c:if test="${dto.controlType eq 'numbertext'}">
				$('#add${dto.fieldCode}').numberbox('clear');
			</c:if>
			<c:if test="${dto.controlType eq 'datetext'}">
				$('#add${dto.fieldCode}').datebox('setValue', '');
			</c:if>
			<c:if test="${dto.controlType eq 'datetimetext'}">
				$('#add${dto.fieldCode}').datetimebox('setValue', '');
			</c:if>
			<c:if test="${dto.controlType eq 'pulltexttype' }">
				$('#add${dto.fieldCode}').combobox('setValue', '');
				$('#add${dto.fieldCode}').combobox('setText', '');
			</c:if>
			<c:if test="${dto.controlType eq 'selectpersontext'}">
				$('#add${dto.fieldCode}').combotree('setValue', '');
				$('#add${dto.fieldCode}').combotree('setText', '');
			</c:if>
			<c:if test="${dto.controlType eq 'selectdepttext'}">
				$('#add${dto.fieldCode}').combotree('setValue', '');
				$('#add${dto.fieldCode}').combotree('setText', '');
			</c:if>
			<c:if test="${dto.controlType eq 'outkey_relate'}">
				$('#add${dto.fieldCode}').combobox('setValue', '');
				$('#add${dto.fieldCode}').combobox('setText', '');
			</c:if>
		</c:forEach>
		// 以上是对增加dialog的窗口进行数据的清空操作
		$('#addForm').dialog({
			title:'增加窗口',
			modal:true,
			draggable:false,
			buttons:[{
				text:'保存',
				handler:function() {
					$('#saveForm').form({
						url:'${ctx}/core/coreAction!save.action?menuId=${menuId}',
						method:'POST',
						success:function(data) {
							var data = eval('(' + data + ')');
							if (data.operateSuccess) {
								$('#addForm').dialog('close');
								$.messager.alert('成功',data.operateMessage);
								$('#addForm').dialog('close');
								grid.reloadGrid();
							} else {
								$.messager.alert('失败',data.operateMessage);
							}
						}
					});
					$('#saveForm').submit();
				}
			}, {
				text:'取消',
				handler:function() {
					$('#addForm').dialog('close');
				}
			}]
		});
	}
	</shiro:hasPermission>
	
	<shiro:hasPermission name="${menuId}:update">
	// 更新窗口
	function updateFunction(rowId) {
		$.loding();
		<c:forEach var="dto" items="${listDTO}" varStatus="status">
		<c:if test="${dto.controlType eq 'onetext' 
			|| dto.controlType eq 'telephone' || dto.controlType eq 'multitext'}">
			$('#update${dto.fieldCode}').val('');
		</c:if>
		<c:if test="${dto.controlType eq 'numbertext'}">
			$('#update${dto.fieldCode}').numberbox('clear');
		</c:if>
		<c:if test="${dto.controlType eq 'datetext'}">
			$('#update${dto.fieldCode}').datebox('setValue', '');
		</c:if>
		<c:if test="${dto.controlType eq 'datetimetext'}">
			$('#update${dto.fieldCode}').datetimebox('setValue', '');
		</c:if>
		<c:if test="${dto.controlType eq 'pulltexttype' }">
			$('#update${dto.fieldCode}').combobox('setValue', '');
			$('#update${dto.fieldCode}').combobox('setText', '');
		</c:if>
		<c:if test="${dto.controlType eq 'selectpersontext'}">
			$('#update${dto.fieldCode}').combotree('setValue', '');
			$('#update${dto.fieldCode}').combotree('setText', '');
		</c:if>
		<c:if test="${dto.controlType eq 'selectdepttext'}">
			$('#update${dto.fieldCode}').combotree('setValue', '');
			$('#update${dto.fieldCode}').combotree('setText', '');
		</c:if>
		<c:if test="${dto.controlType eq 'outkey_relate'}">
			$('#update${dto.fieldCode}').combobox('setValue', '');
			$('#update${dto.fieldCode}').combobox('setText', '');
		</c:if>
		</c:forEach>
		$('#updateId').val(rowId);
		// 填充数据
		var query = {
			'menuId':menuId,
			'id':rowId
		};
		$.post('${ctx}/core/coreAction!getById.action', query, function(data) {
			var data = eval('(' + data + ')');
			<c:forEach var="dto" items="${listDTO}" varStatus="status">
			<c:if test="${dto.controlType eq 'onetext'
				|| dto.controlType eq 'telephone' || dto.controlType eq 'multitext'}">
				$('#update${dto.fieldCode}').val(data[0]['${dto.fieldCode}']);
			</c:if>
			<c:if test="${dto.controlType eq 'numbertext'}">
				$('#update${dto.fieldCode}').numberbox('setValue', data[0]['${dto.fieldCode}']);
			</c:if>
			<c:if test="${dto.controlType eq 'datetext'}">
				$('#update${dto.fieldCode}').datebox('setValue', data[0]['${dto.fieldCode}']);
			</c:if>
			<c:if test="${dto.controlType eq 'datetimetext'}">
				$('#update${dto.fieldCode}').datetimebox('setValue', data[0]['${dto.fieldCode}']);
			</c:if>
			<c:if test="${dto.controlType eq 'pulltexttype' }">
				$('#update${dto.fieldCode}').combobox('setValue', data[0]['${dto.fieldCode}']);
				var temp = {
					'id':data[0]['${dto.fieldCode}']
				};
				$.post('${ctx}/data/dataAction!getById.action', temp, function(dataObj) {
					if(dataObj !=null && dataObj.dataName != null)
					$('#update${dto.fieldCode}').combobox('setText', dataObj.dataName);
				});
			</c:if>
			<c:if test="${dto.controlType eq 'selectpersontext'}">
				$('#update${dto.fieldCode}').combotree('setValue', data[0]['${dto.fieldCode}']);
				var temp = {
					'id':data[0]['${dto.fieldCode}']	
				};
				$.post('${ctx}/user/userAction!getByIdDTO.action', temp,function(dataObj) {
					$('#update${dto.fieldCode}').combotree('setText', dataObj.userName);
				});
			</c:if>
			<c:if test="${dto.controlType eq 'selectdepttext'}">
				$('#update${dto.fieldCode}').combotree('setValue', data[0]['${dto.fieldCode}']);
				var temp = {
					'id':data[0]['${dto.fieldCode}']	
				};
				$.post('${ctx}/dept/deptAction!getById.action', temp, function(dataObj) {
					$('#update${dto.fieldCode}').combotree('setText', dataObj.deptName);
				});
			</c:if>
			<c:if test="${dto.controlType eq 'outkey_relate'}">
				$('#update${dto.fieldCode}').combobox('setValue', data[0]['${dto.fieldCode}']);
				var str = '${parentName}:${dto.relatedCode}:' + data[0]['${dto.fieldCode}'];
				var temp = {
					'parentTableParam':str	
				};
				$.post('${ctx}/core/coreAction!updateComboDataByValueId.action', temp, function(data) {
					$('#update${dto.fieldCode}').combobox('setText', data);
				});
			</c:if>
			</c:forEach>
			$.loded();
		$('#updateForm').dialog({
			title:'更新窗口',
			modal:true,
			draggable:false,
			buttons:[{
				text:'更新',
				handler:function() {
					$('#updateFormF').form({
						url:'${ctx}/core/coreAction!update.action?menuId=${menuId}',
						method:'POST',
						success:function(data) {
							var data = eval('(' + data + ')');
							if (data.operateSuccess) {
								$('updateForm').dialog('close');
								$.messager.alert('成功',data.operateMessage);
								grid.reloadGrid();
								$('#updateForm').dialog('close');
							} else {
								$.messager.alert('失败',data.operateMessage);
							}
						}
					});
					$('#updateFormF').submit();
				}
			}, {
				text:'取消',
				handler:function() {
					$('#updateForm').dialog('close');
				}
			}]
		});
		});
	}
	</shiro:hasPermission>
	
	<shiro:hasPermission name="${menuId}:view">
	// 查看窗口
	function viewFunction(rowId) {
		$.loding();
		// 填充数据
		var query = {
			'menuId':menuId,
			'id':rowId
		};
		$.post('${ctx}/core/coreAction!getById.action', query, function(data) {
			var data = eval('(' + data + ')');
			<c:forEach var="dto" items="${listDTO}" varStatus="status">
			<c:if test="${dto.controlType eq 'onetext' || dto.controlType eq 'numbertext'
				|| dto.controlType eq 'telephone'
				|| dto.controlType eq 'datetext' || dto.controlType eq 'multitext'
				|| dto.controlType eq 'datetimetext'}">
// 				$('#view${dto.fieldCode}').val(data[0]['${dto.fieldCode}']);
				$('#view${dto.fieldCode}').html(data[0]['${dto.fieldCode}']);
			</c:if>
			<c:if test="${dto.controlType eq 'pulltexttype' }">
				var temp = {
					'id':data[0]['${dto.fieldCode}']
				};
				$.post('${ctx}/data/dataAction!getById.action', temp, function(dataObj) {
					if(dataObj!=null){
						$('#view${dto.fieldCode}').html(dataObj.dataName);
					}
				});
			</c:if>
			<c:if test="${dto.controlType eq 'selectpersontext'}">
				var temp = {
					'id':data[0]['${dto.fieldCode}']	
				};
				$.post('${ctx}/user/userAction!getByIdDTO.action', temp,function(dataObj) {
					$('#view${dto.fieldCode}').html(dataObj.userName);
				});
			</c:if>
			<c:if test="${dto.controlType eq 'selectdepttext'}">
				var temp = {
					'id':data[0]['${dto.fieldCode}']	
				};
				$.post('${ctx}/dept/deptAction!getById.action', temp, function(dataObj) {
					$('#view${dto.fieldCode}').html(dataObj.deptName);
				});
			</c:if>
			<c:if test="${dto.controlType eq 'outkey_relate'}">
				var str = '${parentName}:${dto.relatedCode}:' + data[0]['${dto.fieldCode}'];
				var temp = {
					'parentTableParam':str	
				};
				$.post('${ctx}/core/coreAction!updateComboDataByValueId.action', temp, function(data) {
					$('#view${dto.fieldCode}').html(data);
				});
			</c:if>
			</c:forEach>
			$.loded();
			$('#viewForm').dialog({
				title:'查看窗口',
				modal:true,
				draggable:false,
				buttons:[{
					text:'取消',
					handler:function() {
						$('#viewForm').dialog('close');
					}
				}]
			});
		});
		<c:forEach var="childDTO" items="${childList}" varStatus="status">
			$('#${childDTO.tableName}data_list').datagrid({
				title : '${childDTO.tableName}',//去除title
				iconCls : 'icon-search',
				nowrap : false,
				striped : true,
				border : true,
				collapsible : false,
				fit : true,
				url : '${ctx}/core/coreAction!coreChildPageList.action?childTableParam=${childDTO.tableName}:' + rowId,
				remoteSort : false,
				singleSelect : true,
				//pagination : true,
				rownumbers : true,
				loadMsg:"正在努力拉取数据中...",
				fitColumns:true
			});
		</c:forEach>
		
		
	}
	</shiro:hasPermission>
	
	
	<shiro:hasPermission name="${menuId}:imp">
	function impFunction() {
		$('#excelWin').dialog({
			width:500,
			title:'导入窗口',
			modal:true,
			draggable:false,
			buttons:[{
				text:'取消',
				handler:function() {
					$('#excelWin').dialog('close');
				}
			}]
		});
	}
	</shiro:hasPermission>
	
	<shiro:hasPermission name="${menuId}:exp">
	function expFunction() {
		var searchParam = "";
		<c:forEach var="dto" items="${listDTO}">
		<c:if test="${dto.isSearch eq '1'}">
			   <c:if test="${dto.controlType eq 'onetext' 
				   || dto.controlType eq 'telephone' || dto.controlType eq 'numbertext'}">
			   searchParam = searchParam + '${dto.fieldCode}:' + $('#search${dto.fieldCode}').val() + ',';
			   	</c:if>
				<c:if test="${dto.controlType eq 'datetimetext'}">
				searchParam = searchParam + '${dto.fieldCode}:' + $('#search${dto.fieldCode}').datetimebox('getValue') + ',';
				</c:if>
				<c:if test="${dto.controlType eq 'datetext'}">
				searchParam = searchParam + '${dto.fieldCode}:' + $('#search${dto.fieldCode}').datebox('getValue') + ',';
				</c:if>
				<c:if test="${dto.controlType eq 'pulltexttype' }">
				searchParam = searchParam + '${dto.fieldCode}:' + $('#search${dto.fieldCode}').combobox('getValue') + ',';
				</c:if>
		</c:if>
		</c:forEach>
		$('#searchMap').val(searchParam);
		$('#expForm').form({
			url:'${ctx}/core/coreAction!exp.action',
			method:'POST'
		});
		$('#expForm').submit();
	}
	
	</shiro:hasPermission>
	
	<shiro:hasPermission name="${menuId}:delete">
	function deleteFunction() {
        var rows = grid.getSelectNodes();
        if (rows.length == 0) {
            $.messager.alert('提示','请选择要删除的行');
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
                        url:'${ctx}/core/coreAction!deleteByIds?ids=' + ids + '&menuId=${menuId}',
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
	</shiro:hasPermission>
	
	function gridFormatter(value, rowData, rowIndex) {
    	var rowId = rowData.id;
    	var url = ""
    	/* 
    	<shiro:hasPermission name="${menuId}:view">
    	+"<input type='button' onclick='showViewDialog(this);' value='查看' rowId='"+rowId+"'/>&nbsp;&nbsp;"
		</shiro:hasPermission>
		<shiro:hasPermission name="${menuId}:update">
    	+"<input type='button' onclick='showUpdateDialog(this);' value='修改'  rowId='"+rowId+"'&nbsp;&nbsp;/>"
		</shiro:hasPermission>
    	*/
    	<c:forEach var="m" items="${menuOptList}">
		<c:if test="${m.optType=='1' }">
			/* 单记录操作按钮 */
			<shiro:hasPermission name="${menuId}:${m.optCode}">
			<c:choose>
			<c:when test="${m.displayType=='1'}">
			+"<input type='button' class='btn1' onclick=\"customPage('" + rowId+ "','${m.openType}','${m.optName}','${fn:replace(m.designPageUrl,'\\','/')}');\" value='${m.optName}'/>&nbsp;&nbsp;"
			</c:when>
			<c:when test="${not empty m.formId}">
			+"<input type='button'  class='btn1' onclick=\"unionFormFunction('${m.formId}','${m.urlPara}','${m.openType}','${m.optName}'"
				<c:forEach items="${fn:split(m.urlPara,',')}" var="f">+",'"+rowData.${fn:split(f,':')[1]}+"'"</c:forEach>
					+");\" value='${m.optName}'/>&nbsp;&nbsp;"
			</c:when>
			<c:when test="${empty m.funcName}">
			+"<input type='button'  class='btn1' onclick='${m.optCode}Function(\"" + rowId + "\");' value='${m.optName}'/>&nbsp;&nbsp;"
			</c:when>
			<c:when test="${not empty m.funcName}">
			+"<input type='button'  class='btn1' onclick='${m.funcName}(\"" + rowId + "\");' value='${m.optName}' />&nbsp;&nbsp;"
			</c:when>
			</c:choose>
			</shiro:hasPermission>
		</c:if>
		</c:forEach>
    	;
    	return url;
	}
	
	function searchGrid() {
		var searchParam = "";
		<c:forEach var="dto" items="${listDTO}">
		<c:if test="${dto.isSearch eq '1'}">
			   <c:if test="${dto.controlType eq 'onetext' || dto.controlType eq 'telephone'}">
			   searchParam = searchParam + '${dto.fieldCode}:' + $('#search${dto.fieldCode}').val() + ',';
			   	</c:if>
				<c:if test="${dto.controlType eq 'datetimetext'}">
				searchParam = searchParam + '${dto.fieldCode}:' + $('#search${dto.fieldCode}').datetimebox('getValue') + ',';
				</c:if>
				<c:if test="${dto.controlType eq 'datetext'}">
				searchParam = searchParam + '${dto.fieldCode}:' + $('#search${dto.fieldCode}').datebox('getValue') + ',';
				</c:if>
				<c:if test="${dto.controlType eq 'pulltexttype' }">
				searchParam = searchParam + '${dto.fieldCode}:' + $('#search${dto.fieldCode}').combobox('getValue') + ',';
				</c:if>
				<c:if test="${dto.controlType eq 'numbertext'}">
				searchParam = searchParam + '${dto.fieldCode}:' + $('#search${dto.fieldCode}').val() + ',';
				</c:if>
		</c:if>
		</c:forEach>
		/*
		$.post('${ctx}/core/coreAction!pageList.action','menuId=${menuId}&searchMap=' + searchParam
				+ '&filterParameterNames=${filterParameterNames}&filterParameterValues=${filterParameterValues}'
				, function(data) {
			var dataObj = eval('(' + data + ')');
			*/
			var params = {
				searchMap : searchParam,
				filterParameterNames: '${filterParameterNames}',
				filterParameterValues: '${filterParameterValues}'
			}
			grid.doSearch(params);
		//});
	}
	function clearSearchGrid() {
		var searchParam = "";
		<c:forEach var="dto" items="${listDTO}">
		<c:if test="${dto.isSearch eq '1'}">
			   <c:if test="${dto.controlType eq 'onetext' || dto.controlType eq 'telephone'
				   || dto.controlType eq 'numbertext'}">
			   $('#search${dto.fieldCode}').val('');
			   	</c:if>
				<c:if test="${dto.controlType eq 'datetimetext'}">
				$('#search${dto.fieldCode}').datetimebox('setValue', '');
				</c:if>
				<c:if test="${dto.controlType eq 'datetext'}">
				$('#search${dto.fieldCode}').datebox('setValue', '');
				</c:if>
				<c:if test="${dto.controlType eq 'pulltexttype' }">
				$('#search${dto.fieldCode}').combobox('setValue', '');
				</c:if>
		</c:if>
		</c:forEach>
	}
	function customPage(rowId,openType,optName,freemarkerPath){
		// 填充数据
		var query = {
			'menuId':menuId,
			'id':rowId,
			'freemarkerPath':freemarkerPath
		};
		if(openType=='0'){
			document.frames["alertFrame"].document.body.innerText = "";
			$.postForm('${ctx}/core/coreAction!getById.action',query,'alertFrame');
			$("#alertFrame").window({title:optName});
			$("#alertFrame").window("open");
		}else{
			$.postForm('${ctx}/core/coreAction!getById.action',query);
		}
	}
	/* 自定义单记录操作按钮所调用方法(关联表单) */
	function unionFormFunction(formId,urlPara,openType,optName) {
		var jsonStr="{'formId':'"+formId+"'";
		var f = urlPara.split(',');
		var names = [];
		var values = [];
		for (i=0 ; i<f.length;i++){
			if(i+3>=arguments.length){
				break;
			}
			names.push(f[i].split(':')[0]);
			values.push(arguments[i+4]);
			//alert(f[i].split(':')[0]+'|'+arguments[i+2]);
			//jsonStr+=",'"+f[i].split(':')[0]+"':'"+arguments[i+2]+"'";
		}
		jsonStr+=",'filterParameterNames':'"+names+"'";
		jsonStr+=",'filterParameterValues':'"+values+"'";
		jsonStr+="}";
		//alert(jsonStr);
		var json = eval('('+jsonStr+')');
		if(openType=='0'){
			document.frames["alertFrame"].document.body.innerText = "";
			$.postForm('${ctx}/core/coreAction!unionForm.action',json,'alertFrame');
			$("#alertFrame").window({title:optName});
			$("#alertFrame").window("open");
		}else{
			$.postForm('${ctx}/core/coreAction!unionForm.action',json);
		}
	}
	/* 自定义多记录操作按钮所调用方法 */
	function batchFunction(batchSql) {
        var rows = grid.getSelectNodes();
        if (rows.length == 0) {
            $.messager.alert('提示','请选择要操作的行');
            return;
        } else {
            var ids = new Array();
            $.messager.confirm('警告', '确定要操作该记录?', function (r) {
                if (r) {
                    var i = 0;
                    for (i=0; i<rows.length; i++) {
                        ids.push(rows[i].id);
                    }
                    $.post('${ctx}/core/coreAction!batchOperation?ids='+ids+'&batchSql='+batchSql,function(data){
                    	var data = eval('(' + data + ')');
                    	if (data.operateSuccess) {
                    		$.messager.alert('成功',data.operateMessage);
                            grid.reloadGrid();
                        } else {
                        	$.messager.alert('失败',data.operateMessage);
                        }
                    });
                }
            });
        }
	}
	/* 生成按钮自定义的function 不是定制页面且有方法时  */
	<c:forEach var="m" items="${menuOptList}">
	<c:if test="${m.displayType!='1' && not empty m.funcName}">
		<shiro:hasPermission name="${menuId}:${m.optCode}">
			${m.funcContent}
		</shiro:hasPermission>
	</c:if>
	</c:forEach>
</script>
</head>
<body>
	<div id="tb">
		<c:set var="isShowCheckbox" value="false"></c:set>
		<c:forEach var="m" items="${menuOptList}">
			<c:if test="${m.optType=='2'}">
				<!-- 多记录操作按钮 -->
				<shiro:hasPermission name="${menuId}:${m.optCode}">
					<c:set var="isShowCheckbox" value="true"></c:set>
					<c:if test="${empty m.funcContent && empty m.funcName }">
						<a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-${m.optCode}', plain:true"
							onclick="${m.optCode}Function();return false;">${m.optName}</a>
					</c:if>
					<c:if test="${empty m.funcContent && not empty m.funcName }">
						<a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-${m.optCode}', plain:true"
							onclick="${m.funcName}();return false;">${m.optName}</a>
					</c:if>
					<c:if test="${not empty m.funcContent }">
						<a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-${m.optCode}', plain:true"
							onclick="batchFunction('${m.funcContent}');return false;">${m.optName}</a>
					</c:if>
				</shiro:hasPermission>
			</c:if>
			<c:if test="${m.optType=='3' }">
				<!-- 记录无关操作按钮 -->
				<shiro:hasPermission name="${menuId}:${m.optCode}">
					<c:if test="${empty m.funcName }">
						<a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-${m.optCode}', plain:true"
							onclick="${m.optCode}Function();return false;">${m.optName}</a>
					</c:if>
					<c:if test="${not empty m.funcName }">
						<a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-${m.optCode}', plain:true"
							onclick="${m.funcName}();return false;">${m.optName}</a>
					</c:if>
				</shiro:hasPermission>
			</c:if>
		</c:forEach>
		<%--
		<shiro:hasPermission name="${menuId}:add">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addFunction();return false;">增加</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="${menuId}:delete">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteFunction();return false;">删除</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="${menuId}:imp">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="showImp();return false;">导入</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="${menuId}:exp">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="exp();return false;">导出</a>
		</shiro:hasPermission>
		  --%>

		<!-- 查询的按钮 -->
		<div>
			<c:forEach var="dto" items="${listDTO}">
				<c:if test="${dto.isSearch eq '1'}">
					<c:if
						test="${dto.controlType eq 'onetext' || dto.controlType eq 'telephone'}">
							      &nbsp;&nbsp;${dto.viewName}:&nbsp;&nbsp;<input type="text"
							id="search${dto.fieldCode}" name="${dto.fieldCode}" />
					</c:if>
					<c:if test="${dto.controlType eq 'datetimetext'}">
							&nbsp;&nbsp;${dto.viewName}:&nbsp;&nbsp;<input type="text"
							class="easyui-datetimebox" id="search${dto.fieldCode}"
							name="${dto.fieldCode}" data-option="editable:false" />
					</c:if>
					<c:if test="${dto.controlType eq 'datetext'}">
							&nbsp;&nbsp;${dto.viewName}:&nbsp;&nbsp;<input type="text"
							class="easyui-datebox" id="search${dto.fieldCode}"
							name="${dto.fieldCode}" data-option="editable:false" />
					</c:if>
					<c:if test="${dto.controlType eq 'pulltexttype' }">
								&nbsp;&nbsp;${dto.viewName}:&nbsp;&nbsp;<input
							id="search${dto.fieldCode}" name="${dto.fieldCode}"
							class="easyui-combobox"
							data-options="editable:false,valueField:'id', textField:'dataName', url:'${ctx }/data/dataAction!getDataByType.action?idParam=${dto.dataTypeId}'"
							panelHeight="auto" />
					</c:if>
					<c:if test="${dto.controlType eq 'numbertext'}">
							&nbsp;&nbsp;${dto.viewName}:&nbsp;&nbsp;<input
							id="search${dto.fieldCode}" name="${dto.fieldCode}"
							class="easyui-numberbox" />
					</c:if>
				</c:if>
			</c:forEach>
			&nbsp;&nbsp;<a href="#" class="easyui-linkbutton"
				iconCls="icon-search" onclick="searchGrid();return false;">查询</a>
			&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-clear" onclick="clearSearchGrid();">清空</a>
		</div>
	</div>

	<div style="height: 100%">
		<table id="data_list" style="display: none">
			<thead>
				<tr>
					<c:forEach var="dto" items="${listDTO}">
						<c:if test="${dto.isView eq '1'}">
							<th align="center" field="${dto.fieldCode}" width="${dto.width}"
								title="${dto.fieldName}"
								<c:if test="${dto.orderType eq '1'}">sortable=true</c:if>>${dto.viewName}</th>
						</c:if>
					</c:forEach>
					<th align="center" field="id" width="160" title="id"
						formatter="gridFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<shiro:hasPermission name="${menuId}:add">
		<!-- 添加表单 Begin -->
		<!-- 每两个一行 -->
		<div style="display: none;">
			<div id="addForm" style="width: 960px; height: 500px;">
				<form id="saveForm" name="saveForm" method="post"
					action="${ctx}/core/coreAction!save.action">
					<div style="margin: 0 auto;" align="center">
						<table class="detailTable" style="width: 80%;">
							<c:set var="rowStatus" value="false"></c:set>
							<c:forEach var="dto" items="${listDTO}" varStatus="status">
								<c:if test="${dto.editType != '2'}">
									<c:set var="rowStatus" value="${not rowStatus }"></c:set>
									<c:set var="dataOptions" value=""></c:set>
									<c:if test="${dto.isNull eq '0' || dto.required eq '1'}">
										<c:set var="dataOptions" value="required:true"></c:set>
									</c:if>
									<c:choose>
										<c:when
											test="${dto.controlType eq 'numbertext' || dto.controlType eq 'onetext'
					||dto.controlType eq 'multitext'}">
											<c:if
												test="${not empty dto.fieldLength && dto.fieldLength != 0 && dto.controlType != 'numbertext'}">
												<c:if test="${not empty dataOptions }">
													<c:set var="dataOptions" value="${dataOptions},"></c:set>
												</c:if>
												<c:set var="dataOptions"
													value="${dataOptions}validType:'maxByteLength[${dto.fieldLength}]'"></c:set>
											</c:if>
											<c:if test="${dto.controlType eq 'numbertext'}">
												<c:if test="${not empty dataOptions }">
													<c:set var="dataOptions" value="${dataOptions},"></c:set>
												</c:if>
												<c:set var="dataOptions"
													value="${dataOptions}validType:'systemInt'"></c:set>
											</c:if>
										</c:when>
										<c:when test="${dto.controlType eq 'telephone'}">
											<c:if test="${not empty dataOptions }">
												<c:set var="dataOptions" value="${dataOptions},"></c:set>
											</c:if>
											<c:set var="dataOptions"
												value="${dataOptions}validType:'phoneNumber'"></c:set>
										</c:when>
										<c:otherwise>
											<c:if test="${not empty dataOptions }">
												<c:set var="dataOptions" value=",${dataOptions}"></c:set>
											</c:if>
										</c:otherwise>
									</c:choose>

									<c:if test="${rowStatus}">
										<tr>
									</c:if>
									<c:choose>
										<c:when
											test="${dto.controlType eq 'onetext' || dto.controlType eq 'telephone'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-validatebox" id="add${dto.fieldCode}"
												name="${dto.fieldCode}" data-options="${dataOptions }" /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'multitext'}">
											<c:if test="${not rowStatus}">
												<td colspan="2" class="contentTd"></td>
												</tr>
												<tr>
											</c:if>
											<c:if test="${rowStatus}">
												<c:set var="rowStatus" value="${not rowStatus }"></c:set>
											</c:if>
											<td class="labelTd">${dto.viewName}:</td>
											<td colspan="3" class="contentTd"><textarea
													id="add${dto.fieldCode}" class="easyui-validatebox"
													name="${dto.fieldCode}" style="width: 80%; height: 80px;"
													data-options="${dataOptions }"></textarea></td>
										</c:when>
										<c:when test="${dto.controlType eq 'datetimetext'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-datetimebox" id="add${dto.fieldCode}"
												name="${dto.fieldCode}"
												data-options="editable:false${dataOptions }" /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'datetext'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-datebox" id="add${dto.fieldCode}"
												name="${dto.fieldCode}"
												data-options="editable:false${dataOptions }" /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'selectpersontext'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-combotree"
												data-options="url:'${ctx}/user/userAction!reloadComboUserTree.action',required:true,valueField:'id',textField:'text',editable:false${dataOptions}"
												id="add${dto.fieldCode}" name="${dto.fieldCode}" /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'selectdepttext'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-combotree"
												data-options="url:'${ctx}/dept/deptAction!reloadDeptComboTree.action',required:true,valueField:'id',textField:'text',editable:false${dataOptions}"
												id="add${dto.fieldCode}" name="${dto.fieldCode}" /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'pulltexttype' }">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input id="add${dto.fieldCode}"
												name="${dto.fieldCode}" class="easyui-combobox"
												data-options="valueField:'id', textField:'dataName', url:'${ctx }/data/dataAction!getDataByType.action?idParam=${dto.dataTypeId}',editable:false${dataOptions}"
												panelHeight="auto" /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'onecheckboxtype'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><%=(String) request.getAttribute("${dto.fieldCode}")%></td>
										</c:when>
										<c:when test="${dto.controlType eq 'multicheckboxtype'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><%=(String) request.getAttribute("${dto.fieldCode}") %></td>
										</c:when>
										<c:when test="${dto.controlType eq 'outkey_relate'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-combobox"
												data-options="url:'${ctx}/core/coreAction!getComboboxData.action?parentTableParam=${parentName}:${dto.relatedCode}', required:true,
								valueField:'id', textField:'value', editable:false${dataOptions}"
												id="add${dto.fieldCode}" name="${dto.fieldCode}"
												panelHeight="auto" /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'numbertext'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-numberbox" id="add${dto.fieldCode}"
												name="${dto.fieldCode}" data-options="${dataOptions }" /></td>
										</c:when>
									</c:choose>
									<c:if test="${not rowStatus}">
										</tr>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${rowStatus}">
								<td colspan="2" class="contentTd"></td>
								</tr>
							</c:if>
						</table>
					</div>
				</form>
			</div>
		</div>
		<!-- 添加表单 End -->
	</shiro:hasPermission>

	<shiro:hasPermission name="${menuId}:update">
		<!-- 这里统一显示成Text类型的 -->
		<!-- 更新窗口 Begin-->
		<div style="display: none;">
			<div id="updateForm" style="width: 960px; height: 500px;">
				<form id="updateFormF" name="updateFormF" method="post"
					action="${ctx}/core/coreAction!update.action">
					<input type="hidden" name="id" id="updateId" />
					<div style="margin: 0 auto;" align="center">
						<table class="detailTable" style="width: 80%;">
							<c:set var="rowStatus" value="false"></c:set>
							<c:forEach var="dto" items="${listDTO}" varStatus="status">
								<c:if test="${dto.editType != '2'}">
									<c:set var="rowStatus" value="${not rowStatus }"></c:set>
									<c:set var="dataOptions" value=""></c:set>
									<c:if test="${dto.isNull eq '0' || dto.required eq '1'}">
										<c:set var="dataOptions" value="required:true"></c:set>
									</c:if>
									<c:choose>
										<c:when
											test="${dto.controlType eq 'numbertext' || dto.controlType eq 'onetext'
					||dto.controlType eq 'multitext'}">
											<c:if
												test="${not empty dto.fieldLength && dto.fieldLength != 0 && dto.controlType != 'numbertext'}">
												<c:if test="${not empty dataOptions }">
													<c:set var="dataOptions" value="${dataOptions},"></c:set>
												</c:if>
												<c:set var="dataOptions"
													value="${dataOptions}validType:'maxByteLength[${dto.fieldLength}]'"></c:set>
											</c:if>
											<c:if test="${dto.controlType eq 'numbertext'}">
												<c:if test="${not empty dataOptions }">
													<c:set var="dataOptions" value="${dataOptions},"></c:set>
												</c:if>
												<c:set var="dataOptions"
													value="${dataOptions}validType:'systemInt'"></c:set>
											</c:if>
										</c:when>
										<c:when test="${dto.controlType eq 'telephone'}">
											<c:if test="${not empty dataOptions }">
												<c:set var="dataOptions" value="${dataOptions},"></c:set>
											</c:if>
											<c:set var="dataOptions"
												value="${dataOptions}validType:'phoneNumber'"></c:set>
										</c:when>
										<c:otherwise>
											<c:if test="${not empty dataOptions }">
												<c:set var="dataOptions" value=",${dataOptions}"></c:set>
											</c:if>
										</c:otherwise>
									</c:choose>
									<c:if test="${rowStatus}">
										<tr>
									</c:if>
									<c:choose>
										<c:when
											test="${dto.controlType eq 'onetext' || dto.controlType eq 'telephone'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-validatebox" id="update${dto.fieldCode}"
												name="${dto.fieldCode}" data-options="${dataOptions }"
												<c:if test="${dto.editType eq '0' }">disabled="true"</c:if> /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'multitext'}">
											<c:if test="${not rowStatus}">
												<td colspan="2" class="contentTd"></td>
												</tr>
												<tr>
											</c:if>
											<c:if test="${rowStatus}">
												<c:set var="rowStatus" value="${not rowStatus }"></c:set>
											</c:if>
											<td class="labelTd">${dto.viewName}:</td>
											<td colspan="3" class="contentTd"><textarea
													class="easyui-validatebox" id="update${dto.fieldCode}"
													name="${dto.fieldCode}" style="width: 80%; height: 80px;"
													<c:if test="${dto.editType eq '0' }">disabled="true"</c:if>></textarea></td>
										</c:when>
										<c:when test="${dto.controlType eq 'datetimetext'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-datetimebox" id="update${dto.fieldCode}"
												name="${dto.fieldCode}"
												data-options="editable:false${dataOptions }"
												<c:if test="${dto.editType eq '0' }">disabled="true"</c:if> /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'datetext'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-datebox" id="update${dto.fieldCode}"
												name="${dto.fieldCode}"
												data-options="editable:false${dataOptions }"
												<c:if test="${dto.editType eq '0' }">disabled="true"</c:if> /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'selectpersontext'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-combotree"
												data-options="url:'${ctx}/user/userAction!reloadComboUserTree.action',required:true,valueField:'id',textField:'text',editable:false${dataOptions}"
												id="update${dto.fieldCode}" name="${dto.fieldCode}"
												<c:if test="${dto.editType eq '0' }">disabled="true"</c:if> /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'selectdepttext'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-combotree"
												data-options="url:'${ctx}/dept/deptAction!reloadDeptComboTree.action',required:true,valueField:'id',textField:'text',editable:false${dataOptions}"
												id="update${dto.fieldCode}" name="${dto.fieldCode}"
												<c:if test="${dto.editType eq '0' }">disabled="true"</c:if> /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'pulltexttype' }">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input id="update${dto.fieldCode}"
												name="${dto.fieldCode}" class="easyui-combobox"
												data-options="valueField:'id', textField:'dataName', url:'${ctx }/data/dataAction!getDataByType.action?idParam=${dto.dataTypeId}',editable:false${dataOptions}"
												<c:if test="${dto.editType eq '0' }">disabled="true"</c:if>
												panelHeight="auto" /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'onecheckboxtype'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><%=(String) request.getAttribute("${dto.fieldCode}")%></td>
										</c:when>
										<c:when test="${dto.controlType eq 'multicheckboxtype'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><%=(String) request.getAttribute("${dto.fieldCode}") %></td>
										</c:when>
										<c:when test="${dto.controlType eq 'outkey_relate'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-combobox"
												data-options="url:'${ctx}/core/coreAction!getComboboxData.action?parentTableParam=${parentName}:${dto.relatedCode}', required:true,
								valueField:'id', textField:'value', editable:false${dataOptions}"
												id="update${dto.fieldCode}" name="${dto.fieldCode}"
												<c:if test="${dto.editType eq '0' }">disabled="true"</c:if>
												panelHeight="auto" /></td>
										</c:when>
										<c:when test="${dto.controlType eq 'numbertext'}">
											<td class="labelTd">${dto.viewName}:</td>
											<td class="contentTd"><input type="text"
												class="easyui-numberbox" id="update${dto.fieldCode}"
												name="${dto.fieldCode}" data-options="${dataOptions }"
												<c:if test="${dto.editType eq '0' }">disabled="true"</c:if> /></td>
										</c:when>
									</c:choose>

									<c:if test="${not rowStatus}">
										</tr>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${rowStatus}">
								<td colspan="2" class="contentTd"></td>
								</tr>
							</c:if>
						</table>
					</div>
				</form>
			</div>
		</div>
		<!-- 更新窗口 End -->
	</shiro:hasPermission>

	<shiro:hasPermission name="${menuId}:view">
		<!-- 这里统一显示成Text类型的 -->
		<!-- 查看窗口 Begin-->
		<div style="display: none;">
			<div id="viewForm" style="width: 960px; height: 500px;">
				<div style="margin: 0 auto;" align="center">
					<table class="detailTable" style="width: 80%;">
						<c:set var="rowStatus" value="false"></c:set>
						<c:forEach var="dto" items="${listDTO}" varStatus="status">
							<c:if test="${dto.editType != '2'}">
								<c:set var="rowStatus" value="${not rowStatus }"></c:set>
								<c:set var="dataOptions" value=""></c:set>
								<c:if test="${dto.isNull eq '0'}">
									<c:set var="dataOptions" value="required:true"></c:set>
								</c:if>
								<c:if
									test="${not empty dto.fieldLength && dto.fieldLength != 0}">
									<c:if test="${not empty dataOptions }">
										<c:set var="dataOptions" value="${dataOptions},"></c:set>
									</c:if>
									<c:set var="dataOptions"
										value="${dataOptions}validType:'length[0,${dto.fieldLength}]'"></c:set>
								</c:if>
								<c:if test="${rowStatus}">
									<tr>
								</c:if>
								<c:choose>
									<c:when
										test="${dto.controlType eq 'onetext' || dto.controlType eq 'datetimetext'
									|| dto.controlType eq 'datetext' || dto.controlType eq 'selectpersontext'
									|| dto.controlType eq 'selectdepttext' || dto.controlType eq 'pulltexttype'
									|| dto.controlType eq 'onecheckboxtype' || dto.controlType eq 'multicheckboxtype'
									|| dto.controlType eq 'outkey_relate' || dto.controlType eq 'numbertext'
									|| dto.controlType eq 'telephone'}">
										<td class="labelTd">${dto.viewName}:</td>
										<td class="contentTd"><span id="view${dto.fieldCode}"
											name="${dto.fieldCode}" class="oneLineText"></span></td>
									</c:when>
									<c:when test="${dto.controlType eq 'multitext'}">
										<c:if test="${not rowStatus}">
											<td colspan="2" class="contentTd"></td>
											</tr>
											<tr>
										</c:if>
										<c:if test="${rowStatus}">
											<c:set var="rowStatus" value="${not rowStatus }"></c:set>
										</c:if>
										<td class="labelTd">${dto.viewName}:</td>
										<td colspan="3" class="contentTd"><span
											id="view${dto.fieldCode}" name="${dto.fieldCode}"
											style="width: 100%; height: 80px;"></span></td>
									</c:when>
								</c:choose>
								<c:if test="${not rowStatus}">
									</tr>
								</c:if>
							</c:if>
						</c:forEach>
						<c:if test="${rowStatus}">
							<td colspan="2" class="contentTd"></td>
							</tr>
						</c:if>
					</table>
				</div>

				<!-- 将子表的所有数据遍历出来 -->
				<c:forEach var="childDTO" items="${childList}" varStatus="status">
					<div style="height: 172px; width: 530px">
						<table id="${childDTO.tableName}data_list">
							<thead>
								<tr>
									<c:forEach var="childFormDTO" items="${childDTO.formDTO}">
										<c:if test="${childFormDTO.isView eq '1'}">
											<%--
							因为是对父表里的一条数据进行查看的操作，故这里不需要再将关联的父表值进行显示，因为所有的数据都是一样的
						 --%>
											<c:if test="${childFormDTO.controlType != 'outkey_relate'}">
												<th field="${childFormDTO.fieldCode}"
													width="${childFormDTO.width}"
													title="${childFormDTO.fieldName}"
													<c:if test="${childFormDTO.orderType eq '1'}">sortable=true</c:if>>${childFormDTO.viewName}</th>
											</c:if>
										</c:if>
									</c:forEach>
								</tr>
							</thead>
						</table>
					</div>
				</c:forEach>
			</div>
		</div>
		<!-- 查看窗口 End -->
	</shiro:hasPermission>

	<!-- 导出操作 -->
	<shiro:hasPermission name="${menuId}:exp">
		<form id="expForm">
			<input type="hidden" name="menuId" value="${menuId}" /> <input
				type="hidden" id="searchMap" name="searchMap" value="" /> <input
				type="hidden" name="filterParameterNames"
				value="${filterParameterNames}" /> <input type="hidden"
				name="filterParameterValues" value="${filterParameterValues}" />
		</form>
	</shiro:hasPermission>

	<!-- 导入操作 -->
	<shiro:hasPermission name="${menuId}:imp">
		<div style="display: none;">
			<div id="excelWin" iconCls="icon-save" title="上传Excel文件">
				<form name="excelForm" method="post" enctype="multipart/form-data"
					action="${ctx}/core/coreAction!imp.action?menuId=${menuId}"
					style="padding: 10px 20px 10px 20px;" target="excelFrame">
					上传Excel文件<input type="file" name="excelFile" id="excelFile" /> <input
						type="submit" value="上传" />
				</form>
				<iframe id="excelFrame" name="excelFrame" frameborder="0"
					width="100%" height="100"></iframe>
			</div>
		</div>
	</shiro:hasPermission>
	<!-- 打开方式为弹出框 -->
	<iframe id="alertFrame" name="alertFrame" frameborder="0"
		style="width: 100%;"></iframe>
	<script type="text/javascript">
	$(function(){
		$("form").submit(function(){
			$("textarea").each(function(){
				$(this).val($(this).val().parseTextarea());
			});
		});
		$("#alertFrame").attr("height",fillHeightSize(0.9));
		$("#alertFrame").window({
			width:fillWidthSize(0.9),
			height:fillHeightSize(0.9),
		    modal:true,
		    collapsible:false,  
		    minimizable:false,  
		    maximizable:false  
		});
		$("#alertFrame").window('close'); 
		
		// 对Grid进行加载的工作
		grid = new Grid('${formName}', 'icon-edit', '${ctx}/core/coreAction!pageList.action?menuId=${menuId}' 
				+ '&filterParameterNames=${filterParameterNames}&filterParameterValues=${filterParameterValues}'
				, 'data_list' ,${isShowCheckbox});
		grid.loadGrid();
		
	});
	function  fillWidthSize(percent) {
		var bodyWidth =  document.body.clientWidth;
		return (bodyWidth - 90) *  percent;
	}
	function  fillHeightSize(percent) {
		var bodyHeight =  document.body.clientHeight;
		return (bodyHeight - 90) *  percent;
	}
	</script>
</body>
</html>