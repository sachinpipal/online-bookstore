package com.org.demo.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.demo.bookstore.exception.BookStoreBaseException;
import com.org.demo.bookstore.model.Book;
import com.org.demo.bookstore.model.Order;
import com.org.demo.bookstore.repository.BookStoreRepository;
import com.org.demo.bookstore.repository.OrderRepository;
import com.org.demo.bookstore.request.OrderRequestVO;
import com.org.demo.bookstore.response.OrderResponseVO;
import com.org.demo.bookstore.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private BookStoreRepository bookStoreRepository;
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public OrderResponseVO buyBook(List<OrderRequestVO> orderRequestVOList) {
		List<Order> orders = new ArrayList<>();
		List<String> titleList = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		try {
			titleList = orderRequestVOList.stream().map(OrderRequestVO::getTitle).collect(Collectors.toList());
			String orderId = "ODR" + new Random().nextInt(999999);
			for (OrderRequestVO orderRequestVO : orderRequestVOList) {
				Optional<Book> optionalBook = bookStoreRepository.getBookByTitleAndQuantity(orderRequestVO.getTitle(),
						orderRequestVO.getQuantity());
				if (optionalBook.isPresent()) {
					Order order = new Order();
					Book book = optionalBook.get();
					order.setBook(book);
					order.setQuantity(orderRequestVO.getQuantity());
					order.setOrderId(orderId);
					orders.add(order);
					book.setQuantity(book.getQuantity() - orderRequestVO.getQuantity());
					books.add(book);
				} else {
					throw new BookStoreBaseException(
							"Order quantity not matched for title :" + orderRequestVO.getTitle());
				}
			}
			orderRepository.saveAll(orders);
			bookStoreRepository.saveAll(books);
			return new OrderResponseVO(titleList, orderId);
		} catch (Exception ex) {
			log.error("Exception occured  " + this.getClass().getName() + "method :buyBook () for titleList : "
					+ titleList, ex.getCause());
			throw new BookStoreBaseException("No result found with given search, titleList: " + titleList,
					ex.getCause());
		}
	}
}
