package com.bebo.shopmanager.util;


public class CustomErrorType {

    private String errorMessage;

    
    public CustomErrorType() {
		super();
	}

	public CustomErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
