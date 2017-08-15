<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作提醒</title>
<style>
.panel-header {
	display: block !important;
}
</style>
<link rel="stylesheet" href="${ctx}/css/newCrud.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript">
       $(document).ready(function(){
    	  // getCertificate();
    	   //getDanger();
    	   //getEquipment();
    	   //getProblem();
    	   $('#tx').dialog('close');
       });
	   	
       /**获取所有的资质证书提醒*/
       function getCertificate(){
		   $.ajax({
			   url:'${ctx}/qualificationCertificate/qualificationCertificateAction!getAll',
			   method:'post',
			   success:function(data){
				   var data = eval('('+data+')');
				   if(data != null && data.length > 0){
					   for(var j=0;j<data.length;j++){
						   if(j>4){
							   break;
						   }
						   var name = data[j].qualificationName;
						   var time = data[j].expirationTime;
						   var id = data[j].id;
						   var now = new Date();
						   var old = new Date(Date.parse(time.replace(/-/g, "/")));
						   var str = "<tr style='text-align:center'>";
					  	   str += "<td><a href='javascript:void(0)' onclick='openCertificate(\""+id+"\")'>"+subString(name)+"</a></td>";
				   		   str += "<td>"+time+"</td>";
						   if(now >= old){
							   var diff= now.getTime()-old.getTime();
							   var days = Math.floor(diff/(24*3600*1000));
							   str += "<td><span style='color: red'>已过期"+days+"天</span></td>";
						   }else{
							   diff=old.getTime() -now.getTime();
							   var days = Math.floor(diff/(24*3600*1000));
							   if(days != 0){
								   str += "<td><span style='color: blue'>还有"+days+"天即将到期</span></td>";
							   }
							   else{
								   str += "<td><span style='color: red'>明天将到期</span></td>";
							   }
						   }
						   $('#certificate').append(str);
					   }
				   }
				   else{
					   var str = "<center>暂无数据</center>";
					   $('#certificate').append(str);
				   }
			   }
		   });
		  
	   }
       
       
       /**获取安全隐患提醒*/
       function getDanger(){
    	   $.ajax({
    		   url:'${ctx}/dangerHidden/dangerHiddenAction!getAll',
			   method:'post',
			   data:{'dangerHidden.presentStatus':'未整改'},
			   success:function(data){
				   var data = eval('('+data+')');
				   if(data != null && data.length>0){
					   for(var i=0;i<data.length;i++){
						   if(i>4){
							   break;
						   }
						   var name = data[i].existDanger;
						   var time = data[i].finishDate;
						   var id = data[i].id;
						   var now = new Date();
						   var old = new Date(Date.parse(time.replace(/-/g, "/")));
						   var str = "<tr style='text-align:center'>";
					  	   str += "<td><a href='javascript:void(0)' onclick='openDanger(\""+id+"\")'>"+subString(name)+"</a></td>";
				   		   str += "<td>"+time+"</td>";
				   		   if(now >= old){
							   var diff= now.getTime()-old.getTime();
							   var days = Math.floor(diff/(24*3600*1000));
							   str += "<td><span style='color: red'>已超过"+days+"天未完成整改</span></td>";
						   }else{
							   diff=old.getTime() -now.getTime();
							   var days = Math.floor(diff/(24*3600*1000));
							   if(days != 0){
								   str += "<td><span style='color: blue'>计划还有"+days+"天完成整改</span></td>";
							   }
							   else{
								   str += "<td><span style='color: r'>计划明天完成整改</span></td>";
							   }
						   }
				   			$('#danger').append(str);
					   }
				   }
				   else{
					   var str = "<center>暂无数据</center>";
					   $('#danger').append(str); 
				   }
			   }
    	   });
       }
       
       /**获取设备设施*/
       function getEquipment(){
    	   $.ajax({
    		   url:'${ctx}/equipment/equipmentAction!getAll',
			   method:'post',
			   success:function(data){
				   var data = eval('('+data+')');
				   if(data != null && data.length>0){
					   for(var i=0;i<data.length;i++){
						   if(i>4){
							   break;
						   }
						   var name = data[i].equipName;
						   var time = data[i].nextCheckingTime;
						   var id = data[i].id;
						   var now = new Date();
						   var old = new Date(Date.parse(time.replace(/-/g, "/")));
						   var str = "<tr style='text-align:center'>";
					  	   str += "<td><a href='javascript:void(0)' onclick='openEquipment(\""+id+"\")'>"+subString(name)+"</a></td>";
				   		   str += "<td>"+time+"</td>";
				   		   if(now >= old){
							   var diff= now.getTime()-old.getTime();
							   var days = Math.floor(diff/(24*3600*1000));
							   str += "<td><span style='color: red'>超过"+days+"天未检修</span></td>";
						   }else{
							   diff=old.getTime() -now.getTime();
							   var days = Math.floor(diff/(24*3600*1000));
							   if(days != 0){
								   str += "<td><span style='color: blue'>距下次检修还剩"+days+"天</span></td>";
							   }
							   else{
								   str += "<td><span style='color: red'>明天为检修时间</span></td>";
							   }
						   }
				   			$('#equipment').append(str);
					   }
				   }
				   else{
					   var str = "<center>暂无数据</center>";
					   $('#equipment').append(str); 
				   }
			   }
    	   });
       }
       
       /**获取故障设施*/
       function getProblem(){
    	   $.ajax({
    		   url:'${ctx}/problemEquipt/problemEquiptAction!getAll',
			   method:'post',
			   data:{"problemEquipt.doneType":"未处理"},
			   dataType:'json',
			   success:function(data){
				   if(data != null && data.length>0){
					   for(var i=0;i<data.length;i++){
						   if(i>4){
							   break;
						   }
						   var name = data[i].equipName;
						   var time = data[i].ecoTime;
						   var id = data[i].id;
						   var now = new Date();
						   var old = new Date(Date.parse(time.replace(/-/g, "/")));
						   var str = "<tr style='text-align:center'>";
					  	   str += "<td><a href='javascript:void(0)' onclick='openProblemEqu(\""+id+"\")'>"+subString(name)+"</a></td>";
				   		   str += "<td>"+time+"</td>";
				   		   if(now >= old){
							   var diff= now.getTime()-old.getTime();
							   var days = Math.floor(diff/(24*3600*1000));
							   str += "<td><span style='color: red'>超过整改期限"+days+"天</span></td>";
						   }else{
							   diff=old.getTime() -now.getTime();
							   var days = Math.floor(diff/(24*3600*1000));
							   if(days != 0){
								   str += "<td><span style='color: blue'>距整改时间还剩"+days+"天</span></td>";
							   }
							   else{
								   str += "<td><span style='color: red'>明天为整改时间</span></td>";
							   }
						   }
				   			$('#problemEquipt').append(str);
					   }
				   }
				   else{
					   var str = "<center>暂无数据</center>";
					   $('#problemEquipt').append(str); 
				   }
			   }
    	   });
       }
       
       function subString(name){
    	   if(name == null || name =='undifined'){
    		   return " ";
    	   }
    	   if(name.length >10){
    		   name = name.substring(0,9)+"...";
    	   }
    	   return name;
    		 
       }
</script>
</head>
<body>
	<div class="middle">
		<div
			style="width: 40%; height: 200px; float: left; margin: 10px 10px 0 0">
			<div class="panel-header" style="width: 100%;">
				<div class="panel-title panel-with-icon">到期换证提醒</div>
				<div class="panel-tool" style="padding-right: 30px">
					<a
						href="${ctx }/autoJump.action?menuId=222&url=jsp/ent/enterpriseInfomation/qualificationCertificate.jsp"
						class="more_a">more>></a>
				</div>
			</div>
			<div class="table_list">
				<table cellpadding="0" style="font-size: 12px;" cellspacing="0"
					width="100%" id="certificate" class="table_ent">
					<tr style="text-align: center;">
						<th>资质证书名称</th>
						<th>到期时间</th>
						<th>状态提醒</th>
					</tr>
				</table>
			</div>
		</div>
		<div
			style="width: 40%; height: 200px; float: left; margin: 10px 0 0 10px">
			<div class="panel-header" style="width: 100%;">
				<div class="panel-title panel-with-icon">未整改隐患提醒</div>
				<div class="panel-icon icon-edit"></div>
				<div class="panel-tool" style="padding-right: 30px">
					<a
						href="${ctx }/autoJump.action?menuId=242&url=jsp/ent/DangerHidden.jsp"
						class="more_a">more>></a>
				</div>
			</div>
			<div class="table_list">
				<table cellpadding="0" style="font-size: 12px;" cellspacing="0"
					border="0" width="100%" id="danger" class="table_ent">
					<tr style="text-align: center;">
						<th>存在的问题和隐患</th>
						<th>计划完成时间</th>
						<th>状态提醒</th>
					</tr>
				</table>
			</div>
		</div>

		<div
			style="width: 40%; height: 200px; float: left; margin: 10px 10px 0 0">
			<div class="panel-header" style="width: 100%;">
				<div class="panel-title panel-with-icon">设备设施提醒</div>
				<div class="panel-icon icon-edit"></div>
				<div class="panel-tool" style="padding-right: 30px">
					<a
						href="${ctx }/autoJump.action?menuId=254&url=jsp/ent/equipment/equipment.jsp"
						class="more_a">more>></a>
				</div>
			</div>
			<div class="table_list">
				<table cellpadding="0" style="font-size: 12px;" cellspacing="0"
					border="0" width="100%" id="equipment" class="table_ent">
					<tr style="text-align: center;">
						<th>设备设施名称</th>
						<th>限令整改时间</th>
						<th>状态提醒</th>
					</tr>
				</table>
			</div>
		</div>

		<div
			style="width: 40%; height: 200px; float: left; margin: 10px 0 0 10px">
			<div class="panel-header" style="width: 100%;">
				<div class="panel-title panel-with-icon">故障设施提醒</div>
				<div class="panel-icon icon-edit"></div>
				<div class="panel-tool" style="padding-right: 30px">
					<a
						href="${ctx }/autoJump.action?menuId=255&url=jsp/ent/equipment/problemEquipt.jsp"
						onclick="openProblem();" class="more_a">more>></a>
				</div>
			</div>
			<div class="table_list">
				<table cellpadding="0" style="font-size: 12px;" cellspacing="0"
					border="0" width="100%" id="problemEquipt" class="table_ent">
					<tr style="text-align: center;">
						<th>故障设施名称</th>
						<th>限令整改时间</th>
						<th>状态提醒</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="tx" class="easyui-dialog"
		data-options="iconCls:'icon-save',resizable:false,modal:true,maximizable:false">
		<iframe id="mainframe" name="mainframe" width="100%"
			style="margin-top: 0px;" frameborder="0" height="400"></iframe>
	</div>
	<script type="text/javascript">
	function openProblem(){
		location.href="${ctx}/jsp/ent/equipment/problemEquipt.jsp";
	}
	
	function openCertificate(id){
		var src = '${ctx}/qualificationCertificate/qualificationCertificateAction!view?id='+id;
		$('#mainframe').attr("src",src);
		$('#tx').dialog({
			title:'查看安全生产资质证书',
			width: 850,  
		    height: 450
		});
		$('#tx').dialog('open');
	}
	
	function openDanger(id){
		var src = '${ctx}/dangerHidden/dangerHiddenAction!view?id='+id;
		$('#mainframe').attr("src",src);
		$('#tx').dialog({
			title:'查看安全隐患',
			width: 850,  
		    height: 450
		});
		$('#tx').dialog('open');
	}
	
	
	function openEquipment(id){
		var src = '${ctx}/equipment/equipmentAction!view?id='+id;
		$('#mainframe').attr("src",src);
		$('#tx').dialog({
			title:'查看设备设施',
			width: 850,  
		    height: 450
		});
		$('#tx').dialog('open');
	}
	
	function openProblemEqu(id){
		var src = '${ctx}/problemEquipt/problemEquiptAction!view?id='+id;
		$('#mainframe').attr("src",src);
		$('#tx').dialog({
			title:'查看故障设施',
			width: 850,  
		    height: 450
		});
		$('#tx').dialog('open');
	}
	
	
	
	function closeDialog(){
		$('#tx').dialog('close');
	}
</script>
</body>
</html>