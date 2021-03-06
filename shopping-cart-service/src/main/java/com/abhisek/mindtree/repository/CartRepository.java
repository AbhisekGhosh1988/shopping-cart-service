package com.abhisek.mindtree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.abhisek.mindtree.constant.*;
import com.abhisek.mindtree.entity.Cart;

import javax.transaction.Transactional;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	@Query(Constants.QUERY_FIND_ALL_CART_ITEMS)
	public List<Cart> findAllCartItems(@Param("userId") long userId);

	@Query(Constants.QUERY_EXIST_CART_ITEM_CUSTOM)
	boolean existsCartItemCustomQuery(@Param("userId") long id, @Param("productId") int productId);

	@Query(Constants.QUERY_FIND_CART_ITEM)
	public Cart findCartItem(@Param("userId") long id, @Param("productId") int productId);

	@Transactional
	@Modifying
	@Query(Constants.QUERY_DELETE_CART_ITEM)
	public int deleteCartItem(@Param("userId") long userId, @Param("productId") int productId);

}
