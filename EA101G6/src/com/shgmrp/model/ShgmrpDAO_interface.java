package com.shgmrp.model;

import java.util.List;

public interface ShgmrpDAO_interface {
	public void insert(ShgmrpVO shgmrpvo);
	public void update(ShgmrpVO shgmrpvo);
	public void delete(Integer shgmrpno);
	public ShgmrpVO findByPrimaryKey(Integer shgmrpno);
	public List<ShgmrpVO> getAll();
}
