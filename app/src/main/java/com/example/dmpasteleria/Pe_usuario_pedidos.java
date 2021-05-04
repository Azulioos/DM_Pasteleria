package com.example.dmpasteleria;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Pe_usuario_pedidos extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore fstore;
    Pe_usuario_pedidos_adapter myAdapter;
    ArrayList<Pe_usuario_pedidos_datos> list;
    private static final String TAG = "Pedidos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_usuario_pedidos);

        recyclerView = findViewById(R.id.userList);
        DocumentReference documentReference = fstore.collection("pedidos").document();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new Pe_usuario_pedidos_adapter(this,list);
        recyclerView.setAdapter(myAdapter);


        fstore.collection("Pedidos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                }
                for(DocumentChange doc: value.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED) {
                        Pe_usuario_pedidos_datos user = doc.getDocument().toObject(Pe_usuario_pedidos_datos.class);
                        list.add(user);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}