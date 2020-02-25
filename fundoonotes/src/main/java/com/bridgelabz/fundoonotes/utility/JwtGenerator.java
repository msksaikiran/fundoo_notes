
package com.bridgelabz.fundoonotes.utility;

import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Component
public class JwtGenerator {
	private static final String SECRET = "2129152050365";

	public String jwtToken(long l) {
		String token = null;
		try {
			token = JWT.create().withClaim("id", l).sign(Algorithm.HMAC512(SECRET));
		} catch (IllegalArgumentException | JWTCreationException e) {

			e.printStackTrace();
		}
		return token;
	}

	public Long parseJWT(String jwt) {
		Long userId = (long) 0;
		if (jwt != null) {
			userId = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(jwt).getClaim("id").asLong();
		}
		return userId;
	}

}