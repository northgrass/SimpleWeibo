<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" import="java.sererlet.http.*"
	import="java.util.*" import="entity.User"%>
<%@ page import="entity.AccountBean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
body, div, ul, li {
	margin: 0;
	padding: 0;
	font-style: normal;
	font: 12px/22px "\5B8B\4F53", Arial, Helvetica, sans-serif
}

ol, ul, li {
	list-style: none
}

img {
	border: 0;
	vertical-align: middle
}

body {
	color: #000000;
	background: #FFF;
	text-align: center
}

.clear {
	clear: both;
	height: 1px;
	width: 100%;
	overflow: hidden;
	margin-top: -1px
}

a {
	color: #000000;
	text-decoration: none
}

a:hover {
	color: #BA2636
}

.red, .red a {
	color: #F00
}

.lan, .lan a {
	color: #1E51A2
}

.pd5 {
	padding-top: 5px
}

.dis {
	display: block
}

.undis {
	display: none
}

ul#nav {
	width: 100%;
	height: 60px;
	background: #00A2CA;
	margin: 0 auto
}

ul#nav li {
	display: inline;
	height: 60px
}

ul#nav li a {
	display: inline-block;
	padding: 0 20px;
	height: 60px;
	line-height: 60px;
	color: #FFF;
	font-family: "\5FAE\8F6F\96C5\9ED1";
	font-size: 16px
}

ul#nav li a:hover {
	background: #0095BB
}
</style>
<link href="./css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
	<ul id="nav">
		<li><a href="./HotWeibo">首页</a></li>
		<li><a href="./WeiboList">关注微博</a></li>
		<li><a href="search-page.jsp">用户搜索</a></li>
		<li><a href="changepass-page.jsp">修改密码</a></li>
		<li><a href="Quit?flag=quit">退出登录</a></li>
	</ul>

	<%
		List<User> fansList = (List<User>) request.getAttribute("fansList");
	%>
	<c:if test="${fansList.size()>0}">
			<div class="list-group" style="width:200px;margin-left:auto;margin-right:auto">
		<c:forEach items="${fansList}" var="User">
				<a href="./PersonalPage?userid=${User.getUserid()}"
					class="list-group-item">${User.getUsername()}</a>
		</c:forEach>
			</div>
	</c:if>


	<% Integer curPage = (Integer)request.getAttribute("curPage");
           Integer pageCount = (Integer)request.getAttribute("pageCount");
        %>
	<a href="FansList?curPage=1">首页</a>
	<a href="FansList?curPage=<%=curPage - 1%>">上一页</a>
	<a href="FansList?curPage=<%=curPage + 1%>">下一页</a>
	<a href="FansList?curPage=<%=pageCount%>">尾页</a> 第<%=curPage%>页/共<%=pageCount%>页


</body>
</html>