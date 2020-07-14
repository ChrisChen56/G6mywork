package com.shgmrp.model;

import java.util.Set;

public interface ShgmrpDAO_interface {
	public void insert(ShgmrpVO shgmrpvo);
	public void update(ShgmrpVO shgmrpvo);
	public void delete(String shgmrpno);
	public ShgmrpVO findByPrimaryKey(String shgmrpno);
	public ShgmrpVO findByShgmno(String shgmno);
	public Set<ShgmrpVO> getAll();
	public Set<ShgmrpVO> getAllUncheck();
}
