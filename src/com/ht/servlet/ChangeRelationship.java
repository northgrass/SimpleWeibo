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

public class ChangeRelationship extends HttpServlet {
	public static final int PAGESIZE = 5;
	int pageCount;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AccountBean account = (AccountBean) req.getSession().getAttribute("account");
		long myid = account.getUserid();
		String flag = req.getParameter("userid");
		long userid = Long.parseLong(flag);
		String relationship = req.getParameter("relation");
		int relation = Integer.parseInt(relationship);
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			String sql;
			if (relation == 0) {
				sql = "insert into weibo.relationship (user_id,follow_id) values (?,?)";
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				pstmt.setLong(1, myid);
				pstmt.setLong(2, userid);
				pstmt.executeUpdate();
				

				sql = "update weibo.user set follow_count=follow_count+1 where id=?";
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				pstmt.setLong(1, myid);
				pstmt.executeUpdate();

				sql = "update weibo.user set fans_count=fans_count+1 where id=?";
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				pstmt.setLong(1, userid);
				pstmt.executeUpdate();
			} else {
				sql = "delete from weibo.relationship where user_id=? and follow_id=?";
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				pstmt.setLong(1, myid);
				pstmt.setLong(2, userid);
				pstmt.executeUpdate();

				sql = "update weibo.user set follow_count=follow_count-1 where id=?";
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				pstmt.setLong(1, myid);
				pstmt.executeUpdate();

				sql = "update weibo.user set fans_count=fans_count-1 where id=?";
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				pstmt.setLong(1, userid);
				pstmt.executeUpdate();
			}
			System.out.println(userid);
			
			dbUtil.release(conn, pstmt);

			req.getRequestDispatcher("./PersonalPage?userid=" + userid).forward(req, resp);

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
