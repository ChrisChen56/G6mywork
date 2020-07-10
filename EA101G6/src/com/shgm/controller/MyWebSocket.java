package com.shgm.controller;

import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.mbrpf.model.MbrpfVO;
import com.shgm.model.ShgmService;
import com.shgm.model.ShgmVO;

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
	public void message(Session session, String jsondata) {
		
		httpsession = (HttpSession) conf.getUserProperties().get("httpsession");
		String sendthis = null;
		String mbrno = null;
		JSONObject jsonobj = new JSONObject(jsondata);
		System.out.println(jsonobj);
		ShgmService shgmsvc = new ShgmService();
		ShgmVO shgmorg = shgmsvc.getOneShgm(jsonobj.getString("shgmno"));
		
		Gson gson = new Gson();
		ShgmVO shgmvo = gson.fromJson(jsondata, ShgmVO.class);
		if(shgmvo.getUpcheck() != null) {
			mbrno = shgmorg.getSellerno();
			sendthis = "您已改變狀態";
		}
		if(shgmvo.getBoxstatus() == null) {
			
		}
		if(shgmvo.getStatus() == null) {
			
		}
//		MbrpfVO mbrpfvo = (MbrpfVO) httpsession.getAttribute("member");
		for(String hashmapkey: connectedSessions.keySet()) {
//			connectedSessions.get(hashmapkey).getAsyncRemote().sendText(sendthis);//sendToAll
			System.out.println("enter");
			if(mbrno.equals(hashmapkey)) {
				System.out.println("here");
				connectedSessions.get(hashmapkey).getAsyncRemote().sendText(sendthis);//sendToMbr
				System.out.println(hashmapkey);
			}
		}
	}
	
	@OnError
	public void error(Session session, Throwable error) {
		System.out.println("error:"+error.getMessage());
	}
	
	@OnClose
	public void close(Session session, CloseReason reason) {
		System.out.println("close:"+reason.getReasonPhrase());
	}

}
