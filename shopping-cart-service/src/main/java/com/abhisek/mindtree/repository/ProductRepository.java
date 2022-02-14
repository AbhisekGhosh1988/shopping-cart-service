package com.abhisek.mindtree.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abhisek.mindtree.entity.Product;
import com.abhisek.mindtree.constant.*;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query(value = Constants.QUERY_FINDBY_DTYPE, countQuery = Constants.QUERY_FINDBY_DTYPE_COUNT, nativeQuery = true)
	Page<Product> findByDtype(String category, Pageable pageable);

	@Query(value = Constants.QUERY_FINDBY_PRODUCT_NAME, countQuery = Constants.QUERY_FINDBY_PRODUCT_NAME_COUNT, nativeQuery = true)
	Page<Product> findByProductName(String name, Pageable pageable);

}
