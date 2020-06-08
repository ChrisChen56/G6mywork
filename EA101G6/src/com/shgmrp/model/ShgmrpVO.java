package com.shgmrp.model;

import java.io.Serializable;

public class ShgmrpVO implements Serializable{
	private Integer shgmrpno;
	private Integer shgmno;
	private Integer suiterno;
	private String detail;
	private Integer status;
	
	public Integer getShgmrpno() {
		return shgmrpno;
	}
	public void setShgmrpno(Integer shgmrpno) {
		this.shgmrpno = shgmrpno;
	}
	public Integer getShgmno() {
		return shgmno;
	}
	public void setShgmno(Integer shgmno) {
		this.shgmno = shgmno;
	}
	public Integer getSuiterno() {
		return suiterno;
	}
	public void setSuiterno(Integer suiterno) {
		this.suiterno = suiterno;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
