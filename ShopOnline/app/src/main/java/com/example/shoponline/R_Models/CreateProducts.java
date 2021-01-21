package com.example.shoponline.R_Models;

public class CreateProducts {

    int id;
    String brand_name;
    String product_name;
    String product_price;
    String product_cat;
    String gender;
    String image_file;

    public CreateProducts(String brand_name, String product_name, String product_price, String product_cat, String gender, String image_file) {
        this.brand_name = brand_name;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_cat = product_cat;
        this.gender = gender;
        this.image_file = image_file;
    }





    public int getProduct_id() {
        return id;
    }

    public void setProduct_id(int id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage_file() {
        return image_file;

    }

    public void setImage_file(String image_file) {
        this.image_file = image_file;
    }
}
