package com.mbrpf.model;
import java.sql.Date;

public class MbrpfVO implements java.io.Serializable{
	private String mbrno;
	private String mbract;
	private String mbrpw;
	private String mbrname;
	private Date birth;
	private byte[] mbrimg;
	private Integer sex;
	private String mail;
	private String phone;
	private String mbrac;
	private String nickname;
	private Integer points;
	private Integer status;
	private Integer ratedtotal;
	private Integer startotal;
	private Integer unattend;
	private Integer ttattend;
	
	
	public String getMbrno() {
		return mbrno;
	}
	public void setMbrno(String mbrno) {
		this.mbrno = mbrno;
	}
	public String getMbract() {
		return mbract;
	}
	public void setMbract(String mbract) {
		this.mbract = mbract;
	}
	public String getMbrpw() {
		return mbrpw;
	}
	public void setMbrpw(String mbrpw) {
		this.mbrpw = mbrpw;
	}
	public String getMbrname() {
		return mbrname;
	}
	public void setMbrname(String name) {
		this.mbrname = name;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public byte[] getMbrimg() {
		return mbrimg;
	}
	public void setMbrimg(byte[] mbrimg) {
		this.mbrimg = mbrimg;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMbrac() {
		return mbrac;
	}
	public void setMbrac(String mbrac) {
		this.mbrac = mbrac;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRatedtotal() {
		return ratedtotal;
	}
	public void setRatedtotal(Integer ratedtotal) {
		this.ratedtotal = ratedtotal;
	}
	public Integer getStartotal() {
		return startotal;
	}
	public void setStartotal(Integer startotal) {
		this.startotal = startotal;
	}
	public Integer getUnattend() {
		return unattend;
	}
	public void setUnattend(Integer unattend) {
		this.unattend = unattend;
	}
	public Integer getTtattend() {
		return ttattend;
	}
	public void setTtattend(Integer ttattend) {
		this.ttattend = ttattend;
	}

}
