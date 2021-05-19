package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pe_user_settings extends AppCompatActivity {
    private static final String TAG = "";
    TextView UserFiled, UserMail, UserDate;
    FirebaseAuth fAuth;
    String userID;
    FirebaseUser Usuario;
    FirebaseFirestore fStore;
    Button buttonChangeData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_account_settings);  //Hace un set del contenido que se mostrará

        //Instancias de los TextView que mostrarán los datos
        UserFiled = findViewById(R.id.showUsername);        //Username field
        UserMail = findViewById(R.id.showUserMail);    //User email field
        UserDate = findViewById(R.id.showDate);         //User date birth field

        //Instancias de Firebase

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Usuario = FirebaseAuth.getInstance().getCurrentUser();
        userID = Usuario.getUid();

        //Instancia del botón para cambiar los datos
        buttonChangeData = findViewById(R.id.buttonEditData);

        /*Hacer el query a la Cloud Firestore para obtener los datos alojados, utilizando el ID
        * del usuario correspondiente*/
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

        buttonChangeData.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Pe_change_settings.class));
            finish();
        });

    }



    //Esta función permite regresar a la pantalla anterior, es llamada desde un android:onClick
    public void Regresar(View view) {
        startActivity(new Intent((getApplicationContext()), Pe_usuario_inicio.class));
        finish();
    }
}
