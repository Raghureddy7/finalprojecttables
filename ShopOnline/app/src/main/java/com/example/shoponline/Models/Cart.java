package com.example.shoponline.Models;

public class Cart {
    String User_name;
    String Product_id;

    public Cart()
    {

    }

    public Cart(String user_name, String product_id) {
        User_name = user_name;
        Product_id = product_id;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id)
    {
        Product_id = product_id;
    }
}
