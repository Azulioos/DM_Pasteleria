package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pe_iniciodesesion extends AppCompatActivity {
    private static final String TAG = "Login";
    EditText mEmail, mPassword;
    Button mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CheckBox mIsAdmin, mIsUser, mIsWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_iniciodesesion);
        fAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.passwords);
        mLoginBtn = findViewById(R.id.boton_1);
        mIsUser = findViewById(R.id.checkbox);
        mIsWorker = findViewById(R.id.checkbox2);
        mIsAdmin = findViewById(R.id.checkbox3);

        //Hace un set para cada posible resultado en los checkbox
        mIsAdmin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                mIsUser.setChecked(false);
                mIsWorker.setChecked(false);
            }
        });

        mIsUser.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                mIsAdmin.setChecked(false);
                mIsWorker.setChecked(false);
            }
        });

        mIsWorker.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                mIsAdmin.setChecked(false);
                mIsUser.setChecked(false);
            }
        });

        //Set on click listener para el botón de LOGIN
        mLoginBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();

            //Revisa si hay campos en blanco
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Ingresa tu Email.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Ingresa tu contraseña, por favor.");
                return;
            }

            if (!(mIsWorker.isChecked() || mIsUser.isChecked() || mIsAdmin.isChecked())) {
                Toast.makeText(Pe_iniciodesesion.this, "Seleciona un tipo de cuenta", Toast.LENGTH_SHORT).show();
                return;
            }

            fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                Pe_iniciodesesion.this.openPrincipal(authResult.getUser().getUid());
                Pe_iniciodesesion.this.finish();
            }).addOnFailureListener(e -> Toast.makeText(Pe_iniciodesesion.this, "Error al iniciar sesion.", Toast.LENGTH_SHORT).show());
        });
    }

    //Función que abre la pantalla principal dependiendo el rol
    public void openPrincipal(String Uid){
        String good = "1";                   //String para comparar el estado de los roles
        DocumentReference df = fStore.collection("users").document(Uid);
        df.get().addOnSuccessListener(documentSnapshot -> {
            Log.d("TAG","OnSuccess" + documentSnapshot.getData());

            //Valora para cada posible resultado de los checkbox
            if (documentSnapshot.getString("Role") != null && mIsUser.isChecked()){
                Toast.makeText(Pe_iniciodesesion.this,"Abriendo pantalla de usuario", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Pe_iniciodesesion.this, Pe_usuario_inicio.class));
                finish();
            } else{
                if(documentSnapshot.getString("RoleAdmin") != null && mIsAdmin.isChecked()){
                    Toast.makeText(Pe_iniciodesesion.this,"Abriendo pantalla de administrador", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Pe_iniciodesesion.this, Pe_admin_inicio.class));
                    finish();
                } else{
                    if(documentSnapshot.getString("RoleWorker") != null && mIsWorker.isChecked()){
                        Toast.makeText(Pe_iniciodesesion.this,"Abriendo pantalla de empleado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Pe_iniciodesesion.this, Pe_empleado_inicio.class));
                        finish();
                    } else{
                        if(!(mIsWorker.isChecked() || mIsUser.isChecked() || mIsAdmin.isChecked())){
                            Toast.makeText(Pe_iniciodesesion.this, "Error de rol", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    //Función de regresar al registro
    public void ir_a_registro(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_registro.class));
        finish();
    }

    //Función para regresar
    public void regresar(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_inicio.class));
        finish();
    }
}