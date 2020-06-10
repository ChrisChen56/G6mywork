package com.shgmrp.model;

import java.util.List;

public interface ShgmrpDAO_interface {
	public void insert(ShgmrpVO shgmrpvo);
	public void update(ShgmrpVO shgmrpvo);
	public void delete(String shgmrpno);
	public ShgmrpVO findByPrimaryKey(String shgmrpno);
	public List<ShgmrpVO> getAll();
}
