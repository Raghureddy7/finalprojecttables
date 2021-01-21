package com.example.shoponline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoponline.Adpaters.BagCustomAdpater;
import com.example.shoponline.Models.Cart;
import com.example.shoponline.Models.Products;
import com.example.shoponline.Models.SendOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    ArrayList<Products> products=new ArrayList<Products>();
    ArrayList<String> cart_product_id= new ArrayList<>();
    FirebaseFirestore database;
    BagCustomAdpater adpater;
    TextView TotalMrp;
    TextView Taxes;
    TextView TotalAmount;
    Button placeOrder;
    double totalAmount=0;
    SharedPreferences prf;
    String user_id="";
    String product_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        user_id=prf.getString("username",null);
        database=FirebaseFirestore.getInstance();
        listView=findViewById(R.id.BagListView);
        TotalAmount=findViewById(R.id.totalamount);
        Taxes=findViewById(R.id.Totaltaxes);
        TotalMrp=findViewById(R.id.TotalMrp);
        getCartdetails();
        getData();
        adpater=new BagCustomAdpater(products);
        listView.setAdapter(adpater);


        placeOrder=findViewById(R.id.place_orderBtn);
        placeOrder.setOnClickListener(this);

    }
public void getCartdetails()
{
    database.collection("cart")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot document:task.getResult()){
                            Cart cart_products=document.toObject(Cart.class);
                            if(cart_products.getUser_name().equals(user_id))
                            {
                                product_id=cart_products.getProduct_id().toString();
                               cart_product_id.add(cart_products.getProduct_id());
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
                            int totalmrp=0;
                            for(DocumentSnapshot document:task.getResult()){
                                Products product=document.toObject(Products.class);
                                if(cart_product_id.contains(product.getProduct_id().toString()))
                                { // "PI00"+product.getImage_file()
                                    int resID = getResources().getIdentifier("sample" , "drawable", getPackageName());
                                    products.add(new Products(product.getProduct_id().toString(),product.getBrand_name().toString(),product.getProduct_name().toString(),product.getProduct_price().toString(),product.getProduct_cat().toString(),product.getGender().toString(),resID));
                                    String temp= product.getProduct_price().split("\\$")[1].split("\\.")[0];

                                   totalmrp += Integer.parseInt(temp);
                                }
                            }
                            double withtaxes=totalmrp*(0.15);
                            TotalMrp.setText("$"+totalmrp+".00");
                            totalAmount=totalmrp+withtaxes;
                            Taxes.setText(("$"+withtaxes));
                            TotalAmount.setText("$"+totalAmount);

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

    @Override
    public void onClick(View v) {

        ArrayList<String> products=cart_product_id;
        double total=totalAmount;
        sendOrder(user_id,products,total);

    }

    public void sendOrder(String username, ArrayList<String> products, final double total){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        SimpleDateFormat order_id = new SimpleDateFormat("yyyyMMdd'A'HHmmss");
        String currentDateandTime = sdf.format(new Date());
        String new_order_id=order_id.format(new Date());
        SendOrder new_order=new SendOrder(username,products,currentDateandTime,total,new_order_id);
        database.collection("sendOrder")
                .add(new_order)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Order sent Continue To payment",Toast.LENGTH_LONG).show();
                        clearCart();
                        Toast.makeText(getApplicationContext(),"Continue to Payment",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
                        intent.putExtra("total","$"+total);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error. Try Again"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void clearCart(){
        final CollectionReference ref = database.collection("cart");
        Query query = ref.whereEqualTo("user_name", user_id);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        ref.document(document.getId()).delete();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Error. Try Again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}


