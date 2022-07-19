package com.gupshup.shopping.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import com.gupshup.shopping.entity.Message;
import com.gupshup.shopping.util.MessageUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.List;


@Repository
public class MessageRepository {
    
    private static final String STORE_MESSAGE = "INSERT INTO messages(message,scheduled_at,destination_phone_number,userid,created_at,scheduled_status,submitted_at,submitted_status,whatsapp_api_message_id) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_MESSAGE = "SELECT * FROM messages WHERE scheduled_at < ? AND scheduled_status = 1";
    private static final String UPDATE_MESSAGE = "UPDATE messages SET scheduled_status=?,submitted_at=?,submitted_status=? WHERE messageid = ?";

    @Autowired
    @Qualifier("shoppingJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MessageUtil messageUtill;

    public String storeMessage(int userid,Map<String,String> messagedata){

      SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      int response = jdbcTemplate.update(STORE_MESSAGE,messagedata.get("message"),messagedata.get("scheduled_at"),messagedata.get("phonenumber"),userid,DateFor.format(new Date()),1,DateFor.format(new Date()),0,"");
      return "success";
    }


    public void fetchScheduleMessage(String currentdate){

        System.out.println("Fixed rate Scheduler: Task running at - "+ currentdate);
      
        List<Message> messageDetails = jdbcTemplate.query(SELECT_MESSAGE, (rs, rowNum) -> {
            return new Message(rs.getInt("messageid"),
                    rs.getString("message"),
                    rs.getTimestamp("scheduled_at"),
                    rs.getString("destination_phone_number"),
                    rs.getInt("userid"),
                    rs.getTimestamp("created_at"),
                    rs.getBoolean("scheduled_status"),
                    rs.getTimestamp("submitted_at"),
                    rs.getInt("submitted_status"),
                    rs.getString("whatsapp_api_message_id")
                    );
        },currentdate);
      
        if (messageDetails.size() == 0)
            System.out.println("No message are scheduled: Task running at - "+ currentdate);

        for (Message msg : messageDetails) 
            sendMessage(msg);
        
    }


    public void sendMessage(Message msg) {

        int status = 0;
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //getting the API response
        status = messageUtill.sendWhatsAppMessage(msg);

        System.out.println("response status from api  - "+ status);
        int response = jdbcTemplate.update(UPDATE_MESSAGE,0,DateFor.format(new Date()),status,msg.getMessageID());
        
    }

    
    

}