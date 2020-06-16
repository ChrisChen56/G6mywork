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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.shgm.model.ShgmService;
import com.shgm.model.ShgmVO;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 7 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ShgmServlet extends HttpServlet {

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
		System.out.println("enter controller");

		if ("get_one".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {
				String str = request.getParameter("shgmno");
				String strreg = "^CA\\d{5}$";
				if (str == null || (str.trim()).length() == 0) {
					errormsgs.add("您未輸入市集商品編號");
				} else if (!str.matches(strreg)) {
					errormsgs.add("請依照市集商品編號格式輸入");
				}
				if (!errormsgs.isEmpty()) {
					RequestDispatcher failureview = request.getRequestDispatcher("shgm_select_page.jsp");
					failureview.forward(request, response);
					return;
				}
				ShgmVO shgmvo = shgmsvc.getOneShgm(str);
				if (shgmvo == null) {
					errormsgs.add("查無資料");
				}
				if (!errormsgs.isEmpty()) {
					RequestDispatcher failureview = request.getRequestDispatcher("shgm_select_page.jsp");
					failureview.forward(request, response);
					return;
				}
				request.setAttribute("shgmvo", shgmvo);
				String url = "listOneShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormsgs.add("無法取得個別資料" + e.getMessage());
				RequestDispatcher failureview = request.getRequestDispatcher("shgm_select_page.jsp");
				failureview.forward(request, response);
			}
		}

		if ("get_all".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			List<ShgmVO> shgmlist = null;
			try {
				if (session.getAttribute("shgmlist") == null) {

					
					shgmlist = shgmsvc.getAllShgm();
					System.out.println("get new shgmlist from session");
					// 測試錯誤處理throw new SQLException();
					if (shgmlist == null)
						errormsgs.add("查無資料");
					if (!errormsgs.isEmpty()) {
						RequestDispatcher failureview = request.getRequestDispatcher("shgm_select_page.jsp");
						failureview.forward(request, response);
						return;
					}
				} else {
					shgmlist = (List<ShgmVO>) session.getAttribute("shgmlist");
					System.out.println("get from session");
				}
				session.setAttribute("shgmlist", shgmlist);
				String url = "listAllShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormsgs.add("無法取得全部資料" + e.getMessage());
				RequestDispatcher failureview = request.getRequestDispatcher("shgm_select_page.jsp");
				failureview.forward(request, response);
			}
		}

		if ("insert".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			String imagefailed = request.getParameter("imgtag");
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
				if (imgreq.getSize() == 0 && (byte[])session.getAttribute("img") == null) {
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
							img = (byte[])session.getAttribute("img");
						}
						
						imagefailed = Base64.encode(img);
						
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
					request.setAttribute("imagefailed", imagefailed);
					request.setAttribute("shgmvo", shgmvo);
					String url = "addShgm.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				
				shgmsvc.addShgm(buyerno, sellerno, shgmname, price, intro, img, upcheck, uptimevo, take, takernm,
						takerph, address, boxstatus, paystatus, status, soldtimevo);

				request.removeAttribute("imagefailed");
				session.removeAttribute("shgmlist");
				session.removeAttribute("img");//新增成功把顯示用的圖片刪掉

				String url = "shgm.do?action=get_all";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				request.setAttribute("imagefailed", imagefailed);
				errormsgs.add("無法新增市集商品：" + e.getMessage());
				String url = "addShgm.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}
		
		if ("sellshgm".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			String imagefailed = request.getParameter("imgtag");
			try {

				String sellerno = request.getParameter("sellerno");

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
				if (imgreq.getSize() == 0 && (byte[])session.getAttribute("img") == null) {
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
							img = (byte[])session.getAttribute("img");
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
					String url = "/front-end/shgm/sellPage.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}
				
				shgmsvc.sellShgm(sellerno, shgmname, price, intro, img);

				request.removeAttribute("imagefailed");
				session.removeAttribute("shgmlist");
				session.removeAttribute("img");//新增成功把顯示用的圖片刪掉

				String url = "XXX";//forward到mainPage or myshgamePage??
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				request.setAttribute("imagefailed", imagefailed);
				errormsgs.add("無法新增您的商品：" + e.getMessage());
				String url = "/front-end/shgm/sellPage.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("delete".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {
				String shgmno = request.getParameter("shgmno");
				
				shgmsvc.deleteShgm(shgmno);

				session.removeAttribute("shgmlist");

				String url = "shgm.do?action=get_all";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormsgs.add("刪除發生錯誤" + e.getMessage());
				String url = "listAllShgm.jsp";
				RequestDispatcher errorview = request.getRequestDispatcher(url);
				errorview.forward(request, response);
			}
		}

		if ("getone_update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			request.setAttribute("errorMsgs", errorMsgs);

			try {
				String shgmno = request.getParameter("shgmno");

				
				ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);

				request.setAttribute("shgmvo", shgmvo);
				String url = "updateShgm.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url);
				successView.forward(request, response);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				String url = "shgm_select_page.jsp";
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
					img = (byte[]) session.getAttribute("img");
					if(img == null) {
						ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);
						img = shgmvo.getImg();
						session.setAttribute("img", img);
						System.out.println("img stored in session");
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
					String url = "updateShgm.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				
				shgmsvc.updateShgm(shgmno, buyerno, sellerno, shgmname, price, intro, img, upcheck, uptimevo, take,
						takernm, takerph, address, boxstatus, paystatus, status, soldtimevo);
				
				request.setAttribute("shgmvo", shgmvo);
				session.removeAttribute("shgmlist");
				session.removeAttribute("img");
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