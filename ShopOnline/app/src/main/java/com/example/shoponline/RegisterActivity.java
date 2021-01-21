package com.example.shoponline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoponline.Models.UserAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText passoword;
    EditText cpassword;
    Button submit;

    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstName=findViewById(R.id.firstname);
        lastName=findViewById(R.id.Lastname);
        passoword=findViewById(R.id.password);
        cpassword=findViewById(R.id.confirm_password);
        email=findViewById(R.id.EmailID);
        submit=findViewById(R.id.submitBtn);
        submit.setOnClickListener(this);

        database=FirebaseFirestore.getInstance();


    }

    @Override
    public void onClick(View v) {
        String _firstName=firstName.getText().toString();
        String _lastName=lastName.getText().toString();
        String _passoword=passoword.getText().toString();
        String _cpassword=cpassword.getText().toString();
        String _email=email.getText().toString();

        if(!_passoword.equals(_cpassword))
        {
            Toast.makeText(getApplicationContext(),"Passwords did not match",Toast.LENGTH_LONG).show();        }
else
        {
            addUser(_firstName,_lastName,_email,_passoword);
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

    public void addUser(String Fname,String Lname,String Email,String Password){
        UserAccount new_user=new UserAccount(Fname,Lname,Email,Password);
        database.collection("userAccount")
                .add(new_user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_LONG).show();
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