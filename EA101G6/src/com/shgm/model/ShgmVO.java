package com.shgm.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ShgmVO implements Serializable{
	private String shgmno;
	private String sellerno;
	private String buyerno;
	private String shgmname;
	private Double price;
	private String intro;
	private byte[] img;
	private Integer upcheck;
	private Timestamp uptime;
	private String take;
	private String takernm;
	private Integer takerph;
	private String address;
	private Integer boxstatus;
	private Integer paystatus;
	private Integer status;
	private Timestamp soldtime;
	
	public String getShgmno() {
		return shgmno;
	}
	public void setShgmno(String shgmno) {
		this.shgmno = shgmno;
	}
	public String getSellerno() {
		return sellerno;
	}
	public void setSellerno(String sellerno) {
		this.sellerno = sellerno;
	}
	public String getBuyerno() {
		return buyerno;
	}
	public void setBuyerno(String buyerno) {
		this.buyerno = buyerno;
	}
	public String getShgmname() {
		return shgmname;
	}
	public void setShgmname(String shgmname) {
		this.shgmname = shgmname;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public Integer getUpcheck() {
		return upcheck;
	}
	public void setUpcheck(Integer upcheck) {
		this.upcheck = upcheck;
	}
	public Timestamp getUptime() {
		return uptime;
	}
	public void setUptime(Timestamp uptime) {
		this.uptime = uptime;
	}
	public String getTake() {
		return take;
	}
	public void setTake(String take) {
		this.take = take;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getBoxstatus() {
		return boxstatus;
	}
	public void setBoxstatus(Integer boxstatus) {
		this.boxstatus = boxstatus;
	}
	public Integer getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
	}
	public Timestamp getSoldtime() {
		return soldtime;
	}
	public void setSoldtime(Timestamp soldtime) {
		this.soldtime = soldtime;
	}
	public String getTakernm() {
		return takernm;
	}
	public void setTakernm(String takernm) {
		this.takernm = takernm;
	}
	public Integer getTakerph() {
		return takerph;
	}
	public void setTakerph(Integer takerph) {
		this.takerph = takerph;
	}
}
