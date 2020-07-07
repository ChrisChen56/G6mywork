package com.mbrpf.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.mbrpf.model.MbrpfVO;

public class MbrpfService {

	private MbrpfDAO_interface dao;

	public MbrpfService() {
		dao = new MbrpfDAO();
	}

	public MbrpfVO addMbrpf(String mbract, String mbrpw, String mbrname, byte[] mbrimg, Date birth, Integer sex,
			String mail, String phone, String mbrac, String nickname, Integer points, Integer status,
			Integer ratedtotal, Integer startotal, Integer unattend, Integer ttattend) {

		MbrpfVO mbrpfVO = new MbrpfVO();

		mbrpfVO.setMbract(mbract);
		mbrpfVO.setMbrpw(mbrpw);
		mbrpfVO.setMbrname(mbrname);
		mbrpfVO.setMbrimg(mbrimg);
		mbrpfVO.setBirth(birth);
		mbrpfVO.setSex(sex);
		mbrpfVO.setMail(mail);
		mbrpfVO.setPhone(phone);
		mbrpfVO.setMbrac(mbrac);
		mbrpfVO.setNickname(nickname);
		mbrpfVO.setPoints(points);
		mbrpfVO.setStatus(status);
		mbrpfVO.setRatedtotal(ratedtotal);
		mbrpfVO.setStartotal(startotal);
		mbrpfVO.setUnattend(unattend);
		mbrpfVO.setTtattend(ttattend);
		dao.insert(mbrpfVO);

		return mbrpfVO;
	}

	public MbrpfVO updateMbrpf(String mbrno, String mbract, String mbrpw, String mbrname, byte[] mbrimg,
			java.sql.Date birth, Integer sex, String mail, String phone, String mbrac, String nickname, Integer points,
			Integer status, Integer ratedtotal, Integer startotal, Integer unattend, Integer ttattend) {

		MbrpfVO mbrpfVO = new MbrpfVO();

		mbrpfVO.setMbrno(mbrno);
		mbrpfVO.setMbract(mbract);
		mbrpfVO.setMbrpw(mbrpw);
		mbrpfVO.setMbrname(mbrname);
		mbrpfVO.setMbrimg(mbrimg);
		mbrpfVO.setBirth(birth);
		mbrpfVO.setSex(sex);
		mbrpfVO.setMail(mail);
		mbrpfVO.setPhone(phone);
		mbrpfVO.setMbrac(mbrac);
		mbrpfVO.setNickname(nickname);
		mbrpfVO.setPoints(points);
		mbrpfVO.setStatus(status);
		mbrpfVO.setRatedtotal(ratedtotal);
		mbrpfVO.setStartotal(startotal);
		mbrpfVO.setUnattend(unattend);
		mbrpfVO.setTtattend(ttattend);
		dao.update(mbrpfVO);

		return mbrpfVO;
	}

	public void deleteMbrpf(String mbrno) {
		dao.delete(mbrno);
	}

	public MbrpfVO getOneMbrpf(String mbrno) {
		return dao.findByPrimaryKey(mbrno);
	}

	public List<MbrpfVO> getAll() {
		return dao.getAll();
	}

	public MbrpfVO getOneMbract(String mbract) {
		return dao.findByMbract(mbract);
	}

	public MbrpfVO checkLogin(String mbract) {
		return dao.login(mbract);
	}
	
	public void updateMbrpf(MbrpfVO mbrpfVO ,Connection conn) {
		dao.updatePoint(mbrpfVO, conn);
	}
	
	public void updateMbrpf(MbrpfVO mbrpfVO) {
		dao.updatePoint(mbrpfVO);
	}

}
