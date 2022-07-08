package com.gupshup.shopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.gupshup.shopping.dao.UserRepository;
import com.gupshup.shopping.util.ValidateUtil;
import com.gupshup.shopping.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
public class loginController{

   @Autowired
   UserRepository userRepository;

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody String loginjson){
      
      Gson gson = new Gson();
      Type mapType = new TypeToken<Map<String,String>>() {}.getType();
      Map<String,String> logindata = gson.fromJson(loginjson,mapType);

      return userRepository.login(logindata);
    }

    @GetMapping("/auth")
    public Map<String,Boolean> Authenticate(@RequestAttribute("user_token") String user_token){
      return ValidateUtil.Authenticate(user_token);
    }


}
