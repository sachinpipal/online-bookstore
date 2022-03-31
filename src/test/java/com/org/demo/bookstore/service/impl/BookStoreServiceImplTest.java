package com.org.demo.bookstore.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;

import com.org.demo.bookstore.model.Book;
import com.org.demo.bookstore.repository.BookStoreRepository;
import com.org.demo.bookstore.request.BookRequestVO;
import com.org.demo.bookstore.request.SearchRequestVO;
import com.org.demo.bookstore.response.BookResponseVO;
import com.org.demo.bookstore.response.PostResponseVO;

import reactor.core.publisher.Flux;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class BookStoreServiceImplTest {

	@Mock
	private BookStoreRepository bookStoreRepository;
	@InjectMocks
	private BookStoreServiceImpl bookStoreService;
	@Autowired
	private TestEntityManager testEntityManager;

	private WebClient.Builder webClientBuilder = mock(WebClient.Builder.class);
	private WebClient webClient = mock(WebClient.class);

	private RequestHeadersUriSpec requestBodyUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
	private WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
	private WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

	@BeforeEach
	void setup() {
		Mockito.when(webClientBuilder.baseUrl("localhost/")).thenReturn(webClientBuilder);
		Mockito.when(webClientBuilder.build()).thenReturn(webClient);
	}

	@Test
	final void testAddNewBookSuccess() {
		BookRequestVO bookRequestVO = new BookRequestVO();
		bookRequestVO.setIsbn("90923");
		bookRequestVO.setTitle("Java Development");
		bookRequestVO.setAuthor("M L Malhotra");
		bookRequestVO.setPrice(new BigDecimal(900));
		Book savedbook = new Book();
		savedbook.setIsbn("90923");
		savedbook.setTitle("Java Development");
		savedbook.setAuthor("M L Malhotra");
		savedbook.setPrice(900d);
		testEntityManager.persist(savedbook);
		Mockito.when(bookStoreRepository.save(any(Book.class))).thenReturn(savedbook);
		BookResponseVO bookResponseVO = bookStoreService.addNewBook(bookRequestVO);
		Assertions.assertEquals(bookResponseVO.getIsbn(), bookResponseVO.getIsbn());
	}

	@Test
	final void testSearchBookSuccess() {
		SearchRequestVO searchRequestVO = new SearchRequestVO();
		searchRequestVO.setIsbn("90923");
		searchRequestVO.setTitle("java development");
		searchRequestVO.setAuthor("m l malhotra");
		Book savedbook = new Book();
		savedbook.setIsbn("90923");
		savedbook.setTitle("Java Development");
		savedbook.setAuthor("M L Malhotra");
		savedbook.setPrice(900d);
		testEntityManager.persist(savedbook);
		List<Book> books = new ArrayList<>();
		books.add(savedbook);
		Mockito.when(bookStoreRepository.searchBook(searchRequestVO.getIsbn(), searchRequestVO.getTitle(),
				searchRequestVO.getAuthor())).thenReturn(books);
		List<BookResponseVO> bookResponseVOList = bookStoreService.searchBook(searchRequestVO);
		Assertions.assertEquals(savedbook.getTitle(), bookResponseVOList.get(0).getTitle());
	}

	@Test
	final void testSearchMediaCoverageSuccess() {
		when(webClient.get()).thenReturn(requestBodyUriSpec);
		when(requestBodyUriSpec.uri("/posts")).thenReturn(requestBodySpec);
		when(requestBodySpec.attribute("title", "provident")).thenReturn(requestBodySpec);
		when(requestBodySpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToFlux(PostResponseVO.class))
				.thenReturn(Flux.just(new PostResponseVO(1, 1, "provident", "provident")));
		List<String> titleList = bookStoreService.searchMediaCoverage("provident");
		Assertions.assertEquals("provident", titleList.get(0));
	}

}
