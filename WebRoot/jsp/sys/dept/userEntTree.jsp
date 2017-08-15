<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织结构管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<!-- 将Tree对象引入进来 -->
<script type="text/javascript" src="${ctx}/js/CharacterUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<style type="text/css">

</style>
<script type="text/javascript">
        var parentId = null;

        /*
         * 回调函数
         */
        function doSearch(id, name, type) {
        }
        
        var tree = null;
        var key = null;
        var grid = null;
        var ids = [];
		var idsShow = [];
		var idsTree = [];
		var idsTreeShow = [];
		var idsOld = "";
		var idsOldShow = ""
		var shownnn ="";

        $(document).ready(function () {
        	initEntUserTree();
        	grid = new Grid('${ctx}/enterprise/enterpriseAction!getEntTreeList','data_list');
        	grid.loadGrid();
        	idsOld = '${param.byWatchers}';
        	idsOldShow = '${param.byWatchersShow}';
        });

		
        function initEntUserTree(){
        	$.post('${ctx}/dept/deptAction!findAllEntDeptUser.action', function(data){
        		//console.log(data);
            	data = eval('('+data+')');
            	//console.log(data);
            	if(data == null)return;
            	for (var i in data) {
    				if (data[i].parentId == '-1') {
    					data[i].open= true;
    				}
    			}
            	deptTree1 = $.fn.zTree.init($("#dept_ent_tree"), {
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
    	    	   			
   	    	   				//console.log(idsTree);
   	    	   				//console.log(idsTreeShow);
    	            	},
    	            	onClick : function(event,treeId,treeNode){
    	            		if(treeNode.children != null && treeNode.children != "undefined"){
    	            			return;
    	            		}
    	            		var id = treeNode.name;
    	            		$('#enthylb').val(id);
    	            		search();
    	            	},
    	            	onRightClick : function(event,treeId,treeNode){
    	            		$.fn.zTree.getZTreeObj('dept_ent_tree').cancelSelectedNode(treeNode)
    	            		$('#enthylb').val('');
    	            		search();
    	            	}
    	    	   	}
    	    	}, data);
            	
            	var ztreeobj = $.fn.zTree.getZTreeObj('dept_ent_tree');
            	var allnode = ztreeobj.getNodes();
            	var allid = idsOld.split(",");
            	for(var i in allnode){
            		for(var j in allid){
            			if(allid[j] == allnode[i].id){
            				ztreeobj.checkNode(allnode[i],true,true);
            			}
            		}
            		console.log(allnode[i]);
            	}
            	
            });
        }
        
        function appendSupervise(){
        	
        	idsTree = [];
   			idsTreeShow =[];
   			var nodes = deptTree1.getCheckedNodes(true);
   			for(var i in nodes) {
   				var node = nodes[i];
   				if(node.check_Child_State != 1) {
   					var pnode = node.getParentNode();
   					if(pnode == null || (pnode != null && pnode.check_Child_State == 1)) {
   						idsTree.push(node.id);
   						idsTreeShow.push(node.name);
   					}
   				}
   			}
        	
        	var rows = grid.getSelectNodes();
        	var cols = $.fn.zTree.getZTreeObj('dept_ent_tree').getCheckedNodes(true);
            if (rows.length == 0 && cols.length == 0) {
                $.messager.show({
                    title:'提示',
                    msg:'请选择要添加的企业',
                    timeout:2000,
                    showType:'slide'
                });
                return;
            } else {
            	var ids = new Array();
                $.messager.confirm('提示', '是否确定追加企业?', function (r) {
                    if (r) {
                    	ids = [];
                    	idsShow = [];
                    	shownnn = "";
                    	var oldId = idsOld.split(",");
                    	var oldShow = idsOldShow.split(",");
                    	var i = 0;
                    	//将grid选择的数据放入数组
	    	   			for(var i in rows) {
	    	   				var node = rows[i];
   	   						ids.push(node.id);
   	   						idsShow.push(node.qymc);
	    	   			}
	    	   			
                    	// 将树中选择的节点放入数组
	    	   			for(var j in idsTree){
	    	   				ids.push(idsTree[j]);
	    	   				idsShow.push(idsTreeShow[j])
	    	   			}
	    	   			
	    	   			console.log(ids);
	    	   			console.log(idsShow);
	    	   			
	    	   			var idsGo = [];
	    	   			var idsShowGo = [];
	    	   			
	    	   			// 将之前的属性放入数组
	    	   			for(var i in oldId){
	    	   				idsGo.push(oldId[i]);
	    	   			}
	    	   			
	    	   			for(var i in oldShow){
	    	   				idsShowGo.push(oldShow[i]);
	    	   			}
	    	   			
	    	   			// 追加
	    	   			for(var k in ids){
	    	   				var count = 0;
	    	   				for(var l in oldId){
	    	   					if(oldId[l] == ids[k]){
	    	   						count ++;
	    	   					}
	    	   				}
	    	   				if(count == 0){
	    	   					idsGo.push(ids[k]);
	    	   				}
	    	   			}
	    	   			
	    	   			for(var k in idsShow){
	    	   				var count = 0;
	    	   				for(var l in oldShow){
	    	   					if(oldShow[l] == idsShow[k]){
	    	   						count ++;
	    	   					}
	    	   				}
	    	   				if(count == 0){
	    	   					idsShowGo.push(idsShow[k]);
	    	   				}
	    	   			}
	    	   			
	    	   			for(var key in idsShowGo){
	 	   					   shownnn += "<div class='nameDiv'>"+idsShowGo[key]+"</div>&nbsp;&nbsp;";
	                    }
                        //temp.removeByIds(ids);
                        console.log(idsGo);
                        console.log(idsShowGo);
                        console.log(shownnn);
                        window.opener.setUserInfo(idsGo,idsShowGo,shownnn);
                        
                        window.close();
                    }
                });
            }
        }
        
        function addToSupervise(){
        	
        	idsTree = [];
   			idsTreeShow =[];
   			var nodes = deptTree1.getCheckedNodes(true);
   			for(var i in nodes) {
   				var node = nodes[i];
   				if(node.check_Child_State != 1) {
   					var pnode = node.getParentNode();
   					if(pnode == null || (pnode != null && pnode.check_Child_State == 1)) {
   						idsTree.push(node.id);
   						idsTreeShow.push(node.name);
   					}
   				}
   			}
        	
        	var rows = grid.getSelectNodes();
        	var cols = $.fn.zTree.getZTreeObj('dept_ent_tree').getCheckedNodes(true);
            if (rows.length == 0 && cols.length == 0) {
                $.messager.show({
                    title:'提示',
                    msg:'请选择要添加的企业',
                    timeout:2000,
                    showType:'slide'
                });
                return;
            } else {
            	var ids = new Array();
                $.messager.confirm('提示', '是否确定选择企业?', function (r) {
                    if (r) {
                    	ids = [];
                    	idsShow = [];
                    	shownnn = "";
                    	var i = 0;
	    	   			for(var i in rows) {
	    	   				var node = rows[i];
   	   						ids.push(node.id);
   	   						idsShow.push(node.qymc);
	    	   			}
	    	   			
	    	   			for(var j in idsTree){
	    	   				ids.push(idsTree[j]);
	    	   				idsShow.push(idsTreeShow[j])
	    	   			}
	    	   			for(var key in idsShow){
	 	   					   shownnn += "<div class='nameDiv'>"+idsShow[key]+"</div>&nbsp;&nbsp;";
 	                    }
                        //temp.removeByIds(ids);
                        console.log(ids);
                        console.log(idsShow);
                        console.log(shownnn);
                        window.opener.setUserInfo(ids,idsShow,shownnn);
                        window.close();
                    }
                });
            }
        }
        
        function search(){
        	var form = $.form2Json($('#searchForm'));
        	grid.doSearch(form);
        }
    </script>
</head>
<body class="easyui-layout" id="main_layout">
 <div region="center" style="overflow: hidden;" border="true" plain="true" id="center">
 <div class="datagrid-toolbar" id="tb" style="padding-left: 0px; height: 10px;">
		<div>
			<form id="searchForm" style="margin: 0;">
				<input type="hidden" id="enthylb" name="enterprise.hylbShow" />  
				&nbsp;&nbsp;企业名称:&nbsp;&nbsp;<input type="text" name="enterprise.qymc" onchange="search()" onkeyup="search()" /> 
				&nbsp;&nbsp;监管单位:&nbsp;&nbsp;<input type="text" name="enterprise.showDept" onchange="search()" onkeyup="search()" />
			</form>
		</div>
	</div>
<div class="search_add">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="addToSupervise();">更改</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="appendSupervise();">追加</a>
</div>
 	<div style="height: 74%; padding-top: 5px;">
		<table id="data_list" width="100%" loadMsg="正在努力拉取数据中..." fit="true" fitColumns="true">
			<thead>
				<tr>
					<th align="center" field="qymc" width="150" >企业名称</th>
					<th align="center" field="showDept" width="150" >监管单位</th>
					<th align="center" field="hylb" width="150" >企业类型</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
	 
			
	<div region="west" split="true" id="main_west" border="true"
		plain="true" style="width: 220px; padding1: 1px; overflow: hidden;">
		<div id="p" class="easyui-panel" title="&nbsp;&nbsp;组织机构管理" fit="true"
			tools="#tt" border="false" plain="true">
			<div id="tree_search" style="display: none">
				关键字:&nbsp;&nbsp;<input type="text" id="key" name="key"
					style="width: 80px; margin-top: 3px; height: 25px;" />&nbsp;&nbsp;<a
					href="#" class="easyui-linkbutton" iconCls="icon-search"
					plain="true" onclick="tree.searchNode();">搜索</a>
			</div>
			<div id="dept_ent_tree" class="ztree"></div>
<!-- 			<div id="dept_tree" class="ztree"></div> -->
		</div>
		<div id="tt">
			<a href="#" class="icon-treeSearch"
				onclick="javascript:tree.changeTreeSearchPanelShow();" title="搜索"></a>
			<a href="#" class="icon-treeRefresh"
				onclick="javascript:tree.loadTree();" title="刷新"></a> <a href="#"
				class="icon-treeAdd" onclick="javascript:tree.expandAllNode(false);"
				title="收缩所有"></a> <a href="#" class="icon-treeDelete"
				onclick="javascript:tree.expandAllNode(true);" title="展开所有"
				style="margin-right: 10px;"></a>
		</div>
	</div>
</body>
</html>