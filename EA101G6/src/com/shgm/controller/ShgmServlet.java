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

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 7 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ShgmServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		String action = request.getParameter("action");
		System.out.println("enter controller");

		if ("login".equals(action)) {

			// 請求參數取得帳號密碼
			String mbract = request.getParameter("mbract");
			String mbrpw = request.getParameter("mbrpw");

			MbrpfService mbrsvc = new MbrpfService();
			// MbrpfService的check方法在資料庫查詢是否有相對應的資料
			if (mbrsvc.check(mbract, mbrpw)) {
				// true，session存入當前使用者的資料
				MbrpfVO mbrpfvo = mbrsvc.getByActPw(mbract, mbrpw);
				session.setAttribute("member", mbrpfvo);
				// 測試印出姓名
				System.out.println(mbrpfvo.getMbrname());

				// 轉送到市集頁面
				String url = "/front-end/shgm/mainPage.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);

			} else {
				// false，request存入錯誤訊息
				String error = "帳號密碼錯誤";
				request.setAttribute("error", error);

				// 轉送到登入頁面
				String url = "/front-end/shgm/simpleLogin.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("get_one".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {
				// 請求參數取得市集商品編號
				String shgmno = request.getParameter("shgmno");
				// 錯誤處理
				String strreg = "^CA\\d{5}$";
				if ((shgmno.trim()).length() == 0) {
					errormsgs.add("您未輸入市集商品編號");
				} else if (!shgmno.trim().matches(strreg)) {
					errormsgs.add("請依照市集商品編號格式輸入");
				}

				// 如果參數有問題，轉送到後臺首頁，且程式中斷於此
				if (!errormsgs.isEmpty()) {
					String url = "/back-end/shgm/shgm_select_page.jsp";
					RequestDispatcher failureview = request.getRequestDispatcher(url);
					failureview.forward(request, response);
					return;
				}

				ShgmService shgmsvc = new ShgmService();
				ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);
				// 錯誤處理
				if (shgmvo == null) {
					errormsgs.add("查無資料");
				}

				// 如果shgmvo是空值，轉送到後臺首頁，且程式中斷於此
				if (!errormsgs.isEmpty()) {
					String url = "/back-end/shgm/shgm_select_page.jsp";
					RequestDispatcher failureview = request.getRequestDispatcher(url);
					failureview.forward(request, response);
					return;
				}
				// shgmvo存入request中，轉送到展示個別市集商品頁面
				request.setAttribute("shgmvo", shgmvo);
				String url = "/back-end/shgm/listOneShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);

				// 出現其他錯誤，存入錯誤訊息，轉送到後台首頁
			} catch (Exception e) {
				errormsgs.add("無法取得個別資料" + e.getMessage());
				String url = "/back-end/shgm/shgm_select_page.jsp";
				RequestDispatcher failureview = request.getRequestDispatcher(url);
				failureview.forward(request, response);
			}
		}
		if ("getOneToInfo".equals(action)) {

			String shgmno = request.getParameter("shgmno");

			ShgmService shgmsvc = new ShgmService();
			ShgmVO shgmvo = shgmsvc.getOneForInfo(shgmno);

			session.setAttribute("shgmvo", shgmvo);
			String url = "/front-end/shgm/infoPage.jsp";
			RequestDispatcher nextjsp = request.getRequestDispatcher(url);
			nextjsp.forward(request, response);

		}

		if ("insert".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {

				String sellerno = request.getParameter("sellerno");
				String memberreg = "^BM\\d{5}$";
				if (sellerno.trim().length() == 0) {
					errormsgs.add("賣家編號：請勿輸入空白");
				} else if (!sellerno.trim().matches(memberreg)) {
					errormsgs.add("賣家編號：BM開頭、長度7的格式");
				}

				// 買家可為空字串
				String buyerno = request.getParameter("buyerno");

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
						price = new Integer(pricestr.trim());
					} catch (Exception e) {
						errormsgs.add("市集商品價錢：格式不正確");
					}
				}

				String intro = request.getParameter("intro");
				if (intro.trim().length() == 0)
					errormsgs.add("市集商品簡介：簡介文字不得為空");

				byte[] img = null;
				Part imgreq = request.getPart("img");
				if (imgreq.getSize() == 0) {
					errormsgs.add("市集商品圖片：市集商品圖片不得為空");
				} else if (imgreq.getSize() > 0) {
					InputStream is = imgreq.getInputStream();
					img = new byte[is.available()];
					is.read(img);
				}

				Integer upcheck = new Integer(request.getParameter("upcheck"));

				// 取貨方式可為空字串
				String take = request.getParameter("take");

				// 取貨人姓名可為空字串
				String takernm = request.getParameter("takernm");

				// 取貨人電話可為空字串
				String takerph = request.getParameter("takerph");

				// 取貨地址可為空字串
				String address = request.getParameter("address");

				Integer boxstatus = new Integer(request.getParameter("boxstatus"));

				Integer paystatus = new Integer(request.getParameter("paystatus"));

				Integer status = new Integer(request.getParameter("status"));

				// 只要買家、取貨方式、取貨人姓名、取貨人電話、取貨地址五個欄位任一個有填入資料，其他四個欄位也必須要填
				// 而且出貨、付款、訂單狀態只要不是初始值，其餘欄位就要填寫
				if (buyerno.trim().length() > 0 || take.trim().length() > 0 || takernm.trim().length() > 0
						|| takerph.trim().length() > 0 || address.trim().length() > 0 || boxstatus != 0
						|| paystatus != 0 || status != 0) {

					// 買家編號錯誤處理
					if (buyerno.trim().length() == 0) {
						errormsgs.add("買家編號：不得為空");
					} else if (!buyerno.trim().matches(memberreg)) {
						errormsgs.add("買家編號：BM開頭、長度7的格式");
					}

					// 取貨方式錯誤處理
					if (take.trim().length() == 0) {
						errormsgs.add("取貨方式：不得為空");
					} else if (take.trim().length() > 3) {
						errormsgs.add("取貨方式：長度不正確");
					}

					// 取貨人姓名錯誤處理
					if (takernm.trim().length() == 0) {
						errormsgs.add("取貨人姓名：不得為空");
					} else if (takernm.trim().length() > 3)
						errormsgs.add("取貨人姓名：長度不正確");

					// 取貨人電話錯誤處理
					String takerphreg = "^09\\d{8}$";
					if (takerph.trim().length() == 0) {
						errormsgs.add("取貨人電話：不得為空");
					} else if (!takerph.trim().matches(takerphreg))
						errormsgs.add("取貨人電話：請輸入符合格式的電話號碼");

					// 取貨地址
					if (address.trim().length() == 0)
						errormsgs.add("取貨地址：地址不得為空");
				}

				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setBuyerno(buyerno);
				shgmvo.setSellerno(sellerno);
				shgmvo.setShgmname(shgmname);
				shgmvo.setPrice(price);
				shgmvo.setIntro(intro);
				shgmvo.setImg(img);
				shgmvo.setUpcheck(upcheck);
				shgmvo.setTake(take);
				shgmvo.setTakernm(takernm);
				shgmvo.setTakerph(takerph);
				shgmvo.setAddress(address);
				shgmvo.setBoxstatus(boxstatus);
				shgmvo.setPaystatus(paystatus);
				shgmvo.setStatus(status);

				if (!errormsgs.isEmpty()) {
					// 把存有正確格式的資料轉送回新增頁面
					request.setAttribute("shgmvo", shgmvo);
					String url = "/back-end/shgm/addShgm.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				ShgmService shgmsvc = new ShgmService();
				// 上架且通過審核，更新上架時間
				if (upcheck == 1) {
					shgmsvc.addShgmCheck1(sellerno, buyerno, shgmname, price, intro, img, upcheck, take, takernm,
							takerph, address, boxstatus, paystatus, status);
					// 上架且通過審核並賣出，更新上架、售出時間
				} else if (upcheck == 1 && boxstatus == 2 && paystatus == 1 && status == 2) {
					shgmsvc.addShgmSold(sellerno, buyerno, shgmname, price, intro, img, upcheck, take, takernm, takerph,
							address, boxstatus, paystatus, status);
					MbrpfService mbrsvc = new MbrpfService();
					sellerno = shgmvo.getSellerno();
					// 取出賣家的mbrpfvo以便對points做更動
					MbrpfVO mbrpfvo = mbrsvc.getOneMbrpf(sellerno);
					// 把賣家原本的points加上販售之價格
					mbrsvc.update(sellerno, mbrpfvo.getPoints() + shgmvo.getPrice());
				} else {
					// 正常上架未通過審查，上架、售出時間為空值
					shgmsvc.addShgmNocheck(sellerno, buyerno, shgmname, price, intro, img, upcheck, take, takernm,
							takerph, address, boxstatus, paystatus, status);
				}

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

		if ("sellshgm".equals(action)) {

			HashMap<Long, String> errormap = new HashMap<Long, String>();
			request.setAttribute("errormap", errormap);

			try {

				String sellerno = request.getParameter("sellerno");

				String shgmname = request.getParameter("shgmname");
				if (shgmname.trim().length() == 0)
					errormap.put((long) 1, "名稱不得為空");

				Integer price = null;
				String pricestr = request.getParameter("price");
				if (pricestr.trim().length() == 0) {
					errormap.put((long) 2, "價錢不得為空");
				} else {
					try {
						price = new Integer(pricestr);
						if (price > 999999)
							errormap.put((long) 2, "金額超過本平台規範");
					} catch (Exception e) {
						errormap.put((long) 2, "格式不正確");
					}
				}

				String intro = request.getParameter("intro");
				if (intro.trim().length() == 0)
					errormap.put((long) 3, "簡介文字不得為空");

				byte[] img = null;
				Part imgreq = request.getPart("img");
				if (imgreq.getSize() == 0) {
					errormap.put((long) 4, "圖片不得為空");
				} else if (imgreq.getSize() > 0) {
					InputStream is = imgreq.getInputStream();
					img = new byte[is.available()];
					is.read(img);
				}

				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setSellerno(sellerno);
				shgmvo.setShgmname(shgmname);
				shgmvo.setPrice(price);
				shgmvo.setIntro(intro);
				shgmvo.setImg(img);

				if (!errormap.isEmpty()) {
					request.setAttribute("shgmsell", shgmvo);
					String url = "/front-end/shgm/sellPage.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				ShgmService shgmsvc = new ShgmService();
				shgmsvc.sellShgm(sellerno, shgmname, price, intro, img);

				String url = "/front-end/shgm/mainPage.jsp";// forward到mainPage or myshgamePage??
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put((long) 5, "無法新增您的商品");
				String url = "/front-end/shgm/sellPage.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("dealingshgm".equals(action)) {
			ShgmVO shgmvo = (ShgmVO) session.getAttribute("shgmvo");

			HashMap<Long, String> errormap = new HashMap<Long, String>();
			request.setAttribute("errormap", errormap);

			try {
				String shgmno = request.getParameter("shgmno");

				// 從會員資料取得，不需要錯誤處理
				String buyerno = request.getParameter("buyerno");

				String take = request.getParameter("take");
				if (take.trim().length() > 3)// 還需要修改
					errormap.put((long) 1, "長度不正確");
				if (take.trim().length() == 0)
					errormap.put((long) 1, "請勿輸入空白");

				String takernm = request.getParameter("takernm");
				if (takernm.trim().length() > 3)// 還需要修改
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
				if (address.trim().length() > 10) {// 還需要修改
					errormap.put((long) 4, "長度不正確");
				}
				if (address.trim().length() == 0) {
					errormap.put((long) 4, "地址不得為空");
				}

				Integer boxstatus = new Integer(request.getParameter("boxstatus"));

				Integer paystatus = new Integer(request.getParameter("paystatus"));

				Integer status = new Integer(request.getParameter("status"));

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
					String url = "/front-end/shgm/buyPage.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				ShgmService shgmsvc = new ShgmService();
				shgmsvc.dealingshgm(shgmno, buyerno, take, takernm, takerph, address, boxstatus, paystatus, status);

				// 已上架的市集商品，同時也已送達、已付款、已完成，即是訂單完成，可以增加賣家的點數了
				if (boxstatus == 2 && paystatus == 1 && status == 2) {
					MbrpfService mbrsvc = new MbrpfService();
					String sellerno = shgmvo.getSellerno();
					// 取出賣家的mbrpfvo以便對points做更動
					MbrpfVO mbrpfvo = mbrsvc.getOneMbrpf(sellerno);
					// 把賣家原本的points加上販售之價格
					mbrsvc.update(sellerno, mbrpfvo.getPoints() + shgmvo.getPrice());
					// 資料庫更新售出時間
					shgmsvc.soldtimeCT(shgmno);
				}

				String url = "/front-end/shgm/mainPage.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put((long) 5, "無法購買此商品");
				String url = "/front-end/shgm/buyPage.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("delete".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			try {
				String shgmno = request.getParameter("shgmno");

				ShgmService shgmsvc = new ShgmService();
				shgmsvc.deleteShgm(shgmno);

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

				ShgmService shgmsvc = new ShgmService();
				ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);

				request.setAttribute("shgmvo", shgmvo);
				String url = "/back-end/shgm/updateShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);

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
				String memberreg = "^BM\\d{5}$";
				if (sellerno.trim().length() == 0) {
					errormsgs.add("賣家編號：請勿輸入空白");
				} else if (!sellerno.trim().matches(memberreg)) {
					errormsgs.add("賣家編號：BM開頭、長度7的格式");
				}

				// 買家可為空字串
				String buyerno = request.getParameter("buyerno");

				String shgmname = request.getParameter("shgmname");
				if (shgmname.trim().length() == 0)
					errormsgs.add("市集商品名稱：請勿輸入空白");

				Integer price = null;
				String pricestr = request.getParameter("price");
				if (pricestr.trim().length() == 0) {
					errormsgs.add("市集商品價錢：價錢不得為空");
				} else {
					try {
						price = new Integer(pricestr.trim());
						if (pricestr.trim().length() > 6)
							errormsgs.add("市集商品價錢：長度超過本平台規範");
					} catch (Exception e) {
						errormsgs.add("市集商品價錢：格式不正確");
					}
				}
				String intro = request.getParameter("intro");
				if (intro.trim().length() == 0)
					errormsgs.add("市集商品簡介：簡介文字不得為空");

				byte[] img = null;
				Part imgreq = request.getPart("img");
				if (imgreq.getSize() > 0) {
					InputStream is = imgreq.getInputStream();
					img = new byte[is.available()];
					is.read(img);
				} else {
					ShgmService shgmsvc = new ShgmService();
					ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);
					img = shgmvo.getImg();
				}

				Integer upcheck = new Integer(request.getParameter("upcheck"));

				// 取貨方式可為空字串
				String take = request.getParameter("take");

				// 取貨人姓名可為空字串
				String takernm = request.getParameter("takernm");

				// 取貨人電話可為空字串
				String takerph = request.getParameter("takerph");

				// 取貨地址可為空字串
				String address = request.getParameter("address");

				Integer boxstatus = new Integer(request.getParameter("boxstatus"));

				Integer paystatus = new Integer(request.getParameter("paystatus"));

				Integer status = new Integer(request.getParameter("status"));

				// 只要買家、取貨方式、取貨人姓名、取貨人電話、取貨地址五個欄位任一個有填入資料，其他四個欄位也必須要填。
				// 而且出貨、付款、訂單狀態只要不是初始狀態，其餘欄位就要填寫
				if (buyerno.trim().length() > 0 || take.trim().length() > 0 || takernm.trim().length() > 0
						|| takerph.trim().length() > 0 || address.trim().length() > 0 || boxstatus != 0
						|| paystatus != 0 || status != 0) {

					// 買家編號錯誤處理
					if (buyerno.trim().length() == 0) {
						errormsgs.add("買家編號：不得為空");
					} else if (!buyerno.trim().matches(memberreg)) {
						errormsgs.add("買家編號：BM開頭、長度7的格式");
					}

					// 取貨方式錯誤處理
					if (take.trim().length() == 0) {
						errormsgs.add("取貨方式：不得為空");
					} else if (take.trim().length() > 3) {
						errormsgs.add("取貨方式：長度不正確");
					}

					// 取貨人姓名錯誤處理
					if (takernm.trim().length() == 0) {
						errormsgs.add("取貨人姓名：不得為空");
					} else if (takernm.trim().length() > 3)
						errormsgs.add("取貨人姓名：長度不正確");

					// 取貨人電話錯誤處理
					String takerphreg = "^09\\d{8}$";
					if (takerph.trim().length() == 0) {
						errormsgs.add("取貨人電話：不得為空");
					} else if (!takerph.trim().matches(takerphreg))
						errormsgs.add("取貨人電話：請輸入符合格式的電話號碼");

					// 取貨地址
					if (address.trim().length() == 0)
						errormsgs.add("取貨地址：地址不得為空");
				}
				
				ShgmService shgmsvc = new ShgmService();
				ShgmVO shgm = shgmsvc.getOneShgm(shgmno);
				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(shgmno);
				shgmvo.setSellerno(sellerno);
				shgmvo.setBuyerno(buyerno);
				shgmvo.setShgmname(shgmname);
				shgmvo.setPrice(price);
				shgmvo.setIntro(intro);
				shgmvo.setImg(img);
				shgmvo.setUpcheck(upcheck);
				shgmvo.setUptime(shgm.getUptime());
				shgmvo.setTake(take);
				shgmvo.setTakernm(takernm);
				shgmvo.setTakerph(takerph);
				shgmvo.setAddress(address);
				shgmvo.setBoxstatus(boxstatus);
				shgmvo.setPaystatus(paystatus);
				shgmvo.setStatus(status);
				shgmvo.setSoldtime(shgm.getSoldtime());

				if (!errormsgs.isEmpty()) {
					request.setAttribute("shgmvo", shgmvo);
					String url = "/back-end/shgm/updateShgm.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				shgmsvc.updateShgm(shgmno, sellerno, buyerno, shgmname, price, intro, img, upcheck, take, takernm,
						takerph, address, boxstatus, paystatus, status);

				// 已上架的市集商品，同時修改成已送達、已付款、已完成，即是訂單完成，可以增加賣家的點數了
				if (upcheck == 1 && boxstatus == 2 && paystatus == 1 && status == 2) {
					MbrpfService mbrsvc = new MbrpfService();
					sellerno = shgmvo.getSellerno();
					// 取出賣家的mbrpfvo以便對points做更動
					MbrpfVO mbrpfvo = mbrsvc.getOneMbrpf(sellerno);
					// 把賣家原本的points加上販售之價格
					mbrsvc.update(sellerno, mbrpfvo.getPoints() + shgmvo.getPrice());
					// 資料庫更新售出時間
					if(shgm.getUptime() == null)
						shgmsvc.uptimeCT(shgmno);
					shgmsvc.soldtimeCT(shgmno);
				} else if (upcheck == 1) {
					// 修改後，已通過審查，同時更新上架時間
					shgmsvc.uptimeCT(shgmno);
					shgmsvc.soldtimeNU(shgmno);
				} else {
					// 修改後，未通過審查，上架、審查時間為空值
					shgmsvc.uptimeNU(shgmno);
					shgmsvc.soldtimeNU(shgmno);
				}

				String url = "/back-end/shgm/listAllShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormsgs.add("無法修改資料" + e.getMessage());
				String url = "/back-end/shgm/shgm_select_page.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}
	}
}