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

public class ChangePwd extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		AccountBean account = (AccountBean) session.getAttribute("account");
		String username = account.getUsername();
		String oldpwd = req.getParameter("oldpwd");
		String newpwd1 = req.getParameter("newpwd1");
		String newpwd2 = req.getParameter("newpwd2");
		if (oldpwd == "" || newpwd1 == "" || newpwd2 == "") {
			req.setAttribute("message", "密码输入不能为空！");
			req.getRequestDispatcher("changepass-page.jsp").forward(req, resp);
			return;
		}
		if (!newpwd1.equals(newpwd2)) {
			req.setAttribute("message", "两次输入密码不一致！");
			req.getRequestDispatcher("changepass-page.jsp").forward(req, resp);
			return;
		}
		PreparedStatement pstmt = null;
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			String sql;
			sql = "SELECT email,passwd FROM weibo.user WHERE email = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String rightpwd = rs.getString("passwd");
				if (rightpwd.equals(oldpwd)) {
					sql = "update weibo.user set passwd = ? where email = ?";
					pstmt = (PreparedStatement) conn.prepareStatement(sql);
					pstmt.setString(1, newpwd1);
					pstmt.setString(2, username);
					pstmt.executeUpdate();
					req.setAttribute("message", "密码修改成功！");
					req.getRequestDispatcher("changepass-page.jsp").forward(req, resp);
					return;
				} else {
					req.setAttribute("message", "旧密码输入错误！");
					req.getRequestDispatcher("changepass-page.jsp").forward(req, resp);
					return;
				}
			}
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
		// }
	}
}
