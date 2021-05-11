package com.example.dmpasteleria;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Pe_usuario_pedidos extends AppCompatActivity {
    private FirebaseFirestore fstore;
    private RecyclerView recyclerView;
    FirebaseUser Usuario;
    String Usuario_2;
    FirebaseAuth fAuth;

    FirestoreRecyclerAdapter adapter;

    private static final String TAG = "Pedidos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_usuario_pedidos);
        Toolbar toolbar = findViewById(R.id.toolbar_i);
        setSupportActionBar(toolbar);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.userList);
        Usuario = FirebaseAuth.getInstance().getCurrentUser();
        Usuario_2 = Usuario.getUid();

        Query query = fstore.collection("pedidos").
                whereEqualTo("Usuario", Usuario_2);
        FirestoreRecyclerOptions<Pe_usuario_pedidos_datos> options = new FirestoreRecyclerOptions.Builder<Pe_usuario_pedidos_datos>()
                .setQuery(query, Pe_usuario_pedidos_datos.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<Pe_usuario_pedidos_datos, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pe_usuario_pedidos_objetos, parent, false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull Pe_usuario_pedidos_datos model) {
                holder.tvTamano.setText(model.getTamano());
                holder.tvPan.setText(model.getPan());
                holder.tvCondimentos.setText(model.getCondimentos());
                holder.tvColores.setText(model.getColores());
                holder.tvExtra.setText(model.getExtra());
            }
        };
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTamano;
        private  TextView tvPan;
        private TextView tvCondimentos;
        private  TextView tvColores;
        private TextView tvExtra;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTamano = itemView.findViewById(R.id.tvTamano);
            tvPan = itemView.findViewById(R.id.tvPan);
            tvCondimentos = itemView.findViewById(R.id.tvCondimentos);
            tvColores = itemView.findViewById(R.id.tvColores);
            tvExtra = itemView.findViewById(R.id.tvExtra);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;

    }

    public void signOut(MenuItem item) {
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        startActivity(new Intent(getApplicationContext(), Pe_inicio.class));
        finish();
    }


    public void openUserAccount(MenuItem item) {
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        startActivity(new Intent(getApplicationContext(), Pe_user_settings.class));
        finish();
    }

    public void Personalized(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_usuario_pasteles_personalizados.class));
        finish();
    }
}