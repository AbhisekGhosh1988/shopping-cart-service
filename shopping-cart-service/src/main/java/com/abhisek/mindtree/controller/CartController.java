package com.abhisek.mindtree.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.abhisek.mindtree.constant.Constants;
import com.abhisek.mindtree.entity.Cart;
import com.abhisek.mindtree.exception.CartException;
import com.abhisek.mindtree.model.ApiResponse;
import com.abhisek.mindtree.model.MessageApi;
import com.abhisek.mindtree.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/cart")
@Tag(name = "CartController Controller", description = "(This is used for user add/update and delete from cart)")
public class CartController {

	private static final Logger LOGGER = LogManager.getLogger(CartController.class);

	@Autowired
	CartService cartService;

	@GetMapping("/list/{id}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Get All items ", description = "Get All items to cart", tags = { "cart" })
	public ResponseEntity<?> getCartItems(@PathVariable long id) {
		LOGGER.info(Constants.GETTING_CART_INFO + id);
		try {
			List<Cart> cartItems = cartService.getAllCartItems(id);

			if (cartItems.size() > 0) {
				ApiResponse response = new ApiResponse();
				response.setCartItems(cartItems);
				response.setCartTotal(cartItems.stream().mapToDouble(p -> p.getSubTotal()).sum());
				return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
			} else {
				LOGGER.info(Constants.CART_EMPTY_USER + id);
				MessageApi api = MessageApi.builder().message(Constants.CART_EMPTY).build();
				return ResponseEntity.status(HttpStatus.OK).body(api);
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION + e.getMessage() + Constants.GETTING_CART_INFO + id);
			throw new CartException();
		}

	}

	@PostMapping("/add/{id}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Add items ", description = "Add items to cart", tags = { "cart" })
	public ResponseEntity<Cart> addItem(@RequestBody Cart cart, @PathVariable long id) {
		Cart savedItem = null;
		try {
			cart.setUserId(id);
			savedItem = cartService.saveItem(cart);
			if (savedItem == null) {
				return new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<Cart>(savedItem, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION + e.getMessage() + Constants.ADD_CART_USER + id);
			return new ResponseEntity<Cart>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Delete items ", description = "Delete items from cart", tags = { "cart" })
	public ResponseEntity<?> deleteItem(@RequestBody Cart cart, @PathVariable long id) {

		try {
			cart.setUserId(id);
			Object updatedCart = cartService.deleteItem(cart);
			if (updatedCart != null && updatedCart instanceof Cart) {
				return ResponseEntity.status(HttpStatus.OK).body(updatedCart);
			} else {
				MessageApi api = MessageApi.builder().message(Constants.NO_PRODUCT_AVBL).build();
				return ResponseEntity.status(HttpStatus.OK).body(api);
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION + e.getMessage() + Constants.DELETE_CART_USER + id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}
}
