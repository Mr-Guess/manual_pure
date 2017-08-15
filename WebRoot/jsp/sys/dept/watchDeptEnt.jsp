<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织结构管理</title>

<!-- 将Tree对象引入进来 -->
<script type="text/javascript" src="${ctx}/js/CharacterUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js">
    </script>
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
	text-align: right;
	padding-right: 15px;
	background: #EAF4FC;
	line-height: 28px;
}

.table_con {
	font-size: 13px;
	border: 0px;
	background: #fff;
	line-height: 28px;
	padding-left: 15px;
	width: 75%;
}

.btn1 {
	BORDER: #65b5e4 1px solid;
	PADDING-RIGHT: 15px;
	PADDING-LEFT: 15px;
	FONT-SIZE: 12px;
	FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,
		StartColorStr=#ffffff, EndColorStr=#d7ebf9);
	CURSOR: hand;
	COLOR: black;
	PADDING-TOP: 3px;
}

.nameDiv {
	background-color: rgb(78, 194, 241);
	margin-left: 5px;
	float: left;
	margin-top: 5px;
	padding-left: 10px;
	position: relative;
	padding-right: 10px;
}
</style>
<script type="text/javascript">
        var parentId = null;
        var deptTree1 = null;

        /*
         * 回调函数
         */
        function doSearch(id, name, type) {
            if (id != '-1') {
                $.ajax({
                    url:'${ctx}/dept/deptAction!getById.action?id=' + id,
                    success:function (data) {
                    	clearForm();
                    	if(data == null){
                    		$.ajax({
                    			url:'${ctx}/user/userAction!getById.action?id=' + id,
                    			success:function(edata){
                    				clearForm();
                    				$('#id').val(edata.id);
                                    $('#name').val(edata.userName);
                                    $('#watchers').html(edata.userName);
                                    $('#watchersinput').val(edata.id);
        	                    	$.ajax({
        	                        	url:'${ctx}/watch/watchAction!getById?id='+edata.id,
        	                        	success: function(adata){
        	                        		if(adata == null || adata == ""){
        	                        			$('#byWatchers').val('');
        	                        			$('#backByWatchersShow').val('');
        	                        			$('#showIndex').html('');
        	                        		}else{
        	                        			$('#byWatchers').val(adata.byWatchers);
        	                        			$('#backByWatchersShow').val(adata.byWatchersShow);
        	                        			var arr = adata.byWatchersShow.split(',');
        	                        			var shownnn = "";
        	            	   				    for(var key in arr){
        	            	   					   shownnn += "<div class='nameDiv'>"+arr[key]+"</div>&nbsp;&nbsp;";
        	            	                    }
        	                        			$('#showIndex').html(shownnn);
        	                        		}
        	                        	}
        	                        });
                    			}
                    		});
                    	}else{
	                        $('#id').val(data.id);
	                        $('#name').val(data.deptName);
	                        $('#watchers').html(data.deptName);
	                        $('#watchersinput').val(data.id)
	                    	$.ajax({
	                        	url:'${ctx}/watch/watchAction!getById?id='+data.id,
	                        	success: function(adata){
	                        		if(adata == null || adata == ""){
	                        			$('#byWatchers').val('');
	                        			$('#backByWatchersShow').val('');
	                        			$('#showIndex').html('');
	                        		}else{
	                        			$('#byWatchers').val(adata.byWatchers);
	                        			$('#backByWatchersShow').val(adata.byWatchersShow);
	                        			var arr = adata.byWatchersShow.split(',');
	                        			var shownnn = "";
	            	   				    for(var key in arr){
	            	   					   shownnn += "<div class='nameDiv'>"+arr[key]+"</div>&nbsp;&nbsp;";
	            	                    }
	                        			$('#showIndex').html(shownnn);
	                        		}
	                        	}
	                        });
                    	}
                    }
                });
            } else {
                $('#id').val('');
                $('#parentId').val('-1');
            }
        }
        
        function instAllTable(){
        	$.ajax({
        		url:'${ctx}/watch/watchAction!getAllEntTable.action',
        		success:function(data){
        			var seaDat = eval('('+data+')');
        			var name = "";
        			var table = "";
        			var showName = "";
        			for(var key in seaDat){
        				if(name.length>1){
        					name += ","
        				}
        				
        				if(table.length>1){
        					table += ",";
        				}
        				
        				if(showName.length>1){
        					showName+="&nbsp;";
        				}
        				name += seaDat[key].name;
        				showName += "<div class='nameDiv'>"+seaDat[key].name+"</div>";
        				table += seaDat[key].id;
                    }
        			$('#tables').html(showName);
        			$('#backTables').val(table);
        			$('#backMods').val(name);
        			
        		}
        	});
        }
        
        function clearForm(){
        	$('#id').val("");
            $('#parentId').val("");
            $('#name').val("");
            $('#watchers').html("");
            $('#watchersinput').val("")
        }

        var tree = null;
        var key = null;

        $(document).ready(function () {
            tree = new Tree('${ctx}/sysGroup/sysGroup!reloadDeptUserTree.action', 'dept_tree', 'tree_search', 'key', doSearch);
            tree.loadTree();
            $('#saveBtn').linkbutton();
            instAllTable();
            initEntUserTree();
            $('#treeWin').dialog({
            	 title: '企业树',
           	     width: 400,
            	 height: 600,
            	 closed: true,
            	 cache: false,
            	 modal: true,
            	 buttons: [{
            		 text: '确定',
            		 handler: function(){
            			 var watchers = $('#byWatchersTemp').val();
 	   					 var watchersShow = $('#backByWatchersShowTemp').val();
 	   					 var showTemp = $('#showTemp').val();
 	   					 $('#byWatchers').val(watchers);
	   					 $('#backByWatchersShow').val(watchersShow);
	   					 $('#showIndex').html(showTemp);
            			 $('#treeWin').dialog('close');
            		 }
            	 },{
            		 text: '取消',
            		 handler: function(){
            			 $('#treeWin').dialog('close');
            		 }
            	 }]
            });
        });
        
        function initEntUserTree(){
        	$.post('${ctx}/dept/deptAction!findAllEntDeptUser.action', function(data){
        		console.log(data);
            	data = eval('('+data+')');
            	console.log(data);
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
    	   					$('#byWatchersTemp').val(ids.join(','));
    	   					$('#backByWatchersShowTemp').val(idsShow.join(','));
    	   					var shownnn = "";
    	   				    for(var key in idsShow){
    	   					   shownnn += "<div class='nameDiv'>"+idsShow[key]+"</div>&nbsp;&nbsp;";
    	                    }
    	   					$('#showTemp').val(shownnn);
    	            	},
    	            	onClick : function(event,treeId,treeNode){
    	            		if(treeNode.children != null && treeNode.children != "undefined"){
    	            			return;
    	            		}
    	            		var id = treeNode.id;
    	            		$.ajax({
    	            			url:'${ctx}/dept/deptAction!findChildrenUser.action?id='+id,
    	            			success: function(data){
    	            				if(data==null || data == "" || data == "[]"){
    	            					return;
    	            				}
    	            				var sdata = eval('('+data+')');
    	            				deptTree1.addNodes(treeNode,sdata,false);
    	            			}
    	            		});
    	            	}
    	    	   	}
    	    	}, data);
            });
        }
        
        function getSerch() {
    		var t = $.fn.zTree.getZTreeObj('#dept_ent_tree');
    		console.log(t);

    		t.expandAllNode(true);
    		t.updateNodes(false);
    		
    		var value = $.trim($('#twkey').val());
    		var keyType = "name";
    		if (value === "") {
    			return;
    		}
    		
    		var filterNode = function(node) {
    			value = value.toUpperCase();
    			var py = String2Alpha(node.name);
    			if (py.indexOf(value) > -1) {
    				return true;
    			}

    			return (node.name.indexOf(value) > -1);
    		};
    		t.filterNodeList = zTree.getNodesByFilter(filterNode);
    		t.updateNodes(true);

    	}
        
        function panelShow(){
        	if ($('#dree_ent_search').css('display') == "none") {
    			$('#dree_ent_search').show();
    		} else {
    			$('#dree_ent_search').hide();
    		}
        }
        
    	function openWin(){
//     		var tree=$.fn.zTree.getZTreeObj("dept_ent_tree");
//     		var nodes=tree.getNodes();
//     		var divNames=$("#showIndex").find(".nameDiv");
//     		for(var i=0;i<nodes.length;i++){
//     			$(divNames).each(function(){
//     				if(nodes[i].name==$(this).text()){
//     					tree.checkNode(nodes[i], true, true);
//     				}
//     			});
//     		}
    		
    		$('#treeWin').dialog('open');
    	}

        // 更新或者是保存时调用的方法
        function saveForm() {
        	if($("#id").val()=='' || $('#watchersinput').val() == ''){
        		$.messager.alert('提示','请选择部门或人员');
        		return false;
        	}
            $('#deptForm').form({
                url:'${ctx}/watch/watchAction!saveOrUpdate.action',
                success:function (data) {
                	var data = eval('(' + data + ')');
                    if (data.operateSuccess) {
                    	$.messager.alert('成功',data.operateMessage); 
                    } else {
                    	$.messager.alert('失败',data.operateMessage); 
                    }
                    tree.loadTree();
                }
            });
            $('#deptForm').submit();
        }
    </script>
</head>
<body class="easyui-layout" id="main_layout">
	<div region="center" style="overflow: hidden;" border="true"
		plain="true" id="center">
		<div>
			<br> <a href="#" id="saveBtn" iconCls="icon-save"
				onclick="saveForm()">保存</a> <input type="hidden" id="byWatchersTemp">
			<input type="hidden" id="backByWatchersShowTemp"> <input
				type="hidden" id="showTemp">
		</div>
		<div>
			<form id="deptForm" method="post">
				<input type="hidden" id="id" name="watch.id" /> 
				<input type="hidden" id="watchersinput" name="watch.watchers" /> 
				<input type="hidden" id="name" name="watch.watchersShow" /> 
				<input type="hidden" id="backTables" name="watch.modules" /> 
				<input type="hidden" id="backMods" name="watch.modulesShow" /> 
				<input type="hidden" id="byWatchers" name="watch.byWatchers" /> 
				<input type="hidden" id="backByWatchersShow" name="watch.byWatchersShow" /> 
				<input type="hidden" name="watch.watchType" value="2" />
				<table cellspacing="1" border="0" bgcolor="#aed0ea" width="70%">
					<tr>
						<td class="table_nemu" width="20%">监管者</td>
						<td class="table_con" width="80%"><span id="watchers"></span>
						</td>
					</tr>
					<tr>
						<td class="table_nemu">监管单位</td>
						<td class="table_con">
							<div id="showIndex"
								style="width: 70%; height: 200px; border: 1px solid gray;"
								onclick="openWin()"></div>
						</td>
					</tr>
					<tr>
						<td class="table_nemu" width="20%">监管模块</td>
						<td class="table_con" width="80%"><span id="tables"></span></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<div id="treeWin">
		<div id="p" class="easyui-panel" title="企业树" fit="true" tools="#tt"
			border="false" plain="true">
			<div id="dree_ent_search" style="display: none">
				关键字:<input type="text" id="twkey" name="key" style="width: 80px;" /><a
					href="#" class="easyui-linkbutton" iconCls="icon-search"
					plain="true" onclick="getSerch();">搜索</a>
			</div>
			<div id="dept_ent_tree" class="ztree"></div>
		</div>
		<div id="tt">
			<a href="#" class="icon-treeSearch" onclick="javascript:panelShow();"
				title="搜索"></a>
		</div>
	</div>

	<div region="west" split="true" id="main_west" border="true"
		plain="true" style="width: 180px; padding1: 1px; overflow: hidden;">
		<div id="p" class="easyui-panel" title="监管部门" fit="true" tools="#tt"
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
				class="icon-treeAdd" onclick="javascript:tree.expandAllNode(false);"
				title="收缩所有"></a> <a href="#" class="icon-treeDelete"
				onclick="javascript:tree.expandAllNode(true);" title="展开所有"
				style="margin-right: 10px;"></a>
		</div>
	</div>
</body>
</html>