<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织结构管理</title>

<!-- 将Tree对象引入进来 -->
<script type="text/javascript" src="${ctx}/js/CharacterUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js">
    </script>
<style type="text/css">
.top {
	border: 1px solid #aed0ea;
	background: #f2f5f7 url('images/ui-bg_glass_100_e4f1fb_1x400.png')
		repeat-x;
	line-height: 28px;
	padding-left: 15px;
	font-size: 14px;
	font-weight: bold;
	color: #222222;
}

.table_title {
	border: 1px solid #cccccc;
	background: #efefef;
	font-size: 14px;
	font-weight: bold;
	color: #2779aa;
	line-height: 28px;
	padding-left: 15px;
}

.table_nemu {
	font-size: 13px;
	border: 0px;
	text-align: right;
	padding-right: 15px;
	background: #EAF4FC;
	line-height: 28px;
}

.table_con {
	font-size: 13px;
	border: 0px;
	background: #fff;
	line-height: 28px;
	padding-left: 15px;
	width: 75%;
}

.btn1 {
	BORDER: #65b5e4 1px solid;
	PADDING-RIGHT: 15px;
	PADDING-LEFT: 15px;
	FONT-SIZE: 12px;
	FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,
		StartColorStr=#ffffff, EndColorStr=#d7ebf9);
	CURSOR: hand;
	COLOR: black;
	PADDING-TOP: 3px;
}

#tabs {
  overflow: hidden;
  width: 100%;
  margin: 0;
  padding: 0;
  list-style: none;
  position: relative;
  z-index: 90;
}

#tabs li {
  float: left;
  margin: 0 .5em 0 0;
}

#tabs a {
  position: relative;
  background: #ddd;
  background-image: linear-gradient(to bottom, #fff, #ddd);  
  padding: .7em 3.5em;
  float: left;
  text-decoration: none;
  color: #444;
  text-shadow: 0 1px 0 rgba(255,255,255,.8);
  border-radius: 5px 0 0 0;
  box-shadow: 0 2px 2px rgba(0,0,0,.4);
}

#tabs a:hover,
#tabs a:hover::after,
#tabs a:focus,
#tabs a:focus::after {
  background: #fff;
}

#tabs a:focus {
  outline: 0;
}

#tabs a::after {
  content:'';
  position:absolute;
  z-index: 990;
  top: 0;
  right: -.5em;  
  bottom: 0;
  width: 1em;
  background: #ddd;
  background-image: linear-gradient(to bottom, #fff, #ddd);  
  box-shadow: 2px 2px 2px rgba(0,0,0,.4);
  transform: skew(10deg);
  border-radius: 0 5px 0 0;  
}

#tabs #current a,
#tabs #current a::after {
  background: #fff;
  z-index: 999;
}

#content {
  background: #fff;
  padding: 2em;
  height: 90%;
  border:1px solid lightgray;
  position: relative;
  z-index: 2; 
  border-radius: 0 5px 5px 5px;
  box-shadow: 0 -2px 3px -2px rgba(0, 0, 0, .5);
}

.nameDiv {
	background-color: rgb(78, 194, 241);
	margin-left: 5px;
	float: left;
	margin-top: 5px;
	padding-left: 10px;
	position: relative;
	padding-right: 10px;
}
</style>
<script type="text/javascript">
        var parentId = null;
		var grid = null;
        /*
         * 回调函数
         */
        function doSearch(id, name, type) {
            if (id != '-1') {
            	$.ajax({
                    url:'${ctx}/dept/deptAction!getById.action?id=' + id,
                    success:function (data) {
                    	clearForm();
                    	if(data == null){
                    		$.ajax({
                    			url:'${ctx}/user/userAction!getById.action?id=' + id,
                    			success:function(edata){
                    				clearForm();
                    				$('#id').val(edata.id);
                                    $('#name').val(edata.userName);
                                    $('#watchers').html(edata.userName);
                                    $('#watchersinput').val(edata.id);
        	                    	$.ajax({
        	                        	url:'${ctx}/watch/watchAction!getById?id='+edata.id,
        	                        	success: function(adata){
        	                        		if(adata == null || adata == ""){
        	                        			//console.log(edata.id);
        	                        			//grid = new Grid('${ctx}/byWatchersInfo/byWatchersInfoAction!list?byWatchersInfo.watchId='+edata.id+'&byWatchersInfo.byWatchersType=1','data_list_ent');
        	                        			//grid.loadGrid();
        	                        			//dgrid = new Grid('${ctx}/byWatchersInfo/byWatchersInfoAction!list?byWatchersInfo.watchId='+edata.id+'&byWatchersInfo.byWatchersType=2','data_list_dept');
        	                        			//dgrid.loadGrid();
        	                        			$('#byWatchers').val('');
        	                        			$('#backByWatchersShow').val('');
        	                        			$('#showIndex').html('');
        	                        		}else{
	        	                        		//grid = new Grid('${ctx}/byWatchersInfo/byWatchersInfoAction!list?byWatchersInfo.watchId='+edata.id+'&byWatchersInfo.byWatchersType=1','data_list_ent');
	        	                        		//grid.loadGrid();
	        	                        		//dgrid = new Grid('${ctx}/byWatchersInfo/byWatchersInfoAction!list?byWatchersInfo.watchId='+edata.id+'&byWatchersInfo.byWatchersType=2','data_list_dept');
	    	                        			//dgrid.loadGrid();
        	                        			$('#byWatchers').val(adata.byWatchers);
        	                        			$('#backByWatchersShow').val(adata.byWatchersShow);
        	                        			var arr = adata.byWatchersShow.split(',');
        	                        			var shownnn = "";
        	            	   				    for(var key in arr){
        	            	   					   shownnn += "<div class='nameDiv'>"+arr[key]+"</div>&nbsp;&nbsp;";
        	            	                    }
        	                        			$('#showIndex').html(shownnn);
        	                        		}
        	                        	}
        	                        });
                    			}
                    		});
                    	}else{
	                        $('#id').val(data.id);
	                        $('#name').val(data.deptName);
	                        $('#watchers').html(data.deptName);
	                        $('#watchersinput').val(data.id)
	                        parentId = data.parentId;
	                        $('#tas1id').val(data.id);
	                        $('#tas1parentId').val(data.parentId);
	                        $('#tas1name').val(data.deptName);
	                        $('#tas1order').val(data.deptOrder);
	                        $('#province').combobox('setValue',data.province);
	                        $('#province').combobox('setText',data.provinceTxt);
	                        $('#city').combobox('setValue',data.city);
	                        $('#city').combobox('setText',data.cityTxt);
	                        $('#area').combobox('setValue',data.area);
	                        $('#area').combobox('setText',data.areaTxt);
	                        $('#street').combobox('setValue',data.street);
	                        $('#street').combobox('setText',data.streetTxt);
	                    	$.ajax({
	                        	url:'${ctx}/watch/watchAction!getById?id='+data.id,
	                        	success: function(adata){
	                        		if(adata == null || adata == ""){
	                        			//dgrid = new Grid('${ctx}/byWatchersInfo/byWatchersInfoAction!list?byWatchersInfo.watchId='+data.id+'&byWatchersInfo.byWatchersType=2','data_list_dept');
	                        			//dgrid.loadGrid();
	                        			//grid = new Grid('${ctx}/byWatchersInfo/byWatchersInfoAction!list?byWatchersInfo.watchId='+data.id+'&byWatchersInfo.byWatchersType=1','data_list_ent');
	                        			//grid.loadGrid();
	                        			$('#byWatchers').val('');
	                        			$('#backByWatchersShow').val('');
	                        			$('#showIndex').html('');
	                        		}else{
	                        			//dgrid = new Grid('${ctx}/byWatchersInfo/byWatchersInfoAction!list?byWatchersInfo.watchId='+data.id+'&byWatchersInfo.byWatchersType=2','data_list_dept');
	                        			//dgrid.loadGrid();
	                        			//grid = new Grid('${ctx}/byWatchersInfo/byWatchersInfoAction!list?byWatchersInfo.watchId='+data.id+'&byWatchersInfo.byWatchersType=1','data_list_ent');
	                        			//grid.loadGrid();
	                        			$('#byWatchers').val(adata.byWatchers);
	                        			$('#backByWatchersShow').val(adata.byWatchersShow);
	                        			var arr = adata.byWatchersShow.split(',');
	                        			var shownnn = "";
	            	   				    for(var key in arr){
	            	   					   shownnn += "<div class='nameDiv'>"+arr[key]+"</div>&nbsp;&nbsp;";
	            	                    }
	                        			$('#showIndex').html(shownnn);
	                        		}
	                        	}
	                        });
                    	}
                    }
                });
            } else {
                $('#id').val('');
                $('#parentId').val('-1');
            }
        }
        
        function gridFormatterLength(value, rowData, rowIndex) {
        	if(value==null || value == '' || value == 'undefined')
        		return '';
    		if(value.length > 25)
    			return value.substring(0, 25) + '...';
    		return value;
    	}
        
        function clearForm(){
        	$('#tas1id').val("");
            $('#tas1parentId').val("");
            $('#tas1name').val("");
            $('#tas1order').val("");
            $('#province').combobox('setValue','');
            $('#city').combobox('setValue','');
            $('#area').combobox('setValue','');
            $('#street').combobox('setValue','');
           	
           	$('#id').val("");
            $('#parentId').val("");
            $('#name').val("");
            $('#watchers').html("");
            $('#watchersinput').val("")
           	
        }

        // 添加新的部门
        function addNew() {
            var idVal = $('#tas1id').val();
            $('#tas1id').val('');
            $('#tas1name').val('');
            $('#tas1order').val('');
            $('#parentType').val(1);
            $('#province').combobox('setValue','');
            $('#city').combobox('setValue','');
            $('#area').combobox('setValue','');
            $('#street').combobox('setValue','');
            if (parentId == null && idVal != null && idVal != '') {
                $.messager.show({
                    title:'提示',
                    msg:'请选择父类结点',
                    timeout:2000,
                    showType:'show'

                });
                return;
            } else {
                if (idVal == null || idVal == '') {
                    $('#tas1parentId').val('-1');
                } else {
                    $('#tas1parentId').val(idVal);
                }
            }
        }

        var tree = null;
        var key = null;

        $(document).ready(function () {
        	instAllTable();
            tree = new Tree('${ctx}/dept/deptAction!reloadDeptTree.action?dept.parentType=1', 'dept_tree', 'tree_search', 'key', doSearch);
//             tree = new Tree('${ctx}/sysGroup/sysGroup!reloadDeptUserTree.action', 'dept_tree', 'tree_search', 'key', doSearch);
            tree.loadTree();
            $('#addBtn').linkbutton();
            $('#removeBtn').linkbutton();
            $('#saveBtn').linkbutton();
            $('#tab2saveBtn').linkbutton();
            grid = new Grid('${ctx}/byWatchersInfo/byWatchersInfoAction!list?byWatchersInfo.id=&byWatchersInfo.byWatchersType=2','data_list_ent');
            //加载省
			  $('#province').combobox({
				    url:'${ctx}/data/dataAction!findByType?parentId=-1&typeId=0b8415e08bf3474686e643318c0a497c',
				    valueField:'dataCode',
				    textField:'dataName',
				    width:100
			});
			//加载市
			  $('#province').combobox({
			    	 onSelect: function(opt){
			    		 $('#city').combobox('setValues','');//清除区值
			    		 $('#street').combobox('setValues','');//清除区值
			    		 $('#area').combobox('setValues','');//清除区值
			    		 $('#city').combobox({
			    			 width:100
			    		 });
			    		 loadXzqhjd(opt.dataCode,"city");
			    	 }
			   });
			//加载区
			  $('#city').combobox({
				  onSelect:function(opt){
					  $('#area').combobox({
			    			 width:100
			    		 });
					  loadXzqhjd(opt.dataCode,"area");
				  }
			  });
			//加载街道
			$("#area").combobox({
				onSelect:function(opt){
					$('#street').combobox({
		    			 width:100
		    		 });
					loadXzqhjd(opt.dataCode,"street");
				}
			});
			
			$("#content").find("[id^='tab']").hide(); // Hide all content
		    $("#tabs li:first").attr("id","current"); // Activate the first tab
		    $("#content #tab1").fadeIn(); // Show first tab's content
		    
		    $('#tabs a').click(function(e) {
		        e.preventDefault();
		        if ($(this).closest("li").attr("id") == "current"){ //detection for current tab
		         return;       
		        }
		        else{             
		          $("#content").find("[id^='tab']").hide(); // Hide all content
		          $("#tabs li").attr("id",""); //Reset id'ne
		          $(this).parent().attr("id","current"); // Activate this
		          $('#' + $(this).attr('name')).fadeIn(); // Show content for the current tab
		        }
		    });

        });

        // 更新或者是保存时调用的方法
        function saveForm() {
        	if($("#industryTypeNameStr").val()==''){
        		alert('请添加行业类别！');
        		return false;
        	}
            $('#deptForm').form({
                url:'${ctx}/dept/deptAction!saveOrUpdate.action?',
                success:function (data) {
                	var data = eval('(' + data + ')');
                    if (data.operateSuccess) {
                    	$.messager.alert('成功',data.operateMessage); 
                    } else {
                    	$.messager.alert('失败',data.operateMessage);
                    }
                    tree.loadTree();
                }
            });
            $('#deptForm').submit();
        }
        
        function setValue(){
        	var vales = $(".ord").val();
        	$("#order").val(vales);
        }
        function loadXzqhjd(code,conten){
			$("#"+conten).combobox({
				url:'${ctx}/data/dataAction!findCodeAndType?typeId=0b8415e08bf3474686e643318c0a497c&dataCode='+code,
    			 valueField:'dataCode',
    			 textField:'dataName'
			 });
		}
        
        function setUserInfo(ids,idsShow,shownnn){

        	$('#byWatchersTemp').val(ids.join(','));
			$('#backByWatchersShowTemp').val(idsShow.join(','));
			$('#showTemp').val(shownnn);
			
			 var watchers = $('#byWatchersTemp').val();
			 var watchersShow = $('#backByWatchersShowTemp').val();
			 var showTemp = $('#showTemp').val();
			 $('#byWatchers').val(watchers);
			 $('#backByWatchersShow').val(watchersShow);
			 $('#showIndex').html(showTemp);
        }
		
        function getSerch() {
    		var t = $.fn.zTree.getZTreeObj('#dept_ent_tree');
    		//console.log(t);

    		t.expandAllNode(true);
    		t.updateNodes(false);
    		
    		var value = $.trim($('#twkey').val());
    		var keyType = "name";
    		if (value === "") {
    			return;
    		}
    		
    		var filterNode = function(node) {
    			value = value.toUpperCase();
    			var py = String2Alpha(node.name);
    			if (py.indexOf(value) > -1) {
    				return true;
    			}

    			return (node.name.indexOf(value) > -1);
    		};
    		t.filterNodeList = zTree.getNodesByFilter(filterNode);
    		t.updateNodes(true);

    	}
        
        function panelShow(){
        	if ($('#dree_ent_search').css('display') == "none") {
    			$('#dree_ent_search').show();
    		} else {
    			$('#dree_ent_search').hide();
    		}
        }
        
    	function openWin(){
    		var tree=$.fn.zTree.getZTreeObj("dept_ent_tree");
    		var nodes=tree.getNodes();
    		var divNames=$("#showIndex").find(".nameDiv");
    		for(var i=0;i<nodes.length;i++){
    			$(divNames).each(function(){
    				if(nodes[i].name==$(this).text()){
    					tree.checkNode(nodes[i], true, true);
    				}
    			});
    		}
			var byWatchers = $('#byWatchers').val();
			var byWatchersShow = $('#backByWatchersShow').val();
    		window.open('${ctx}/jsp/sys/dept/userEntTree.jsp?byWatchers='+byWatchers+'&byWatchersShow='+byWatchersShow);
    		$('#treeWin').dialog('open');
    	}

        // 更新或者是保存时调用的方法
        function saveAuthorityForm() {
        	if($("#id").val()=='' || $('#watchersinput').val() == ''){
        		$.messager.alert('提示','请选择部门或人员');
        		return false;
        	}
            $('#authorityForm').form({
                url:'${ctx}/watch/watchAction!saveOrUpdate.action',
                success:function (data) {
                	var data = eval('(' + data + ')');
                    if (data.operateSuccess) {
                    	$.messager.alert('成功',data.operateMessage); 
                    } else {
                    	$.messager.alert('失败',data.operateMessage); 
                    }
                    tree.loadTree();
                }
            });
            $('#authorityForm').submit();
        }
     // 删除一个部门结点
        function removeDeptNode() {
        	$.messager.confirm('警告', '是否要删除该记录?', function (r) {
                if (r) {
		            var idVal = $('#id').val();
		            $.ajax({
		                url:'${ctx}/dept/deptAction!deleteDeptById.action?id=' + idVal,
		                method:'POST',
		                success:function (data) {
		                    if (data.operateSuccess) {
		                    	$.messager.alert('成功',data.operateMessage); 
		                    } else {
		                    	$.messager.alert('失败',data.operateMessage); 
		                    }
		                    tree.loadTree();
		                }
		            });
        		}
        	});
        }
        
        function instAllTable(){
        	$.ajax({
        		url:'${ctx}/watch/watchAction!getAllEntTable.action',
        		success:function(data){
        			var seaDat = eval('('+data+')');
        			var name = "";
        			var table = "";
        			var showName = "";
        			for(var key in seaDat){
        				if(name.length>1){
        					name += ","
        				}
        				
        				if(table.length>1){
        					table += ",";
        				}
        				
        				if(showName.length>1){
        					showName+="&nbsp;";
        				}
        				name += seaDat[key].name;
        				showName += "<div class='nameDiv'>"+seaDat[key].name+"</div>";
        				table += seaDat[key].id;
                    }
        			$('#shownTable').html(showName);
        			$('#backTables').val(table);
        			$('#backMods').val(name);
        			
        		}
        	});
        }
        function reloadAllGrid(){
        	grid.reloadGrid();
        	dgrid.reloadGrid();
        }
    </script>
</head>
<body class="easyui-layout" id="main_layout">
 <div region="center" style="overflow: hidden;" border="true" plain="true" id="center">
	 <ul id="tabs">
<!-- 	    <li><a href="#" name="tab1">组织机构维护</a></li> -->
<!-- 	    <li><a href="#" name="tab2">数据权限维护</a></li> -->
	</ul>
	
	<div id="content"> 
	    <div id="tab1"><div>
				<br> <a href="#" id="addBtn" iconCls="icon-save"
					onclick="addNew()" style="margin-left: 10px;">新增</a> <a href="#"
					id="removeBtn" iconCls="icon-remove" onclick="removeDeptNode();">删除</a>
				<a href="#" id="saveBtn" iconCls="icon-save" onclick="saveForm()">保存</a>
			</div>
			<div style="margin-top: 5px; margin-left: 10px;">
				<form id="deptForm" method="post">
					<input type="hidden" id="tas1id" name="dept.id" /> 
					<input type="hidden" id="tas1parentId" name="dept.parentId" /> 
					<input type="hidden" id="parentType" name="dept.parentType" />
					<table cellspacing="1" border="0" bgcolor="#aed0ea" width="70%">
						<tr>
							<td class="table_nemu" width="20%">名称</td>
							<td class="table_con" width="80%">
							<input class="easyui-validatebox" data-options="required:true,validType:'length[0,25]',invalidMessage:'输入的内容不能超过25个字符'"
								id="tas1name" name="dept.deptName" /></td>
						</tr>
						<tr>
							<td class="table_nemu">职能等级</td>
							<td class="table_con">
								<select class="easyui-combobox" name="dept.province" width="177px" panelHeight="auto" >
									<option value="部门">部门</option>
									<option value="管理岗位">管理岗位</option>
									<option value="职位">职位</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="table_nemu">排序</td>
							<td class="table_con"><input class="easyui-validatebox ord"
								data-options="required:true" validtype="positiveInt"
								onfocus="setValue();" id="tas1order" name="dept.deptOrder" /></td>
						</tr>
					</table>
				</form>
 </div></div>
	    <div id="tab2">
	    	<div>
			<br> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAuthorityForm()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="openWin()">选择企业</a>
				<input type="hidden" id="byWatchersTemp">
			<input type="hidden" id="backByWatchersShowTemp"> 
			<input type="hidden" id="showTemp">
		</div>
		
		<div style="margin-top: 5px;">
			<form id="authorityForm" method="post">
				<input type="hidden" id="id" name="watch.id" /> 
				<input type="hidden" id="watchersinput" name="watch.watchers" /> 
				<input type="hidden" id="name" name="watch.watchersShow" /> 
				<input type="hidden" id="backTables" name="watch.modules" /> 
				<input type="hidden" id="backMods" name="watch.modulesShow" /> 
				<input type="hidden" id="byWatchers" name="watch.byWatchers" /> 
				<input type="hidden" id="backByWatchersShow" name="watch.byWatchersShow" /> 
				<input type="hidden" name="watch.watchType" value="2" />
				<table cellspacing="1" border="0" bgcolor="#aed0ea" width="90%">
					<tr>
						<td class="table_nemu" width="20%">监管者</td>
						<td class="table_con" width="90%"><span id="watchers"></span>
						</td>
					</tr>
					<tr>
						<td class="table_nemu">监管单位</td>
						<td class="table_con">
<!-- 							<div id="ts" class="easyui-tabs" style="width:750px;height:200px;"> -->
<!-- 							    <div title="监管企业" style="padding:5px;"> -->
<!-- 							   		<table id="data_list_ent" loadMsg="正在努力拉取数据中..." fitColumns="true"> -->
<!-- 							        	 <thead> -->
<!-- 									        <tr> -->
<!-- 									            <th align="center" field="byWatchersName" width="100%" formatter="gridFormatterLength">名称</th> -->
<!-- 									            <th align="center" field="id" width="120" formatter="gridFormatter" rowspan="2">操作</th>          -->
<!-- 									        </tr> -->
<!-- 								        </thead> -->
<!-- 							        </table> -->
							        <div id="showIndex"
									style="width: 700px; height: 100px; border: 1px solid gray;"
									onclick="openWin()"></div>
<!-- 							    </div> -->
<!-- 							    <div title="监管行业" data-options="closable:true" style="overflow:auto;padding:5px;"> -->
<!-- 							        <table id="data_list_dept" loadMsg="正在努力拉取数据中..." fitColumns="true"> -->
<!-- 							        	 <thead> -->
<!-- 									        <tr> -->
<!-- 									            <th align="center" field="byWatchersName" width="100%" formatter="gridFormatterLength">名称</th> -->
<!-- 									            <th align="center" field="id" width="120" formatter="gridFormatter" rowspan="2">操作</th>          -->
<!-- 									        </tr> -->
<!-- 								        </thead> -->
<!-- 							        </table> -->
<!-- 							    </div> -->
<!-- 							</div> -->
							
						</td>
					</tr>
					<tr>
						<td class="table_nemu" width="20%">监管模块</td>
						<td class="table_con" width="80%"><span id="shownTable" ></span></td>
					</tr>
				</table>
			</form>
		</div>
	    </div>
	</div>
	 
			
	</div>
	<div region="west" split="true" id="main_west" border="true"
		plain="true" style="width: 220px; padding1: 1px; overflow: hidden;">
		<div id="p" class="easyui-panel" title="&nbsp;&nbsp;组织机构管理" fit="true"
			tools="#tt" border="false" plain="true">
			<div id="tree_search" style="display: none">
				关键字:&nbsp;&nbsp;<input type="text" id="key" name="key"
					style="width: 80px; margin-top: 3px; height: 25px;" />&nbsp;&nbsp;<a
					href="#" class="easyui-linkbutton" iconCls="icon-search"
					plain="true" onclick="tree.searchNode();">搜索</a>
			</div>
			<div id="dept_tree" class="ztree"></div>
		</div>
		<div id="tt">
			<a href="#" class="icon-treeSearch"
				onclick="javascript:tree.changeTreeSearchPanelShow();" title="搜索"></a>
			<a href="#" class="icon-treeRefresh"
				onclick="javascript:tree.loadTree();" title="刷新"></a> <a href="#"
				class="icon-treeAdd" onclick="javascript:tree.expandAllNode(false);"
				title="收缩所有"></a> <a href="#" class="icon-treeDelete"
				onclick="javascript:tree.expandAllNode(true);" title="展开所有"
				style="margin-right: 10px;"></a>
		</div>
	</div>
</body>
</html>