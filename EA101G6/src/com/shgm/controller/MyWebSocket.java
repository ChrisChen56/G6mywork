package com.shgm.controller;

import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.mbrpf.model.MbrpfService;
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
		JSONObject jsonobj = new JSONObject(jsondata);
		ShgmService shgmsvc = new ShgmService();
		ShgmVO shgmorg = shgmsvc.getOneShgm(jsonobj.getString("shgmno"));
		
		MbrpfService mbrpfsvc = new MbrpfService();
		String sellerno = shgmorg.getSellerno();
		String buyerno = shgmorg.getBuyerno();
		MbrpfVO sellerVO = mbrpfsvc.getOneMbrpf(sellerno);
		MbrpfVO buyerVO = mbrpfsvc.getOneMbrpf(buyerno);
		
		Gson gson = new Gson();
		ShgmVO shgmvo = gson.fromJson(jsondata, ShgmVO.class);
		if(shgmvo.getUpcheck() != null) {
			sendthis = "您已改變上架狀態";//其實可以刪掉
			sendmsg(sellerno, sendthis);
		}
		if(shgmvo.getBoxstatus() != null) {
			
			if(shgmvo.getBoxstatus() == 1) {
				sendthis = "賣家 "+sellerVO.getNickname()+"，已將您購買的商品「"+shgmorg.getShgmname()+"」出貨";
				sendmsg(buyerno, sendthis);
			} else if(shgmvo.getBoxstatus() == 2) {
				sendthis = buyerVO.getNickname()+"，您購買的商品「"+shgmorg.getShgmname()+"」已送達，請確認取貨！";
				sendmsg(buyerno, sendthis);
			}
		}
		if(shgmvo.getStatus() != null) {
			if(shgmvo.getStatus() == 2) {
				sendthis = "買家  "+buyerVO.getNickname()+"，已確認收貨，您的商品「"+shgmorg.getShgmname()+"」已賣出！";
				sendmsg(sellerno, sendthis);
			} else if(shgmvo.getStatus() == 3) {
				sendthis = "買家  "+buyerVO.getNickname()+"，已取消購買「"+shgmorg.getShgmname()+"」，請至賣家專區回收商品";
				sendmsg(sellerno, sendthis);
				sendthis = "親愛的 "+buyerVO.getNickname()+"，您已成功取消購買「"+shgmorg.getShgmname()+"」，點數共 "+shgmorg.getPrice()+"點已退還至您的帳戶";
				sendmsg(buyerno, sendthis);
			}
		}
		
	}
	
	public void sendmsg(String mbrno, String sendthis) {
		for(String hashmapkey: connectedSessions.keySet()) {
			if(mbrno.equals(hashmapkey)) {
				if(connectedSessions.get(hashmapkey).isOpen())
					connectedSessions.get(hashmapkey).getAsyncRemote().sendText(sendthis);//sendToMbr
			}
		}
	}
	
	@OnError
	public void error(Session session, Throwable error) {
		error.printStackTrace();
		System.out.println("error:"+error.getMessage());
	}
	
	@OnClose
	public void close(Session session, CloseReason reason) {
		System.out.println("close:"+reason.getReasonPhrase());
	}

}
