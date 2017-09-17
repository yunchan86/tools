package com.utuky.tools.generalcode.model;

public enum RespCodeEnum {

	SUCCESS(0,"成功"),
    DEFAULT_ERROR(1,"系统初始化"),
    DATA_NO_EXISTS(2,"数据不存在");
	
	private int code ;
	private String msg ;
	
	RespCodeEnum(int code,String msg) {
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
