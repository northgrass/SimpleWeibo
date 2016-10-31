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

public class MyWeibo extends HttpServlet {
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
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		PreparedStatement pstmt = null;
		try {

			String sql;
			sql = "select weibo_count from weibo.user where id = ?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, myid);
			ResultSet rs = pstmt.executeQuery();
			int size = 0;
			if (rs.next()) {
				size = rs.getInt("weibo_count");

			}
			
			//由管理员删除的微博也显示出来
			
			sql = "select count(1) from weibo.weibo where is_checked=true and is_deleted=true and user_id=?";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, myid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				size = size + (int)rs.getLong(1);
			}

			pageCount = (size % PAGESIZE == 0) ? (size / PAGESIZE) : (size / PAGESIZE + 1);
			int start = PAGESIZE * (curPage - 1); // 获得当前的查询起点
			int offset = 5;
//			if (size < start + PAGESIZE) { // 获得offset查询量的值
//				offset = size - start;
//			}
			sql = "select weibo.weibo.repost_content,weibo.weibo.content,weibo.weibo.comment_count,weibo.weibo.repost_count,weibo.weibo.create_date,weibo.user.email,weibo.weibo.id,weibo.weibo.reference_id,weibo.weibo.is_deleted,weibo.weibo.is_checked from weibo.weibo join weibo.user on weibo.weibo.user_id=weibo.user.id where weibo.weibo.user_id = ? and (weibo.weibo.is_deleted = false or (weibo.weibo.is_deleted = true and weibo.weibo.is_checked=true)) order by create_date desc limit ? offset ?";

			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setLong(1, myid);
			pstmt.setLong(2, offset);
			pstmt.setLong(3, start);
			rs = pstmt.executeQuery();
			ArrayList<Weibo> weiboList = new ArrayList<Weibo>();
			// while (rs.next()) {
			// Weibo weibo = new Weibo();
			// weibo.setCommentCount(rs.getLong("comment_count"));
			// weibo.setContent(rs.getString("content"));
			// weibo.setCreateDate(rs.getString("create_date"));
			// weibo.setRepostCount(rs.getLong("repost_count"));
			// weibo.setUsername(rs.getString("email"));
			// weibo.setWeiboid(rs.getLong("id"));
			//
			// weiboList.add(weibo);
			// }

			while (rs.next()) {
				Weibo weibo = new Weibo();
				if(rs.getBoolean(9)==false){
				if (rs.getLong(8) > 0) {
					long reference_id = rs.getLong(8);
					sql = "select weibo.weibo.content from weibo.weibo join weibo.user on weibo.weibo.user_id=weibo.user.id where weibo.weibo.is_deleted = false and weibo.weibo.id =?";
					pstmt = (PreparedStatement) conn.prepareStatement(sql);
					pstmt.setLong(1, reference_id);
					ResultSet rs1 = pstmt.executeQuery();
					if (rs1.next()) {
						weibo.setContent(rs.getString("repost_content") + rs1.getString("content"));
					}
				} else {
					weibo.setContent(rs.getString("content"));
					
				}
				weibo.setCommentCount(rs.getLong("comment_count"));
				weibo.setCreateDate(rs.getString("create_date"));
				weibo.setRepostCount(rs.getLong("repost_count"));
				weibo.setUsername(rs.getString("email"));
				weibo.setWeiboid(rs.getLong("id"));
				weibo.setReferenceid(rs.getLong(8));
				weibo.setFlag(0);
				
				weiboList.add(weibo);
				}
				else if(rs.getBoolean(9)==true&&rs.getBoolean(10)==true){
					weibo.setUsername(rs.getString("email"));
					weibo.setFlag(1);
					weibo.setCreateDate(rs.getString("create_date"));
					
					weiboList.add(weibo);
				}
				

			
			}
			req.setAttribute("weiboList", weiboList);
			req.setAttribute("curPage", curPage);
			req.setAttribute("pageCount", pageCount);
			req.getRequestDispatcher("myweibo-page.jsp").forward(req, resp);
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
