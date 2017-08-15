<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息提醒</title>
<style type="text/css">
body{
	margin:0;
	padding:0;
	width:100%;
	height:100%;
	background:url(qydatsb.png) no-repeat fixed center;
}

.divc{
	position:absolute;
	top:50%;
	left:50%;
	margin-top:-160px;
	margin-left:-480px;
	/*此时宽和高都要固定 zuo 做上下左右居中*/
	width:960px;
	height:320px;
}	


</style>
<script type="text/javascript">
	var deptId ="${param.showDept}";
	var xzccount =0;
	var wscount =0;
	var bgcount =0;
	
	$(function(){
		if(deptId==""){
			deptId="${user.orgTree}".substring(1);
		}
		$("#xxtx").hide();
		$.ajax({
			url:"${ctx}/",
			async:false,
			dataType:"json",
			success:function(data){
			}
		})
	
		
</script>
</head>

<body>
<div id="xxtx" style="width: 220px;height: 322px;
	position:fixed;bottom:-16;right:0;z-index: 1993;">
<!-- 	F8F8F8 -->
	<div style="width: 260px;height: 30px;line-height:30px;background-color:#299AD3; "> 
		<span style="margin-left: 20px;font-family: 微软雅黑;font-size: 14px;color:white;">企业待审核信息提醒</span>
	</div>
	
	
	
	<!-- 新注册审核 -->
	<div style="width: 260px;height: 93px;background-color: white;">
		<div style="height: 10px;"></div>
		<span style="font-family: 微软雅黑;font-size: 15px;margin-left: 20px;">您当前还有：<br></span>
		<span style="font-family: 微软雅黑;font-size: 15px;margin-left: 20px;">
		<span id="xzccount" style="color: #f53204;"></span>
		家新注册企业未审核！</span>
		<br>
		<div style="height: 8px;"></div>
		<span style="margin-left: 20px;">
		<a href="${ctx}/autoJump.action?menuId=466&url=jsp/gov/baseInfo/enterprise_dsh.jsp" style="color: #0086FF;font-family: 微软雅黑;font-size: 15px;">点击查看详情</a>
		</span>
	</div>
	<div style="padding-left: 20px;border-top:1px solid #CCCCCC;width: 300px;"></div>
	<!-- 完善审核 -->
	<div style="width: 260px;height: 73px;background-color: white;">
		<div style="height: 10px;"></div>
		<span style="font-family: 微软雅黑;font-size: 15px;margin-left: 20px;">
		<span id="wscount" style="color: #f53204;font-family: 微软雅黑;font-size: 16px;"></span>
		家企业信息完善未审核！</span>
		<br>
		<div style="height: 8px;"></div>
		<span style="margin-left: 20px;">
		<a href="${ctx}/autoJump.action?menuId=1921&url=jsp/gov/baseInfo/enterprise_wssh.jsp" style="color: #0086FF;font-family: 微软雅黑;font-size: 15px;">点击查看详情</a>
		</span>
	</div>
	<div style="padding-left: 20px;border-top:1px solid #CCCCCC;width: 300px;"></div>
	<!-- 变更审核 -->
	<div style="width: 260px;height: 75px;background-color: white;" >
		<div style="height: 10px;"></div>
		<span style="font-family: 微软雅黑;font-size: 15px;margin-left: 20px;">
		<span id="bgcount" style="color: #f53204;font-family: 微软雅黑;font-size: 16px;"></span>
		家企业信息变更未审核！</span>
		<br>
		<div style="height: 8px;"></div>
		<span style="margin-left: 20px;">
		<a href="${ctx}/autoJump.action?menuId=1922&url=jsp/gov/baseInfo/enterprise_bgsh.jsp" style="color: #0086FF;font-family: 微软雅黑;font-size: 15px;">点击查看详情</a>
		</span>
	</div>
	
	<div style="width: 260px;background-color: white;">
		<div style="padding-left: 20px;border-top:1px solid #DADADA;width: 300px;">
		<div style="height: 5px;"></div>
		<a onclick="hlbctx()" href="javascript:void(0)" style="color: #f53204;font-family: 微软雅黑;font-size: 14px;">
		忽略本次提醒
		</a>
		<div style="height: 5px;"></div>
		</div>
	</div>
	
	
</div>

<div class="divc">
	        <p style="text-align: center;"><img src="qydats.png"  border="0" usemap="#Map" /></p>
		<map name="Map" id="Map">
			<area shape="rect" coords="48,0,250,319" onclick="daSearch()" href="javascript:void(0)" />
			<!-- <area shape="rect" coords="268,0,470,319" onclick="tjSearch()" href="javascript:void(0)" /> -->
			<area shape="rect" coords="268,0,470,319" onclick="shSearch()" href="javascript:void(0)" />
			<area shape="rect" coords="489,0,691,319" onclick="mmSearch()" href="javascript:void(0)" />
		</map>
</div>

</body>
</html>
