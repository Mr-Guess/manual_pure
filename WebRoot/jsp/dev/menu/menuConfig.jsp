<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<html>
<head>
<title>菜单树维护</title>
<link href="${ctx}/templets/admin/menuconfig.css" rel="stylesheet"
	type="text/css" />
<link rel="STYLESHEET" type="text/css"
	href="${ctx}/dhtml/dhtmlxtree.css" />
<script type="text/javascript" src="${ctx}/dhtml/dhtmlxcommon.js"></script>
<script type="text/javascript" src="${ctx}/dhtml/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<%-- <script type="text/javascript" src="${ctx}/resources/js/jquery/jquery-1.7.2.js"></script> --%>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/CharacterUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<style type="text/css">
<!--
此部分的样式定义被移至“templets
/template_2/css/menuconfig
.css”
 
-->
</style>
</head>
<body onload="init()">
	<div class="divMain">
		<div id="titleBar">
			<div class="barLeft">
				<span>菜单树维护</span>
			</div>
		</div>
		<div class="con_list">
			<!--firefox下的出现浮动溢出，解决办法是CSS清除浮动，请保留下面这个clear作用的层-->
			<div style="clear: both;"></div>
			<table class="tdLeftList">
				<tr>
					<td class="tdModeFunc">
						<div class="divModeFunc">
							<a href="javascript:void(0);" style="height: 30px;" class="icon-treeSearch"
								onclick="javascript:changeTreeSearchPanelShow();" title="搜索">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							<a href="javascript:void(0);" class="icon-treeRefresh"
								onclick="javascript:loadTree();" title="刷新">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							<a href="javascript:void(0);" class="icon-treeAdd"
								onclick="javascript:tree.closeAllItems('MenuTree');"
								title="收缩所有">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a> <a href="javascript:void(0);"
								class="icon-treeDelete"
								onclick="javascript:tree.openAllItems('MenuTree');" title="展开所有"
								style="margin-right: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>

							<!-- 		<a href="javascript:void(0);" -->
							<!-- 			onclick="tree.openAllItems('MenuTree');" title="全部展开">全部展开</a> <a -->
							<!-- 			href="javascript:void(0);" -->
							<!-- 			onclick="tree.closeAllItems('MenuTree');" title="全部收缩">全部收缩</a> -->
						</div>
					</td>
				</tr>
				<tr>
					<td class="tdLeftFunc" align="left" width="260px" height="360px"
						style="margin: 0px;" valign="top">
						<div id="tree_search" style="display: none">
							关键字:&nbsp;&nbsp;<input type="text" id="key" name="key"
								style="width: 80px; margin-top: 3px; height: 25px;" />&nbsp;&nbsp;<a
								href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"
								plain="true" onclick="tree.searchNode();">搜索</a>
						</div>
						<div id="treeboxbox_tree"
							style="width: 260px; height: 360px; border: 0px solid Silver;"></div>
						<!-- 左侧菜单树 -->
						<div style="text-align: center; margin-top: 5px; width: 260px;">
							<input id="btnUp" type="button" value="上移" class="b1"
								title="平级节点的向上移动" onclick="itemUp()" /> <input id="btnDown"
								type="button" value="下移" class="b1" title="平级节点的向下移动"
								onclick="itemDown()" /> <input id="btnSaveTree" type="button"
								value="保存顺序" class="b1" title="移动节点位置之后，需要点此按钮保存整颗树的各节点位置!"
								onclick="saveTree()" />
							<!--  <input id="btnDel" type="button" value="删除" class="b1"  title="删除选中节点" />-->
						</div>
					</td>
					<td class="tdRightFunc" valign="top">
						<table class="tableFunc">
							<tr>
								<td><input id="btnAddItem1" type="button" value="增加平级菜单"
									class="btn" title="增加平级菜单"
									onclick="addItem(); changeColor(this,'item1');" />&nbsp; <input
									id="btnAddItem2" type="button" value="增加选中项子菜单" class="btn"
									title="增加选中子菜单"
									onclick="addItemChild();changeColor(this,'item2');" />&nbsp; <input
									id="btnSave" type="button" value="保存" class="btn"
									title="保存节点信息" onclick="saveJd();" />&nbsp; <input id="btnDel"
									type="button" value="删除" class="btn" title="删除选中节点"
									onclick="delJd()" />&nbsp; <input id="btnManager"
									type="button" value="菜单权限管理" class="btn" title="菜单权限按钮的维护"
									onclick="menuManager();" /></td>
							</tr>
						</table>
						<form id="form1" method="post" enctype="multipart/form-data">
							<div class="divRightForm">
								<table class="tableInfo" id="TABLE1">
									<tr>
										<td class="tdInfo1">菜单名称：</td>
										<td class="tdInfo2"><input type="text" ID="txtCdMc"
											name="menu_name" style="width: 70%" /><span
											class="colorStar">*</span></td>
									</tr>

									<tr>
										<td class="tdInfo1">菜单链接：</td>
										<td class="tdInfo2"><input type="text" ID="txtCdUrl"
											name="menu_url" style="width: 70%" /></td>
									</tr>

									<tr>
										<td class="tdInfo1">显示方式：</td>
										<td class="tdInfo2"><select ID="ddlTarget"
											name="menu_type">
												<option Value="0">新窗口</option>
												<option Value="1" Selected="selected">右侧显示</option>
										</select></td>
									</tr>
									<tr>
										<td class="tdInfo1">菜单类型：</td>
										<td class="tdInfo2"><select ID="ddlKind" name="menu_kind">
												<option Value="12" Selected="selected">公共菜单</option>
												<option Value="1">政府菜单</option>
												<option Value="2">企业菜单</option>
										</select></td>
									</tr>
									<tr>
										<td class="tdInfo1">对应模块：</td>
										<td class="tdInfo2"><select ID="ddlModule"
											name="menu_module">
												<option value="" Selected="selected">--</option>
												<option Value="01">企业综合监管</option>
												<option Value="02">危险源监管</option>
												<option Value="03">应急预案管理</option>
												<option Value="04">日常综合办公</option>
												<option Value="05">系统管理</option>
										</select></td>
									</tr>
									<tr>
										<td class="tdInfo1">是否启用：</td>
										<td class="tdInfo2"><select ID="ddlswitch"
											name="menu_switch">
												<option Value="1" Selected="selected">是</option>
												<option Value="0">否</option>
										</select></td>
									</tr>
									<tr>
										<td class="tdInfo1">模块表名：</td>
										<td class="tdInfo2"><input type="text" width="70%"
											id="menuTableName" name="menuTableName" readonly="readonly"/></td>
									</tr>
									<tr>
										<td class="tdInfo1">菜单图标： </td>
										<td class="tdInfo2">
											<img id="preview"  src="" width="30px" height="30px" style="float: left;" />
											<input type="hidden" value="" name="iconNo" id="iconNo"/> 
											<div style="padding-top:3px;float: left;">&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" onclick="getImg();"/>选择图标</a></div>
										</td>
									</tr>
									<tr>
										<td class="tdInfo1">菜单简介：</td>
										<td class="tdInfo2"><textarea ID="txtCdJj"
												name="menu_info" style="width: 70%;" rows="9"></textarea></td>
									</tr>
								</table>
							</div>
							<input type="hidden" name="menu_sjbh" id="menu_sjbh" /> <input
								type="hidden" name="operateFlag" id="operateFlag" value="update" />
							<input type="hidden" name="menu_order" id="menu_order" /> <input
								type="hidden" name="menu_jb" id="menu_jb" /> <input
								type="hidden" name="menu_path" id="menu_path" /> <input
								type="hidden" name="menu_id" id="menu_id" /> <input
								type="hidden" name="addChildItem" id="addChildItem"
								value="update" /> <input type="hidden" name="selected_menuid"
								id="selected_menuid" />

						</form>
					</td>
				</tr>
			</table>
			<br />
		</div>
	</div>
	<div id="dd" style="overflow: hidden;">
		<iframe  src="${ctx}/jsp\sys\icon\iconView.jsp" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
	</div>
	
	
		<div id="sheets">
			<iframe name="sheet" src="${ctx}/jsp/sys/create/sheetView.jsp" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
		</div>
</body>
<script type="text/javascript">
	
	$(function(){
		$('#dd').dialog({
			title:'选择图标',
			modal:true,
			draggable:false,
			maximizable: true, 
			width:600,
			height:300,
			buttons:[
				{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#dd').dialog('close');
					}
			}]
		});
		$('#dd').dialog('close');
		$('#sheets').dialog({
			title:'关联表',
			modal:true,
			draggable:false,
			maximizable: false, 
			width:800,
			height:500,
			buttons:[
			    {
				text:'确定',
				iconCls:'icon-save',
				handler:function(){	
					var sheets=sheet.window.change();
					/*if(sheets==null){
						$.messager.alert("警告","请至少选择一行");
					}else{*/
						$('#menuTableName').val(sheets);
						$('#sheets').dialog('close');
				}
				},
				{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#sheets').dialog('close');
					}
			}]
		});
		$('#sheets').dialog('close');
		$('#menuTableName').click(function(){
			$('#sheets').dialog('open');
		});
	});
	function getIconNo(iconNo){
		$("#preview").attr('src','showIcon.action?type=1&menuId='+iconNo);
		$('#iconNo').val(iconNo);
		$('#dd').dialog('close');
	}
	function getImg(){
		$('#dd').dialog('open');
	}	
 function menuManager(){
	 var menuId = $("#menu_id").val();
	 var menuName = $("#txtCdMc").val();
	 //if($("#menu_jb").val()!=3){
		 //alert("请选择三级菜单！");
		// return false;
		 
	// }else{
		 //window.location.href="${ctx}/menuOpt/menuOpt!list.action?menuId="+menuId+"&menuName="+menuName;
		 //修改方式，wgw修改于2012-12-25
	 var form = $("<form>").attr("action","${ctx}/jsp/dev/menuOpt/menuOptList.jsp").attr("method", "post").appendTo($("body"));
	 form.append($("<input name='menuId' />").attr("value", menuId));
	 form.append($("<input name='menuName' />").attr("value", menuName));
	 form.submit();
// 	 window.location.href="${ctx}/jsp/dev/menuOpt/menuOptList.jsp?menuId="+menuId+"&menuName="+menuName;
	// }
 }

 var treeboxbox_tree = $("#treeboxbox_tree");
    treeboxbox_tree.add("<span id='imgShowLoad' class='showLoad'><img align='absmiddle'  alt='' src='App_Themes/Blue/images/loading.gif'/>数据加载中，请稍等...</span>");
 	//新建一个树对象
   	var tree = new dhtmlXTreeObject("treeboxbox_tree", "100%", "100%", "MenuTree");  
    function myErrorHandler(type, desc, erData) {
    	$.messager.alert("信息","菜单读取出错！请联系管理员！");
    }
  
    
    //初始化树
    function initTree(flag, cdId) { 
   		//tree设置属性
   		if(flag!="0"){
			//flag=0 表示第一次进入页面，flag =1 表示是添加平级菜单、子菜单 或者  修改左侧某个节点的信息后，点击保存后后重新加载左侧的树
   			tree.destructor();//将当前的树清空
   			treeboxbox_tree.add("<span id='imgShowLoad' class='showLoad'><img align='absmiddle'  alt='' src='App_Themes/Blue/images/loading.gif'/>数据加载中，请稍等...</span>");
   		   	tree = new dhtmlXTreeObject("treeboxbox_tree", "100%", "100%", "MenuTree");  
   	   	}
        tree.setSkin('dhx_skyblue');
        tree.setImagePath("${ctx}/dhtml/imgs/csh_dhx_skyblue/");
        //把父亲控件颜色先还原
        $("#btnAddItem1").attr("class","btn");
        $("#btnAddItem2").attr("class","btn");
        //节点之间有线条
        tree.enableTreeLines(true);
        //节点不可拖曳
        tree.enableDragAndDrop(true);
        //
        tree.enableDragAndDropScrolling(false);
        //显示出异常信息    
        dhtmlxError.catchError("LoadXML", myErrorHandler);
        //下面进行Jquery ajax
        $.post('${ctx}/menu/menuAction!initTree.action',{
				menuKind:'${param.menuKind}'
            },function (data){
        	 	tree.loadXMLString(data,
                     function() {
                         //去除加载提示
                         if ($("#imgShowLoad").get(0) != null) {
                             treeboxbox_tree.remove("#imgShowLoad");
                         }
                         if (flag=="0"){
                        	 //flag=0 表示第一次进入页面，默认将第一个节点的子节点打开！
                         	 tree.openItem(tree.getSubItems(tree.getSelectedItemId()).split(',')[0]);
	                         if (tree.getSelectedItemId() != "noMenu") {
	                             showItem(tree.getSelectedItemId());
	                          }
                         }else{
							//flag =1 表示是添加平级菜单、子菜单 或者  修改左侧某个节点的信息后，点击保存后后重新加载左侧的树
 							if (tree.getSelectedItemId() != "noMenu") {
                             	if (cdId != "" && cdId != "noMenu") {
                                 	tree.openItem(cdId);
                                 	tree.focusItem(cdId);
                                 	showItem(cdId);
                                 	tree.selectItem(cdId, true);
                             	}else{
                                 	//如果此时cdId="noMenu",说明此时树只有一个一级菜单，且该菜单还没有子菜单
                             		tree.openItem(tree.getSubItems("MenuTree").split(',')[0]);
                             		showItem(tree.getSubItems("MenuTree").split(',')[0]);
                                }
                         	}
                         }
                         
                         //打开链接
                         tree.attachEvent("onClick", function (id) { showItem(id); });
                     });      
        },"text");      
    }
    //初始菜单
    initTree("0","");
	
	//根据传入的菜单id ，初始化菜单详细信息 
    function showItem(menuId) {
    	init();//清空菜单详细信息
        //把父亲控件颜色先还原
        $("#btnAddItem1").attr("class","btn");
        $("#btnAddItem2").attr("class","btn");
        $("#operateFlag").val("update");

        //根据当前选中节点的id值，从userdata中获取该节点的相关信息
        $("#menu_id").val(menuId);
        $("#txtCdMc").val(tree.getItemText(menuId));
        $("#txtCdUrl").val(tree.getUserData(menuId,"menu_url"));
        $("#ddlTarget").val(tree.getUserData(menuId,"menu_type")); 
       	$("#menuTableName").val(tree.getUserData(menuId, "menu_table_name"));
       	//console.log(tree.getUserData(menuId, "menu_table_name"));
        var txtCdJj = tree.getUserData(menuId,"menu_info");
        if('null'!=txtCdJj){
        	$("#txtCdJj").val(tree.getUserData(menuId,"menu_info"));
        }else{
        	$("#txtCdJj").val("");
        }
        $("#ddlswitch").val(tree.getUserData(menuId,"menu_switch"));
        $("#ddlKind").val(tree.getUserData(menuId,"menu_kind"));
        $("#ddlModule").val(tree.getUserData(menuId,"menu_module"));
        $("#menu_sjbh").val(tree.getUserData(menuId,"menu_sjbh"));
        $("#menu_order").val(tree.getUserData(menuId,"menu_order"));
        $("#menu_jb").val(tree.getUserData(menuId,"menu_jb")); 
        $("#menu_path").val(tree.getUserData(menuId,"menu_path"));
        var iconNo=tree.getUserData(menuId,"iconNo");
        $('#preview').attr('src','showIcon.action?type=1&menuId='+iconNo);
        $('#PicLoad').val('');
        $('#iconNo').val(iconNo);
       
        
//         var imgStr = tree.getUserData(menuId,"menu_icon");
//         if(imgStr.indexOf(".")>0){
// 	        //imgStr = imgStr.substring(6);//去掉menu_icon中的字符串'admin\'
// 	        $("#imgDiv").attr("style","display: display;");
// 	        $("#imgShow").attr("src","${ctx}/jsp/dev/"+imgStr);
//         }else{
//         	$("#imgDiv").attr("style","display: display;");
//         	$("#imgShow").attr("src","${ctx}/jsp/dev/icon/images/noimg.png");
//         }
     }
     

  	//添加选中项的平级项
    function addItem() {
        init();//初始化页面
        $("#operateFlag").val("additem");
      //保存当前操作的类型，以确保不能添加第四级菜单
        $("#addChildItem").val("addFather");
        
        //将当前选中节点的id保存起来，方便点击保存操作后重新加载左侧的树，并默认打开新增加的节点
        $("#selected_menuid").val(tree.getSelectedItemId());
        
        var menu_sjbh = "0";
        var menu_jb = "1";
        var menu_path = "0";
        var menu_order = "1";
		
		
		
        menu_sjbh = tree.getParentId(tree.getSelectedItemId());//取得同级别菜单的父节点ID 
        menu_jb =   tree.getUserData(tree.getSelectedItemId(),"menu_jb");//需要取得当前节点的级别
        menu_path = tree.getUserData(tree.getSelectedItemId(),"menu_path");//取得当前节点的路径
        menu_order = tree.hasChildren(tree.getParentId(tree.getSelectedItemId()))+1;//获取同级别节点的总个数
        
       
        
        if(tree.getParentId(tree.getSelectedItemId())=="MenuTree"){
			//说明此时添加的菜单为一级菜单
			menu_sjbh = "0";			
		}
		if(tree.getSelectedItemId()=="noMenu"){
			//说明此时数据库中尚无菜单记录，此时增加的菜单为第一个菜单
			menu_jb = "1";
			menu_path = "0";
			menu_order = "1";
		}
		$("#menu_sjbh").val(menu_sjbh); //对应数据库中上级节点编号字段  
       	$("#menu_order").val(menu_order);//对应数据库中菜单显示顺序字段
       	$("#menu_jb").val(menu_jb); //对应数据库中菜单级别字段
       	$("#menu_path").val(menu_path);//对应数据库中菜单路径字段
       	
       
    }
    
	//新增子项
    function addItemChild() {        
        init();       
        $("#operateFlag").val("additem");  
        //保存当前操作的类型，以确保不能添加第四级菜单
        $("#addChildItem").val("addChild");
        
      	//将当前选中节点的id保存起来，方便点击保存操作后重新加载左侧的树，并默认打开新增加的节点
        $("#selected_menuid").val(tree.getSelectedItemId());
         
        if(tree.getSelectedItemId()=="noMenu"){
			//说明此时数据库中尚无菜单记录
			$.messager.alert("信息","目前暂无菜单列表，请先增加平级菜单！");
		}else{			
	        $("#menu_sjbh").val(tree.getSelectedItemId()); //取得当前节点ID ，对应数据库中上级节点编号       
	        $("#menu_order").val(tree.hasChildren(tree.getSelectedItemId())+1);//获取当前节点的子节点总个数，菜单显示顺序       
	        $("#menu_jb").val(parseInt(tree.getUserData(tree.getSelectedItemId(),"menu_jb"))+1); //需要取得当前节点的子节点级别
	        $("#menu_path").val(tree.getUserData(tree.getSelectedItemId(),"menu_path")+","+tree.getSelectedItemId());//取得当前节点的路径
		}	      
    }
    
  //保存新增或修改的节点信息
    function saveJd() {
        //这里采用JSON数据回传给服务器
//          var elemMenuinfo = $('#form1');
//          var menuinfo = elemMenuinfo.serializeObject();
//          var jsonmenuinfo = JSON.stringify(menuinfo);
//          alert(jsonmenuinfo);
        //通过验证了
        
        if (checkInput()) {
        	$('#form1').form('submit',{
                url: '${ctx}/menu/menuAction!addMenu.action',
                success:function (data) {
                	$.messager.alert('信息',data);
        		 	//window.location.reload();         		 
        		 	//保存成功后，重新读取数据库，获得树
        		 	var flag = $("#operateFlag").val();
        		 	if(flag=="update"){        		 		
        		 		initTree("1",$('#menu_id').val());  
            		}
            		else if(flag=="additem"){
            			initTree("1",$('#selected_menuid').val());  
            		}
                }
             });
        	
        	 /*$.ajax({
                 type : 'POST',
                 contentType : 'application/json',
                 url : '${ctx}/menu/menuAction!addMenu.action',
                 data : jsonmenuinfo,
                 dataType : 'text',
                 success : function(data){
                 	$.messager.alert('信息',data);
        		 	//window.location.reload();         		 
        		 	//保存成功后，重新读取数据库，获得树
        		 	var flag = $("#operateFlag").val();
        		 	if(flag=="update"){        		 		
        		 		initTree("1",$('#menu_id').val());  
            		}
            		else if(flag=="additem"){
            			initTree("1",$('#selected_menuid').val());  
            		}
                 },
                 error : function(data){
                     $.messager.alert("信息","操作失败!");
                 }
             });	*/		          
       }
    }

    //删除选中节点
    function delJd() {
    	$.messager.confirm('警告', '删除选中节点，其子节点也将全部删除，确定？', function (r) {
        if (r) {
        	var name = tree._globalIdStorageFind(tree.getSelectedItemId()).label;
        	if(name == '开发工具'){
        		$.messager.alert('警告','开发工具不可删除！');
        		return;
        	}
        	 $.post('${ctx}/menu/menuAction!delMenu.action',{
 			 	menuId : tree.getSelectedItemId()		 	
 			 }, function (data){
 				$.messager.alert('信息',data);
 			        //window.location.reload(); 

 			        //删除成功后，重新读取数据库，获得树 
 			        if("MenuTree" == tree.getParentId(tree.getSelectedItemId())){
 			        	//如果删除的是某个一级菜单，取得当前所有一级菜单的个数
 			        	var i = tree.hasChildren(tree.getParentId(tree.getSelectedItemId()));
 			        	if(i==1){
							//说明此时只有一个一级菜单
 			        		window.location.reload(); 
 	 	 			    }else{
 	 	 	 			    //说明此时至少有两个一级菜单（两个或以上），    重新加载后默认显示第一个一级菜单。
 	 	 	 			    for(var m=0; m<i; m++){
								if( tree.getSelectedItemId()== tree.getSubItems(tree.getParentId(tree.getSelectedItemId())).split(',')[m] ){
									if(m==0){
										//说明此时删除的是第一个一级菜单，则重新加载后默认显示第二个一级菜单。
										initTree("1",tree.getSubItems(tree.getParentId(tree.getSelectedItemId())).split(',')[1]);  
									}
									else{
										//说明此时删除的不是第一个一级菜单，则重新加载后默认显示第一个一级菜单
										initTree("1",tree.getSubItems(tree.getParentId(tree.getSelectedItemId())).split(',')[0]);  	
									}
								}
 	 	 	 	 			}
 			        		
 	 	 			    }
 	 			    }else{
 	 			    	initTree("1",tree.getParentId(tree.getSelectedItemId())); 
 	 	 	 		}
 			        
 		   	   },"text");  	
        	}
    	});
    }
	//决定按钮是否可用
    function changeLx(_this) {
        if (_this.selectedIndex != 2) {
            document.getElementById("btnSelectDx").disabled = true;
        }
        else {
            document.getElementById("btnSelectDx").disabled = false;
        }
    }

	//检验数据
    function checkInput() {
        if ($("#txtCdMc").value == "") {
            $.messager.alert("信息","菜单名称不可为空！");
            return false;
        }
        var choose = $("#addChildItem").val();
        //如果当前操作为增加子菜单，并且选中节点已经为三级菜单的时候，给出提示
        /* 
        if( (choose =="addChild") && (tree.getUserData((tree.getSelectedItemId()),"menu_jb")=="3") ){
            alert("目前最多添加三层菜单,请重新添加");
            return false;
        }
 		*/
        return true;
    }
	//下移菜单
    function itemUp() {
        var tmpNode = tree.getSelectedItemId();
        var tmpNodeIndex = tree.getIndexById(tmpNode);
        tree.moveItem(tree.getSelectedItemId(), 'up_strict');
        tree.selectItem(tmpNode);
        if (tree.getIndexById(tmpNode) == tmpNodeIndex) {
        	$.messager.alert("信息","已在所属节点首位");
        }
    }
	//上移菜单
    function itemDown() {
        var tmpNode = tree.getSelectedItemId();
        var tmpNodeIndex = tree.getIndexById(tmpNode);
        tree.moveItem(tree.getSelectedItemId(), 'down_strict');
        tree.selectItem(tmpNode);
        if (tree.getIndexById(tmpNode) == tmpNodeIndex) {
        	$.messager.alert("信息","已在所属节点末位");
        }
    }

    //获取整颗树
    function getTreeMenu(id) {
        var t = tree.getSubItems(id).split(',');
        var temp = "";
        var tt = "";
        for (var i = 0; i < t.length; i++) {
            temp += getSpace(tree.getLevel(t[i]) - 1) + t[i] + "--" + tree.getItemText(t[i]) + "节点级别：" + tree.getLevel(t[i]) + "\r\n ";
            if (tree.hasChildren(t[i])) {
                tt = getTreeMenu(t[i]);
                temp += tt + "\r\n";
            }
        }
        return temp;
    }

    function getSpace(k) {
        var t = "";
        for (var i = 0; i < k; i++) {
            t += "--";
        }
        return t;
    }

    //初始化控件,清空 页面 
    function init() {  
        $("#txtCdMc").val("");//菜单名称
        $("#txtCdUrl").val("");//菜单url地址 
        $("#ddlTarget").val("1");//菜单显示方式     
        $("#txtCdJj").val("");//菜单简介
        $("#imgOne").val("");//显示图标
        $("#imgDiv").attr("style","display:none;");
        $("#ddlswitch").val("1");//菜单是否启用
        $("#txtCdbh").val(""); //菜单节点编号
        $("#menuTableName").val("");
        $('#preview').attr('src','');
        $('#PicLoad').val('');
        $('#iconNo').val('');
    }

    //按钮点击后变色
    function changeColor(obj,itemname) {
        obj.className = "btnBg";
        if(itemname=="item1"){
        	 $("#btnAddItem2").attr("class","btn");
        }
        else if(itemname=="item2"){
        	 $("#btnAddItem1").attr("class","btn");
        }
    }

    //获取当前节点的父级节点
    function getNodeLj(id) {
        var tmpReturnNode = "";
        var parentId = tree.getParentId(id);
        if (parentId != "") {
            tmpReturnNode += getNodeLj(parentId) + "," + parentId + ",";
        }
        return tmpReturnNode;
    }


    //保存树（移动位置后）
    function saveTree() {
        //获取所有节点的级别，上级编号，路径，进行更改！

        //document.getElementById("hfTreeMenuJson").value = tree.serializeTreeToJSON();
        //alert(tree.serializeTreeToJSON());

        var tmpAllNodes = tree.getAllSubItems("MenuTree").split(',');

        //alert(tmpAllNodes.length);

        var tmpNodeXx = "";

        var cd_id = "";
        var cd_jb = "";
        var cd_sjbh = "";
        var cd_lj = "";
        var cd_order = 1;


        for (var i = 0; i < tmpAllNodes.length; i++) {

            cd_id = tmpAllNodes[i];
            cd_jb = tree.getLevel(tmpAllNodes[i]);
            cd_sjbh = tree.getParentId(tmpAllNodes[i]).replace(/MenuTree/g, "0");
            cd_lj = getNodeLj(tmpAllNodes[i]).replace(/,,/g, ",").replace(/MenuTree/g, "0");
            cd_lj = cd_lj.substring(1, cd_lj.length - 1);

            var tmpCurParNode = tree.getSubItems(tree.getParentId(tmpAllNodes[i])).split(',');
            for (var j = 0; j < tmpCurParNode.length; j++) {
                if (tmpCurParNode[j] == tmpAllNodes[i]) {
                    cd_order = j + 1; //该点在树同级别节点中的位置
                }
            }

            tmpNodeXx += cd_id + "*aykj*" + cd_jb + "*aykj*" + cd_sjbh + "*aykj*" + cd_lj + "*aykj*" + cd_order + "&aykj&";

        }
        if (tmpNodeXx != "") {
            tmpNodeXx = tmpNodeXx.substring(0, tmpNodeXx.length - 6);
        }

        //将数据送往后台进行保存！
		  $.post('${ctx}/menu/menuAction!saveTreeMenuByIDs.action',
        	{menuId : tmpNodeXx 
       		 }, function (data){  
       			$.messager.alert("信息",data);
       		 
       		 },"text"); 
       
        return true;
    }
    //给JQuery 增加一个新的方法
    $.fn.serializeObject = function() {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [ o[this.name] ];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
      return o;
    };

function selectImages(){
	window.open('${ctx}/icon/iconAction!list.action?operflag=menuSelect&time='+new Date(),'_blank','top=0,left=0,width=710,height=500');
}
//重新加载tree
function loadTree(){
	initTree("1",$('#menu_id').val());
}
//隐藏和显示搜索
function changeTreeSearchPanelShow(){
	if($("#tree_search").is(":visible")==false){
		$("#tree_search").show();
	}else{
		$("#tree_search").hide();
	}
}
var allSuffix='jpg,gif,bmp,png,jpeg';//上传格式
function setImagePreview(docObj,localImagId,imgObjPreview)   
{  
	var suffix=docObj.value.substring(docObj.value.lastIndexOf('.')+1,docObj.value.length);//获取文件类型
	$('#myfile').val(docObj.value);
	suffix=suffix.toLowerCase();	//转换成小写
	if(-1==allSuffix.indexOf(suffix)){
		$.messager.alert('警告','只能上传'+allSuffix+'格式文件');
		docObj.value="";
		return;
	}
	$('#icon').val(docObj.value);
    if(docObj.files && docObj.files[0])  
    {  
        //火狐下，直接设img属性  
        imgObjPreview.style.display = 'block';  
        imgObjPreview.style.width = '23px';  
        imgObjPreview.style.height = '23px';                      
        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式    
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);  
    }  
    else  
    {  
        //IE下，使用滤镜  
        docObj.select();  
        var imgSrc = document.selection.createRange().text;  
        //必须设置初始大小  
        localImagId.style.width = "23px";  
        localImagId.style.height = "23px";  
          
        //图片异常的捕捉，防止用户修改后缀来伪造图片  
        try  
        {  
            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";  
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;  
         }  
         catch(e)  
         {  
            $.messager.alert("警告","您上传的图片格式不正确，请重新选择!");  
            return false;  
          }                            
          imgObjPreview.style.display = 'none';  
          document.selection.empty();  
    }  
    return true;  
} 

</script>
</html>
