package com.example.dmpasteleria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class Pe_ayuda_informacion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_ayuda_informacion);
        Toolbar toolbar = findViewById(R.id.toolbar_i);
        setSupportActionBar(toolbar);

        loadLocale();
    }

    public void Volver(View view) {
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        startActivity(new Intent(getApplicationContext(), Pe_inicio.class));
        finish();
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }

    public void showChangeLanguageDialog(MenuItem menu) {
        final String[] listItems = {"English","Español", "Portugues"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Pe_ayuda_informacion.this);
        mBuilder.setTitle("Escoge un idioma");
        mBuilder.setSingleChoiceItems(listItems, -1, (dialogInterface, i) -> {
            if(i == 0){
                setLocale("en");
                recreate();
            }else if(i == 1){
                setLocale("es");
                recreate();
            }else if (i == 2){
                setLocale(("pt"));
                recreate();
            }
            dialogInterface.dismiss();
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;

    }

    public void signOut(MenuItem item) {
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        startActivity(new Intent(getApplicationContext(), Pe_inicio.class));
        finish();
    }


    public void openUserAccount(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), Pe_user_settings.class));
        finish();
    }

    public void Payments(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), Pe_usuario_pedidos_paypal.class));
        finish();
    }

    public void Ayuda(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), Pe_ayuda_informacion.class));
        finish();
    }

    public void Pedidos(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), Pe_usuario_pedidos.class));
        finish();
    }
}