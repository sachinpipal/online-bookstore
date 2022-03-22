package com.org.demo.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.demo.bookstore.model.Book;

@Repository
public interface BookStoreRepository extends JpaRepository<Book, Integer> {

	@Query(value = "FROM  Book b WHERE lower(b.isbn) like  %?1% OR lower(b.title) like %?2% OR  lower(b.author) like %?3% ")
	List<Book> searchBook(String isbn, String title, String author);

	@Query(value = "FROM  Book b")
	List<Book> getAllBooks();

}
