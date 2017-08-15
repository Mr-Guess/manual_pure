<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
.errorpage {
	height: 337px;
	width: 558px;
	vertical-align: middle;
	text-align: center;
	margin-top: 100px;
	background-image: url('../images/bg_errorpage.jpg');
	background-repeat: no-repeat;
	margin-left: auto;
	margin-right: auto;
}

.errorlogin {
	height: 337px;
	width: 558px;
	vertical-align: middle;
	text-align: center;
	margin-top: 100px;
	background-image: url('../images/bg_errorlogin.jpg');
	background-repeat: no-repeat;
	margin-left: auto;
	margin-right: auto;
}

.errorcon {
	width: 100%;
	position: relative;
	top: 20px;
	text-align: left;
	vertical-align: middle;
	color: #FFFFFF;
}

.errorcon span {
	text-align: center;
	width: 100%;
}
</style>
<title>异常</title>
</head>

<body>
	<div class="errorpage">
		<div class="errorcon">
			<span><a href=""><img src="../resources/images/error.jpg"
					border="0" /></a></span>
		</div>
	</div>
</body>
</html>