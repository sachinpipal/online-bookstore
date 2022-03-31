package com.org.demo.bookstore.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseVO {

	private List<String> titleList;
	private String orderId;
}
