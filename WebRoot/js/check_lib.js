/***********************************************************************************
���⣺check_lib.js
���ܣ���Ч��
˵����
���ߣ����ݷ�
ʱ�䣺2004-6-21
�汾��
�޸ģ�2004-6-24
**********************************************************************************


==================================================================

LTrim(string):ȥ����ߵĿո�

==================================================================

*/
function show(){
	alert("����");
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

RTrim(string):ȥ���ұߵĿո�

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

Trim(string):ȥ��ǰ��ո�

==================================================================

*/

function Trim(str)

{

    return RTrim(LTrim(str));

}

/*

================================================================================

XMLEncode(string):���ַ�������XML����

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

��֤�ຯ��
���ύʱ���
================================================================================

*/
function IsEmpty(obj)
{
    if(Trim(obj.value)=="")
    {

        alert(obj.desc+":"+"�ֶβ���Ϊ�ա�");        

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
����objΪ������
���ܣ�����Ƿ�Ϊ����
�ɷ��ڱ�Ԫ�ص�onblur�¼���Ҳ�ɷ��ڱ��ύʱ���
================================================================================
*/
function check_datatyp_int(obj)
{

   if(isNaN(obj.value))
	{
       alert(obj.desc+":"+"����������");
	   //obj.focus();
	   return false;
	}



	if (obj.value.indexOf(".")>=0)
	{
	   alert(obj.desc+":"+"�������������");
	   //obj.focus();
	   return false;
	}

	if (typeof(obj.length)!='undefined'&&obj.length.length>0)
   {
 
	   if (!check_length(obj))
		   return false;
   }
   
   if(obj.value<0){
	   alert(obj.desc+":"+"�������븺��");
	   //obj.focus();
	   return false;
   		
   	}
	return true;
}
/*
================================================================================
check_datatyp_float(obj)
����objΪ������
���ܣ��ж��Ƿ�Ϊ������
�ɷ��ڱ�Ԫ�ص�onblur�¼���Ҳ�ɷ��ڱ��ύʱ���
================================================================================
*/

function check_datatyp_float(obj)
{
	//alert('dd');
    if(isNaN(obj.value)){
       alert(obj.desc+":"+"�Ƿ��ַ�");
	   //obj.focus();
	   return false;
    }
   //�ж�С��λ��
    if (typeof(obj.length)!='undefined'&&obj.length.length>0) {//�ж��Ƿ��Ѿ����������ֵľ���
	var all_length=0;//��������λ��
	var float_length=0;//С��������
		//��ȡ���ֳ��ȹ���ֵ
		if(obj.value.indexOf(".")!=-1){//������С��λ��				
			all_length=new Number(obj.length.substring(0,obj.length.indexOf("."))) ;			
			float_length=new Number(obj.length.substr(obj.length.indexOf(".")+1));			
		}else{//û�ж���С��λ��
			all_length=new Number(obj.length.substring(0,obj.length.indexOf(".")));
		}
		//���ݾ����ж��Ƿ񳬱�
		if(obj.value.indexOf(".")!=-1){//����С��
			var int_str=obj.value.split(".")[0];//�������ֵ��ַ���
			var float_str=obj.value.split(".")[1];//С�����ֵ��ַ���
			
			if(float_str.length>float_length){//�ж�С���Ƿ񳬹�����
				alert(obj.desc+"�������ݵ�С��λ�����ܳ���"+float_length+"λ");
				obj.focus();
				return false;
			}
			if(int_str.length>14){//�ж������Ƿ񳬹�����
				alert(obj.desc+"��������ֲ��ܳ���14λ");
				obj.focus();
				return false;
			}
			if((int_str.length+float_str.length)>all_length){
				alert(obj.desc+"�������̫�����Ѿ������˼�¼���ķ�Χ");
				obj.focus();
				return false;
			}
			
		}else{//������С��
			if(obj.value.length>all_length){
				alert(obj.desc+"��������ֲ��ܳ���"+all_length+"λ");
				obj.focus();
				return false;
			}
		}
		
		
   
   if(obj.value<0){
	   alert(obj.desc+":"+"�������븺��");
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
*��������:�û�����ı���ȫ��������
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
		  alert(obj.desc+":"+"����������");
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
*  У��������Ƿ���Ӣ��
*  �û�����ı���ȫ����Ӣ��
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
	  alert(obj.desc+":"+"������Ӣ��");
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
* ��鵱ǰ�����ĳ��ȣ������ַ����ĳ����Ѿ��������ľ���
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
*  ��⸡�������ֵĳ���
*/
function check_float_length(obj)
{
   var tmpArr = obj.value.split(".");
   var tmpDeFineArr = obj.length.split(".");

   for (var i=0;i<tmpDeFineArr.length ;i++ )
   {
	   if (tmpArr[i].length>tmpDeFineArr[i])
	   {
	     alert(obj.desc+":"+tmpArr[i]+"�Ѿ�����")
	     obj.focus();
	     return false;
	   }
   }
   return true;
}


/*
================================================================================
* obj:��ҪУ��Ķ���maxLen:�ö����ܳ�������󳤶�
* У���ַ�������󳤶Ȳ��ܳ�����ǰ�������趨����󳤶�
================================================================================
*/


function check_string_length(obj)
{
   if (obj.value.length>obj.length)
   {
	   alert(obj.desc+":"+"���볤���ѳ���Ҫ�����󳤶�"+obj.length);
	   obj.focus();
	   return false;
   }
   return true;
}






/*
================================================================================
*  ���ú���
*  desc�ǵ����÷��������ʱ������ʾ�����ԣ�����������ȷ������
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
* ���¼����Ƿ�Ϊ��ȷ������
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
	  alert(obj.desc+":"+"��������ȷ�����ڸ�ʽ������'-'����");
	  obj.focus();
	  return false;
  }
  
  if (isNaN(tmpStr[0]))
  {
	  alert(obj.desc+":"+"����к��зǷ��ַ�");
	  obj.focus();
	  return false;
  }
  if ((tmpStr[0].length<1)||(tmpStr[0].length>4))
  {
	  alert(obj.desc+":"+"��ݵĳ���ֻ������λ");
	  obj.focus();
	  return false;
  }
  if (isNaN(tmpStr[1]))
  {
	  alert(obj.desc+":"+"�·��к��зǷ��ַ�");
	  obj.focus();
	  return false;
  }
  
  if ((parseFloat(tmpStr[1])<1)||(parseFloat(tmpStr[1])>12))
  {
	  alert(obj.desc+":"+"�·ݱ�����1-12֮��");
	  obj.focus();
	  return false;
  }
  if (isNaN(tmpStr[2]))
  {
	  alert(obj.desc+":"+"����к��зǷ��ַ�");
	  obj.focus();
	  return false;
  }
  if ((parseFloat(tmpStr[2])<1)||(parseFloat(tmpStr[2])>31))
  {
	  alert(obj.desc+":"+"����������1-31֮��");
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
	     alert("�绰����:�Ƿ��ַ���");
		 obj.focus();
         return false;
	  }
    }
      return true;
}

function check_wendu(obj){
	  if(isNaN(obj.value))
	{
       alert(obj.desc+":"+"�Ƿ��ַ�");
	   //obj.focus();
	   return false;
	}
	if (obj.value.indexOf(".")>=0)
	{
	   alert(obj.desc+":"+"�������������");
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
       alert(obj.desc+":"+"�Ƿ��ַ�");
	   //obj.focus();
	   return false;
    }
   //�ж�С��λ��
    if (typeof(obj.length)!='undefined'&&obj.length.length>0) {//�ж��Ƿ��Ѿ����������ֵľ���
	var all_length=0;//��������λ��
	var float_length=0;//С��������
		//��ȡ���ֳ��ȹ���ֵ
		if(obj.value.indexOf(".")!=-1){//������С��λ��				
			all_length=new Number(obj.length.substring(0,obj.length.indexOf(".")));			
			float_length=new Number(obj.length.substr(obj.length.indexOf(".")+1));			
		}else{//û�ж���С��λ��
			all_length=new Number(obj.length.substring(0,obj.length.indexOf(".")));
		}
		//���ݾ����ж��Ƿ񳬱�
		if(obj.value.indexOf(".")!=-1){//����С��
			var int_str=obj.value.split(".")[0];//�������ֵ��ַ���
			var float_str=obj.value.split(".")[1];//С�����ֵ��ַ���
			
			if(float_str.length>float_length){//�ж�С���Ƿ񳬹�����
				alert(obj.desc+"�������ݵ�С��λ�����ܳ���"+float_length+"λ");
				return false;
			}
			if((int_str.length+float_str.length)>all_length){
				alert(obj.desc+"�������̫�����Ѿ������˼�¼���ķ�Χ");
				return false;
			}
			
		}else{//������С��
			if(obj.value.length>all_length){
				alert(obj.desc+"��������ֲ��ܳ���"+all_length+"λ");
				return false;
			}
		}
		
		
   	
   	if(obj.name=='c101igrj'){
   			if(myform.c101igmicl!=null&&myform.c101igmicl.value!=''){
   					if(Number(obj.value)<Number(myform.c101igmicl.value)){
   							alert('������������ܴ����ݻ���');
   							return false;
   						}
   				}
   		}
   	if(obj.name=='c101igmicl'){
   			if(myform.c101igrj!=null&&myform.c101igrj.value!=''){
   					if(Number(obj.value)>Number(myform.c101igrj.value)){
   							alert('������������ܴ����ݻ���');
   							return false;
   						}
   				}
   		}
	
	return true;
    }else{
    	return true;
    }
}

//��֤�ֻ������
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
				alert("��������ȷ���ֻ�����\n����:13916752109");
				return false;
			}
}
//��֤��������
function chkiszip(obj){
		var s =obj.value;
		var pattern =/^[0-9]{6}$/;
		if(s!=""){
			if(!pattern.exec(s)){
				alert("��������ȷ����������!");
				obj.focus();
			}
		}
	}
function check_idCard(obj) {
			if (obj.value.isIdCard())  {
				return true;
			}else {
				alert("��������ȷ�����֤����");
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
		alert("��������ȷ���ʼ���ַ\n����:tom@hotmail.com");	
		obj.focus();
		return false;
	}
}