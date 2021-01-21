package com.example.shoponline.Models;

import java.util.ArrayList;

public class SendOrder {

    String username;
    ArrayList<String> productid;
    String dateOrdered;
    double orderTotal;
    String Order_id;

    public SendOrder() {
    }

    public SendOrder(String username, ArrayList<String> productid, String dateOrdered, double orderTotal, String order_id) {
        this.username = username;
        this.productid = productid;
        this.dateOrdered = dateOrdered;
        this.orderTotal = orderTotal;
        Order_id = order_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getProductid() {
        return productid;
    }

    public void setProductid(ArrayList<String> productid) {
        this.productid = productid;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }
}
