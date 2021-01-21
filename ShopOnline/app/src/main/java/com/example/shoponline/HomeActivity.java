package com.example.shoponline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shoponline.Models.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ListView category_list;
    FirebaseFirestore database;
    List<String> categoryArray=new ArrayList<String>();
    ArrayAdapter arraylistadpater;
    ImageButton bagiconBtn;
    ImageButton profileiconBtn;
    ImageButton wishIconBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final TabLayout tabs = findViewById(R.id.tabs);
        category_list=findViewById(R.id.categories);
        database=FirebaseFirestore.getInstance();
        profileiconBtn=findViewById(R.id.profile);
        wishIconBtn=findViewById(R.id.wishlist);
        bagiconBtn=findViewById(R.id.cart);
        profileiconBtn.setOnClickListener(this);
        wishIconBtn.setOnClickListener(this);
        bagiconBtn.setOnClickListener(this);
        getData();
        arraylistadpater=new ArrayAdapter(this,android.R.layout.simple_list_item_1,categoryArray);

        category_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ProductsActivity.class);
                String selectedItem = (String) arraylistadpater.getItem(position);
                int tab_position=tabs.getSelectedTabPosition();
                String gender_selected="";
                switch(tab_position){
                    case 0: gender_selected="MEN";
                        break;
                    case 1: gender_selected="WOMEN";
                        break;
                    case 2: gender_selected="KIDS";
                        break;
                }
                intent.putExtra("genderSelected",gender_selected);
                intent.putExtra("selectedItem", selectedItem);
                startActivity(intent);
            }
        });

    }

    public void getData(){

        database.collection("category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document:task.getResult()){
                                Category cat=document.toObject(Category.class);
                                categoryArray.add(cat.getCategoryName().toString());
                            }
                            category_list.setAdapter(arraylistadpater);
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
        }
    }
}