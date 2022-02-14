package com.abhisek.mindtree.model;

import java.util.List;

import com.abhisek.mindtree.entity.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto {
	List<Product> product;
	int totalPages;
	long totalElements;

	public ProductResponseDto(List<Product> product, int totalPages, long totalElements) {
		super();
		this.product = product;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
	}

}
