package com.shgm.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.shgm.model.ShgmService;
import com.shgm.model.ShgmVO;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 7 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ShgmServlet2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ShgmService shgmsvc = (ShgmService) session.getAttribute("shgmsvc");
		if (shgmsvc == null) {
			shgmsvc = new ShgmService();
			session.setAttribute("shgmsvc", shgmsvc);
		}
		String action = request.getParameter("action");
		System.out.println("enter controller2");

		if ("sellshgm".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			String imagefailed = request.getParameter("imgtag");
			try {

				String sellerno = request.getParameter("sellerno");

				
				sellerno = "CA00005";//這裡先寫死
				
				
				String shgmname = request.getParameter("shgmname");
				if (shgmname.trim().length() == 0)
					errormsgs.add("市集商品名稱：請勿輸入空白");

				Double price = null;
				try {
					price = new Double(request.getParameter("price"));
				} catch (Exception e) {
					errormsgs.add("市集商品價錢：格式不正確");
				}

				String intro = request.getParameter("intro");
				if (intro.trim().length() == 0)
					errormsgs.add("市集商品簡介：簡介文字不得為空");

				byte[] img = null;

				Part imgreq = request.getPart("img");
				if (imgreq.getSize() == 0 && (byte[]) session.getAttribute("img") == null) {
					errormsgs.add("市集商品圖片：市集商品圖片不得為空");
				} else {
					try {
						if (imgreq.getSize() != 0) {
							System.out.println("enter here");
							InputStream is = imgreq.getInputStream();
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							byte[] bufferd = new byte[8192];
							int i = 0;
							while ((i = is.read(bufferd)) != -1) {
								baos.write(bufferd, 0, i);
							}
							baos.flush();
							baos.close();
							img = baos.toByteArray();
							session.setAttribute("img", img);
							System.out.println(img);
						} else {
							img = (byte[]) session.getAttribute("img");
						}

						imagefailed = Base64.encode(img);

					} catch (Exception e) {
						errormsgs.add("市集商品圖片：" + e.getMessage());
					}
				}

				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setSellerno(sellerno);
				shgmvo.setShgmname(shgmname);
				shgmvo.setPrice(price);
				shgmvo.setIntro(intro);
				shgmvo.setImg(img);

				if (!errormsgs.isEmpty()) {
					request.setAttribute("imagefailed", imagefailed);
					request.setAttribute("shgmvo", shgmvo);
					String url = "sellPage.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}
				
				shgmsvc.sellShgm(sellerno, shgmname, price, intro, img);

				request.removeAttribute("imagefailed");
				session.removeAttribute("shgmlist");
				session.removeAttribute("img");// 新增成功把顯示用的圖片刪掉
				System.out.println("新增成功");

				String url = "mainPage.jsp";// forward到mainPage or myshgamePage??
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				request.setAttribute("imagefailed", imagefailed);
				errormsgs.add("無法新增您的商品：" + e.getMessage());
				String url = "sellPage.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}
		
		if("buyshgm".equals(action)) {
			
			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {
				String shgmno = request.getParameter("shgmno");

				String buyerno = request.getParameter("buyerno");

				String take = request.getParameter("take");
				if(take.trim().length() > 10)
					errormsgs.add("取貨方式：長度不正確");
				if(take.trim().length() == 0)
					errormsgs.add("取貨方式：請勿輸入空白");
				
				String takernm = request.getParameter("takernm");
				if(takernm.trim().length() > 10)
					errormsgs.add("取貨人姓名：長度不正確");
				if(takernm.trim().length() == 0)
					errormsgs.add("取貨人姓名：請勿輸入空白");

				
				Integer takerph = null;
				try {
					String takerphstr = request.getParameter("takerph");
					if(takerphstr.trim().length() > 10)
						errormsgs.add("取貨人電話：請輸入十碼以內的電話號碼");
					if(takerphstr.trim().length() == 0)
						errormsgs.add("取貨人電話：請勿輸入空白");
					takerph = new Integer(takerphstr);
				} catch (Exception e) {
					errormsgs.add("取貨人電話：格式不正確");
				}

				String address = request.getParameter("address");
				if (address.trim().length() == 0) {
					errormsgs.add("取貨地址：地址不得為空");
				}

				Integer boxstatus = new Integer(request.getParameter("boxstatus"));

				Integer paystatus = new Integer(request.getParameter("paystatus"));

				Integer status = new Integer(request.getParameter("status"));

				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(shgmno);
				shgmvo.setBuyerno(buyerno);
				shgmvo.setTake(take);
				shgmvo.setTakernm(takernm);
				shgmvo.setTakerph(takerph);
				shgmvo.setAddress(address);
				shgmvo.setBoxstatus(boxstatus);
				shgmvo.setPaystatus(paystatus);
				shgmvo.setStatus(status);

				if (!errormsgs.isEmpty()) {
					request.setAttribute("shgmvo", shgmvo);
					String url = "updateShgm.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				shgmsvc.buyshgm(shgmno, buyerno, take, takernm, takerph, address, boxstatus, paystatus, status);
				
				if(boxstatus == 2 && paystatus == 1 && status == 2) {
					shgmsvc.odComplete(shgmno);
				}
				
				request.setAttribute("shgmvo", shgmvo);
				session.removeAttribute("shgmlist");
				System.out.println("removed from session");

				String url = "shgm.do?action=get_all";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormsgs.add("無法修改資料" + e.getMessage());
				String url = "updateShgm.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}
		
	}
}