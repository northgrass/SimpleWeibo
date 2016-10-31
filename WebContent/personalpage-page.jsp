<%@ page import="entity.AccountBean" import="java.util.*"
	import="entity.Weibo" import="entity.User"
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/bootstrap.min.css" rel="stylesheet">
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

div#container {
	width: 100%
}

div#header {
	background-color: #99bbbb;
}

div#menu {
	background-color: #ffff99;
	height: 200px;
	width: 10%;
	float: left;
}

div#content {
	background-color: #EEEEEE;
	width: 90%;
	float: left;
}

div#menu1 {
	background-color: #EEEEEE;
	height: 200px;
	width: 10%;
	float: left;
}

div#content1 {
	background-color: #ffff99;
	height: 200px;
	width: 90%;
	float: left;
}

h1 {
	margin-bottom: 0;
}

h2 {
	margin-bottom: 0;
	font-size: 18px;
}

ul {
	margin: 0;
}

li {
	list-style: none;
}
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

	<div id="container">

		<div id="menu">
			<h2>
				<%
					User user = (User) request.getAttribute("user");
					long userid = user.getUserid();
					String user_name = user.getUsername();
					Integer relation = (Integer) request.getAttribute("relation");
				%>
				<%=user_name%></h2>
			<c:if test="${relation==0}">
				<a
					href="./ChangeRelationship?userid=<%=userid%>&relation=<%=relation%>">关注</a>
			</c:if>
			<c:if test="${relation==1}">
				<a
					href="./ChangeRelationship?userid=<%=userid%>&relation=<%=relation%>">取关</a>
			</c:if>
		</div>

		<div id="content">
			<div style="padding: 15px; margin: 0px 300px 0px 300px">

				<%
					List<Weibo> weiboList = (List<Weibo>) request.getAttribute("weiboList");
				%>

				<c:if test="${weiboList.size()>0}">
					<c:forEach items="${weiboList}" var="Weibo">
						<div class="panel panel-default" style="margin-top: 15px">
							<div class="panel-body">
								<table class="table table-striped">
									<tr>
										<td><a href="./PersonalPage?userid=${Weibo.getUserid()}">${Weibo.getUsername()}</a></td>
										<td style="padding: 15px;"></td>
										<td>${Weibo.getCreateDate()}</td>
									</tr>
								</table>
								<p align="left">${Weibo.getContent()}</p>

								<div class="container-fluid">
									<div class="row">
										<div class="btn-group" role="group"
											style="float: left; width: 100%">
											<button type="button" class="btn btn-default"
												style="width: 50%"
												onclick="location.href='./RepostPage?id=${Weibo.getWeiboid()}'">转发:${Weibo.getRepostCount()}</button>
											<button type="button" class="btn btn-default"
												style="width: 50%"
												onclick="location.href='./WeiboDetail?id=${Weibo.getWeiboid()}&curPage=1'">评论:${Weibo.getCommentCount()}</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:if>

			</div>

			<%
				Integer curPage = (Integer) request.getAttribute("curPage");
				Integer pageCount = (Integer) request.getAttribute("pageCount");
			%>
			<a href="PersonalPage?userid=<%=userid%>&curPage=1">首页</a> <a
				href="PersonalPage?userid=<%=userid%>&curPage=<%=curPage - 1%>">上一页</a>
			<a href="PersonalPage?userid=<%=userid%>&curPage=<%=curPage + 1%>">下一页</a>
			<a href="PersonalPage?userid=<%=userid%>&curPage=<%=pageCount%>">尾页</a>
			第<%=curPage%>页/共<%=pageCount%>页


		</div>



	</div>

</body>
</html>