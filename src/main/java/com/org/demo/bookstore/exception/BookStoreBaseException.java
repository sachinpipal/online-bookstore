package com.org.demo.bookstore.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookStoreBaseException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1206332862180836467L;

	public BookStoreBaseException(String message, Throwable cause) {
		super(message, cause);

	}

}
