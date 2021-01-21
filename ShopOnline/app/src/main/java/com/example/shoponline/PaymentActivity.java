package com.example.shoponline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    Button order;
    TextView Amount;
    String total_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        order=findViewById(R.id.orderBtn);
        Amount=findViewById(R.id.cardAmount);
        Bundle bundle = getIntent().getExtras();
        total_amount=bundle.getString("total");
        Amount.setText("CREDIT CARD :"+total_amount);
        order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(),"Payment done. Continue Shopping",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }
}