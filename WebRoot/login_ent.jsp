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
<title>地市级基础版本v2.0</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link href="css/archefoucs.css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css"
		href="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/themes/cupertino/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/themes/icon.css">
			<script type="text/javascript"
				src="<%=jquery_basePath%>resources/js/jquery/jquery-1.7.2.js"></script>
			<script src="js/archefoucs.js" type="text/javascript"></script>
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

	$(document).ready(
	  function() { 
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
 	});
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
	height: 36px;
	border: 0;
	line-height: 36px;
	font-size: 16px; *+
	line-height: 36px;
	padding-left: 20px;
	background: none;
}
</style>
</head>

<body
	onLoad="MM_preloadImages('images/weihai_index/index_button_h.png','images/weihai_index/index_zc_h.png')">
	<form name="loginForm" method="post" action="login!login.action"
		onKeyDown="javascript:Enter()">
		<input name="SkinType" value="2" type="hidden" />
		<!-- 代码 开始 -->
		<div class="header">
			<center>
				<div class="top_ent"></div>
			</center>
			<div class="gg" id="gg">
				<div class="ggs">
					<div class="ggBox" id="ggBox">
						<a target="_blank" style="z-index: 3; opacity: 4;"> <img
							src="images/5.jpg" alt="" / heigt="90%"></a> <a target="_blank">
							<img src="images/6.jpg" alt="" heigt="90%" />
						</a> <a target="_blank"> <img src="images/7.jpg" alt=""
							heigt="90%" /></a> <a target="_blank"> <img src="images/8.jpg"
							alt="" heigt="90%" /></a>
					</div>
				</div>
				<div class="ggb">
					<div class="ggBtns" id="ggBtns">
						<a href='javascript:void(0)' class="ggOn"><em></em></a> <a
							href='javascript:void(0)'><em></em></a> <a
							href='javascript:void(0)'><em></em></a> <a
							href='javascript:void(0)'><em></em></a>
					</div>
				</div>
			</div>
			<div class="loginiput">
				<table width="970" align="center">
					<tr>
						<td class="t_item">用户名&nbsp;:</td>
						<td width="210" align="left">
							<div id="zfDiv">
								<input id="username" name="username" value="admin"
									class="easyui-validatebox" data-options="required: true"
									missingMessage="请输入帐号" />
							</div>
						</td>
						<!--<td class="td_jz" width="110">
	  	<input name="member" type="checkbox" class="cb" value="记住密码"/><span class="jzmm">记住密码</span>
	  </td>-->
						<td class="t_item">密&nbsp;&nbsp;&nbsp;码&nbsp;:</td>
						<td align="left">
							<div id="zfDiv">
								<input id="password" name="password" value="11111111"
									type="password" class="easyui-validatebox"
									data-options="required: true, validType:'minLength[8]'"
									missingMessage="请输入登录 密码" />
							</div>
						</td>
						<td><a href="javascript:void(0)" onClick="form_sub()"><img
								src="images/weihai_index/button_etn.png" /></a></td>
						<td width="170"><a href="register.jsp"><img
								src="images/weihai_index/button_etnzc.png"></a></td>
					</tr>
				</table>
			</div>
		</div>
		<!--<center>
<div id="wap">
    <div id="main">
     <div id="slider">
 <ul id="sliderContent">
  <li class="sliderImage">
   <img src="images/1.jpg" width="795" height="290" />
   <span class="bottom">欢迎使用宿迁市生态化工园区监管平台。</span>
  </li>
  <li class="sliderImage">
   <img src="images/2.jpg" width="795" height="290" />
   <span class="bottom">安全第一，综合治理，预防为主。</span>
  </li>
  <li class="sliderImage">
   <img src="images/3.jpg" width="795" height="290" />
   <span class="bottom">科技创新，继往开来。</span>
  </li>
  <li class="sliderImage">
   <img src="images/4.jpg" width="795" height="290" />
   <span class="bottom">转作风，重规范，提效能。</span>
  </li>
  <div class="clear sliderImage"></div>
 </ul>
</div>
    <div id="content">
    
      </div>
    </div>
</div>
</center>-->
	</form>
	<script>
function form_sub(){
 	var username = document.loginForm.username.value;
	var password = document.loginForm.password.value;
	var validCode='';
	if(document.loginForm.validCode)
		validCode = document.loginForm.validCode.value; 
 	if($("#username").validatebox("isValid")&&$("#password").validatebox("isValid")){
		$.ajax({
			url:"login!login.action",
			type:"post",
			cache:false,
			data:"username="+username+"&password="+password+"&validCode="+validCode,
			success:function(ret){
				if(typeof ret == 'string' && ret.operateSuccess==null){
					try {
					var ret = eval('('+ret+')');
					} catch(e) {
						document.loginForm.submit();
						return;
					}
				}
				if(ret instanceof Object){
					if(!ret.operateSuccess){
						$.messager.show({
                            title:'失败',
                            msg:ret.operateMessage,
                            timeout:2000,
                            showType:'slide'
                        });
						changeCode(document.getElementById("validCodeImg"));
					}else{
						document.loginForm.submit();
					}
				}
			}
		});
	} else {
		$.messager.show({
            title:'提示',
            msg:'请输入正确的帐号和密码',
            timeout:2000,
            showType:'slide'
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