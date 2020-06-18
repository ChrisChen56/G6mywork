package com.mbrpf.model;

import java.io.Serializable;

public class MbrpfVO implements Serializable{
	private String mbrno;
	private String mbract;
	private String mbrpw;
	private String mbrname;
	private Integer points;
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
	public void setMbrname(String mbrname) {
		this.mbrname = mbrname;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}

}
