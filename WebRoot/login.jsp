<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ay.framework.shiro.SystemParameter"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String jquery_path = request.getContextPath();
	String jquery_basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ jquery_path + "/";
	String login_authcode_switch = SystemParameter.getLogin_authcode_switch();
	request.setAttribute("login_authcode_switch", login_authcode_switch);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>地市级基础版本V2.0</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css"
		href="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/themes/cupertino/easyui.css">
		<link href="css/fashionfoucs_lrtk.css" type=text/css rel=stylesheet>
			<script src="js/fashionfoucs.js" type="text/javascript"></script>
			<script src="js/jquery.plugin.min.js" type="text/javascript"></script>
			<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
			<script type="text/javascript"
				src="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/jquery.easyui.min.js"></script>
			<script type="text/javascript"
				src="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/locale/easyui-lang-zh_CN.js"></script>
			<script type="text/javascript">

//验证密码长度
	$.extend($.fn.validatebox.defaults.rules, {  
	    minLength: {  
	        validator: function(value, param){  
	            return value.length >= param[0];  
	        },  
	        message: '密码长度不能小于8位.'  
	    }  
	});  
		 if ($.browser.msie){ 
			 /**
			 if ($.browser.version == "6.0"){		   
			   $("#login").html("");		  
			   $("#login").css("backgroundImage","url(templets/login_images/bg_browerupdate1.png)"); 
			   $("#login").css("width","711px");
			   $("#login").css("height","315px");
			   $("#login").append("<div style='margin-left:185px;margin-top:140px;'><ul style=' list-style:none;'><li style='float:left;margin-right:5px;'><input type='button' onclick='browerUpdate()' style='background:url(templets/login_images/btn_yes.png) no-repeat;width:40px;height:22px;display:block;border:0; cursor:hand;' /></li> <li><input type='button' onclick='window_close()' style='background:url(templets/login_images/btn_no.png) no-repeat;width:40px;height:22px;display:block;border:0; cursor:hand;' /></li> </ul></div>");
			   return;
			 }
			 */
			 if(parseInt($.browser.version) < 8) {
				 $.messager.alert('警告','您的IE版本太低，请升级IE版本!<br/><a target="_blank" href="http://download.microsoft.com/download/1/6/1/16174D37-73C1-4F76-A305-902E9D32BAC9/IE8-WindowsXP-x86-CHS.exe">点此升级</a>'
						 ,'info', function() {
				 });
			 }
		 } else {
			 //$.messager.alert('警告','推荐使用IE浏览器!','info');
		 }
 /**
 function browerUpdate(){
 	window.location.href="http://windows.microsoft.com/zh-CN/internet-explorer/products/ie/home";
 }
 function window_close(){
 	var info = window.confirm("您确定不升级IE版本吗?确定，您访问的页面!");
 	if(info){
 	  window.close();
 	}else{
 	  return;
 	}
 	
 }
 */
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
</script>
			<style>
.easyui-validatebox {
	width: 190px;
	height: 40px;
	background: url(images/input.png) no-repeat left center;
	border: none;
	padding-left: 15px;
	line-height: 40px
}
</style>
</head>

<body
	onLoad="MM_preloadImages('images/weihai_index/index_button_h.png','images/weihai_index/index_zc_h.png')">
	<form name="loginForm" method="post" action="login!login.action" onmousewheel="return false;"
		onKeyDown="javascript:Enter()">

		<input name="SkinType" value="2" type="hidden" />
		<!-- 代码 开始 -->
		<div id="banner">
			<ul id="banner_img">
				<li class="item1">
					<div class="wrapper">
						<div class="ad_txt">
							<h2>安全产业系统建设</h2>
							<p>
								深入排查整治安全生产隐患，督促全面落实安全生产责任制度，<br />
								铁腕打击非法违法和违规违章行为，大力推进煤矿安全治本攻坚，<br /> 切实抓好重点行业领域安全整治，严格监管执法和事故查处，<br />
								进一步夯实安全生产基础，坚决堵塞安全生产监管漏洞，有效防<br />
								范和遏制重特大安全事故发生，促进全国安全生产形势持续稳定好转。
							</p>
						</div>
						<div class="ad_img">
							<img src="images/1.png" width="488" height="348"
								alt="互联网品牌传播解决方案" />
						</div>
					</div>
				</li>
				<li class="item2">
					<div class="wrapper">
						<div class="ad_txt">
							<h2>应用系统开发</h2>
							<p>
								自主研发、完善的开发框架。<br /> 详细的需求调研及解决方案。<br /> 实施项目经验丰富的项目团队。
							</p>
						</div>
						<div class="ad_img">
							<img src="images/2.png" width="409" height="334"
								alt="Web应用(B/S)定制开发" />
						</div>
					</div>
				</li>
				<li class="item3">
					<div class="wrapper">
						<div class="ad_txt">
							<h2>高效率实时监控系统</h2>
							<p>
								建立权威的危害隐患基础数据库，为危害隐患监管提供信息支持；对危害<br />
								监督被管理单位分级监督；建立危害日常监管、应急救援、危害隐患监管<br />
								辅助决策系统。通过该系统的建设，加强企业危害隐患信息的采集、加工，进<br />
								行本地区危害隐患情况进行分析、汇总，形成各类辅助分析数据，并配以GIS<br />
								地理信息系统，动态掌握本地区的各企业危害隐患的分布情况。
							</p>
						</div>
						<div class="ad_img">
							<img src="images/3.png" width="493" height="319" alt="互联网时代的整合营销" />
						</div>
					</div>
				</li>
				<li class="item4">
					<div class="wrapper">
						<div class="ad_txt">
							<h2>产业安全监督管理</h2>
							<p>
								针对过程工业的生产特点，以典型工艺和过程为研究对象，针对工业火灾、<br />
								爆炸、泄漏等典型事故过程的危险状态及其存在与转化条件、工业灾害<br />
								事故成灾机理及其动力学过程，用数学、力学和热物理理论揭示灾害发生<br />
								发展的规律,建立灾害过程理论模型，为事故预防和灾情控制提供技术支撑。
							</p>
						</div>
						<div class="ad_img">
							<img src="images/4.png" width="349" height="328" alt="您的网站全职管家" />
						</div>
					</div>
				</li>

			</ul>
			<div id="banner_ctr">
				<div id="drag_ctr"></div>
				<ul>
					<li class="first-item">网站建设</li>
					<li>安监行业系统建设</li>
					<li>应用系统开发</li>
					<li>高效率监控</li>
					<li>工业安全监管</li>
				</ul>
				<table>
					<tr>
						<td colspan="2"><input type="radio" name="checkType"
							value="gov" checked /><span
							style="right: 20px; font-size: 14px; color: #000">政府端</span> <input
							type="radio" name="checkType" value="ent" /><span
							style="font-size: 14px; color: #000">企业端</span></td>
						<td></td>
					</tr>
					<tr>
						<td class="dl"><input id="username" name="username"
							value="administrator" class="easyui-validatebox"
							data-options="required: true" missingMessage="请输入帐号" tabindex="1" /></td>
						<td class="in_lock" rowspan="2"><a href="#"
							onClick="form_sub();"><img src="images/in_dl.png"
								onMouseOut="MM_swapImgRestore()" /></a></td>
						<td rowspan="2"><a href="register.jsp"><img
								src="images/in_zc.png" /></a></td>
					</tr>
					<tr>
						<td class="dl"><input id="password" name="password"
							value="admin123" type="password" class="easyui-validatebox"
							data-options="required: true, validType:'minLength[8]'"
							missingMessage="请输入登录 密码" tabindex="2" /></td>
					</tr>
				</table>
				<div id="drag_arrow"></div>
			</div>
		</div>
		<script type="text/javascript" src="js/fashionfoucs.js"></script>
		<!-- 代码 结束 -->
	</form>
	<script>
function form_sub(){
 	var username = document.loginForm.username.value;
	var password = document.loginForm.password.value;
	var checkTypeValue;
	var menutype1;
	l=document.getElementsByName("checkType")  
	 for(i=0;i<l.length;i++)  
	 {  
	 if(l[i].checked){
	  checkTypeValue=l[i].value;
	  } 
	  if(checkTypeValue==2){
	  		menutype1='1';
	  }else{
	  		menutype1='0';
	  }
	 }  
	var validCode='';
	if(document.loginForm.validCode)
		validCode = document.loginForm.validCode.value; 
 	if($("#username").validatebox("isValid")&&$("#password").validatebox("isValid")){
		$.ajax({
			url:"login!login.action",
			type:"post",
			data:"username="+username+"&password="+password+"&validCode="+validCode+"&checkType="+checkTypeValue+"&menu_type1="+menutype1,
			success:function(ret){
				if(ret instanceof Object){
					if(!ret.operateSuccess){
						$.messager.show({
                            title:'失败',
                            msg:ret.operateMessage,
                            timeout:2000,
                            showType:'slide'
                        });
						changeCode(document.getElementById("validCodeImg"));
					}
				}else{
					document.loginForm.submit();
				}
			}
		});
	} 	

}

function Enter(){
	if(event.keyCode==13){
		form_sub();
	}	
}
function changeCode(obj){
	if(obj){		
	   //获取当前的时间作为参数，无具体意义  
	   var timenow = new Date().getTime();  
      	//每次请求需要一个不同的参数，否则可能会返回同样的验证码  
	   //这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。  
	   obj.src="validCode.action?d="+timenow;  
	}
}
</script>
</body>
</html>