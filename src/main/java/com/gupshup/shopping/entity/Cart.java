package com.gupshup.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {


    private int cartid;
    private int userid;
    private int qty;
    private int itemid;

    public Integer getQty(){
    	return qty;
    }

}