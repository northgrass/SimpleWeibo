<%@ page import="entity.AccountBean" import="java.util.*"
	import="entity.Weibo" import="entity.User" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
body, ul, li {
	margin: 0;
	padding: 0;
	font-style: normal;
	font: 12px/22px "\5B8B\4F53", Arial, Helvetica, sans-serif
}

ol, ul, li {
	list-style: none
}

body {
	text-align: center
}

a {
	color: #000000;
	text-decoration: none
}

a:hover {
	color: #BA2636
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
	width: 100%;
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

	<div id="container">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<%
					List<Weibo> weiboList = (List<Weibo>) request.getAttribute("weiboList");
				%>

				<c:if test="${weiboList.size()>0}">
					<c:forEach items="${weiboList}" var="Weibo">

						<c:if test="${Weibo.getFlag()==1}">
							<div class="panel panel-default" style="margin-top: 15px">
								<div class="panel-body">
									<table class="table table-striped">
										<tr>
											<td><a href="./PersonalPage?userid=${Weibo.getUserid()}">${Weibo.getUsername()}</a></td>
											<td style="padding: 15px;"></td>
											<td>${Weibo.getCreateDate()}</td>

										</tr>
									</table>
									<p align="left">weibo is deleted!</p>
								</div>
							</div>
						</c:if>

						<c:if test="${Weibo.getFlag()==0}">
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
						</c:if>
					</c:forEach>
				</c:if>
			</div>
			<div class="col-md-4"></div>
		</div>



		<%
			Integer curPage = (Integer) request.getAttribute("curPage");
			Integer pageCount = (Integer) request.getAttribute("pageCount");
		%>
		<a href="WeiboList?curPage=1">首页</a> <a
			href="WeiboList?curPage=<%=curPage - 1%>">上一页</a> <a
			href="WeiboList?curPage=<%=curPage + 1%>">下一页</a> <a
			href="WeiboList?curPage=<%=pageCount%>">尾页</a> 第<%=curPage%>页/共<%=pageCount%>页


	</div>




</body>
</html>