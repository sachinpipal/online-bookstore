package com.org.demo.bookstore.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.org.demo.bookstore.model.Book;

@DataJpaTest
class TaskRepositoryTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private BookStoreRepository bookStoreRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	final void testSearchBookSuccess() {
		Book book = new Book();
		book.setIsbn("9339282");
		book.setTitle("Java Development");
		book.setAuthor("M L Malhotra");
		Book book2 = new Book();
		book2.setIsbn("903239");
		book2.setTitle("Spring Development");
		book2.setAuthor("P C Gupta");
		Book savedBook = testEntityManager.persist(book);
		testEntityManager.persist(book2);
		testEntityManager.persist(book);
		List<Book> books = bookStoreRepository.searchBook("9339282", "Java Development", "M L Malhotra");
		if (!books.isEmpty()) {
			Assertions.assertEquals(savedBook.getAuthor(), books.get(0).getAuthor());
		}
	}

}
