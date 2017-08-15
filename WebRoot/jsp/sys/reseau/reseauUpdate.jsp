<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监管网格</title>
<link rel="stylesheet" href="${ctx}/css/newCrud.css" type="text/css"></link>
<script type="text/javascript"
	src="${ctx}/jsp/sys/reseau/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/SelectUtil.js"></script>
<style type="text/css">
.table_nemu_new {
	text-align: center;
}

.nameDiv {
	background-color: rgb(78, 194, 241);
	margin-left: 5px;
	float: left;
	margin-top: 5px;
	padding-left: 10px;
	position: relative;
	padding-right: 23px;
}
</style>
<script type="text/javascript">
$('document').ready(function(){
	dataInit();
	initDiv();
	$('#deptUserTreeMain').dialog({
		title:'人员选择',
		width: 350,  
	    height: 400,  
	    closed: true,  
	    cache: false,  
	    modal: true  
	});
	$('#deptUserTreeSub').dialog({
		title:'人员选择',
		width: 350,  
	    height: 400,  
	    closed: true,  
	    cache: false,  
	    modal: true  
	});
	$('#deptUserTreeDouble').dialog({
		title:'人员选择',
		width: 350,  
	    height: 400,  
	    closed: true,  
	    cache: false,  
	    modal: true  
	});
	var title = $('#title_s').val();
	title = decodeURI(title) +"更新";
	$('#title').html(title);
});

function initDiv() {
	var maintake = $("input[name='reseau.mainTake']").val();
	var maintakeId = $("input[name='reseau.mainTakeId']").val();
	var subtake = $("input[name='reseau.subTake']").val().split(",");
	var subtakeId = $("input[name='reseau.subTakeId']").val().split(",");
	var doubletake = $("input[name='reseau.doubleTake']").val().split(",");
	var doubletakeId = $("input[name='reseau.doubleTakeId']").val().split(",");
	var treeMain = [];
	var treeSub = [];
	var treeDouble = [];
	var treeNode = {};
	treeNode.name =  maintake;
	treeNode.id= maintakeId;
	treeMain.push(treeNode);
	setShowNameDiv(treeMain,"showName","main");
	for (var i in subtake) {
		var node = {};
		node.name = subtake[i];
		node.id = subtakeId[i];
		treeSub.push(node);
	}
	setShowNameDiv(treeSub,"subName","sub");
	for (var i in doubletake) {
		var node = {};
		node.name = doubletake[i];
		node.id = doubletakeId[i];
		treeDouble.push(node);
	}
	setShowNameDiv(treeDouble,"doubleName","double");
}


var zTreeObj;
var zTreeNodes;
var zTreeData;
setting = {
	view : {
		selectedMulti : false
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
		onClick: zTreeOnClick
	}
};

checkSetting = {
		view : {
			selectedMulti : true
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
		check : {
			checkStyle:"checkbox",
			enable:true
		},
		callback: {
			onClick: null
		}
};
String.prototype.endWith = function(str) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	if (this.substring(this.length - str.length) == str)
		return true;
	else
		return false;
	return true;
};
function clearZero(str) {
	var temp = str.trim();
	while(temp.endWith('000')) {
		temp = temp.substring(0, temp.length-3);
	}
	return temp;
}
function filter(treeObj) {
	var addrMatch = '${valueMap["gc"].addrMatch}';
	var nodes = treeObj.getNodesByFilter(function(node) {
		if (typeof (node.account) == 'undefined') { //判断是部门
			if(node.gridconfig == null) {//理论上不可能为null
				return false;
			}
			var addrShort = clearZero(node.gridconfig.addrMatch);
			if(addrMatch.indexOf(addrShort)==-1) {
				return true;
			}
		}
		return false;
	});
	treeObj.hideNodes(nodes);
}	
function initTree() {
	zTreeNodes = zTreeData;
	zTreeObj = $.fn.zTree.init($("#dept_tree_main"), setting, zTreeNodes);
	filter(zTreeObj);
}
	function initCheckTree() {
		zTreeNodes = zTreeData;
		for (var i in zTreeNodes) {
			if (typeof (zTreeNodes[i].account) == 'undefined') {
				zTreeNodes[i].nocheck = true;
			}
		}
		zTreeObj = $.fn.zTree.init($("#dept_tree_sub"), checkSetting, zTreeNodes);
		filter(zTreeObj);
		zTreeObj = $.fn.zTree.init($("#dept_tree_double"), checkSetting, zTreeNodes);
		filter(zTreeObj);
	}
	
	//数据初始化
	function dataInit() {
		$.ajax({
			url:'${ctx}/reseau/reseauAction!getDeptUser',
			type:'POST',
			dataType: "json",
			success:function(data){
				if(data != null){
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
					zTreeData = data;
					initTree();
					initCheckTree();
				}
				else{
					alert("未查到相关数据！");
				}
			}
		});
	}

	//点击节点，显示详细信息
	function zTreeOnClick(event, treeId, treeNode) {
		if(typeof (treeNode.account) != "undefined") {
			//$('textArea[name="reseau.mainTake"]').text(treeNode.name);
			//$('input[name="reseau.mainTakeId"]').val(treeNode.id);
			var tree = [];
			tree.push(treeNode);
			setShowNameDiv(tree,"showName","main");
			$('#deptUserTreeMain').dialog('close');
		} else {
			$.messager.show({
				title : '警告',
				msg : '请选择用户',
				timeout : 2000,
				showType : 'slide'
			});
		}
	}
	
	function mainTakeChose() {
		$('#deptUserTreeMain').dialog({
			buttons:[]
		});
		$('#deptUserTreeMain').dialog('open');
	}
	
	function otherTakeChose() {
		var treeObj = $.fn.zTree.getZTreeObj("dept_tree_sub");
		getDivData(treeObj,"sub");
		$('#deptUserTreeSub').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					getNodes();
					}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#deptUserTreeSub').dialog('close');
					}
			}]
		});
		$('#deptUserTreeSub').dialog('open');
	}
	
	function doubleTakeChose(){
		var treeObj = $.fn.zTree.getZTreeObj("dept_tree_double");
		getDivData(treeObj,"doub");
		$('#deptUserTreeDouble').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					getNodes2();
					}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#deptUserTreeDouble').dialog('close');
					}
			}]
		});
		$('#deptUserTreeDouble').dialog('open');
	}
	
	
	function cancel() {
		history.go(-1);
	}

	
	function getNodes2 () {
		var treeObj = $.fn.zTree.getZTreeObj("dept_tree_double");
		var nodes = treeObj.getCheckedNodes(true);
		//$('textArea[name="reseau.doubleTake"]').text(persion.join(","));
		//$('input[name="reseau.doubleTakeId"]').val(persionId.join(","));
		setShowNameDiv(nodes,"doubleName","double");
		$('#deptUserTreeDouble').dialog('close');
	}
	
	function getNodes() {
		var treeObj = $.fn.zTree.getZTreeObj("dept_tree_sub");
		var nodes = treeObj.getCheckedNodes(true);
		setShowNameDiv(nodes,"subName","sub");
		//$('textArea[name="reseau.subTake"]').text(persion.join(","));
		//$('input[name="reseau.subTakeId"]').val(persionId.join(","));
		$('#deptUserTreeSub').dialog('close');
	}
	
	 function validateFunction() {
		 var flag = true;
		 $('input').each(function (){
			 if ($(this).val() == "") {
				flag = false;
			 } 
		 });
		 return flag;
	 }
	 
	function update() {
		var id = $('#id').val();
		var sub = [];
		var subT = [];
		var doub = [];
		var doubT = [];
		$(".nameDiv").each(function () {
			if ($(this).attr("type") == "main") {
				$('input[name="reseau.mainTakeId"]').val($(this).attr("data"));
				$('input[name="reseau.mainTake"]').val($(this).attr("name"));
			}
			if ($(this).attr("type") == "sub") {
				sub.push($(this).attr("data"));
				subT.push($(this).attr("name"));
			}
			if ($(this).attr("type") == "double") {
				doub.push($(this).attr("data"));
				doubT.push($(this).attr("name"));
			}
		});
		$('input[name="reseau.subTakeId"]').val(sub.join(","));
		$('input[name="reseau.subTake"]').val(subT.join(","));
		$('input[name="reseau.doubleTake"]').val(doubT.join(","));
		$('input[name="reseau.doubleTakeId"]').val(doub.join(","));
		
		if(validateFunction()== false) {
			$.messager.show({
				title : '提示',
				msg : "请正确配置用户",
				timeout : 2000,
				showType : 'slide'
			});
			return;
		}
		$('#addForm').form('submit', {
			url : '${ctx}/reseau/reseauAction!update',
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.operateSuccess) {
					$.messager.show({
						title : '成功',
						msg : data.operateMessage,
						timeout : 2000,
						showType : 'slide'
					});
					location.href = '${ctx}/reseau/reseauAction!view?id=' + id;
				} else {
					$.messager.show({
						title : '失败',
						msg : data.operateMessage,
						timeout : 2000,
						showType : 'slide'
					});
				}
			}
		});
	}
	
	 /**
	 人员展示
	 */
   function setShowNameDiv(tree,targetId,type){
   	if(tree != null) {
   		var div = $("#"+targetId).empty();
       	for(var i in tree) {
       			var a = $("<div class='nameDiv'>").attr("data",tree[i].id).attr("name",tree[i].name).attr("type",type);
	        		a.text( tree[i].name).css({"cursor":"pointer"});
	        		var b = $("<div class='panel-tool'>");
	        		var c = $("<a class='panel-tool-close'>").click(function (e) {
	        			$(this).parent().parent("div").remove();
	        			e.stopPropagation();
	        		}).attr("href","javascript:void(0)");
	        		b.append(c);
	        		a.append(b);
	        		div.append(a);
       	}
   	}
   }
	 
   function getDivData(ztreeObj,type) {
		ztreeObj.checkAllNodes(false);
		if (type == "doub") {
		$(".nameDiv").each(function () {
			if ($(this).attr("type") == "double") {
				var node = ztreeObj.getNodeByParam("id", $(this).attr("data"), null);
				if (node.getParentNode()) {
					ztreeObj.expandNode(node.getParentNode(), true, false, false);
				}
				ztreeObj.checkNode(node, true, true);
			}
		});
		}
		if (type == "sub") {
			$(".nameDiv").each(function () {
				if ($(this).attr("type") == "sub") {
					var node = ztreeObj.getNodeByParam("id", $(this).attr("data"), null);
					if (node.getParentNode()) {
						ztreeObj.expandNode(node.getParentNode(), true, false, false);
					}
					ztreeObj.checkNode(node, true, true);
				}
			});
		}
	}
</script>
</head>

<body style="background-color: white; overflow: hidden">
	<center>
		<div>
			<br />
		</div>
		<form id="addForm" method="post" style="width: 900px; height: 450px;">
			<input id="id" type="hidden" name="reseau.id" value="${reseau.id}" />
			<input id="title_s" type="hidden" value="${param.title}" /> <input
				type="hidden" name="reseau.mainTakeId" value="${reseau.mainTakeId }" />
			<input type="hidden" name="reseau.mainTake"
				value="${reseau.mainTake }" /> <input type="hidden"
				name="reseau.subTakeId" value="${ reseau.subTakeId}" /> <input
				type="hidden" name="reseau.subTake" value="${ reseau.subTake}" /> <input
				type="hidden" name="reseau.doubleTakeId"
				value="${reseau.doubleTakeId}" /> <input type="hidden"
				name="reseau.doubleTake" value="${reseau.doubleTake}" />
			<!--	地区码	-->
			<div style="margin: 0 auto;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px"
					style="font-size: 12px;" cellspacing="1" border="0"
					bgcolor="#aed0ea" width="85%">
					<thead>
						<tr>
							<th class="table_th" colspan="4"
								style="background-color: #f1f6f9; height: 35px; margin: 0; padding-bottom: 0"><b
								id="title"></b></th>
						</tr>
					</thead>
					<tr>
						<td class="table_nemu_new"
							style="background-color: #f1f6f9; width: 20%">&nbsp;主要负责人:</td>
						<td class="table_con" style="width: 25%;">
							<div id="showName" onclick="mainTakeChose()"
								style="width: 99%; border: 1px #efefef solid; height: 75px; overflow-y: auto;">
							</div>
						</td>
						<td class="table_nemu_new"
							style="background-color: #f1f6f9; width: 20%">&nbsp;安全职责:</td>
						<td class="table_con"><textarea class="easyui-validatebox"
								data-options="required:true" style="width: 75%; height: 80px;"
								name="reseau.mainDuty">${reseau.mainDuty}</textarea></td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9">&nbsp;分管负责人:</td>
						<td class="table_con"><div id="subName"
								onclick="otherTakeChose()"
								style="width: 99%; border: 1px #efefef solid; height: 75px; overflow-y: auto;"></div>
						</td>
						<td class="table_nemu_new" style="background-color: #f1f6f9">&nbsp;安全职责:</td>
						<td class="table_con"><textarea class="easyui-validatebox"
								data-options="required:true" style="width: 75%; height: 80px;"
								name="reseau.subDuty">${reseau.subDuty}</textarea></td>
					</tr>

					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9">一岗双责负责人:</td>
						<td class="table_con"><div id="doubleName"
								onclick="doubleTakeChose()"
								style="width: 99%; border: 1px #efefef solid; height: 75px; overflow-y: auto;"></div>
						</td>
						<td class="table_nemu_new" style="background-color: #f1f6f9">&nbsp;安全职责:</td>
						<td class="table_con"><textarea class="easyui-validatebox"
								data-options="required:true" style="width: 75%; height: 80px;"
								name="reseau.doubleDuty">${reseau.doubleDuty}</textarea></td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9">&nbsp;属地监管责任:</td>
						<td class="table_con" colspan="3"><textarea
								class="easyui-validatebox" name="reseau.duties"
								data-options="required:true" style="width: 500px; height: 85px;">${reseau.duties}</textarea>
						</td>
				</table>
				<br>
				<button type="button" value="" onclick="update()" class="save"></button>
				<button type="button" value="" onclick="cancel()" class="back"></button>
			</div>
		</form>

		<div id="deptUserTreeMain">
			<div id="dept_tree_main" class="ztree"></div>
		</div>
		<div id="deptUserTreeSub">
			<div id="dept_tree_sub" class="ztree"></div>
		</div>
		<div id="deptUserTreeDouble">
			<div id="dept_tree_double" class="ztree"></div>
		</div>
	</center>
</body>
</html>
