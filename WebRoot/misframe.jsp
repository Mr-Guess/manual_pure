<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
<title></title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">

<meta name="GENERATOR" content="Namo WebEditor v5.0">


<%
String mainurl=new String(request.getParameter("mainurl").getBytes("iso8859_1"),"utf-8");
%>
<style>
.stateicon {
	margin-left: 10px;
	margin-top: 5px;
	margin-right: 0;
	float: left;
}

.menu {
	font-size: 12px;
	text-align: center;
	float: left;
	padding-left: 0;
	font-weight: bold;
}

.menu A {
	COLOR: #000000;
	TEXT-DECORATION: none;
}

.menu A:link {
	COLOR: #000000;
	TEXT-DECORATION: none
}

.menu A:active {
	COLOR: #000000;
	TEXT-DECORATION: none
}

.menu A:visited {
	COLOR: #000000;
	TEXT-DECORATION: none
}

.menu A:hover {
	COLOR: #000000;
	TEXT-DECORATION: underline
}

.bgstate {
	background-image: url(images/menu/bg_state.gif);
	background-repeat: repeat-x;
	height: 33px;
	line-height: 33px;
}

.statebutton {
	text-align: right;
	font-size: 12px;
	float: right;
	margin-top: -6px;
}

.statebutton img {
	margin-top: 8px;
	margin-right: 12px;
}

.statebutton a {
	text-align: right;
	margin-right: 5px;
	font-size: 12px;
	font-weight: normal;
	margin-right: 5px;
}
</style>
</head>
<body leftmargin="0" marginwidth="0" topmargin="0" marginheight="0"
	scroll="no">

	<table border="0" width="100%" height="100%" cellspacing="0"
		cellpadding="0">

		<tr class="bgstate">

			<td height="33"><span class="stateicon"><img
					src="images/menu/icon_statebar.gif" /></span><span class="menu" id="nav">您当前的位置:</span>
				<span class="statebutton"><a href="#"
					onclick="mainf.history.go(1);return false;"><img
						src="images/menu/bg_buttonnext.gif" border="0" alt="前进"
						style="margin-top: 12px;" /></a></span><span class="statebutton"><a
					href="#" onClick="goback();return false;"><img
						src="images/menu/bg_buttonback.gif" border="0" alt="后退"
						style="margin-top: 12px;" /></a></span> <script>
		var Menus=new Array();
		
		function menuitem(title,com,target){
			this.com=""+com;
			this.title=title;
			this.target=target;
			this.isNow=true;
			this.toString = function(){
				if(this.isNow){
				   	this.isNow=false;
					return "<a href="+this.com+" target="+this.target+">"+"<font color=\"red\">"+this.title+"</font></a>";
				}else{
					return "<a href="+this.com+" target="+this.target+">"+this.title+"</a>";
				}
			   
			}
		}
		
		function insertmenu(title,com,target){
			
		        if(title==null||title==""){
		        	return;
		        }
			var newMenu=new menuitem(title,com,target);
			
			for(var i=0;i<Menus.length;i++){
			   lastMenu=Menus[i];
			   if(newMenu.title==Menus[i].title){
			   	lastMenu=Menus[i-1];
			        Menus.splice(i+1,Menus.length-i-1);
			        Menus[i].isNow=true;
			        Menus[i].com=newMenu.com;
			        nav.innerHTML="&nbsp;&nbsp;您当前的位置:"+(Menus.slice(0,Menus.length).join("-->"));
			        return;
			    }
			}
			Menus[Menus.length]=(newMenu);
			if(Menus.length==6){
				Menus.splice(0,1);
			}
			nav.innerHTML="&nbsp;&nbsp;您当前的位置:"+(Menus.slice(0,Menus.length).join("-->"));
			return;
		}
		
		var lastMenu=null;
		
		function goback(){
		   if(lastMenu!=null){
		       // alert(lastMenu.com);
		   	mainf.location=lastMenu.com;
		   }else{
			mainf.history.back();
		   }
		}
	 </script></td>
		</tr>
		<tr>
			<td><iframe frameborder=0 name="mainf" src="<%=mainurl%>"
					width="100%" height="100%"
					onload='document.title=mainf.document.title;insertmenu(mainf.document.title,mainf.document.location,"mainf")'></iframe>
			</td>
		</tr>
	</table>
</body>
</html>