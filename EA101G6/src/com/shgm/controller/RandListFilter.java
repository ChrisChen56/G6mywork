package com.shgm.controller;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.shgm.model.*;

public class RandListFilter extends HttpServlet implements Filter {
	
	private FilterConfig config;
       
    public void init(FilterConfig filterConfig) throws javax.servlet.ServletException { 
    	this.config = config;
    }
    
    public void destroy() {
    	config = null;
    }

    public void doFilter(ServletRequest request1, ServletResponse response1, FilterChain chain) throws java.io.IOException, javax.servlet.ServletException { 
    	HttpServletRequest request = (HttpServletRequest) request1;
    	HttpServletResponse response = (HttpServletResponse) response1;
    	HttpSession session = request.getSession();
    	
    	ShgmService shgmsvc = new ShgmService();
    	String shgmno = request.getParameter("shgmno");
    	ShgmVO shgmvo = shgmsvc.getOneShgm(shgmno);//取得使用者點選的市集商品
    	
    	List<ShgmVO> list = new ArrayList<ShgmVO>();
    	list = shgmsvc.getAllForMain();
    	
    	
    	for(Iterator<ShgmVO> it = list.iterator();it.hasNext();) {
    		ShgmVO shgm = it.next();
    		if(shgm.getShgmno().equals(shgmvo.getShgmno())) {
    			it.remove();
    		}
    	}
    	Collections.shuffle(list);//打亂
    	session.setAttribute("randlist", list);
    	chain.doFilter(request1, response1);
    }

}
