package com.mbrpf.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.mbrpf.model.MbrpfService;
import com.mbrpf.model.MbrpfVO;
import com.mbrpf.model.*;

@MultipartConfig

public class MbrpfServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
if ("getOne_For_Display".equals(action)) {//來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("mbrno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mbrpf/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String mbrno = null;
				try {
					mbrno = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mbrpf/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				MbrpfService mbrpfSvc = new MbrpfService();
				MbrpfVO mbrpfVO = mbrpfSvc.getOneMbrpf(mbrno);
				if (mbrpfVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mbrpf/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("mbrpfVO", mbrpfVO); // 資料庫取出的mbrpfVO物件,存入req
				String url = "/front-end/mbrpf/listOneMbrpf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMbrpf.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mbrpf/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
if ("getOne_For_Update".equals(action)) { // 來自listAllMbrpf.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String mbrno = new String(req.getParameter("mbrno"));
				
				/***************************2.開始查詢資料****************************************/
				MbrpfService mbrpfSvc = new MbrpfService();
				MbrpfVO mbrpfVO = mbrpfSvc.getOneMbrpf(mbrno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("mbrpfVO", mbrpfVO);         // 資料庫取出的empVO物件,存入req
				String url = "/front-end/mbrpf/update_mbrpf_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mbrpf/listAllMbrpf.jsp");
				failureView.forward(req, res);
			}
		}
		
		
if ("update".equals(action)) { // 來自update_mbrpf_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mbrno = req.getParameter("mbrno");
				
				String mbract = req.getParameter("mbract");
				
				String mbractReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mbract == null || mbract.trim().length() == 0) {
					errorMsgs.add("姓名: 請勿空白");
				} else if(!mbract.trim().matches(mbractReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String mbrpw = req.getParameter("mbrpw").trim();
				if (mbrpw == null || mbrpw.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}
				
				String mbrname = req.getParameter("mbrname").trim();
				if (mbrname == null || mbrname.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				
//				String mbrimg = req.getParameter("mbrimg").trim();
//				if (mbrimg == null || mbrimg.trim().length() == 0) {
//					errorMsgs.add("格式錯誤");
//				}
				
				byte[] buf = null;
			    Part part = req.getPart("mbrimg");
			    InputStream in =  part.getInputStream();
		if(in.available()>0) {			   
			    buf = new byte[in.available()];
			    in.read(buf);
			    in.close();
		}else {
			MbrpfService mbrpfSvc = new MbrpfService();
			MbrpfVO mbrpfVO = mbrpfSvc.getOneMbrpf(mbrno);
			buf = mbrpfVO.getMbrimg();
		}	
				
				
				java.sql.Date birth = null;
				try {
					birth = java.sql.Date.valueOf(req.getParameter("birth").trim());
				} catch (IllegalArgumentException e) {
					birth=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer sex = null;
				try {
					sex = new Integer(req.getParameter("sex").trim());
				} catch (NumberFormatException e) {
					sex = null;
					errorMsgs.add("格式錯誤");
				}	
				String mail = req.getParameter("mail").trim();
				if (mail == null || mail.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				String phone = req.getParameter("phone").trim();
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				String mbrac = req.getParameter("mbrac").trim();
				if (mbrac == null || mbrac.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				String nickname = req.getParameter("nickname").trim();
				if (nickname == null || nickname.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				Integer points = null;
				try {
					points = new Integer(req.getParameter("points").trim());
				} catch (NumberFormatException e) {
					points = null;
					errorMsgs.add("格式錯誤");
				}	
				Integer status = null;
				try {
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					status = null;
					errorMsgs.add("格式錯誤");
				}	
				Integer ratedtotal = null;
				try {
					ratedtotal = new Integer(req.getParameter("ratedtotal").trim());
				} catch (NumberFormatException e) {
					ratedtotal = null;
					errorMsgs.add("格式錯誤");
				}	
				Integer startotal = null;
				try {
					startotal = new Integer(req.getParameter("startotal").trim());
				} catch (NumberFormatException e) {
					startotal = null;
					errorMsgs.add("格式錯誤");
				}	
				Integer unattend = null;
				try {
					unattend = new Integer(req.getParameter("unattend").trim());
				} catch (NumberFormatException e) {
					unattend = null;
					errorMsgs.add("格式錯誤");
				}	
				Integer ttattend = null;
				try {
					ttattend = new Integer(req.getParameter("ttattend").trim());
				} catch (NumberFormatException e) {
					ttattend = null;
					errorMsgs.add("格式錯誤");
				}	

				
//				System.out.println(mbrno+ ","+mbract+","+mbrpw +" ,"+mbrname+" ,"+buf+" ,"+birth+", "+sex+" ,"+mail+", "+phone+", "+mbrac+", "+nickname+" ,"+points+" ,"+status+", "+
//				ratedtotal+", "+startotal+", "+unattend+", "+ttattend);
				MbrpfVO mbrpfVO = new MbrpfVO();
				mbrpfVO.setMbrno(mbrno);
				mbrpfVO.setMbract(mbract);
				mbrpfVO.setMbrpw(mbrpw);
				mbrpfVO.setMbrname(mbrname);
				mbrpfVO.setMbrimg(buf);
				mbrpfVO.setBirth(birth);
				mbrpfVO.setSex(sex);
				mbrpfVO.setMail(mail);
				mbrpfVO.setPhone(phone);
				mbrpfVO.setMbrac(mbrac);
				mbrpfVO.setNickname(nickname);
				mbrpfVO.setPoints(points);
				mbrpfVO.setStatus(status);
				mbrpfVO.setRatedtotal(ratedtotal);
				mbrpfVO.setStartotal(startotal);
				mbrpfVO.setUnattend(unattend);
				mbrpfVO.setTtattend(ttattend);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mbrpfVO", mbrpfVO); // 含有輸入格式錯誤的mbrpfVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mbrpf/update_mbrpf_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.開始修改資料*****************************************/
				MbrpfService mbrpfSvc = new MbrpfService();
				mbrpfVO = mbrpfSvc.updateMbrpf(mbrno,mbract,mbrpw,mbrname,buf,birth,sex,mail,phone,mbrac,nickname,points,status,ratedtotal,startotal,unattend,ttattend);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("mbrpfVO", mbrpfVO); // 資料庫update成功後,正確的的mbrpfVO物件,存入req
		//session刪掉,不然會抓到舊圖
				HttpSession session = req.getSession();
				session.removeAttribute(mbrno);
				
				String url = "/front-end/mbrpf/listOneMbrpf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneMbrpf.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mbrpf/update_mbrpf_input.jsp");
				failureView.forward(req, res);
			}
		}
//新增資料
if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String mbract = req.getParameter("mbract");
				String mbractReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mbract == null || mbract.trim().length() == 0) {
					errorMsgs.add("姓名: 請勿空白");
				} else if(!mbract.trim().matches(mbractReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String mbrpw = req.getParameter("mbrpw").trim();
				if (mbrpw == null || mbrpw.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				String mbrname = req.getParameter("mbrname").trim();
				if (mbrname == null || mbrname.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				
//				String mbrimg = req.getParameter("mbrimg").trim();
//				if (mbrimg == null || mbrimg.trim().length() == 0) {
//					errorMsgs.add("格式錯誤");
//				}
				
				
			    Part part = req.getPart("mbrimg");
			    InputStream in =  part.getInputStream();
			    byte[] buf = new byte[in.available()];
			    in.read(buf);
				
				java.sql.Date birth = null;
				try {
					birth = java.sql.Date.valueOf(req.getParameter("birth").trim());
				} catch (IllegalArgumentException e) {
					birth=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer sex = null;
				try {
					sex = new Integer(req.getParameter("sex").trim());
				} catch (NumberFormatException e) {
					sex = null;
					errorMsgs.add("格式錯誤");
				}	
				String mail = req.getParameter("mail").trim();
				if (mail == null || mail.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				String phone = req.getParameter("phone").trim();
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				String mbrac = req.getParameter("mbrac").trim();
				if (mbrac == null || mbrac.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				String nickname = req.getParameter("nickname").trim();
				if (nickname == null || nickname.trim().length() == 0) {
					errorMsgs.add("格式錯誤");
				}	
				Integer points = null;
				try {
					points = new Integer(req.getParameter("points").trim());
				} catch (NumberFormatException e) {
					points = null;
					errorMsgs.add("格式錯誤");
				}	
				Integer status = null;
				try {
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					status = null;
					errorMsgs.add("格式錯誤");
				}	
				Integer ratedtotal = null;
				try {
					ratedtotal = new Integer(req.getParameter("ratedtotal").trim());
				} catch (NumberFormatException e) {
					ratedtotal = null;
					errorMsgs.add("格式錯誤");
				}	
				Integer startotal = null;
				try {
					startotal = new Integer(req.getParameter("startotal").trim());
				} catch (NumberFormatException e) {
					startotal = null;
					errorMsgs.add("格式錯誤");
				}	
				Integer unattend = null;
				try {
					unattend = new Integer(req.getParameter("unattend").trim());
				} catch (NumberFormatException e) {
					unattend = null;
					errorMsgs.add("格式錯誤");
				}	
				Integer ttattend = null;
				try {
					ttattend = new Integer(req.getParameter("ttattend").trim());
				} catch (NumberFormatException e) {
					ttattend = null;
					errorMsgs.add("格式錯誤");
				}	

				
//				System.out.println(mbract +',' +mbrpw +',' +mbrname+',' +birth+',' +sex+',' +);
				MbrpfVO mbrpfVO = new MbrpfVO();
				mbrpfVO.setMbract(mbract);
				mbrpfVO.setMbrpw(mbrpw);
				mbrpfVO.setMbrname(mbrname);
				mbrpfVO.setMbrimg(null);
				mbrpfVO.setBirth(birth);
				mbrpfVO.setSex(sex);
				mbrpfVO.setMail(mail);
				mbrpfVO.setPhone(phone);
				mbrpfVO.setMbrac(mbrac);
				mbrpfVO.setNickname(nickname);
				mbrpfVO.setPoints(points);
				mbrpfVO.setStatus(status);
				mbrpfVO.setRatedtotal(ratedtotal);
				mbrpfVO.setStartotal(startotal);
				mbrpfVO.setUnattend(unattend);
				mbrpfVO.setTtattend(ttattend);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mbrpfVO", mbrpfVO); // 含有輸入格式錯誤的mbrpfVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mbrpf/addMbrpf.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				MbrpfService mbrpfSvc = new MbrpfService();
				mbrpfVO = mbrpfSvc.addMbrpf(mbract,mbrpw,mbrname,buf,birth,sex,mail,phone,mbrac,nickname,points,status,ratedtotal,startotal,unattend,ttattend);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/mbrpf/listAllMbrpf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mbrpf/addMbrpf.jsp");
				failureView.forward(req, res);
			}
		}
		
//刪除資料	
if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String mbrno = new String(req.getParameter("mbrno").trim());
				
				/***************************2.開始刪除資料***************************************/
				MbrpfService mbrpfSvc = new MbrpfService();
				mbrpfSvc.deleteMbrpf(mbrno);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/mbrpf/listAllMbrpf.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mbrpf/listAllMbrpf.jsp");
				failureView.forward(req, res);
			}
		}

if("tryLogin".equals(action)) {// 來自login.jsp的請求
	List<String> errorMsgs = new LinkedList<String>();
	req.setAttribute("errorMsgs", errorMsgs);
	try {
		/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		req.setAttribute("account", account);
		req.setAttribute("password", password);
		
		if(account == null || account.trim().length() == 0) {
			errorMsgs.add("請輸入帳號");
		}
		
		if(password == null || password.trim().length() == 0) {
			errorMsgs.add("請輸入密碼");
		}
		
		if(!errorMsgs.isEmpty()) {
			RequestDispatcher failView = req.getRequestDispatcher("/front-end/login.jsp");
			failView.forward(req, res);
			return;
		}
		/***************************2.開始驗證是否為會員***************************************/
			MbrpfService mbrpfSvc = new MbrpfService();//此處的account為mbract，如果可以透過這個mbract取得會員物件，代表這account確實為會員
			MbrpfVO mbrpfVO = mbrpfSvc.checkLogin(account);//透過傳mbract進去可以取得mbrpfVO物件的方法，取得要更改密的會員物件
			String mbrpwd = mbrpfVO.getMbrpw();
			if(password.equals(mbrpwd)) {//帳號正確，取出的密碼也和輸入的一樣
				HttpSession session =req.getSession();
				session.setAttribute("account", account);//將帳號存進session，之後可以藉由這個取得他所擁有的權限
				session.setAttribute("mbrpfVO", mbrpfVO);
		/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				try {//查看是否有來源網頁
					String location = (String)session.getAttribute("location");
					if(location != null) {//如果有來源網頁
						session.removeAttribute("location");
						res.sendRedirect(location);//重導至該網頁
						return;
					}
				}catch(Exception e) {
					res.sendRedirect(req.getContextPath()+"/front-end/mbrpf/select_page.jsp");
					return;
				}
				//沒有來源網頁的話就去首頁
				res.sendRedirect(req.getContextPath()+"/front-end/mbrpf/select_page.jsp");
				return;
			}else {
				errorMsgs.add("帳號、密碼錯誤");
				RequestDispatcher failView = req.getRequestDispatcher("/front-end/login.jsp");
				failView.forward(req, res);
			}
			
			/***************************其他可能的錯誤處理**********************************/
		}catch(Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/login.jsp");
			failureView.forward(req, res);
		}
}


if("logout".equals(action)) {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
	
		List<String> successMsgs = new LinkedList<String>();
		req.setAttribute("successMsgs", successMsgs);
	/***************************1.登出*****************************************/
		try {
		HttpSession session = req.getSession();
		session.removeAttribute("account");
		session.removeAttribute("mbrpfVO");
		/***************************2.登出成功,準備轉交(Send the Success view)*************/
		successMsgs.add("帳號已登出");
		RequestDispatcher failView = req.getRequestDispatcher("/front-end/login.jsp");
		failView.forward(req, res);
		return;
		/***************************其他可能的錯誤處理*************************************/	
		}catch(Exception e) {
		errorMsgs.add("登出失敗");
		RequestDispatcher failView = req.getRequestDispatcher("/front-end/login.jsp");
		failView.forward(req, res);
		}
	}

	}
}
