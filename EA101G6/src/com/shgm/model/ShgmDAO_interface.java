package com.shgm.model;

public interface ShgmDAO_interface {
	public void insert(ShgmVO shgamevo);
	public void update(ShgmVO shgamevo);
	public void delete(String shgmno);
	public ShgmVO findByPrimaryKey(String shgmno);
	public java.util.List<ShgmVO> getall();
}
