package com.org.demo.bookstore.service;

import java.util.List;

import com.org.demo.bookstore.request.OrderRequestVO;
import com.org.demo.bookstore.response.OrderResponseVO;

public interface OrderService {
	public OrderResponseVO buyBook(List<OrderRequestVO> orderRequestVOList);

}
