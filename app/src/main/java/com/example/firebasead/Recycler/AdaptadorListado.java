package com.example.firebasead.Recycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.firebasead.DetallesClienteActivity;
import com.example.firebasead.R;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorListado extends RecyclerView.Adapter<AdaptadorListado.ViewHolder> {

    private List<PerfilesClientes> perfilesList;
    private ArrayList<PerfilesClientes> perfilesList2;
    private final Context context;

    public AdaptadorListado(List<PerfilesClientes> perfilesList, Context context) {
        this.perfilesList = perfilesList;
        this.context = context;
        perfilesList2 = new ArrayList<>();
        perfilesList2.addAll(perfilesList);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.caja_perfiles, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,  int position) {
        PerfilesClientes perfilesClientes=perfilesList.get(position);
        holder.nombre_cliente.setText(perfilesClientes.getNombre());
        holder.apellido_cliente.setText(perfilesClientes.getApellido());

        final int pos=position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilesClientes clienteSeleccionado = perfilesList.get(pos);
                Intent intent = new Intent(context, DetallesClienteActivity.class);
                intent.putExtra("perfil", clienteSeleccionado);
                context.startActivity(intent);
                ((Activity) v.getContext()).finish();
            }
        });

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre_cliente;
        TextView apellido_cliente;
        ImageView imagen_cliente;


        ViewHolder(View itemView) {
            super(itemView);
            nombre_cliente = itemView.findViewById(R.id.nombre_cliente);
            apellido_cliente = itemView.findViewById(R.id.apellido_cliente);
            imagen_cliente = itemView.findViewById(R.id.imagen_cliente);


        }
    }

    @Override
    public int getItemCount() {
        return perfilesList.size();
    }


    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            perfilesList.clear();
            perfilesList.addAll(perfilesList2);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<PerfilesClientes> collecion = perfilesList.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                perfilesList.clear();
                perfilesList.addAll(collecion);
            } else {
                for (PerfilesClientes c : perfilesList2) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        perfilesList.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

}