package com.abhisek.mindtree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhisek.mindtree.entity.Cart;
import com.abhisek.mindtree.repository.CartRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	CartRepository cartRepository;

	@Override
	public List<Cart> getAllCartItems(long userSessionId) {
		List<Cart> cartItems = cartRepository.findAllCartItems(userSessionId);
		return cartItems;
	}

	@Override
	public Cart saveItem(Cart cart) {
		Cart savedCartItem = null;

		if (cartRepository.existsCartItemCustomQuery(cart.getUserId(), cart.getProductId())) {
			Optional<Cart> productInCart = Optional
					.of(cartRepository.findCartItem(cart.getUserId(), cart.getProductId()));
			if (productInCart.isPresent()) {
				int productQuantityInCart = productInCart.get().getQuantity();
				Double cartSubTotal = productInCart.get().getSubTotal();
				cartSubTotal = cartSubTotal + (cart.getPrice());
				productQuantityInCart = productQuantityInCart + cart.getQuantity();
				productInCart.get().setQuantity(productQuantityInCart);
				productInCart.get().setSubTotal(cartSubTotal);
				savedCartItem = cartRepository.save(productInCart.get());
			}

		} else {
			cart.setSubTotal(cart.getPrice());
			savedCartItem = cartRepository.save(cart);
		}

		return savedCartItem;
	}

	@Override
	public Cart deleteItem(Cart cart) {
		if (cartRepository.existsCartItemCustomQuery(cart.getUserId(), cart.getProductId())) {
			Optional<Cart> productInCart = Optional
					.of(cartRepository.findCartItem(cart.getUserId(), cart.getProductId()));
			if (productInCart.isPresent()) {
				int productQuantityInCart = productInCart.get().getQuantity();
				if (productQuantityInCart > 0 && productQuantityInCart > cart.getQuantity()) {
					Double cartSubtotal = productInCart.get().getSubTotal();
					productQuantityInCart = productQuantityInCart - cart.getQuantity();
					cartSubtotal = cartSubtotal - cart.getPrice();
					productInCart.get().setQuantity(productQuantityInCart);
					productInCart.get().setSubTotal(cartSubtotal);
					Optional<Cart> updatedCartItem = Optional.of(cartRepository.save(productInCart.get()));
					if (updatedCartItem.isPresent()) {
						return updatedCartItem.get();
					} else {
						return updatedCartItem.get();
					}
				} else {
					int deletedCart=cartRepository.deleteCartItem(cart.getUserId(), cart.getProductId());
					return null;
				}
			} else {
				return null;
			}
		}
		return null;
	}
}
