package com.example.dmpasteleria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Pe_usuario_pedidos_adapter extends RecyclerView.Adapter<Pe_usuario_pedidos_adapter.MyViewHolder>{
    Context context;
    ArrayList<Pe_usuario_pedidos_datos> list;


    public Pe_usuario_pedidos_adapter(Context context, ArrayList<Pe_usuario_pedidos_datos> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.pe_usuario_pedidos_objetos , parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pe_usuario_pedidos_datos user = list.get(position);
        holder.tamano.setText(user.getTamano());
        holder.pan.setText(user.getPan());
        holder.condimentos.setText(user.getCondimentos());
        holder.colores.setText(user.getColores());
        holder.extra.setText(user.getExtra());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tamano, pan, condimentos, colores, extra;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tamano = itemView.findViewById(R.id.tvTamano);
            pan = itemView.findViewById(R.id.tvPan);
            condimentos = itemView.findViewById(R.id.tvCondimentos);
            colores = itemView.findViewById(R.id.tvColores);
            extra = itemView.findViewById(R.id.tvExtra);
        }
    }
}
