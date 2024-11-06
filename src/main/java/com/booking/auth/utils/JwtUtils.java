package com.booking.auth.utils;

import com.booking.auth.constant.RequestConstant;
import com.booking.users.dtos.request.UserRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("all")
public class JwtUtils {

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    static  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(RequestConstant.AUTH_SECRET).parseClaimsJws(token).getBody();
    }

    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static String generateToken(UserRequest userRequest) {
        Map<String, Object> claims = new HashMap<>();
        return RequestConstant.AUTH_TOKEN_PREFIX + createToken(claims, userRequest.getEmail());
    }

    public static String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return RequestConstant.AUTH_TOKEN_PREFIX + createToken(claims, email);
    }

    private static String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + RequestConstant.JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, RequestConstant.AUTH_SECRET).compact();
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
