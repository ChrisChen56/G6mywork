package com.shgmrp.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shgmrp.model.ShgmrpService;
import com.shgmrp.model.ShgmrpVO;

public class ShgmrpServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("get_one".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);
			try {

				String shgmrpno = request.getParameter("shgmrpno");
				String shgmrpnoreg = "^CB\\d{5}$";

				if (shgmrpno.trim().length() == 0) {
					errormsgs.add("您未輸入市集商品檢舉編號");
				} else if (!shgmrpno.matches(shgmrpnoreg)) {
					errormsgs.add("請依照市集商品檢舉編號格式輸入");
				}
				if (!errormsgs.isEmpty()) {
					RequestDispatcher failedview = request.getRequestDispatcher("shgmrp_select_page.jsp");
					failedview.forward(request, response);
					return;
				}

				ShgmrpService shgmrpsvc = new ShgmrpService();
				ShgmrpVO shgmrpvo = shgmrpsvc.getOneShgmrp(shgmrpno);
				if(shgmrpvo == null) {
					errormsgs.add("查無資料");
				}
				if (!errormsgs.isEmpty()) {
					RequestDispatcher failedview = request.getRequestDispatcher("shgmrp_select_page.jsp");
					failedview.forward(request, response);
					return;
				}
				
				request.setAttribute("shgmrpvo", shgmrpvo);
				RequestDispatcher successview = request.getRequestDispatcher("listOneShgmrp.jsp");
				successview.forward(request, response);

			} catch (Exception e) {
				errormsgs.add("無法取得個別資料" + e.getMessage());
				RequestDispatcher failedview = request.getRequestDispatcher("shgmrp_select_page.jsp");
				failedview.forward(request, response);
			}
		}

	}

}
