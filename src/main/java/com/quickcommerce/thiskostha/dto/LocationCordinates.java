package com.quickcommerce.thiskostha.dto;

public class LocationCordinates {
	private double longitude;
	private double latitude;
	public LocationCordinates(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public LocationCordinates() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	

}
