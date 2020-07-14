package com.shgm.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;

import com.mbrpf.model.MbrpfVO;

public class ShgmService {

	private ShgmDAO_interface dao;

	public ShgmService() {
		dao = new ShgmDAO();
	}

	public ShgmVO addShgm(String sellerno, String buyerno, String shgmname, Integer price,
			String intro, byte[] img, Integer upcheck, Timestamp uptime, String take, String takernm, String takerph, String address,
			Integer boxstatus, Integer paystatus, Integer status,Timestamp soldtime) {

		ShgmVO shgmvo = new ShgmVO();

		shgmvo.setSellerno(sellerno);
		shgmvo.setBuyerno(buyerno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);
		shgmvo.setUpcheck(upcheck);
		shgmvo.setUptime(uptime);
		shgmvo.setTake(take);
		shgmvo.setTakernm(takernm);
		shgmvo.setTakerph(takerph);
		shgmvo.setAddress(address);
		shgmvo.setBoxstatus(boxstatus);
		shgmvo.setPaystatus(paystatus);
		shgmvo.setStatus(status);
		shgmvo.setSoldtime(soldtime);
		
		ShgmService shgmsvc = new ShgmService();
		shgmvo = shgmsvc.timeUpdate(shgmvo);
		
		dao.insertShgm(shgmvo);

		return shgmvo;
	}

	public ShgmVO updateShgm(String shgmno, String sellerno, String buyerno, String shgmname, Integer price,
			String intro, byte[] img, Integer upcheck, Timestamp uptime, String take, String takernm, String takerph, String address,
			Integer boxstatus, Integer paystatus, Integer status,Timestamp soldtime) {

		ShgmVO shgmvo = new ShgmVO();
		shgmvo.setShgmno(shgmno);
		shgmvo.setSellerno(sellerno);
		shgmvo.setBuyerno(buyerno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);
		shgmvo.setUpcheck(upcheck);
		shgmvo.setUptime(uptime);
		shgmvo.setTake(take);
		shgmvo.setTakernm(takernm);
		shgmvo.setTakerph(takerph);
		shgmvo.setAddress(address);
		shgmvo.setBoxstatus(boxstatus);
		shgmvo.setPaystatus(paystatus);
		shgmvo.setStatus(status);
		shgmvo.setSoldtime(soldtime);
		
		ShgmService shgmsvc = new ShgmService();
		shgmvo = shgmsvc.timeUpdate(shgmvo);
		
		dao.update(shgmvo);

		return shgmvo;
	}

	public ShgmVO updateShgm(String shgmno, String sellerno, String buyerno, String shgmname, Integer price,
			String intro, byte[] img, Integer upcheck, Timestamp uptime, String take, String takernm, String takerph, String address,
			Integer boxstatus, Integer paystatus, Integer status,Timestamp soldtime, MbrpfVO mbrpfVO) {

		ShgmVO shgmvo = new ShgmVO();
		shgmvo.setShgmno(shgmno);
		shgmvo.setSellerno(sellerno);
		shgmvo.setBuyerno(buyerno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);
		shgmvo.setUpcheck(upcheck);
		shgmvo.setUptime(uptime);
		shgmvo.setTake(take);
		shgmvo.setTakernm(takernm);
		shgmvo.setTakerph(takerph);
		shgmvo.setAddress(address);
		shgmvo.setBoxstatus(boxstatus);
		shgmvo.setPaystatus(paystatus);
		shgmvo.setStatus(status);
		shgmvo.setSoldtime(soldtime);
		
		ShgmService shgmsvc = new ShgmService();
		shgmvo = shgmsvc.timeUpdate(shgmvo);

		dao.update(shgmvo, mbrpfVO);

		return shgmvo;
	}
	
	public ShgmVO updateShgm(String shgmno, String sellerno, String buyerno, String shgmname, Integer price,
			String intro, byte[] img, Integer upcheck, Timestamp uptime, String take, String takernm, String takerph, String address,
			Integer boxstatus, Integer paystatus, Integer status,Timestamp soldtime, Connection con) {

		ShgmVO shgmvo = new ShgmVO();
		shgmvo.setShgmno(shgmno);
		shgmvo.setSellerno(sellerno);
		shgmvo.setBuyerno(buyerno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);
		shgmvo.setUpcheck(upcheck);
		shgmvo.setUptime(uptime);
		shgmvo.setTake(take);
		shgmvo.setTakernm(takernm);
		shgmvo.setTakerph(takerph);
		shgmvo.setAddress(address);
		shgmvo.setBoxstatus(boxstatus);
		shgmvo.setPaystatus(paystatus);
		shgmvo.setStatus(status);
		shgmvo.setSoldtime(soldtime);
		
		ShgmService shgmsvc = new ShgmService();
		ShgmVO shgmvo2 = shgmsvc.timeUpdate(shgmvo);

		dao.update(shgmvo2, con);

		return shgmvo2;
	}

	public ShgmVO timeUpdate(ShgmVO shgmvo) {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());

		if (shgmvo.getUpcheck() == 0) {
			shgmvo.setUptime(null);
			shgmvo.setSoldtime(null);
		}
		if (shgmvo.getUpcheck() == 1) {
			// 上架的市集商品，同時修改成已送達、已付款、已完成，即是訂單完成
			if (shgmvo.getBoxstatus() == 2 && shgmvo.getPaystatus() == 1 && shgmvo.getStatus() == 2) {
				// 資料庫更新上架時間、售出時間，如果之前沒上架，更新上架時間；如果本來就是上架中，那就沿用上架時間
				if (shgmvo.getUptime() == null) {
					shgmvo.setUptime(currentTime);
				}
				// 更新售出時間
				shgmvo.setSoldtime(currentTime);
				// 上架的市集商品，更新上架時間
			} else if (shgmvo.getBoxstatus() == 0 && shgmvo.getPaystatus() == 0 && shgmvo.getStatus() == 0) {
				shgmvo.setUptime(currentTime);
				shgmvo.setSoldtime(null);
			}
		}
		if (shgmvo.getUpcheck() == 2) {
			;// do nothing
		}
		return shgmvo;
	}

	public void deleteShgm(String shgmno) {

		dao.delete(shgmno);
	}

	public ShgmVO getOneShgm(String shgmno) {

		return dao.findByPrimaryKey(shgmno);
	}
	
	public HashMap<String, String> splitAddress(String address) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		// 將address分割為city、area、ads
		String adres = address;
		String city = null;
		String area = null;
		String ads = null;
		String[] citylevel = { "縣", "市", "島" };
		String[] arealevel = { "鄉", "鎮", "島", "區", "市" };
		for (String clevel : citylevel) {
			if (adres.contains(clevel)) {
				city = adres.substring(0, adres.indexOf(clevel) + 1);
				adres = adres.substring(adres.indexOf(clevel) + 1, adres.length());
				for (String alevel : arealevel) {
					if (adres.contains(alevel)) {
						area = adres.substring(0, adres.indexOf(alevel) + 1);
						ads = adres.substring(adres.indexOf(alevel) + 1, adres.length());
					}
				}
			}
		}
		hashmap.put("city", city);
		hashmap.put("area", area);
		hashmap.put("ads", ads);

		return hashmap;
	}

	public ShgmVO getOneForInfo(String shgmno) {

		return dao.getOneForInfo(shgmno);
	}

	public Set<ShgmVO> getAllShgm() {

		return dao.getall();
	}

	public Set<ShgmVO> allForSeller(String sellerno) {

		return dao.allForSeller(sellerno);
	}

	public Set<ShgmVO> allForBuyer(String buyerno) {

		return dao.allForBuyer(buyerno);
	}

	public Set<ShgmVO> getAllForMain() {

		return dao.getAllForMain();
	}
	
	public List<ShgmVO> getAllForInfoShuffle() {

		return dao.getAllForInfoShuffle();
	}
	
	public Set<ShgmVO> allForPersonalMkt(String sellerno) {

		return dao.allForPpersonalMkt(sellerno);
	}

	public Set<ShgmVO> searchForMain(String word) {

		return dao.searchForMain(word);
	}
	
	public Set<ShgmVO> getAllShgmUncheck() {

		return dao.getAllUncheck();
	} 

}
