package com.org.demo.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.org.demo.bookstore.model.Book;
import com.org.demo.bookstore.model.Order;
import com.org.demo.bookstore.repository.BookStoreRepository;
import com.org.demo.bookstore.repository.OrderRepository;
import com.org.demo.bookstore.request.OrderRequestVO;
import com.org.demo.bookstore.response.OrderResponseVO;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
	@Mock
	private BookStoreRepository bookStoreRepository;
	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderServiceImpl orderService;

	@BeforeAll
	public static void setUp() {
	}

	@Test
	final void testBuyBookSuccess() {
		OrderRequestVO orderRequestVO = new OrderRequestVO();
		orderRequestVO.setTitle("Java Development");
		orderRequestVO.setQuantity(1);
		List<OrderRequestVO> orderRequestVOList = new ArrayList<>();
		orderRequestVOList.add(orderRequestVO);

		Book savedbook = new Book();
		savedbook.setIsbn("90923");
		savedbook.setTitle("Java Development");
		savedbook.setAuthor("M L Malhotra");
		savedbook.setQuantity(2);
		savedbook.setPrice(900d);
		bookStoreRepository.save(savedbook);
		Order savedOrder = new Order();
		savedOrder.setBook(savedbook);
		savedOrder.setOrderId("323");
		savedOrder.setQuantity(1);
		List<Order> orderList = new ArrayList<>();
		orderList.add(savedOrder);
		Mockito.when(
				bookStoreRepository.getBookByTitleAndQuantity(orderRequestVO.getTitle(), orderRequestVO.getQuantity()))
				.thenReturn(Optional.ofNullable(savedbook));
		OrderResponseVO orderResponseVO = orderService.buyBook(orderRequestVOList);
		Assertions.assertEquals(orderRequestVO.getTitle(), orderResponseVO.getTitleList().get(0));
	}
}
