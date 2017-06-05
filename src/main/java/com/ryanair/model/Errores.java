package com.ryanair.model;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonView;
import com.ryanair.views.Views;


public class Errores extends RuntimeException {

	private static final long serialVersionUID = 1015936872621785329L;
	private HttpStatus code;
	private String error;
	private String message;

	public Errores() {

	}

	public Errores(String error, String message) {
		this.error = error;
		this.message = message;
	}

	public Errores(HttpStatus code, String error, String message) {
		this.code = code;
		this.error = error;
		this.message = message;
	}

	public Errores(Exception ex) {
		this.error = ex.getMessage();
		Throwable t = ex.getCause();
		if (t != null)
			this.message = t.getMessage();
	}

	@JsonView(Views.List.class)
	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}

	@JsonView(Views.List.class)
	public String getError() {
		return error;
	}

	public void setError(String description) {
		this.error = description;
	}

	@JsonView(Views.List.class)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
