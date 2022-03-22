package com.org.demo.bookstore.controller;

import java.math.BigDecimal;

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
import com.org.demo.bookstore.response.BookResponseVO;
import com.org.demo.bookstore.service.BookStoreService;
import com.org.demo.bookstore.util.ObjectMapperUtil;

@ExtendWith(MockitoExtension.class)
class BookStoreControllerTest {

	private MockMvc mockMvc;
	@Mock
	private BookStoreService bookStoreService;
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
		BookResponseVO bookResponseVO = new BookResponseVO();
		bookResponseVO.setIsbn("90923");
		bookResponseVO.setTitle("Java Development");
		bookResponseVO.setAuthor("M L Malhotra");
		bookResponseVO.setPrice("900");
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

}
