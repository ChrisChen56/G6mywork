package com.shgm.model;

public interface ShgmDAO_interface {
	public void insertSold(ShgmVO shgmvo);
	public void insertCheck1(ShgmVO shgmvo);
	public void insertNocheck(ShgmVO shgmvo);
	public void sellshgm(ShgmVO shgmvo);
	public void update(ShgmVO shgmvo);
	public void upcheckUpdate(Integer upcheck, String shgmno);
	public void sellerUpdate(ShgmVO shgmvo);
	public void dealingshgm(ShgmVO shgmvo);
	public void soldtimeCT(String shgmno);
	public void uptimeCT(String shgmno);
	public void soldtimeNU(String shgmno);
	public void uptimeNU(String shgmno);
	public void delete(String shgmno);
	public ShgmVO findByPrimaryKey(String shgmno);
	public ShgmVO getOneForInfo(String shgmno);
	public java.util.List<ShgmVO> allForSeller(String sellerno);
	public java.util.List<ShgmVO> allForBuyer(String buyerno);
	public java.util.List<ShgmVO> getall();
	public java.util.List<ShgmVO> getAllForMain();
}
