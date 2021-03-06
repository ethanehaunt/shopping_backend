package com.gupshup.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private int userid;
    private String name;
    private String email;
    private String password;
    private String authToken;
    private boolean isadmin;

    public Integer getUserid(){
    	return userid;
    }

    public Boolean getIsAdmin(){
    	return isadmin;
    }

}