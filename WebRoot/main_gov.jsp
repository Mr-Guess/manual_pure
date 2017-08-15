<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html style="height:100%; text-algin:center;">
<head>

<title>地市级基础版本v2.0</title>
<link rel="stylesheet" type="text/css"
	href="resources/js/jquery/easyui-1.3/themes/cupertino/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="resources/js/jquery/easyui-1.3/themes/icon.css" />
<script src="resources/js/jquery/jquery-1.7.2.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/navstyle-zw.css" type="text/css" />
<link rel="stylesheet" href="css/animate.css"/>
<script type="text/javascript" src="resources/js/jquery/easyui-1.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resources/js/jquery/easyui-1.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/GisUtil.js"></script>
<!-- <link href="templets/template_2/css/JS_QQMenuGroup2.css" rel="stylesheet" type="text/css" /> -->
<link href="templets/template_2/css/template_zw.css" rel="stylesheet"
	type="text/css" />
<!-- <link href="css/newMenu_zf.css" rel="stylesheet" type="text/css" /> -->
<style>
.ui-skin-nav {
    float: right;
	padding: 0;
	margin-right: 10px;
	list-style: none outside none;
	height: 30px;
}

.ui-skin-nav .li-skinitem {
    float: left;
    font-size: 12px;
    line-height: 30px;
	margin-left: 10px;
    text-align: center;
}
.ui-skin-nav .li-skinitem span {
	cursor: pointer;
	width:10px;
	height:10px;
	display:inline-block;
}
.ui-skin-nav .li-skinitem span.cs-skin-on{
	border: 1px solid #FFFFFF;
}

.ui-skin-nav .li-skinitem span.gray{background-color:#026997;}
.ui-skin-nav .li-skinitem span.default{background-color:#1A70B7;}
.ui-skin-nav .li-skinitem span.bootstrap{background-color:#0C79DA;}
.ui-skin-nav .li-skinitem span.blue{background-color:#188AFC;}
.ui-skin-nav .li-skinitem span.metro{background-color:#6871BF;}
.ui-skin-nav .li-skinitem span.green{background-color:#77B080;}
.ui-skin-nav .li-skinitem span.purple{background-color:#B951FF;}
#menuTabAbsDiv{
	position:absolute;
	left:217px;
	right:6px;
	top:108px;
	bottom:20px;
}
#menuPosition {
	font-size: 12px;
}
body{
/* 	background-image: url(images/menu/bg_top.png); */
	background-color:#1A70B7;
}

/* #nav{
	width:97%;
	/* background: url(images/menu/bg.gif) repeat-x  0px -110px; */
	margin-left:0.5%;
    border-radius: 6px 6px 6px 6px;
    -moz-border-radius: 6px 6px 6px 6px;
    -webkit-border-radius: 6px 6px 6px 6px;
    box-shadow: 0px 0px 3px #d0d0d0;
    -moz-box-shadow: 0 0 3px #d0d0d0;
    -webkit-box-shadow: 0 0 3px #d0d0d0;
    padding-left: 1%;
	 padding-right: 1%;
} */

#menuPosition .firstPosition {
	color: #5F6062;
	font-weight: normal;
	font-size: 12px;
}

#menuPosition .position3 {
	color: #5F6062;
	font-weight: normal;
	font-size: 12px;
}

#menuPosition .position2 {
	color: #5F6062;
	font-weight: normal;
	font-size: 12px;
}

#menuPosition .position1 {
	color: #5F6062;
	font-size: 12px;
	font-weight: blod;
}

#menuPosition .menuSplitStr {
	color: #5F6062;
	font-weight: normal;
}

#user_clor {
	color: #fff;
	font-family: SimHei;
	height: 13px;
	float: left;
	padding: 3px 0 0 25px;
	background: url(images/user.png) no-repeat center left;
}


#change_mode {
	height: 47px;
	float: left;
	border: none
}

#re_fresh {
	background: url(images/rfresh.png) 0 center no-repeat;
	padding: 10px 0 11px 35px;
}

#quite {
	background: url(images/exit.png) 0 1px no-repeat;
	padding: 10px 0 11px 35px;
	margin-top: 8px;
}
.top_bottom{
	background:url("images/topBtn.jpg");
}

#yewu_mod {
	background: url(templets/template_2/top/Subfield.jpg) no-repeat right
		bottom;
	height: 47px;
	margin: 0 5px;
	display: block;
	padding: 0 5px
}

#yewu_mod:hover {
	background: url(templets/template_2/top/main_ahover.png) no-repeat top
		center;
	margin: 0 5px;
	display: block;
	padding: 0 5px;
	height: 47px;
}

#map_mod {
	background: url(templets/template_2/top/Subfield.jpg) no-repeat right
		bottom;
	height: 47px;
	margin: 0 5px;
	display: block;
	padding: 0 5px
}

#map_mod:hover {
	background: url(templets/template_2/top/main_ahover.png) no-repeat top
		center;
	margin: 0 5px;
	display: block;
	padding: 0 5px;
	height: 47px;
}

.dock-item {
	background: url(templets/template_2/top/Subfield.jpg) no-repeat right
		bottom;
	height: 47px;
	display: block;
	padding: 0 5px;
	float: left
}

.dock-item:hover {
	background: url(templets/template_2/top/main_ahover.png) no-repeat top
		center;
	display: block;
	padding: 0 5px;
	height: 47px;
}

.main_back {
	padding: 4px 5px 2px 5px;
	background: #007263;
	border: 1px solid #01ad96;
	margin: 3px 0 0 10px
}

#nav2{
	position: absolute;
	left: 7px;
	overflow-y:auto;
	overflow-x:hidden;
/* 	padding-top: 6px; */
	bottom:20px;
	top:108px;
}
#nav{
	height: 34px;
	padding-top:3px;
}
#right{
	position: absolute;
	width:85.5%;
	left:217px;
	top:108px;
	bottom:45px;
}
#maxOrmin{	
	position: absolute;
	top: 127px;
	left:215px;
	z-index: 9000;
	width: 10px;
	height: 100%;
	cursor: pointer;
}
#min{
	margin-top: 280px;
}
#topBtn{
	width:90%; 
	position: absolute;left:205px; top:94px; z-index: 9000;
	color: white;
	border-top: 1px solid #9ccbce;
}
#body{
/* 	background-color: #49b4d6; */
	height: 100%;
	width: 100%;
}
.level1{
	border-left: 2px dotted #CDF0F2;
	height: 25px;
}
.level1 .Icon{
	width:20px;
	height: 20px;
	margin-top: 4px;
	float: left;
}
.level1 a{
	padding-left: 5px;
}
#top_minOrMax{
	position: absolute;
	top:100px;
	z-index: 9000;
	width: 100%;
	height: 30px;
}
#pageTabs{
	width: 800px;
	height: 25px;
	margin-top: 2px;
}
#pageTabs li{
	width: 120px;
	float: left;
	height: 22px;
	line-height: 22px;
	text-align: center;
	font-weight: bold;
	margin-left: 10px;
	cursor: pointer;
	border: 1px solid #9ccbce;
}
#pageTabs li img{
	width:10px;
	height:10px;
	margin-left: 3px;
}
#bodyBg{
	/* background-color:white; */
	z-index: 9000;height: 88%;width: 100%;
}
.panel,.panel-body,.tabs-header{
	background-color: white;
	background-image: none;
}
.tabs-tool{
	right:100px;
}

#nav3{
	overflow-y:auto; overflow-x:hidden;
}
.tabs-panels{
	border-bottom: 0px;
} 

/* #nav2 { */
/*     background: #fff; */
/*     border-radius: 6px 6px 6px 6px; */
/*     -moz-border-radius: 6px 6px 6px 6px; */
/*     -webkit-border-radius: 6px 6px 6px 6px; */
/*     box-shadow: 0px 0px 3px #d0d0d0; */
/*     -moz-box-shadow: 0 0 3px #d0d0d0; */
/*     -webkit-box-shadow: 0 0 3px #d0d0d0; */
/*     position: relative; */
/*     padding: 1px; */
/*     top: 3px; */
/*     padding-bottom: 20px; */
/* 	 padding-top: 10px; */
/* } */

/* #right{ */
/* 	 background: #fff; */
/*     border-radius: 6px 6px 6px 6px; */
/*     -moz-border-radius: 6px 6px 6px 6px; */
/*     -webkit-border-radius: 6px 6px 6px 6px; */
/*     box-shadow: 0px 0px 3px #d0d0d0; */
/*     -moz-box-shadow: 0 0 3px #d0d0d0; */
/*     -webkit-box-shadow: 0 0 3px #d0d0d0; */
/*     padding-bottom: 20px; */
/* 	 padding-top: 10px; */
/* } */

.MenuBgTabMain{
	background-color: #FFFFFF;
}
.MenuBgTab .MenuBgTdTop1 {
	background-image: url(images/menu/menuBgImg/gov_menu_img1.png);
	background-repeat: no-repeat;
	background-position: left top;
}
.MenuBgTab .MenuBgTdTop2 {
	background-image: url(images/menu/menuBgImg/gov_menu_img2.png);
	background-repeat: no-repeat;
	background-position: right top;
}
.MenuBgTab .MenuBgTdDown1 {
	background-image: url(images/menu/menuBgImg/gov_menu_img3.png);
	background-repeat: no-repeat;
	background-position: left bottom;
}
.MenuBgTab .MenuBgTdDown2 {
	background-image: url(images/menu/menuBgImg/gov_menu_img4.png);
	background-repeat: no-repeat;
	background-position: right bottom;
}

.us{
	display:none;
	position:absolute;
	width:200px;
	height:200px;
	padding:5px;
	top:55px;
	right:50px;
	background-color:#0099FF; 
	z-index: 999
}

</style>
<script src="js/publicfun.js"></script>
<script type="text/javascript">
	//后退操作
	function goBack() {
		localhost.go(-1);
// 		if(operationIndex<=1)
// 			return;
// 		operationIndex--;
// 		operationSimulator = true;
// 		var obj = operationRecord[operationIndex-1];
// 		obj.click();
	}
	
	//前进操作
	function goForward() {
		localhost.go(1);
// 		if(operationIndex>=operationRecord.length)
// 			return;
// 		operationIndex++;
// 		operationSimulator = true;
// 		var obj = operationRecord[operationIndex-1];
// 		obj.click();
	}
	function getRemind(){
		$.ajax({
			url:'${ctx}/jobRemind/jobRemindAction!shwoRemind?userId=${user_id}&userDept=${user.deptId}',
			success:function(data){
				for(var obj in data){
					if(typeof(data[obj]) == 'object'){
						if(data[obj].num == 0){
							$("#remindTable").append("<tr><td colspan='2'style=‘height: 50px; text-align: center;'><b>没有数据</b></td></tr>");
						}else{
							$("#remindTable").append("<tr><td>您有"+data[obj].num+"条待办事项等待处理</td></tr>");
						}
					}
				}
			}
		});
	}
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
	var menuPage=4;	//一级菜单每页显示多少个菜单
	var currPage=1; //当前菜单页
	var totalPage=0; //总页数
	var totalCount=0; //总条数
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
		var showNumber=currPage*menuPage;
		$(".level1").hide();
		for(var i=showNumber-menuPage;i<showNumber;i++){
			if(i<totalCount)$('.group'+i).show();
		}
	}
	//菜单下一页
	function goAfter(){
		if(currPage<totalPage)
			currPage++;
		toggleMenu();
	}
	
	//菜单上一页
	function goBefore(){
		if(currPage>1)
			currPage--;
		toggleMenu();
	}
	var url = '';
	$(function(){
		var menus=[];
		<c:if test="${param.menuModule!=null}">url='&menuModule=${param.menuModule}';</c:if>
		$.post('menu/menuAction!loadFirstMeun.action?menuJb=1&menuKind=1'+url,function(data){
			var data = eval('(' + data + ')');
			console.log(data);
			menus=data[0];
			var showlist = $('#nav');
            showall(data, showlist);	//绑定一级菜单与点击事件
            loadChildMenu(data[0].menuId,data[0].menuName,0);//初始化二级菜单
		});
		gis = new GisUtil({mainFrame:$('#main'),gisFrame:$('#GisMain')});
		$('#page').hide();
		$('#main_top').addClass('animated fadeInDownBig').delay(2000).queue(function(next){
			$(this).removeClass('animated fadeInDownBig');
			next();
		});
		$('#tt').tabs({
				onSelect: function(title){
					$('#menuPosition').find('.currName').text(title);
			  },
			  tools:[
			    {
			      iconCls:'icon-close',
			      handler:function(){
			    	  $(".tabs .tabs-closable").each(function(index) {
			              //获取所有可关闭的选项卡
			            var title=$(this).text();
			            $('#tt').tabs('close', title);
			        });
			      }
			    },
			    {
				  iconCls:'icon-add',
				  handler:function(){
						topHide();
// 						setTimeout(function(){
// 							$("#tt").css("display","block").tabs('resize');
// 						},1000);
						//切换按钮
						var add=$('.tabs-tool').find('.icon-add');
						if($(add).length!=0){
							$(add).removeClass("icon-add");
							$(add).addClass("icon-undo")
						}else{
							add=$('.tabs-tool').find('.icon-undo');
							$(add).removeClass("icon-undo");
							$(add).addClass("icon-add");
						}
						
					}
				}],
			 widht:'auto',
			 height:$('#tt').parent().height()
		});
		getRemind();
	});
	
	function showRemind(){
		$(".us").slideDown("slow");
		$("#map_mod").css('display','none');
		$("#yewu_mod").css('display','');
		
	}
	function hideRemind(){
		$(".us").slideUp("slow");
		$("#map_mod").css('display','');
		$("#yewu_mod").css('display','none');
	}
	function ttreset(){
		$('#tt').tabs({
			onSelect: function(title){
				$('#menuPosition').find('.currName').text(title);
		  },
		  tools:[
		    {
		      iconCls:'icon-close',
		      handler:function(){
		    	  $(".tabs .tabs-closable").each(function(index) {
		              //获取所有可关闭的选项卡
		            var title=$(this).text();
		            $('#tt').tabs('close', title);
		        });
		      }
		    },
		    {
			  iconCls:'icon-add',
			  handler:function(){
					topHide();
					setTimeout(function(){
						$("#tt").css("display","block").tabs('resize');
					},1000);
					//切换按钮
					var add=$('.tabs-tool').find('.icon-add');
					if($(add).length!=0){
						$(add).removeClass("icon-add");
						$(add).addClass("icon-undo");
					}else{
						add=$('.tabs-tool').find('.icon-undo');
						$(add).removeClass("icon-undo");
						$(add).addClass("icon-add");
					}
					
				}
			}],
		 widht:'auto',
		 height:$('#tt').parent().height()
	});
	}
	function loadChildMenu(parentId,title,index){
		var cls='erji'+index;
		$('#nav3 li[name="erji"]').hide();
		if($('.'+cls).length>0){	//如果已经加载 就直接显示 无需再次加载
			$('.'+cls).show();
			return;	
		}
		$.post('menu/menuAction!children.action?menuId='+parentId+url,function(data){
// 			console.log(new Date());
			var data=eval('('+data+')');
			loader(data,title,cls);
		});
	}
	
	function gisInit() {
		//gis.setParams({gisFunction:window.frames['GisMain'].linkFromBusinessPage});
	}
	function mainInit() {
		//gis.setParams({gisFunction:window.frames['main'].linkFromBusinessPage});
	}
	function toggleBtn() {
		addTabs("地图模式","地图模式","map");
	}
	//加载菜单
	function showall(data,nva){
		calcTotalPage(data.length);//计算总页数
		$(data).each(function(j){
			$(this).each(function(){
				var children=this;
				var li=$('<li>').attr('class','level1 group'+j);
				if(j==0||j%menuPage==0){
					$(li).attr('style','border-left:0px;');
				}
				var img=$('<img>').attr('src','showIcon.action?menuId='+this.iconNo+'&radom='+new Date()).attr('class','Icon');//这行是去拉菜单的图标
				if(j+1>currPage*menuPage){
					$(li).hide();
				}
				var a=$('<a>').attr('href','#').text(this.menuName);
				$(li).mousemove(function(){
					//  
					$(this).find('img:first').addClass('animated rotateIn').delay(2000).queue(function(next){
					    $(this).removeClass('animated rotateIn');
					    next();
					});	
				});
				$(a).hover(function() {
					$(a).attr('style','color:#000;')
				}, function() {
					$(a).attr('style','');
				});
				$(a).click(function(){
					var clickName=$(this).text();//单击的名字
					$(children).each(function(){
						loadChildMenu(this.menuId,clickName,j);//初始化二级菜单
					});
				});
				$(nva).append($(li).html(img).append(a));
			});
			$('.group'+(data.length-1)).attr('style','border-right:0px;');
			if(data.length==1){
				$('.group0').attr('style','border-left:0px;');
			}
			if(data.length>menuPage){
				$('.group'+(data.length-1)).hide();
			}
		});
	}
	
	var sanjiMenuClickObj = null;
	//默认加载
	function loader(menus,title,cls){
		$('#nva3 li[name="erji"]').hide();
		$(menus).each(function(){
<%--				$('#nav2 a:first').text(title);--%>
<%--				$('#nav2 img:first').attr('src','showIcon.action?menuId='+menus.menuId+'&radom='+new Date());--%>
				var li2=$('<li>').attr('class','nav_erji').addClass(cls).attr('name','erji');
				var img=$('<img>').attr('src','showIcon.action?menuId='+this.iconNo+'&radom='+new Date()).attr('class','menuIcon');
				$(li2).mousemove(function(){
					$(this).find('img:first').addClass('animated bounceIn').delay(2000).queue(function(next){
						$(this).removeClass('animated bounceIn');
						next();
					});
				});
				var ref=this.url==undefined?"#":this.url;
				var a2=$('<a>').attr('href',ref).text(this.menuName);
				var sanChildren=this.children;
				var ul=$('<ul>').attr('style','display:none');
				$(sanChildren).each(function(){
					var li3=$('<li>').attr('class','nav_sanji')
					var url=this.menuUrl;
					var jumpUrl = url == ''? 'javascript:void(0)' : url;
					var a3="";
					if(jumpUrl.indexOf(".action") == -1 && jumpUrl.indexOf("javascript") == -1 && jumpUrl.indexOf("Action") == -1){
						a3 = $('<a>').attr('href','autoJump.action?menuId='+this.menuId+'&url='+jumpUrl+'').html(this.menuName).attr('target','main').attr('menuId',this.menuId);;
					}else{
						a3 = $('<a>').attr('href',jumpUrl).html(this.menuName).attr('menuId',this.menuId);;
					}
					var currName=this.menuName;
					$(a3).click(function(e){
						var title=$(this).text();
						var name="main"+$(this).attr("menuId");
						$(this).attr("target",name);
// 						alert($(this).offset().left+Number($(this).css("width").replace("px","")));
// 						alert($(this).offset().top);
						sanjiMenuClickObj = this;
						$("#sanjiMenuSanjiaoDiv").css({top:$(sanjiMenuClickObj).offset().top+5,left:"205px","display":"block"});
						return addTabs(title,currName,name);
					});
					$(ul).append($(li3).html(a3));
				});
				$('#nav3').append($(li2).html(img).append(a2).append(ul).show());
				$(a2).click(function(){
					var liones = this.parentNode.getElementsByTagName("ul");
					var lione=liones[0];
					//$('.nav_sanji').parent().hide();
					if(lione.style.display == 'none'){
						lione.style.display = '';
						this.parentNode.setAttribute('class','nav_erji02 '+cls);
					}else{
						lione.style.display= 'none';
						this.parentNode.setAttribute('class','nav_erji '+cls);
					}
				});
		});
	//  bounceIn 动画效果
		$('#nav2').addClass('animated fadeInLeftBig').delay(1000).queue(function(next){
		    $(this).removeClass('animated fadeInLeftBig');
		    next();
		});
		
	}
	//计算总页数
	function calcTotalPage(count){
		totalCount=count;	//设置总条数
		totalPage=DivUp(totalCount,menuPage);
	}
	function topHide(){
// 		menuHide();

// 		var src=$('#min').attr('title');
// 		if(src=='收缩'){
			
// 		}else{
// 			$('#min').attr('src','images/hide.png').attr('title','收缩');
// 			$('#right').animate({
// 				width:BODYWIDTH-225,
// 				left:'217px'
// 			},100);
// 			$('#nav2').animate({width:'toggle'},100);
// 			$('#maxOrmin').animate({
// 				left:'215px'
// 			},100);
// 			$('#topBtn').animate({
// 				width:'90%',
// 				left:'205px'
// 			},100);
// 			$('#tt').tabs({
// 				 height:BODYHEIGHT-153
// 				});
// 			setTimeout(function(){
// 				$("#tt").css("display","block").tabs('resize');
				
// 			},500);
			
// 		}
		
		var tit=$('#main_top').attr('title');
		if(tit=='收缩'){
			
			$('#min').attr('src','images/hide.png').attr('title','收缩');
			$('#right').animate({
				width:BODYWIDTH-225,
				top:'108px',
				left:'217px',
				bottom:'46px'
			},300);
			
			$("#sanjiMenuSanjiaoDiv").show();
// 			$('#nav2').animate({width:'show'},100);
			$('#nav2').show();
			$('#nav2').animate({top:'108px',bottom:'20px'},100);
			$('#maxOrmin').animate({
				left:'215px'
			},100);
			$('#topBtn').animate({
				width:'90%',
				left:'205px'
			},100);
			$('#tt').tabs({
				 height:BODYHEIGHT-153
				});
			setTimeout(function(){
				$("#tt").css("display","block").tabs('resize');
				
			},500);
			
			$('#main_top').attr('title','展开');
			$('#main_top').animate({height:'toggle'},100);
			$('#right').animate({
				top:'108px',
				bottom:'46px',
				height:BODYHEIGHT-154
			},100);
			$('#bodyBg').animate({
				height:'85%'
			});
		}else{
			$('#min').attr('src','images/show.png').attr('title','展开');
			$('#right').animate({
				width:'100%',
				height:'100%',
				top:'0px',
				left:'0px'
			},300);
			$("#sanjiMenuSanjiaoDiv").hide();
			$('#nav2').animate({width:'toggle'},100);
			$('#maxOrmin').animate({
				left:'0px'
			},'slow');
			$('#topBtn').animate({
				width:'100%',
				left:'0px'
			},100);
			
			$('#tt').tabs({
				 height:BODYHEIGHT-45
				});
			setTimeout(function(){
				$("#tt").css("display","block").tabs('resize');
			},500);
			
			
			$('#main_top').animate({height:'toggle'},100);
			$('#right').animate({
				top:'0px',
				height:'120%'
			},100);
			$('#bodyBg').animate({
				height:'97%'
			});
			$('#main_top').attr('title','收缩');
			
		}
		if(sanjiMenuClickObj!=null){
			setTimeout(function(){
			$("#sanjiMenuSanjiaoDiv").css({top:$(sanjiMenuClickObj).offset().top+5,left:"205px","display":"block"});
			$("#sanjiMenuSanjiaoDiv").show();},100);
		}
	}
	document.onmousewheel = function() {return false;}	//禁止滚动
	function menuHide(){
		var src=$('#min').attr('title');
		if(src=='收缩'){
			$('#min').attr('src','images/show.png').attr('title','展开');
			$('#right').animate({
				width:'100%',
// 				height:'100%',
// 				top:'108px',
				bottom:'46px',
				left:'0px'
			},300);
			
			$("#nav2").hide(100);
// 			$('#nav2').animate({width:'hide'},100);
			$('#maxOrmin').animate({
				left:'0px'
			},'slow');
			$('#topBtn').animate({
				width:'100%',
				left:'0px'
			},100);
			
			setTimeout(function(){
				$("#tt").css("display","block").tabs('resize');
			},500);
		}else{
			$('#min').attr('src','images/hide.png').attr('title','收缩');
			$('#right').animate({
				width:BODYWIDTH-225,
				left:'217px'
			},300);
			
			if(sanjiMenuClickObj!=null){
				$("#sanjiMenuSanjiaoDiv").css({top:$(sanjiMenuClickObj).offset().top+5,left:"205px","display":"block"});
				$("#sanjiMenuSanjiaoDiv").show();
			}
			var tit=$('#main_top').attr('title');
			
			if(tit=='收缩'){
				$('#nav2').css({top:'0px',bottom:'20px'});
			}else{
				$('#nav2').css({top:'108px',bottom:'20px'});
			}
			$('#nav2').show(100);
// 			$('#nav2').animate({width:'toggle'},100);
			$('#maxOrmin').animate({
				left:'215px'
			},100);
			$('#topBtn').animate({
				width:'90%',
				left:'205px'
			},100);
			setTimeout(function(){
				$("#tt").css("display","block").tabs('resize');
				
			},500);
			
		}
		
		if(sanjiMenuClickObj!=null){
			setTimeout(function(){
			$("#sanjiMenuSanjiaoDiv").css({top:$(sanjiMenuClickObj).offset().top+5,left:"205px","display":"block"});
			$("#sanjiMenuSanjiaoDiv").show();},100);
		}
		
	}
	//增加一个选项卡
	function addTabs(title,currName,name){
		$('#menuPosition').find('.currName').text(currName);
		if ($('#tt').tabs('exists',title)){//如果选项卡已经加装 则不重新加装
			$('#tt').tabs('select', title);
			return false;
		}else{
			var content=$("<iframe>").attr("name",name).attr("width","100%").attr("frameborder","0").attr("height","100%");
			$(content).load(function(){
				mainInit();
			});
			$('#tt').tabs('add',{
	            title:title,
	            content:content,
	            closable:true
	        });
			$('#tt').tabs('select', title);
		}
		return true;
	}
	function topMinOrShow(){
		$('#main_top').animate({height:'toggle'},'slow');
	}
	
	var BODYWIDTH = "";
	var BODYHEIGHT = "";
	$(function(){
		szMainIframe();
		$('#right').css("width",BODYWIDTH-225);
		$('#right').css("height",BODYHEIGHT-154);
	});
	
	function szMainIframe(){
		BODYWIDTH = Number($("#fullScreenDiv").css("width").replace("px",""));
		BODYHEIGHT = Number($("#fullScreenDiv").css("height").replace("px",""));
	}
	window.onresize= function(){
		szMainIframe();
// 		$('#right').css("height",BODYHEIGHT-153);
// 		$('#right').css("width",BODYWIDTH-225);
		var tit=$('#main_top').attr('title');
		if(tit==undefined || tit=='展开'){
			$('#right').css("height",BODYHEIGHT-153);
		}else{
			$('#right').css("height",BODYHEIGHT-50);
			
		}
		
		var src=$('#min').attr('title');
		if(src=='收缩'){
			
			$('#right').css("width",BODYWIDTH-228);
		}else{
			$('#right').css("width",BODYWIDTH);
			
		}
		ttreset();
	};
	
	$(function(){
		$(".li-skinitem").click(function(){
			$("body:eq(0)").css("background-color",$($(this).find("span:eq(0)")).css("background-color"));
		});
	});
	
</script>
</head>

<body style="overflow: hidden;" onmousewheel="return false;">

<div region="north" border="true" class="cs-north" style="position: absolute;top:85px;z-index:0;right:120px;">
		<div class="cs-north-bg">
		<div class="cs-north-logo"></div>
		<ul class="ui-skin-nav">				
			<li class="li-skinitem" title="gray"><span class="gray" rel="gray"></span></li>
			<li class="li-skinitem" title="default"><span class="default" rel="default"></span></li>
			<li class="li-skinitem" title="bootstrap"><span class="bootstrap" rel="bootstrap"></span></li>
			<li class="li-skinitem" title="blue"><span class="blue" rel="blue"></span></li>
			<li class="li-skinitem" title="metro"><span class="metro" rel="metro"></span></li>
			<li class="li-skinitem" title="green"><span class="green" rel="green"></span></li>
			<li class="li-skinitem" title="purple"><span class="purple" rel="purple"></span></li>
		</ul>	
		</div>
	</div>
	
<div id="sanjiMenuSanjiaoDiv" style="width:20px;height:20px;position: absolute;left:205px;top:300px;background-image: url(images/menu/sanjiaotubiao.png);z-index: 99999;display:none;"></div>
<div id="fullScreenDiv" style="position: absolute;margin:0px 0px;padding:0px 0px;height:100%;width:100%;left:0px;top:0px;z-index:-1;"></div>
<div id="main_top">
	<div class="toptitle">
		<div class="topright">
			<div class="topshe">
				<div class=""></div>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
							<div id="user_clor">当前用户：${user_name}</div>
						</td>
						<td align="left"><script type="text/javascript"
								language="javascript">
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
		    </script></td>
						<td>
							<div id="change_mode" class='contact'>
								<a id="yewu_mod" href="javascript:hideRemind();" style="display: none;">
									<img src="templets/template_2/top/main_dt.png" />
								</a>
								<a id="map_mod" href="javascript:showRemind()">
									<img src="templets/template_2/top/main_dt.png" />
								</a>
							</div>
							
							<div id="link_clor">
<%-- 								<a class="dock-item" href="${ctx}/jsp/gov/baseInfo/release.jsp" --%>
<!-- 									style="margin-right: 10px;"> href="${ctx}/jsp/menhu/index.jsp" -->
<!-- 									<img src="templets/template_2/top/main_sy.png" /></a> -->
									 <a class="dock-item" href="#" onClick="location.reload(true)"><img
									src="templets/template_2/top/main_sx.png" /></a><a
									class="dock-item" href="${ctx}/logout!logout.action"
									style="margin-right: 10px;"><img
									src="templets/template_2/top/main_tc.png" /></a>
							</div>
							<div class="us">
								<table id="remindTable" style="border: 1xp solid black;">
								</table>
							</div>
						</td>
					</tr>
					<input type="hidden" id="yhm" value="${user_name} ">
				</table>
			</div>
		</div>
	</div>
<%--	<div id="top_minOrMax"><img src="images/up.png" id="topMin" title="收缩"/></div>--%>
	<div class="datewordspos" id='ces'>
		<%-- <div class="userinfo">当前用户：${user_name}</div> --%>
	</div>
	<div>
		<!-- 这里是菜单位置 -->
		<ul id="nav">
			<div onmousemove="setTimeout($('#page').show(),2000);" onmouseout="setTimeout($('#page').hide(),2000);" class="lee" style="position: absolute; width:100px; margin: 5px 10px 0 0; right: 0; color: #FFF; height: 30px;">
				<div id="page">
				<a href="#" onclick="goBefore();return false;">
				<img src="images/menu/btn_left.gif" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="goAfter();return false;" >
				<img src="images/menu/btn_right.gif" /></a></div>
			</div>
		</ul>
	</div>
	
	</div>
<%--	<div id="top_minOrMax" style="cursor: pointer;" onclick="topHide();"><center><img src="images/up.png" title="收缩"/></center></div>--%>
	<div class="top_bottom" id="topBtn" style="display: none;">
			<img src="templets/template_2/top/house.png" style="float: left;" />
			<h2 id="menuPosition" >
				<span class="firstPosition">您当前的位置</span>&nbsp;&nbsp;<span class="currName" style="color:#335860;">企业信息统计</span>
			</h2>
	</div>
		<div id="maxOrmin" onclick="menuHide();" onmousemove="setTimeout($('#min').show(),1000);"
		onmouseout="setTimeout($('#min').hide(),1000);">
		<img id="min" title="收缩" src="images/hide.png"/></div>
	 <div id="body">
	 	<div id="bodyBg" style="overflow:hidden;">
	 	
	 	
		 <!-- 左边菜单位置 -->
		<div id="nav2">
		
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="MenuBgTab">
		  <tr>
		    <td width="12" height="12" class="MenuBgTdTop1"><div style="width:12px;height:12px;overflow:hidden;"></div></td>
		    <td width="190" class="MenuBgTabMain"><div style="width:100%;height:12px;overflow:hidden;"></div></td>
		    <td width="12" class="MenuBgTdTop2"><div style="width:12px;height:12px;overflow:hidden;"></div></td>
		  </tr>
		  <tr>
		    <td colspan="3" class="MenuBgTabMain" valign="top">
<%--		<img style="width: 20px;height:20px;float: left;margin-top: 4px;margin-left: 50px;"/>--%>
<%--			<a id="nav_yiji" href="#" style="text-align: left;margin-left: 5px;"></a>--%>
			<ul id="nav3"></ul>
			
			</td>
		  </tr>
		  <tr>
		    <td width="12" height="12" class="MenuBgTdDown1"><div style="width:12px;height:12px;overflow:hidden;"></div></td>
		    <td class="MenuBgTabMain">&nbsp;</td>
		    <td class="MenuBgTdDown2" width="12"><div style="width:12px;height:12px;overflow:hidden;"></div></td>
		  </tr>
		</table>
		</div>
		
		<div id="right" >
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="MenuBgTab">
		  <tr>
		    <td width="12" height="12" class="MenuBgTdTop1"><div style="width:10px;height:12px;overflow:hidden;"></div></td>
		    <td width="100%" class="MenuBgTabMain"><div style="width:12px;height:12px;overflow:hidden;"></div></td>
		    <td width="12" class="MenuBgTdTop2"><div style="width:8px;height:12px;overflow:hidden;"></div></td>
		  </tr>
		  </table>
<%--		<div style="height: 80%;width: 100%;overflow:hidden;">--%>
			<div id="tt" style="background-color: white;">
			   <div title="欢迎回来！${user_name}">
				     <div style="height: 100%; margin: 0; padding: 0; width: 100%;overflow: hidden; ">
						<div id="iframeTd" style="height: 100%; margin: 0; padding: 0;">
							<script>
				            function resize(){
				                 document.getElementById("main").style.height=(document.getElementById('iframeTd').offsetHeight - 3)+'px';            
				            }
				            // 特效  fadeInUp rotateInDownRight
				            function mainInit(){
				            	$('#right').addClass('animated fadeInUp').delay(1000).queue(function(next){
								    $(this).removeClass('animated fadeInUp');
								    next();
								});	
					        }
				            </script>
							<iframe id="main" name="main" width="100%"  
								src="${ctx}/welcome.jsp" frameborder="0" height="100%"
								onload="mainInit()"></iframe>
							<iframe id="GisMain" name="GisMain" width="100%" src="" scrolling="no"
								frameborder="0" onload="gisInit()" style="display: none;"></iframe>
						</div>
					</div>
<%--			    </div>--%>
		    </div>
		</div>
		<div id="topWindow" style="display: none;"></div>
		<input type="hidden" value="<%=request.getParameter("menuModule") %>" id="menuModule" />
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="MenuBgTab">
		  <tr>
		    <td width="10" height="12" class="MenuBgTdDown1"><div style="width:8px;height:12px;overflow:hidden;"></div></td>
		    <td class="MenuBgTabMain"><div style="width:12px;height:12px;overflow:hidden;"></div></td>
		    <td class="MenuBgTdDown2" width="12"><div style="width:8px;height:12px;overflow:hidden;"></div></td>
		  </tr>
		</table>
		</div>
		<div style="position: absolute;bottom:2px;left: 50%;color: white;font-weight: bold;margin:auto -95px;">技术支持:&nbsp;&nbsp;&nbsp;南京市安元科技有限公司</div>
	</div>
</body>
</html>