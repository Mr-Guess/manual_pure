/***********************************************************************************
标题：check_lib.js
功能：表单效验
说明：
作者：王惠芳
时间：2004-6-21
版本：
修改：2004-6-24
**********************************************************************************


==================================================================

LTrim(string):去除左边的空格

==================================================================

*/
function show(){
	alert("尼玛");
}
function LTrim(str)
{

    var whitespace = new String(" \t\n\r");

    var s = new String(str);
    if (whitespace.indexOf(s.charAt(0)) != -1)

    {

        var j=0, i = s.length;

        while (j < i && whitespace.indexOf(s.charAt(j)) != -1)

        {

            j++;

        }

        s = s.substring(j, i);

    }

    return s;

}

 

/*

==================================================================

RTrim(string):去除右边的空格

==================================================================

*/

function RTrim(str)

{

    var whitespace = new String(" \t\n\r");

    var s = new String(str);

 

    if (whitespace.indexOf(s.charAt(s.length-1)) != -1)

    {

        var i = s.length - 1;

        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)

        {

            i--;

        }

        s = s.substring(0, i+1);

    }

    return s;

}

 

/*

==================================================================

Trim(string):去除前后空格

==================================================================

*/

function Trim(str)

{

    return RTrim(LTrim(str));

}

/*

================================================================================

XMLEncode(string):对字符串进行XML编码

================================================================================

*/

function XMLEncode(str)

{

       str=Trim(str);

       str=str.replace("&","&amp;");

       str=str.replace("<","&lt;");

       str=str.replace(">","&gt;");

       str=str.replace("'","&apos;");

       str=str.replace("\"","&quot;");

       return str;

}




/*

================================================================================

验证类函数
表单提交时检查
================================================================================

*/
function IsEmpty(obj)
{
    if(Trim(obj.value)=="")
    {

        alert(obj.desc+":"+"字段不能为空。");        

        if(obj.disabled==false && obj.readOnly==false)
        {
            obj.focus();
        }
		return false;
    }
	return true;
}


/*
================================================================================
check_datatyp_int(obj)
参数obj为检查对象
功能：检查是否为整数
可放在表单元素的onblur事件中也可放在表单提交时检查
================================================================================
*/
function check_datatyp_int(obj)
{

   if(isNaN(obj.value))
	{
       alert(obj.desc+":"+"请输入数字");
	   //obj.focus();
	   return false;
	}



	if (obj.value.indexOf(".")>=0)
	{
	   alert(obj.desc+":"+"此列输入非整数");
	   //obj.focus();
	   return false;
	}

	if (typeof(obj.length)!='undefined'&&obj.length.length>0)
   {
 
	   if (!check_length(obj))
		   return false;
   }
   
   if(obj.value<0){
	   alert(obj.desc+":"+"不能输入负数");
	   //obj.focus();
	   return false;
   		
   	}
	return true;
}
/*
================================================================================
check_datatyp_float(obj)
参数obj为检查对象
功能：判断是否为浮点数
可放在表单元素的onblur事件中也可放在表单提交时检查
================================================================================
*/

function check_datatyp_float(obj)
{
	//alert('dd');
    if(isNaN(obj.value)){
       alert(obj.desc+":"+"非法字符");
	   //obj.focus();
	   return false;
    }
   //判断小数位数
    if (typeof(obj.length)!='undefined'&&obj.length.length>0) {//判断是否已经定义了数字的精度
	var all_length=0;//所有数字位数
	var float_length=0;//小数精度数
		//获取数字长度规则值
		if(obj.value.indexOf(".")!=-1){//定义了小数位数				
			all_length=new Number(obj.length.substring(0,obj.length.indexOf("."))) ;			
			float_length=new Number(obj.length.substr(obj.length.indexOf(".")+1));			
		}else{//没有定义小数位数
			all_length=new Number(obj.length.substring(0,obj.length.indexOf(".")));
		}
		//根据精度判断是否超标
		if(obj.value.indexOf(".")!=-1){//包含小数
			var int_str=obj.value.split(".")[0];//整数部分的字符串
			var float_str=obj.value.split(".")[1];//小数部分的字符串
			
			if(float_str.length>float_length){//判断小数是否超过精度
				alert(obj.desc+"输入数据的小数位数不能超过"+float_length+"位");
				obj.focus();
				return false;
			}
			if(int_str.length>14){//判断整数是否超过精度
				alert(obj.desc+"输入的数字不能超过14位");
				obj.focus();
				return false;
			}
			if((int_str.length+float_str.length)>all_length){
				alert(obj.desc+"输入的数太大了已经超出了记录集的范围");
				obj.focus();
				return false;
			}
			
		}else{//不包含小数
			if(obj.value.length>all_length){
				alert(obj.desc+"输入的数字不能超过"+all_length+"位");
				obj.focus();
				return false;
			}
		}
		
		
   
   if(obj.value<0){
	   alert(obj.desc+":"+"不能输入负数");
	   //obj.focus();
	   return false;
   		
   	}
   	
	return true;
    }else{
    	return true;
    }
}
/*
================================================================================
check_datatyp_chinese()
*测试中文:用户输入的必须全部是中文
*
================================================================================
*/
function check_datatyp_chinese(obj) 
{
  var tmpStr = obj.value;
  var maxLen=obj.value.length;
  var tmpChar = "";
  var isSucc=true;
  for (var i=0;i<tmpStr.length ;i++ )
  {
      tmpChar = escape(tmpStr.charAt(i));
	  if (tmpChar.length<=3)
	  {
		  isSucc = false;
		  break;
	  }
  }

	  if (!isSucc)
	  {
		  alert(obj.desc+":"+"请输入中文");
		  obj.focus();
		  return false;
	  }
	  if (typeof(obj.length)!='undefined'&&obj.length.length>0)
   {
	   if (!check_length(obj))
		   return false;
   }
	  return true;
} 


/*
================================================================================
*  校验输入的是否是英文
*  用户输入的必须全部是英文
================================================================================
*/

function check_datatyp_english(obj)
{
  var tmpStr = obj.value;
  var tmpChar = "";
    var isSucc=true;
  for (var i=0;i<tmpStr.length ;i++ )
  {
      tmpChar = escape(tmpStr.charAt(i));
	  if (tmpChar.length>3)
	  {
		  isSucc = false;
		  break;
	  }
  }
  if (!isSucc)
  {
	  alert(obj.desc+":"+"请输入英文");
      obj.focus();
	  return false;
  }
  if (typeof(obj.length)!='undefined'&&obj.length.length>0)
   {
	   if (!check_length(obj))
		   return false;
   }
  return true;
}

/**
* 检查当前输入框的长度，包括字符串的长度已经浮点数的精度
*/
function check_length(obj)
{
   var tmpDeFineArr = obj.length.split(".");
   if (tmpDeFineArr.length==1)
	 return check_string_length(obj);
   else if(obj.datatype=="int")//xzp
        return check_string_length(obj);
   else
	return check_float_length(obj);
}
/**
*  检测浮点行数字的长度
*/
function check_float_length(obj)
{
   var tmpArr = obj.value.split(".");
   var tmpDeFineArr = obj.length.split(".");

   for (var i=0;i<tmpDeFineArr.length ;i++ )
   {
	   if (tmpArr[i].length>tmpDeFineArr[i])
	   {
	     alert(obj.desc+":"+tmpArr[i]+"已经超长")
	     obj.focus();
	     return false;
	   }
   }
   return true;
}


/*
================================================================================
* obj:需要校验的对象，maxLen:该对象不能超过的最大长度
* 校验字符串的最大长度不能超过当前参数所设定的最大长度
================================================================================
*/


function check_string_length(obj)
{
   if (obj.value.length>obj.length)
   {
	   alert(obj.desc+":"+"输入长度已超过要求的最大长度"+obj.length);
	   obj.focus();
	   return false;
   }
   return true;
}






/*
================================================================================
*  调用函数
*  desc是当调用发生错误的时候所提示的语言，如请输入正确的中文
================================================================================


function check_input_text_textarea(obj,chkType,desc)
{
   if (!check_input_text_textarea(obj,chkType))
   {
	   alert(desc);
	   return false;
   }
   return true;
}

*/


/*
================================================================================
* 检查录入的是否为正确的日期
================================================================================
*/
function check_datatyp_date(obj)
{
if(obj.value==""||obj.value=="null")
	{
		return true;
	}		
  var tmpStr = obj.value.split("-");
  if (tmpStr.length!=3)
  {
	  alert(obj.desc+":"+"请输入正确的日期格式，并以'-'隔开");
	  obj.focus();
	  return false;
  }
  
  if (isNaN(tmpStr[0]))
  {
	  alert(obj.desc+":"+"年份中含有非法字符");
	  obj.focus();
	  return false;
  }
  if ((tmpStr[0].length<1)||(tmpStr[0].length>4))
  {
	  alert(obj.desc+":"+"年份的长度只能是四位");
	  obj.focus();
	  return false;
  }
  if (isNaN(tmpStr[1]))
  {
	  alert(obj.desc+":"+"月份中含有非法字符");
	  obj.focus();
	  return false;
  }
  
  if ((parseFloat(tmpStr[1])<1)||(parseFloat(tmpStr[1])>12))
  {
	  alert(obj.desc+":"+"月份必须在1-12之间");
	  obj.focus();
	  return false;
  }
  if (isNaN(tmpStr[2]))
  {
	  alert(obj.desc+":"+"天份中含有非法字符");
	  obj.focus();
	  return false;
  }
  if ((parseFloat(tmpStr[2])<1)||(parseFloat(tmpStr[2])>31))
  {
	  alert(obj.desc+":"+"天数必须在1-31之间");
	  obj.focus();
	  return false;
  }
  return true;
}

function check_telphoneNumber(obj){
	
	 var c;
     var i;
     var tt="0123456789-";
	 str=obj.value;
    for( i = 0; i < str.length; i ++ )
    {
		
      c = str.charAt( i );     
      if (tt.indexOf( c ) < 0){		 
	     alert("电话号码:非法字符！");
		 obj.focus();
         return false;
	  }
    }
      return true;
}

function check_wendu(obj){
	  if(isNaN(obj.value))
	{
       alert(obj.desc+":"+"非法字符");
	   //obj.focus();
	   return false;
	}
	if (obj.value.indexOf(".")>=0)
	{
	   alert(obj.desc+":"+"此列输入非整数");
	   //obj.focus();
	   return false;
	}
	if (typeof(obj.length)!='undefined'&&obj.length>0)
   {
 
	   if (!check_length(obj))
		   return false;
   }
	return true;
}

function check_income(obj)
{
	//alert('dd');
    if(isNaN(obj.value)){
       alert(obj.desc+":"+"非法字符");
	   //obj.focus();
	   return false;
    }
   //判断小数位数
    if (typeof(obj.length)!='undefined'&&obj.length.length>0) {//判断是否已经定义了数字的精度
	var all_length=0;//所有数字位数
	var float_length=0;//小数精度数
		//获取数字长度规则值
		if(obj.value.indexOf(".")!=-1){//定义了小数位数				
			all_length=new Number(obj.length.substring(0,obj.length.indexOf(".")));			
			float_length=new Number(obj.length.substr(obj.length.indexOf(".")+1));			
		}else{//没有定义小数位数
			all_length=new Number(obj.length.substring(0,obj.length.indexOf(".")));
		}
		//根据精度判断是否超标
		if(obj.value.indexOf(".")!=-1){//包含小数
			var int_str=obj.value.split(".")[0];//整数部分的字符串
			var float_str=obj.value.split(".")[1];//小数部分的字符串
			
			if(float_str.length>float_length){//判断小数是否超过精度
				alert(obj.desc+"输入数据的小数位数不能超过"+float_length+"位");
				return false;
			}
			if((int_str.length+float_str.length)>all_length){
				alert(obj.desc+"输入的数太大了已经超出了记录集的范围");
				return false;
			}
			
		}else{//不包含小数
			if(obj.value.length>all_length){
				alert(obj.desc+"输入的数字不能超过"+all_length+"位");
				return false;
			}
		}
		
		
   	
   	if(obj.name=='c101igrj'){
   			if(myform.c101igmicl!=null&&myform.c101igmicl.value!=''){
   					if(Number(obj.value)<Number(myform.c101igmicl.value)){
   							alert('最大贮存量不能大于容积！');
   							return false;
   						}
   				}
   		}
   	if(obj.name=='c101igmicl'){
   			if(myform.c101igrj!=null&&myform.c101igrj.value!=''){
   					if(Number(obj.value)>Number(myform.c101igrj.value)){
   							alert('最大贮存量不能大于容积！');
   							return false;
   						}
   				}
   		}
	
	return true;
    }else{
    	return true;
    }
}

//验证手机号码的
String.prototype.Trim = function() {
var m = this.match(/^\s*(\S+(\s+\S+)*)\s*$/);
return (m == null) ? "" : m[1];
}

String.prototype.isMobile = function() {
//return (/^(?:13\d|15[012389]|18[089])-?\d{5}(\d{3}|\*{3})$/.test(this.Trim()));
return (/^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(this.Trim()));
}

String.prototype.isTel = function()
{

    return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(this.Trim()));
}

function chkisMobile(obj) {
			if (obj.value.isMobile())  {
				return true;
			}else {
				alert("请输入正确的手机号码\n例如:13916752109");
				return false;
			}
}
//验证邮政编码
function chkiszip(obj){
		var s =obj.value;
		var pattern =/^[0-9]{6}$/;
		if(s!=""){
			if(!pattern.exec(s)){
				alert("请输入正确的邮政编码!");
				obj.focus();
			}
		}
	}
function check_idCard(obj) {
			if (obj.value.isIdCard())  {
				return true;
			}else {
				alert("请输入正确的身份证号码");
				return false;
			}
}

String.prototype.isIdCard = function() {
return (/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/.test(this.Trim()));
}

String.prototype.isEmail = function() {
	var str = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;

	return (str.test(this.Trim()));
}

function check_data_email(obj){
	if (obj.value.isEmail())  {
		return true;
	}else {
		alert("请输入正确的邮件地址\n例如:tom@hotmail.com");	
		obj.focus();
		return false;
	}
}