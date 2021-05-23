package com.example.dmpasteleria;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

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

public class Pe_admin_gestionari extends AppCompatActivity implements Pe_admin_adapter.OnListItemClick{
    private FirebaseFirestore fstore;
    private RecyclerView recyclerView;
    private Pe_admin_adapter.OnListItemClick onListItemClick;
    int Valor;
    FirebaseUser Usuario;
    String Usuario_2;
    int Estado_2;
    FirebaseAuth fAuth;
    private Pe_admin_adapter adapter;
    String Estado_3;
    private static final String TAG = "Inventario";
    DocumentReference docref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe_admin_gestionari);
        Toolbar toolbar = findViewById(R.id.toolbar_i);
        setSupportActionBar(toolbar);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.userList);
        Usuario = FirebaseAuth.getInstance().getCurrentUser();
        Usuario_2 = Usuario.getUid();

        Query query = fstore.collection("inventario");

        /*PagingConfig config_2 = new PagingConfig();*/

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();

        FirestorePagingOptions<Pe_admin_datos> options = new FirestorePagingOptions.Builder<Pe_admin_datos>()
                .setLifecycleOwner(this)
                .setQuery(query, config, Pe_admin_datos.class)
                .build();


        adapter = new Pe_admin_adapter(options, this);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Log.d("ORDEN","Orden numero: "+ snapshot.getId());
        fstore.collection("inventario")
                .whereEqualTo(FieldPath.documentId(), snapshot.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                int Estado = document.getLong("Cantidad").intValue();
                                docref = FirebaseFirestore.getInstance()
                                        .collection("inventario")
                                        .document(document.getId());
                                Estado_2 = Estado;
                                Log.d("Cargando","Cargando");
                                numberPickerDialog();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void numberPickerDialog() {
        Log.d("Hola","Hola");
        NumberPicker myNumberPicker = new NumberPicker(this);
        myNumberPicker.setMaxValue(100);
        myNumberPicker.setMinValue(1);
        NumberPicker.OnValueChangeListener myValChangeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int Estado_2, int newVal) {
                Valor = newVal;
            }
        };
        myNumberPicker.setOnValueChangedListener(myValChangeListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(myNumberPicker);
        builder.setTitle(getResources().getString(R.string.Pedidos_18));
        builder.setMessage(getResources().getString(R.string.Pedidos_19));
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                numberPickerDialog();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("El objeto existe");
                Map<String, Object> map_1 = new HashMap<>();
                map_1.put("Cantidad", Valor);
                docref.update(map_1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(), Pe_admin_gestionari.class));
                                System.out.println("Vamonos");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Algo anda mal.");

                    }
                });
            }
        });
        builder.show();
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

    public void showChangeLanguageDialog(MenuItem menu) {
        final String[] listItems = {"English", "Español", "Portugues"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Pe_admin_gestionari.this);
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

    public void Payments(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), Pe_usuario_pedidos_paypal.class));
        finish();
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
        startActivity(new Intent(getApplicationContext(), Pe_user_settings.class));
        finish();
    }

    public void Personalized(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_usuario_pasteles_personalizados.class));
        finish();
    }

    public void Ayuda(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), Pe_usuario_pedidos.class));
        finish();
    }

    public void Pedidos(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), Pe_usuario_pedidos.class));
        finish();
    }
}