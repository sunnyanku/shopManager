package com.bebo.shopmanager.dto;

/**
 * Shop DTO to fetch and send data to controller.
 * @author abhi
 *
 */
public class ShopDTO {
	
	private Long id;
	private String shopName;
	private String country;
	private String postcode;
	private String latitude;
	private String longitude;
	private String username;
	private Long version;
	
	
	public ShopDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ShopDTO(String shopName, String country, String postcode, String username) {
		super();
		this.shopName = shopName;
		this.country = country;
		this.postcode = postcode;
		this.username = username;
	}


	public ShopDTO(Long id, String shopName, String country, String postcode, String latitude, String longitude,
			String username) {
		super();
		this.id = id;
		this.shopName = shopName;
		this.country = country;
		this.postcode = postcode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.username = username;
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
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
		ShopDTO other = (ShopDTO) obj;
		if (shopName == null) {
			if (other.shopName != null)
				return false;
		} else if (!shopName.equals(other.shopName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ShopDTO [id=" + id + ", shopName=" + shopName + ", country=" + country + ", postcode=" + postcode
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", username=" + username + "]";
	}
	
	
	

}
