package com.shgm.controller;

import java.io.*;
import java.sql.*;

import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


public class DisplayImg extends HttpServlet {
	
	private static javax.sql.DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA101G6DB");
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
		
	private static final String GET_IMG_STMT= "SELECT img FROM SHGM WHERE shgmno=?";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("enter displayimg.jsp");
		response.setContentType("image/gif");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Blob blob = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_IMG_STMT);
			
			String shgmno = request.getParameter("shgmno");
			pstmt.setString(1, shgmno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				blob = rs.getBlob("img");
				System.out.println("filelength："+blob.length()+"     "+blob.toString());
			}
			InputStream is = blob.getBinaryStream();
			ServletOutputStream ops = response.getOutputStream();
			byte[] buffer = new byte[(int)blob.length()];
			int i = 0;
			while((i = is.read(buffer)) != -1) {
				ops.write(buffer, 0, i);
			}
			ops.close();
			is.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}
