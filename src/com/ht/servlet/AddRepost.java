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

public class AddRepost extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		AccountBean account = (AccountBean) session.getAttribute("account");
		Date date = new Date();
		Timestamp commentDate = new Timestamp(date.getTime());
		long myid = account.getUserid();
		String id = req.getParameter("weiboid");
		long weibo_id = Long.parseLong(id);
		String content = req.getParameter("repost");// 提交框里的消息
		
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();		
		PreparedStatement pstmt = null;
		try {
			String sql;
			sql = "select weibo.weibo.repost_content,weibo.weibo.reference_id,weibo.user.email from weibo.weibo join weibo.user on weibo.weibo.user_id=weibo.user.id where weibo.weibo.id=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, weibo_id);
			ResultSet rs = pstmt.executeQuery();
			String username = "";
			String repost_content = "";
			long reference_id = 0;

			while (rs.next()) {
				username = rs.getString("email");
				if (rs.getLong("reference_id") > 0) {
					reference_id = rs.getLong("reference_id");
				} else {
					reference_id = weibo_id;
				}
				repost_content = rs.getString("repost_content");
			}
			if (repost_content == null) {
				repost_content = "";
			}
			String contentToSave = content + "@" + username + ":" + repost_content;
			sql = "insert into weibo.weibo (content,user_id,create_date,reference_id,repost_content) values (?,?,?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, "");
			pstmt.setLong(2, myid);
			pstmt.setTimestamp(3, commentDate);
			pstmt.setLong(4, reference_id);
			pstmt.setString(5, contentToSave);
			pstmt.executeUpdate();

			if (reference_id == 0) {
				sql = "update weibo.weibo set repost_count = repost_count + 1 where id = ?";
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				pstmt.setLong(1, reference_id);
				pstmt.executeUpdate();
			}

			sql = "update weibo.weibo set repost_count = repost_count + 1 where id = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, weibo_id);
			pstmt.executeUpdate();

			sql = "update weibo.user set weibo_count = weibo_count + 1 where id = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, myid);
			pstmt.executeUpdate();
			
			dbUtil.release(conn, pstmt);

			resp.sendRedirect("./MyWeibo");
			//
			// req.setAttribute("message", "微博发布成功！");
			// req.getRequestDispatcher("/HotWeibo").forward(req, resp);
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
