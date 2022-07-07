package com.gupshup.shopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import com.gupshup.shopping.dao.CartRepository;
import com.gupshup.shopping.entity.Cart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
public class cartController{

    @Autowired
    CartRepository cartRepository;

    @GetMapping("/mycart")
    public List<Map<String,Object>> myCart(){
      
      int userid = 1;
      return cartRepository.myCart(userid);
    }

    @PostMapping("/addtocart")
    public String addToCart(@RequestBody String cartjson){
      
      int userid = 1;

      Gson gson = new Gson();
      Type mapType = new TypeToken<Map<String,String>>() {}.getType();
      Map<String,String> cartdata = gson.fromJson(cartjson,mapType);

      return cartRepository.addToCart(userid,cartdata);
    }

    @PostMapping("/updatemycart")
    public String updateCart(@RequestBody String cartjson){
      
      int userid = 1;

      Gson gson = new Gson();
      Type mapType = new TypeToken<Map<String,String>>() {}.getType();
      Map<String,String> cartdata = gson.fromJson(cartjson,mapType);

      return cartRepository.updateCart(userid,cartdata);
    }

    @DeleteMapping("/removefromcart/{_id}")
    public String removeFromCart(@PathVariable("_id") int itemid){
      return cartRepository.removeFromCart(itemid);
    }

    @DeleteMapping("/removecart")
    public String removeCart(){

      int userid = 1;
      return cartRepository.removeCart(userid);
    }

    

  

}
