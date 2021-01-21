package com.example.shoponline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener  {
    Button SignInBtn;
    Button SignUpBtn;
    Button TestDataBtn;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        SignInBtn=findViewById(R.id.SignIn);
        SignUpBtn=findViewById(R.id.SignUP);
        SignInBtn.setOnClickListener(this);
        SignUpBtn.setOnClickListener(this);
        Intent  intent = new Intent(this.getApplicationContext(), HomeActivity.class);
        if(pref.contains("username") && pref.contains("password")){
            startActivity(intent);
        }
// to insert test data... to be hidden while running application
        TestDataBtn=findViewById(R.id.testDataBtn);
        TestDataBtn.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SignIn:
                Intent intent=new Intent(this.getApplicationContext(), LoginActivity.class);
                this.startActivity(intent);
                break;
            case R.id.SignUP:
                intent=new Intent(this.getApplicationContext(), RegisterActivity.class);
                this.startActivity(intent);
                break;
            case R.id.testDataBtn:
                intent=new Intent(this.getApplicationContext(), TestDataActivity.class);
                this.startActivity(intent);
                break;


        }
    }
}