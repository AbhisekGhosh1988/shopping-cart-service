package com.abhisek.mindtree.service;

import java.util.List;

import com.abhisek.mindtree.entity.Product;
import com.abhisek.mindtree.model.ProductRequest;
import com.abhisek.mindtree.model.ProductResponseDto;

public interface ProductService {
	public List<Product> saveProduct(List<ProductRequest> productRequests);

	public String deleteProduct(int id);

	public ProductResponseDto getProductsByCategory(String category, int pageNumber, int pageSize);

	public ProductResponseDto getProductsByName(String name);
}
