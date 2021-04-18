package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pe_admin_agregari extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText mNombreProducto, mProveedor;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    private static final String TAG = "Inventario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_admin_agregari);

        mNombreProducto = findViewById(R.id.user);
        mProveedor = findViewById(R.id.cnacimiento);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Spinner spinners = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.tipo, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinners.setAdapter(adapter1);
        spinners.setOnItemSelectedListener(this);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Producto = mNombreProducto.getText().toString().trim();
                String Proveedor = mProveedor.getText().toString().trim();
                String Spinner_0 = spinners.getSelectedItem().toString().trim();


                if (TextUtils.isEmpty(Producto)) {
                    mNombreProducto.setError("Ingresa el nombre del producto.");
                    return;
                }
                if (TextUtils.isEmpty(Proveedor)) {
                    mProveedor.setError("Ingresa el nombre del proveedor");
                    return;
                }

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

    public void regresar(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_admin_inicio.class));
        finish();
    }
}
