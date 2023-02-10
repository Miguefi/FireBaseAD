package com.example.firebasead.RecyclerArchivos;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasead.DescripcionArchivo;
import com.example.firebasead.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorListado extends RecyclerView.Adapter<AdaptadorListado.ViewHolder> {

    private List<Archivos> listaArchivos;
    ArrayList<Archivos> arrayListaArchivos;

    public AdaptadorListado(List<Archivos> listaArchivos) {
        this.listaArchivos = listaArchivos;
        arrayListaArchivos = new ArrayList<>();
        arrayListaArchivos.addAll(listaArchivos);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_archivo, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Archivos archivos = listaArchivos.get(position);
        holder.nombre.setText(archivos.getNombre());
        holder.id.setText(archivos.getId());
        holder.propietario.setText(archivos.getPropietario());
    }

    @Override
    public int getItemCount() {
        return listaArchivos.size();
    }


    public interface ItemClickListener {
        void onClick(View view, Archivos listaArchivos);
    }

    private ItemClickListener clickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nombre, id, propietario;
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            nombre = (TextView) v.findViewById(R.id.idNombre);
            id = (TextView) v.findViewById(R.id.idArchivo);
            propietario = (TextView) v.findViewById(R.id.idPropietario);

            v.setOnClickListener(this);
        }

        public void onClick(View view) {
            // Si tengo un manejador de evento lo propago con el índice
            if (clickListener != null) clickListener.onClick(view, listaArchivos.get(getAdapterPosition()));

            Archivos archivo = listaArchivos.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), DescripcionArchivo.class);
            intent.putExtra("archivo", archivo);
            view.getContext().startActivity(intent);
        }

    }

}
