package com.ht.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;

import entity.AccountBean;
import entity.Comment;
import entity.DBUtil;

public class CommentList extends HttpServlet {
	public static final int PAGESIZE = 5;
	int pageCount;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AccountBean account = (AccountBean) req.getSession().getAttribute("account");
		long myid = account.getUserid();
		long weibo_id = 0;
		int curPage = 0;
		int comment_count = 0;
		if(req.getParameter("page")==null){
			curPage = (Integer) req.getAttribute("curPage");
		}
		else{
			curPage = Integer.parseInt(req.getParameter("page"));
		}
		if(req.getParameter("commentcount")==null){
			comment_count = (Integer) req.getAttribute("comment_count");
		}
		else{
			comment_count = Integer.parseInt(req.getParameter("commentcount"));
		}
		if(req.getParameter("weiboid")==null){
			weibo_id = (long)req.getAttribute("weibo_id");
		}
		else{
			weibo_id = Long.parseLong(req.getParameter("weiboid"));
		}
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			int size = comment_count;

			pageCount = (size % PAGESIZE == 0) ? (size / PAGESIZE) : (size / PAGESIZE + 1);
			int start = PAGESIZE * (curPage - 1); // 获得当前的查询起点
			int offset = 5;
			if (size < start + PAGESIZE) { // 获得offset查询量的值
				offset = size - start;
			}
            String sql;
			sql = "select weibo.comment.user_id,weibo.comment.id,weibo.comment.comment_content,weibo.comment.comment_date,weibo.user.email from weibo.comment join weibo.user on weibo.comment.user_id = weibo.user.id where weibo.comment.weibo_id = ? and weibo.comment.is_delete=false order by weibo.comment.comment_date desc limit ? offset ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, weibo_id);
			pstmt.setLong(2, offset);
			pstmt.setLong(3, start);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Comment> commentList = new ArrayList<Comment>();

			while (rs.next()) {
				Comment comment = new Comment();
				comment.setContent(rs.getString("comment_content"));
				comment.setCommentDate(rs.getTimestamp("comment_date").toString());
				comment.setUsername(rs.getString("email"));
				comment.setUserId(rs.getLong("user_id"));
				comment.setCommentId(rs.getLong("id"));
				commentList.add(comment);
			}
			req.setAttribute("commentList", commentList);
			req.setAttribute("curPage", curPage);
			req.setAttribute("pageCount", pageCount);
			req.getRequestDispatcher("commentlist.jsp").forward(req, resp);
            dbUtil.release(conn, pstmt);
			return;
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}
}
