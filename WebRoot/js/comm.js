	function switchSysBar(){
		
		if (switchPoint.innerText==3){
			switchPoint.innerText=4
			document.all("treediv").style.display="none";
			//isMapbutton
			//$(isMapbutton).style.left="12px";
			document.getElementById("main1").style.width="100%";
			var temp_url=document.getElementById("main1").src;			
			document.getElementById("main1").src = temp_url.substr(0,temp_url.indexOf("width="))+"width="+screen.width+temp_url.substr(temp_url.indexOf("&height="),temp_url.length);
			document.getElementById("frmMain").style.width="100%";
			
		}else{
			switchPoint.innerText=3
			document.all("treediv").style.display="";
			//$(isMapbutton).style.left="227px";
			var temp_url=document.getElementById("main1").src;			
			document.getElementById("main1").src = temp_url.substr(0,temp_url.indexOf("width="))+"width="+(screen.width-223)+temp_url.substr(temp_url.indexOf("&height="),temp_url.length);
			
			document.getElementById("frmMain").style.width=(self.document.body.offsetWidth-223)+"px";
			
		}
	}

	function nowDate(){
		var now= new Date();
		var year=now.getYear();
		var month=now.getMonth()+1;
		var day=now.getDate();
		var hour=now.getHours();
		var minute=now.getMinutes();
		var second=now.getSeconds();
		var nowTime=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
		return nowTime;
	}


function encode(str){
  str=str.replace(/\//g,"%2f");
  str=str.replace(/\?/g,"%3f");
  str=str.replace(/\=/g,"%3d");
  str=str.replace(/\&/g,"%26");
  return str
}



/*
改造window.open函数,保证所有窗口居中弹出,并指定固定的几种窗口大小.以统一系统中的操作风格.

url        --   弹出窗口路径
sTiltle    --  窗口标题
sSize     --  窗口型号,可以自己再增加
*/
    
function openwindow(url,sTitle,sSize)      
{    
	

    if (sSize == undefined) {
       alert("请指定窗口型号!");
       return false;
    }
    else if (sSize == 100) {
       sWidth  = screen.availWidth;
       sHeight = screen.availHeight;
    }

   else if (sSize == 1) {
       sWidth  = 280;
       sHeight = 120;
    }
   else if (sSize == 2) {
       sWidth  = 620;
       sHeight = 325;
    }
   else if (sSize == 3) {
       sWidth  = 380;
       sHeight = 580;
    }
    else if (sSize == 4) {
       sWidth  = 620;
       sHeight = 365;
    }
	 else if (sSize == 5) {
       sWidth  = 750;
       sHeight = 465;
    }
    var l = ( screen.availWidth - sWidth ) / 2;
    var  t = ( screen.availHeight - sHeight ) / 2;      

     
     window.open(""+url+"",sTitle,'left='+l+',top='+t+',width='+sWidth+',height='+sHeight+',scrollbars=no,resizable=yes');      
     
}