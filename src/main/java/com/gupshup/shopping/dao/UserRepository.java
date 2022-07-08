package com.gupshup.shopping.dao;

import com.gupshup.shopping.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.gupshup.shopping.util.ValidateUtil;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
public class UserRepository {

    private static final String SELECT_USER = "SELECT * FROM users WHERE email = ? AND password = ?";
        
    @Autowired
    @Qualifier("shoppingJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

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
            userDetail.put("user_token",ValidateUtil.doGenerateToken(claims, user.get(0).getUserid().toString()));
            userDetail.put("isadmin",user.get(0).getIsAdmin().toString());
        }
        else{
            userDetail.put("user_token","-1");
            userDetail.put("isadmin","0");
        }

        return userDetail;
    }

}