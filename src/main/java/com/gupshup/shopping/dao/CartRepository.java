package com.gupshup.shopping.dao;

import com.gupshup.shopping.entity.Cart;
import com.gupshup.shopping.dao.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Repository
public class CartRepository {

    private static final String MY_CART = "SELECT cartid,qty,itemid FROM mycart WHERE userid = ?";
    private static final String ITEM_DETAILS = "SELECT product,company,color,image,price FROM items WHERE itemid = ?";
    private static final String SELECT_CART = "SELECT * FROM mycart WHERE userid = ? AND itemid = ?";
    private static final String INSERT_CART = "INSERT INTO mycart (userid,qty,itemid) VALUES (?,?,?)";
    private static final String UPDATE_CART = "UPDATE mycart SET qty = ? WHERE userid = ? AND itemid = ?";
    private static final String DELETE_FROMCART = "DELETE FROM mycart WHERE cartid = ?";
    private static final String DELETE_CART = "DELETE FROM mycart WHERE userid = ?";
  
    @Autowired
    @Qualifier("shoppingJdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    public List<Map<String,Object>> myCart(int userid) {
        
        List<Map<String,Object>> mycart = new ArrayList<Map<String,Object>>();

        jdbcTemplate.query(MY_CART, (rs, rowNum) -> {
            
            Map<String,Object> cart = new HashMap<String,Object>();
            cart.put("_id",(int)rs.getInt("cartid"));
            cart.put("qty",(int)rs.getInt("qty"));
            cart.put("itemid",(int)rs.getInt("itemid"));
        
            List<Map<String,Object>> productdetails = new ArrayList<Map<String,Object>>();
            jdbcTemplate.query(ITEM_DETAILS, (rs1, rowNum1) -> {
                Map<String,Object> item = new HashMap<String,Object>();
                item.put("product",(String)rs1.getString("product"));
                item.put("company",(String)rs1.getString("company"));
                item.put("color",(String)rs1.getString("color"));
                item.put("image",(String)rs1.getString("image"));
                item.put("price",(int)rs1.getInt("price"));
                
                productdetails.add(item);
                return true;
            
            },rs.getInt("itemid")); 

            cart.put("productdetails",(List<Map<String,Object>>)productdetails);           
            mycart.add(cart);
            return true;
        
        },userid);

        return mycart;
    }

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