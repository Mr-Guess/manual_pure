/*
================================================================================
*  У����������
*  chkTypeΪУ�����ͣ�����int,float,chinese,english,string
================================================================================
*/
	



function check_input_text_textarea(obj){   
	
	check_text_textarea_space(obj);
	
	var chkType=obj.datatype;	
	var inputvalue=obj.value;
        var valuelength=obj.valuelength;
	
        if (valuelength!=null)
        {
                if(inputvalue.length!=valuelength)
                {           
                    alert("��������"+valuelength+"�ַ�");
                    obj.focus();
                    return false;
                }
	}
	if(inputvalue==""||inputvalue=="null")
	{
		return true;
	}		
    else if (chkType=='int')
        {
		return check_datatyp_int(obj);
	}
	 else if (chkType=='telNumber')
    {
		return check_telphoneNumber(obj);
	}
	 else if (chkType=='idCard')
    {
		return check_idCard(obj);
	}
	else if (chkType=='integer')
	{
		return check_wendu(obj);
	}
	else if(chkType == 'income')
	{
		return check_income(obj);
	}
	else if (chkType=='float'||chkType=='decimal')
	{
		return check_datatyp_float(obj);
	}
	else if (chkType=='chinese')
	{
		return check_datatyp_chinese(obj);
	}
	else if (chkType=='english')
	{
		return check_datatyp_english(obj);
	}
	else if (chkType=='date')
	{
		return check_datatyp_date(obj);
	}
	
////////////////////////////////////////////////xzp	
	
        else if (chkType=='datetime')
	{
		return check_datatyp_date(obj);
	}
	else if (chkType=='smalldatetime')
	{
		return check_datatyp_date(obj);
	}
	else if (chkType=='numeric')
	{
		return check_datatyp_float(obj);
	}
	else if (chkType=='real')
	{
		return check_datatyp_int(obj);
	}
        else if(chkType=='text')
        {
                return check_string_length(obj);
        }
        else if(chkType=='varchar')
        {
                return check_string_length(obj);
        }
        else if(chkType=='nvarchar')
        {
                return check_string_length(obj);
        }
		else if(chkType=='mobile')
        {
                return chkisMobile(obj);
        }
		else if(chkType=='zip')//��֤��������
        {	
                return chkiszip(obj);
        }
		else if(chkType=='email')
        {
                return check_data_email(obj);
        }
        else if (chkType=='bit')
        {
		return true;
	}

//////////////////////////////////////////xzp
}



/*************************************************  ѭ����֤  ****************************************************/

 function check_form(obj){

if(obj.password!=null){
	if(obj.password.value.length<8){
		alert('���볤�Ȳ���С��8λ��');
		return false;
	}
}

		inputs=obj.getElementsByTagName("input");
		for(i=0;i<inputs.length;i++){
			
			   if(inputs[i].type=="text"||inputs[i].type=="file"||inputs[i].type=="password"){
			   	
				     if(check_text_textarea_space(inputs[i])==false){
				     	//alert(inputs[i].name+"��ֵ����");
				     	return false;
				     }
				     if(check_input_text_textarea(inputs[i])==false){
				     	//alert(inputs[i].name+"Ч�����");
				     	return false;
				     }
				     //alert(inputs[i].name+"ͨ��Ч��");
				     
			  }
		}
		inputs=obj.getElementsByTagName("textarea");
		for(i=0;i<inputs.length;i++){
			 
			   	
				     if(check_text_textarea_space(inputs[i])==false){
				     	//alert(inputs[i].name+"��ֵ����");
				     	return false;
				     }
				     if(check_input_text_textarea(inputs[i])==false){
				     	//alert(inputs[i].name+"Ч�����");
				     	return false;
				     }
				     //alert(inputs[i].name+"ͨ��Ч��");
				     
			
		}
		
		inputs=obj.getElementsByTagName("select");
		for(i=0;i<inputs.length;i++){
			 
			   	
				     if(check_text_textarea_space(inputs[i])==false){
				     	//alert(inputs[i].name+"��ֵ����");
				     	return false;
				     }
				   
				     //alert(inputs[i].name+"ͨ��Ч��");
				     
			
		}


		
		if(obj.c1ss!=null){
			obj.c1ss.disabled=false;
			}
			
			
		if(obj.sfdm!=null){
			obj.sfdm.disabled=false;
			}
		
		return true;	
 }
 

/*
*��֤�ı��������Ƿ���Ͽ�ֵԼ��������Ҫ���򷵻�true���������򷵻�false����������ʱҪ�󷢳�alert�쳣��ʾ��
*/
function check_text_textarea_space(obj){
	
	
	if(obj.space_able=="true"){
	   return true;
	}
	if(obj.space_able=="false"){
		if(obj.value==null||Trim(obj.value)==""){
			alert(obj.desc+":"+"����Ϊ��");
			//obj.focus();
			return false;
		}
	}
	
	return true;
}


function showHelp(obj){
   
    if(false&&navigator.appVersion.indexOf("MSIE 6")!=-1){
    var oPopup = window.createPopup();
    var oPopBody = oPopup.document.body;
    oPopBody.style.backgroundColor = "lightyellow";
    oPopBody.style.border = "solid black 1px";
    oPopBody.innerHTML = obj.displayname;
    oPopup.show(0, 20, 180, 25, obj);
    }
    //alert()
}
function check_min(obj){
	if(obj.value<1753){
		alert("���ڲ�����С��1753��")
		obj.focus();
	}
}