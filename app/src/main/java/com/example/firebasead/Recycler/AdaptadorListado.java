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
import com.example.firebasead.DetallesClienteActivity;
import com.example.firebasead.R;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorListado extends RecyclerView.Adapter<AdaptadorListado.ViewHolder> {

    private List<Cliente> perfilesList;
    private ArrayList<Cliente> perfilesList2;
    private final Context context;

    public AdaptadorListado(List<Cliente> perfilesList, Context context) {
        this.perfilesList = perfilesList;
        this.context = context;
        //almacena una copia de la lista en perfilesList2.
        perfilesList2 = new ArrayList<>(perfilesList);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.caja_perfiles, parent, false);
        return new ViewHolder(view);
    }

    //Establece los valores de los componentes de la vista con los datos de PerfilesClientes
    @Override
    public void onBindViewHolder(ViewHolder holder,  int position) {
        Cliente cliente =perfilesList.get(position);
        holder.nombre_cliente.setText(cliente.getNombre());
        holder.dni_cliente.setText(cliente.getDni_cliente());

        final int pos=position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente clienteSeleccionado = perfilesList.get(pos);
                Intent intent = new Intent(context, DetallesClienteActivity.class);
                intent.putExtra("perfil", clienteSeleccionado);
                context.startActivity(intent);
                ((Activity) v.getContext()).finish();
            }
        });

    }

    // clase de contenedor que contiene referencias a los componentes de la vista.
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre_cliente;
        TextView dni_cliente;
        ImageView imagen_cliente;


        ViewHolder(View itemView) {
            super(itemView);
            nombre_cliente = itemView.findViewById(R.id.nombre_cliente);
            dni_cliente = itemView.findViewById(R.id.dni_cliente);
            imagen_cliente = itemView.findViewById(R.id.imagen_cliente);


        }
    }

    @Override
    public int getItemCount() {
        return perfilesList.size();
    }


    //se utiliza para filtrar la lista perfilesList en función de una cadena de búsqueda dada.
    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            perfilesList.clear();
            perfilesList.addAll(perfilesList2);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Cliente> collecion = perfilesList.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                perfilesList.clear();
                perfilesList.addAll(collecion);
            } else {
                for (Cliente c : perfilesList2) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        perfilesList.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

}