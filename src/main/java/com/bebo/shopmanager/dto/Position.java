package com.bebo.shopmanager.dto;

/**
 * Position to fetch lat, long info in controller.
 * @author abhi
 *
 */
public class Position {

	private String latitude;
	private String longitude;
	private int radius = 10;
	
	
	public Position() {
		super();
	}
	
	
	public Position(String latitude, String longitude, int radius) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
	}


	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	
}
