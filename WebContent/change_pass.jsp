<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
body, div, ul, li{margin:0; padding:0;font-style: normal;font:12px/22px "\5B8B\4F53",Arial, Helvetica, sans-serif} 

ol, ul ,li{list-style:none} 
img {border: 0; vertical-align:middle} 
body{color:#000000;background:#FFF; text-align:center} 
.clear{clear:both;height:1px;width:100%; overflow:hidden; margin-top:-1px} 
a{color:#000000;text-decoration:none}  
a:hover{color:#BA2636} 
 
.red ,.red a{ color:#F00} 
.lan ,.lan a{ color:#1E51A2} 
.pd5{ padding-top:5px} 
.dis{display:block} 
.undis{display:none} 
 
ul#nav{ width:100%; height:60px; background:#00A2CA;margin:0 auto} 
ul#nav li{display:inline; height:60px} 
ul#nav li a{display:inline-block; padding:0 20px; height:60px; line-height:60px; 
color:#FFF; font-family:"\5FAE\8F6F\96C5\9ED1"; font-size:16px} 
ul#nav li a:hover{background:#0095BB} 

</style>
</head>
<body>
	<ul id="nav">
		<li><a href="./HotWeibo">首页</a></li>
		<li><a href="./WeiboList">关注微博</a></li>
		<li><a href="search-page.jsp">用户搜索</a></li>
		<li><a href="changepass-page.jsp">修改密码</a></li>
		<li><a href="Quit?flag=quit">退出登录</a></li>
	</ul>

	<form action="changepass">
		旧密码:<input type="password" name="oldpwd"><br> passwd:<input
			type="password" name="newpwd1"><br> passwd again:<input
			type="password" name="newpwd2"><br> <input type="submit"
			value="修改密码">

		<%
			String mess = (String) request.getAttribute("message");
			if (mess != null) {
				out.println(mess);
			}
		%>
	
</body>
</html>