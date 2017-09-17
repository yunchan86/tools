package com.utuky.commons.tools.address;

/**
 * @since 2017-09-17
 * @author Frain
 *
 */
public class BaiduLocation {

	private double lng;//经度
	private double lat;//纬度
	
	public BaiduLocation(){}
	public BaiduLocation(double lng,double lat) {
		this.lng = lng ;
		this.lat = lat ;
	}
	
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	
}
