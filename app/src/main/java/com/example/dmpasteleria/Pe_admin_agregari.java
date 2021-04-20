package com.example.dmpasteleria;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Pe_admin_agregari extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText mNombreProducto, mProveedor;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageRef;

    private ImageView mImagen;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    public Uri imageURL;

    private static final String TAG = "Inventario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_admin_agregari);

        mNombreProducto = findViewById(R.id.user);
        mProveedor = findViewById(R.id.cnacimiento);
        mImagen = findViewById(R.id.passwords);
        mRegisterBtn = findViewById(R.id.verificar_inicio);

        mImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Spinner spinners = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.tipo, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinners.setAdapter(adapter1);
        spinners.setOnItemSelectedListener(this);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String randomKey = UUID.randomUUID().toString();
                StorageReference riverRef = storageReference.child("images/" + randomKey);
                riverRef.putFile(imageURL)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                String Producto = mNombreProducto.getText().toString().trim();
                String Proveedor = mProveedor.getText().toString().trim();
                String Spinner_0 = spinners.getSelectedItem().toString().trim();
                String mUpload = riverRef.getDownloadUrl().toString();


                if (TextUtils.isEmpty(Producto)) {
                    mNombreProducto.setError("Ingresa el nombre del producto.");
                    return;
                }
                if (TextUtils.isEmpty(Proveedor)) {
                    mProveedor.setError("Ingresa el nombre del proveedor");
                    return;
                }

                Map<String, Object> inventario = new HashMap<>();
                inventario.put("Producto", Producto);
                inventario.put("Proveedor", Proveedor);
                inventario.put("Tipo", Spinner_0);
                inventario.put("Cantidad", 0);
                inventario.put("imagen", mUpload);


                fStore.collection("inventario")
                        .add(inventario)
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

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode== RESULT_OK && data!=null && data.getData()!=null){
            imageURL = data.getData();
            mImagen.setImageURI(imageURL);
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

    public void regresar(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_admin_inicio.class));
        finish();
    }
}
