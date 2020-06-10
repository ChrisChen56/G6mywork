package com.shgmrp.model;

public class ShgmrpService {
	
	private ShgmrpDAO_interface dao;
	
	public ShgmrpService() {
		dao = new ShgmrpDAO();
	}
	
	public ShgmrpVO addShgmrp(String shgmno, String suiterno, String detail, Integer status) {
		
		ShgmrpVO shgmrpvo = new ShgmrpVO();
		shgmrpvo.setShgmno(shgmno);
		shgmrpvo.setSuiterno(suiterno);
		shgmrpvo.setDetail(detail);
		shgmrpvo.setStatus(status);
		dao.insert(shgmrpvo);
		
		return shgmrpvo;
	}
	
	public ShgmrpVO updateShgmrp(String shgmrpno, String shgmno, String suiterno, String detail, Integer status) {
		
	}
}
