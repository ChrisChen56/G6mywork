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
import com.shgmrp.model.ShgmrpService;
import com.shgmrp.model.ShgmrpVO;

import connectionpool.WsMessage;

import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.OnError;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;

//updateShgm
@ServerEndpoint(value = "/mainPage/{mbrno}", configurator = GetHttpSessionConfigurator.class)
public class MyWebSocket {
	private static final Map<String, Session> connectedSessions = Collections
			.synchronizedMap(new HashMap<String, Session>());

	private EndpointConfig conf;

	HttpSession httpsession;

	WsMessage wsmsg = new WsMessage();

	@OnOpen
	public void open(@PathParam("mbrno") String mbrno, Session session, EndpointConfig conf) {
		this.conf = conf;
		System.out.println("mbrno:" + mbrno + " entered, " + "sessionID:" + session.getId());
		connectedSessions.put(mbrno, session);
	}

	@OnMessage
	public void message(Session session, String data) {
		httpsession = (HttpSession) conf.getUserProperties().get("httpsession");
		StringBuilder sendthis = new StringBuilder();
		ShgmService shgmsvc = new ShgmService();
		MbrpfService mbrpfsvc = new MbrpfService();
		String sellerno = null;
		String buyerno = null;
		MbrpfVO sellerVO = null;
		MbrpfVO buyerVO = null;

		// 只有後台會送出市集商品編號的資料
		String shgmRegexp = "^CA\\d{5}$";
		if (data.matches(shgmRegexp)) {
			System.out.println("enter data send");
			ShgmVO shgmvo = shgmsvc.getOneShgm(data);
			sellerno = shgmvo.getSellerno();
			sellerVO = mbrpfsvc.getOneMbrpf(sellerno);
			if (shgmvo.getUpcheck() == 0) {
				;// do nothing
			} else if (shgmvo.getUpcheck() == 1) {
				sendthis.append(sellerVO.getNickname() + "，您的商品「" + shgmvo.getShgmname() + "」，已經上架了！");
				sendmsg(sellerno, sendthis);
				sendthis.setLength(0);
				sendthis.append(sellerVO.getNickname() + "有新上架商品「" + shgmvo.getShgmname() + "」，趕快去市集看看！");
				sendMsgToAll(sellerno, sendthis);
			} else if (shgmvo.getUpcheck() == 2) {
				ShgmrpService shgmrpsvc = new ShgmrpService();
				sendthis.append(sellerVO.getNickname() + "，您的商品「" + shgmvo.getShgmname() + "」，已經下架了！");
				ShgmrpVO shgmrpvo = shgmrpsvc.getOnerpByShgmno(data);
				if (shgmrpvo != null && shgmrpvo.getStatus() == 1)
					sendthis.append("\n下架原因：" + shgmrpvo.getDetail());
				sendmsg(sellerno, sendthis);
			}
			return;

		} else {
			// 從前台頁面ajax送來的json格式資料
			JSONObject jsonobj = new JSONObject(data);
			ShgmVO shgmorg = shgmsvc.getOneShgm(jsonobj.getString("shgmno"));

			sellerno = shgmorg.getSellerno();
			buyerno = shgmorg.getBuyerno();
			sellerVO = mbrpfsvc.getOneMbrpf(sellerno);
			buyerVO = mbrpfsvc.getOneMbrpf(buyerno);

			Gson gson = new Gson();
			ShgmVO shgmvo = gson.fromJson(data, ShgmVO.class);
			if (shgmvo.getBoxstatus() != null) {
				if (shgmvo.getBoxstatus() == 1) {
					sendthis.append("賣家 " + sellerVO.getNickname() + "，已將您購買的商品「" + shgmorg.getShgmname() + "」出貨。");
					sendmsg(buyerno, sendthis);
				} else if (shgmvo.getBoxstatus() == 2) {
					sendthis.append(buyerVO.getNickname() + "，您購買的商品「" + shgmorg.getShgmname() + "」已送達，請確認取貨。");
					sendmsg(buyerno, sendthis);
				}
			}
			if (shgmvo.getStatus() != null) {
				if (shgmvo.getStatus() == 2) {
					sendthis.append("買家 " + buyerVO.getNickname() + "，已確認收貨，您的商品「" + shgmorg.getShgmname() + "」已賣出！");
					sendmsg(sellerno, sendthis);
				} else if (shgmvo.getStatus() == 3) {
					sendthis.append(
							"買家 " + buyerVO.getNickname() + "，已取消購買「" + shgmorg.getShgmname() + "」，請至賣家專區回收商品。");
					sendmsg(sellerno, sendthis);
					sendthis.setLength(0);
					sendthis.append(buyerVO.getNickname() + "，您已成功取消購買「" + shgmorg.getShgmname() + "」，點數共 "
							+ shgmorg.getPrice() + "點已退還至您的帳戶。");
					sendmsg(buyerno, sendthis);
				}
			}
			// 購買成功，前台送過來
			if (shgmvo.getPaystatus() != null) {
				sendthis.append("買家 " + buyerVO.getNickname() + "，已購買您的商品「" + shgmorg.getShgmname() + "」，請至賣家專區出貨。");
				sendmsg(sellerno, sendthis);
				sendthis.setLength(0);
				sendthis.append(sellerVO.getNickname() + "，您已成功購買商品「" + shgmorg.getShgmname() + "」，請等待賣家出貨。");
				sendmsg(buyerno, sendthis);
			}
		}
	}

	public void sendmsg(String mbrno, StringBuilder sendthis) {
		String strSendThis = sendthis.toString();
		for (String hashmapkey : connectedSessions.keySet()) {
			if (mbrno.equals(hashmapkey)) {// 挑出此會員
				wsmsg.saveMbrmsg(mbrno, strSendThis);// 送出之前保存
				if (connectedSessions.get(hashmapkey).isOpen())
					connectedSessions.get(hashmapkey).getAsyncRemote().sendText(strSendThis);// sendToOne
			}
		}
	}

	public void sendMsgToAll(String mbrno, StringBuilder sendthis) {
		String strSendThis = sendthis.toString();

		MbrpfService mbrpfsvc = new MbrpfService();
		List<MbrpfVO> list = mbrpfsvc.getAll();
		Set<String> mbrnoSet = new HashSet<String>();
		for (MbrpfVO mbrpfvo : list) {
			mbrnoSet.add(mbrpfvo.getMbrno());
			System.out.println(mbrpfvo.getMbrno() + ":Msg");
		}

		for (String hashmapkey : mbrnoSet) {
			if (!mbrno.equals(hashmapkey)) {// 排除此會員
				wsmsg.saveMbrmsg(hashmapkey, strSendThis);// 送出之前保存
				if (connectedSessions.get(hashmapkey) != null)
					connectedSessions.get(hashmapkey).getAsyncRemote().sendText(strSendThis);// sendToAll除了賣家自己
			}
		}
	}

	@OnError
	public void error(Session session, Throwable error) {
		error.printStackTrace();
		System.out.println("error:" + error.getMessage());
	}

	@OnClose
	public void close(Session session, CloseReason reason) {
		for (Entry<String, Session> keyValue : connectedSessions.entrySet()) {
			if (session.equals(keyValue.getValue()))
				System.out.println("close: " + keyValue.getKey() + " leaved , reason:" + reason.getReasonPhrase());
		}
	}

}
