package com.example.dmpasteleria;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;

public class Pe_empleado_mapa extends AppCompatActivity implements OnMapReadyCallback {
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    FirebaseUser Usuario;
    String Usuario_2;
    private static final String TAG = "Pedidos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Usuario = FirebaseAuth.getInstance().getCurrentUser();
        Usuario_2 = Usuario.getUid();
        System.out.println(Usuario_2);
        setContentView(R.layout.activity_pe_empleado_mapa);
        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if(location != null || !(location.equals(""))){
                    Geocoder geocoder = new Geocoder(Pe_empleado_mapa.this);
                    try{
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList == null  || addressList.size() == 0) {
                        Toast.makeText(Pe_empleado_mapa.this, "address_not_found", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    System.out.println(address);
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        List<Address> addressList_2 = null;
        Geocoder geocoder_2 = new Geocoder(Pe_empleado_mapa.this);
        try {
            addressList_2 = geocoder_2.getFromLocationName("Nuevo leon,San Nicolas, Ciudad universitaria", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address_2 = addressList_2.get(0);
        System.out.println(address_2);
        LatLng latLng_2 = new LatLng(address_2.getLatitude(), address_2.getLongitude());
        map.addMarker(new MarkerOptions().position(latLng_2).title("San Nicolas, Ciudad universitaria"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng_2, 10));

    }

    public void Enviar(View view) {
            startActivity(new Intent(getApplicationContext(), Pe_empleado_enviado.class));
    }

    public void Cancelar(View view) {
        startActivity(new Intent(getApplicationContext(), Pe_empleado_inicio.class));
    }
}