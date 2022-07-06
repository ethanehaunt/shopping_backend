package com.gupshup.shopping.dao;

import com.gupshup.shopping.entity.Cart;
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
public class CartRepository {

    private static final String SELECT_CART = "SELECT * FROM mycart WHERE userid = ? AND itemid = ?";
    private static final String INSERT_CART = "INSERT INTO mycart (userid,qty,itemid) VALUES (?,?,?)";
    private static final String UPDATE_CART = "UPDATE mycart SET qty = ? WHERE userid = ? AND itemid = ?";
    private static final String DELETE_FROMCART = "DELETE FROM mycart WHERE itemid = ?";
    private static final String DELETE_CART = "DELETE FROM mycart WHERE userid = ?";
  
    @Autowired
    @Qualifier("shoppingJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public String addToCart(int userid,Map<String,String> cartdata) {
        
        int response = -1;

        List<Cart> cart = jdbcTemplate.query(SELECT_CART, (rs, rowNum) -> {
            return new Cart(rs.getInt("cartid"),
                    rs.getInt("userid"),
                    rs.getInt("qty"),
                    rs.getInt("itemid")
                    );
        },userid,Integer.parseInt(cartdata.get("itemid")));


        if(cart.size() > 0){          
            response = jdbcTemplate.update(UPDATE_CART,Integer.parseInt(cartdata.get("qty"))+cart.get(0).getQty(),userid,Integer.parseInt(cartdata.get("itemid")));
        }
        else{
            response = jdbcTemplate.update(INSERT_CART,userid,Integer.parseInt(cartdata.get("qty")),Integer.parseInt(cartdata.get("itemid")));
        }

        return "success";
    }

    public String updateCart(int userid,Map<String,String> cartdata) {
        int response = jdbcTemplate.update(UPDATE_CART,Integer.parseInt(cartdata.get("qty")),userid,Integer.parseInt(cartdata.get("itemid")));
        return "success";
    }

    public String removeFromCart(int itemid) {
        int response = jdbcTemplate.update(DELETE_FROMCART,itemid);
        return "success";
    }

    public String removeCart(int userid) {
        int response = jdbcTemplate.update(DELETE_CART,userid);
        return "success";
    }


}