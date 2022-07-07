package com.gupshup.shopping.dao;

import com.gupshup.shopping.entity.Item;
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
public class ItemRepository {

    private static final String GET_ITEMS = "SELECT * FROM items";
    private static final String GET_CATEGORIES = "SELECT DISTINCT company FROM items";
    private static final String DELETE_ITEMS = "DELETE FROM `items` WHERE itemid = ?";
    private static final String INSERT_ITEMS = "INSERT INTO items (product,company,color,price,description,image,isbestseller,isNew,inStock,rating) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ITEMS = "UPDATE items SET product=?,company=?,color=?,price=?,description=?,image=?,isbestseller=?,isNew=?,inStock=?,rating=? WHERE itemid=?";
  
    @Autowired
    @Qualifier("shoppingJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<Item> items() {
        return jdbcTemplate.query(GET_ITEMS, (rs, rowNum) -> {
            return new Item(rs.getInt("itemid"),
                    rs.getString("product"),
                    rs.getString("company"),
                    rs.getString("color"),
                    rs.getInt("price"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getBoolean("isbestseller"),
                    rs.getBoolean("isNew"),
                    rs.getBoolean("inStock"),
                    rs.getInt("rating")
                    );
        });

    }

    public List<Item> itemSearch(String searchitem,String searchvalue) {

        String ITEM_SEARCH = ""; 
        if (searchitem.equals("_id")) {
            ITEM_SEARCH = GET_ITEMS + " WHERE itemid = "+searchvalue;
        }
        else{
            ITEM_SEARCH = GET_ITEMS + " WHERE "+searchitem+" like '%"+searchvalue+"%'";
        }

        return jdbcTemplate.query(ITEM_SEARCH, (rs, rowNum) -> {
            return new Item(rs.getInt("itemid"),
                    rs.getString("product"),
                    rs.getString("company"),
                    rs.getString("color"),
                    rs.getInt("price"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getBoolean("isbestseller"),
                    rs.getBoolean("isNew"),
                    rs.getBoolean("inStock"),
                    rs.getInt("rating")
                    );
        });

    }

    public List<String> itemCategory() {
        
        List<String> categoryList = new ArrayList<String>();
        jdbcTemplate.query(GET_CATEGORIES, (rs, rowNum) -> {
            return categoryList.add(rs.getString("company"));
        });

        return categoryList;
    }

    public String addItem(Map<String,String> itemdata) {
        int response = jdbcTemplate.update(INSERT_ITEMS,itemdata.get("product"),itemdata.get("company"),itemdata.get("color"),Integer.parseInt(itemdata.get("price")),itemdata.get("description"),itemdata.get("image"),Boolean.parseBoolean(itemdata.get("isbestseller"))?1:0,Boolean.parseBoolean(itemdata.get("isnew"))?1:0,Boolean.parseBoolean(itemdata.get("inStock"))?1:0,Integer.parseInt(itemdata.get("rating")));
        return "success";
    }


    public String updateItem(Map<String,String> itemdata) {
        int response = jdbcTemplate.update(UPDATE_ITEMS,itemdata.get("product"),itemdata.get("company"),itemdata.get("color"),Integer.parseInt(itemdata.get("price")),itemdata.get("description"),itemdata.get("image"),Boolean.parseBoolean(itemdata.get("isbestseller"))?1:0,Boolean.parseBoolean(itemdata.get("isnew"))?1:0,Boolean.parseBoolean(itemdata.get("inStock"))?1:0,Integer.parseInt(itemdata.get("rating")),Integer.parseInt(itemdata.get("_id")));
        return "success";
    }

    public String removeItem(int itemid) {
        int response = jdbcTemplate.update(DELETE_ITEMS,itemid);
        return "success";
    } 

}