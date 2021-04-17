package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

        mIsAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    mIsUser.setChecked(false);
                    mIsWorker.setChecked(false);
                }
            }
        });

        mIsUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    mIsAdmin.setChecked(false);
                    mIsWorker.setChecked(false);
                }
            }
        });

        mIsWorker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    mIsAdmin.setChecked(false);
                    mIsUser.setChecked(false);
                }
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                fAuth = FirebaseAuth.getInstance();
                fStore = FirebaseFirestore.getInstance();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Ingresa tu Email.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Ingresa tu contraseña, por favor.");
                    return;
                }

                if(!(mIsWorker.isChecked() || mIsUser.isChecked() || mIsAdmin.isChecked())){
                    Toast.makeText(Pe_iniciodesesion.this, "Seleciona un tipo de cuenta", Toast.LENGTH_SHORT).show();
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        openPrincipal(authResult.getUser().getUid());
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Pe_iniciodesesion.this, "Error al iniciar sesion.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void openPrincipal(String Uid){
        DocumentReference df = fStore.collection("users").document(Uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","OnSuccess" + documentSnapshot.getData());

                if(documentSnapshot.getString("Role") != null && mIsUser.isChecked()){
                    Intent intent = new Intent(Pe_iniciodesesion.this, Pe_usuario_inicio.class);
                    startActivity(intent);
                    Toast.makeText(Pe_iniciodesesion.this, "Iniciando pantalla de usuario", Toast.LENGTH_SHORT).show();
                    finish();
                }

                if(documentSnapshot.getString("RoleAdmin") != null && mIsAdmin.isChecked()){
                    Intent intent = new Intent(Pe_iniciodesesion.this, Pe_admin_inicio.class);
                    Toast.makeText(Pe_iniciodesesion.this, "Iniciando pantalla de administrador", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }

                if(documentSnapshot.getString("RoleWorker") != null && mIsWorker.isChecked()){
                    Intent intent = new Intent(Pe_iniciodesesion.this, Pe_empleado_inicio.class);
                    Toast.makeText(Pe_iniciodesesion.this, "Iniciando pantalla de empleado", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }

                if(!(mIsWorker.isChecked() || mIsUser.isChecked() || mIsAdmin.isChecked())){
                    Toast.makeText(Pe_iniciodesesion.this, "Error de rol", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Pe_iniciodesesion.this, Pe_registro.class);
                    startActivity(intent);


                }
            }
        });
    }

    public void ir_a_registro(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_registro.class));
        finish();
    }

    public void regresar(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_inicio.class));
        finish();
    }
}