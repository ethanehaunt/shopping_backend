package com.gupshup.shopping.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.gupshup.shopping.dao.MessageRepository;
import com.gupshup.shopping.util.ValidateUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

@RestController
public class messageController{

   @Autowired
   MessageRepository messageRepository;

    @PostMapping("/message")
    public String sendMessage(@RequestAttribute("user_token") String user_token,@RequestBody String messagejson){
      
      Gson gson = new Gson();
      Type mapType = new TypeToken<Map<String,String>>() {}.getType();
      Map<String,String> messagedata = gson.fromJson(messagejson,mapType);
      
      return messageRepository.storeMessage(Integer.parseInt(ValidateUtil.getUserID(user_token)),messagedata);

    }
    

}
