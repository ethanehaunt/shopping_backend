package com.gupshup.shopping.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;

public class ValidateUtil {
	
	private static final long serialVersionUID = -2550185165626007488L;
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static String secret  = "somend";

    public static String doGenerateToken(Map<String, Object> claims, String subject) {

        System.out.println("utll----------------");
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

	public static Map<String,Boolean> Authenticate(String usertoken) {

       Map<String,Boolean> auth =  new HashMap<String,Boolean>();
       auth.put("authentication",validateToken(usertoken));       
       
       return auth;
    }

    public static String getUserID(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private static Boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
   

    private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

       

}
