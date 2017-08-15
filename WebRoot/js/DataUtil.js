var DevData = function(type, code, url){
	this.type=type;
	this.code=code;
	this.url=url;
};

DevData.prototype.getDataName = function(){
	var data=null;
	$.ajax({
		url:this.url,
		data:{'data.typeId':this.type,'data.dataCode',this.code},
		type:'post',
		async: false,
		success:function(d){
			alert(d);
			data=d;
			return data;
		}
	});
	return d;
};
