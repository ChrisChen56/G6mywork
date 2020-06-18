package com.mbrpf.model;

public interface MbrpfDAO_interface {
	public void update(MbrpfVO mbrpfvo);
	public MbrpfVO findByPrimaryKey(String mbrno);
	public java.util.List<MbrpfVO> getall();
}
