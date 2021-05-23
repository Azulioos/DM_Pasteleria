package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class Pe_admin_inicio extends AppCompatActivity {


    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_admin_inicio);


        Toolbar toolbar = findViewById(R.id.toolbar_i);
        setSupportActionBar(toolbar);
    }

    public void gadmin1(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_admin_agregarp.class));
        finish();
    }

    public void gadmin2(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_admin_agregari.class));
        finish();
    }

    public void gadmin3(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_admin_historial.class));
        finish();
    }

    public void gadmin4(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_empleado_inicio.class));
        finish();
    }

    public void gadmin5(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_admin_expandira.class));
        finish();
    }

    public void gadmin6(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_admin_gestionari.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.amenu,menu);
        return true;

    }

    public void logout(MenuItem item) {
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        startActivity(new Intent(getApplicationContext(), Pe_inicio.class));
        finish();
    }

    public void openUserAccount(MenuItem item) {
        Log.d(TAG, "Abriendo actividad");
        startActivity(new Intent(getApplicationContext(), Pe_user_settings.class));
        Log.d(TAG, "Post abriendo actividad");
        finish();
    }
}