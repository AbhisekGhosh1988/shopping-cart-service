package com.abhisek.mindtree.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhisek.mindtree.constant.Constants;
import com.abhisek.mindtree.entity.Cart;
import com.abhisek.mindtree.entity.Product;
import com.abhisek.mindtree.model.MessageApi;
import com.abhisek.mindtree.model.ProductRequest;
import com.abhisek.mindtree.model.ProductResponseDto;
import com.abhisek.mindtree.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "product")
@Tag(name = "Product Controller", description = "(This is used for Admin to  add/update and delete from catalogue and user to view all products)")

public class ProductController {

	@Autowired
	private ProductService productService;
	private static final Logger LOGGER = LogManager.getLogger(ProductController.class);

	@PostMapping("/saveproduct")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Save Products", description = "Save products to catalogue", tags = { "product" })
	public ResponseEntity<?> saveProduct(@RequestBody List<ProductRequest> productRequests) {

		try {
			List<Product> products = productService.saveProduct(productRequests);
			if (products == null) {
				MessageApi api = MessageApi.builder().message(Constants.UNABLE_SAVE_PRODUCT).build();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(api);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(products);
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION + e.getMessage() + Constants.UNABLE_SAVE_PRODUCT);
			return new ResponseEntity<Cart>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/deleteproduct/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Delete Products", description = "Delete products from catalogue", tags = { "product" })
	public ResponseEntity<MessageApi> deleteProduct(@PathVariable int id) {
		try {
			String product = productService.deleteProduct(id);
			if (product.equals("Success")) {
				MessageApi api = MessageApi.builder().message(Constants.PRODUCT_DELETE_SUCCESS).build();
				return ResponseEntity.status(HttpStatus.OK).body(api);
			} else {
				MessageApi api = MessageApi.builder().message(Constants.PRODUCT_DELETE_NOT_SUCCESS).build();
				return ResponseEntity.status(HttpStatus.OK).body(api);
			}
		} catch (Exception e) {
			MessageApi api = MessageApi.builder()
					.message(Constants.EXCEPTION + e.getMessage() + Constants.FAILED_DELETING_CART_ITEM + id).build();
			LOGGER.info(Constants.EXCEPTION + e.getMessage() + Constants.FAILED_DELETING_CART_ITEM + id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
		}

	}

	@GetMapping("/category/{category}")
	@Operation(summary = "Get Products", description = "Get products from catalogue by category", tags = { "product" })
	public ResponseEntity<?> getProductsByCategory(@PathVariable String category,
			@RequestParam(defaultValue = "1") int pagenumber, @RequestParam(defaultValue = "10") int pagesize) {

		try {
			ProductResponseDto productResponseDto = productService.getProductsByCategory(category, pagenumber,
					pagesize);
			if (productResponseDto != null) {
				return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
			} else {
				MessageApi api = MessageApi.builder().message(Constants.NO_PRODUCT_FOUND).build();
				return ResponseEntity.status(HttpStatus.OK).body(api);
			}

		} catch (Exception e) {
			MessageApi api = MessageApi.builder().message(Constants.EXCEPTION + e.getMessage()).build();
			LOGGER.info(Constants.EXCEPTION + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
		}

	}

	@GetMapping("/name/{name}")
	@Operation(summary = "Get Products", description = "Get products from catalogue by name", tags = { "product" })
	public ResponseEntity<?> getProductsByName(@PathVariable String name) {

		try {
			ProductResponseDto productResponseDto = productService.getProductsByName(name);
			if (productResponseDto != null) {
				return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
			} else {
				MessageApi api = MessageApi.builder().message(Constants.NO_PRODUCT_FOUND).build();
				return ResponseEntity.status(HttpStatus.OK).body(api);
			}

		} catch (Exception e) {
			MessageApi api = MessageApi.builder().message(Constants.EXCEPTION + e.getMessage()).build();
			LOGGER.info(Constants.EXCEPTION + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
		}

	}

}
