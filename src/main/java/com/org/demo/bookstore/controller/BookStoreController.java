package com.org.demo.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.demo.bookstore.request.BookRequestVO;
import com.org.demo.bookstore.request.OrderRequestVO;
import com.org.demo.bookstore.request.SearchRequestVO;
import com.org.demo.bookstore.response.BookResponseVO;
import com.org.demo.bookstore.response.OrderResponseVO;
import com.org.demo.bookstore.service.BookStoreService;
import com.org.demo.bookstore.service.OrderService;
import com.org.demo.bookstore.util.MessageResponse;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Controller for the bookstore projects.Acceptance Criteria are as follows :
 * <li>1.Add new book to the bookstore.
 * <li>2.Search book by isbn/title/author
 * <li>3.Search media coverage of book by title.
 * <li>4.Buy book by title.
 */
@OpenAPIDefinition(info = @Info(title = "BookStore API", version = "1.0", description = "Book store Information"))
@RestController
@RequestMapping("/v1/books")
public class BookStoreController {

	@Autowired
	private BookStoreService bookStoreService;
	@Autowired
	private OrderService orderService;

	@PostMapping("/add-book")
	public ResponseEntity<MessageResponse> addNewBook(@Valid @RequestBody BookRequestVO bookRequestVO) {
		BookResponseVO bookResponseVO = bookStoreService.addNewBook(bookRequestVO);
		return ResponseEntity.ok(new MessageResponse("New book added successfully !!", bookResponseVO));
	}

	@GetMapping("/search")
	public ResponseEntity<MessageResponse> searchBook(@RequestParam(value = "isbn", required = false) String isbn,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "author", required = false) String author) {
		List<BookResponseVO> bookResponseVOList = bookStoreService.searchBook(new SearchRequestVO(isbn, title, author));
		if (bookResponseVOList.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("No result found", bookResponseVOList));
		}
		return ResponseEntity.ok(new MessageResponse("Books searched successfully !!", bookResponseVOList));
	}

	@GetMapping("/search-media-coverage/title/{title}")
	public ResponseEntity<MessageResponse> searchMediaCoverage(@PathVariable("title") String title) {
		List<String> titleList = bookStoreService.searchMediaCoverage(title);
		if (titleList.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("No result found", titleList));
		}
		return ResponseEntity.ok(new MessageResponse("Media coverage searched successfully !!", titleList));
	}

	@PostMapping("/buy-book")
	public ResponseEntity<MessageResponse> buyBook(@RequestBody List<OrderRequestVO> orderRequestVOList) {
		OrderResponseVO orderResponseVO = orderService.buyBook(orderRequestVOList);
		return ResponseEntity.ok(new MessageResponse("Book ordered successfully !!", orderResponseVO));
	}

}
