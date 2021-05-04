package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Pe_usuario_pasteles_personalizados_mapa extends AppCompatActivity {
    Button btPicker, btEnviar;
    TextView textView;
    int PLACE_PICKER_REQUEST = 1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String place_2 = null;
    private static final String TAG = "Pedidos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_usuario_pasteles_personalizados_mapa);

        btPicker = findViewById(R.id.bt_picker);
        textView = findViewById(R.id.text_view);
        btEnviar = findViewById(R.id.bt_enviar);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), Pe_inicio.class));
            finish();
        }

        btPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(Pe_usuario_pasteles_personalizados_mapa.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser Usuario = fAuth.getCurrentUser();
                String Usuario_2 = null;
                Usuario_2.equals(Usuario);
                fStore.collection("Pedidos").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange doc: value.getDocumentChanges()){
                            DocumentReference documentReference = fStore.collection("Pedidos").document(doc.toString());
                            documentReference.get().addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if(document.exists()){
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                        textView.setText((CharSequence) document.get("Direccion"));
                                    } else{
                                        Log.d(TAG, "No such document");
                                    }
                                } else{
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
                place_2 = place.toString();
                textView.setText(place_2);
            }
        }
    }
}