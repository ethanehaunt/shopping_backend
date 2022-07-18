package com.gupshup.shopping.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.gupshup.shopping.entity.Message;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.URL;
import java.util.Map;
import java.util.List;


@Repository
public class MessageRepository {

    private final String INSTANCE_ID = "YOUR_INSTANCE_ID_HERE";
    private final String CLIENT_ID = "YOUR_CLIENT_ID_HERE";
    private final String CLIENT_SECRET = "YOUR_CLIENT_SECRET_HERE";
    private final String WA_GATEWAY_URL = "http://api.whatsmate.net/v3/whatsapp/single/text/message/" + INSTANCE_ID;
   
    private static final String STORE_MESSAGE = "INSERT INTO messages(message,scheduled_at,destination_phone_number,userid,created_at,scheduled_status,submitted_at,submitted_status,whatsapp_api_message_id) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_MESSAGE = "SELECT * FROM messages WHERE scheduled_at < ? AND submitted_status = 0";
    private static final String UPDATE_MESSAGE = "UPDATE messages SET submitted_at=?,submitted_status=? WHERE messageid = ?";


    @Autowired
    @Qualifier("shoppingJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

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
                    rs.getBoolean("submitted_status"),
                    rs.getString("whatsapp_api_message_id")
                    );
      },currentdate);
      
      if (messageDetails.size() > 0) {

        //send valid and scheduled messages
        for (Message msg : messageDetails) 
          sendMessage(msg.getMessageID(),msg.getDestinationPhoneNumber(),msg.getMessage());
      }

    }


    public void sendMessage(int messageid,String number, String message) {
    
        try{

          System.out.println("Sending message: "+message+" to: "+ number+" ");
 
          SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          int response = jdbcTemplate.update(UPDATE_MESSAGE,DateFor.format(new Date()),1,messageid);
 


        //     // TODO: Should have used a 3rd party library to make a JSON string from an object
        //     String jsonPayload = new StringBuilder()
        //       .append("{")
        //       .append("\"number\":\"")
        //       .append(number)
        //       .append("\",")
        //       .append("\"message\":\"")
        //       .append(message)
        //       .append("\"")
        //       .append("}")
        //       .toString();

        //     URL url = new URL(WA_GATEWAY_URL);
        //     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //     conn.setDoOutput(true);
        //     conn.setRequestMethod("POST");
        //     conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        //     conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        //     conn.setRequestProperty("Content-Type", "application/json");

        //     OutputStream os = conn.getOutputStream();
        //     os.write(jsonPayload.getBytes());
        //     os.flush();
        //     os.close();

        //     int statusCode = conn.getResponseCode();
        //     System.out.println("Response from WA Gateway: \n");
        //     System.out.println("Status Code: " + statusCode);
        //     BufferedReader br = new BufferedReader(new InputStreamReader(
        //         (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
        //       ));
        //     String output;
        //     while ((output = br.readLine()) != null) {
        //         System.out.println(output);
        //     }

        //     conn.disconnect();


        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}