package com.org.demo.bookstore.util;

public class MessageResponse {

	private String message;
	private Object data;

	public MessageResponse(String message, Object userResponse) {

		this.message = message;
		this.data = userResponse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
