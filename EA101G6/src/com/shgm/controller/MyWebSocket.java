package com.shgm.controller;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.OnError;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;

@ServerEndpoint(value="/updateShgm/{connectName}", configurator=GetHttpSessionConfigurator.class)
public class MyWebSocket {
	private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());
	
	private EndpointConfig conf;
	
	HttpSession httpsession = null;
	
	@OnOpen
	public void open(Session session, EndpointConfig conf) {
		this.conf = conf;
		
		System.out.println(session.getId());
	}
	
	@OnMessage
	public void message(Session session, String msg) {
		httpsession = (HttpSession) conf.getUserProperties().get("HttpSession");
//		httpsession.getAttribute();
		System.out.println();
	}
	
	@OnError
	public void error(Session session, Throwable error) {
		
	}
	
	@OnClose
	public void close(Session session, CloseReason ression) {
		
	}

}
