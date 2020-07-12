package com.shgmrp.model;

import java.sql.Connection;

import com.shgm.model.ShgmService;
import com.shgm.model.ShgmVO;

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
	
	public void updateUpcheck(ShgmVO shgmvo, Integer status, Connection con) {
		//確定檢舉，下架市集商品
		if(status == 1) {
			shgmvo.setUpcheck(2);
			//取消檢舉或未審核檢舉，上架市集商品
		} else if(status == 2 || status == 0) {
			shgmvo.setUpcheck(1);
		}
		
		ShgmService shgmsvc = new ShgmService();
		shgmsvc.updateShgm(shgmvo.getShgmno(), shgmvo.getSellerno(), shgmvo.getBuyerno(),
				shgmvo.getShgmname(), shgmvo.getPrice(), shgmvo.getIntro(), shgmvo.getImg(),
				shgmvo.getUpcheck(), shgmvo.getUptime(), shgmvo.getTake(), shgmvo.getTakernm(),
				shgmvo.getTakerph(), shgmvo.getAddress(), shgmvo.getBoxstatus(), shgmvo.getPaystatus(),
				shgmvo.getStatus(), shgmvo.getSoldtime(), con);
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
	
	public java.util.Set<ShgmrpVO> getAllShgmrp(){
		
		return dao.getAll();
	}
}
