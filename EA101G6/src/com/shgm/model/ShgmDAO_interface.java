package com.shgm.model;

public interface ShgmDAO_interface {
	public void insertSold(ShgmVO shgmvo);
	public void insertCheck1(ShgmVO shgmvo);
	public void insertNocheck(ShgmVO shgmvo);
	public void sellshgm(ShgmVO shgmvo);
	public void update(ShgmVO shgmvo);
	public void updateCheck1(ShgmVO shgmvo);
	public void sellerUpdate(ShgmVO shgmvo);
	public void dealingshgm(ShgmVO shgmvo);
	public void sold(String shgmno);
	public void up(String shgmno);
	public void delete(String shgmno);
	public ShgmVO findByPrimaryKey(String shgmno);
	public ShgmVO getOneForInfo(String shgmno);
	public java.util.List<ShgmVO> getall();
	public java.util.List<ShgmVO> getAllForMain();
}
