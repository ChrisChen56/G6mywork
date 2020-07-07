package com.shgm.model;

import java.sql.Connection;
import java.sql.Timestamp;

import com.mbrpf.model.MbrpfVO;

public class ShgmService {

	private ShgmDAO_interface dao;

	public ShgmService() {
		dao = new ShgmDAO();
	}

	public ShgmVO addShgm(String sellerno, String buyerno, String shgmname, Integer price, String intro, byte[] img,
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

		if (upcheck == 1) {
			dao.insertCheck1(shgmvo);
		} else if (upcheck == 1 && boxstatus == 2 && paystatus == 1 && status == 2) {
			dao.insertSold(shgmvo);
		} else {
			dao.insertNocheck(shgmvo);
		}

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

	public void upcheckUpdate(Integer upcheck, String shgmno) {

		dao.upcheckUpdate(upcheck, shgmno);
	}

	public void boxstatusUpdate(Integer boxstatus, String shgmno) {

		dao.boxstatusUpdate(boxstatus, shgmno);
	}

	public void statusUpdate(Integer status, String shgmno) {

		dao.statusUpdate(status, shgmno);
	}

	public ShgmVO sellerUpdate(String shgmno, String shgmname, Integer price, String intro, byte[] img) {

		ShgmVO shgmvo = new ShgmVO();
		shgmvo.setShgmno(shgmno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);

		dao.sellerUpdate(shgmvo);

		return shgmvo;
	}

	public ShgmVO sellerUpdate(String shgmno, String shgmname, Integer price, String intro, byte[] img,
			MbrpfVO mbrpfVO) {

		ShgmVO shgmvo = new ShgmVO();
		shgmvo.setShgmno(shgmno);
		shgmvo.setShgmname(shgmname);
		shgmvo.setPrice(price);
		shgmvo.setIntro(intro);
		shgmvo.setImg(img);

		dao.sellerUpdate(shgmvo, mbrpfVO);

		return shgmvo;
	}

	public ShgmVO buyerupdate(String shgmno, String buyerno, String take, String takernm, String takerph,
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

		dao.buyerupdate(shgmvo);

		return shgmvo;
	}
	
	public ShgmVO buyerupdate(String shgmno, String buyerno, String take, String takernm, String takerph,
			String address, Integer boxstatus, Integer paystatus, Integer status, MbrpfVO mbrpfVO) {

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

		dao.buyerupdate(shgmvo, mbrpfVO);

		return shgmvo;
	}
	
	public void timeUpdate(ShgmVO shgmvo, Connection con) {
		//判斷後呼叫自身dao的方法
		if(???)
	}


	public Timestamp soldtimeCT(String shgmno, Connection con) {

		return dao.soldtimeCT(shgmno, con);
	}

	public void soldtimeNU(String shgmno, Connection con) {

		dao.soldtimeNU(shgmno, con);
	}

	public Timestamp uptimeCT(String shgmno, Connection con) {

		return dao.uptimeCT(shgmno, con);
	}

	public void uptimeNU(String shgmno, Connection con) {

		dao.uptimeNU(shgmno, con);
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

	public java.util.List<ShgmVO> allForSeller(String sellerno) {

		return dao.allForSeller(sellerno);
	}

	public java.util.List<ShgmVO> allForBuyer(String buyerno) {

		return dao.allForBuyer(buyerno);
	}

	public java.util.List<ShgmVO> getAllForMain() {

		return dao.getAllForMain();
	}

//	public ShgmVO updateShgm
}
