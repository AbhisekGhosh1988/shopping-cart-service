package com.abhisek.mindtree.service;


import java.util.List;

import com.abhisek.mindtree.entity.Cart;

public interface CartService {
    public List<Cart> getAllCartItems(long userSessionId);

    public Cart saveItem(Cart cart);

    public Object deleteItem(Cart cart);
}
