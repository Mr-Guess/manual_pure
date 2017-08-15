
<%
	String jquery_path = request.getContextPath();
	String jquery_basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ jquery_path + "/";
%>
<link rel="stylesheet" href="${ctx}/css/animate.css"/>
<link rel="stylesheet" type="text/css" href="<%=jquery_basePath%>resources/js/jquery/easyui-1.3.6/themes/metro-self/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=jquery_basePath%>resources/js/jquery/easyui-1.3.6/themes/icon.css">
<!-- <link rel="stylesheet" type="text/css" href="<%=jquery_basePath%>css/newCrud.css"> -->

<script type="text/javascript" src="<%=jquery_basePath%>resources/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
<!-- ztree -->
<link rel="stylesheet"
	href="<%=jquery_basePath%>resources/js/jquery/zTree-3.3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/zTree-3.3/jquery.ztree.core-3.3.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/zTree-3.3/jquery.ztree.excheck-3.3.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/zTree-3.3/jquery.ztree.exedit-3.3.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/zTree-3.3/jquery.ztree.exhide-3.3.js"></script>
<!-- jquery autocomplete -->
<link rel="stylesheet"
	href="<%=jquery_basePath%>resources/js/jquery/autocomplete/jquery.autocomplete.css"
	type="text/css">
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/autocomplete/autocomplete.js"></script>
<!-- ajaxfileupload -->
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/ajaxfileupload/ajaxfileupload.js"></script>
<!-- form -->
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/form/jquery.form.js"></script>

<!-- qtip2 -->
<link rel="stylesheet" type="text/css"
	href="<%=jquery_basePath%>resources/js/jquery/qtip2/jquery.qtip.css">
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/qtip2/jquery.qtip.js"></script>

<!-- Permission js -->
<script type="text/javascript"
	src="<%=jquery_basePath%>js/Permission.js"></script>

<!-- common functions -->
<script type="text/javascript"
	src="<%=jquery_basePath%>js/commonFunctions.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>js/windowUtil.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>js/easyui-loding.js"></script>

<!-- dateFormatter -->
<%-- <script type="text/javascript" src="<%=jquery_basePath%>js/validator.js"></script> --%>

<!-- json2 -->
<script type="text/javascript" src="<%=jquery_basePath%>js/json2.js"></script>
<!-- my97 -->
<script type="text/javascript"
	src="<%=jquery_basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>js/easyui-my97.js"></script>
