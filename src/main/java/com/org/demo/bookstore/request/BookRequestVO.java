package com.org.demo.bookstore.request;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestVO {
	@NotBlank(message = "Please provide valid isbn.")
	private String isbn;
	@NotBlank(message = "Please provide valid title.")
	private String title;
	@NotBlank(message = "Please provide valid author name.")
	private String author;
	@NotNull(message = "Please provide a price")
    @DecimalMin("1.00")
	private BigDecimal  price;
}
	