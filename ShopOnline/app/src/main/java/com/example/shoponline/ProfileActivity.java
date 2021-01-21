package com.example.shoponline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoponline.Models.SendOrder;
import com.example.shoponline.Models.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView FirstName;
    TextView LastName;
    TextView Email;
    FirebaseFirestore database;
    SharedPreferences prf;
    String userid;
    Button LogoutBtn;
    ListView orders_list;
    List<String> ordersArray=new ArrayList<String>();
    ArrayAdapter arraylistadpater;
    ImageButton bagiconBtn;
    ImageButton profileiconBtn;
    ImageButton wishIconBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirstName=findViewById(R.id.first_name);
        LastName=findViewById(R.id.last_name);
        Email=findViewById(R.id.username);
        LogoutBtn=findViewById(R.id.LogoutBtn);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        userid=prf.getString("username",null);
        database=FirebaseFirestore.getInstance();
        orders_list=findViewById(R.id.order_history);
        profileiconBtn=findViewById(R.id.profile);
        wishIconBtn=findViewById(R.id.wishlist);
        bagiconBtn=findViewById(R.id.cart);
        profileiconBtn.setOnClickListener(this);
        wishIconBtn.setOnClickListener(this);
        bagiconBtn.setOnClickListener(this);
        LogoutBtn.setOnClickListener(this);
        getuser(userid);
 getOrders(userid);
        arraylistadpater=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ordersArray);

        orders_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),OrdersActivity.class);
                String selectedItem = (String) arraylistadpater.getItem(position);
                intent.putExtra("selectedItem", selectedItem);
                startActivity(intent);
            }
        });
    }
    public void getOrders(final String user_name){

        database.collection("sendOrder")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document:task.getResult()){
                                SendOrder order=document.toObject(SendOrder.class);
                                if(user_name.equals(order.getUsername())) {
                                    ordersArray.add(order.getOrder_id().toString());
                                }
                            }
                            orders_list.setAdapter(arraylistadpater);
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
    public void getuser(final String user_name){
        database.collection("userAccount")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            String firstname="", lastname="";
                            for(DocumentSnapshot document:task.getResult()){
                                UserAccount user=document.toObject(UserAccount.class);
                                if(user_name.equals(user.getEmail())){
                                    firstname=user.getFirstname();
                                    lastname=user.getLastname();
                                    break;
                                }
                            }
                            FirstName.setText("First Name:  "+firstname);
                            LastName.setText("Last Name:  "+lastname);
                            Email.setText("Email:  "+user_name);
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

            case R.id.LogoutBtn:
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                Intent log_intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(log_intent);
                break;
        }
    }
}