package com.example.dmpasteleria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.synnapps.carouselview.CarouselView;

import java.util.Locale;

public class Pe_usuario_inicio extends AppCompatActivity {
    FirebaseAuth fAuth;

    //Creación del carousel
    private final int[] mImages = new int[] {
            R.drawable.cake1, R.drawable.cake2, R.drawable.cake3, R.drawable.cake4, R.drawable.cake5
    };

    //Coloca los titulos de cada imagen del carousel
    private final String[] mImagesTitle = new String[]{
            "Pastel 1", "Pastel 2", "Pastel 3", "Pastel 4", "Pastel 5"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_usuario_inicio);
        loadLocale();

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), Pe_inicio.class));
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar_i);
        setSupportActionBar(toolbar);


        CarouselView carouselView = findViewById(R.id.carousel);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener((position, imageView) -> imageView.setImageResource(mImages[position]));
        carouselView.setImageClickListener(position -> {
            Toast.makeText( Pe_usuario_inicio.this, mImagesTitle[position] , Toast.LENGTH_SHORT).show();   //Muestra un mensaje al hacer click en el pastel
        });

        CarouselView carouselView2 = findViewById(R.id.carousel_2);
        carouselView2.setPageCount(mImages.length);
        carouselView2.setImageListener((position, imageView) -> imageView.setImageResource(mImages[position]));
        carouselView2.setImageClickListener(position -> {
            Toast.makeText( Pe_usuario_inicio.this, mImagesTitle[position] , Toast.LENGTH_SHORT).show();   //Muestra un mensaje al hacer click en el pastel
        });

        CarouselView carouselView3 = findViewById(R.id.carousel_3);
        carouselView3.setPageCount(mImages.length);
        carouselView3.setImageListener((position, imageView) -> imageView.setImageResource(mImages[position]));
        carouselView3.setImageClickListener(position -> {
            Toast.makeText( Pe_usuario_inicio.this, mImagesTitle[position] , Toast.LENGTH_SHORT).show();   //Muestra un mensaje al hacer click en el pastel
        });
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }

    public void showChangeLanguageDialog(MenuItem menu) {
        final String[] listItems = {"English","Español", "Portugues"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Pe_usuario_inicio.this);
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
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        startActivity(new Intent(getApplicationContext(), Pe_user_settings.class));
        finish();
    }

    public void Personalized(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_usuario_pasteles_personalizados.class));
        finish();
    }
}