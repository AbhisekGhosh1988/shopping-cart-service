package com.abhisek.mindtree.constant;

public interface Constants {
	
	final static String USER_REGISTER_SUCCES="User registered successfully!";
	final static String USERNAME_TAKEN="Error: Username is already taken!";
	final static String EMAIL_TAKEN="Error: Email is already taken!";
	final static String GETTING_CART_INFO="Getting Cart Items for User =======> ";
	final static String CART_EMPTY_USER="Cart Empty for User =======> " ;
	final static String CART_EMPTY="Cart is empty";
	final static String EXCEPTION="Exception: ";
	final static String ADD_CART_USER=" adding Cart Item for User =======> ";
	final static String NO_PRODUCT_AVBL="No Product available in Cart";
	final static String DELETE_CART_USER=" deleting Cart Item for User =======> ";
	final static String UNABLE_SAVE_PRODUCT="Unable to save Product in Db";
	final static String PRODUCT_DELETE_SUCCESS="Product deleted Successfully";
	final static String PRODUCT_DELETE_NOT_SUCCESS="Product cannot be deleted";
	final static String FAILED_DELETING_CART_ITEM=" failed deleting Cart Item  =======> ";
	final static String NO_PRODUCT_FOUND="No Product found";
	final static String ROLE_NOT_FOUND="Error: Role is not found." ;
	final static String UNAUTHORIZED="You are not Authorized! Please provide valid token";
	final static String UNAUTHORIZED_EXCEPTION="Unauthorized error: {}";
	final static String CANNOT_SET_AUTHENTICATION="Cannot set user authentication: {}";
	final static String AUTH="Authorization";
	final static String BEARER="Bearer ";
	final static String INVALID_JWT_SIGNATURE="Invalid JWT signature: {}";
	final static String INVALID_JWT_TOKEN="Invalid JWT token: {}";
	final static String TOKEN_EXPIRED="JWT token is expired: {}";
	final static String TOKEN_UNSUPPORTED="JWT token is unsupported: {}";
	final static String JWT_CLAIM_EMPTY="JWT claims string is empty: {}";
	final static String QUERY_FIND_ALL_CART_ITEMS="select c from Cart c where c.userId=:userId ";
	final static String QUERY_EXIST_CART_ITEM_CUSTOM="select case when count(c)> 0 then true else false end from Cart c where  c.userId=:userId  and :productId = c.productId";
	final static String QUERY_FIND_CART_ITEM="select c from Cart c where c.userId=:userId and c.productId = :productId";
	final static String QUERY_DELETE_CART_ITEM="delete from Cart c where c.userId=:userId  and c.productId = :productId";	
	final static String QUERY_FINDBY_DTYPE="select * from product p where p.dtype= :category";
	final static String QUERY_FINDBY_DTYPE_COUNT="select count(*) from product p where p.dtype= :category";
	final static String QUERY_FINDBY_PRODUCT_NAME="select * from product p where p.product_name like %:name%";
	final static String QUERY_FINDBY_PRODUCT_NAME_COUNT="select count(*) from product p where p.product_name like %:name%";
	final static String OPENAPI_TITLE="Sphopping Cart Application API";
	final static String OPENAPI_DESC="(This is a shopping cart application using OpenApi Documentation)";
	final static String OPENAPI_POLICY="https://policies.google.com/terms?hl=en-IN&fg=1";
	final static String OPENAPI_VER="1.0";
	final static String OPENAPI_COPYRIGHT="CopyRight Abhisek Ghosh 2022";
	final static String OPENAPI_COPYRIGHT_URL="www.xyz.com";
	final static String OPENAPI_CONTACT_EMAIL="ghosh.abhisek@gmail.com";
	final static String OPENAPI_CONTACT_NAME="Abhisek Ghosh";
	final static String OPENAPI_CONTACT_URL="https://www.google.co.in/";

	

}
