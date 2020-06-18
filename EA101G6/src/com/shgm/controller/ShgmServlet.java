package com.shgm.controller;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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

import com.mbrpf.model.MbrpfService;
import com.mbrpf.model.MbrpfVO;

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
		
		if("login".equals(action)) {
			
			String mbract = request.getParameter("mbract");
			String mbrpw = request.getParameter("mbrpw");
			
			MbrpfService mbrsvc = new MbrpfService();
			if(mbrsvc.check(mbract, mbrpw)) {
				MbrpfVO mbrpfvo = mbrsvc.getByActPw(mbract, mbrpw);
				
				session.setAttribute("member", mbrpfvo);
				RequestDispatcher successview = request.getRequestDispatcher("mainPage.jsp");
				successview.forward(request, response);
				
			} else {
				String error = "帳號密碼錯誤";
				request.setAttribute("error", error);
				RequestDispatcher failedview = request.getRequestDispatcher("simpleLogin.jsp");
				failedview.forward(request, response);
			}
		}

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

		if ("getOneToBuy".equals(action)) {

			String str = request.getParameter("shgmno");
			ShgmVO shgmvo = shgmsvc.getOneShgm(str);
			request.setAttribute("shgmvo", shgmvo);
			RequestDispatcher successview = request.getRequestDispatcher("buyPage.jsp");
			successview.forward(request, response);
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

				Integer price = null;
				String pricestr = request.getParameter("price");
				if (pricestr.trim().length() == 0) {
					errormsgs.add("市集商品價錢：價錢不得為空");
				} else if (pricestr.trim().length() > 6) {
					errormsgs.add("市集商品價錢：金額超過本平台規範");
				} else {
					try {
						price = new Integer(pricestr);
					} catch (Exception e) {
						errormsgs.add("市集商品價錢：格式不正確");
					}
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
						} else {
							img = (byte[]) session.getAttribute("img");
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
				} catch (ParseException e) {
					errormsgs.add("上架時間：日期轉型發生錯誤");
				}

				String take = request.getParameter("take");

				String takernm = request.getParameter("takernm");

				String takerph = request.getParameter("takerph");
				String takerphreg = "^09\\d{8}$";
				if (takerph.trim().length() == 0) {
					errormsgs.add("取貨人電話：電話不得為空");
				} else if (!takerph.trim().matches(takerphreg)) {
					errormsgs.add("取貨人電話：請輸入符合格式的電話號碼");
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
				session.removeAttribute("img");// 新增成功把顯示用的圖片刪掉

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

			HashMap<Long, String> errormap = new HashMap<Long, String>();
			request.setAttribute("errormap", errormap);

			String imagefailed = request.getParameter("imgtag");
			try {

				String sellerno = request.getParameter("sellerno");

				sellerno = "BM00005";// 這裡先寫死

				String shgmname = request.getParameter("shgmname");
				if (shgmname.trim().length() == 0)
					errormap.put((long) 1, "名稱不得為空");

				Integer price = null;
				String pricestr = request.getParameter("price");
				if (pricestr.trim().length() == 0) {
					errormap.put((long) 2, "價錢不得為空");
				} else if (pricestr.trim().length() > 6) {
					errormap.put((long) 2, "金額超過本平台規範");
				} else {
					try {
						price = new Integer(pricestr);
					} catch (Exception e) {
						errormap.put((long) 2, "格式不正確");
					}
				}

				String intro = request.getParameter("intro");
				if (intro.trim().length() == 0)
					errormap.put((long) 3, "簡介文字不得為空");

				byte[] img = null;

				Part imgreq = request.getPart("img");
				if (imgreq.getSize() == 0 && (byte[]) session.getAttribute("img") == null) {
					errormap.put((long) 4, "圖片不得為空");
				} else {
					try {
						if (imgreq.getSize() != 0) {
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
						} else {
							img = (byte[]) session.getAttribute("img");
						}

						imagefailed = Base64.encode(img);

					} catch (Exception e) {
						errormap.put((long) 4, "圖片無法上傳");
					}
				}

				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setSellerno(sellerno);
				shgmvo.setShgmname(shgmname);
				shgmvo.setPrice(price);
				shgmvo.setIntro(intro);
				shgmvo.setImg(img);

				if (!errormap.isEmpty()) {
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

				String url = "mainPage.jsp";// forward到mainPage or myshgamePage??
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				request.setAttribute("imagefailed", imagefailed);
				errormap.put((long) 5, "無法新增您的商品");
				String url = "sellPage.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("dealingshgm".equals(action)) {
			ShgmVO shgmvo = null;

			HashMap<Long, String> errormap = new HashMap<Long, String>();
			request.setAttribute("errormap", errormap);
			try {
				String shgmno = request.getParameter("shgmno");

				String buyerno = request.getParameter("buyerno");
				buyerno = "BM00007";

				String take = request.getParameter("take");
				if (take.trim().length() > 10)
					errormap.put((long) 1, "長度不正確");
				if (take.trim().length() == 0)
					errormap.put((long) 1, "請勿輸入空白");

				String takernm = request.getParameter("takernm");
				if (takernm.trim().length() > 10)
					errormap.put((long) 2, "長度不正確");
				if (takernm.trim().length() == 0)
					errormap.put((long) 2, "請勿輸入空白");

				String takerph = request.getParameter("takerph");
				String takerphreg = "^09\\d{8}$";
				if (takerph.trim().length() == 0) {
					errormap.put((long) 3, "請勿輸入空白");
				} else if (!takerph.trim().matches(takerphreg)) {
					errormap.put((long) 3, "請輸入符合格式的電話號碼");
				}

				String address = request.getParameter("address");
				if (address.trim().length() == 0) {
					errormap.put((long) 4, "地址不得為空");
				}

				// 還有付款要處理 這裡先以正常出貨狀態來跑(0未出貨1已付款1已下訂)
				Integer boxstatus = new Integer(request.getParameter("boxstatus"));
				boxstatus = 2;

				Integer paystatus = new Integer(request.getParameter("paystatus"));
				paystatus = 1;

				Integer status = new Integer(request.getParameter("status"));
				status = 2;

				shgmvo = shgmsvc.getOneShgm(shgmno);
				// 取出當前要交易的商品，forward後可以順便用EL取出它的名稱與價錢
				shgmvo.setShgmno(shgmno);
				shgmvo.setBuyerno(buyerno);
				shgmvo.setTake(take);
				shgmvo.setTakernm(takernm);
				shgmvo.setTakerph(takerph);
				shgmvo.setAddress(address);
				shgmvo.setBoxstatus(boxstatus);
				shgmvo.setPaystatus(paystatus);
				shgmvo.setStatus(status);

				if (!errormap.isEmpty()) {
					request.setAttribute("shgmvo", shgmvo);
					String url = "buyPage.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				shgmsvc.dealingshgm(shgmno, buyerno, take, takernm, takerph, address, boxstatus, paystatus, status);

				//已送達、已付款、已完成
				if (boxstatus == 2 && paystatus == 1 && status == 2) {
					MbrpfService mbrsvc = new MbrpfService();
					String sellerno = shgmvo.getSellerno();
					//取出賣家的mbrpfvo以便對points做更動
					MbrpfVO mbrpfvo = mbrsvc.getOneMbrpf(sellerno);
					//把賣家原本的points加上販售之價格
					mbrsvc.update(sellerno, mbrpfvo.getPoints()+shgmvo.getPrice());
					//資料庫更新售出時間
					shgmsvc.odComplete(shgmno);
				}

				session.removeAttribute("shgmlist");
				System.out.println("removed from session");

				String url = "mainPage.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				request.setAttribute("shgmvo", shgmvo);
				errormap.put((long) 5, "無法購買此商品");
				String url = "buyPage.jsp";
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
				buyerno = null;
//				String buyernoreg = "^BM\\d{5}$";
//				if (buyerno.trim().length() == 0) {
//					errormsgs.add("買家編號：請勿輸入空白");
//				} else if (!buyerno.trim().matches(buyernoreg)) {
//					errormsgs.add("買家編號：BM開頭、長度7的格式");
//				}

				String shgmname = request.getParameter("shgmname");
				if (shgmname.trim().length() == 0)
					errormsgs.add("市集商品名稱：請勿輸入空白");

				Integer price = null;
				String pricestr = request.getParameter("price");
				if (pricestr.trim().length() == 0) {
					errormsgs.add("市集商品價錢：價錢不得為空");
				} else if (pricestr.trim().length() > 6) {
					errormsgs.add("市集商品價錢：金額超過本平台規範");
				} else {
					try {
						price = new Integer(pricestr);
					} catch (Exception e) {
						errormsgs.add("市集商品價錢：格式不正確");
					}
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
					if (img == null) {
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
				if (take.trim().length() > 10)
					errormsgs.add("取貨方式：長度不正確");
				if (take.trim().length() == 0)
					errormsgs.add("取貨方式：請勿輸入空白");

				String takernm = request.getParameter("takernm");
				if (takernm.trim().length() > 10)
					errormsgs.add("取貨人姓名：長度不正確");
				if (takernm.trim().length() == 0)
					errormsgs.add("取貨人姓名：請勿輸入空白");

				String takerph = request.getParameter("takerph");
				takerph = null;
//				String takerphreg = "^09\\d{8}$";
//				if (takerph.trim().length() == 0) {
//					errormsgs.add("取貨人電話：請勿輸入空白");
//				} else if (!takerph.trim().matches(takerphreg)) {
//					errormsgs.add("取貨人電話：請輸入符合格式的電話號碼");
//				}

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
					String url = "updateShgm.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				ShgmVO shgmvo2 = shgmsvc.updateShgm(shgmno, sellerno, buyerno, shgmname, price, intro, img, upcheck, uptimevo, take,
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