package com.example.dmpasteleria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class Pe_usuario_pedidos extends AppCompatActivity {
    private FirebaseFirestore fstore;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;

    Pe_usuario_pedidos_adapter myAdapter;
    ArrayList<Pe_usuario_pedidos_datos> list;
    private static final String TAG = "Pedidos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_usuario_pedidos);

        fstore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.userList);

        Query query = fstore.collection("pedidos");
        FirestoreRecyclerOptions<Pe_usuario_pedidos_datos> options = new FirestoreRecyclerOptions.Builder<Pe_usuario_pedidos_datos>()
                .setQuery(query, Pe_usuario_pedidos_datos.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<Pe_usuario_pedidos_datos, ProductViewHolder>(options) {
            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pe_usuario_pedidos_objetos, parent, false);
                return new ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Pe_usuario_pedidos_datos model) {
                holder.list_tamano.setText(model.getTamano());
                holder.list_pan.setText(model.getPan());
                holder.list_condimentos.setText(model.getCondimentos());
                holder.list_colores.setText(model.getColores());
                holder.list_extra.setText(model.getExtra());
            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView list_tamano;
        private  TextView list_pan;
        private TextView list_condimentos;
        private  TextView list_colores;
        private TextView list_extra;

        public ProductViewHolder(@NonNull View itemView){
            super(itemView);
            list_tamano = itemView.findViewById(R.id.tvTamano);
            list_pan = itemView.findViewById(R.id.tvPan);
            list_condimentos = itemView.findViewById(R.id.tvCondimentos);
            list_colores = itemView.findViewById(R.id.tvColores);
            list_extra = itemView.findViewById(R.id.tvExtra);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}