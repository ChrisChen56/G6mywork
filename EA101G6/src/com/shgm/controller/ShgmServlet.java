package com.shgm.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

import connectionpool.WsMessage;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 7 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ShgmServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		String action = request.getParameter("action");

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
				ShgmVO shgmvo = shgmsvc.getOneForInfo(shgmno);
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
				if (shgmname.trim().length() > 21)
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
				if (buyerno.trim().length() > 0 || take != null || takernm.trim().length() > 0
						|| takerph.trim().length() > 0 || ads.trim().length() > 0 || boxstatus != 0 || paystatus != 0
						|| status != 0) {

					// 買家編號錯誤處理
					if (buyerno.trim().length() == 0) {
						errormsgs.add("買家編號：不得為空");
					} else if (!buyerno.trim().matches(memberreg)) {
						errormsgs.add("買家編號：BM開頭、長度7的格式");
					}

					// 取貨方式錯誤處理
					if (take == null)
						errormsgs.add("取貨方式：請選擇取貨方式");

					// 取貨人姓名錯誤處理
					String takernmreg = "^[(\u4e00-\u9fa5)]{1,10}$";
					if (takernm.trim().length() == 0)
						errormsgs.add("取貨人姓名：請勿輸入空白");
					if (!takernm.trim().matches(takernmreg))
						errormsgs.add("取貨人姓名：只能是中文，且長度必須在1到10之間");

					// 取貨人電話錯誤處理
					String takerphreg = "^09\\d{8}$";
					if (takerph.trim().length() == 0) {
						errormsgs.add("取貨人電話：不得為空");
					} else if (!takerph.trim().matches(takerphreg))
						errormsgs.add("取貨人電話：請輸入符合格式的電話號碼");

					// 取貨地址
					String adsReg = "^[(\u4e00-\u9fa5)(\\w)]{5,40}$";
					if (ads.equals(address))
						errormsgs.add("取貨地址：請選擇鄉鎮縣市");
					if (ads.trim().length() == 0)
						errormsgs.add("取貨地址：地址不得為空");
					if (!ads.trim().matches(adsReg))
						errormsgs.add("取貨地址：只能是中、英文字母、數字、底線，且長度必須在5到40之間");
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
				shgmsvc.addShgm(sellerno, buyerno, shgmname, price, intro, img, upcheck, shgmvo.getUptime(), take,
						takernm, takerph, address, boxstatus, paystatus, status, shgmvo.getSoldtime());

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

			HashMap<String, String> errormap = new HashMap<String, String>();
			request.setAttribute("errormap", errormap);

			try {

				String sellerno = request.getParameter("sellerno");

				String shgmname = request.getParameter("shgmname");
				if (shgmname.trim().length() == 0)
					errormap.put("shgmname", "名稱不得為空");
				if (shgmname.trim().length() > 21)
					errormap.put("shgmname", "名稱過長");

				Integer price = null;
				String pricestr = request.getParameter("price");
				if (pricestr.trim().length() == 0) {
					errormap.put("price", "價錢不得為空");
				} else {
					try {
						price = new Integer(pricestr);
						if (price > 999999)
							errormap.put("price", "金額超過本平台規範");
					} catch (Exception e) {
						errormap.put("price", "格式不正確");
					}
				}

				String intro = request.getParameter("intro");
				if (intro.trim().length() == 0)
					errormap.put("intro", "簡介文字不得為空");

				byte[] img = null;
				Part imgreq = request.getPart("img");
				if (imgreq.getSize() == 0) {
					errormap.put("img", "圖片不得為空");
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
				shgmsvc.addShgm(sellerno, null, shgmname, price, intro, img, 0, null, null, null, null, null, 0, 0, 0,
						null);

				String url = "/front-end/shgm/mainPage.jsp";// forward到mainPage or myshgamePage??
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put("error", "無法新增您的商品");
				String url = "/front-end/shgm/sellPage.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("oneForSellerUpdate".equals(action)) {

			HashMap<String, String> errormap = new HashMap<String, String>();
			request.setAttribute("errormap", errormap);

			try {

				String shgmno = request.getParameter("shgmno");

				ShgmService shgmsvc = new ShgmService();
				ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);

				request.setAttribute("shgmvo", shgmvo);

				String url = "/front-end/shgm/sellerUpdate.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put("error", "無法取得您的商品");
				String url = "/front-end/shgm/sellerPage.jsp";// sellerPage.jsp的錯誤處理？
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("sellerUpdate".equals(action)) {

			HashMap<String, String> errormap = new HashMap<String, String>();
			request.setAttribute("errormap", errormap);

			String shgmno = request.getParameter("shgmno");

			ShgmService shgmsvc = new ShgmService();
			ShgmVO shgmorg = shgmsvc.getOneShgm(shgmno);
			try {

				String sellerno = request.getParameter("sellerno");

				String shgmname = request.getParameter("shgmname");
				if (shgmname.trim().length() == 0)
					errormap.put("shgmname", "名稱不得為空");
				if (shgmname.trim().length() > 21)
					errormap.put("shgmname", "名稱過長");

				Integer price = null;
				String pricestr = request.getParameter("price");
				if (pricestr.trim().length() == 0) {
					errormap.put("price", "價錢不得為空");
				} else {
					try {
						price = new Integer(pricestr);
						if (price > 999999)
							errormap.put("price", "金額超過本平台規範");
					} catch (Exception e) {
						errormap.put("price", "格式不正確");
					}
				}

				String intro = request.getParameter("intro");
				if (intro.trim().length() == 0)
					errormap.put("intro", "簡介文字不得為空");

				byte[] img = null;
				Part imgreq = request.getPart("img");
				if (imgreq.getSize() > 0) {
					InputStream is = imgreq.getInputStream();
					img = new byte[is.available()];
					is.read(img);
				} else {
					img = shgmorg.getImg();
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

				shgmsvc.updateShgm(shgmno, sellerno, shgmorg.getBuyerno(), shgmname, price, intro, img,
						shgmorg.getUpcheck(), shgmorg.getUptime(), shgmorg.getTake(), shgmorg.getTakernm(),
						shgmorg.getTakerph(), shgmorg.getAddress(), shgmorg.getBoxstatus(), shgmorg.getPaystatus(),
						shgmorg.getStatus(), shgmorg.getSoldtime());

				String url = "/front-end/shgm/sellerPage.jsp";// 回到原本的頁面
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put("error", "無法修改您的商品");
				String url = "/front-end/shgm/sellerUpdate.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("getOneForMoreInfo".equals(action)) {

			String shgmno = request.getParameter("shgmno");

			String requestURL = request.getParameter("requestURL");

			ShgmService shgmsvc = new ShgmService();
			ShgmVO shgmvo = shgmsvc.getOneForInfo(shgmno);

			session.setAttribute("infoshgm", shgmvo);

			MbrpfService mbrpfsvc = new MbrpfService();
			MbrpfVO mbrpfvo = mbrpfsvc.getOneMbrpf(shgmvo.getSellerno());
			session.setAttribute("sellerinfo", mbrpfvo);

			String url = null;
			if (requestURL == null) {
				url = "/front-end/shgm/infoPage.jsp";
			} else if (requestURL.equals("/front-end/shgm/infoPage.jsp")) {
				url = "/front-end/shgm/buyPage.jsp";
			}
			RequestDispatcher nextjsp = request.getRequestDispatcher(url);
			nextjsp.forward(request, response);
		}

		if ("dealingshgm".equals(action)) {

			HashMap<String, String> errormap = new HashMap<String, String>();
			request.setAttribute("errormap", errormap);

			ShgmVO shgmvo = (ShgmVO) session.getAttribute("infoshgm");

			try {
				String shgmno = request.getParameter("shgmno");

				// 從會員資料取得，不需要錯誤處理
				String buyerno = request.getParameter("buyerno");
				if (buyerno.equals(shgmvo.getSellerno()))
					errormap.put("error", "無法購買自己的市集商品");

				String take = request.getParameter("take");
				if (take == null)
					errormap.put("take", "請選擇取貨方式");

				String takernm = request.getParameter("takernm");
				String takernmreg = "^[(\u4e00-\u9fa5)]{1,10}$";
				if (takernm.trim().length() == 0) {
					errormap.put("takernm", "請勿輸入空白");
				} else if (!takernm.trim().matches(takernmreg)) {
					errormap.put("takernm", "只能是中文，且長度必須在1到10之間");
				}

				String takerph = request.getParameter("takerph");
				String takerphreg = "^09\\d{8}$";
				if (takerph.trim().length() == 0) {
					errormap.put("takerph", "請勿輸入空白");
				} else if (!takerph.trim().matches(takerphreg)) {
					errormap.put("takerph", "請輸入符合格式的電話號碼");
				}

				String city = request.getParameter("city");
				String area = request.getParameter("area");
				String ads = request.getParameter("ads");
				String adsReg = "^[(\u4e00-\u9fa5)(\\w)]{5,40}$";
				String address = request.getParameter("address");
				if (!ads.trim().matches(adsReg))
					errormap.put("ads", "只能是中、英文字母、數字、底線，且長度必須在5到40之間");
				if (ads.trim().length() == 0)
					errormap.put("ads", "地址不得為空");
				if (ads.equals(address))
					errormap.put("ads", "請選擇縣市、鄉鎮");

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
					String url = "/front-end/shgm/buyPage.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				MbrpfService mbrpfsvc = new MbrpfService();
				// 取出買家的mbrpfvo以便對points做更動
				MbrpfVO mbrpfVO = mbrpfsvc.getOneMbrpf(buyerno);
				// 把買家原本的points扣掉價格
				mbrpfVO.setPoints(mbrpfVO.getPoints() - shgmvo.getPrice());

				ShgmService shgmsvc = new ShgmService();
				shgmvo = shgmsvc.updateShgm(shgmno, shgmvo.getSellerno(), buyerno, shgmvo.getShgmname(),
						shgmvo.getPrice(), shgmvo.getIntro(), shgmvo.getImg(), shgmvo.getUpcheck(), shgmvo.getUptime(),
						take, takernm, takerph, address, 0, 1, 1, shgmvo.getSoldtime(), mbrpfVO);

				// 回到infoPage的JSTL判斷用的
				shgmvo.setPaystatus(1);
				session.setAttribute("infoshgm", shgmvo);

				request.setAttribute("buysuccess", "success");

				String url = "/front-end/shgm/infoPage.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put("error", "無法購買此商品");
				String url = "/front-end/shgm/buyPage.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("buyerUpdate".equals(action)) {

			HashMap<String, String> errormap = new HashMap<String, String>();
			request.setAttribute("errormap", errormap);

			try {
				String shgmno = request.getParameter("shgmno");

				// 從會員資料取得，不需要錯誤處理
				String buyerno = request.getParameter("buyerno");

				String take = request.getParameter("take");
				if (take == null)
					errormap.put("take", "請選擇取貨方式");

				String takernm = request.getParameter("takernm");
				String takernmreg = "^[(\u4e00-\u9fa5)]{1,10}$";
				if (takernm.trim().length() == 0)
					errormap.put("takernm", "請勿輸入空白");
				if (!takernm.trim().matches(takernmreg))
					errormap.put("takernm", "只能是中文，且長度必須在1到10之間");

				String takerph = request.getParameter("takerph");
				String takerphreg = "^09\\d{8}$";
				if (takerph.trim().length() == 0) {
					errormap.put("takerph", "請勿輸入空白");
				} else if (!takerph.trim().matches(takerphreg)) {
					errormap.put("takerph", "請輸入符合格式的電話號碼");
				}

				String city = request.getParameter("city");
				String area = request.getParameter("area");
				String ads = request.getParameter("ads");
				String adsReg = "^[(\u4e00-\u9fa5)(\\w)]{5,40}$";
				String address = request.getParameter("address");
				if (!ads.trim().matches(adsReg))
					errormap.put("ads", "只能是中、英文字母、數字、底線，且長度必須在5到40之間");
				if (ads.trim().length() == 0)
					errormap.put("ads", "地址不得為空");
				if (ads.equals(address))
					errormap.put("ads", "請選擇縣市、鄉鎮");

				ShgmService shgmsvc = new ShgmService();
				ShgmVO shgmorg = shgmsvc.getOneShgm(shgmno);

				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(shgmno);
				shgmvo.setBuyerno(buyerno);
				shgmvo.setShgmname(shgmorg.getShgmname());
				shgmvo.setPrice(shgmorg.getPrice());
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
					request.setAttribute("shgmvo", shgmvo);
					String url = "/front-end/shgm/buyerUpdate.jsp";
					RequestDispatcher failedview = request.getRequestDispatcher(url);
					failedview.forward(request, response);
					return;
				}

				shgmsvc.updateShgm(shgmno, shgmorg.getSellerno(), buyerno, shgmorg.getShgmname(), shgmorg.getPrice(),
						shgmorg.getIntro(), shgmorg.getImg(), shgmorg.getUpcheck(), shgmorg.getUptime(), take, takernm,
						takerph, address, shgmorg.getBoxstatus(), shgmorg.getPaystatus(), shgmorg.getStatus(),
						shgmorg.getSoldtime());

				request.setAttribute("updateSuccess", "success");

				String url = "/front-end/shgm/myShgm.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				errormap.put("error", "無法修改此商品");
				String url = "/front-end/shgm/buyerUpdate.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}
		
		if ("MsgUpdate".equals(action)) {
			response.setContentType("text/html; charset=utf-8");
			Writer out = response.getWriter();
			JSONObject jsonobj = new JSONObject();
			
			String mbrno = request.getParameter("mbrno");
			Integer index = new Integer(request.getParameter("index"));
			
			WsMessage wsMsg = new WsMessage();
			wsMsg.updateMbrmsg(mbrno, index);
			
			
			jsonobj.put("success", "updateSuccess!!");
			out.write(jsonobj.toString());
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
					// 待上架、上架中選擇自行下架，改成下架中狀態
					if (upcheck == 0 || upcheck == 1) {
						// 先更新
						shgmsvc.updateShgm(shgmno, shgmvo.getSellerno(), shgmvo.getBuyerno(), shgmvo.getShgmname(),
								shgmvo.getPrice(), shgmvo.getIntro(), shgmvo.getImg(), 2, shgmvo.getUptime(),
								shgmvo.getTake(), shgmvo.getTakernm(), shgmvo.getTakerph(), shgmvo.getAddress(),
								shgmvo.getBoxstatus(), shgmvo.getPaystatus(), shgmvo.getStatus(), shgmvo.getSoldtime());

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
						shgmsvc.updateShgm(shgmno, shgmvo.getSellerno(), shgmvo.getBuyerno(), shgmvo.getShgmname(),
								shgmvo.getPrice(), shgmvo.getIntro(), shgmvo.getImg(), 0, shgmvo.getUptime(),
								shgmvo.getTake(), shgmvo.getTakernm(), shgmvo.getTakerph(), shgmvo.getAddress(),
								shgmvo.getBoxstatus(), shgmvo.getPaystatus(), shgmvo.getStatus(), shgmvo.getSoldtime());

						jsonobj.put("shgmno", shgmvo.getShgmno());
						jsonobj.put("shgmname", shgmvo.getShgmname());
						jsonobj.put("price", shgmvo.getPrice());
						jsonobj.put("upcheck", 0);// upcheck的值在ajax作流程控制用的
					}
				}
				// 改變出貨狀態
				if (request.getParameter("boxstatus") != null) {

					Integer boxstatus = new Integer(request.getParameter("boxstatus"));

					jsonobj.put("shgmno", shgmvo.getShgmno());
					jsonobj.put("buyerno", shgmvo.getBuyerno());// 通知買家
					jsonobj.put("shgmname", shgmvo.getShgmname());
					jsonobj.put("takernm", shgmvo.getTakernm());
					jsonobj.put("takerph", shgmvo.getTakerph());
					jsonobj.put("address", shgmvo.getAddress());

					// 待出貨選擇進行出貨，改成出貨中
					if (boxstatus == 0) {
						shgmsvc.updateShgm(shgmno, shgmvo.getSellerno(), shgmvo.getBuyerno(), shgmvo.getShgmname(),
								shgmvo.getPrice(), shgmvo.getIntro(), shgmvo.getImg(), shgmvo.getUpcheck(),
								shgmvo.getUptime(), shgmvo.getTake(), shgmvo.getTakernm(), shgmvo.getTakerph(),
								shgmvo.getAddress(), 1, shgmvo.getPaystatus(), shgmvo.getStatus(),
								shgmvo.getSoldtime());
						jsonobj.put("boxstatus", 1);
					}
					// 出貨中選擇送達商品，改成已送達
					if (boxstatus == 1) {
						shgmsvc.updateShgm(shgmno, shgmvo.getSellerno(), shgmvo.getBuyerno(), shgmvo.getShgmname(),
								shgmvo.getPrice(), shgmvo.getIntro(), shgmvo.getImg(), shgmvo.getUpcheck(),
								shgmvo.getUptime(), shgmvo.getTake(), shgmvo.getTakernm(), shgmvo.getTakerph(),
								shgmvo.getAddress(), 2, shgmvo.getPaystatus(), shgmvo.getStatus(),
								shgmvo.getSoldtime());
						jsonobj.put("boxstatus", 2);
					}
				}
				// 訂單狀態
				if (request.getParameter("status") != null) {

					Integer status = new Integer(request.getParameter("status"));
					// 賣家回收市集商品，回到待上架狀態
					if (status == 3) {

						// 清除買家資料
						shgmsvc.updateShgm(shgmno, shgmvo.getSellerno(), null, shgmvo.getShgmname(), shgmvo.getPrice(),
								shgmvo.getIntro(), shgmvo.getImg(), 0, null, null, null, null, null, 0, 0, 0, null);

						jsonobj.put("shgmno", shgmno);
						jsonobj.put("shgmname", shgmvo.getShgmname());
						jsonobj.put("price", shgmvo.getPrice());
						jsonobj.put("status", 0);
					}
					// 已送達，買家確認收貨，下訂改成完成
					if (status == 2) {

						// 增加賣家點數
						String sellerno = shgmvo.getSellerno();
						MbrpfService mbrpfsvc = new MbrpfService();
						MbrpfVO mbrpfVO = mbrpfsvc.getOneMbrpf(sellerno);
						mbrpfVO.setPoints(mbrpfVO.getPoints() + shgmvo.getPrice());

						shgmvo = shgmsvc.updateShgm(shgmno, shgmvo.getSellerno(), shgmvo.getBuyerno(),
								shgmvo.getShgmname(), shgmvo.getPrice(), shgmvo.getIntro(), shgmvo.getImg(),
								shgmvo.getUpcheck(), shgmvo.getUptime(), shgmvo.getTake(), shgmvo.getTakernm(),
								shgmvo.getTakerph(), shgmvo.getAddress(), shgmvo.getBoxstatus(), shgmvo.getPaystatus(),
								2, shgmvo.getSoldtime(), mbrpfVO);

						java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Timestamp uptimeCT = shgmvo.getUptime();
						String uptime = df.format(uptimeCT);
						Timestamp soldtimeCT = shgmvo.getSoldtime();
						String soldtime = df.format(soldtimeCT);

						jsonobj.put("shgmno", shgmno);
						jsonobj.put("buyerno", shgmvo.getBuyerno());// 通知買家
						jsonobj.put("shgmname", shgmvo.getShgmname());
						jsonobj.put("price", shgmvo.getPrice());
						jsonobj.put("uptime", uptime);
						jsonobj.put("soldtime", soldtime);
						jsonobj.put("status", 2);
					}
					// 不論訂單狀態，買家取消訂單
					if (status == 8) {

						// 退款給買家
						String buyerno = shgmvo.getBuyerno();
						MbrpfService mbrpfsvc = new MbrpfService();
						MbrpfVO mbrpfVO = mbrpfsvc.getOneMbrpf(buyerno);
						mbrpfVO.setPoints(mbrpfVO.getPoints() + shgmvo.getPrice());

						shgmsvc.updateShgm(shgmno, shgmvo.getSellerno(), buyerno, shgmvo.getShgmname(),
								shgmvo.getPrice(), shgmvo.getIntro(), shgmvo.getImg(), shgmvo.getUpcheck(),
								shgmvo.getUptime(), shgmvo.getTake(), shgmvo.getTakernm(), shgmvo.getTakerph(),
								shgmvo.getAddress(), shgmvo.getBoxstatus(), shgmvo.getPaystatus(), 3,
								shgmvo.getSoldtime(), mbrpfVO);

						jsonobj.put("shgmno", shgmno);
						jsonobj.put("sellerno", shgmvo.getSellerno());// 通知賣家
						jsonobj.put("shgmname", shgmvo.getShgmname());
						jsonobj.put("price", shgmvo.getPrice());
						jsonobj.put("status", 3);
					}
				}

				out.write(jsonobj.toString());

			} catch (org.json.JSONException e) {
				e.printStackTrace();
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
			String url = null;
			try {
				String shgmno = request.getParameter("shgmno");

				ShgmService shgmsvc = new ShgmService();
				ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);
				String address = shgmvo.getAddress();

				HashMap<String, String> hashmap = null;
				if (address != null)
					hashmap = shgmsvc.splitAddress(address);

				request.setAttribute("cityarea", hashmap);

				request.setAttribute("shgmvo", shgmvo);
				// 來自後台的修改請求
				if (requestURL.equals("/back-end/shgm/listAllShgm.jsp")
						|| requestURL.equals("/back-end/shgm/shgm_select_page.jsp")) {
					url = "/back-end/shgm/updateShgm.jsp";
					// 來自前台買家的修改請求
				} else if (requestURL.equals("/front-end/shgm/myShgm.jsp"))
					url = "/front-end/shgm/buyerUpdate.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);

			} catch (Exception e) {
				errormsgs.add("無法取得要修改的資料:" + e.getMessage());
				if (requestURL.equals("/back-end/shgm/listAllShgm.jsp")
						|| requestURL.equals("/back-end/shgm/shgm_select_page.jsp")) {
					url = "/back-end/shgm/shgm_select_page.jsp";
				} else if (requestURL.equals("/front-end/shgm/myShgm.jsp"))
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
				if (sellerno.trim().length() == 0)
					errormsgs.add("賣家編號：請勿輸入空白");
				if (!sellerno.trim().matches(memberreg))
					errormsgs.add("賣家編號：BM開頭、長度7的格式");

				// 買家可為空字串
				String buyerno = request.getParameter("buyerno");

				String shgmname = request.getParameter("shgmname");
				if (shgmname.trim().length() == 0)
					errormsgs.add("市集商品名稱：請勿輸入空白");
				if (shgmname.trim().length() > 21)
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
				if (buyerno.trim().length() > 0 || take != null || takernm.trim().length() > 0
						|| takerph.trim().length() > 0 || address.trim().length() > 0 || boxstatus != 0
						|| paystatus != 0 || status != 0) {

					// 買家編號錯誤處理
					if (buyerno.trim().length() == 0) {
						errormsgs.add("買家編號：不得為空");
					} else if (!buyerno.trim().matches(memberreg))
						errormsgs.add("買家編號：BM開頭、長度7的格式");

					// 取貨方式錯誤處理
					if (take == null)
						errormsgs.add("取貨方式：請選擇取貨方式");

					// 取貨人姓名錯誤處理
					String takernmreg = "^[(\u4e00-\u9fa5)]{1,10}$";
					if (takernm.trim().length() == 0) {
						errormsgs.add("取貨人姓名：不得為空");
					} else if (!takernm.trim().matches(takernmreg))
						errormsgs.add("取貨人姓名：只能是中文，且長度必須在1到10之間");

					// 取貨人電話錯誤處理
					String takerphreg = "^09\\d{8}$";
					if (takerph.trim().length() == 0) {
						errormsgs.add("取貨人電話：不得為空");
					} else if (!takerph.trim().matches(takerphreg))
						errormsgs.add("取貨人電話：請輸入符合格式的電話號碼");

					String adsReg = "^[(\u4e00-\u9fa5)(\\w)]{5,40}$";
					if (ads.trim().length() == 0) {
						errormsgs.add("取貨地址：地址不得為空");
					} else if (ads.equals(address)) {
						errormsgs.add("取貨地址：請選擇鄉鎮縣市");
					} else if (!ads.trim().matches(adsReg))
						errormsgs.add("取貨地址：只能是中、英文字母、數字、底線，且長度必須在5到40之間");
				}

				// 後臺要改變未審核商品的狀態，必須先審核上架或審核下架，確保有審核行為
				if (upcheck < 1 && (boxstatus != 0 || paystatus != 0 || status != 0)) {
					errormsgs.add("審核狀態：欲改變狀態，先審核此市集商品");
				}

				ShgmService shgmsvc = new ShgmService();
				ShgmVO shgmorg = shgmsvc.getOneShgm(shgmno);
				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(shgmno);
				shgmvo.setSellerno(sellerno);
				shgmvo.setBuyerno(buyerno);
				shgmvo.setShgmname(shgmname);
				shgmvo.setPrice(price);
				shgmvo.setIntro(intro);
				shgmvo.setImg(img);
				shgmvo.setUpcheck(upcheck);
				shgmvo.setUptime(shgmorg.getUptime());
				shgmvo.setTake(take);
				shgmvo.setTakernm(takernm);
				shgmvo.setTakerph(takerph);
				shgmvo.setAddress(address);
				shgmvo.setBoxstatus(boxstatus);
				shgmvo.setPaystatus(paystatus);
				shgmvo.setStatus(status);
				shgmvo.setSoldtime(shgmorg.getSoldtime());

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
					shgmsvc.updateShgm(shgmno, sellerno, null, shgmname, price, intro, img, 0, null, null, null, null,
							null, 0, 0, 0, null);
				} else {
					shgmsvc.updateShgm(shgmno, sellerno, buyerno, shgmname, price, intro, img, upcheck,
							shgmorg.getUptime(), take, takernm, takerph, address, boxstatus, paystatus, status,
							shgmorg.getSoldtime());
				}

				String url = null;
				if (whichPage != null) {
					url = requestURL + "?whichPage=" + whichPage;
					request.setAttribute("shgmvo", shgmvo);
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

		if ("search".equals(action)) {

			try {
				String word = request.getParameter("word");

				ShgmService shgmsvc = new ShgmService();
				Set<ShgmVO> set = shgmsvc.searchForMain(word);
				request.setAttribute("searchResult", set);
				request.setAttribute("setsize", (long) set.size());
				String url = "/front-end/shgm/mainPage.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				String url = "/front-end/shgm/mainPage.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}

		if ("toPrsnlMkt".equals(action)) {

			try {
				String sellerno = request.getParameter("sellerno");

				ShgmService shgmsvc = new ShgmService();
				Set<ShgmVO> set = shgmsvc.allForPersonalMkt(sellerno);
				request.setAttribute("pslset", set);
				MbrpfService mbrpfsvc = new MbrpfService();
				MbrpfVO mbrpfvo = mbrpfsvc.getOneMbrpf(sellerno);
				session.setAttribute("sellerinfo", mbrpfvo);
				String url = "/front-end/shgm/personalMkt.jsp";
				RequestDispatcher successview = request.getRequestDispatcher(url);
				successview.forward(request, response);
			} catch (Exception e) {
				String url = "/front-end/shgm/personalMkt.jsp";
				RequestDispatcher failedview = request.getRequestDispatcher(url);
				failedview.forward(request, response);
			}
		}
	}
}