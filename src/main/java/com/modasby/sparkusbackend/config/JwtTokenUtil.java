package com.modasby.sparkusbackend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenUtil {

    public static final long JWT_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000;

    @Value("${jwt.secret}")
    private String secret;

    public DecodedJWT decodeJwt(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT
                .require(algorithm)
                .withIssuer("sparkus-security")
                .build()
                .verify(token);
    }

    public String getCredentialFromToken(String token) {
        return decodeJwt(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return decodeJwt(token).getExpiresAt();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        return doGenerateToken(userDetails.getUsername());
    }

    private String doGenerateToken(String subject) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
            .withIssuer("sparkus-security")
            .withExpiresAt(getExpiration())
            .withSubject(subject)
            .withIssuedAt(Instant.now())
            .sign(algorithm);
    }

    private Date getExpiration() {
        return new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String credential = getCredentialFromToken(token);

        return (credential.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
