package com.gupshup.shopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.gupshup.shopping.dao.MessageRepository;

@RestController
public class messageController{

    @Autowired

    @PostMapping("/message")
    public void sendMessage(){
      

      String number = "9163214034";  //  TODO: Specify the recipient's number here. NOT the gateway number
      String message = "Howdy, isn't this exciting?";

      MessageRepository.sendMessage(number, message);
    }
    

}
