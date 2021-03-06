package com.abhisek.mindtree.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.abhisek.mindtree.constant.Constants;
import com.abhisek.mindtree.exception.CartException;
import com.abhisek.mindtree.exception.EmailException;
import com.abhisek.mindtree.exception.NoRoleFoundException;
import com.abhisek.mindtree.exception.ProductSavingException;
import com.abhisek.mindtree.exception.UnAuthorizedException;
import com.abhisek.mindtree.model.MessageApi;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(EmailException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageApi handleEmailException(EmailException ex) {

		MessageApi errorResponse = MessageApi.builder().message(Constants.EMAIL_TAKEN).build();
		return errorResponse;
	}

	@ExceptionHandler(NoRoleFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageApi handleNoRoleFoundException(NoRoleFoundException ex) {

		MessageApi errorResponse = MessageApi.builder().message(Constants.ROLE_NOT_FOUND).build();
		return errorResponse;
	}

	@ExceptionHandler(CartException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public MessageApi handleCartException(CartException ex) {

		MessageApi errorResponse = MessageApi.builder().message(Constants.GETTING_CART_INFO).build();
		return errorResponse;
	}

	@ExceptionHandler(ProductSavingException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public MessageApi handleProductSavingException(ProductSavingException ex) {

		MessageApi errorResponse = MessageApi.builder().message(Constants.UNABLE_SAVE_PRODUCT).build();
		return errorResponse;
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public MessageApi handleUnAuthorizedException(UnAuthorizedException ex) {

		MessageApi errorResponse = MessageApi.builder().message(Constants.UNAUTHORIZED).build();
		return errorResponse;
	}
}
