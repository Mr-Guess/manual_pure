<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>图标管理列表</title>
<script type="text/javascript"
	src="../../../resources/js/jquery/jquery-1.7.2.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/jsp/dev/icon/fancybox/jquery.fancybox-1.3.1.css"
	media="screen" />
<link href="${ctx}/jsp/dev/icon/css/webplan.css" rel="stylesheet"
	type="text/css" />

<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

body {
	font-family: Arial, "宋体", Tahoma;
	font-size: 12px;
}

#wrap {
	margin: 0 auto;
	padding-top: 20px;
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

#top_icon h3,h4 {
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
	padding-top: 10px; *
	padding-top: 4px; /* 1024: width:800px; 1280: width:1060px; */
	overflow-y: auto;
}

hr {
	color: #d6d5d1;
}

.icon {
	background: url(./images/tab_bg.gif) no-repeat;
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
	background: url(./images/line.gif) repeat-x;
	height: 1px;
	width: 98%;
	margin: 0 auto;
}
</style>
</head>
<body>
	<div id="wrap">
		<div id="top_icon">
			<img src="${ctx}/jsp/dev/icon/images/top_icon.gif" width="38"
				height="32" />
			<h3>图标管理</h3>
			<h4>
				(分类显示：<select name="icon_leibie"
					onchange="searchIconList(this.value);" style="width: 120px">
					<option value="">--</option>
					<c:forEach items="${leibieList}" var="leibie">
						<option value="${leibie.dataCode}"
							<c:if test="${icon_leibie==leibie.dataCode}">selected</c:if>>${leibie.dataName}</option>
					</c:forEach>
					<option value="">全 部</option>
				</select>)
			</h4>
		</div>
		<div id="content">
			<h6 class="hr_line"></h6>
			<div id="box_icon">
				<c:if test="${iconListSize!=0 }">
					<s:iterator value="iconList" id="icon">
						<div class="icon">
							<ul>
								<li><a
									href="javascript:selectIcon('${icon.icon_id}','${icon.icon_type }');"
									class="lightbox"> <img border="0"
										src="${ctx}/jsp/dev/icon/${icon.icon_url}" width="62"
										height="51" />
								</a></li>
							</ul>
						</div>
					</s:iterator>
				</c:if>
				<c:if test="${iconListSize==0 }">
					<span style="text-align: center">对不起，图标库中无图标！</span>
				</c:if>
			</div>
			<h6 class="hr_line"></h6>
		</div>
	</div>
	<script type="text/javascript">
function selectIcon(id,type){
	var icon_url = "..\\icon\\upload\\" + id + "." + type;
	window.opener.document.getElementById("imgOne").value = "jsp\\dev" + icon_url;
	window.opener.document.getElementById("imgDiv").style.display = "";
	window.opener.document.getElementById("imgShow").src = icon_url;
	
	window.close();
}

function searchIconList(icon_leibie){
	window.location.href = "icon/iconAction!list.action?operflag=menuSelect&icon_leibie="+icon_leibie+"&time="+new Date();
}
</script>
</body>
</html>