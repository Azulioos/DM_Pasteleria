package com.example.dmpasteleria;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;

public class Pe_admin_adapter extends FirestorePagingAdapter<Pe_admin_datos ,Pe_admin_adapter.ProductsViewHolder> {
    private OnListItemClick onListItemClick;
    private static final String TAG = "Pedidos";

    public Pe_admin_adapter(@NonNull FirestorePagingOptions<Pe_admin_datos> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;

    }

    @Override
    protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull Pe_admin_datos model) {
        holder.tvProducto.setText(model.getProducto());
        holder.tvProovedor.setText(model.getProveedor());
        holder.tvTipo.setText(model.getTipo());
        holder.tvCantidad.setText(model.getCantidad() + "");
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pe_admin_objetos, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state){
        super.onLoadingStateChanged(state);
        switch (state){
            case LOADING_INITIAL:
                Log.d("Cargando","Cargando");
                break;
            case LOADING_MORE:
                Log.d("Cargando","Cargando");
                break;
            case FINISHED:
                Log.d("Cargando","Cargando");
                break;
            case ERROR:
                Log.d("Cargando","Cargando");
                break;
            case LOADED:
                Log.d("Cargando","Cargando");
                break;

        }

    }



    public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvProducto;
        private  TextView tvProovedor;
        private TextView tvTipo;
        private TextView tvCantidad;
        private Button tvEmpleado;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProducto = itemView.findViewById(R.id.tvProducto);
            tvProovedor = itemView.findViewById(R.id.tvProovedor);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvEmpleado = itemView.findViewById(R.id.tvEmpleado);

            tvEmpleado.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()), getAdapterPosition());
        }
    }

    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

}

