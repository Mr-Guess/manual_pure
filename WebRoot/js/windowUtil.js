$.openWindow = function(url) {
	return window.open(url,'_blank','width='+ (screen.availWidth - 10) + ',height=' + (screen.availHeight-50) + ',Left=0,Top=0,status=yes,menubar=no, location=no,scrollbars=yes,resizable=yes');
};
