package com.xinju.bean;

import java.util.List;

public class EbookingBean {

	private List<EpriceBean> EPrice;
	private String code;
	private String msg;
	private String rowCount;
	public List<EpriceBean> getEprice() {
		return EPrice;
	}
	public void setEprice(List<EpriceBean> EPrice) {
		this.EPrice = EPrice;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRowCount() {
		return rowCount;
	}
	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}
	
}
