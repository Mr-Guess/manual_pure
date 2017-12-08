<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ay.framework.shiro.SystemParameter"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String jquery_path = request.getContextPath();
	String jquery_basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ jquery_path + "/";
	String login_authcode_switch = SystemParameter.getLogin_authcode_switch();
	request.setAttribute("login_authcode_switch", login_authcode_switch);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>南京永坤信息科技有限公司</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="../js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="js/jquery-latest.js"></script>
<script src="../js/jquery.plugin.min.js" type="text/javascript"></script>
<script src="js/alert.js" type="text/javascript" ></script>
<script src="js/alert-api.js" type="text/javascript"></script>
<script src='js/SyntaxHighlighter/shCore.js' type="text/javascript"></script>
<script src='js/SyntaxHighlighter/makeSy.js' type="text/javascript"></script>
<link rel="stylesheet" href="css/alert.css">
<style type="text/css">
		html, body, div, span, applet, object, iframe,
		h1, h2, h3, h4, h5, h6, p, blockquote, pre,
		a, abbr, acronym, address, big, cite, code,
		del, dfn, em, img, ins, kbd, q, s, samp,
		small, strike, strong, sub, sup, tt, var,
		b, u, i, center,
		dl, dt, dd, ol, ul, li,
		fieldset, form, label, legend,
		table, caption, tbody, tfoot, thead, tr, th, td,
		article, aside, canvas, details, embed, 
		figure, figcaption, footer, header, hgroup, 
		menu, nav, output, ruby, section, summary,
		time, mark, audio, video {
			margin: 0;
			padding: 0;
			border: 0;
			font-size: 100%;
			font: inherit;
			vertical-align: baseline;
		}
		/* HTML5 display-role reset for older browsers */
		article, aside, details, figcaption, figure, 
		footer, header, hgroup, menu, nav, section {
			display: block;
		}
		body {
			line-height: 1;
		}
		ol, ul {
			list-style: none;
		}
		blockquote, q {
			quotes: none;
		}
		blockquote:before, blockquote:after,
		q:before, q:after {
			content: '';
			content: none;
		}
		table {
			border-collapse: collapse;
			border-spacing: 0;
		}
	</style>
	<style type="text/css">
		
		/* Importing Amaranth Font for menu text */
/*		@import url(http://fonts.useso.com/css?family=Amaranth);*/
		header, a, img, li{
			transition: all 1s;
			-moz-transition: all 1s; /* Firefox 4 */
			-webkit-transition: all 1s; /* Safari and Chrome */
			-o-transition: all 1s; /* Opera */
		}

		/* Basic layout */
		body{
			background-color: #ebebeb;
		}

		ul{
			list-style-type: none;
			float: right;
		}

		li{
			display: inline;
			float: left;
		}

		img.logo{
			float: left;
		}


		nav{
			width: 960px;
			margin: 0 auto;
		}

		section.stretch{
			float: left;
			height: 1500px;
			width: 100%;
		}

			section.stretch p{
				font-family: 'Amaranth', sans-serif;
				font-size: 30px;
				color: #969696;
				text-align: center;
				position: relative;
				margin-top: 250px;
			}

			section.stretch p.bottom{
				top: 100%;
			}


		header{
			background: #C7C7C7;
			border-bottom: 1px solid #aaaaaa;
			float: left;
			width: 100%;
			position: fixed;
			z-index: 10;
		}

			header a{
				color: #969696;
				text-decoration: none;
				font-family: 'Amaranth', sans-serif;
				text-transform: uppercase;
			}

			header a.active, header a:hover{
				color: #3d3d3d;
			}

			header li{
				margin-right: 30px;
			}

			/* Sizes for the bigger menu */
			header.large{
				height: 120px;
			}

			header.large img{
				width: 489px;
				height: 113px;
			}

			header.large li{
				margin-top: 45px;
	
			}
			/* Sizes for the smaller menu */
			header.small{ 
				height: 50px; 
			}

			header.small img{ 
				width: 287px; 
				height: 69px; 
				margin-top: -10px; 
			}

			header.small li{ 
				margin-top: 17px; 
			}

	</style>
</head>
<body>
<section class="stretch">
<p>使用右键滚动试一试！</p>
<p class="bottom">End of the line.</p>
</section>
</body>
</html>