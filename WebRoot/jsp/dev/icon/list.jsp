<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>图标管理列表</title>
<script type="text/javascript"
	src="../../../resources/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="fancybox/jquery.fancybox-1.3.1.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/jsp/dev/icon/fancybox/jquery.fancybox-1.3.1.css"
	media="screen" />
<link href="${ctx}/jsp/dev/icon/css/webplan.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript">
</script>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

body {
	font-family: Arial, "宋体", Tahoma;
	font-size: 14px;
}

#wrap {
	margin: 0 auto;
	padding-top: 10px;
}

#top_icon {
	font-weight: bold;
	color: #6e6d6d;
	font-size: 13px;
	padding-left: 8px;
}

#top_icon img {
	float: left;
}

#top_icon a {
	float: right;
	padding-right: 50px;
}

#top_icon h3 {
	float: left;
	padding: 7px 0 0 12px;
}

#content {
	clear: both;
	padding-top: 15px !important; *
	padding-top: 6px;
}

#box_icon {
	clear: both;
	height: 385px;
	width: 98%;
	margin: 0 auto;
	padding-top: 10px !important; *
	padding-top: 4px; /* 1024: width:800px; 1280: width:1060px; */
	overflow-y: auto;
}

hr {
	color: #d6d5d1;
}

.icon {
	background: url(${ctx}/jsp/dev/icon/images/tab_bg.gif) no-repeat;
	height: 95px;
	width: 94px;
	margin: 0 auto;
	margin-top: 22px !important; *
	margin-top: 15px;
	float: left;
	margin-left: 18px; /* 控制间距 不同分辨率*/
}

.icon ul li {
	list-style: none;
	text-align: center;
	padding-top: 10px !important; *
	padding-top: 7px;
}

.xuan_s {
	
}

.xuan_s a {
	text-decoration: none;
	color: #5a5958;
}

.xuan_s a:hover {
	text-decoration: underline;
	color: #03F;
}

.hr_line {
	border: 1px #bcbcbc solid;
	width: 98%;
	margin: 0 auto;
}
/* 两个或者四个文字的按钮（如：打印、打印预览、新增等） */
a.list_button2:link {
	background: url(${ctx}/jsp/dev/icon/images/bg_button0.gif) no-repeat;
	width: 83px;
	height: 27px;
	color: #000;
	font-size: 12px;
	font-weight: bold;
	text-align: center;
	line-height: 27px;
	text-decoration: none;
}

a.list_button2:hover {
	background: url(${ctx}/jsp/dev/icon/images/bg_button0.gif) no-repeat;
	width: 83px;
	height: 27px;
	color: #000;
	font-size: 12px;
	font-weight: bold;
	text-align: center;
	line-height: 27px;
	text-decoration: none;
}

a.list_button2:visited {
	background: url(${ctx}/jsp/dev/icon/images/bg_button0.gif) no-repeat;
	width: 83px;
	height: 27px;
	color: #000;
	font-size: 12px;
	font-weight: bold;
	text-align: center;
	line-height: 27px;
	text-decoration: none;
}
</style>
</head>
<body style="overflow: hidden;">
	<div id="wrap">
		<div id="top_icon" style="height: 32px; background: none;">
			<img src="${ctx}/jsp/dev/icon/images/top_icon.gif" width="38"
				height="32" />
			<h3>图标管理</h3>
		</div>
		<div style="background: #fff">
			<div
				style="width: 98%; height: 50px; float: left; padding-left: 60px; padding-top: 10px; BORDER-TOP: #2169c6 1PX double; FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0, startColorStr=#d2ebfa, endColorStr=#f6f6f8);">
				<form name="form1" method="post" enctype="multipart/form-data"
					action="icon/iconAction!addSave.action">
					上传图标文件<input type="file" name="upload" id="file_up" size="32"
						onChange="setFilename(this.value)" /> 选择分类：<select
						name="icon_leibie" style="width: 70px">
						<option value="">--</option>
						<c:forEach items="${leibieList}" var="leibie">
							<option value="${leibie.dataCode}"
								<c:if test="${icon_leibie==leibie.dataCode}">selected</c:if>>${leibie.dataName}</option>
						</c:forEach>
						<option value="">全 部</option>
					</select> <a href="javascript:uploadFile();" class="list_button2">上 传</a> <input
						type="hidden" name="filetype" value="" />
				</form>
			</div>
			<div id="content">
				<h6 class="hr_line"></h6>
				<form name="myform" method="post">
					<div id="box_icon">
						<c:if test="${iconListSize!=0 }">
							<s:iterator value="iconList" id="icon">
								<div class="icon">
									<ul>
										<li><img border="0"
											src="${ctx}/jsp/dev/icon/${icon.icon_url}" width="62"
											height="51" /></li>
										<li class="xuan_s"><input type="checkbox" name="lim"
											value="${icon.icon_id}:${icon.icon_type}" /></li>
									</ul>
								</div>
							</s:iterator>
					</div>
					<h6 class="hr_line"></h6>
					<div
						style="width: 98%; height: 50px; FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0, startColorStr=#f6f6f8, endColorStr=#d2ebfa); padding-left: 20px;">
						分类显示：<select name="icon_leibie"
							onChange="searchIconList(this.value);" style="width: 120px">
							<option value="">--</option>
							<c:forEach items="${leibieList}" var="leibie">
								<option value="${leibie.dataCode}"
									<c:if test="${icon_leibie==leibie.dataCode}">selected</c:if>>${leibie.dataName}</option>
							</c:forEach>
							<option value="">全 部</option>
						</select> <input type="button" value="全选" onClick="checkAll();" /> <input
							type="button" value="反选" onClick="checkNo();" /> <input
							type="button" value="删除选中" onClick="delChecked();" />
					</div>
				</form>
				</c:if>
				<c:if test="${iconListSize==0 }">
					<div style="text-align: center">对不起，图标库中无图标！</div>
			</div>
			<h6 class="hr_line"></h6>
			<div
				style="width: 98%; height: 50px; FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0, startColorStr=#f6f6f8, endColorStr=#d2ebfa); padding-left: 20px;">
				分类显示：<select name="icon_leibie"
					onChange="searchIconList(this.value);" style="width: 120px">
					<option value="">--</option>
					<c:forEach items="${leibieList}" var="leibie">
						<option value="${leibie.dataCode}"
							<c:if test="${icon_leibie==leibie.dataCode}">selected</c:if>>${leibie.dataName}</option>
					</c:forEach>
					<option value="">全 部</option>
				</select>
			</div>
			</c:if>
		</div>
	</div>
	</div>
</body>
<script>
var obj = document.getElementsByName("lim");
function checkAll(){
	if(obj.length){ 
		for(var i=0;i<obj.length;i++){ 
			obj[i].checked = true; 
		} 
	}else{ 
		obj.checked = true; 
	}
}

function checkNo(){
	if(obj.length){ 
		for(var i=0;i<obj.length;i++){ 
			obj[i].checked = false; 
		} 
	}else{ 
		obj.checked = false; 
	}
}

function delChecked(){
	var checkFlag = false;
	if(obj.length){ 
		for(var i=0;i<obj.length;i++){ 
			if(obj[i].checked){
				checkFlag = true;
				break;
			}
		} 
	}else{ 
		if(obj.checked){
			checkFlag = true;
		}
	}
	if(!checkFlag){
		alert("尚未选中要删除的图标，无法进行删除操作！");
		return;
	}
	if(confirm("确定要执行删除操作吗？")){
		myform.action = "icon/iconAction!delBatch.action";
		myform.submit();
	}
}

function searchIconList(icon_leibie){
	window.location.href = "icon/iconAction!list.action?operflag=iconList&icon_leibie="+icon_leibie+"&time="+new Date();
}

function setFilename(filepath){
	var  pos2 = filepath.lastIndexOf(".");	
	form1.filetype.value = filepath.substring(pos2 + 1);
}

function uploadFile(){
	if(form1.filetype.value==""){
		alert("请选择要上传的图片文件！");
		//return false;
	}
	else{
		var ftype = form1.filetype.value;
		if(ftype=="gif" || ftype=="jpg" || ftype=="png"){
			form1.submit();
		}
		else{
			form1.reset();
			alert("只能上传图片文件，请重新选择！");
			//return false;	
		}
	}
	
}
</script>
</html>