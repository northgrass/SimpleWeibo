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

import entity.DBUtil;
import entity.Weibo;

public class HotWeibo extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PreparedStatement pstmt = null;
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		ArrayList<Weibo> weiboList = new ArrayList<Weibo>();
		System.out.println("length");

		try {
			String sql;
			sql = "select weibo.weibo.repost_content,weibo.weibo.content,weibo.weibo.comment_count,weibo.weibo.repost_count,weibo.weibo.create_date,weibo.user.email,weibo.user.id,weibo.weibo.id,weibo.weibo.reference_id from weibo.weibo join weibo.user on weibo.weibo.user_id=weibo.user.id where weibo.weibo.is_deleted = false and weibo.weibo.is_locked=false order by weibo.weibo.comment_count desc limit 10";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Weibo weibo = new Weibo();
				if (rs.getLong(9) > 0) {
					long reference_id = rs.getLong(9);
					sql = "select weibo.weibo.content from weibo.weibo join weibo.user on weibo.weibo.user_id=weibo.user.id where weibo.weibo.is_deleted = false and weibo.weibo.is_locked=false and weibo.weibo.id =?";
					pstmt = (PreparedStatement) conn.prepareStatement(sql);
					pstmt.setLong(1, reference_id);
					ResultSet rs1 = pstmt.executeQuery();
					while (rs1.next()) {
						weibo.setContent(rs.getString("repost_content") + rs1.getString("content"));
					}
					rs1.close();
				} else {
					weibo.setContent(rs.getString("content"));
				}
				weibo.setCommentCount(rs.getLong("comment_count"));
				weibo.setCreateDate(rs.getString("create_date"));
				weibo.setRepostCount(rs.getLong("repost_count"));
				weibo.setUsername(rs.getString("email"));
				weibo.setUserid(rs.getLong(7));
				weibo.setWeiboid(rs.getLong(8));
				weibo.setReferenceid(rs.getLong(9));

				weiboList.add(weibo);
			}
			rs.close();
			System.out.println("length" + weiboList.size());
			req.setAttribute("weiboList", weiboList);
			req.getRequestDispatcher("homepage.jsp").forward(req, resp);
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
