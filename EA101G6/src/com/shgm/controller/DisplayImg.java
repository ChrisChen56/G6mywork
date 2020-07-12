package com.shgm.controller;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		String shgmno = request.getParameter("shgmno");
		ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);
		bytearr = shgmvo.getImg();

		ServletOutputStream ops = response.getOutputStream();
		ops.write(bytearr);
		ops.close();
	}
}
