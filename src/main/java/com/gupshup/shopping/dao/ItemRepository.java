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
import java.util.Map;

@Repository
public class ItemRepository {

    private static final String GET_ITEMS = "SELECT * FROM items";
    private static final String DELETE_ITEMS = "DELETE FROM `items` WHERE itemid = ?";
    private static final String INSERT_ITEMS = "INSERT INTO items (product,company,color,price,description,image,isbestseller,isNew,rating) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ITEMS = "UPDATE items SET product=?,company=?,color=?,price=?,description=?,image=?,isbestseller=?,isNew=?,rating=? WHERE itemid=?";
  
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
                    rs.getInt("rating")
                    );
        });

    }

    public String addItem(Map<String,String> itemdata) {
        int response = jdbcTemplate.update(INSERT_ITEMS,itemdata.get("product"),itemdata.get("company"),itemdata.get("color"),Integer.parseInt(itemdata.get("price")),itemdata.get("description"),itemdata.get("image"),Boolean.parseBoolean(itemdata.get("isbestseller"))?1:0,Boolean.parseBoolean(itemdata.get("isNew"))?1:0,Integer.parseInt(itemdata.get("rating")));
        return "success";
    }


    public String updateItem(Map<String,String> itemdata) {
        int response = jdbcTemplate.update(UPDATE_ITEMS,itemdata.get("product"),itemdata.get("company"),itemdata.get("color"),Integer.parseInt(itemdata.get("price")),itemdata.get("description"),itemdata.get("image"),Boolean.parseBoolean(itemdata.get("isbestseller"))?1:0,Boolean.parseBoolean(itemdata.get("isNew"))?1:0,Integer.parseInt(itemdata.get("rating")),Integer.parseInt(itemdata.get("itemid")));
        return "success";
    }

    public String removeItem(int itemid) {
        int response = jdbcTemplate.update(DELETE_ITEMS,itemid);
        return "success";
    } 

}