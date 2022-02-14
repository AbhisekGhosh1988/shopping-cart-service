package com.abhisek.mindtree.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

	private String productName;
	private double price;
	private String genre;
	private String author;
	private String publication;
	private String type;
	private String brand;
	private String design;
}
