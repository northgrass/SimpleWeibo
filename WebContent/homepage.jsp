<%@ page import="entity.AccountBean" import="java.util.*"
	import="entity.Weibo" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

ul, li {
	list-style: none
}

body {
	color: #000000;
	background: #FFF;
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
	width: 100%
}

div#menu {
	background-color: #ffff99;
	height: 200px;
	width: 10%;
	float: left;
	display: table;
}

div#content {
	background-color: #EEEEEE;
	height: 200px;
	width: 90%;
	float: left;
}

div#menu1 {
	background-color: #EEEEEE;
	width: 10%;
	float: left;
}

div#content1 {
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
			<div style="display: table-cell; vertical-align: middle;">
				<h2>

					<%
						AccountBean account = (AccountBean) request.getSession().getAttribute("account");
						String user_name = account.getUsername();
					%>
					<%=user_name%></h2>
			</div>
		</div>

		<div id="content">
			<form action="postweibo" method="post" onSubmit="return checkForm()">
				<p
					style="margin: 20px 30px 10px 40px; font-size: 20px; color: #6495ED">有什么新鲜事想告诉大家?</p>
				<textarea class="form-control" id="newWeibo" name="weibo_content"
					style="margin: 0px 0px 20px 140px; width: 70%; height: 80px"
					onfocus="hide()"></textarea>
				<button class="btn btn-sm btn-info " type="submit"
					style="align: center">发布</button>
					
					
			<div>
						<h4 style="color: red; font-family: 黑体">${requestScope.message}</h4>
					</div>
					<div class="error-item1" style="display:none">
		        <p  style="color:red; font-size:18px"></p>
		    </div>

			</form>

		</div>

		<div id="menu1">
			<ul style="margin: 30px 0px 0px 0px">
				<li><a href="./MyWeibo">我的微博</a></li>
				<li><a href="./FansList">我的粉丝</a></li>
				<li><a href="./FollowList">我的关注</a></li>
				<li>我的资料</li>
				<li>用户推荐</li>
			</ul>
		</div>

		<div id="content1">

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
										<c:if test="${Weibo.getUserid() != account.getUserid()}">
											<div class="btn-group" role="group"
												style="float: left; width: 100%">
												<button type="button" class="btn btn-default"
													style="width: 50%"
													onclick="location.href='./RepostPage?id=${Weibo.getWeiboid()}'">转发:${Weibo.getRepostCount()}</button>
												<button type="button" class="btn btn-default"
													style="width: 50%"
													onclick="location.href='./WeiboDetail?id=${Weibo.getWeiboid()}&curPage=1'">评论:${Weibo.getCommentCount()}</button>
											</div>
										</c:if>

										<c:if test="${Weibo.getUserid() == account.getUserid()}">
											<div class="btn-group" role="group"
												style="float: left; width: 100%">
												<button type="button" class="btn btn-default"
													style="width: 33%"
													onclick="location.href='./RepostPage?id=${Weibo.getWeiboid()}'">转发:${Weibo.getRepostCount()}</button>
												<button type="button" class="btn btn-default"
													style="width: 33%"
													onclick="location.href='./WeiboDetail?id=${Weibo.getWeiboid()}&curPage=1'">评论:${Weibo.getCommentCount()}</button>
												<button type="button" class="btn btn-default"
													style="width: 34%"
													onclick="location.href='DeleteWeibo?id=${Weibo.getWeiboid()}'">删除</button>
											</div>
										</c:if>
									</div>
								</div>

							</div>
						</div>
					</c:forEach>
				</c:if>

			</div>
		</div>

	</div>
	
	<script src="./js/jquery-3.1.0.min.js"></script>
	
	<script type="text/javascript">
	function checkForm(){
		var newWeibo = $('#newWeibo').val();
		if (newWeibo != "") {
			return true;
		} else {
			$('.error-item1 > p').html("微博内容不能为空！");
			$('.error-item1').show();
			return false;
		}
	}
</script>

</body>
</html>