package com.abhisek.mindtree.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.abhisek.mindtree.constant.Constants;
import com.abhisek.mindtree.security.UserDetailsImpl;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${shopping.app.jwtSecret}")
	private String jwtSecret;

	@Value("${shopping.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error(Constants.INVALID_JWT_SIGNATURE, e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error(Constants.INVALID_JWT_TOKEN, e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error(Constants.TOKEN_EXPIRED, e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error(Constants.TOKEN_UNSUPPORTED, e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error(Constants.JWT_CLAIM_EMPTY, e.getMessage());
		}

		return false;
	}
}