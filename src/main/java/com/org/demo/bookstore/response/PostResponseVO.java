package com.org.demo.bookstore.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseVO {

	private Integer id;
	private Integer userId;
	private String title;
	private String body;

}
