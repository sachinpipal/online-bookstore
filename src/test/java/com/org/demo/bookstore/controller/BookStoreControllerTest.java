package com.org.demo.bookstore.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.org.demo.bookstore.request.BookRequestVO;
import com.org.demo.bookstore.request.OrderRequestVO;
import com.org.demo.bookstore.request.SearchRequestVO;
import com.org.demo.bookstore.response.BookResponseVO;
import com.org.demo.bookstore.response.OrderResponseVO;
import com.org.demo.bookstore.service.BookStoreService;
import com.org.demo.bookstore.service.OrderService;
import com.org.demo.bookstore.util.ObjectMapperUtil;

@ExtendWith(MockitoExtension.class)
class BookStoreControllerTest {

	private MockMvc mockMvc;
	@Mock
	private BookStoreService bookStoreService;
	@Mock
	private OrderService orderService;
	@InjectMocks
	private BookStoreController bookStoreController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(bookStoreController).build();
	}

	@Test
	final void testAddNewBookSuccess() throws Exception {
		BookRequestVO bookRequestVO = new BookRequestVO();
		bookRequestVO.setIsbn("90923");
		bookRequestVO.setTitle("Java Development");
		bookRequestVO.setAuthor("M L Malhotra");
		bookRequestVO.setPrice(new BigDecimal(900));
		bookRequestVO.setQuantity(1);
		BookResponseVO bookResponseVO = new BookResponseVO();
		bookResponseVO.setIsbn("90923");
		bookResponseVO.setTitle("Java Development");
		bookResponseVO.setAuthor("M L Malhotra");
		bookResponseVO.setPrice("900");
		bookRequestVO.setQuantity(1);
		Mockito.when(bookStoreService.addNewBook(bookRequestVO)).thenReturn(bookResponseVO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/books/add-book")
				.accept(MediaType.APPLICATION_JSON).content(ObjectMapperUtil.objectToJson(bookRequestVO))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200, status);
	}

	@Test
	final void testAddNewBookFailure() throws Exception {
		BookRequestVO bookRequestVO = new BookRequestVO();
		bookRequestVO.setTitle("Java Development");
		bookRequestVO.setAuthor("M L Malhotra");
		bookRequestVO.setPrice(new BigDecimal(900));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/books/add-book")
				.accept(MediaType.APPLICATION_JSON).content(ObjectMapperUtil.objectToJson(bookRequestVO))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(400, status);
	}

	@Test
	final void testSearchBookSuccess() throws Exception {
		SearchRequestVO searchRequestVO = new SearchRequestVO();
		searchRequestVO.setIsbn("90923");
		searchRequestVO.setTitle("Java Development");
		searchRequestVO.setAuthor("M L Malhotra");
		BookResponseVO bookResponseVO = new BookResponseVO();
		bookResponseVO.setIsbn("90923");
		bookResponseVO.setTitle("Java Development");
		bookResponseVO.setAuthor("M L Malhotra");
		bookResponseVO.setPrice("900");
		List<BookResponseVO> bookRequestVOList = new ArrayList<>();
		bookRequestVOList.add(bookResponseVO);
		Mockito.when(bookStoreService.searchBook(searchRequestVO)).thenReturn(bookRequestVOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/books/search")
				.accept(MediaType.APPLICATION_JSON).param("isbn", "90923").param("title", "Java Development")
				.param("author", "M L Malhotra").contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200, status);
	}

	@Test
	final void testSearchBookFailure() throws Exception {
		SearchRequestVO searchRequestVO = new SearchRequestVO();
		searchRequestVO.setIsbn("90923");
		searchRequestVO.setTitle("Java Development");
		searchRequestVO.setAuthor("M L Malhotra");
		List<BookResponseVO> bookRequestVOList = new ArrayList<>();
		Mockito.when(bookStoreService.searchBook(searchRequestVO)).thenReturn(bookRequestVOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/books/search")
				.accept(MediaType.APPLICATION_JSON).param("isbn", "90923").param("title", "Java Development")
				.param("author", "M L Malhotra").contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200, status);
	}

	@Test
	final void testSearchMediaCoverageSuccess() throws Exception {
		List<String> titleList = new ArrayList<>();
		titleList.add("Java Development");
		Mockito.when(bookStoreService.searchMediaCoverage("Java Development")).thenReturn(titleList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/v1/books/search-media-coverage/title/{title}", "Java Development")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200, status);
	}

	@Test
	final void testSearchMediaCoverageSuccess_whenEmptyList() throws Exception {
		List<String> titleList = new ArrayList<>();
		Mockito.when(bookStoreService.searchMediaCoverage("Java Development")).thenReturn(titleList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/v1/books/search-media-coverage/title/{title}", "Java Development")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200, status);
	}

	@Test
	final void testBuyBookSuccess() throws Exception {
		OrderRequestVO orderRequestVO = new OrderRequestVO();
		orderRequestVO.setTitle("Java Development");
		orderRequestVO.setQuantity(1);
		List<OrderRequestVO> orderRequestVOList = new ArrayList<>();
		orderRequestVOList.add(orderRequestVO);
		OrderResponseVO orderResponseVO = new OrderResponseVO();
		orderResponseVO.setOrderId("90923");
		orderResponseVO.setTitleList(Arrays.asList("Java Development", "Spring Dev"));
		Mockito.when(orderService.buyBook(orderRequestVOList)).thenReturn(orderResponseVO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/books/buy-book")
				.accept(MediaType.APPLICATION_JSON).content(ObjectMapperUtil.objectToJson(orderRequestVOList))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200, status);
	}

}
