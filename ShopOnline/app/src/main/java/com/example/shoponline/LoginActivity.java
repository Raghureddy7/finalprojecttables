package com.example.shoponline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoponline.Models.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button LoginBtn;
    EditText username;
    EditText password;
    int login_flag=0;
    SharedPreferences pref;

    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database=FirebaseFirestore.getInstance();
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        LoginBtn=findViewById(R.id.loginBtn);
        username=findViewById(R.id.EmailID);
        password=findViewById(R.id.password);
        Intent  intent = new Intent(this.getApplicationContext(), HomeActivity.class);
        if(pref.contains("username") && pref.contains("password")){
            startActivity(intent);
        }
        LoginBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String user_name=username.getText().toString();
        String _password=password.getText().toString();
        getdata(user_name,_password);
    if(login_flag==1)
            {
        Intent intent=new Intent(this.getApplicationContext(), HomeActivity.class);
        this.startActivity(intent);
            }
    else
        Toast.makeText(getApplicationContext(),"Invalid details. Try Again",Toast.LENGTH_LONG).show();
    }

    public void getdata(final String username, final String password){
        database.collection("userAccount")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document:task.getResult()){
                                UserAccount user=document.toObject(UserAccount.class);
                                if(username.equals(user.getEmail()) &&  password.equals(user.getPassword())){
                                    login_flag=1;
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("username",username);
                                    editor.putString("password",password);
                                    editor.commit();
                                    break;
                                }
                            }
                            Toast.makeText(getApplicationContext(),"Please wait while we process",Toast.LENGTH_LONG).show();
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