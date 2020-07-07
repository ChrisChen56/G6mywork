package com.mbrpf.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.mbrpf.model.*;

public class MbrimgServlet extends HttpServlet {

	

	protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {

		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		//讀一次後存到session裡讓效能好一點
			HttpSession session = req.getSession();	
			MbrpfVO mbrpfVO = null;
			String mbrno = req.getParameter("mbrno");
			System.out.println(mbrno);
			if(session.getAttribute(mbrno)==null) {
				MbrpfService ser = new MbrpfService();
				mbrpfVO = ser.getOneMbrpf(mbrno);
				out.write(mbrpfVO.getMbrimg());
				session.setAttribute(mbrno, mbrpfVO);
			}else {
				mbrpfVO=(MbrpfVO)session.getAttribute(mbrno);
				out.write(mbrpfVO.getMbrimg());
			}

			
			
			
		}

}
