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
		
		ShgmrpVO shgmrpvo = new ShgmrpVO();
		shgmrpvo.setShgmrpno(shgmrpno);
		shgmrpvo.setShgmno(shgmno);
		shgmrpvo.setSuiterno(suiterno);
		shgmrpvo.setDetail(detail);
		shgmrpvo.setStatus(status);
		dao.update(shgmrpvo);
		
		return shgmrpvo;
	}
	
	public void updateStatus(Integer status, String shgmrpno) {
		
		dao.updateStatus(status, shgmrpno);
	}
	
	public void deleteShgmrp(String shgmrpno) {
		
		dao.delete(shgmrpno);
	}
	
	public ShgmrpVO getOneShgmrp(String shgmrpno) {
		
		return dao.findByPrimaryKey(shgmrpno);
	}
	
	public ShgmrpVO getOnerpByShgmno(String shgmno) {
		
		return dao.findByShgmno(shgmno);
	}
	
	public java.util.List<ShgmrpVO> getAllShgmrp(){
		
		return dao.getAll();
	}
}
