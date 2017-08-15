/*
 * 菜单点击事件
 */


function encode(str){
	  str=str.replace(/\//g,"%2f");
	  str=str.replace(/\?/g,"%3f");
	  str=str.replace(/\=/g,"%3d");
	  str=str.replace(/\&/g,"%26");
	  return str
}

function setFatherWin(){   
	   if(!miswin.closed){
	        miswin.fatherwin=window;
	   	setTimeout("setFatherWin()",1000);
		//status=i++;
	   }else{
	       status="结束";
	   }
	  //alert(win.miswin);
	}

var miswin;
function menu_onclick(meun_url, menu_type) {
	if (menu_type == 0) {// 新窗口打开
		window.open(meun_url, '_blank');
	} else {// 右侧打开
		var str = meun_url;
		//var str_match = str.match("[?]");
		//if ("?" == str_match) {
			//window.open(meun_url + "&date=" + new Date(), 'main');
        miswin=main;
        miswin.location="misframe.jsp?mainurl="+meun_url,"miswindow";
        miswin.focus();
        setTimeout("setFatherWin()",1000);
		//} else {
		//	window.open(meun_url + "?date=" + new Date(), 'main');
			
		//}
	}
}

function createXMLHttpRequest() {
	if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	}
}

function show(menuId) {
	createXMLHttpRequest();
	if (xmlHttp != null) {
		xmlHttp.open("POST", "menu/menuAction!setControlByUser.action?menuId="+menuId ,false);
		xmlHttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xmlHttp.send();
	}
}

