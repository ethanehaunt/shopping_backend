package com.gupshup.shopping.dao;

import com.gupshup.shopping.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Repository
public class UserRepository {

    private static final String SELECT_USER = "SELECT * FROM users WHERE email = ? AND password = ?";
      
    @Autowired
    @Qualifier("shoppingJdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private String secret  = "somend";

    public Map<String,String> login(Map<String,String> logindata) {
        
        List<User> user = jdbcTemplate.query(SELECT_USER, (rs, rowNum) -> {
            return new User(rs.getInt("userid"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getBoolean("isadmin")
                    );
        },logindata.get("email"),logindata.get("password"));
        
        Map<String,String> userDetail =  new HashMap<String,String>();

        if (user.size() > 0) {
            Map<String, Object> claims = new HashMap<>();
  
            userDetail.put("user_token",doGenerateToken(claims, user.get(0).getUserid().toString()));
            userDetail.put("isadmin",user.get(0).getIsAdmin().toString());
        }
        else{
            userDetail.put("user_token","-1");
            userDetail.put("isadmin","0");
        }

        return userDetail;
    }

    public Map<String,Boolean> Authenticate(String usertoken) {

       Map<String,Boolean> auth =  new HashMap<String,Boolean>();
       auth.put("authentication",validateToken(usertoken));       
       
       return auth;
    }

    public Boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    

}