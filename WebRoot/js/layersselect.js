 /*
  *selectobj:   待设置的下拉框对象
  *fathervalue: 该下拉框列表的父索引值
  *selectarray: 该下拉列表所对应的数据数组
  */
function configSelect(selectobj,fathervalue,selectarray){
	//alert(selectobj+fathervalue)
  	if(selectobj.options.length>0){
  		for (m=selectobj.options.length-1;m>=0;m--){
			selectobj.remove(m);//=null;
		}
	}
	
  	
	selectobj.options[0]=new Option("--","")
	for (i=0,j=1;i<selectarray.length;i++){
		if(selectarray[i][2]==fathervalue){
			selectobj.options[j]=new Option(selectarray[i][0],selectarray[i][1]);
			j++;
		}
	}
	//alert(selectobj+fathervalue+"end")
}
           
           
function corpkey_abc2corpkey(corpkey_a,corpkey_b,corpkey_c,corpkey){
	corpkey.value=corpkey_a.value+"_"+corpkey_b.value+"_"+corpkey_c.value;
}
        
//从单位代码字段到分节字段
function corpkey2corpkey_abc(obj){
	//alert(obj.corpkey_a.value);
	obj.corpkey_a.value="d";
	obj.corpkey_b.value="d";
	obj.corpkey_c.value="d";
}
        
        
function date2ymd(dateobj,yobj,mobj,dobj){
	//alert(dateobj.name)
	if(dateobj.value!=null&&dateobj.value.length>0){
   		//alert(obj.offsetParent.tagName)
   		var date_strs=dateobj.value.substr(0,10).split("-");
   		var date=new Date(date_strs[0],new Number(date_strs[1])-1,date_strs[2]);
   		//alert(date);
   		yobj.value=(date.getFullYear());        		
   		mobj.value=(date.getMonth()+1);
   		dobj.value=(date.getDate());
   	}
   	//alert(dateobj.name)
}

function ymd2date(dateobj,yobj,mobj,dobj){
	var date=new Date();
	if(dateobj.value!=null&&dateobj.value.length>0){
		var date_strs=dateobj.value.substr(0,10).split("-");
		date=new Date(date_strs[0],new Number(date_strs[1])-1,date_strs[2]);
	}
	if(yobj.value!=null&&yobj.value.length>0&&(isNaN(new Number(yobj.value))==false)){
		//alert(new Number(yobj.value)+isNaN(parseInt(yobj.value))+yobj.value)
		date.setFullYear(yobj.value);
 	}
 	if(mobj.value!=null&&mobj.value.length>0&&(isNaN(new Number(mobj.value))==false)){
 		//alert(mobj.value)
		date.setMonth(new Number(mobj.value)-1);
	}
	if(dobj.value!=null&&dobj.value.length>0&&(isNaN(new Number(dobj.value))==false)){
		//alert(dobj.value)
		date.setDate(dobj.value);
	}
		
	dateobj.value=(date.getFullYear())+"-"+(date.getMonth()+1)+"-"+(date.getDate()); 
	//alert(date+"\n"+dateobj.value)      
	date2ymd(dateobj,yobj,mobj,dobj) 		
	//mobj.value=(date.getMonth()+1);
	//dobj.value=(date.getDate());
	
}
        
function COMPARE_C001NZUR_C001NLR(obj){
	if(obj.form.c001nzur.value==""||obj.form.c001nlr.value==""){
		return;
	}
	var C001NZUR=new Number(obj.form.c001nzur.value);
	var C001NLR=new Number(obj.form.c001nlr.value);
	if(isNaN(C001NZUR)||isNaN(C001NLR)){
		return;
	}
	if(C001NZUR<C001NLR){
		alert("年利润不能高于年总收入");
		obj.form.c001nzur.value=C001NLR;
	}
	
	//alert(obj.name)
}
           
function C101YWFHD(obj){
	if(obj.value==0){
		obj.form.c101fhdmj.value="";
		obj.form.c101fhdmj.readOnly=true;
	}else{
		obj.form.c101fhdmj.readOnly=false;
	}
}
        
function C101IGIGXV(obj){
	if(obj.value=="3 球形罐"){
		obj.form.c101igigxu.value="";
		obj.form.c101igigxu.style.display ="none";
	}else{
		obj.form.c101igigxu.style.display ="";
   }
}
        
           