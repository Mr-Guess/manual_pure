<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ay.framework.shiro.SystemParameter"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="${ctx}/js/CharacterUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业注册</title>
<link rel="stylesheet" href="${ctx}/css/register.css" type="text/css" />
<!-- <link rel="stylesheet" type="text/css" -->
<!-- 	href="${ctx}/resources/js/jquery/easyui-1.3.3/themes/default/easyui.css" /> -->
<!-- <link rel="stylesheet" type="text/css" -->
<!-- 	href="${ctx}/resources/js/jquery/easyui-1.3.3/themes/icon.css" /> -->
	<!-- 将Tree对象引入进来 -->
	<script type="text/javascript">
		$(document).ready(
						function() {
// 							$.extend($.fn.validatebox.defaults.rules, {
// 								corpCode : {
// 									validator : function(value, param) {
// 										if (value) {
// 											return value.length().test(value);
// 										} else {
// 											return true;
// 										}
// 									},
// 									message : '请输入正确的单位代码'
// 								}
// 							});
							$("#jjlx")
								.combobox({
								   url: '${ctx}/data/dataAction!findByType?parentId=-1&typeId=jjlx',
								   valueField:'dataCode',
								   textField:'dataName'
							 });
							$('#managerType')
									.combobox(
											{
												url : '${ctx}/data/dataAction!findDataByTypeFirstLevel?typeId=1373691c30c24e80b83c2c00346052c6',
												valueField : 'dataCode',
												textField : 'dataName',
												onSelect : function(record){
													var goId = record.id;
													$('#hyid').val(record.id);
													$.ajax({
														url : '${ctx}/dept/deptAction!getDeptByName?id='+goId,
														success: function(data){
														}
													});
												}
											});
							$("#companyName").validatebox({
								required : true
							});
							$("#companyCode1").validatebox({
								required : true
							});
							$("#reCompanyCode1").validatebox({
								required : true,
								validType : 'equalTo["#companyCode1"]'
							});
							$("#rePassword").validatebox({
								required : true,
								validType : "equalTo['#password']"
							});
							
							$("#username").validatebox({
								required : true
							});
							$("#userTel").validatebox({
								required : true,
								validType : "phoneNumber"
							});
							$("#treeAddr").validatebox({
								required:true,
								validType:'gridAddr'
							});
							$("#detailsAddr").validatebox({
								required:true
							});
							$("#validCode").validatebox({
								required : true
							});
							//加载省
							  $('#enterpriseCity').combobox({
								    url:'${ctx}/data/dataAction!findByType?parentId=-1&typeId=0b8415e08bf3474686e643318c0a497c',
								    valueField:'dataCode',
								    textField:'dataName'
							});
							//加载市
							  $('#enterpriseCity').combobox({
							    	 onSelect: function(opt){
							    		 $('#xzqhqx').combobox('setValues','');//清除区值
							    		 loadXzqhjd(opt.dataCode,"xzqhs");
							    	 }
							   });
							//加载区
							  $('#xzqhs').combobox({
								  onSelect:function(opt){
									  loadXzqhjd(opt.dataCode,"xzqhqx");
								  }
							  });
							$("#xzqhqx").combobox({
 								onSelect:function(opt){
 									if(opt.dataCode=='320101'){	//是否是市辖区
										$('input[name="enterprise.lsgx"]:eq(0)').attr('checked','checked');
										$('input[name="enterprise.lsgx"]').attr('disabled','disabled');
									}else{
										$('input[name="enterprise.lsgx"]').removeAttr('disabled');
										//$('input[name="enterprise.lsgx"]:eq(1)').attr('checked','checked');
									}
 								}
 							});
							$('#dept').dialog('close');
							 trees = new Tree('${ctx}/dept/deptAction!reloadGovDept.action', 'trees');
					         trees.loadTree();
						});
	     //加载监管部门
	    var tree = null;
	    var trees=null;
		var key = null;
		var upTree="";//上级树
		var deptTree="";//整棵树
		var nodes=[];	//选择的节点
		function loadTree(address,lsgx){
			 tree = new Tree('${ctx}/dept/deptAction!reloadAreaTree.action?dept.area='+address+'&isOrArea='+lsgx, 'dept_tree','tree_search','key',doSearch);
	         tree.loadTree();
	         $('#dept').dialog("open");	
		}
		/*
         * 回调函数
         */
        function doSearch(id, name, type) {
            if (id != '-1') {
            	var treeObj=$.fn.zTree.getZTreeObj("trees");
            	nodes = treeObj.transformToArray(treeObj.getNodesByParam("id",id,null));//获取节点树 Array
            	$('#jgdw').val(name);
            	$('#showDept').val(id);	//设置显示的值
            	getDeptTree(id);
                $('#dept').dialog("close");
            }
        }
		/*获取所有子节点*/
		function treeNodes(nodes){
			var dtree="";
			for(var i=1;i<nodes.length;i++){
				dtree+=","+nodes[i].id;
			}
			return dtree.substring(1,dtree.length);
		}
        //递归查询部门树 找上级 
		function getDeptTree(id){
			$.ajax({
                url:'${ctx}/dept/deptAction!getById.action?id=' + id,
                success:function (data) {
                	if(data.parentId != '-1'){
                		upTree+=","+data.id;
                		getDeptTree(data.parentId);
                	}else{
                		upTree+=","+data.id;
                		upTree=upTree.substring(1,upTree.length);
                		deptTree+=upTree;//+=','+treeNodes(nodes);
                		$('#jgdwDept').val(deptTree);
                		del();//清除树
                	}
                }
            });
		}
        
        function del(){
        	deptTree="";
        	upTree="";
        }
        

		/** 提交表单*/
		function submitForm() {
			$('#registerForm').form('submit', {
				url : '${ctx}/enterprise/enterpriseAction!register.action',
				onSubmit : function() {
					var isValid = $(this).form('validate');
					if (isValid) {
					}
					if($('#companyCode1').val().length < 8){
						return false;
					}else{
					}
					return isValid;
				},
				success : function(data) {
					var data = eval('(' + data + ')');
					if (data.operateSuccess) {
						var username=$('#companyCode1').val();
						var password=$('#password').val();
						window.top.location.href="${ctx}/register_succeed.jsp?username="+username+"&password="+password;
					} else {
						$.messager.alert('失败', data.operateMessage);
						$.loded();
					}
					var obj = document.getElementById("imgValidCode");
					changeCode(obj);
				}
			});
		}
		function changeCode(obj) {
			if (obj) {
				var timenow = new Date().getTime();
				$('#imgValidCode').attr("src","validCode.action?d=" + timenow); 
			}
		}
		var timeOut = null;
		/** 验证组织机构代码判断企业是否重复*/
		function validCorpCode() {
			var code = $("#companyCode1").val();
			if (code != "" && 'underfed' != code && code.length >= 8 &&code.length<=16) {
				$("#dwdm").attr("src","${ctx}/images/false.png").attr("title","单位代码不规范").show();
				if (timeOut) {
					window.clearTimeout(timeOut);
				}
				timeOut = setTimeout(
					function() {
						$.post("${ctx}/enterprise/enterpriseAction!isCorpExist.action",
							{'enterprise.zzjgdm' : code},
								function(ret) {
									if (ret == 'false') {
										$("#dwdm").attr("src","${ctx}/images/false.png").attr("title","单位代码已存在").show();
									} else {
										$("#dwdm").attr("src","${ctx}/images/true.png").attr("title","单位代码可使用").show();
									}
								});
					}, 200);
			} else {
				$("#dwdm").attr("src","${ctx}/images/false.png").show();
			}
		}
		//验证企业名称是否存在
		function valideQymc(){
			var qymc=$("#companyName").val();
			if(qymc==""||qymc=='undefined'){
				$("#qymc").attr("src","${ctx}/images/false.png").attr("title","企业名称不规范").show();
			}else{
				$.ajax({
					url:'${ctx}/enterprise/enterpriseAction!isCorpExist.action',
					type:'post',
					data:{'enterprise.qymc':qymc,'op':'qymc'},
					success:function(data){
						if(data=='false'){
							$("#qymc").attr("src","${ctx}/images/false.png").attr("title","企业名称已存在").show();
						}else{
							$("#qymc").attr("src","${ctx}/images/true.png").attr("title","可以使用").show();
						}
					}
				});
			}
		}
		function loadDept(){
			var xzqhqx=$('#xzqhqx').combobox('getValue');//获取区code
			var lsgx=$('input[name="enterprise.lsgx"]:checked').val();	//获取市属关系
			if(xzqhqx==""||xzqhqx=='null'){
				$.messager.alert('温馨提示', '请先选择所在区');
			}else{
				$("#tree_search").hide();
				if(xzqhqx=='320101'){
					lsgx='0';
				}
				loadTree(xzqhqx,lsgx);
			}
		}
		//加载二级联动
		function loadXzqhjd(code,conten){
			$("#"+conten).combobox({
				url:'${ctx}/data/dataAction!findCodeAndType?typeId=0b8415e08bf3474686e643318c0a497c&dataCode='+code,
    			 valueField:'dataCode',
    			 textField:'dataName'
			 });
		}
		
		function search(){
			if($("#tree_search").is(":hidden")){
				$("#tree_search").show();
			}else{
				$("#tree_search").hide();
			}
		}
		
		function checkCity(){
			$('#jgdw').val('');
			$('#jgdwDept').val('');
			$('#showDept').val('');
			
		}
	</script>
</head>
<style>
#qymc,#dwdm {
	position: absolute;
	margin-left: 5px;
	margin-top: 10px;
}

#enterprise ul .sel input {
	margin-left: 0px;
	height: 33px;
}
#dept_tree{
	background-color: #F2F5F7;
}

.combo-arrow {
	width: 35px;
	height: 34px;
}

</style>
<body>
	<div id="body">
		<form id="registerForm" name="registerForm" method="post">
			<div id="top">
				<img src="images/zc_name.png" /> <a href="login.jsp">返回首页</a> <input
					type="hidden" id="hyid" name="hyid" />
			</div>
			<div id="main">
				<img src="images/zc_title_qy.png" />
				<div id="enterprise">
					<ul>
						<li>企业名称：<input id="companyName" name="enterprise.qymc"
							type="text" space_able="false" onblur="valideQymc()" /><img
							id="qymc" style="display: none;" src="" /></li>
						<li>单位代码：<input id="companyCode1" name="enterprise.zzjgdm"
							space_able="false" onblur="validCorpCode()" type="text"
							validType="corpCode" /><img id="dwdm" style="display: none;"
							src="" /></li>
						<li>代码确认：<input id="reCompanyCode1" type="text" /></li>
						<li class="password">用户密码：<input id="password"
							name="user.password" type="password" space_able="false"
							class="easyui-validatebox" data-options="required:true,validType:'length[8,100]'" />
						</li>
						<li class="password">确认密码：<input id="rePassword"
							type="password" space_able="false" /></li>
						<li id="representative">法定代表人：<input id="username"
							name="enterprise.fddbr" type="text" space_able="false" /></li>
						<li id="phone">法人联系电话：<input id="userTel"
							name="enterprise.lxdh" type="text" space_able="false" /></li>
						<li class="sel">经济类型： <input type="text"
							class="easyui-combobox" name="enterprise.jjlx" id="jjlx"
							data-options="required:true,validType:'maxByteLength[100]',editable:false"
							style="width: 313px; height: 33px; border: 0px;" />
						</li>
						<li class="sel">行业类别： <input type="text"
							class="easyui-combobox" name="enterprise.hylb" id="managerType"
							data-options="required:true,validType:'maxByteLength[100]',editable:false"
							style="width: 313px; height: 33px; border: 0px;" />
						</li>
						<li class="sel">所在地区： <input class="easyui-combobox"
							data-options="required:true,validType:'maxByteLength[100]',editable:false,panelHeight:'auto'"
							name="enterprise.xzqhs"
							style="width: 130px; height: 33px; border: 0px;"
							id="enterpriseCity" />&nbsp;省 <input class="easyui-combobox"
							name="enterprise.xzqh" id="xzqhs"
							data-options="required:true,validType:'maxByteLength[100]',editable:false,panelHeight:'auto'"
							style="width: 110px; height: 33px; border: 0px;" />&nbsp;市
						</li>
						<li class="sel"><span style="visibility: hidden;">所在地区：</span>
							<input class="easyui-combobox" 
							name="enterprise.xzqhqx" id="xzqhqx" 
							data-options="required:true,validType:'maxByteLength[100]',editable:false,panelHeight:'auto'"
							style="width: 130px; height: 33px; border: 0px;margin-left: 5px;" />&nbsp;区
							</li>
						<li>是否市属：<input name="enterprise.lsgx"
						type="radio" style="width: 20px;height: 20px;border: 0px;" space_able="false" value="1" onclick="checkCity()" />是&nbsp;&nbsp;
						<input style="width: 20px;height: 20px;border: 0px;" name="enterprise.lsgx"
						type="radio" space_able="false"  checked="checked" value="0" onclick="checkCity()" />否&nbsp;&nbsp;</li>
						<li>监管单位：<input id="jgdw"  onclick="loadDept();" class="easyui-validatebox"
							space_able="false"  type="text" data-options="required:true"/>
							<input name="enterprise.dept" type="hidden" id="jgdwDept"/>
							<input name="enterprise.showDept" type="hidden" id="showDept"/>
						</li>
					</ul>
				</div>
				
				<div id="user">
					<ul>
						
					</ul>
				</div>
				<div id="security_code">
					请输入验证码：<img id="imgValidCode" src="validCode.action"
						title="看不清，点击换一张" onclick="changeCode(this)" />&nbsp;<input
						type="text" id="validCode" name="validCode" class="input_text" />&nbsp;

					<a href="javascript:changeCode(this)">看不清，换一张</a>
				</div>
			</div>
			<div id="foot">
				<a href="javascript:submitForm();"></a>
			</div>
		</form>
	</div>
	<div id="dept" class="easyui-dialog" data-options="title:'监管部门',toolbar:'#tt',modal:true">
			<div id="tree_search" style="display: none">
				关键字:&nbsp;&nbsp;<input type="text" id="key" name="key"
					style="width: 80px; margin-top: 3px; height: 25px;" />&nbsp;&nbsp;<a
					href="#" class="easyui-linkbutton" iconCls="icon-search"
					plain="true" onclick="tree.searchNode();"></a>
			</div>
				<div id="dept_tree" style="height: 280px;width:380px;" class="ztree"></div>
		</div>
		<div id="tt" style="margin-top: 5px;">
			<a href="javascript:search();" class="icon-treeSearch"
				 title="搜索">&nbsp;&nbsp;</a>
			<a href="#" class="icon-treeRefresh"
				onclick="javascript:tree.loadTree();" title="刷新">&nbsp;&nbsp;</a> <a href="#"
				class="icon-treeAdd" onclick="javascript:tree.expandAllNode(false);"
				title="收缩所有">&nbsp;&nbsp;</a> <a href="#" class="icon-treeDelete"
				onclick="javascript:tree.expandAllNode(true);" title="展开所有"
				>&nbsp;&nbsp;</a>
		</div>
		<div id="trees" class="ztree" style="display: none;"></div>
</body>
</html>
