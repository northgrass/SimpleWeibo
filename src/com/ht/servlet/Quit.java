package com.ht.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.AccountBean;
import entity.DBUtil;

public class Quit extends HttpServlet {
//	static final String JDBC_DRIVER = "org.postgresql.Driver";
//	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
//	static final String USER = "postgres";
//	static final String PASS = "123456";

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String flag = req.getParameter("flag");
		if (flag.equals("quit")) {
			HttpSession session = req.getSession();
			AccountBean account = (AccountBean) session.getAttribute("account");
			String username = account.getUsername();
//			Connection conn = null;
			DBUtil dbUtil = new DBUtil();
			Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = null;
			try {
//				Class.forName("org.postgresql.Driver");
//				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				String sql;
				sql = "update weibo.user set is_login = false where email = ?";
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				pstmt.setString(1, username);
				pstmt.executeUpdate();
				dbUtil.release(conn, pstmt);
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

			session.invalidate();
			resp.sendRedirect("login-page.jsp");
		}
	}
}
