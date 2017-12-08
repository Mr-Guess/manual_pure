<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">

<html style="height:100%; text-algin:center;">
<head>
<title>南京永坤信息科技有限公司</title>
<link rel="stylesheet" type="text/css" href="resources/js/jquery/easyui-1.3/themes/cupertino/easyui.css"/>
<link rel="stylesheet" type="text/css" href="resources/js/jquery/easyui-1.3/themes/icon.css"/>
<script src="resources/js/jquery/jquery-1.7.2.js" type="text/javascript"></script>
<script type="text/javascript" src="resources/js/jquery/easyui-1.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resources/js/jquery/easyui-1.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/GisUtil.js"></script>
<link href="templets/template_2/css/JS_QQMenuGroup2.css" rel="stylesheet" type="text/css" />
<link href="templets/template_2/css/template2_qyd.css" rel="stylesheet" type="text/css" />
<link href="css/newMenu.css" rel="stylesheet" type="text/css" />
<style>
#menuPosition {font-size:12px;}
#menuPosition .firstPosition{color:#5F6062; font-weight: normal; font-size:12px;}
#menuPosition .position3{color:#5F6062;font-weight: normal;font-size:12px;}
#menuPosition .position2{color:#5F6062; font-weight: normal; font-size:12px;}
#menuPosition .position1{color:#5F6062; font-size:12px;font-weight:blod;}
#menuPosition .menuSplitStr{color:#5F6062; font-weight: normal;}
#user_clor{ color:#fff; font-family:"微软雅黑"; font-size:12px; float:left; padding-left:20px; background:url(images/user.png) no-repeat left center; }
#change_mode{  float: right; height: 90px; line-height: 160px; width: 101px;  }
#wel_user{ float:left; padding-bottom:5px;  margin: 0 20px 0 10px; line-height:31px }
#re_fresh{ background:url(images/refs.png) 0 center no-repeat; padding: 10px 0 11px 28px; font-family: "微软雅黑"; font-size: 13px;}
#quite{ background:url(images/quit_ne.png) 0 4px no-repeat; padding: 10px 0 11px 8px; margin-top:8px; font-family: "微软雅黑"; font-size: 13px; }

#yewu_mod{ background: url(images/ywms.png) center 0 no-repeat; color:#fff; font-family: "微软雅黑"; font-size: 13px; padding: 50px 0 0; }
#map_mod{ background: url(images/mapms.png) center 0 no-repeat; color:#fff; font-family: "微软雅黑"; font-size: 13px; padding: 50px 0 0;}

.topshe { background:url(images/top_hd_02.gif) repeat-x;}
#link_clor { float:right; line-height:31px; }
#nav { float:left; /*background:#000;*/ border:0; width:20%; height:auto;   }
#nav LI { float:none}
#nav LI A { background:none; color:#3bb6bb; font-size:16px; font-family:"微软雅黑"; text-align:center; height: 45px; line-height: 45px; padding: 0; width:100% }
#nav li ul { margin:-23px 0 0 100%; }
#nav LI:hover LI > A { width:auto}
#nav LI:hover LI:hover > A { font-weight:none; color:black; /*background:#498ac1;url(images/lllibg.png) 26px 12px no-repeat;*/ }
#web_site { position:absolute; right:-1px; width:87%;}

/*新菜单样式*/
#nav { float:left; background:url(images/nav_back.jpg) repeat-x left top;width:13%; height:100%; padding:2px 0 0;}
#nav a {
 display: block;
 width: 100%; 
 text-align:center;
 color:#fff !important; font-weight:bold; font-size:13px; height:30px; line-height:30px;
 /*Width(一定要)，否则下面的Li会变形*/
}
#nav li {
 background:url(images/nav_li_back.jpg) repeat-x left top; /*一级目录的背景色*/
 /*border-top:#3c8dcc 1px solid;
 border-bottom:#3c8dcc 1px solid;*/  /*下面的一条白边*/
 float:left;
 width:100%
 /*float：left,本不应该设置，但由于在Firefox不能正常显示
 继承Nav的width,限制宽度，li自动向下延伸*/
}
#nav li a:hover{
 /*background:url(images/nav_a_back.jpg) repeat-x top left; color:#fff !important; /*一级目录onMouseOver显示的背景色*/
}
#nav a:link  {
 color:#666; text-decoration:none;
}
#nav a:visited  {
 color:#666;text-decoration:none;
}
#nav a:hover  {
 color:#FFF;text-decoration:none;font-weight:bold;
}

/*==================二级目录===================*/
#nav li>ul {
 list-style:none;
 padding-left:9px;
 width:169px;
 background:url(images/ul_topback.png) no-repeat top center;
 color: black;
}
#nav li ul li{width:169px;  border:0; height:45px; line-height:45px; /*background:#2c2c2c !important;*/ }
#nav li ul li a{
		color:#000;
        text-align:center;
        width:100%; 
		 /*background:#2C2C2C url(images/lllibg.png) 10px 12px no-repeat !important;*/
		height:45px; 
		line-height:45px;
 /* padding-left二级目录中文字向右移动，但Width必须重新设置=(总宽度-padding-left)*/
}
/*下面是二级目录的链接样式*/
#nav li ul a:hover {
 color:#000 !important;
 text-decoration:none;
 font-weight:none;
 /*background:url(images/a_hover.png) center left no-repeat;*/
 /* 二级onmouseover的字体颜色、背景色*/
}

/*设置选中时样式*/
.selectedCss{
	
}
-->
#nav li ul li a:hover li a{color:#000;}
<%-- #main { height:680px !important; } --%>

</style> 
<script src="js/publicfun.js"></script>
<script type="text/javascript">
//向上取整
	function DivUp(number1, number2) {
	    var n1 = Math.round(number1);
	    var n2 = Math.round(number2);
	    var result = n1 / n2;
	    if (result >= 0) {
	    	result = Math.ceil(result);
	    } else {
	    	result = Math.floor(result);
	    }
	    return result;
	}
	//向下取整
	function DivDown(number1, number2) {
	    var n1 = Math.round(number1);
	    var n2 = Math.round(number2);
	   // alert(n1+"::::::::"+n2);
	    var result = n1 / n2;
	    if (result >= 0) {
	    	result = Math.floor(result);
	    	//alert(result);
	    
	    } else {
	    	result = Math.ceil(result);
	    	//alert(result);
	    }
	    return result;
	}
	//菜单等级
	var level = 0;
	//1级菜单数量
	var levelOneCount = 0;
	//1级菜单1组10个
	var groupCount = DivDown($(document).width(),150);
	//alert($(document).width);
	//当前显示的菜单组number:group0
	var groupShowNumber = 1;
	//一共多少组
	var groupTotalCount = 0;
	function showall(menu_list, parent) {
        for (var menu in menu_list) {
        	level++;
        	if(level==1){
        		levelOneCount++;
        	}
        	if(typeof(menu_list[menu].children) == "undefined" )
        		continue;
           	if(level==1){
           		var groupNumber = DivUp(levelOneCount,groupCount);
                var li = $("<li class=\"level"+level+" group"+groupNumber+"\"></li>");
           	}else{
                var li = $("<li class=\"level"+level+"\"></li>");
           	}
            var url = menu_list[menu].menuUrl == ''? 'javascript:void(0)' : menu_list[menu].menuUrl;
         	var menuId = menu_list[menu].menuId;
            if(menu_list[menu].menuUrl.indexOf(".action") == -1 && url.indexOf("javascript") == -1 && menu_list[menu].menuUrl.indexOf("Action") == -1){
           		if(menu_list[menu].menuUrl == ''){
            	$(li).append('<a href="'+url+'" target="main" >'+ menu_list[menu].menuName+'</a>')
            		.append("<ul></ul>").appendTo(parent);
	            } else {
	            	$(li).append('<a href="autoJump.action?menuId='+menuId+'&url='+url+'" target="main" onclick="addMenuInfo(this)">'+ menu_list[menu].menuName+'</a>')
	        		.append("<ul></ul>").appendTo(parent);
	            }
           	}else{
           		if(menu_list[menu].menuUrl == ''){
            	$(li).append('<a href="'+url+'" target="main" >'+ menu_list[menu].menuName+'</a>')
            		.append("<ul></ul>").appendTo(parent);
	            } else {
	            	$(li).append('<a href="'+url+'" target="main" onclick="addMenuInfo(this)">'+ menu_list[menu].menuName+'</a>')
	        		.append("<ul></ul>").appendTo(parent);
	            }
           	}
            if (menu_list[menu].children.length > 0) {
                showall(menu_list[menu].children, $(li).children().eq(1));
            }
            level--;
        }
      	//设置样式
      	$(".level1").mousemove(function(){
        	$(this).attr("style","background:url(${ctx}/images/nav_a_back.jpg) repeat-x left;color:white !important;");
        	$(this).find("a:eq(0)").attr("style","color: #498ac1!important; font-weight:bold;");
        });
        $(".level2").mousemove(function(){
        	var level1=$(this).parents(".level1");
        	$(level1).attr("style","background:url(${ctx}/images/nav_a_back.jpg;color:#000 !important;");
        	$(level1).find("a:eq(0)").attr("style","color:#000!important; font-weight:bold;");
        });
 		$(".level3").mousemove(function(){
 			var level2=$(this).parents(".level2");
        	$(level2).attr("style","color:#fff !important;");
        	$(level2).find("a:eq(0)").attr("style","color:#000!important; font-weight:bold;");
        });
 		$(".level1").mouseout(function(){
        	$(this).attr("style","");
        	$(this).find("a:eq(0)").attr("style","");
        });
		$(".level2").mouseout(function(){
			var level1=$(this).parents(".level1");
        	$(level1).attr("style","");
        	$(level1).find("a:eq(0)").attr("style","");
        });
        $(".level3").mouseout(function(){
        	var level2=$(this).parents(".level2");
        	$(level2).attr("style","");
        	$(level2).find("a:eq(0)").attr("style","");
        });
      	
    }
	//菜单位置中的菜单分隔符
	var menuSplitStr = '&nbsp;>&nbsp;';
	//菜单位置级别
	var menuPositionLevel = 0;
	//菜单点击操作记录
	var operationRecord = [];
	//菜单当前显示index
	var operationIndex = 0;
	//是否是前进后退的模拟点击操作
	var operationSimulator = false;
	//后退操作
	function goBack() {
		if(operationIndex<=1)
			return;
		operationIndex--;
		operationSimulator = true;
		var obj = operationRecord[operationIndex-1];
		obj.click();
	}
	//前进操作
	function goForward() {
		if(operationIndex>=operationRecord.length)
			return;
		operationIndex++;
		operationSimulator = true;
		var obj = operationRecord[operationIndex-1];
		obj.click();
	}
	//添加 当前位置信息
	function addMenuInfo(obj){
		gis.showMain();
		//判断是否为前进或后退操作
		if(!operationSimulator){
			if(operationIndex<operationRecord.length) {
				operationRecord.splice(operationIndex,operationRecord.length-operationIndex);
				operationIndex = operationRecord.length;
			}
			operationRecord.push(obj);
			operationIndex++;
		} else {
			operationSimulator = false;
		}
		$('#menuPosition').html('<span class="firstPosition">您当前的位置：</span>');
		addAllMenuInfo($(obj),$('#menuPosition'));
	}
	function addAllMenuInfo(obj,menuPosition){
		menuPositionLevel++;
		var parent = $(obj).parents('li').parents('li').children().eq(0);
		if($(parent).html() != null){
			addAllMenuInfo(parent,menuPosition);
			$(menuPosition).append('<span class="menuSplitStr">'+menuSplitStr+'</span>');
		}
		$(menuPosition).append('<span class="position' + menuPositionLevel + '">' + $(obj).html() +'</span>');
		menuPositionLevel--;
	}
	
	function toggleMenu(){
		for(var i =0 ; i<=groupTotalCount ;i++){
			if(i!=groupShowNumber)
				$(".group"+i).hide();
		}
		$(".group"+groupShowNumber).show();
	}
	//菜单下一页
	function goAfter(){
		if(groupShowNumber<groupTotalCount)
			groupShowNumber++;
		toggleMenu();
	}
	//菜单上一页
	function goBefore(){
		if(groupShowNumber>1)
			groupShowNumber--;
		toggleMenu();
	}
	$(function(){
		$.post('menu/menuAction!initMenu2.action',{checkType:'ent'},function(data){
			var data = eval('(' + data + ')');
			var showlist = $('#nav');
            showall(data, showlist);
            groupTotalCount = DivUp(levelOneCount,groupCount);
            if(groupTotalCount<=1){
            	$('#nav div.lee').hide();
            	return;
            }
            toggleMenu();
		});
		gis = new GisUtil({mainFrame:$('#main'),gisFrame:$('#GisMain')});
	});
	function gisInit() {
		//gis.setParams({gisFunction:window.frames['Gismail'].linkFromBusinessPage});
	}
	function toggleBtn() {
		$('#yewu_mod').toggle();
		$('#map_mod').toggle();
	}
</script>
</head>
<body id="body">
<div id="fullScreenDiv" style="position: absolute;margin:0px 0px;padding:0px 0px;height:100%;width:100%;left:0px;top:0px;z-index:-1;"></div>
<div id="bodyHeightDiv" style="position:absolute; top:0px;left:0px;width:100%;height:100%;"></div>
		<div class="toptitle">
        	<div class="topshe" >
        <div class=""></div>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr><td align="left">         
        <div id="wel_user" style="position: absolute; right: 220px;top:65px;">
        <div id="user_clor">当前用户：${user_name}</div><script type="text/javascript" language="javascript">
			    today = new Date();
			    function initArray()
			    {
				    this.length=initArray.arguments.length                
				    for(var i=0;i<this.length;i++)
				    this[i+1]=initArray.arguments[i]
			    }       
			    var d = new initArray
			    (
				    "星期日",                    
				    "星期一",                 
				    "星期二",
				    "星期三",
				    "星期四",    
				    "星期五",                       
				    "星期六"
			    );                                                             
	
			    document.write
			    (
				    "<span class='datewords'> ",
				    today.getFullYear()," 年 ",
				    today.getMonth()+1," 月 ",
				    today.getDate()," 日 ",
				    d[today.getDay()+1],
				    "</span>" 
			    );
		    </script>
            </div>
            <div id="link_clor" style="position: absolute;  right:120px;top:65px;">
				<a class="dock-item" href="#" onClick="location.reload(true)"><span id="re_fresh">刷新</span></a><span>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;</span><a class="dock-item" href="${ctx}/logout!logout.action" style=" margin-right:10px;"><span id="quite">退出</span></a>
                </div></td></tr>
        <input type="hidden" id="yhm" value="${user_name} ">
        </table>
        
		  </div>
			<div class="topright"></div>
            <div id="change_mode"><a id="yewu_mod" href="javascript:gis.toggle();toggleBtn();" class="forward" >业务模式</a><a id="map_mod" href="javascript:gis.toggle();toggleBtn();" class="forward" style="display:none;">地图模式</a></div>
			
		</div>
		<div class="datewordspos" id='ces'>
			<%-- <div class="userinfo">当前用户：${user_name}</div> --%>
		</div>
        
        <!-- 这里是菜单位置 -->
			<ul id="nav">
			<div class="lee" style="position: absolute;float: right; margin: 0px 10px 0 0; right:0; color:#FFF; height:30px;">
            <a href="#" onclick="goBefore();return false;" style="background:none; border:none"><img src="templets/template_2/top/roll_1.gif"/></a><a href="#" onclick="goAfter();return false;" style="background:none; border:none"><img src="templets/template_2/top/roll_2.gif" /></a></div>
			</ul>
            
			<div id="web_site">
            <div class="top_bottom"><img src="templets/template_2/top/house.png" style=" float:left;" /><h2 id="menuPosition"><span class="firstPosition">您当前的位置</span></h2>
            <div style="float:right;margin:5px;line-height:20px;">
            <a href="javascript:goBack();" class="back" style="color:#222;"><img src="images/weihai_index/back2.png" id="Image1" style="vertical-align:middle;margin:0px 5px 0 5px;" />后退</a>&nbsp;|
            <a href="javascript:goForward();" class="forward" style="color:#222;">&nbsp;前进<span class="forward"><img src="images/weihai_index/forward2.png" id="Image2" style="vertical-align:middle;margin:0px 0px 0 5px;" /></span></a>
            
            </div>
            </div>
	</div>
	
	<div id="iframeMainDiv" class="aui_state_lock aui_state_focus" style="left: 180px; top: 30px; width: width: 80%; position: absolute;">
		<table class="aui_border">
			<tbody>
				<tr>
				<td class="aui_main" id="iframeMainTd" style="visibility: visible;">
				<iframe id="main" name="main" src="${ctx}/welcome.jsp" frameborder="0" style="border: 0px currentColor; border-image: none; width: 100%; height: 100%;z-index:-1;"></iframe>
					<iframe id="GisMain" name="GisMain" width="100%" src="" style="display:none;" frameborder="0" onload="gisInit()" ></iframe>
				</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="topWindow" style="display: none;"></div>
	
	<script type="text/javascript">
		
		var BODYWIDTH = "";
		var BODYHEIGHT = "";
		$(function(){
			szMainIframe();
		});
		
		function szMainIframe(){
			BODYWIDTH = Number($("#fullScreenDiv").css("width").replace("px",""));
			BODYHEIGHT = Number($("#fullScreenDiv").css("height").replace("px",""));
			$("#iframeMainDiv").css("left",Number($("#nav").css("width").replace("px",""))+2);
			$("#iframeMainTd").css("height",(BODYHEIGHT-148));
			$("#iframeMainTd").css("width",BODYWIDTH-Number($("#nav").css("width").replace("px",""))-2);
			$("#iframeMainDiv").css("top",145);
			
		}
		window.onresize= function(){
			szMainIframe();
		};
	</script>
</body>
</html>