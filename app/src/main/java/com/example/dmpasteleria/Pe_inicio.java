package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pe_inicio extends AppCompatActivity {
    Intent i;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_inicio);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            fAuth.signOut();
            startActivity(new Intent(getApplicationContext(),Pe_inicio.class));
            finish();
        }
    }

    public void Registro(View view) {
        i = new Intent(getApplicationContext(), Pe_registro.class);
        startActivity(i);
    }

    public void Inicio(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_iniciodesesion.class));
        finish();
    }

}