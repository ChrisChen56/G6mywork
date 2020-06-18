package com.mbrpf.model;

public interface MbrpfDAO_interface {
	public boolean check(String mbract, String mbrpw);
	public MbrpfVO findByActPw(String mbract, String mbrpw);
	public void update(MbrpfVO mbrpfvo);
	public MbrpfVO findByPrimaryKey(String mbrno);
	public java.util.List<MbrpfVO> getall();
}
