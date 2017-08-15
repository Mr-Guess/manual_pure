<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>网格监管</title>
<link href="${ctx}/templets/admin/menuconfig.css" rel="stylesheet"
	type="text/css" />
<link
	href="${ctx}/resources/js/jquery/easyui-1.3/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
<%-- <link rel="STYLESHEET" type="text/css" href="${ctx}/dhtml/dhtmlxtree.css" /> --%>
<%-- <script type="text/javascript" src="${ctx}/dhtml/dhtmlxcommon.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/dhtml/dhtmlxtree.js"></script> --%>
<link rel="stylesheet"
	href="${ctx}/resources/js/jquery/zTree-3.5/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/resources/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jquery/easyui-1.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jquery/zTree-3.5/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jquery/zTree-3.5/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jquery/zTree-3.5/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jquery/zTree-3.5/jquery.ztree.exhide-3.5.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<style type="text/css">
<!--
此部分的样式定义被移至“templets
/template_2/css/menuconfig
.css”
 
-->
</style>
</head>
<body>
	<div class="divMain">
		<div id="titleBar">
			<div class="barLeft">
				<span>网格监管</span>
			</div>
		</div>
		<div class="con_list">
			<!--firefox下的出现浮动溢出，解决办法是CSS清除浮动，请保留下面这个clear作用的层-->
			<div style="clear: both;"></div>
			<table class="tdLeftList">
				<tr>
					<td class="tdModeFunc">
						<div class="divModeFunc">
							<a href="javascript:void(0);" onclick="openAll()" title="全部展开">全部展开</a>
							<a href="javascript:void(0);" onclick="closeAll();" title="全部收缩">全部收缩</a>
						</div>
					</td>
				</tr>
				<tr>
					<td class="tdLeftFunc" align="left" width="260px" height="360px"
						style="margin: 0px;" valign="top">
						<div id="search"
							style="width: 260px; height: 40px; border: 0px solid Silver; display: none;">
							关键字：<input id="key" type="text"><input type="button"
								value="搜索" onclick="keySearch()">
						</div>
						<!-- 顶部搜索 -->
						<div id="grid_tree" class="ztree"
							style="width: 260px; height: 360px; border: 0px solid Silver; overflow-y: scroll;"></div>
						<!-- 左侧菜单树 -->
						<div style="text-align: center; margin-top: 5px; width: 260px;">
							<shiro:hasPermission name="${menuId}:up">
								<input id="btnUp" type="button" value="上移" class="b1"
									title="平级节点的向上移动" onclick="itemUp()" />
							</shiro:hasPermission>
							<shiro:hasPermission name="${menuId}:down">
								<input id="btnDown" type="button" value="下移" class="b1"
									title="平级节点的向下移动" onclick="itemDown()" />
							</shiro:hasPermission>
							<input id="btnSearch" type="button" value="搜索" class="b1"
								title="节点搜索" onclick="itemSearch()" />
							<!--                 <input id="btnSaveTree" type="button" value="保存顺序" class="b1" title="移动节点位置之后，需要点此按钮保存整颗树的各节点位置!" onclick="saveTree()"/> -->
						</div>
					</td>
					<td class="tdRightFunc" valign="top">
						<table class="tableFunc">
							<tr>
								<td><shiro:hasPermission name="${menuId}:addMenu">
										<input id="btnAddItem1" type="button" value="增加平级网格"
											class="btn" title="增加平级网格" onclick="addItem();" />&nbsp;
					</shiro:hasPermission> <shiro:hasPermission name="${menuId}:addSubMenu">
										<input id="btnAddItem2" type="button" value="增加选中项子网格"
											class="btn" title="增加选中项子网格" onclick="addItemChild();" />&nbsp;
					</shiro:hasPermission> <shiro:hasPermission name="${menuId}:add">
										<input id="btnSave" type="button" value="保存" class="btn"
											title="保存节点信息" onclick="saveItem();" />&nbsp; 
					</shiro:hasPermission> <shiro:hasPermission name="${menuId}:delete">
										<input id="btnDel" type="button" value="删除" class="btn"
											title="删除选中节点" onclick="delItem()" />&nbsp;
					</shiro:hasPermission></td>
							</tr>
						</table>
						<form id="addForm" method="post">
							<div class="divRightForm">
								<table class="tableInfo" id="TABLE1">
									<tr>
										<td class="tdInfo1">网格名称：</td>
										<td class="tdInfo2"><input type="text" ID="wgmc"
											name="gridconfig.wgmc" style="width: 70%" /><span
											class="colorStar">*</span></td>
									</tr>
									<tr>
										<td class="tdInfo1">网格等级：</td>
										<td class="tdInfo2"><input id="gridLevel"
											name="gridconfig.gridLevel" class="easyui-combobox" /></td>
									</tr>
									<tr>
										<td class="tdInfo1">行政区划:</td>
										<td class="tdInfo2" valign="top"><input id="treeAddr"
											type="text" onclick="showMenu(this,'menuAddrType')"
											class="input_text" readonly="readonly" style="width: 250px" />
											<input id="addr1" type="hidden" name="gridconfig.addr1" /> <input
											id="addr2" type="hidden" name="gridconfig.addr2" /> <input
											id="addr3" type="hidden" name="gridconfig.addr3" /> <input
											id="addr4" type="hidden" name="gridconfig.addr4" /> <input
											type="hidden" id="gridAddr" name="gridconfig.addrLast" /> <input
											type="hidden" id="addrMatch" name="gridconfig.addrMatch" />
										</td>
									</tr>

									<tr>
										<td class="tdInfo1">是否启用：</td>
										<td class="tdInfo2"><select ID="gridswitch"
											name="gridconfig.gridSwitch">
												<option Value="1" Selected="selected">是</option>
												<option Value="0">否</option>
										</select></td>
									</tr>

									<tr>
										<td class="tdInfo1">网格简介：</td>
										<td class="tdInfo2"><textarea ID="gridinfo"
												name="gridconfig.gridInfo" style="width: 70%;" rows="9"></textarea></td>
									</tr>
								</table>
							</div>
							<input type="hidden" name="gridconfig.parentId" id="parentId" />
							<input type="hidden" name="gridconfig.id" id="id" />

						</form>
					</td>
				</tr>
			</table>
			<br />
		</div>
	</div>

	<div id="search" style="border: 1px #7F9DB9 solid; display: none;"></div>
	<div id="menuAddrType" class="menuContent"
		style="display: none; position: absolute; color: #2f332a;">
		<ul id="treeAddrType" class="ztree"
			style="margin-top: 0; width: 220px; height: 198px; background: #f0f6e4; overflow: auto;">
		</ul>
	</div>
</body>
<script type="text/javascript">


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

	$(document).ready(function dataInit() {
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
				}
				else{
					//alert("未查到相关数据！");
					$.messager.alert('提示','未查到相关数据！','info');
				}
			}
		});
		$('#gridLevel').combobox({
        	url:"${ctx}/data/dataAction!findGridLevel",
        	valueField:'dataCode',
        	textField:'dataName',
    		editable:false,
    		panelHeight:'auto'
        });
		
		$.post('${ctx}/data/dataAction!findDataByTypeFirstLevel?typeId=0b8415e08bf3474686e643318c0a497c',function(data){
			var nodes = eval('(' + data + ')');
			for(var i in nodes){
				nodes[i].isParent = true;
			}
			$.fn.zTree.init($("#treeAddrType"), {
				async: {
					enable: true,
					url: "${ctx}/data/dataAction!findChildList",
					autoParam: ["dataCode=parentId"],
					dataFilter: function ajaxDataFilter(treeId, parentNode, responseData) {
					    if (parentNode.level < 2) {
					        for(var i =0; i < responseData.length; i++) {
					          responseData[i].isParent = true;
					        }
					      }
					      return responseData;
					  }
				},
				data: {
					key: {
						name: "dataName"
					},
					simpleData: {
						enable: true,
						idKey: "dataCode",
						pIdKey: "parentId",
						rootPId: "-1"
					}
				},
				callback: {
					onClick: function(e, treeId, treeNode) {
						var v = '';
						var pnode = treeNode;
						var i=treeNode.level+1;
						while(i>0) {
							$('#addr'+i).val(pnode.dataCode);
							v = pnode.dataName + ' ' + v;
							pnode = pnode.getParentNode();
							i--;
						}
						$("#treeAddr").val(v);
						$("#gridAddr").val(treeNode.dataCode);
					}
				},
				view: {
					showIcon: false,
					selectedMulti:false
				}
			}, nodes);
			var zTree2 = $.fn.zTree.getZTreeObj("treeAddrType");
			zTree2.reAsyncChildNodes(zTree2.getNodes()[0], "refresh");
		});
	});
	
	//数据初始化
	function dataInit(id) {
		$.ajax({
			url:'${ctx}/gridconfig/gridconfigAction!initTree',
			type:'POST',
			dataType: "json",
			success:function(data){
				if(data != null){
					zTreeNodes = data;
					zTreeObj = $.fn.zTree.init($("#grid_tree"), setting, zTreeNodes);
					var nodes = zTreeObj.getNodesByParam("id", id, null);
					if(nodes.length > 0){
						zTreeObj.expandNode(nodes[0], true,false,false);
					}
				}
				else{
					//alert("未查到相关数据！");
					$.messager.alert('提示','未查到相关数据！','info');
					return;
				}
			}
		})
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
	   // alert(treeNode.id + ", " + treeNode.gridSwitch);
	   var wgmc = treeNode.wgmc;
	   var gridSwitch = treeNode.gridSwitch;
	   var gridInfo = treeNode.gridInfo;
	   var id = treeNode.id;
	   var parentId = treeNode.parentId;
	   var level = treeNode.gridLevel;
	   var addr1 = showNull(treeNode.addr1Text);
	   var addr2 = showNull(treeNode.addr2Text);
	   var addr3 = showNull(treeNode.addr3Text);
	   var addr4 = showNull(treeNode.addr4Text);
	   
	   $('#wgmc').val(wgmc);
	   $('#gridswitch').val(gridSwitch);
	   $('#gridinfo').text(setNull(gridInfo));
	   $('#id').val(id);
	   $('#parentId').val(parentId);
	   $('#gridLevel').combobox('setValue',level);
	   $('#treeAddr').val(addr1+' '+addr2+' '+addr3+' '+addr4);
	   
	   //以下为初始化隐藏域
	   $('#addr1').val(treeNode.addr1);
	   $('#addr2').val(treeNode.addr2);
	   $('#addr3').val(treeNode.addr3);
	   $('#addr4').val(treeNode.addr4);
	   $('#gridAddr').val(treeNode.addrLast);
	   
	};
	
	//显示信息时去掉null
	function setNull(str){
		if(str == null || str == "null" || str == "undefined"){
			str = '';
		}
		return str;
	}
	
	var itemType = '0';//'0'表示增加平级菜单,'1'表示添加子项菜单
	//增加平级菜单
	function addItem(){
		var treeObj = $.fn.zTree.getZTreeObj("grid_tree");
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length < 1){
			//alert("未选中节点");
			$.messager.alert('提示','未选中节点','info');
			return;
		}
		clearForm(nodes[0].parentId);
		itemType = '0';
	}
	
	//增加子项菜单
	function addItemChild(){
		var treeObj = $.fn.zTree.getZTreeObj("grid_tree");
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length < 1){
			//alert("未选中节点");
			$.messager.alert('提示','未选中节点','info');
			return;
		}
		clearForm(nodes[0].id);
		itemType = '1';
	}
	
	
	//增加菜单时清空表单选项
	function clearForm(pid) {
		$('#wgmc').val('');
		$('#gridswitch').val('1');
		$('#gridinfo').text('');
		$('#id').val('');
		$('#parentId').val(pid);
		$('#gridLevel').combobox('setValue','');
		$('#treeAddr').val('');
	}
	
	//保存菜单
	function saveItem(){
		var treeObj = $.fn.zTree.getZTreeObj("grid_tree");
		var nodes = treeObj.getSelectedNodes();
		var id = $('#id').val();
		if(nodes.length < 1){
			//alert("未选中节点");
			$.messager.alert('提示','未选中节点','info');
			return;
		}
		if(id == ''){
			$('#addForm').form('submit',{
				url:'${ctx}/gridconfig/gridconfigAction!add',
				onSubmit: function(){
					var wgmc = $('#wgmc').val();
					if(wgmc==null || wgmc == ''){
						//alert("网格名称不能为空");
						$.messager.alert('提示','网格名称不能为空!','info');
						return false;
					}
					var gridLevel = $('#gridLevel').combobox('getValue');
					if(gridLevel != null && gridLevel != '' ){
						var node = '#addr'+gridLevel;
						$('#addrMatch').val($(node).val());
					}
				},
	            success:function(data) {
	            	var data = eval('(' + data + ')');
	                if (data.operateSuccess ) {
	                	//alert("添加成功");
	                	$.messager.alert('提示','添加成功','info');
	                	var nodeId = '' ;
	                	if(itemType == '0'){
	                		nodeId = nodes[0].getParentNode().id;
	                	}
	                	else{
	                		nodeId = nodes[0].id;
	                	}
	                	dataInit(nodeId);
	                } else {
	                	//alert("添加失败");
	                	$.messager.alert('提示','添加失败','info');
	                }
	            }
	        });
		}
		else{
			$('#addForm').form('submit',{
				url:'${ctx}/gridconfig/gridconfigAction!update',
				onSubmit: function(){
					var wgmc = $('#wgmc').val();
					if(wgmc==null || wgmc == ''){
						//alert("网格名称不能为空");
						$.messager.alert('提示','网格名称不能为空!','info');
						return false;
					}
					var level = $('#gridLevel').combobox('getValue');
					if(level != null && level != '' ){
						var node = '#addr'+level;
						$('#addrMatch').val($(node).val());
					}
				},
	            success:function(data) {
	            	var data = eval('(' + data + ')');
	                if (data.operateSuccess) {
	                	//showMsg("修改成功！");
	                	$.messager.alert('提示','修改成功！','info');
	                	dataInit(nodes[0].getParentNode().id);
	                } else {
	                	//showMsg("修改失败！");
	                	$.messager.alert('提示','修改失败！','info');
	                }
	            }
	        });
		}
	}
	
	
	//删除节点
	function delItem(){
		var treeObj = $.fn.zTree.getZTreeObj("grid_tree");
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length < 1){
			//showMsg("请先选中节点");
			$.messager.alert('提示','请先选中节点','info');
			return;
		}
		var id =nodes[0].id; 
		$.ajax({
			url:'${ctx}/gridconfig/gridconfigAction!delete?ids='+id,
			type:'get',
			success:function(data){
				var data = eval('(' + data + ')');
                if (data.operateSuccess) {
                	//showMsg("删除成功");
                	$.messager.alert('提示','删除成功','info');
                	treeObj.removeNode(nodes[0]);
                } else {
                	//showMsg("删除失败");
                	$.messager.alert('提示','删除失败','info');
                }
			}
		});
	}
	
	//上移
	function itemUp(){
		var zTree = $.fn.zTree.getZTreeObj("grid_tree");
		var nodes = zTree.getSelectedNodes();
		if(nodes.length < 1){
			//showMsg("请先选中节点");
			$.messager.alert('提示','请先选中节点','info');
			return;
		}
		var node = nodes[0];
		var index = zTree.getNodeIndex(node);
		if(index == 0){
			//showMsg("已在所属节点首位");
			$.messager.alert('提示','已在所属节点首位','info');
			return;
		}
		var preNode = node.getPreNode();
		zTree.moveNode(preNode, node, "prev",true);
		var str = node.id+"-"+preNode.gridOrder+","+preNode.id+"-"+node.gridOrder;
		$.ajax({
			url:'${ctx}/gridconfig/gridconfigAction!change?ids='+str,
			type:'get'
		});
		var temp = node.gridOrder;
    	node.gridOrder = preNode.gridOrder;
    	preNode.gridOrder = temp;
    	zTree.updateNode(node);
    	zTree.updateNode(preNode);
	}
	
	//下移
	function itemDown(){
		var zTree = $.fn.zTree.getZTreeObj("grid_tree");
		var nodes = zTree.getSelectedNodes();
		if(nodes.length < 1){
			//showMsg("请先选中节点");
			$.messager.alert('提示','请先选中节点','info');
			return;
		}
		var node = nodes[0];
		var index = zTree.getNodeIndex(node);
		var parent = node.getParentNode();
		var childs = parent.children;
		var len = childs.length-1;
		if(index == len){
			//showMsg("已在所属节点末位");
			$.messager.alert('提示','已在所属节点末位','info');
			return;
		}
		var nextNode = node.getNextNode();
		zTree.moveNode(node,nextNode, "prev",true);
		var str = node.id+"-"+nextNode.gridOrder+","+nextNode.id+"-"+node.gridOrder;
		$.ajax({
			url:'${ctx}/gridconfig/gridconfigAction!change?ids='+str,
			type:'get'
		});
		var temp = node.gridOrder;
    	node.gridOrder = nextNode.gridOrder;
    	nextNode.gridOrder = temp;
    	zTree.updateNode(node);
    	zTree.updateNode(nextNode);
		
	}
	//打开搜索框
	function itemSearch(){
		var search = $('#search');
		if(search.css('display')== 'none'){
			search.show();
		}
		else{
			search.hide();
		}
	}
	
	var count = 0;//模糊查询时的节点计数
	var keyMsg = '';//查询时key值得存放变量
	//进行搜索节点
	function keySearch(){
		var key = $('#key').val().replace(/(^\s*)|(\s*$)/g, "");
		if(key == null || key == '' || key.length == 0){
			showMsg("请填写正确的关键字！");
			$.messager.alert('提示','请填写正确的关键字！','info');
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
			//showMsg("未查到相关数据");
			$.messager.alert('提示','未查到相关数据！','info');
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
	
	function showMsg(info){
		$.messager.show({
            title:'温馨提示',
            msg:info,
            timeout:2000,
            showType:'show'

        })
	}
	
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
	
	function showNull(str){
		if(str == null || str=='undefined' || str == ''){
			str=" ";
		}
		return str;
	}
</script>
</html>
