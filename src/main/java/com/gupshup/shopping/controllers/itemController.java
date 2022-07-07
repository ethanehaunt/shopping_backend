package com.gupshup.shopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import com.gupshup.shopping.dao.ItemRepository;
import com.gupshup.shopping.entity.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
public class itemController{

   @Autowired
   ItemRepository itemRepository;

   @GetMapping("/items")
   public List<Item> items(){
   	  return itemRepository.items();
   }

   @GetMapping("/items/{search}")
   public List<Item> itemSearch(@PathVariable("search") String search){

      System.out.println(search.split("="));

      String searchitem = search.split("=")[0];
      String searchvalue = search.split("=")[1];

      return itemRepository.itemSearch(searchitem,searchvalue);
   }

   @PostMapping("/additem")
   public String addItem(@RequestBody String itemjson){
      
      Gson gson = new Gson();
      Type mapType = new TypeToken<Map<String,String>>() {}.getType();
      Map<String,String> itemdata = gson.fromJson(itemjson,mapType);

      return itemRepository.addItem(itemdata);
    }

    @GetMapping("/category")
    public List<String> itemCategory(){
      return itemRepository.itemCategory();
    }

    @PostMapping("/updateitem")
    public String updateItem(@RequestBody String itemjson){
      
      Gson gson = new Gson();
      Type mapType = new TypeToken<Map<String,String>>() {}.getType();
      Map<String,String> itemdata = gson.fromJson(itemjson,mapType);
      
      return itemRepository.updateItem(itemdata);
    }
   
  	@DeleteMapping("/removeitem/{_id}")
    public String removeItem(@PathVariable("_id") int itemid){
       return itemRepository.removeItem(itemid);
    }

  

}
