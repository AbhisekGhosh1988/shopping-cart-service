package com.abhisek.mindtree.model;

import java.util.List;

import com.abhisek.mindtree.entity.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
	private List<Cart> cartItems;
	private Double cartTotal;

}
