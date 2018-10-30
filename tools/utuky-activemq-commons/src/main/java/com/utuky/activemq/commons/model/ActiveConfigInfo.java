package com.utuky.activemq.commons.model;

import java.io.Serializable;

public class ActiveConfigInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3259215868424689345L;
	
	private String username ;
	private String pwd ;
	private String brokerUrl;
	private int poolSize ;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getBrokerUrl() {
		return brokerUrl;
	}
	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
	
	
}
