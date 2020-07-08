package com.shgm.model;

import java.sql.Connection;

import com.mbrpf.model.MbrpfVO;

public interface ShgmDAO_interface {
	public void insertSold(ShgmVO shgmvo);
	public void insertCheck1(ShgmVO shgmvo);
	public void insertNocheck(ShgmVO shgmvo);
	public void update(ShgmVO shgmvo);
	public void update(ShgmVO shgmvo, MbrpfVO mbrpfVO);
	public void update(ShgmVO shgmvo, Connection con);
	public java.sql.Timestamp soldtimeCT(String shgmno, Connection con);
	public java.sql.Timestamp uptimeCT(String shgmno, Connection con);
	public void soldtimeNU(String shgmno, Connection con);
	public void uptimeNU(String shgmno, Connection con);
	public void delete(String shgmno);
	public ShgmVO findByPrimaryKey(String shgmno);
	public ShgmVO getOneForInfo(String shgmno);
	public java.util.List<ShgmVO> allForSeller(String sellerno);
	public java.util.List<ShgmVO> allForBuyer(String buyerno);
	public java.util.List<ShgmVO> getall();
	public java.util.List<ShgmVO> getAllForMain();
	public java.util.List<ShgmVO> searchForMain(String word);
}
