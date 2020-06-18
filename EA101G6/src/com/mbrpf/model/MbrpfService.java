package com.mbrpf.model;

import java.util.List;

public class MbrpfService {
	
	private MbrpfDAO_interface dao;
	
	public MbrpfService() {
		dao = new MbrpfDAO();
	}
	
	public MbrpfVO getByActPw(String mbract, String mbrpw) {
		
		return dao.findByActPw(mbract, mbrpw);
	}
	
	public boolean check(String mbract, String mbrpw) {
		System.out.println("1");
		return dao.check(mbract, mbrpw);
	}
	
	public MbrpfVO update(String mbrno, Integer points) {
		
		MbrpfVO mbrpfvo = new MbrpfVO();
		mbrpfvo.setMbrno(mbrno);
		mbrpfvo.setPoints(points);
		
		dao.update(mbrpfvo);
		
		return mbrpfvo;
	}
	
	public MbrpfVO getOneMbrpf(String mbrno) {
		
		return dao.findByPrimaryKey(mbrno);
	}
	
	public List<MbrpfVO> getAllMbrpf() {
		
		return dao.getall();
	}
}
