package com.example.shoponline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoponline.Models.Category;
import com.example.shoponline.Models.Products;
import com.example.shoponline.R_Models.CreateProducts;
import com.example.shoponline.api_interface.JsonPlaceHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestDataActivity extends AppCompatActivity implements View.OnClickListener {

    Button addTestDatabtn;
    FirebaseFirestore database;

    EditText product_name;
    EditText brand_name;
    EditText price;
    EditText gender;
    EditText image_file;
    EditText product_cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_data);
        addTestDatabtn=findViewById(R.id.addTestData);

        product_name=findViewById(R.id.product_name);
        brand_name=findViewById(R.id.brand_name);
        price=findViewById(R.id.product_price);
        gender=findViewById(R.id.gender);
        image_file=findViewById(R.id.image_file);
        product_cat=findViewById(R.id.product_cat);

        addTestDatabtn.setOnClickListener(this);
        database=FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(getApplicationContext(),product_name.getText().toString(),Toast.LENGTH_LONG).show();
        //retrofit
        create_product(brand_name.getText().toString(),product_name.getText().toString(),price.getText().toString(),product_cat.getText().toString(),gender.getText().toString(),image_file.getText().toString());


        //testdata to add products
       /*addProduct("PI0001","Reebok","Short","$12.00","Shorts","MEN",1);*/





    }

    //retrofit
    public void create_product( String brand,String product,String price,String product_cat,String gender,String imgid){
        CreateProducts product_=new CreateProducts( brand, product, price, product_cat, gender, imgid);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http:// 192.168.2.18:3060/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolder JsonHolder=retrofit.create(JsonPlaceHolder.class);
        Call<CreateProducts> call=JsonHolder.Sendproduct(product_);

        call.enqueue(new Callback<CreateProducts>() {
            @Override
            public void onResponse(Call<CreateProducts> call, Response<CreateProducts> response) {
                if(!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),"Code :"+response.code(),Toast.LENGTH_LONG).show();
                    return;
                }

                CreateProducts product_new= response.body();

                String content="";
                content+="Response Code: "+response.code()+"\n";
                content+="id: "+product_new.getProduct_id()+"\n";
                content+="product Name: "+product_new.getProduct_name()+"\n";
                content+="Brand Name: "+product_new.getBrand_name()+"\n";
                content+="Product Category: "+product_new.getProduct_cat()+"\n";
                content+="Gender: "+product_new.getGender()+"\n";
                content+="Price: "+product_new.getProduct_price()+"\n";
                Toast.makeText(getApplicationContext(),content,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<CreateProducts> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }


    public void addCategory(String name){
        Category new_cat=new Category(name);
        database.collection("category")
                .add(new_cat)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Category Submitted",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error. Try Again"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void addProduct(String product_id,String brand,String product,String price,String product_cat,String gender,int imgid){
        Products new_cat=new Products( product_id, brand, product, price, product_cat, gender, imgid);
        database.collection("products")
                .add(new_cat)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"product Submitted",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error. Try Again"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}