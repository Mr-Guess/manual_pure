<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<%@ taglib prefix="jstl" uri="http://www.a-y.com.cn/jstl" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据监控</title>
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>

<link rel="stylesheet" href="${ctx}/css/newCrud.css" type="text/css"></link>
<script type="text/javascript">
function showMenu(obj, menuDivId) {
	var cityObj = $(obj);
	var cityOffset = $(obj).offset();
	$("#"+menuDivId).css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	var hideMenu = function() {
		$("#"+menuDivId).fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	};
	var onBodyDown = function (event) {
		if (!(event.target.id == menuDivId || $(event.target).parents("#"+menuDivId).length>0)) {
			hideMenu();
		}
	};
	$("body").bind("mousedown", onBodyDown);
}
$(document).ready(
 	function (){
 		grid = new Grid('${ctx}/watch/watchAction!list?watch.watchType=1', 'data_list');
		grid.loadGrid();
		crud = new Crud({
			grid:grid, 
			addFormObject:$('#addForm'), 
			searchFormObject:$('#searchForm'),
			entityName:'watch',
			dialogCss : {
				width : '900px',
				height : '600px'
			},
			moduleName:'数据监控',
			url:'${ctx}/watch/watchAction',
			beforeSubmit: function(){
				var idss = '${jstl:uuid()}';
				crud.params.addFormObject.find("input:[name='watch.id']").val(idss);
				$("input:[name='watch.watchType']").val(1);
				return true;
			}
		});
		$.post('${ctx}/dept/deptAction!findAllDeptUser.action', function(data){
        	data = eval(data);
        	if(data == null)return;
        	for (var i in data) {
				if (typeof (data[i].account) == 'undefined') {
					data[i].icon = "${ctx}/images/deptIcon.png";
				} else {
					data[i].icon = "${ctx}/images/personIcon.png";
				}
				if (data[i].parentId == '-1') {
					data[i].open= true;
				}
			}
        	deptTree1 = $.fn.zTree.init($("#deptUserTree1"), {
	    	   	view : {
	    			selectedMulti : false
	    	   	},
	    	   	check: {
	    			enable: true
	    		},
	    	   	data : {
	    	   		key : {
	    	   			checked : "isChecked"
	    	   		},
	    	   		simpleData : {
	    	   			enable : true,
	    	   			idKey : "id",
	    	   			pIdKey : "parentId",
	    	   			rootPId : "-1"
	    	   		}
	    	   	},
	    	   	callback: {
	    	   		onCheck : function (event, treeId, treeNode) {
	    	   			var nodes = deptTree1.getCheckedNodes(true);
	    	   			var ids = [];
	    	   			var idsShow = [];
	    	   			for(var i in nodes) {
	    	   				var node = nodes[i];
	    	   				if(node.check_Child_State != 1) {
	    	   					var pnode = node.getParentNode();
	    	   					if(pnode == null || (pnode != null && pnode.check_Child_State == 1)) {
	    	   						ids.push(node.id);
	    	   						idsShow.push(node.name);
	    	   					}
	    	   				}
	    	   			}
	   					crud.params.addFormObject.find('input[name="watch.watchers"]').val(ids.join(','));
	   					crud.params.addFormObject.find('input[name="watch.watchersShow"]').val(idsShow.join(','));
	            	}
	    	   	}
	    	}, data);
        	deptTree2 = $.fn.zTree.init($("#deptUserTree2"), {
	    	   	view : {
	    			selectedMulti : false
	    	   	},
	    	   	check: {
	    			enable: true
	    		},
	    	   	data : {
	    	   		key : {
	    	   			checked : "isChecked"
	    	   		},
	    	   		simpleData : {
	    	   			enable : true,
	    	   			idKey : "id",
	    	   			pIdKey : "parentId",
	    	   			rootPId : "-1"
	    	   		}
	    	   	},
	    	   	callback: {
	    	   		onCheck : function (event, treeId, treeNode) {
	    	   			var nodes = deptTree2.getCheckedNodes(true);
	    	   			var ids = [];
	    	   			var idsShow = [];
	    	   			for(var i in nodes) {
	    	   				var node = nodes[i];
	    	   				if(node.check_Child_State != 1) {
	    	   					var pnode = node.getParentNode();
	    	   					if(pnode == null || (pnode != null && pnode.check_Child_State == 1)) {
	    	   						ids.push(node.id);
	    	   						idsShow.push(node.name);
	    	   					}
	    	   				}
	    	   			}
	   					crud.params.addFormObject.find('input[name="watch.byWatchers"]').val(ids.join(','));
	   					crud.params.addFormObject.find('input[name="watch.byWatchersShow"]').val(idsShow.join(','));
	            	}
	    	   	}
	    	}, data);
        });
		$.post('${ctx}/watch/watchAction!getAllTable', function(data){
			console.log(data);
			var dat = eval('('+data+')');
			console.log(dat);
       	tree3 = $.fn.zTree.init($("#moduleTree"), {
    	   	view : {
    			selectedMulti : false
    	   	},
    	   	check: {
    			enable: true
    		},
    	   	data : {
    	   		key : {
    	   			checked : "isChecked"
    	   		},
    	   		simpleData : {
    	   			enable : true,
    	   			idKey : "id",
    	   			pIdKey : "parentId",
    	   			rootPId : "-1"
    	   		}
    	   	},
    	   	callback: {
    	   		onCheck : function (event, treeId, treeNode) {
    	   			var nodes = tree3.getCheckedNodes(true);
    	   			var ids = [];
    	   			var idsShow = [];
    	   			for(var i in nodes) {
    	   				var node = nodes[i];
    	   				if(node.check_Child_State != 1) {
    	   					var pnode = node.getParentNode();
    	   					if(pnode == null || (pnode != null && pnode.check_Child_State == 1)) {
    	   						ids.push(node.id);
    	   						idsShow.push(node.name);
    	   					}
    	   				}
    	   			}
   					crud.params.addFormObject.find('input[name="watch.modules"]').val(ids.join(','));
   					crud.params.addFormObject.find('input[name="watch.modulesShow"]').val(idsShow.join(','));
            	}
    	   	}
    	}, dat);
		});
 	}
  ); 
function gridFormatterLength(value, rowData, rowIndex) {
	if (value == null || value == '' || value == 'undefined')
		return '';
	if (value.length > 25)
		return value.substring(0, 25) + '...';
	return value;
}


function gridFormatter(value, rowData, rowIndex) {
	var rowId = rowData.id;
	var url = "";
	<shiro:hasPermission name="${menuId}:view">
	url += "<input type='button' onclick='crud.view(\""+rowId+"\");' value='查看' class='btn1' />&nbsp;&nbsp;&nbsp;&nbsp;";
	</shiro:hasPermission>
	<shiro:hasPermission name="${menuId}:update">
	url += "<input type='button' onclick='crud.update(\""+rowId+"\");' value='修改' class='btn1' />&nbsp;&nbsp;&nbsp;&nbsp;";
	</shiro:hasPermission>
	return url;
}

</script>
</head>
<body>
<div id="toolbar">
	<div class="search_add">
		<shiro:hasPermission name="${menuId}:add">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="crud.add();">添加</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="${menuId}:delete">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="crud.remove();">删除</a>
		</shiro:hasPermission>
	</div>
</div>
	<!-- 表单表头 -->
	<div style="height: 74%;">
		<table id="data_list" title = "数据监控" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true" style="display: none">
			<thead>
				<tr>
					<th align="center" field="watchersShow"
						formatter="gridFormatterLength" width="15%">监控者</th>
					<th align="center" field="modulesShow"
						formatter="gridFormatterLength" width="15%">模块</th>
					<th align="center" field="byWatchersShow"
						formatter="gridFormatterLength" width="15%">被监控者</th>
					<th align="center" field="id" width="15%" formatter="gridFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<div style="display: none;">
		<div id="addForm" style="width: 900px; height: 200px;"
			data-options="iconCls:'icon-save',modal:true,
    			minimizable:true,maximizable:true,collapsible:true,draggable:false">
			<form method="post">
				<input type="hidden" id="updateId" name="watch.id" /> <input
					type="hidden" name="watch.watchers" /> <input type="hidden"
					name="watch.modules" /> <input type="hidden"
					name="watch.byWatchers" /> <input type="hidden"
					name="watch.watchType" />
				<div style="padding: 10px 20px 10px 20px;" align="center"
					name="addDiv">
					<table id="detailTable" cellpadding="5px" style="font-size: 12px;"
						cellspacing="1" border="0" bgcolor="#aed0ea" width="95%">
						<tr>
							<td class="table_nemu_new" width="25%">监控者:</td>
							<td class="table_con" width="75%"><input type="text"
								class="easyui-validatebox" name="watch.watchersShow"
								data-options="required:true" readonly="readonly"
								onclick="showMenu(this,'tree1')" style="width: 80%;" /></td>
						</tr>
						<tr>
							<td class="table_nemu_new">模块:</td>
							<td class="table_con"><input type="text"
								class="easyui-validatebox" name="watch.modulesShow"
								readonly="readonly" data-options="required:true"
								style="width: 80%;" onclick="showMenu(this,'tree3')" /></td>
						</tr>
						<tr>
							<td class="table_nemu_new">被监控者:</td>
							<td class="table_con"><input type="text"
								class="easyui-validatebox" name="watch.byWatchersShow"
								readonly="readonly" data-options="required:true"
								onclick="showMenu(this,'tree2')" style="width: 80%;" /></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	<div id="tree1" class="menuContent"
		style="display: none; position: absolute; color: #2f332a; z-index: 99999;">
		<ul id="deptUserTree1" class="ztree"
			style="margin-top: 0; width: 250px; height: 198px; background: #f0f6e4; overflow: auto;">
		</ul>
	</div>
	<div id="tree2" class="menuContent"
		style="display: none; position: absolute; color: #2f332a; z-index: 99999;">
		<ul id="deptUserTree2" class="ztree"
			style="margin-top: 0; width: 250px; height: 198px; background: #f0f6e4; overflow: auto;">
		</ul>
	</div>
	<div id="tree3" class="menuContent"
		style="display: none; position: absolute; color: #2f332a; z-index: 99999;">
		<ul id="moduleTree" class="ztree"
			style="margin-top: 0; width: 250px; height: 198px; background: #f0f6e4; overflow: auto;">
		</ul>
	</div>
</body>
</html>