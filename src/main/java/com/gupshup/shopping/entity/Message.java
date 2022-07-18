package com.gupshup.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {


    private int messageid;
    private String message;
    private Timestamp scheduled_at;
    private String destination_phone_number;
    private int userid;
    private Timestamp created_at;
    private boolean scheduled_status;
    private Timestamp submitted_at;
    private boolean submitted_status;
    private String whatsapp_api_message_id ;

    public Integer getMessageID(){
        return messageid;
    }

    public String getMessage(){
        return message;
    }

    public String getDestinationPhoneNumber(){
        return destination_phone_number;
    }
}