
//urlValue: 项目访问根路径 
//typeId: 数据库中对应的下拉类型id值
//comboboxObj_one: 一级下拉列对象
//comboboxObj_two: 二级下拉列对象
//comboboxObj_two: 三级下拉列对象
function onloadCombobox(urlValue,typeId,comboboxObj_one,comboboxObj_two,comboboxObj_three){
	comboboxObj_one.combobox({  
	   url:urlValue+'/data/dataAction!findDataByTypeFirstLevel.action?typeId='+typeId,
	   valueField:'id',  
	   textField:'dataName',
	   onChange:function(){
				if(comboboxObj_two != null){
				    //combobox_oneId: 父级id
					//注意这里不能使用$('#XXX').val()方法来获取下拉框的值
					//因为这里使用的是easyui的combobox组件，所以只能使用easyui提供的getValue方法
				    var combobox_oneId = comboboxObj_one.combobox('getValue');
				    comboboxObj_two.combobox({
				     	  url:urlValue+'/data/dataAction!findAllChildren.action?parentId='+combobox_oneId,
				          valueField:'id',
				          textField:'dataName',
				          onChange:function(){
							if(comboboxObj_three != null && comboboxObj_two !=null ){
							    //combobox_twoId: 父级id
								//注意这里不能使用$('#XXX').val()方法来获取下拉框的值
								//因为这里使用的是easyui的combobox组件，所以只能使用easyui提供的getValue方法
							    var combobox_twoId = comboboxObj_two.combobox('getValue');
							    comboboxObj_three.combobox({
							     	  url:urlValue+'/data/dataAction!findAllChildren.action?parentId='+combobox_twoId,
							          valueField:'id',
							          textField:'dataName'
							    });
							}
				    	}
				    });
				}
		}
	});    
}

//加载未通过上级菜单项选择，加载下级菜单项     参数1：上级对象       参数 2：此级对象
function onLoadSelect(urlValue,fatherObj,sonObj){
		var valueId = fatherObj.combobox('getValue');
		sonObj.combobox({
				    url:urlValue+'/data/dataAction!findAllChildren.action?parentId='+valueId,
				    valueField:'id',
				    textField:'dataName'
		});
}