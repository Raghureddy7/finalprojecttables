package com.example.shoponline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoponline.Models.Cart;
import com.example.shoponline.Models.Products;
import com.example.shoponline.Models.Wishlist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView product_image;
    TextView BrandName;
    TextView ProductName;
    TextView Price;
    TextView Description;
    Button Wishlistbtn;
    Button AddtoCartBtn;
    FirebaseFirestore database;
    String product_id;
    SharedPreferences prf;
    String user_id="";
    ImageButton bagiconBtn;
    ImageButton profileiconBtn;
    ImageButton wishIconBtn;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        user_id=prf.getString("username",null);
        database=FirebaseFirestore.getInstance();
        product_image=findViewById(R.id.productImage);
        BrandName=findViewById(R.id.brand_name);
        ProductName=findViewById(R.id.product_name);
        Price=findViewById(R.id.price);
        Toolbar myToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(myToolbar);
        ImageButton cartbtn = (ImageButton) findViewById(R.id.cart);
        profileiconBtn=findViewById(R.id.profile);
        wishIconBtn=findViewById(R.id.wishlist);
        bagiconBtn=findViewById(R.id.cart);
        Description=findViewById(R.id.details);
        AddtoCartBtn=findViewById(R.id.bagBtn);
        Wishlistbtn=findViewById(R.id.whishlistBtn);
        AddtoCartBtn.setOnClickListener(this);
        Wishlistbtn.setOnClickListener(this);
        profileiconBtn.setOnClickListener(this);
        wishIconBtn.setOnClickListener(this);
        cartbtn.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        product_id=bundle.getString("selectedItem");
        Description.setText("100 % Cotton");
        getData();

    }

    public void getData(){

        database.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            String product_name="",brand_name="",price="";
                            for(DocumentSnapshot document:task.getResult()){
                                int resID = getResources().getIdentifier("sample" , "drawable", getPackageName());
                                Products product=document.toObject(Products.class);
                                if(product.getProduct_id().equals(product_id))
                                {
                                    product_name=product.getProduct_name().toString();
                                    brand_name=product.getBrand_name().toString();
                                    price=product.getProduct_price().toString();
                                    // "PI00"+product.getImage_file()--> pass a name in below function
                                     resID = getResources().getIdentifier("sample" , "drawable", getPackageName());
                                }
                                ProductName.setText(product_name);
                                BrandName.setText(brand_name);
                                Price.setText(price);
                                product_image.setImageResource(resID);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bagBtn:
                SendtoBag();
                Toast.makeText(getApplicationContext(),"Go to bag to view added items",Toast.LENGTH_LONG).show();
                break;
            case R.id.whishlistBtn:
                sendWishlist();
                Toast.makeText(getApplicationContext(),"Go to Watchlist to view added items",Toast.LENGTH_LONG).show();
                break;
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

    public void sendWishlist(){
        Wishlist list= new Wishlist(user_id,product_id);
        database.collection("wishlist")
                .add(list)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Added To wishlist",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error. Try Again"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void SendtoBag(){
        Cart list= new Cart(user_id,product_id);
        database.collection("cart")
                .add(list)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Item added to cart",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error. Try Again"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    //retrofit


}