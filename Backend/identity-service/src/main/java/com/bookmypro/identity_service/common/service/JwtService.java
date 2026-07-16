package com.bookmypro.identity_service.common.service;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bookmypro.identity_service.feature.auth.login.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${access-token-expiration}")
	private long accessTokenExpiration;

	@Value("${refresh-token-expiration}")
	private long refreshTokenExpiration;

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateAccessToken(CustomUserDetails user) {

		return generateToken(user, accessTokenExpiration, "ACCESS");
	}

	public String generateRefreshToken(CustomUserDetails user) {

		return generateToken(user, refreshTokenExpiration, "REFRESH");
	}

	private String generateToken(CustomUserDetails user, long expiration, String type) {
		return Jwts.builder().subject(user.getUsername())
				.claim("credentialId", user.getCredentialId())
				.claim("type", type).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration)).signWith(getKey()).compact();
	}

	public String extractUsername(String token) {

		return getClaims(token).getSubject();
	}
	
	public String extractCredentialId(String token) {
	    return getClaims(token).get("credentialId", String.class);
	}

	public String extractTokenType(String token) {
		return getClaims(token).get("type", String.class);
	}
	
	public Date extractExpiredAt(String token) {
		return getClaims(token).getExpiration();
	}

	public boolean isValid(String token, UserDetails user) {

		return extractUsername(token).equals(user.getUsername()) && !isExpired(token);
	}
	
	public boolean validateToken(String token) {
        try {
            return !isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

	private boolean isExpired(String token) {

		return getClaims(token).getExpiration().before(new Date());
	}

	private Claims getClaims(String token) {
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
	}
}
