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
</head>

<body>

	<%
		List<Comment> commentList = (List<Comment>) request.getAttribute("commentList");
		AccountBean account = (AccountBean) request.getSession().getAttribute("account");
	%>

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
	<a href="javascript:void(0)" onclick="paginate(1)">首页</a>
	<a href="javascript:void(0)" onclick="paginate(${curPage-1})">上一页</a>
	<a href="javascript:void(0)" onclick="paginate(${curPage+1})">下一页</a>
	<a href="javascript:void(0)" onclick="paginate(${pageCount})">尾页</a> 第<%=curPage%>页/共<%=pageCount%>页


</body>
</html>