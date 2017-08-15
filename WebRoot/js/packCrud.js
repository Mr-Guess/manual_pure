var grid = null;

//action 路径
var urlValue = null;
var entityName
//加载分页数据
function init(url,entity,title) {
	urlValue = url;
	entityName = entity;
		$('#updateWin').window({
				        width:800,
				        height:450,
				        modal:true,
				        draggable:false,
				        closed:true,
				        onOpen:function(){
				        	$(".window-mask").css("height","100%");
				        }
		});
		
	   $('#addWin').window({
	                width:800,
	                height:450,
	                modal:true,
	                draggable:false,
	                closed:true,
	                onOpen:function(){
	                $(".window-mask").css("height","100%");
	                }
	    });
	  
	    $('#viewWin').window({
			         width:800,
			         height:450,
			         modal:true,
			         draggable:false,
			         closed:true,
			         onOpen:function(){
			         	$(".window-mask").css("height","100%");
			         }
	     });
	    
         grid = new Grid(title+'列表', 'icon-edit',
        		 		urlValue+'!list.action','data_list');
         
         //允许使用xx.js文件
         var head = document.getElementsByTagName('head');
         var testScript = document.createElement('script');
         testScript.src = 'GridUtilNoToolBar.js';
         testScript.type = 'text/javascript';
         head[0].appendChild(testScript);
         //加载数据
         grid.loadGrid();
 }

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

//当点击查看时
function viewFunction(rowId) {
    $('#viewWin').window('open');
    fillViewForm(rowId);
}

//显示修改窗体
function editFunction(rowId) {
	$('#updateWin').window('open');
    fillUpdateForm(rowId);
}

// 当点击添加按钮时做的操作
function addFunction() {
	//清空数据
    $('#addForm')[0].reset();
    $('#addWin').window('open');
}

//关闭窗体
 function closeAllWin() {
    $('#addWin').window('close');
    $('#updateWin').window('close');
    $('#viewWin').window('close');
}

//条件查询
 function searchForm() {                       
  //提交查询表单
	 $('#searchForm').form({
	     url:urlValue+'!list.action',
	     success:function (data) {
	     	var data = eval('(' + data + ')');
			closeAllWin();
	        grid.reloadGridByData(data);
	     }
	 });
	 $('#searchForm').submit();
}

// 提交添加菜单请求
 function addSubmitForm() {
	    //alert(urlValue);
	    $('#addForm').form({
	        url:urlValue+'!add.action',
	        method:'POST',
	        success:function (data) {
	        	var data = eval('(' + data + ')');
	        	closeAllWin();
	        	 $.messager.show({
                     title:'提示',
                     msg:data.operateMessage,
                     timeout:2000,
                     showType:'slide'
                 });
	            if (data.operateSuccess) {
	                grid.reloadGrid();
	            } 
	        }
	    });
	    $('#addForm').submit();
	}
 
//根据选择 删除信息
  function removeFunction() {
    var rows = grid.getSelectNodes();
    if (rows.length == 0) {
        $.messager.show({
            title:'提示',
            msg:'请选择要删除的行',
            timeout:2000,
            showType:'slide'
        });
        return;
    } else {
        var ids = new Array();
        $.messager.confirm('警告', '是否要删除该记录?', function (r) {
            if (r) {
                var i = 0;
                for (i=0; i<rows.length; i++) {
                    ids.push(rows[i].id);
                }
                $.ajax({
                    url:urlValue+'!deleteByIds?ids=' + ids,
                    method:'POST',
                    success:function(data) {
                    	var data = eval('(' + data + ')');
                    	 $.messager.show({
                             title:'提示',
                             msg:data.operateMessage,
                             timeout:2000,
                             showType:'slide'
                         });
                        if (data.operateSuccess) {
                            grid.reloadGrid();
                        } 
                    }
                });
            }
        });
    }
}

//提交修改form
 function updateSubmitForm() {
    $('#updateForm').form({
        url:urlValue+'!update.action',
        success:function (data) {
        	closeAllWin();
        	var data = eval('(' + data + ')');
        	 $.messager.show({
                 title:'提示',
                 msg:data.operateMessage,
                 timeout:2000,
                 showType:'slide'
             });
            if (data.operateSuccess) {
                grid.reloadGrid();
            } 
        }
    });
    $('#updateForm').submit();
}

//填充更新表单中的数据
 function fillViewForm(value) {
 	var rowsid;
 	if (typeof value == "undefined") {
 		var rows = grid.getSelectNode();
 		rowsid = rows.id;
 	} else {
 		rowsid = value;
 	}
 	$.ajax( {url : urlValue+'!getById.action?id=' + rowsid,
 				method : 'POST',
 				success : function(data) {
 					$.each(data,
 							function(key, value) {
 								//判断value是否等于NULL
 								if(value == null || value == ''){
 									value = '';
 								}
 								 $('#viewWin label[name="'+entityName+'.' + key + '"]').text(value);
 							});
 				}
 			});
 }