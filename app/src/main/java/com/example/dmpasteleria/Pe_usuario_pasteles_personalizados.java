package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pe_usuario_pasteles_personalizados extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String mProducto;
    String mPrecio;

    Button mValidarBtn, mProcederBtn;
    CheckBox mIsNormal, mIsSmall, mIsLarge, mExtra_5, mSuperficie_5;
    CheckBox mExtra_1, mExtra_2, mExtra_3, mExtra_4, mExtra_6;
    CheckBox mSuperficie_2, mSuperficie_3, mSuperficie_4, mSuperficie_1, mSuperficie_6;
    ImageView mImagen;
    String[] mPan = {String.valueOf(R.string.Chocolate), String.valueOf(R.string.Vainilla)};
    TextView mPanS;
    TextView mCapasS;
    LinearLayout mRelleno_1, mRelleno_2;
    FirebaseFirestore fStore;
    TableLayout Recibo;

    float PrecioFinal = 0;
    float Precio = 0;
    float Capa = 0;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String[] PPRP = new String[20];
    String[] PPRC = new String[20];
    int Contador_1 = 0;

    private String[]header={"Producto", "Costo"};
    private TableLayout tableLayout;
    private ArrayList<String[]> rows=new ArrayList<>();
    private static final String TAG = "Pedidos";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_usuario_pasteles_personalizados);


        Pe_usuario_pasteles_personalizados_tabla tableDynamic = new Pe_usuario_pasteles_personalizados_tabla(tableLayout,getApplicationContext());
        tableDynamic.addHeader(header);

        mValidarBtn = findViewById(R.id.PastelesPersonalizados_20);
        mProcederBtn = findViewById(R.id.PastelesPersonalizados_22);


        tableLayout =(TableLayout)findViewById(R.id.PastelesPersonalizados_21);
        fStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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
                Precio = 0.8f;
                Capa = 79.99f;

            }
        });

        mIsSmall.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                mIsNormal.setChecked(false);
                mIsLarge.setChecked(false);
                mImagen.setImageResource(R.drawable.icono_pastel);
                Precio = 1f;
                Capa = 99.99f;
            }
        });

        mIsLarge.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                mIsNormal.setChecked(false);
                mIsSmall.setChecked(false);
                mImagen.setImageResource(R.drawable.icono_pastel_grande);
                Precio = 1.2f;
                Capa = 129.99f;
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

        if(Validar_1.equals("2")){
            mRelleno_2.setVisibility(View.GONE);
            mRelleno_1.setVisibility(View.VISIBLE);
        }

        if(Validar_1.equals("3")){
            mRelleno_2.setVisibility(View.VISIBLE);
            mRelleno_1.setVisibility(View.VISIBLE);
        }

        mValidarBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mIsNormal.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(buttonView.isChecked()){
                        Precio = 0.8f;
                        Capa = 79.99f;
                        PrecioFinal += Capa;
                        PPRC[Contador_1] = "Masa chica";
                        PPRP[Contador_1] = String.valueOf(Capa);
                        Contador_1 += 1;


                    }
                });

                mIsSmall.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(buttonView.isChecked()){
                        Precio = 1f;
                        Capa = 99.99f;
                        PrecioFinal += Capa;
                        PPRC[Contador_1] = "Masa normal";
                        PPRP[Contador_1] = String.valueOf(Capa);
                        Contador_1 += 1;
                    }
                });

                mIsLarge.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(buttonView.isChecked()){
                        Precio = 1.2f;
                        Capa = 129.99f;
                        PrecioFinal += Capa;
                        PPRC[Contador_1] = "Masa grande";
                        PPRP[Contador_1] = String.valueOf(Capa);
                        Contador_1 += 1;
                    }
                });

                mSuperficie_1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(buttonView.isChecked()){
                        float Superficie_1 = 29.99f;
                        PrecioFinal += Superficie_1;
                        PPRC[Contador_1] = String.valueOf(R.string.PP_10_1);
                        PPRP[Contador_1] = String.valueOf(Superficie_1);
                        Contador_1 += 1;
                    }
                });

                mSuperficie_2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(buttonView.isChecked()){
                        float Superficie_2 = 19.99f;
                        PrecioFinal += Superficie_2;
                        PPRC[Contador_1] = String.valueOf(R.string.PP_10_2);
                        PPRP[Contador_1] = String.valueOf(Superficie_2);
                        Contador_1 += 1;
                    }
                });

                mSuperficie_3.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(buttonView.isChecked()){
                        float Superficie_3 = 39.99f;
                        PrecioFinal += Superficie_3;
                        PPRC[Contador_1] = String.valueOf(R.string.PP_10_3);
                        PPRP[Contador_1] = String.valueOf(Superficie_3);
                        Contador_1 += 1;
                    }
                });

                mSuperficie_4.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(buttonView.isChecked()){
                        float Superficie_4 = 35.99f;
                        PrecioFinal += Superficie_4;
                        PPRC[Contador_1] = String.valueOf(R.string.PP_10_4);
                        PPRP[Contador_1] = String.valueOf(Superficie_4);
                        Contador_1 += 1;
                    }
                });

                mSuperficie_6.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(buttonView.isChecked()){
                        float Superficie_6 = 24.99f;
                        PrecioFinal += Superficie_6;
                        PPRC[Contador_1] = String.valueOf(R.string.PP_10_6);
                        PPRP[Contador_1] = String.valueOf(Superficie_6);
                        Contador_1 += 1;
                    }
                });

                String Spinner_0 = spinners.getSelectedItem().toString().trim();
                String Spinner_1 = spinners_1.getSelectedItem().toString().trim();
                String Spinner_2 = spinners_2.getSelectedItem().toString().trim();
                EditText Texto_E = findViewById(R.id.Texto);
                String mTexto_E = Texto_E.getText().toString().trim();

                if(Spinner_0.equals("2")){

                    if(Spinner_1.equals("Fresa 39.99 $")){
                        float Relleno_1 = 39.99f;
                        PrecioFinal += Relleno_1;
                        PPRC[Contador_1] = "Fresa";
                        PPRP[Contador_1] = String.valueOf(Relleno_1);
                        Contador_1 += 1;
                    }
                    if(Spinner_1.equals("Piña 29.99 $")){
                        float Relleno_2 = 29.99f;
                        PrecioFinal += Relleno_2;
                        PPRC[Contador_1] = "Piña";
                        PPRP[Contador_1] = String.valueOf(Relleno_2);
                        Contador_1 += 1;
                    }
                    if(Spinner_1.equals("Chocolate 39.99 $")){
                        float Relleno_3 = 39.99f;
                        PrecioFinal += Relleno_3;
                        PPRC[Contador_1] = "Chocolate";
                        PPRP[Contador_1] = String.valueOf(Relleno_3);
                        Contador_1 += 1;
                    }
                    if(Spinner_1.equals("Glaseado 19.99 $")){
                        float Relleno_4 = 19.99f;
                        PrecioFinal += Relleno_4;
                        PPRC[Contador_1] = "Glaseado";
                        PPRP[Contador_1] = String.valueOf(Relleno_4);
                        Contador_1 += 1;

                    }
                    if(Spinner_1.equals("Cajeta 39.99 $")){
                        float Relleno_6 = 39.99f;
                        PrecioFinal += Relleno_6;
                        PPRC[Contador_1] = "Cajeta";
                        PPRP[Contador_1] = String.valueOf(Relleno_6);
                        Contador_1 += 1;
                    }

                }

                if(Spinner_0.equals("3")){
                    if(Spinner_1.equals("Fresa 39.99 $")){
                        float Relleno_1 = 39.99f;
                        PrecioFinal += Relleno_1;
                        PPRC[Contador_1] = "Fresa";
                        PPRP[Contador_1] = String.valueOf(Relleno_1);
                        Contador_1 += 1;
                    }
                    if(Spinner_1.equals("Piña 29.99 $")){
                        float Relleno_2 = 29.99f;
                        PrecioFinal += Relleno_2;
                        PPRC[Contador_1] = "Piña";
                        PPRP[Contador_1] = String.valueOf(Relleno_2);
                        Contador_1 += 1;
                    }
                    if(Spinner_1.equals("Chocolate 39.99 $")){
                        float Relleno_3 = 39.99f;
                        PrecioFinal += Relleno_3;
                        PPRC[Contador_1] = "Chocolate";
                        PPRP[Contador_1] = String.valueOf(Relleno_3);
                        Contador_1 += 1;
                    }
                    if(Spinner_1.equals("Glaseado 19.99 $")){
                        float Relleno_4 = 19.99f;
                        PrecioFinal += Relleno_4;
                        PPRC[Contador_1] = "Glaseado";
                        PPRP[Contador_1] = String.valueOf(Relleno_4);
                        Contador_1 += 1;

                    }
                    if(Spinner_1.equals("Cajeta 39.99 $")){
                        float Relleno_6 = 39.99f;
                        PrecioFinal += Relleno_6;
                        PPRC[Contador_1] = "Cajeta";
                        PPRP[Contador_1] = String.valueOf(Relleno_6);
                        Contador_1 += 1;
                    }

                    if(Spinner_2.equals("Fresa 39.99 $")){
                        float Relleno_7 = 39.99f;
                        PrecioFinal += Relleno_7;
                        PPRC[Contador_1] = "Fresa";
                        PPRP[Contador_1] = String.valueOf(Relleno_7);
                        Contador_1 += 1;
                    }
                    if(Spinner_2.equals("Piña 29.99 $")){
                        float Relleno_8 = 29.99f;
                        PrecioFinal += Relleno_8;
                        PPRC[Contador_1] = "Piña";
                        PPRP[Contador_1] = String.valueOf(Relleno_8);
                        Contador_1 += 1;
                    }
                    if(Spinner_2.equals("Chocolate 39.99 $")){
                        float Relleno_9 = 39.99f;
                        PrecioFinal += Relleno_9;
                        PPRC[Contador_1] = "Chocolate";
                        PPRP[Contador_1] = String.valueOf(Relleno_9);
                        Contador_1 += 1;
                    }
                    if(Spinner_2.equals("Glaseado 19.99 $")){
                        float Relleno_10 = 19.99f;
                        PrecioFinal += Relleno_10;
                        PPRC[Contador_1] = "Glaseado";
                        PPRP[Contador_1] = String.valueOf(Relleno_10);
                        Contador_1 += 1;
                    }
                    if(Spinner_2.equals("Cajeta 39.99 $")){
                        float Relleno_12 = 39.99f;
                        PrecioFinal += Relleno_12;
                        PPRC[Contador_1] = "Cajeta";
                        PPRP[Contador_1] = String.valueOf(Relleno_12);
                        Contador_1 += 1;
                    }
                }

                if(!(mTexto_E.equals(""))){
                    float Texto = 19.99f;
                    PrecioFinal += Texto;
                    PPRC[Contador_1] = "Texto extra.";
                    PPRP[Contador_1] = String.valueOf(Texto);
                    Contador_1 += 1;
                }

                tableDynamic.addData(getClients());
            }
        });

        mProcederBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int i;

                Map<String, Object> pedidos = new HashMap<>();
                for(i=0; i<Contador_1;i++){
                    pedidos.put(PPRC[i], PPRP[i]);
                }
                pedidos.put("Precio total", PrecioFinal);

                fStore.collection("pedidos")
                        .add(pedidos)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "Ingrediente agregado con el ID" + documentReference.getId());
                                startActivity(new Intent(getApplicationContext(),Pe_admin_inicio.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG,"Error");
                    }
                });

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private ArrayList<String[]> getClients() {
        int i;
        for(i=0; i<Contador_1;i++){
            rows.add(new String[]{PPRC[i],PPRP[i]});
        }
        rows.add(new String[]{"Precio total", String.valueOf(PrecioFinal)});
        return rows;
    }
}