package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Pe_usuario_paypal_PaymentDetails extends AppCompatActivity {

    TextView Recibo_1, Recibo_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_usuario_paypal__payment_details);

        Recibo_1 = findViewById(R.id.textView3);        //Username field
        Recibo_2 = findViewById(R.id.textView6);

        Intent intent = getIntent();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount){
        try {
            Recibo_2.setText(response.getString("id"));
            Recibo_1.setText(response.getString("$"+ paymentAmount));

        } catch(JSONException e){
            e.printStackTrace();
        }
    }
}