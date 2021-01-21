package com.example.shoponline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shoponline.Adpaters.BagCustomAdpater;
import com.example.shoponline.Models.Products;
import com.example.shoponline.Models.Wishlist;
import com.example.shoponline.api_interface.JsonPlaceHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WishlistActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    ArrayList<Products> products=new ArrayList<Products>();
    ArrayList<String> wish_product_id= new ArrayList<>();
    FirebaseFirestore database;
    BagCustomAdpater adpater;
    SharedPreferences prf;
    String user_id="";
    ImageButton bagiconBtn;
    ImageButton profileiconBtn;
    ImageButton wishIconBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        user_id=prf.getString("username",null);
        database=FirebaseFirestore.getInstance();
        listView=findViewById(R.id.BagListView);
        profileiconBtn=findViewById(R.id.profile);
        wishIconBtn=findViewById(R.id.wishlist);
        bagiconBtn=findViewById(R.id.cart);
        profileiconBtn.setOnClickListener(this);
        wishIconBtn.setOnClickListener(this);
        bagiconBtn.setOnClickListener(this);
        getCartdetails();

        getData();
        adpater=new BagCustomAdpater(products);
        listView.setAdapter(adpater);
    }

    public void getCartdetails()
    {
        database.collection("wishlist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document:task.getResult()){
                                Wishlist cart_products=document.toObject(Wishlist.class);
                                if(cart_products.getUser_name().equals(user_id))
                                {
                                    wish_product_id.add(cart_products.getProduct_id());
                                }
                            }
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void getData(){

        database.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document:task.getResult()){
                                Products product=document.toObject(Products.class);
                                if(wish_product_id.contains(product.getProduct_id().toString()))
                                { // "PI00"+product.getImage_file()
                                    int resID = getResources().getIdentifier("sample" , "drawable", getPackageName());
                                    products.add(new Products(product.getProduct_id().toString(),product.getBrand_name().toString(),product.getProduct_name().toString(),product.getProduct_price().toString(),product.getProduct_cat().toString(),product.getGender().toString(),resID));


                                }
                            }
                            listView.setAdapter(adpater);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });


    }


    //retrofit


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cart:
                Intent intent=new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
                break;

            case R.id.profile:
                Intent _intent=new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(_intent);
                break;

            case R.id.wishlist:
                Intent wish_intent=new Intent(getApplicationContext(),WishlistActivity.class);
                startActivity(wish_intent);
                break;

        }
    }
}