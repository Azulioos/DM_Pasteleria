package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Pe_user_settings extends AppCompatActivity {
    private static final String TAG = "";
    TextView UserFiled, UserMail, UserDate;
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_account_settings);

        //Instance textviewsname
        UserFiled = findViewById(R.id.showUsername);        //Username field
        UserMail = findViewById(R.id.showUserMail);    //User email field
        UserDate = findViewById(R.id.showDate);         //User date birth field

        //Firebase instances
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Hacer el query a la Cloud Firestore
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    UserFiled.setText((CharSequence) document.get("Username"));
                    UserMail.setText((CharSequence) document.get("Email"));
                    UserDate.setText((CharSequence) document.get("Nacimiento"));
                } else{
                    Log.d(TAG, "No such document");
                }
            } else{
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

    }

    public void Regresar(View view) {
        startActivity(new Intent((getApplicationContext()), Pe_usuario_inicio.class));
        finish();
    }
}
