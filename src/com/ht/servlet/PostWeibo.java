package com.ht.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.AccountBean;
import entity.DBUtil;

public class PostWeibo extends HttpServlet {
//	static final String JDBC_DRIVER = "org.postgresql.Driver";
//	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
//	static final String USER = "postgres";
//	static final String PASS = "123456";

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		AccountBean account = (AccountBean) session.getAttribute("account");
		Date date = new Date();
		Timestamp messageDate = new Timestamp(date.getTime());
		long userid = account.getUserid();
		String content = req.getParameter("weibo_content");
//		Connection conn = null;
		
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		
		PreparedStatement pstmt = null;
		try {
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql;
			sql = "insert into weibo.weibo (content,user_id,create_date,repost_content) values (?,?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setLong(2, userid);
			pstmt.setTimestamp(3, messageDate);
			pstmt.setString(4, "");

			pstmt.executeUpdate();

			sql = "update weibo.user set weibo_count = weibo_count + 1 where id = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, userid);
			pstmt.executeUpdate();
//			resp.sendRedirect("./HotWeibo");
			//
			 req.setAttribute("message", "微博发布成功！");
			 req.getRequestDispatcher("/HotWeibo").forward(req, resp);
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
