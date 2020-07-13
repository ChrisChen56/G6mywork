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
		String MbrnoKey = Mbrno + ":Msg";
		//是空的話就從1開始
		if(jedis.llen(MbrnoKey) == 0) {
			msgvo.setIndex(1);
		} else {
			//不是空的，取得最左邊的值
			strMsgVO = jedis.lindex(MbrnoKey, 0);
			MsgVO msgvoTemp = gson.fromJson(strMsgVO, MsgVO.class);
			index = msgvoTemp.getIndex();
			msgvo.setIndex(index+1);//最左邊的索引值+1
		}
		
		msgvo.setRead(0);// 設為未讀
		msgvo.setMessage(message);// 放入訊息
		String msgvoJson = gson.toJson(msgvo);// 物件轉Json
		System.out.println("WsMessage.saveMbrmsg: Mbrno:" + MbrnoKey + ", message:" + msgvoJson);
		jedis.lpush(MbrnoKey, msgvoJson);// 從最左邊放入(舊的在右邊)

		jedis.close();
	}

	public void updateMbrmsg(String Mbrno, Integer index) {// 傳進索引值
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		
		String MbrnoKey = Mbrno + ":Msg";
		String mbrMsgJson = jedis.lindex(MbrnoKey, index);// 從索引值取出
		System.out.println(mbrMsgJson);
		MsgVO msgvo = gson.fromJson(mbrMsgJson, MsgVO.class);
		msgvo.setRead(1);// 設為已讀
		String msgvoJson = gson.toJson(msgvo);
		System.out.println(index);
		System.out.println(msgvoJson);
		jedis.lset(MbrnoKey, index, msgvoJson);// 存回原本的索引值
		
		System.out.println("updateComplete");

		jedis.close();
	}
	
	public List<MsgVO> getRead0Mbrmsg(String Mbrno) {// 每次進頁面都要get，索引值才能保持最新
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		
		String MbrnoKey = Mbrno + ":Msg";
		List<String> list = jedis.lrange(MbrnoKey, 0, -1);
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
		
		String MbrnoKey = Mbrno + ":Msg";
		if (jedis.del(MbrnoKey) >= 1) {
			dataRemoved = true;
		} else {
			dataRemoved = false;
		}

		jedis.close();
		return dataRemoved;
	}
}
