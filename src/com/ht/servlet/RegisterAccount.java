package com.ht.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.DBUtil;

public class RegisterAccount extends HttpServlet {
//	static final String JDBC_DRIVER = "org.postgresql.Driver";
//	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
//	static final String USER = "postgres";
//	static final String PASS = "123456";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@SuppressWarnings("resource")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		// HttpSession session = req.getSession();
		// AccountBean account = new AccountBean();
		String username = req.getParameter("username");
		String pwd = req.getParameter("pwd");
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();	
//		Connection conn = null;
		PreparedStatement pstmt = null;
		// Statement stmt = null;
		try {
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// stmt = conn.createStatement();
			String sql;

			sql = "SELECT id FROM weibo.user WHERE email = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (!(rs.next())) {
				sql = "insert into weibo.user (email,passwd) values(?,?)";
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				pstmt.setString(1, username);
				pstmt.setString(2, pwd);
				pstmt.executeUpdate();
				String login_suc = "login-page.jsp";
				resp.sendRedirect(login_suc);
			} else {
				req.setAttribute("message", "该用户名已被占用！");
				req.getRequestDispatcher("register-page.jsp").forward(req, resp);
			}
			rs.close();
//			pstmt.close();
//			conn.close();
			dbUtil.release(conn, pstmt);
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
	}

}