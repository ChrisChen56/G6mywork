package com.shgm.controller;

import java.io.*;
import java.sql.*;

import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.shgm.model.ShgmService;
import com.shgm.model.ShgmVO;

public class DisplayImg extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/gif");
		ShgmService shgmsvc = new ShgmService();
		byte[] bytearr = null;
		HttpSession session = request.getSession();

		String shgmno = request.getParameter("shgmno");

		if (session.getAttribute(shgmno + "imgses") == null) {
			
			ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);
			bytearr = shgmvo.getImg();
			session.setAttribute(shgmno + "imgses", bytearr);
			
		} else {
			bytearr = (byte[]) session.getAttribute(shgmno + "imgses");
		}
		ServletOutputStream ops = response.getOutputStream();
		ops.write(bytearr);
		ops.close();
	}
}
