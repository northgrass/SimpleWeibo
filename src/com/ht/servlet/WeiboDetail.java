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
import entity.Weibo;

public class WeiboDetail extends HttpServlet {
//	static final String JDBC_DRIVER = "org.postgresql.Driver";
//	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
//	static final String USER = "postgres";
//	static final String PASS = "123456";
	public static final int PAGESIZE = 5;
	int pageCount;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AccountBean account = (AccountBean) req.getSession().getAttribute("account");
		long myid = account.getUserid();
		String id = req.getParameter("id");
		long weibo_id = Long.parseLong(id);
		String page = req.getParameter("curPage");
		if (page == null) {
			page = "1";
		}
		int curPage = Integer.parseInt(page);
//		Connection conn = null;
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		PreparedStatement pstmt = null;
		String weibo_content = null;
		int comment_count = 0;
		int repost_count = 0;
		Timestamp create_date = null;
		String username = null;
		try {
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql;
			sql = "select weibo.weibo.repost_content,weibo.weibo.id,weibo.weibo.content,weibo.weibo.comment_count,weibo.weibo.repost_count,weibo.weibo.create_date,weibo.user.email,weibo.weibo.reference_id from weibo.weibo join weibo.user on weibo.weibo.user_id=weibo.user.id where weibo.weibo.id = ? and weibo.weibo.is_deleted = false";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, weibo_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getLong("reference_id") > 0) {
					long reference_id = rs.getLong("reference_id");
					sql = "select weibo.weibo.content from weibo.weibo where weibo.weibo.is_deleted = false and weibo.weibo.id =?";
					pstmt = (PreparedStatement) conn.prepareStatement(sql);
					pstmt.setLong(1, reference_id);
					ResultSet rs1 = pstmt.executeQuery();
					if (rs1.next()) {
						weibo_content = rs.getString("repost_content") + rs1.getString("content");
					}
				} else {
					weibo_content = rs.getString("content");
				}
				comment_count = rs.getInt("comment_count");
				repost_count = rs.getInt("repost_count");
				create_date = rs.getTimestamp("create_date");
				username = rs.getString("email");
			}

//			Weibo weibo = new Weibo();
//			weibo.setCommentCount(rs.getLong("comment_count"));
//			weibo.setContent(rs.getString("content"));
//			weibo.setCreateDate(rs.getString("create_date"));
//			weibo.setRepostCount(rs.getLong("repost_count"));
//			weibo.setUsername(rs.getString("email"));
//			weibo.setWeiboid(rs.getLong("id"));
			
			Weibo weibo = new Weibo();
			weibo.setCommentCount(comment_count);
			weibo.setContent(weibo_content);
			weibo.setCreateDate(create_date.toString());
			weibo.setRepostCount(repost_count);
			weibo.setUsername(username);
			weibo.setWeiboid(weibo_id);

			int size = comment_count;

			pageCount = (size % PAGESIZE == 0) ? (size / PAGESIZE) : (size / PAGESIZE + 1);
			int start = PAGESIZE * (curPage - 1); // 获得当前的查询起点
			int offset = 5;
			if (size < start + PAGESIZE) { // 获得offset查询量的值
				offset = size - start;
			}

			sql = "select weibo.comment.user_id,weibo.comment.id,weibo.comment.comment_content,weibo.comment.comment_date,weibo.user.email from weibo.comment join weibo.user on weibo.comment.user_id = weibo.user.id where weibo.comment.weibo_id = ? and weibo.comment.is_delete=false order by weibo.comment.comment_date desc limit ? offset ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, weibo_id);
			pstmt.setLong(2, offset);
			pstmt.setLong(3, start);
			rs = pstmt.executeQuery();
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
			req.setAttribute("weibo", weibo);
			req.setAttribute("curPage", curPage);
			req.setAttribute("pageCount", pageCount);
			req.getRequestDispatcher("weibodetail-page.jsp").forward(req, resp);
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
