package com.example.dmpasteleria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Pe_empleado_inicio extends AppCompatActivity implements Pe_empleado_adapter.OnListItemClick {
    private FirebaseFirestore fstore;
    private RecyclerView recyclerView;
    FirebaseUser Usuario;
    String Usuario_2;
    FirebaseAuth fAuth;
    private Pe_empleado_adapter adapter;

    private static final String TAG = "Pedidos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_empleado_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar_i);
        setSupportActionBar(toolbar);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.userList);
        Usuario = FirebaseAuth.getInstance().getCurrentUser();
        Usuario_2 = Usuario.getUid();

        Query query = fstore.collection("pedidos").
                whereEqualTo("Usuario", Usuario_2);

        /*PagingConfig config_2 = new PagingConfig();*/
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();

        FirestorePagingOptions<Pe_empleado_pedidos_datos> options = new FirestorePagingOptions.Builder<Pe_empleado_pedidos_datos>()
                .setLifecycleOwner(this)
                .setQuery(query, config, Pe_empleado_pedidos_datos.class)
                .build();


        adapter = new Pe_empleado_adapter(options, this);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

    public void showChangeLanguageDialog(MenuItem menu) {
        final String[] listItems = {"English", "Español", "Portugues"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Pe_empleado_inicio.this);
        mBuilder.setTitle("Escoge un idioma");
        mBuilder.setSingleChoiceItems(listItems, -1, (dialogInterface, i) -> {
            if (i == 0) {
                setLocale("en");
                recreate();
            } else if (i == 1) {
                setLocale("es");
                recreate();
            } else if (i == 2) {
                setLocale(("pt"));
                recreate();
            }
            dialogInterface.dismiss();
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Log.d("ORDEN","Orden numero: "+ snapshot.getId());
        fstore.collection("pedidos")
                .whereEqualTo(FieldPath.documentId(), snapshot.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                int Estado = document.getLong("Estado_del_pedido").intValue();
                                final DocumentReference docref = FirebaseFirestore.getInstance()
                                        .collection("pedidos")
                                        .document(document.getId());
                                Estado += 1;
                                System.out.println("El objeto existe");
                                Map<String, Object> map_1 = new HashMap<>();
                                map_1.put("Estado_del_pedido", Estado);
                                docref.update(map_1)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                startActivity(new Intent(getApplicationContext(), Pe_empleado_inicio.class));
                                                System.out.println("Vamonos");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println("Algo anda mal.");

                                    }
                                });

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
