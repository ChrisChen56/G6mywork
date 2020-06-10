package com.shgmrp.model;

import java.io.Serializable;

public class ShgmrpVO implements Serializable{
	private String shgmrpno;
	private String shgmno;
	private String suiterno;
	private String detail;
	private Integer status;
	
	public String getShgmrpno() {
		return shgmrpno;
	}
	public void setShgmrpno(String shgmrpno) {
		this.shgmrpno = shgmrpno;
	}
	public String getShgmno() {
		return shgmno;
	}
	public void setShgmno(String shgmno) {
		this.shgmno = shgmno;
	}
	public String getSuiterno() {
		return suiterno;
	}
	public void setSuiterno(String suiterno) {
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
