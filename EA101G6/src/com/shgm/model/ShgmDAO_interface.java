package com.shgm.model;

public interface ShgmDAO_interface {
	public void insert(ShgmVO shgmvo);
	public void sellshgm(ShgmVO shgmvo);
	public void update(ShgmVO shgmvo);
	public void delete(String shgmno);
	public ShgmVO findByPrimaryKey(String shgmno);
	public java.util.List<ShgmVO> getall();
}
