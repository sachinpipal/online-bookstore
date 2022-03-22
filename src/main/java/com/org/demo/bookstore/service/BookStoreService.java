package com.org.demo.bookstore.service;

import java.util.List;

import com.org.demo.bookstore.request.BookRequestVO;
import com.org.demo.bookstore.request.SearchRequestVO;
import com.org.demo.bookstore.response.BookResponseVO;

public interface BookStoreService {

	public BookResponseVO addNewBook(BookRequestVO bookRequestVO);

	public List<BookResponseVO> searchBook(SearchRequestVO searchRequestVO);

}
