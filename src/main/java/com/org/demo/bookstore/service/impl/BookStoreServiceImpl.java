package com.org.demo.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.demo.bookstore.exception.BookStoreBaseException;
import com.org.demo.bookstore.model.Book;
import com.org.demo.bookstore.repository.BookStoreRepository;
import com.org.demo.bookstore.request.BookRequestVO;
import com.org.demo.bookstore.request.SearchRequestVO;
import com.org.demo.bookstore.response.BookResponseVO;
import com.org.demo.bookstore.service.BookStoreService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class BookStoreServiceImpl implements BookStoreService {

	@Autowired
	private BookStoreRepository bookStoreRepository;

	@Override
	public BookResponseVO addNewBook(BookRequestVO bookRequestVO) {
		Book book = Book.builder().isbn(bookRequestVO.getIsbn()).author(bookRequestVO.getAuthor())
				.price(bookRequestVO.getPrice().doubleValue()).title(bookRequestVO.getTitle()).build();
		Book savedBook = null;
		try {
			savedBook = bookStoreRepository.save(book);
		} catch (PersistenceException ex) {
			log.error("Error occured  " + this.getClass().getName() + "method :addNewBook () while adding book :"
					+ bookRequestVO.getIsbn(), ex.getCause(), ex.getCause());
			throw new BookStoreBaseException("Error occured in " + this.getClass().getName()
					+ "method :addNewBook () while adding book :" + bookRequestVO.getIsbn(), ex.getCause());
		}
		log.info(book.getTitle() + " book saved successfully.");
		return new BookResponseVO(savedBook);
	}

	@Override
	public List<BookResponseVO> searchBook(SearchRequestVO searchRequestVO) {
		List<Book> books = new ArrayList<>();
		try {
			books = bookStoreRepository.searchBook(StringUtils.lowerCase(searchRequestVO.getIsbn()),
					StringUtils.lowerCase(searchRequestVO.getTitle()),
					StringUtils.lowerCase(searchRequestVO.getAuthor()));
			if (books.isEmpty()) {
				log.info("No result found");
			}
		} catch (PersistenceException ex) {
			log.error(
					"Error occured in " + this.getClass().getName()
							+ "method :searchBook () while searching book isbn: " + searchRequestVO.getIsbn()
							+ ", title :" + searchRequestVO.getTitle() + ", author :" + searchRequestVO.getAuthor(),
					ex.getCause());
			throw new BookStoreBaseException("No result found with given search, isbn: " + searchRequestVO.getIsbn()
					+ ", title :" + searchRequestVO.getTitle() + ", author :" + searchRequestVO.getAuthor(),
					ex.getCause());
		}
		return books.stream().map(book -> new BookResponseVO(book)).collect(Collectors.toList());
	}

}
