<%@ page language="java" contentType="text/html"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">
<title>Signin Template for Bootstrap</title>
<link href="./css/bootstrap.min.css"
	rel="stylesheet">
<link href="signin.css" rel="stylesheet">
<script src="../../assets/js/ie-emulation-modes-warning.js"></script>
<style type="text/css">
body, div, ul, li{margin:0; padding:0;font-style: normal;font:12px/22px "\5B8B\4F53",Arial, Helvetica, sans-serif} 

ul ,li{list-style:none} 
body{color:#000000;background:#FFF; text-align:center} 
a{color:#000000;text-decoration:none}  
a:hover{color:#BA2636} 
 
 
ul#nav{ width:100%; height:60px; background:#00A2CA;margin:0 auto} 
ul#nav li{display:inline; height:60px} 
ul#nav li a{display:inline-block; padding:0 20px; height:60px; line-height:60px; 
color:#FFF; font-family:"\5FAE\8F6F\96C5\9ED1"; font-size:16px} 
ul#nav li a:hover{background:#0095BB} 


div#container{width:100%}
div#menu {background-color:#ffff99;height:200px;width:10%;float:left;display: table;}
div#content {background-color:#EEEEEE;height:200px;width:90%;float:left;}
div#menu1 {background-color:#EEEEEE;height:400px;width:10%;float:left;}
div#content1 {background-color:#ffff99;height:400px;width:90%;float:left;}
h1 {margin-bottom:0;}
h2 {margin-bottom:0;font-size:18px;}
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


<div style="margin: 15px 0px 0px 0px">
<form class="form-inline" action="./SearchList" method ="post">
  <div class="form-group">
    <label class="sr-only" for="searchUser">searchUser</label>
    <input type="text" class="form-control" id="searchUser" name="searchname" placeholder="searchUser">
  </div>
  <button type="submit" class="btn btn-default">Search</button>
</form>
</div>
</body>
</html>
