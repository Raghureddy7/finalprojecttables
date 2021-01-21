package com.example.shoponline.api_interface;


import com.example.shoponline.R_Models.CreateProducts;
import com.example.shoponline.R_Models.ViewProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolder {

    @POST("addproduct")
    Call<CreateProducts> Sendproduct(@Body CreateProducts products_new);

    @POST("viewproduct")
    Call<List<CreateProducts>> View_product(@Body ViewProduct product_view);


}
