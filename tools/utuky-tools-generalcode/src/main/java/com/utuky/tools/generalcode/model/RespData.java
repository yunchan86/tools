package com.utuky.tools.generalcode.model;

public class RespData {

	private int code ;
	private String msg;
	
	public RespData(){}
	public RespData(int code,String msg){
		this.code = code ;
		this.msg = msg;
	}
	
	public void setRespData(int code,String msg) {
		this.code = code ;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
