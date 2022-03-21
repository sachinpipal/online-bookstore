package com.org.demo.bookstore.response;

import com.org.demo.bookstore.model.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseVO {

	private Integer Id;
	private String title;
	private String isbn;
	private String author;
	private String price;

	public BookResponseVO(Book book) {
		this.Id = book.getId();
		this.title = book.getTitle();
		this.isbn = book.getIsbn();
		this.author = book.getAuthor();
		this.price = book.getPrice().toString();
	}

}
