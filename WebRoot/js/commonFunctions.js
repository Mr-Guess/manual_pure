$.fn.outerHTML = function(){	 
    // IE, Chrome & Safari will comply with the non-standard outerHTML, all others (FF) will have a fall-back for cloning
    return (!this.length) ? this : (this[0].outerHTML || (
      function(el){
          var div = document.createElement('div');
          div.appendChild(el.cloneNode(true));
          var contents = div.innerHTML;
          div = null;
          return contents;
    })(this[0]));
};

$.postForm = function(URL, PARAMS, target) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";
    if(target)
    	temp.target = target;
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x];        
        // alert(opt.name)         
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;             
};

$.form2Json = function (formObj){
	var form = new Object();
	$(formObj).find("input[name]").each(function(){
		form[$(this).attr("name")]=$(this).val();
	});
	return form;
};

String.prototype.parseTextarea = function() {
	var text = this.replace(new RegExp("\n","gm"),"\\n");
    return text;
};

window.console = window.console || {log:function(){}};

$(function(){
	
	$(".selectConditionDiv").each(function(){
		var tab = $('<table style="width:100%;" border="0" cellpadding="0" cellspacing="0" class="selectConditionTab">');
		$(tab).append($(' <tr>'+
			    '<td width="12" height="12" class="selectConditionLeftTop"><div style="width:12px;height:1px;overflow: hidden;"></div></td>'+
			    '<td width="100%" class="selectConditionTopTd"><div style="width:1px;height:1px;overflow: hidden;"></div></td>'+
			    '<td width="12" height="12" class="selectConditionRightTopTd"><div style="width:12px;height:1px;overflow: hidden;"></div></td>'+
			  '</tr>'));
		$(tab).append('<tr>'+
			    '<td class="selectConditionLeftTd">&nbsp;</td>'+
			    '<td class="selectConditionTab">'+$(this).html()+'</td>'+
			    '<td class="selectConditionRightTd">&nbsp;</td></tr>');
		$(tab).append('<tr>'+
			    '<td height="12" class="selectConditionLeftDownTd"><div style="width:12px;height:1px;overflow: hidden;"></div></td>'+
			    '<td class="selectConditionDownTd"><div style="width:12px;height:1px;overflow: hidden;"></div></td>'+
			    '<td class="selectConditionRightDownTd"><div style="width:12px;height:1px;overflow: hidden;"></div></td>'+
			  '</tr>');
		$(this).html("");
		$(this).append($(tab));
	});
});
var grid;
window.onresize= function(){
	if(grid!=undefined&&grid!=null){
		grid.datagrid('resize');
	}
};