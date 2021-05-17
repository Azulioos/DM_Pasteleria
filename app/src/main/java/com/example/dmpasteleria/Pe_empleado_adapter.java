package com.example.dmpasteleria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Pe_empleado_adapter extends FirestoreRecyclerAdapter<Pe_empleado_pedidos_datos ,Pe_empleado_adapter.ProductsViewHolder> {
    private OnListItemClick onListItemClick;

    public Pe_empleado_adapter(@NonNull FirestoreRecyclerOptions<Pe_empleado_pedidos_datos> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull Pe_empleado_pedidos_datos model) {
        holder.tvTamano.setText(model.getTamano());
        holder.tvPan.setText(model.getPan());
        holder.tvCondimentos.setText(model.getCondimentos());
        holder.tvColores.setText(model.getColores());
        holder.tvExtra.setText(model.getExtra());
        holder.tvPrecio.setText(model.getPrecio_total() + "");
        holder.tvEstado.setText(model.getEstado_del_pedido());
        holder.tvDireccion.setText(model.getDireccion());
        holder.tvUsuarioCorreo.setText(model.getUsuarioCorreo());
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pe_usuario_pedidos_objetos, parent, false);
        return new ProductsViewHolder(view);
    }



    public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTamano;
        private  TextView tvPan;
        private TextView tvCondimentos;
        private  TextView tvColores;
        private TextView tvExtra;
        private TextView tvEstado;
        private TextView tvPrecio;
        private  TextView tvDireccion;
        private  TextView tvUsuarioCorreo;
        private Button tvEmpleado;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTamano = itemView.findViewById(R.id.tvTamano);
            tvPan = itemView.findViewById(R.id.tvPan);
            tvCondimentos = itemView.findViewById(R.id.tvCondimentos);
            tvColores = itemView.findViewById(R.id.tvColores);
            tvExtra = itemView.findViewById(R.id.tvExtra);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvUsuarioCorreo = itemView.findViewById(R.id.tvUsuarioCorreo);
            tvEmpleado = itemView.findViewById(R.id.tvEmpleado);
            tvEmpleado.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()), getAdapterPosition());
        }
    }

    public interface OnListItemClick{
        void onItemClick(Pe_empleado_pedidos_datos snapshot, int position);
    }
}
