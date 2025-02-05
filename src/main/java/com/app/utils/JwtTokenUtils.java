package com.app.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.app.constants.SecurityConstants;
import com.app.dto.TokenBody;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tamnc
 *
 */
@Component
@Slf4j
public class JwtTokenUtils {
	
	private static final SecretKey secretKey = Keys.hmacShaKeyFor(
            SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
	
	public static String generateToken(TokenBody tokenBody) {
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().issuer("Eazy Bank").subject("JWT Token")
                .claim("username", tokenBody.getUsername())
                .claim("authorities", tokenBody.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + 30000000))
                .signWith(key).compact();
	}
	
	public static TokenBody getBody(String jwt) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        String username = String.valueOf(claims.get("username"));
        List<String> authorities = (List<String>) claims.get("authorities");
        return TokenBody.builder()
        		.username(username)
        		.authorities(authorities)
        		.build();
	}
	
    public static boolean validateToken(String authToken) {
        try {
        	Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

 
}
