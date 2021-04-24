package com.example.dmpasteleria;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Pe_usuario_pasteles_personalizados extends AppCompatActivity {

    CheckBox mIsNormal, mIsSmall, mIsLarge;
    ImageView mImagen;
    String[] mPan = {String.valueOf(R.string.Chocolate), String.valueOf(R.string.Vainilla)};
    TextView mPanS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_usuario_pasteles_personalizados);

        mIsSmall = findViewById(R.id.checkbox);
        mIsLarge = findViewById(R.id.checkbox2);
        mIsNormal = findViewById(R.id.checkbox3);
        mImagen = findViewById(R.id.PastelesPersonalizados_5);

        mIsNormal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                mIsSmall.setChecked(false);
                mIsLarge.setChecked(false);
                mImagen.setImageResource(R.drawable.icono_pastel_chico);
            }
        });

        mIsSmall.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                mIsNormal.setChecked(false);
                mIsLarge.setChecked(false);
                mImagen.setImageResource(R.drawable.icono_pastel);
            }
        });

        mIsLarge.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                mIsNormal.setChecked(false);
                mIsSmall.setChecked(false);
                mImagen.setImageResource(R.drawable.icono_pastel_grande);
            }
        });

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(Pe_usuario_pasteles_personalizados.this,
                android.R.layout.simple_spinner_item, mPan);

        mPanS = (TextView)findViewById(R.id.PastelesPersonalizados_7);
        final Spinner sp = new Spinner(Pe_usuario_pasteles_personalizados.this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);

        AlertDialog.Builder builder = new AlertDialog.Builder(Pe_usuario_pasteles_personalizados.this);
        builder.setView(sp);
        builder.create().show();

    }
}