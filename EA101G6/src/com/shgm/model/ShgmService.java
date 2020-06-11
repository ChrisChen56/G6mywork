package com.shgm.model;

import java.sql.Timestamp;

public class ShgmService {
	
	private ShgmDAO_interface dao;
	
	public ShgmService() {
		dao = new ShgmDAO();
	}
	
	public ShgmVO addShgm(String sellerno, String buyerno, String shgmname, Double price, String intro, byte[] img, Integer upcheck, Timestamp uptime,
			String take, String takernm, Integer takerph, String address, Integer boxstatus, Integer paystatus, Integer status, Timestamp soldtime) {
		
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
		
		dao.insert(shgmvo);
		
		return shgmvo;
	}
	
	public ShgmVO updateShgm(String shgmno,String sellerno, String buyerno, String shgmname, Double price, String intro, byte[] img, Integer upcheck, Timestamp uptime,
			String take, String takernm, Integer takerph, String address, Integer boxstatus, Integer paystatus, Integer status, Timestamp soldtime) {
		
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
		
		dao.update(shgmvo);
		
		return shgmvo;
	}
	
	public void deleteShgm(String shgmno) {
		
		dao.delete(shgmno);
	}
	
	public ShgmVO getOneShgm(String shgmno) {
		
		return dao.findByPrimaryKey(shgmno);
	}
	
	public java.util.List<ShgmVO> getAllShgm(){
		
		return dao.getall();
	}
}
