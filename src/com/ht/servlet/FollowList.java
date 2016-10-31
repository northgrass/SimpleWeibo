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

public class FollowList extends HttpServlet {
	public static final int PAGESIZE = 5;
	int pageCount;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AccountBean account = (AccountBean) req.getSession().getAttribute("account");
		long userid = account.getUserid();
		String flag = req.getParameter("curPage");
		if (flag == null) {
			flag = "1";
		}
		int curPage = Integer.parseInt(flag);
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			String sql;
			sql = "select follow_count from weibo.user where id=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, userid);
			// System.out.println(userid);
			ResultSet rs = pstmt.executeQuery();
			int size = 0;
			if (rs.next()) {
				size = rs.getInt("follow_count");
			}
			pageCount = (size % PAGESIZE == 0) ? (size / PAGESIZE) : (size / PAGESIZE + 1);
			int start = PAGESIZE * (curPage - 1); // 获得当前的查询起点
			int offset = 5;
			if (size < start + PAGESIZE) { // 获得offset查询量的值
				offset = size - start;
			}
			sql = "select weibo.relationship.follow_id,weibo.user.email from weibo.relationship join weibo.user on weibo.relationship.follow_id=weibo.user.id where weibo.relationship.user_id=? limit ? offset ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, userid);
			pstmt.setLong(2, offset);
			pstmt.setLong(3, start);
			// System.out.println(offset);
			System.out.println(size);
			rs = pstmt.executeQuery();
			ArrayList<User> followList = new ArrayList<User>();
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getLong("follow_id"));
				user.setUsername(rs.getString("email"));
				System.out.print("11111111111111");
				System.out.print(rs.getString("email"));
				followList.add(user);
			}
			req.setAttribute("followList", followList);
			req.setAttribute("curPage", curPage);
			req.setAttribute("pageCount", pageCount);
			
			dbUtil.release(conn, pstmt);
			req.getRequestDispatcher("followlist-page.jsp").forward(req, resp);

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
