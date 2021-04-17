package com.example.dmpasteleria;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class Pe_usuario_inicio extends AppCompatActivity {


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

        Toolbar toolbar = findViewById(R.id.toolbar_i);
        setSupportActionBar(toolbar);

        CarouselView carouselView = findViewById(R.id.carousel);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText( Pe_usuario_inicio.this, mImagesTitle[position] , Toast.LENGTH_SHORT).show();   //Muestra un mensaje al hacer click en el pastel
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;

    }
}