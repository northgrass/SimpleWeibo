package com.ht.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;

import entity.AccountBean;
import entity.DBUtil;
import entity.User;

public class Search extends HttpServlet {
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
		req.setCharacterEncoding("UTF-8");

		String searchname = req.getParameter("searchname");

		AccountBean account = (AccountBean) req.getSession().getAttribute("account");
		long userid = account.getUserid();
		String flag = req.getParameter("curPage");
		if (flag == null) {
			flag = "1";
		}
		int curPage = Integer.parseInt(flag);
//		Connection conn = null;
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql;
			sql = "select count(*) as num from weibo.user where email like  '%" + searchname + "%'";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int size = 0;
			if (rs.next()) {
				size = rs.getInt("num");
			}
			pageCount = (size % PAGESIZE == 0) ? (size / PAGESIZE) : (size / PAGESIZE + 1);
			int start = PAGESIZE * (curPage - 1); // 获得当前的查询起点
			int offset = 5;
			if (size < start + PAGESIZE) { // 获得offset查询量的值
				offset = size - start;
			}
			sql = "select * from weibo.user where email like '%" + searchname + "%' limit ? offset ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, offset);
			pstmt.setLong(2, start);
			// System.out.println(offset);
			System.out.println(size);
			rs = pstmt.executeQuery();
			ArrayList<User> searchList = new ArrayList<User>();
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getLong("id"));
				user.setUsername(rs.getString("email"));
				searchList.add(user);
			}
			req.setAttribute("searchList", searchList);
			req.setAttribute("curPage", curPage);
			req.setAttribute("pageCount", pageCount);
			req.setAttribute("searchname", searchname);
			req.getRequestDispatcher("searchList-page.jsp").forward(req, resp);
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
