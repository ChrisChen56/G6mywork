package com.shgm.controller;

import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.mbrpf.model.MbrpfVO;

import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.OnError;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
						//updateShgm
@ServerEndpoint(value="/mainPage/{mbrno}", configurator=GetHttpSessionConfigurator.class)
public class MyWebSocket {
	private static final Map<String, Session> connectedSessions = Collections.synchronizedMap(new HashMap<String, Session>());
	
	private EndpointConfig conf;
	
	HttpSession httpsession;
	
	@OnOpen
	public void open(@PathParam("mbrno")String mbrno,Session session, EndpointConfig conf) {
		this.conf = conf;
		connectedSessions.put(mbrno, session);
	}
	
	@OnMessage
	public void message(Session session, String msg) {
		System.out.println("1");
		httpsession = (HttpSession) conf.getUserProperties().get("httpsession");
		System.out.println("2");
		MbrpfVO mbrpfvo = (MbrpfVO) httpsession.getAttribute("member");
		System.out.println("3");
		String mbrno = mbrpfvo.getMbrno();
		System.out.println("4");
		for(String hashmapkey: connectedSessions.keySet()) {
			if(mbrno.equals(hashmapkey)) {
				connectedSessions.get(hashmapkey).getAsyncRemote().sendText("我抓到你了");
				System.out.println(hashmapkey);
			}
		}
	}
	
	@OnError
	public void error(Session session, Throwable error) {
		System.out.println(error.getMessage());
	}
	
	@OnClose
	public void close(Session session, CloseReason ression) {
		System.out.println("close");
	}

}
