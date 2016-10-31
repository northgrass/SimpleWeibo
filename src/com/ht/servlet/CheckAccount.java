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

import entity.AccountBean;
import entity.DBUtil;

public class CheckAccount extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		AccountBean account = new AccountBean();
		String username = req.getParameter("username");
		String pwd = req.getParameter("pwd");
		if (username == "" || pwd == "") {
			req.setAttribute("message", "用户名或密码不能为空！");
			req.getRequestDispatcher("login-page.jsp").forward(req, resp);
			return;
		}
		account.setPassword(pwd);
		account.setUsername(username);
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			String sql;
			sql = "SELECT id,email,passwd,is_login FROM weibo.user WHERE email = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String rightuser = rs.getString("email");
				String rightpass = rs.getString("passwd");
				Boolean loginCon = rs.getBoolean("is_login");
				long userid = rs.getLong("id");
				if (rightuser.equals(username) && rightpass.equals(pwd) && loginCon == false) {
					sql = "update weibo.user set is_login = true where email = ?";
					pstmt = (PreparedStatement) conn.prepareStatement(sql);
					pstmt.setString(1, username);
					pstmt.executeUpdate();
					account.setUserid(userid);
					session.setAttribute("account", account);
					resp.sendRedirect("./HotWeibo");
				} else if (loginCon == true) {
					req.setAttribute("message", "该用户已在线！");
					req.getRequestDispatcher("login-page.jsp").forward(req, resp);
				} else {
					req.setAttribute("message", "密码错误！");
					req.getRequestDispatcher("login-page.jsp").forward(req, resp);
				}

			} else {
				req.setAttribute("message", "用户不存在，请重新输入！");
				req.getRequestDispatcher("login-page.jsp").forward(req, resp);
			}
			rs.close();
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

	}
}