package connectionpool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class WsMessage {

	private static JedisPool pool = JedisUtil.getJedisPool();
	MsgVO msgvo = new MsgVO();
	Gson gson = new Gson();

	public void saveMbrmsg(String Mbrno, String message) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		
		String strMsgVO = null;
		Integer index = null;
		//是空的話就從0開始
		if(jedis.llen(Mbrno) == 0) {
			msgvo.setIndex(0);
		} else {
			strMsgVO = jedis.lindex(Mbrno, 0);
			MsgVO msgvoTemp = gson.fromJson(strMsgVO, MsgVO.class);
			index = msgvoTemp.getIndex();
			msgvo.setIndex(index+1);//最左邊的索引值+1
		}
		
		msgvo.setRead(0);// 設為未讀
		msgvo.setMessage(message);// 放入訊息
		String msgvoJson = gson.toJson(msgvo);// 物件轉Json
		System.out.println("WsMessage.saveMbrmsg: Mbrno:" + Mbrno + ", message:" + msgvoJson);
		jedis.lpush(Mbrno, msgvoJson);// 從最左邊放入(舊的在右邊)

		jedis.close();
	}

	public void saveMbrmsg(String Mbrno, StringBuilder message) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		
		String strMsgVO = null;
		Integer index = null;
		//是空的話就從0開始
		if(jedis.llen(Mbrno) == 0) {
			msgvo.setIndex(1);
		} else {
			strMsgVO = jedis.lindex(Mbrno, 0);
			MsgVO msgvo = gson.fromJson(strMsgVO, MsgVO.class);
			index = msgvo.getIndex();
		}
		
		msgvo.setIndex(index+1);//最左邊的索引值+1
		msgvo.setRead(0);// 設為未讀
		msgvo.setMessage(message.toString());// 放入訊息
		String msgvoJson = gson.toJson(msgvo);// 物件轉Json
		System.out.println("WsMessage.saveMbrmsg: Mbrno:" + Mbrno + ", message:" + msgvoJson);
		jedis.lpush(Mbrno, msgvoJson);// 從最左邊放入

		jedis.close();
	}

	public void updateMbrmsg(String Mbrno, Integer index) {// 傳進索引值？
		Jedis jedis = pool.getResource();
		jedis.auth("123456");

		String mbrMsgJson = jedis.lindex(Mbrno, index);// 從索引值取出
		MsgVO msgvo = gson.fromJson(mbrMsgJson, MsgVO.class);
		msgvo.setRead(1);// 設為已讀
		String msgvoJson = gson.toJson(msgvo);
		System.out.println(index);
		System.out.println(msgvoJson);
		jedis.lset(Mbrno, index, msgvoJson);// 存回原本的索引值
		
		System.out.println("updateComplete");

		jedis.close();
	}
	
	public List<MsgVO> getMbrmsg(String Mbrno) {// 每次進頁面都要get，索引值才能保持最新
		Jedis jedis = pool.getResource();
		jedis.auth("123456");

		List<String> list = jedis.lrange(Mbrno, 0, -1);
		List<MsgVO> msgvoList = new ArrayList<MsgVO>();
		for (String strMsgvo : list) {
			MsgVO msgvo = gson.fromJson(strMsgvo, MsgVO.class);
			if (msgvo.getRead() == 0)//拿未讀的訊息
				msgvoList.add(msgvo);
		}

		jedis.close();
		return msgvoList;
	}

	public boolean deleteMbrAllMsg(String Mbrno) {
		Boolean dataRemoved;
		Jedis jedis = pool.getResource();
		jedis.auth("123456");

		if (jedis.del(Mbrno) >= 1) {
			dataRemoved = true;
		} else {
			dataRemoved = false;
		}

		jedis.close();
		return dataRemoved;
	}
}
