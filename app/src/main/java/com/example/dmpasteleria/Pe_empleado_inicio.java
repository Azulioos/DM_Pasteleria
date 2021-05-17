package com.example.dmpasteleria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
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

import java.util.Locale;

public class Pe_empleado_inicio extends AppCompatActivity implements Pe_empleado_adapter.OnListItemClick {
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
        FirestoreRecyclerOptions<Pe_empleado_pedidos_datos> options = new FirestoreRecyclerOptions.Builder<Pe_empleado_pedidos_datos>()
                .setQuery(query, Pe_empleado_pedidos_datos.class)
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
    public void onItemClick(Pe_empleado_pedidos_datos snapshot, int position) {

    }
}
