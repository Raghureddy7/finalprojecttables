package com.example.shoponline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoponline.Adpaters.ProductsCustomeAdpater;
import com.example.shoponline.Models.Products;
import com.example.shoponline.Models.SendOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Products> products=new ArrayList<Products>();
    ArrayList<String> order_product_id= new ArrayList<>();
    FirebaseFirestore database;
    ProductsCustomeAdpater adpater;
    TextView TotalAmount;
    TextView orderDate;
    TextView OrderID;
    SharedPreferences prf;
    String user_id="";
    String order_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        user_id=prf.getString("username",null);
        database=FirebaseFirestore.getInstance();
        listView=findViewById(R.id.OrderListView);
        TotalAmount=findViewById(R.id.OrderTotal);
        orderDate=findViewById(R.id.OrderDate);
        OrderID=findViewById(R.id.order_idtxt);
        Bundle bundle = getIntent().getExtras();
        order_id=bundle.getString("selectedItem");
        getCartdetails(order_id);
        getData();
        adpater=new ProductsCustomeAdpater(products);
        listView.setAdapter(adpater);

    }

    public void getCartdetails(final String id)
    {
        database.collection("sendOrder")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            String orderid="",orderdate="";
                            double orderTotal=0;
                            for(DocumentSnapshot document:task.getResult()){
                                SendOrder order=document.toObject(SendOrder.class);
                                if(id.equals(order.getOrder_id()))
                                {
                                    order_product_id=order.getProductid();
                                    orderTotal=order.getOrderTotal();
                                    orderid=order.getOrder_id();
                                    orderdate=order.getDateOrdered();
                                }
                            }
                            OrderID.setText(orderid);
                            TotalAmount.setText("Order Total : S"+orderTotal);
                            orderDate.setText("Order Date :"+orderdate);

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
                                if(order_product_id.contains(product.getProduct_id().toString()))
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
}