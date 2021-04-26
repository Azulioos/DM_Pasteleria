package com.example.dmpasteleria;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Pe_usuario_pasteles_personalizados extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CheckBox mIsNormal, mIsSmall, mIsLarge, mExtra_5, mSuperficie_5;
    CheckBox mExtra_1, mExtra_2, mExtra_3, mExtra_4, mExtra_6;
    CheckBox mSuperficie_2, mSuperficie_3, mSuperficie_4, mSuperficie_1, mSuperficie_6;
    ImageView mImagen;
    String[] mPan = {String.valueOf(R.string.Chocolate), String.valueOf(R.string.Vainilla)};
    String[] mCapas = {"1", "2", "3"};
    TextView mPanS;
    TextView mCapasS;
    ListView mListaV;
    List<EditText> mListaE;
    List<TextView> mListaVV;
    LinearLayout mRelleno_1, mRelleno_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_usuario_pasteles_personalizados);

        mRelleno_1 = findViewById(R.id.PastelesPersonalizados_12_1);
        mRelleno_2 = findViewById(R.id.PastelesPersonalizados_12_2);

        mIsSmall = findViewById(R.id.checkbox);
        mIsLarge = findViewById(R.id.checkbox2);
        mIsNormal = findViewById(R.id.checkbox3);
        mImagen = findViewById(R.id.PastelesPersonalizados_5);

        mExtra_5 = findViewById(R.id.Extra_5);
        mExtra_1 = findViewById(R.id.Extra_1);
        mExtra_2 = findViewById(R.id.Extra_2);
        mExtra_3 = findViewById(R.id.Extra_3);
        mExtra_4 = findViewById(R.id.Extra_4);
        mExtra_6 = findViewById(R.id.Extra_6);

        mSuperficie_5 = findViewById(R.id.Superficie_5);
        mSuperficie_1 = findViewById(R.id.Superficie_1);
        mSuperficie_2 = findViewById(R.id.Superficie_2);
        mSuperficie_3 = findViewById(R.id.Superficie_3);
        mSuperficie_4 = findViewById(R.id.Superficie_4);
        mSuperficie_6 = findViewById(R.id.Superficie_6);

        Spinner spinners = findViewById(R.id.PastelesPersonalizados_13);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.numero, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinners.setAdapter(adapter1);
        spinners.setOnItemSelectedListener(this);

        Spinner spinners_1 = findViewById(R.id.PastelesPersonalizados_13_1);
        ArrayAdapter<CharSequence> adapter1_1 = ArrayAdapter.createFromResource(this, R.array.capas, android.R.layout.simple_spinner_item);
        adapter1_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinners_1.setAdapter(adapter1_1);
        spinners_1.setOnItemSelectedListener(this);

        Spinner spinners_2 = findViewById(R.id.PastelesPersonalizados_13_2);
        ArrayAdapter<CharSequence> adapter1_2 = ArrayAdapter.createFromResource(this, R.array.capas, android.R.layout.simple_spinner_item);
        adapter1_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinners_2.setAdapter(adapter1_2);
        spinners_2.setOnItemSelectedListener(this);

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

        mSuperficie_5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                mSuperficie_1.setChecked(false);
                mSuperficie_2.setChecked(false);
                mSuperficie_3.setChecked(false);
                mSuperficie_4.setChecked(false);
                mSuperficie_6.setChecked(false);
            }
        });

        mExtra_5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                mExtra_1.setChecked(false);
                mExtra_2.setChecked(false);
                mExtra_3.setChecked(false);
                mExtra_4.setChecked(false);
                mExtra_6.setChecked(false);
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

        String Validar_1 = mCapasS.getText().toString().trim();
        if(Validar_1.equals("1")){
            mRelleno_2.setVisibility(View.GONE);
            mRelleno_1.setVisibility(View.GONE);
        }
        String Validar_2 = mCapasS.getText().toString().trim();
        if(Validar_1.equals("2")){
            mRelleno_2.setVisibility(View.GONE);
            mRelleno_1.setVisibility(View.VISIBLE);
        }

        String Validar_3 = mCapasS.getText().toString().trim();
        if(Validar_1.equals("3")){
            mRelleno_2.setVisibility(View.VISIBLE);
            mRelleno_1.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}