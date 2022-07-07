package com.gupshup.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {


    private int _id;
    private String product;
    private String company;
    private String color;
    private int price;
    private String description;
    private String image;
    private boolean isbestseller;
    private boolean isnew;
    private boolean inStock;
    private int rating;

}