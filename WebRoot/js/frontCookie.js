String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
var clearValue = function(value) {
	if(value == null || typeof value == undefined || value == 'null' || value == 'undefined')
		return '';
	return value;
};

Cookie.prototype.initMember = function(){
	var member = this.get("member");
	var username = this.get("username");
	var username1 = this.get("username1");
	var username2 = this.get("username2");
	var username3 = this.get("username3");
	username = clearValue(username);
	username1 = clearValue(username1);
	username2 = clearValue(username2);
	username3 = clearValue(username3);
	var zfUser = this.get("zfUser");
	if(zfUser == "false"){
		form1.zfUser.click();
	}else{
		form1.zfUser.click();
		form1.zfUser.click();
	}
	form1.username.value = username;
	form1.username1.value = username1;
	form1.username2.value = username2;
	form1.username3.value = username3;
	if(member == "true"){
		var password = this.get("password");
		password = password==null?'':password;
		form1.password.value = password;
		form1.member.checked = true;
	}
};

Cookie.prototype.saveOrRemoveMember = function(){	
	var member = form1.member.checked;
	username = form1.username.value;
	this.add("username",username,48);
	this.add("username1",form1.username1.value,48);
	this.add("username2",form1.username2.value,48);
	this.add("username3",form1.username3.value,48);
	if(form1.zfUser.checked)
		this.add("zfUser","true",48);
	else
		this.add("zfUser","false",48);
	if (member) {
		password = form1.password.value;
		this.add("password",password,48);
		this.add("member","true",48);
	} else {
		this.remove("password");
		this.remove("member");
	}
};
