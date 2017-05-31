package com.bebo.shopmanager.util;

import java.util.Map;

public class CustomSuccessMessage {

	 private String message;
	 private Map<?, ?> detail;

	 
	public CustomSuccessMessage() {
		super();
	}

	public CustomSuccessMessage(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<?, ?> getDetail() {
		return detail;
	}

	public void setDetail(Map<?, ?> detail) {
		this.detail = detail;
	}

	
	
	 
}
