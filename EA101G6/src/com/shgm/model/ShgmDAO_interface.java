package com.shgm.model;

import java.sql.Connection;
import java.util.*;

import com.mbrpf.model.MbrpfVO;

public interface ShgmDAO_interface {
	public void insertShgm(ShgmVO shgmvo);
	public void update(ShgmVO shgmvo);
	public void update(ShgmVO shgmvo, MbrpfVO mbrpfVO);
	public void update(ShgmVO shgmvo, Connection con);//檢舉更新用的
	public void delete(String shgmno);
	public ShgmVO findByPrimaryKey(String shgmno);
	public ShgmVO getOneForInfo(String shgmno);
	public Set<ShgmVO> getall();
	public Set<ShgmVO> allForSeller(String sellerno);
	public Set<ShgmVO> allForBuyer(String buyerno);
	public Set<ShgmVO> allForPpersonalMkt(String sellerno);
	public Set<ShgmVO> getAllForMain();
	public List<ShgmVO> getAllForInfoShuffle();
	public Set<ShgmVO> searchForMain(String word);
	public Set<ShgmVO> getAllUncheck();
}
