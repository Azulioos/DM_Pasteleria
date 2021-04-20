package com.example.dmpasteleria;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Pe_admin_gestionari extends AppCompatActivity {
    FirebaseFirestore fStore;
    TextView mCantidad, mNombreP;
    ImageView mImagenP;
    private static final String TAG = "Inventario";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_admin_gestionari);

        mImagenP = findViewById(R.id.Inventario_2);
        mNombreP = findViewById(R.id.Inventario_1);
        mCantidad = findViewById(R.id.Inventario_3);
        fStore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = fStore.collection("inventario").document();
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                mNombreP.setText(value.getString("Producto"));
                mCantidad.setText(value.getString("Cantidad"));
                mImagenP.setImageURI((Uri) value.getData(DocumentSnapshot.ServerTimestampBehavior.valueOf("Imagen")));
            }
        });
    }
}