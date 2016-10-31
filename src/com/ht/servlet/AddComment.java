package com.ht.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class AddComment extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("?????????????????");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		AccountBean account = (AccountBean) session.getAttribute("account");
		Date date = new Date();
		Timestamp commentDate = new Timestamp(date.getTime());
		long myid = account.getUserid();
		String id = req.getParameter("weiboid");
		long weibo_id = Long.parseLong(id);
		String content = req.getParameter("comment");
		PreparedStatement pstmt = null;
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			String sql;
			sql = "insert into weibo.comment (comment_content,user_id,comment_date,weibo_id) values (?,?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setLong(2, myid);
			pstmt.setTimestamp(3, commentDate);
			pstmt.setLong(4, weibo_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "update weibo.weibo set comment_count = comment_count + 1 where id = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, weibo_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "select comment_count from weibo.weibo where id = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, weibo_id);
			ResultSet rs = pstmt.executeQuery();
			int comment_count = 0;
			if (rs.next()) {
				comment_count = rs.getInt("comment_count");
			}
			rs.close();
			dbUtil.release(conn, pstmt);
			req.setAttribute("comment_count", comment_count);
			req.setAttribute("curPage", 1);
			req.setAttribute("weibo_id", weibo_id);
			 req.getRequestDispatcher("./CommentList").forward(req, resp);
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
				se2.printStackTrace();
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
