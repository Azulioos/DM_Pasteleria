package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Pe_change_settings extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_settings);
    }

    public void Regresar(View view) {
        startActivity(new Intent(getApplicationContext(),Pe_user_settings.class));
        finish();
    }
}
