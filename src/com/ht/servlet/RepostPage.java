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
import entity.DBUtil;
import entity.Weibo;

public class RepostPage extends HttpServlet {
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
//		Connection conn = null;
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();	
		
		PreparedStatement pstmt = null;
		String weibo_content;
		int comment_count = 0;
		int repost_count;
		Timestamp create_date;
		String username;
		try {
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql;
			sql = "select weibo.weibo.id,weibo.weibo.content,weibo.weibo.comment_count,weibo.weibo.repost_count,weibo.weibo.create_date,weibo.user.email from weibo.weibo join weibo.user on weibo.weibo.user_id=weibo.user.id where weibo.weibo.id = ? and weibo.weibo.is_deleted = false";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, weibo_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				weibo_content = rs.getString("content");
				comment_count = rs.getInt("comment_count");
				repost_count = rs.getInt("repost_count");
				create_date = rs.getTimestamp("create_date");
				username = rs.getString("email");
			}

			Weibo weibo = new Weibo();
			weibo.setCommentCount(rs.getLong("comment_count"));
			weibo.setContent(rs.getString("content"));
			weibo.setCreateDate(rs.getString("create_date"));
			weibo.setRepostCount(rs.getLong("repost_count"));
			weibo.setUsername(rs.getString("email"));
			weibo.setWeiboid(rs.getLong("id"));

			req.setAttribute("weibo", weibo);
			req.getRequestDispatcher("repostpage-page.jsp").forward(req, resp);
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
