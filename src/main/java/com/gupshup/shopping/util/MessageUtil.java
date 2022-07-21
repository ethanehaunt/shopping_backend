package com.gupshup.shopping.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gupshup.shopping.entity.Message;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;

@Service
public class MessageUtil {
	
    final private String CHARSET = "UTF-8";
    final private String BASEURL = "https://api.gupshup.io/sm/api/v1/msg";
    final private String SENDER = "917834811114";
    final private String SRCNAME = "shoppingAPI";
    final private String APIKEY = "ivshzmzfq4nnff808torbjaer5r9kr7v";

    private HttpURLConnection httpConn;
    private Map<String, Object> queryParams;

    public  Map<String,String> sendWhatsAppMessage(Message msg) {

        //initiate response map
        Map<String,String> response = new HashMap<String,String>();
        
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
            
            response = gettingTheResponseString();
            response.put("statuscode",Integer.toString(httpConn.getResponseCode()));

        }
        catch(Exception e){
            System.out.println(e);
        }

        return response;
    }

    private Map<String,String> gettingTheResponseString(){

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        String responseString = "";
        int length;
        
        try{
            while ((length = httpConn.getInputStream().read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            responseString = result.toString(CHARSET);
        }
        catch(UnsupportedEncodingException e){
           e.printStackTrace();
        }
        catch (IOException e) {  
            e.printStackTrace();  
        }

        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String,String>>() {}.getType();
        Map<String,String> response =  gson.fromJson(responseString,mapType);


        return response;
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
