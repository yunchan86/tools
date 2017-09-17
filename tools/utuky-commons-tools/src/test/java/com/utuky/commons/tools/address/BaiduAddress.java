package com.utuky.commons.tools.address;

/**
 * @since 2017-09-17
 * @author Frain
 *
 */
public class BaiduAddress {

	private String name;
	private String uid ;
	private String city ;
	private String district;
	private String business;
	private String cityid;
	private BaiduLocation location;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public BaiduLocation getLocation() {
		return location;
	}
	public void setLocation(BaiduLocation location) {
		this.location = location;
	}
	
}
