package com.abhisek.mindtree.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.abhisek.mindtree.constant.Constants;
import com.abhisek.mindtree.exception.NoRoleFoundException;
import com.abhisek.mindtree.exception.UnAuthorizedException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		try {
			logger.error(Constants.UNAUTHORIZED_EXCEPTION, authException.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constants.UNAUTHORIZED_EXCEPTION);
			throw new UnAuthorizedException();
		} catch (Exception e) {
		}
	}

}