<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" import="java.sererlet.http.*"%>
<%@ page import="entity.AccountBean"%>
<%@ page import="entity.Weibo"%>
<%@ page import="entity.Comment" import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/bootstrap.min.css" rel="stylesheet">
<script src="./js/jquery-3.1.0.min.js"></script>

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
		Weibo weibo = (Weibo) request.getAttribute("weibo");
		List<Comment> commentList = (List<Comment>) request.getAttribute("commentList");
		AccountBean account = (AccountBean) request.getSession().getAttribute("account");
	%>

	<div class="panel panel-default" style="margin-top: 15px">
		<div class="panel-body">
			<div id="container">
				<div class="row">
					<div class="col-md-3"></div>

					<div class="col-md-6">
						<table class="table table-striped">
							<tr>
								<td>${weibo.getUsername()}</td>
								<td style="padding: 15px;"></td>
								<td>${weibo.getCreateDate()}</td>
							</tr>
						</table>
						<p align="left">${weibo.getContent()}</p>
						<div id="content">
							<form>
								<textarea class="form-control" id="newComment" name="comment"
									style="margin: 0px 0px 20px 0px; width: 100%; height: 80px"
									></textarea>
								
							</form>
							<button class="btn btn-sm btn-info"
									style="align: center" onclick="addComment()" >评论</button>
						</div>
						
						
						
						
						<p align="left">[评论列表]</p>
						<div id="comment">
						<c:if test="${commentList.size()>0}">
							<table class="table table-bordered">
								<c:forEach items="${commentList}" var="Comment">
									<tr>
									    <td><a href="./PersonalPage?userid=${Comment.getUserId()}">${Comment.getUsername()}</a></td>
										<td>${Comment.getCommentDate()}</td>
										<td>${Comment.getContent()}</td>
										<c:if
											test="${account.getUsername().equals(Comment.getUsername())||account.getUsername().equals(Weibo.getUsername())}">
											<td><a
												href="javascript:void(0)" onclick="deleteComment(${Comment.getCommentId()})">删除</a></td>
										</c:if>

									</tr>
								</c:forEach>
							</table>
						</c:if>
							<%
		Integer curPage = (Integer) request.getAttribute("curPage");
		Integer pageCount = (Integer) request.getAttribute("pageCount");
	%>
	<a href="WeiboDetail?id=${weibo.getWeiboid()}&curPage=1">首页</a>
	<a href="WeiboDetail?id=${weibo.getWeiboid()}&curPage=<%=curPage - 1%>">上一页</a>
	<a href="WeiboDetail?id=${weibo.getWeiboid()}&curPage=<%=curPage + 1%>">下一页</a>
	<a href="WeiboDetail?id=${weibo.getWeiboid()}&curPage=<%=pageCount%>">尾页</a> 第<%=curPage%>页/共<%=pageCount%>页
						</div>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>
		</div>
	</div>
	
	 <div>
		       <span id="weiboid" style="display:none">${weibo.getWeiboid()}</span>
		       <span id="commentcount" style="display:none">${weibo.getCommentCount()}</span>
		    </div>




<script src="./js/commentlist.js" charset="UTF-8"></script>
</body>
</html>