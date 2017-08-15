<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网格配置查看</title>
<script type="text/javascript">
	$(document).ready(dataInit());
	var zTreeObj;
	var zTreeNodes;
	
	setting = {
		view : {
			selectedMulti : false
		},
		data : {
			key : {
				checked : "isChecked",
				name : "wgmc"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId",
				rootPId : "-1"
			}
		},
		callback: {
			onClick: zTreeOnClick
		}
	};
	//数据初始化
	function dataInit() {
		$.ajax({
			url:'${ctx}/gridconfig/gridconfigAction!initTree',
			type:'POST',
			dataType: "json",
			success:function(data){
				if(data != null){
					zTreeNodes = data;
					zTreeObj = $.fn.zTree.init($("#grid_tree"), setting, zTreeNodes);
					var nodes = zTreeObj.getNodes();
					zTreeObj.expandNode(nodes[0], true, false, false);
					zTreeObj.selectNode(nodes[0]);
					$('#mainframe2').attr("src","${ctx}/reseau/reseauAction!viewReadOnly?id="+nodes[0].id+"&title="+encodeURI(encodeURI(nodes[0].wgmc))+"&menuId=${menuId}");
				}
				else{
					alert("未查到相关数据！");
				}
			}
		});
	}
	
	//展开所有
	function openAll(){
		var treeObj = $.fn.zTree.getZTreeObj("grid_tree");
		treeObj.expandAll(true);
	}
	
	//折叠所有
	function closeAll(){
		var treeObj = $.fn.zTree.getZTreeObj("grid_tree");
		treeObj.expandAll(false);
	}
	
	//点击节点，显示详细信息
	function zTreeOnClick(event, treeId, treeNode) {
	   var wgmc = treeNode.wgmc;
	   var id = treeNode.id;
	   $('#mainframe2').attr("src","${ctx}/reseau/reseauAction!viewReadOnly?id="+id+"&title="+encodeURI(encodeURI(wgmc))+"&menuId=aykj");
	}
	
	var count = 0;//模糊查询时的节点计数
	var keyMsg = '';//查询时key值得存放变量
	//进行搜索节点
	function keySearch(){
		var key = $('#key').val().replace(/(^\s*)|(\s*$)/g, "");
		if(key == null || key == '' || key.length == 0){
			alert("请填写正确的关键字！");
			return;
		}
		if(keyMsg != key){
			count = 0;
		}
		searchNode(key);
	}
	
	//节点搜索，并展开
	function searchNode(key){
		keyMsg = key;
		var treeObj = $.fn.zTree.getZTreeObj("grid_tree");
		var nodes = treeObj.getNodesByParamFuzzy("wgmc", key);
		if(nodes.length < 1){
			alert("未查到相关数据");
			return ;
		}
		for(var i = 0;i<nodes.length;i++){
			if(i==count){
				treeObj.expandNode(nodes[i].getParentNode(), true, false, false);
				treeObj.selectNode(nodes[i]);
			}
		}
		count++;
	}
	
</script>
</head>
<body class="easyui-layout">
	<div border="true" plain="true" data-options="region:'west',split:true"
		style="width: 200px;">
		<input id="key" type="text" class="divsearch" style="width: 70%;" /><input
			type="button" value="查询" onclick="keySearch()" class="linkbutton">
		<!-- 顶部搜索 -->
		<div id="grid_tree" class="ztree"></div>
	</div>
	<div data-options="region:'center'" style="padding: 5px;">
		<iframe id="mainframe2" name="mainframe2" width="99%"
			style="margin-top: 0px;" frameborder="0" height="99%"></iframe>
	</div>
</body>
</html>