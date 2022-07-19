package com.gupshup.shopping.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gupshup.shopping.entity.Message;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;

@Service
public class MessageUtil {
	
    final private String CHARSET = "UTF-8";
    final private String BASEURL = "https://api.gupshup.io/sm/api/v1/msg";
    final private String SENDER = "9163214034";
    final private String SRCNAME = "sivasaiWhatsAppAPI";
    final private String APIKEY = "7lh5yhdz7krvgcjb2tj6m24vltgl2pxr";

    private HttpURLConnection httpConn;
    private Map<String, Object> queryParams;

    public Integer sendWhatsAppMessage(Message msg) {

        int status = 0;
        
        //construct query parameters from msg
        settingTheQueryParams(msg);     
       
        try{
             
            //setting url and its credentails
            URL url = new URL(BASEURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");            
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("apikey", APIKEY);
            httpConn.setRequestProperty("Accept", "application/json");
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            
            //putting together and finishing the request
            byte[] postDataBytes = this.getParamsByte(queryParams);
            httpConn.getOutputStream().write(postDataBytes);
            
            //getting the API response
            status = httpConn.getResponseCode();

        }
        catch(Exception e){
            System.out.println(e);
        }

        return status;
    }
    
    //get message as json param
    private String getMessageAsJsonParams(String message){
        
        String jsonParam = null;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode user = mapper.createObjectNode();
        user.put("type", "text");
        user.put("text", message);
        
        try {
            jsonParam = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        }catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        return jsonParam;
    }

    //construct query parameters from msg
    private void settingTheQueryParams(Message msg){

        this.queryParams = new HashMap<>();
        queryParams.put("channel", "whatsapp");
        queryParams.put("source", SENDER);
        queryParams.put("src.name", SRCNAME);
        queryParams.put("disablePreview", true);
        queryParams.put("destination", msg.getDestinationPhoneNumber());
        queryParams.put("message", getMessageAsJsonParams(msg.getMessage()));
    }

    //encode the request parameters to bytes stream
    private byte[] getParamsByte(Map<String, Object> params) {
        
        byte[] result = null;
        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(this.encodeParam(param.getKey()));
            postData.append('=');
            postData.append(this.encodeParam(String.valueOf(param.getValue())));
        }

        try {
            result = postData.toString().getBytes(CHARSET);
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    //method to encode the parameters 
    private String encodeParam(String data) {        
        
        String result = "";
        
        try {
            result = URLEncoder.encode(data,CHARSET);
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

}
