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

@Repository
public class UserRepository {

    private static final String SELECT_USER = "SELECT * FROM users WHERE email = ? AND password = ?";
      
    @Autowired
    @Qualifier("shoppingJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public String login(Map<String,String> logindata) {
        
        List<User> user = jdbcTemplate.query(SELECT_USER, (rs, rowNum) -> {
            return new User(rs.getInt("userid"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getBoolean("isadmin")
                    );
        },logindata.get("email"),logindata.get("password"));


        System.out.println(user);

        return "success";
    }

}