package com.example.dmpasteleria;

import android.widget.SearchView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pe_admin_datos {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    private static final String TAG = "Pedidos";

    private String Producto, Proveedor, Tipo;
    private int Cantidad;
    private Pe_admin_datos(){

    }

    private Pe_admin_datos(String Producto,String Proveedor,String Tipo, int Cantidad){
        this.Producto = Producto;
        this.Proveedor = Proveedor;
        this.Tipo = Tipo;
        this.Cantidad = Cantidad;
    }

    public String getProducto(){
        return Producto;
    }

    public String getProveedor(){
        return Proveedor;
    }

    public String getTipo(){
        return Tipo;
    }

    public int getCantidad(){
        return Cantidad;
    }

    //Set

    public void setProducto(String Producto){
        this.Producto = Producto;
    }

    public void setProveedor(String Proveedor){
        this.Proveedor = Proveedor;
    }

    public void setTipo(String Tipo){
        this.Tipo = Tipo;
    }

    public void setCantidad(int Cantidad){
        this.Cantidad = Cantidad;
    }
}