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

import org.json.JSONObject;

import com.mbrpf.model.MbrpfService;
import com.mbrpf.model.MbrpfVO;

import com.shgm.model.ShgmService;
import com.shgm.model.ShgmVO;
import com.shgmrp.model.ShgmrpService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 7 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ShgmServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		String action = request.getParameter("action");

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
				String shgmno = request.getParameter("shgmno");
				String strreg = "^CA\\d{5}$";
				if ((shgmno.trim()).length() == 0) {
					errormsgs.add("您未輸入市集商品編號");
				} else if (!shgmno.trim().matches(strreg)) {
					errormsgs.add("請依照市集商品編號格式輸入");
				}

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

			// 把當前要瀏覽的市集商品存入session
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
				if (shgmname.trim().length() > 10)
					errormsgs.add("市集商品名稱：名稱過長");

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
				String city = request.getParameter("city");
				String area = request.getParameter("area");
				String ads = request.getParameter("ads");
				String address = request.getParameter("address");

				Integer boxstatus = new Integer(request.getParameter("boxstatus"));

				Integer paystatus = new Integer(request.getParameter("paystatus"));

				Integer status = new Integer(request.getParameter("status"));

				// 只要買家、取貨方式、取貨人姓名、取貨人電話、取貨地址五個欄位任一個有填入資料，其他四個欄位也必須要填
				// 而且出貨、付款、訂單狀態只要不是初始值，其餘欄位就要填寫
				if (buyerno.trim().length() > 0 || take.trim().length() > 0 || takernm.trim().length() > 0
						|| takerph.trim().length() > 0 || ads.trim().length() > 0 || boxstatus != 0 || paystatus != 0
						|| status != 0) {

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
					if (ads.equals(address))
						errormsgs.add("取貨地址：請選擇鄉鎮縣市");
					if (ads.trim().length() == 0)
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
					HashMap<String, String> hashmap = new HashMap<String, String>();
					hashmap.put("city", city);
					hashmap.put("area", area);
					hashmap.put("ads", ads);
					request.setAttribute("cityarea", hashmap);
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
					// 上架且通過審核並賣出，更新上架、售出時間，即是訂單完成，可以扣掉買家點數、增加賣家的點數了
				} else if (upcheck == 1 && boxstatus == 2 && paystatus == 1 && status == 2) {
					shgmsvc.addShgmSold(sellerno, buyerno, shgmname, price, intro, img, upcheck, take, takernm, takerph,
							address, boxstatus, paystatus, status);
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
				if (shgmname.trim().length() > 10)
					errormap.put((long) 1, "名稱過長");

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

		if ("oneForSellerUpdate".equals(action)) {

			HashMap<Long, String> errormap = new HashMap<Long, String>();
			request.setAttribute("errormap", errormap);

			try {

				String shgmno = request.getParameter("shgmno");
				System.out.println(shgmno);

				ShgmService shgmsvc = new ShgmService();
				ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);
				System.out.println(shgmvo.getShgmno());

				request.setAttribute("shgmvo", shgmvo);

				String url = "/front-end/shgm/sellerUpdate.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put((long) 5, "無法新增您的商品");
				String url = "/front-end/shgm/sellerUpdate.jsp";// 可能要回到sellerPage.jsp
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("sellerUpdate".equals(action)) {

			HashMap<Long, String> errormap = new HashMap<Long, String>();
			request.setAttribute("errormap", errormap);

			try {
				String shgmno = request.getParameter("shgmno");

				String sellerno = request.getParameter("sellerno");

				String shgmname = request.getParameter("shgmname");
				if (shgmname.trim().length() == 0)
					errormap.put((long) 1, "名稱不得為空");
				if (shgmname.trim().length() > 10)
					errormap.put((long) 1, "名稱過長");

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
				if (imgreq.getSize() > 0) {
					InputStream is = imgreq.getInputStream();
					img = new byte[is.available()];
					is.read(img);
				} else {
					ShgmService shgmsvc = new ShgmService();
					ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);
					img = shgmvo.getImg();
				}

				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(shgmno);
				shgmvo.setSellerno(sellerno);
				shgmvo.setShgmname(shgmname);
				shgmvo.setPrice(price);
				shgmvo.setIntro(intro);
				shgmvo.setImg(img);

				if (!errormap.isEmpty()) {
					request.setAttribute("shgmvo", shgmvo);
					String url = "/front-end/shgm/sellerUpdate.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				ShgmService shgmsvc = new ShgmService();
				shgmsvc.sellerUpdate(shgmno, shgmname, price, intro, img);

				String url = "/front-end/shgm/sellerPage.jsp";// 回到原本的頁面
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put((long) 5, "無法新增您的商品");
				String url = "/front-end/shgm/sellerUpdate.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("dealingshgm".equals(action)) {

			// getOneToInfo存入的市集商品在這邊取出
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

				String city = request.getParameter("city");
				String area = request.getParameter("area");
				String ads = request.getParameter("ads");
				String address = request.getParameter("address");
				if (ads.trim().length() > 10) {// 還需要修改
					errormap.put((long) 4, "長度不正確");
				}
				if (ads.trim().length() == 0) {
					errormap.put((long) 4, "地址不得為空");
				}
				if (ads.equals(address)) {
					errormap.put((long) 4, "請選擇縣市、鄉鎮");
				}

				Integer boxstatus = null;

				Integer paystatus = null;

				Integer status = null;

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
					HashMap<String, String> hashmap = new HashMap<String, String>();
					hashmap.put("city", city);
					hashmap.put("area", area);
					hashmap.put("ads", ads);
					request.setAttribute("cityarea", hashmap);
					String url = "/front-end/shgm/buyPage.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				ShgmService shgmsvc = new ShgmService();
				shgmsvc.dealingshgm(shgmno, buyerno, take, takernm, takerph, address, 0, 1, 1);

				MbrpfService mbrsvc = new MbrpfService();
				// 取出買家的mbrpfvo以便對points做更動
				MbrpfVO mbrpfvo = mbrsvc.getOneMbrpf(buyerno);
				// 把買家原本的points扣掉價格
				mbrsvc.update(buyerno, mbrpfvo.getPoints() - shgmvo.getPrice());

				String success = "success";
				request.setAttribute("buysuccess", success);

				String url = "/front-end/shgm/infoPage.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put((long) 5, "無法購買此商品");
				String url = "/front-end/shgm/buyPage.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("buyerUpdate".equals(action)) {

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

				String city = request.getParameter("city");
				String area = request.getParameter("area");
				String ads = request.getParameter("ads");
				String address = request.getParameter("address");
				if (ads.trim().length() > 10) {// 還需要修改
					errormap.put((long) 4, "長度不正確");
				}
				if (ads.trim().length() == 0) {
					errormap.put((long) 4, "地址不得為空");
				}
				if (ads.equals(address)) {
					errormap.put((long) 4, "請選擇縣市、鄉鎮");
				}

				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(shgmno);
				shgmvo.setBuyerno(buyerno);
				shgmvo.setTake(take);
				shgmvo.setTakernm(takernm);
				shgmvo.setTakerph(takerph);
				shgmvo.setAddress(address);

				if (!errormap.isEmpty()) {
					HashMap<String, String> hashmap = new HashMap<String, String>();
					hashmap.put("city", city);
					hashmap.put("area", area);
					hashmap.put("ads", ads);
					request.setAttribute("cityarea", hashmap);
					request.setAttribute("buyerUpdate", shgmvo);
					String url = "/front-end/shgm/buyerUpdate.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				ShgmService shgmsvc = new ShgmService();
				shgmvo = shgmsvc.getOneShgm(shgmno);
				shgmsvc.dealingshgm(shgmno, buyerno, take, takernm, takerph, address, shgmvo.getBoxstatus(),
						shgmvo.getPaystatus(), shgmvo.getStatus());

				String success = "success";
				request.setAttribute("updateSuccess", success);

				String url = "/front-end/shgm/myShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put((long) 5, "無法修改此商品");
				String url = "/front-end/shgm/buyerUpdate.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("statusUpdate".equals(action)) {
			response.setContentType("text/html; charset=utf-8");
			Writer out = response.getWriter();
			JSONObject jsonobj = new JSONObject();

			String shgmno = request.getParameter("shgmno");
			ShgmService shgmsvc = new ShgmService();
			ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);
			try {
				// 改變上架狀態
				if (request.getParameter("upcheck") != null) {

					Integer upcheck = new Integer(request.getParameter("upcheck"));
					System.out.println("更新前：" + upcheck);
					// 待上架、上架中選擇自行下架，改成下架中狀態
					if (upcheck == 0 || upcheck == 1) {
						// 先更新
						shgmsvc.upcheckUpdate(2, shgmno);

						String detail = null;
						ShgmrpService shgmrpsvc = new ShgmrpService();
						// 判斷是否有被檢舉的內容
						if (shgmrpsvc.getOnerpByShgmno(shgmno) != null) {
							detail = shgmrpsvc.getOnerpByShgmno(shgmno).getDetail();
						} else {
							detail = "自行下架";
						}

						// 把jquery動態改變頁面需要的資料放入json
						jsonobj.put("shgmno", shgmvo.getShgmno());
						jsonobj.put("shgmname", shgmvo.getShgmname());
						jsonobj.put("detail", detail);
						jsonobj.put("upcheck", 2);

					}
					// 重新申請上架，下架中改成待上架狀態
					if (upcheck == 2) {
						shgmsvc.upcheckUpdate(0, shgmno);

						// 未審核，上架時間更新為空值
						shgmsvc.uptimeNU(shgmno);

						jsonobj.put("shgmno", shgmvo.getShgmno());
						jsonobj.put("shgmname", shgmvo.getShgmname());
						jsonobj.put("price", shgmvo.getPrice());
						// upcheck的值在ajax作流程控制用的
						jsonobj.put("upcheck", 0);
					}
				}
				// 改變出貨狀態
				if (request.getParameter("boxstatus") != null) {

					Integer boxstatus = new Integer(request.getParameter("boxstatus"));
					System.out.println("更新前：" + boxstatus);

					jsonobj.put("shgmno", shgmvo.getShgmno());
					jsonobj.put("shgmname", shgmvo.getShgmname());
					jsonobj.put("takernm", shgmvo.getTakernm());
					jsonobj.put("takerph", shgmvo.getTakerph());
					jsonobj.put("address", shgmvo.getAddress());

					// 待出貨選擇進行出貨，改成出貨中
					if (boxstatus == 0) {
						shgmsvc.boxstatusUpdate(1, shgmno);
						jsonobj.put("boxstatus", 1);
					}
					// 出貨中選擇送達商品，改成已送達
					if (boxstatus == 1) {
						shgmsvc.boxstatusUpdate(2, shgmno);
						jsonobj.put("boxstatus", 2);
					}
				}
				// 訂單狀態
				if (request.getParameter("status") != null) {

					Integer status = new Integer(request.getParameter("status"));
					// 將買家資料清空，回到待上架狀態
					if (status == 3) {
						shgmsvc.updateShgm(shgmno, shgmvo.getSellerno(), null, shgmvo.getShgmname(), shgmvo.getPrice(),
								shgmvo.getIntro(), shgmvo.getImg(), 0, null, null, null, null, 0, 0, 0);
						System.out.println("update shgm to upcheck0 status");

						jsonobj.put("shgmno", shgmno);
						jsonobj.put("shgmname", shgmvo.getShgmname());
						jsonobj.put("price", shgmvo.getPrice());
					}
					// 已送達，買家確認收貨，下訂改成完成
					if (status == 2) {

						// 增加賣家點數
						MbrpfService mbrsvc = new MbrpfService();
						String sellerno = shgmvo.getSellerno();
						MbrpfVO mbrpfvo = mbrsvc.getOneMbrpf(sellerno);
						mbrsvc.update(sellerno, mbrpfvo.getPoints() + shgmvo.getPrice());

						java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Timestamp uptimeCT = shgmsvc.getOneShgm(shgmno).getUptime();
						String uptime = df.format(uptimeCT);
						Timestamp soldtimeCT = shgmsvc.soldtimeCT(shgmno);
						String soldtime = df.format(soldtimeCT);
						shgmsvc.statusUpdate(2, shgmno);
						System.out.println(soldtimeCT);

						jsonobj.put("shgmno", shgmno);
						jsonobj.put("shgmname", shgmvo.getShgmname());
						jsonobj.put("price", shgmvo.getPrice());
						jsonobj.put("uptime", uptime);
						jsonobj.put("soldtime", soldtime);
					}
					// 不論訂單狀態，買家取消訂單
					if (status == 8) {
						shgmsvc.statusUpdate(3, shgmno);

						// 退款給買家
						MbrpfService mbrsvc = new MbrpfService();
						String buyerno = shgmvo.getBuyerno();
						MbrpfVO mbrpfvo = mbrsvc.getOneMbrpf(buyerno);
						mbrsvc.update(buyerno, mbrpfvo.getPoints() + shgmvo.getPrice());

						jsonobj.put("shgmno", shgmno);
						jsonobj.put("shgmname", shgmvo.getShgmname());
						jsonobj.put("price", shgmvo.getPrice());
					}
				}

				System.out.println(jsonobj.toString());
				out.write(jsonobj.toString());

			} catch (org.json.JSONException e) {
				System.out.println(e.getMessage());
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

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);

			String requestURL = request.getParameter("requestURL");
			request.setAttribute("requestURL", requestURL);
			String whichPage = request.getParameter("whichPage");
			request.setAttribute("whichPage", whichPage);
			System.out.println(whichPage);
			String url = null;
			try {
				String shgmno = request.getParameter("shgmno");

				String city = null;
				String area = null;
				String ads = null;
				String address = null;

				ShgmService shgmsvc = new ShgmService();
				ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);
				// 將address分割為city、area、ads
				if (shgmvo.getAddress() != null) {
					address = shgmvo.getAddress();
					String[] citylevel = { "縣", "市", "島" };
					String[] arealevel = { "鄉", "鎮", "島", "區", "市" };
					for (String clevel : citylevel) {
						if (address.contains(clevel)) {
							city = address.substring(0, address.indexOf(clevel) + 1);
							address = address.substring(address.indexOf(clevel) + 1, address.length());
							for (String alevel : arealevel) {
								if (address.contains(alevel)) {
									area = address.substring(0, address.indexOf(alevel) + 1);
									ads = address.substring(address.indexOf(alevel) + 1, address.length());
								}
							}
						}
					}
				}
				HashMap<String, String> hashmap = new HashMap<String, String>();
				hashmap.put("city", city);
				hashmap.put("area", area);
				hashmap.put("ads", ads);
				request.setAttribute("cityarea", hashmap);

				request.setAttribute("shgmvo", shgmvo);
				//來自後台的修改請求
				if (requestURL.equals("/back-end/shgm/listAllShgm.jsp")
						|| requestURL.equals("/back-end/shgm/shgm_select_page.jsp")) {
					url = "/back-end/shgm/updateShgm.jsp";
					//來自前台買家的修改請求
				} else if(requestURL.equals("/front-end/shgm/myShgm.jsp"))
					url = "/front-end/shgm/buyerUpdate.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);

			} catch (Exception e) {
				errormsgs.add("無法取得要修改的資料:" + e.getMessage());
				if (requestURL.equals("/back-end/shgm/listAllShgm.jsp")
						|| requestURL.equals("/back-end/shgm/shgm_select_page.jsp")) {
					url = "/back-end/shgm/shgm_select_page.jsp";
				} else if(requestURL.equals("/front-end/shgm/myShgm.jsp"))
					url = "/front-end/shgm/myShgm.jsp";
				RequestDispatcher failureView = request.getRequestDispatcher(url);
				failureView.forward(request, response);
			}
		}

		if ("update".equals(action)) {

			List<String> errormsgs = new LinkedList<String>();
			request.setAttribute("errormsgs", errormsgs);
			
			String requestURL = request.getParameter("requestURL");
			request.setAttribute("requestURL", requestURL);
			String whichPage = request.getParameter("whichPage");
			request.setAttribute("whichPage", whichPage);

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
				if (shgmname.trim().length() > 10)
					errormsgs.add("市集商品名稱：名稱過長");

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
				String city = request.getParameter("city");
				String area = request.getParameter("area");
				String ads = request.getParameter("ads");
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
					//
					// 取貨地址
					if (ads.equals(address))
						errormsgs.add("取貨地址：請選擇鄉鎮縣市");
					if (address.trim().length() == 0)
						errormsgs.add("取貨地址：地址不得為空");
				}

				// 後臺要改變未審核商品的狀態，必須先審核上架或審核下架，確保有審核行為
				if (upcheck < 1 && (boxstatus != 0 || paystatus != 0 || status != 0)) {
					errormsgs.add("審核狀態：欲改變狀態，先審核此市集商品");
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
					HashMap<String, String> hashmap = new HashMap<String, String>();
					hashmap.put("city", city);
					hashmap.put("area", area);
					hashmap.put("ads", ads);
					request.setAttribute("cityarea", hashmap);
					request.setAttribute("shgmvo", shgmvo);
					String url = "/back-end/shgm/updateShgm.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				if (status == 3) {
					upcheck = 0;
					boxstatus = 0;
					paystatus = 0;
					status = 0;
					shgmsvc.updateShgm(shgmno, sellerno, null, shgmname, price, intro, img, 0, null, null, null, null,
							0, 0, 0);
				} else {
					shgmsvc.updateShgm(shgmno, sellerno, buyerno, shgmname, price, intro, img, upcheck, take, takernm,
							takerph, address, boxstatus, paystatus, status);
				}

				if (upcheck == 0) {
					shgmsvc.uptimeNU(shgmno);
					shgmsvc.soldtimeNU(shgmno);
				}
				if (upcheck == 1) {
					// 修改後，已通過審查，被檢舉的市集商品取消檢舉
					ShgmrpService shgmrpsvc = new ShgmrpService();
					if (shgmrpsvc.getOnerpByShgmno(shgmno) != null) {
						String shgmrpno = shgmrpsvc.getOnerpByShgmno(shgmno).getShgmrpno();
						shgmrpsvc.updateStatus(2, shgmrpno);
					}
					// 上架的市集商品，同時修改成已送達、已付款、已完成，即是訂單完成
					if (boxstatus == 2 && paystatus == 1 && status == 2) {
						// 資料庫更新上架時間、售出時間，如果之前就是上架中，沿用上架時間
						if (shgm.getUptime() == null)
							shgmsvc.uptimeCT(shgmno);
						shgmsvc.soldtimeCT(shgmno);
					} else {
						// 上架的市集商品，更新上架時間
						shgmsvc.uptimeCT(shgmno);
						shgmsvc.soldtimeNU(shgmno);
					}
				}
				if (upcheck == 2) {
					;// do nothing
				}

				String url = null;
				if(whichPage != null) {
					url= requestURL+"?whichPage="+whichPage;
				} else {
					url = "/back-end/shgm/listAllShgm.jsp";
				}
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