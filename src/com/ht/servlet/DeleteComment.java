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
import sun.text.normalizer.UBiDiProps;

public class DeleteComment extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String flag = req.getParameter("commentid");
		long comment_id = Integer.parseInt(flag);
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			String sql;
			sql = "update weibo.comment set is_delete = true where id = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, comment_id);
			pstmt.executeUpdate();

			sql = "select weibo.comment.weibo_id from weibo.comment where weibo.comment.id =?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, comment_id);
			ResultSet rs = pstmt.executeQuery();
			long weibo_id = 0;
			if (rs.next()) {
				weibo_id = rs.getLong("weibo_id");
			}

			sql = "update weibo.weibo set comment_count=comment_count-1 where id=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, weibo_id);
			pstmt.executeUpdate();
			
			
			sql = "select comment_count from weibo.weibo where id = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, weibo_id);
			rs = pstmt.executeQuery();
			int comment_count = 0;
			if (rs.next()) {
				comment_count = rs.getInt("comment_count");
			}
			req.setAttribute("comment_count", comment_count);
			req.setAttribute("curPage", 1);
			req.setAttribute("weibo_id", weibo_id);
			
			dbUtil.release(conn, pstmt);
			

//			resp.sendRedirect("WeiboDetail?id=" + weibo_id + "&curPage=1");
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
