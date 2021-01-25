package com.masivian.pruebacleancode.dto;

public class GenericResponse {

	String message;

	public GenericResponse(String message) {
		super();
		this.message = message;
	}

	public GenericResponse() {
		super();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
