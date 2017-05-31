package com.bebo.shopmanager.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Shop {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String shopName;
	private String country;
	private String postcode;
	private String latitude;
	private String longitude;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="managerId")
	private Manager manager;
	@Version
	private Long version;

	protected Shop() {}

	/**
	 * 
	 * @param shopName
	 * @param country
	 * @param postcode
	 * @param latitude
	 * @param longitude
	 * @param managerId
	 */
	public Shop(String shopName, String country, String postcode, String latitude, String longitude, Manager manager) {
		super();
		this.shopName = shopName;
		this.country = country;
		this.postcode = postcode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.manager = manager;
	}

	
	public Shop(String shopName, String country, String postcode) {
		super();
		this.shopName = shopName;
		this.country = country;
		this.postcode = postcode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shopName == null) ? 0 : shopName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (shopName == null) {
			if (other.shopName != null)
				return false;
		} else if (!shopName.equals(other.shopName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Shop [id=" + id + ", shopName=" + shopName + ", country=" + country + ", postcode=" + postcode
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", manager=" + manager + ", version="
				+ version + "]";
	}

	



}


