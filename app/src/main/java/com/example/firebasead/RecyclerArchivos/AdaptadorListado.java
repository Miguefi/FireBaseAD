package com.example.firebasead.RecyclerArchivos;

import android.app.Activity;
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
import java.util.stream.Collectors;

public class AdaptadorListado extends RecyclerView.Adapter<AdaptadorListado.ViewHolder> {
    private List<Archivos> listaArchivos;
    private ArrayList<Archivos> arrayListaArchivos;

    public AdaptadorListado(List<Archivos> listaArchivos) {
        this.listaArchivos = listaArchivos;
        arrayListaArchivos = new ArrayList<>();
        arrayListaArchivos.addAll(listaArchivos);
    }
    public void filtrado(final String nombreArchivo) {
        int longitud = nombreArchivo.length();
        if (longitud == 0) {
            listaArchivos.clear();
            listaArchivos.addAll(arrayListaArchivos);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Archivos> collecion = listaArchivos.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(nombreArchivo.toLowerCase()))
                        .collect(Collectors.toList());
                listaArchivos.clear();
                listaArchivos.addAll(collecion);
            } else {
                for (Archivos c : arrayListaArchivos) {
                    if (c.getNombre().toLowerCase().contains(nombreArchivo.toLowerCase())) {
                        listaArchivos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.caja_archivos, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Archivos archivos = listaArchivos.get(position);
        holder.nombre.setText(archivos.getNombre());
        holder.fecha.setText(archivos.getFechaCreacion());
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
        //private final TextView nombre, id, propietario;
        private final TextView nombre, fecha;
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            nombre = (TextView) v.findViewById(R.id.idNombreArchivo1);
            fecha = (TextView) v.findViewById(R.id.fechaMod);
        }

        public void onClick(View view) {
            // Si tengo un manejador de evento lo propago con el índice
            if (clickListener != null) clickListener.onClick(view, listaArchivos.get(getAdapterPosition()));

            Archivos archivo = listaArchivos.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), DescripcionArchivo.class);
            intent.putExtra("archivo", archivo);
            view.getContext().startActivity(intent);
            ((Activity) view.getContext()).finish();
        }

    }

}
