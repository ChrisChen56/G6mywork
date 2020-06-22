package com.shgm.model;

import java.sql.Timestamp;

public class ShgmService {

	private ShgmDAO_interface dao;

	public ShgmService() {
		dao = new ShgmDAO();
	}

	public ShgmVO addShgmSold(String sellerno, String buyerno, String shgmname, Integer price, String intro, byte[] img,
			Integer upcheck, String take, String takernm, String takerph, String address, Integer boxstatus,
			Integer paystatus, Integer status) {

		ShgmVO shgmvo = new ShgmVO();

		shgmvo.setSellerno(sellerno);
		shgmvo.setBuyerno(buyerno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);
		shgmvo.setUpcheck(upcheck);
		shgmvo.setTake(take);
		shgmvo.setTakernm(takernm);
		shgmvo.setTakerph(takerph);
		shgmvo.setAddress(address);
		shgmvo.setBoxstatus(boxstatus);
		shgmvo.setPaystatus(paystatus);
		shgmvo.setStatus(status);

		dao.insertSold(shgmvo);

		return shgmvo;
	}

	public ShgmVO addShgmNocheck(String sellerno, String buyerno, String shgmname, Integer price, String intro,
			byte[] img, Integer upcheck, String take, String takernm, String takerph, String address, Integer boxstatus,
			Integer paystatus, Integer status) {

		ShgmVO shgmvo = new ShgmVO();

		shgmvo.setSellerno(sellerno);
		shgmvo.setBuyerno(buyerno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);
		shgmvo.setUpcheck(upcheck);
		shgmvo.setTake(take);
		shgmvo.setTakernm(takernm);
		shgmvo.setTakerph(takerph);
		shgmvo.setAddress(address);
		shgmvo.setBoxstatus(boxstatus);
		shgmvo.setPaystatus(paystatus);
		shgmvo.setStatus(status);

		dao.insertNocheck(shgmvo);

		return shgmvo;
	}

	public ShgmVO addShgmCheck1(String sellerno, String buyerno, String shgmname, Integer price, String intro,
			byte[] img, Integer upcheck, String take, String takernm, String takerph, String address, Integer boxstatus,
			Integer paystatus, Integer status) {

		ShgmVO shgmvo = new ShgmVO();

		shgmvo.setSellerno(sellerno);
		shgmvo.setBuyerno(buyerno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);
		shgmvo.setUpcheck(upcheck);
		shgmvo.setTake(take);
		shgmvo.setTakernm(takernm);
		shgmvo.setTakerph(takerph);
		shgmvo.setAddress(address);
		shgmvo.setBoxstatus(boxstatus);
		shgmvo.setPaystatus(paystatus);
		shgmvo.setStatus(status);

		dao.insertCheck1(shgmvo);

		return shgmvo;
	}

	public ShgmVO sellShgm(String sellerno, String shgmname, Integer price, String intro, byte[] img) {

		ShgmVO shgmvo = new ShgmVO();
		shgmvo.setSellerno(sellerno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);

		dao.sellshgm(shgmvo);

		return shgmvo;

	}

	public ShgmVO updateShgm(String shgmno, String sellerno, String buyerno, String shgmname, Integer price,
			String intro, byte[] img, Integer upcheck, String take, String takernm, String takerph, String address,
			Integer boxstatus, Integer paystatus, Integer status) {

		ShgmVO shgmvo = new ShgmVO();
		shgmvo.setShgmno(shgmno);
		shgmvo.setSellerno(sellerno);
		shgmvo.setBuyerno(buyerno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);
		shgmvo.setUpcheck(upcheck);
		shgmvo.setTake(take);
		shgmvo.setTakernm(takernm);
		shgmvo.setTakerph(takerph);
		shgmvo.setAddress(address);
		shgmvo.setBoxstatus(boxstatus);
		shgmvo.setPaystatus(paystatus);
		shgmvo.setStatus(status);

		dao.update(shgmvo);

		return shgmvo;
	}

	public ShgmVO updateShgmCheck1(String shgmno, String sellerno, String buyerno, String shgmname, Integer price,
			String intro, byte[] img, Integer upcheck, String take, String takernm, String takerph, String address,
			Integer boxstatus, Integer paystatus, Integer status) {

		ShgmVO shgmvo = new ShgmVO();
		shgmvo.setShgmno(shgmno);
		shgmvo.setSellerno(sellerno);
		shgmvo.setBuyerno(buyerno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);
		shgmvo.setUpcheck(upcheck);
		shgmvo.setTake(take);
		shgmvo.setTakernm(takernm);
		shgmvo.setTakerph(takerph);
		shgmvo.setAddress(address);
		shgmvo.setBoxstatus(boxstatus);
		shgmvo.setPaystatus(paystatus);
		shgmvo.setStatus(status);

		dao.updateCheck1(shgmvo);

		return shgmvo;
	}

//	public ShgmVO sellerUpdate(String shgmno, String shgmname, Integer price, String intro, byte[] img) {
//
//		ShgmVO shgmvo = new ShgmVO();
//		shgmvo.setShgmno(shgmno);
//		shgmvo.setShgmname(shgmname);
//		shgmvo.setPrice(price);
//		shgmvo.setIntro(intro);
//		shgmvo.setImg(img);
//
//		dao.sellerUpdate(shgmvo);
//
//		return shgmvo;
//	}

	public ShgmVO dealingshgm(String shgmno, String buyerno, String take, String takernm, String takerph,
			String address, Integer boxstatus, Integer paystatus, Integer status) {

		ShgmVO shgmvo = new ShgmVO();
		shgmvo.setShgmno(shgmno);
		shgmvo.setBuyerno(buyerno);
		shgmvo.setTake(take);
		shgmvo.setTakernm(takernm);
		shgmvo.setTakerph(takerph);
		shgmvo.setAddress(address);
		shgmvo.setBoxstatus(boxstatus);
		shgmvo.setPaystatus(paystatus);
		shgmvo.setStatus(status);

		dao.dealingshgm(shgmvo);

		return shgmvo;
	}

	public void soldShgm(String shgmno) {

		dao.sold(shgmno);
	}

	public void upShgm(String shgmno) {

		dao.up(shgmno);
	}

	public void deleteShgm(String shgmno) {

		dao.delete(shgmno);
	}

	public ShgmVO getOneShgm(String shgmno) {

		return dao.findByPrimaryKey(shgmno);
	}
	
	public ShgmVO getOneForInfo(String shgmno) {
		
		return dao.getOneForInfo(shgmno);
	}

	public java.util.List<ShgmVO> getAllShgm() {

		return dao.getall();
	}
	
	public java.util.List<ShgmVO> getAllForMain() {

		return dao.getAllForMain();
		
	}
}
