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
import entity.Weibo;

public class WeiboList extends HttpServlet {
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

		AccountBean account = (AccountBean) req.getSession().getAttribute("account");
		long myid = account.getUserid();
		String page = req.getParameter("curPage");
		if (page == null) {
			page = "1";
		}
		int curPage = Integer.parseInt(page);
//		Connection conn = null;
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql;
			sql = "select count(*) as num from weibo.weibo join weibo.user on weibo.weibo.user_id=weibo.user.id join weibo.relationship on weibo.weibo.user_id =weibo.relationship.follow_id where (weibo.weibo.is_deleted=false and weibo.weibo.is_locked=false) or (weibo.weibo.is_deleted=true and weibo.weibo.is_checked=true) "
					+ "and weibo.relationship.user_id=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, myid);
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
			sql = "select distinct weibo.weibo.user_id,weibo.weibo.id,weibo.weibo.content,weibo.weibo.comment_count,"
					+ "weibo.weibo.repost_count,weibo.weibo.create_date,weibo.weibo.is_deleted,weibo.weibo.reference_id,"
					+ "weibo.user.email,weibo.weibo.is_deleted,weibo.weibo.is_checked,weibo.weibo.is_locked from weibo.weibo join weibo.user on weibo.weibo.user_id=weibo.user.id"
					+ " join weibo.relationship on weibo.weibo.user_id =weibo.relationship.follow_id where (weibo.weibo.is_deleted=false and weibo.weibo.is_locked=false) or (weibo.weibo.is_deleted=true and weibo.weibo.is_checked=true) "
					+ "and weibo.relationship.user_id=? order by weibo.weibo.create_date desc limit ? offset ?";

			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, myid);
			pstmt.setLong(2, offset);
			pstmt.setLong(3, start);
			rs = pstmt.executeQuery();
			ArrayList<Weibo> weiboList = new ArrayList<Weibo>();
			while (rs.next()) {
				Weibo weibo = new Weibo();
				if(rs.getBoolean("is_deleted")==false&&rs.getBoolean("is_locked")==false){
				weibo.setCommentCount(rs.getLong("comment_count"));
				weibo.setContent(rs.getString("content"));
				weibo.setCreateDate(rs.getString("create_date"));
				weibo.setRepostCount(rs.getLong("repost_count"));
				weibo.setUsername(rs.getString("email"));
				weibo.setUserid(rs.getLong("user_id"));
				weibo.setWeiboid(rs.getLong("id"));
				weibo.setFlag(0);

				weiboList.add(weibo);
			}
				else if(rs.getBoolean("is_deleted")==true&&rs.getBoolean("is_checked")==true){
					weibo.setUsername(rs.getString("email"));
					weibo.setUserid(rs.getLong("user_id"));
					weibo.setFlag(1);
					weibo.setCreateDate(rs.getString("create_date"));
					
					weiboList.add(weibo);
				}
			}
			
			
			req.setAttribute("weiboList", weiboList);
			req.setAttribute("curPage", curPage);
			req.setAttribute("pageCount", pageCount);
			req.getRequestDispatcher("weibolist-page.jsp").forward(req, resp);
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
