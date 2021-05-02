package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pe_usuario_pasteles_personalizados_mapa extends AppCompatActivity {
    Button btPicker, btEnviar;
    TextView textView;
    int PLACE_PICKER_REQUEST = 1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String place_2 = null;

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
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pedidos");
                FirebaseUser Usuario = fAuth.getCurrentUser();
                String Usuario_2 = null;
                Usuario_2.equals(Usuario);

                reference.orderByChild("Usuario").equalTo(Usuario_2).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot datas : snapshot.getChildren()) {
                            if (reference.orderByChild("Estado del pedido").equals("0")){
                                fStore.collection("pedidos")
                                        .add(place_2);
                                startActivity(new Intent(getApplicationContext(),Pe_usuario_pedidos.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
                place_2.equals(place);

                textView.setText(place_2);
            }
        }
    }
}