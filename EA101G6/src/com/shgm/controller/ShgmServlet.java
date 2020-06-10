package com.shgm.controller;

import java.io.IOException;
import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.shgm.model.ShgmService;
import com.shgm.model.ShgmVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 7 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ShgmServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		System.out.println("enter controller");

		if ("get_one".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {
				String str = request.getParameter("shgmno");
				if (str == null || (str.trim()).length() == 0) {
					errormsgs.add("您未輸入編號");

				}
				if (!errormsgs.isEmpty()) {
					RequestDispatcher failureview = request.getRequestDispatcher("/back-end/shgm/shgm_select_page.jsp");
					failureview.forward(request, response);
					return;
				}

				ShgmService shgmdao = new ShgmService();
				ShgmVO shgmvo = shgmdao.getOneShgm(str);
				if (shgmvo == null) {
					errormsgs.add("查無資料");
				}
				if (!errormsgs.isEmpty()) {
					RequestDispatcher failureview = request.getRequestDispatcher("/back-end/shgm/shgm_select_page.jsp");
					failureview.forward(request, response);
					return;
				}
				request.setAttribute("shgmvo", shgmvo);
				String url = "/back-end/shgm/listOneShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormsgs.add("無法取得個別資料" + e.getMessage());
				RequestDispatcher failureview = request.getRequestDispatcher("/back-end/shgm/shgm_select_page.jsp");
				failureview.forward(request, response);
			}
		}

		if ("get_all".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {

				ShgmService shgmdao = new ShgmService();
				List<ShgmVO> list = shgmdao.getAllShgm();
				// 測試錯誤處理throw new SQLException();
				if (list == null) {
					errormsgs.add("查無資料");
				}
				if (!errormsgs.isEmpty()) {
					RequestDispatcher failureview = request.getRequestDispatcher("/back-end/shgm/shgm_select_page.jsp");
					failureview.forward(request, response);
					return;
				}
				request.setAttribute("list", list);
				String url = "/back-end/shgm/listAllShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormsgs.add("無法取得全部資料" + e.getMessage());
				RequestDispatcher failureview = request.getRequestDispatcher("/back-end/shgm/shgm_select_page.jsp");
				failureview.forward(request, response);
			}
		}

		if ("insert".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {

				String sellerno = request.getParameter("sellerno");
				String sellernoreg = "^BM\\d{5}$";
				if (sellerno.trim().length() == 0) {
					errormsgs.add("賣家編號：請勿輸入空白");
				} else if (!sellerno.trim().matches(sellernoreg)) {
					errormsgs.add("賣家編號：BM開頭、長度7的格式");
				}

				String buyerno = request.getParameter("buyerno");
				String buyernoreg = "^BM\\d{5}$";
				if (buyerno.trim().length() == 0) {
					errormsgs.add("買家編號：請勿輸入空白");
				} else if (!buyerno.trim().matches(buyernoreg)) {
					errormsgs.add("買家編號：BM開頭、長度7的格式");
				}

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
				if (imgreq.getSize() == 0) {
					errormsgs.add("市集商品圖片：市集商品圖片不得為空");
				} else {
					try {
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
						System.out.println(img);
					} catch (Exception e) {
						errormsgs.add("市集商品圖片：" + e.getMessage());
					}
				}

				Integer upcheck = new Integer(request.getParameter("upcheck"));

				Timestamp uptimevo = null;
				try {
					String uptimepara = request.getParameter("uptime");
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date date = dateformat.parse(uptimepara);
					uptimevo = new Timestamp(date.getTime());
					System.out.println(uptimevo);
				} catch (ParseException e) {
					errormsgs.add("上架時間：日期轉型發生錯誤");
				}

				String take = request.getParameter("take");

				String takernm = request.getParameter("takernm");

				Integer takerph = null;
				try {
					takerph = new Integer(request.getParameter("takerph"));
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

				Timestamp soldtimevo = null;
				try {
					String soldtimepara = request.getParameter("soldtime");
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date date = dateformat.parse(soldtimepara);
					soldtimevo = new Timestamp(date.getTime());
					System.out.println(soldtimevo);
				} catch (ParseException e) {
					errormsgs.add(e.getMessage());
					errormsgs.add("售出時間：日期轉型發生錯誤");
				}

				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setBuyerno(buyerno);
				shgmvo.setSellerno(sellerno);
				shgmvo.setShgmname(shgmname);
				shgmvo.setPrice(price);
				shgmvo.setIntro(intro);
				shgmvo.setImg(img);
				shgmvo.setUpcheck(upcheck);
				shgmvo.setUptime(uptimevo);
				shgmvo.setTake(take);
				shgmvo.setTakernm(takernm);
				shgmvo.setTakerph(takerph);
				shgmvo.setAddress(address);
				shgmvo.setBoxstatus(boxstatus);
				shgmvo.setPaystatus(paystatus);
				shgmvo.setStatus(status);
				shgmvo.setSoldtime(soldtimevo);

				if (!errormsgs.isEmpty()) {
					request.setAttribute("shgmvo", shgmvo);
					String url = "/back-end/shgm/addShgm.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				ShgmService shgmdao = new ShgmService();
				shgmdao.addShgm(buyerno, sellerno, shgmname, price, intro, img, upcheck, uptimevo,
						take, takernm, takerph, address, boxstatus, paystatus, status, soldtimevo);

				String url = "/back-end/shgm/listAllShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormsgs.add("無法新增市集商品：" + e.getMessage());
				String url = "/back-end/shgm/addShgm.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("delete".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {
				String shgmno = request.getParameter("shgmno");
				ShgmService shgmdao = new ShgmService();
				shgmdao.deleteShgm(shgmno);

				String url = "/back-end/shgm/listAllShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormsgs.add("刪除發生錯誤" + e.getMessage());
				String url = "/back-end/shgm/listAllShgm.jsp";
				RequestDispatcher errorview = request.getRequestDispatcher(url);
				errorview.forward(request, response);
			}
		}

		if ("getone_update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			request.setAttribute("errorMsgs", errorMsgs);

			try {
				String shgmno = request.getParameter("shgmno");

				ShgmService shgmdao = new ShgmService();
				ShgmVO shgmvo = shgmdao.getOneShgm(shgmno);

				request.setAttribute("shgmvo", shgmvo);
				String url = "/back-end/shgm/updateShgm.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url);
				successView.forward(request, response);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				String url = "/back-end/shgm/shgm_select_page.jsp";
				RequestDispatcher failureView = request.getRequestDispatcher(url);
				failureView.forward(request, response);
			}
		}

		if ("update".equals(action)) {
			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {
				String shgmno = request.getParameter("shgmno");

				String sellerno = request.getParameter("sellerno");
				String sellernoreg = "^BM\\d{5}$";
				if (sellerno.trim().length() == 0) {
					errormsgs.add("賣家編號：請勿輸入空白");
				} else if (!sellerno.trim().matches(sellernoreg)) {
					errormsgs.add("賣家編號：BM開頭、長度7的格式");
				}

				String buyerno = request.getParameter("buyerno");
				String buyernoreg = "^BM\\d{5}$";
				if (buyerno.trim().length() == 0) {
					errormsgs.add("買家編號：請勿輸入空白");
				} else if (!buyerno.trim().matches(buyernoreg)) {
					errormsgs.add("買家編號：BM開頭、長度7的格式");
				}

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
				if (imgreq.getSize() != 0) {
					try {
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
						System.out.println(img);
					} catch (Exception e) {
						errormsgs.add("市集商品圖片：" + e.getMessage());
					}
				} else {
					ShgmService shgmdao = new ShgmService();
					ShgmVO shgmvo = shgmdao.getOneShgm(shgmno);
					img = shgmvo.getImg();
				}

				Integer upcheck = new Integer(request.getParameter("upcheck"));

				Timestamp uptimevo = null;
				try {
					String uptimepara = request.getParameter("uptime");
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date date = dateformat.parse(uptimepara);
					uptimevo = new Timestamp(date.getTime());
					System.out.println(uptimevo);
				} catch (ParseException e) {
					errormsgs.add("上架時間：日期轉型發生錯誤");
				}

				String take = request.getParameter("take");

				String takernm = request.getParameter("takernm");

				Integer takerph = null;
				try {
					takerph = new Integer(request.getParameter("takerph"));
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

				Timestamp soldtimevo = null;
				try {
					String soldtimepara = request.getParameter("soldtime");
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date date = dateformat.parse(soldtimepara);
					soldtimevo = new Timestamp(date.getTime());
					System.out.println(soldtimevo);
				} catch (ParseException e) {
					errormsgs.add(e.getMessage());
					errormsgs.add("售出時間：日期轉型發生錯誤");
				}

				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(shgmno);
				shgmvo.setSellerno(sellerno);
				shgmvo.setBuyerno(buyerno);
				shgmvo.setShgmname(shgmname);
				shgmvo.setPrice(price);
				shgmvo.setIntro(intro);
				shgmvo.setImg(img);
				shgmvo.setUpcheck(upcheck);
				shgmvo.setUptime(uptimevo);
				shgmvo.setTake(take);
				shgmvo.setTakernm(takernm);
				shgmvo.setTakerph(takerph);
				shgmvo.setAddress(address);
				shgmvo.setBoxstatus(boxstatus);
				shgmvo.setPaystatus(paystatus);
				shgmvo.setStatus(status);
				shgmvo.setSoldtime(soldtimevo);

				if (!errormsgs.isEmpty()) {
					request.setAttribute("shgmvo", shgmvo);
					String url = "/back-end/shgm/updateShgm.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				ShgmService shgmdao = new ShgmService();
				shgmdao.updateShgm(shgmno, buyerno, sellerno, shgmname, price, intro, img, upcheck, uptimevo,
						take, takernm, takerph, address, boxstatus, paystatus, status, soldtimevo);

				request.setAttribute("shgmvo", shgmvo);

				String url = "/back-end/shgm/listOneShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormsgs.add("無法修改資料" + e.getMessage());
				String url = "/back-end/shgm/updateShgm.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}
	}
}